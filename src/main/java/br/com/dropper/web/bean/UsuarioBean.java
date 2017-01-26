package br.com.dropper.web.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.dropper.web.dao.RepositorioDAO;
import br.com.dropper.web.dao.UsuarioDAO;
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.transaction.Transacional;

@Named
@SessionScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext context;

	// TODO: Persistencia e Transacao controladas por EJB
	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Inject
	private RepositorioDAO repositorioDAO;

	private Usuario usuario;
	private Usuario usuarioLogado;
	
	private Part file;
	
	@PostConstruct
	public void init(){
		this.usuario = new Usuario();
		this.usuarioLogado = new Usuario();

	}

	public Usuario getUsuarioLogado() {
		this.usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		return this.usuarioLogado;
	}

	@Transacional
	public String cadastrar() {
		System.out.println("Persistindo Usuário: " + usuario.getEmail());
		
		if(file != null){
			try {
				
				this.usuario.setImagemPerfil(IOUtils.toByteArray(file.getInputStream()));
				System.out.println("Possuí imagem de perfil! " + file.getName());
			} catch (Exception e) {
				System.out.println("Ocorreu um erro ao processar a imagem de perfil do usuário.");
				e.printStackTrace();
			}			
		}
		
		usuarioDAO.persist(this.usuario);
		context.addMessage(null, new FacesMessage("Usuário Cadastrado com sucesso!"));
		context.getExternalContext().getFlash().setKeepMessages(true);
		return null;
	}

	@Transacional
	public String remover(){
		System.out.println("Removendo Usuário: " + usuarioLogado.getEmail());
		
		Usuario usuario = usuarioDAO.findById(this.usuarioLogado.getId());
		usuarioDAO.remove(usuario);
		
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		context.getExternalContext().invalidateSession();
		return "login.xhtml?faces-redirect=true";
		
	}
	
	@Transacional
	public void alterar() {
		System.out.println("Atualizando Usuário");
		usuarioDAO.merge(this.usuarioLogado);
		context.addMessage(null, new FacesMessage("Usuário Atualizado com sucesso!"));
		context.getExternalContext().getFlash().setKeepMessages(true);
	}

	public Long getEspacoDisponivel() {
		if (getUsuarioLogado() != null) {
			Long espacoTotal = usuarioLogado.getRepositorio().getEspacoTotal();
			Long espacoOcupado = repositorioDAO.obterEspacoOcupadoImagemPorUsuario(usuarioLogado)
					+ repositorioDAO.obterEspacoOcupadoArquivoPorUsuario(usuarioLogado)
					+ repositorioDAO.obterEspacoOcupadoVideoPorUsuario(usuarioLogado);

			return (espacoOcupado * 100) / espacoTotal;
		}
		return 1L;
	}

	@Transacional
	public void alterarImagemPerfil() throws IOException {
		System.out.println("Alterando Imagem de Pefil do Usuário");
		this.usuarioLogado.setImagemPerfil(IOUtils.toByteArray(file.getInputStream()));
		usuarioDAO.merge(this.usuarioLogado);
		context.addMessage(null, new FacesMessage("Usuário Atualizado com sucesso!"));
		context.getExternalContext().getFlash().setKeepMessages(true);
	}
	
	@Transacional
	public StreamedContent getImagemPerfil() throws Exception {
		String id = context.getExternalContext().getRequestParameterMap().get("id");
		if (!(id == null || id.equals("") || id.equals(" "))) {
			Usuario usuario = usuarioDAO.findById(Integer.parseInt(id));
			return new DefaultStreamedContent(new ByteArrayInputStream(usuario.getImagemPerfil()), "image/png");
		}

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
			// return new DefaultStreamedContent(new
			// ByteArrayInputStream(usuarioLogado.getImagemPerfil()),
			// "image/png");
		} else {
			// So, browser is requesting the image. Return a real
			// StreamedContent with the image bytes.
			Usuario usuario = usuarioDAO.findById(Integer.parseInt(id));
			return new DefaultStreamedContent(new ByteArrayInputStream(usuario.getImagemPerfil()), "image/png");
		}

		}

	// Setters & Getters
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}
}
