package br.com.dropper.web.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.dropper.web.dao.RepositorioDAO;
import br.com.dropper.web.dao.UsuarioDAO;
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.util.JpaUtil;

@Named
@SessionScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FacesContext context;
	
	private EntityManager em = new JpaUtil().getEntityManager();
	private UsuarioDAO usuarioDAO = new UsuarioDAO(em);
	RepositorioDAO repositorioDAO = new RepositorioDAO(em);

	private Usuario usuario = new Usuario();
	private Usuario usuarioLogado = new Usuario();

	private Part file;

	public Usuario getUsuarioLogado() {
		this.usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		return this.usuarioLogado;
	}

	public String cadastrar() {
		System.out.println("Persistindo Usu�rio: " + usuario.getEmail());
		
		if(file != null){
			try {
				this.usuario.setImagemPerfil(IOUtils.toByteArray(file.getInputStream()));
				System.out.println("Possu� imagem de perfil! " + file.getName());
			} catch (Exception e) {
				System.out.println("Ocorreu um erro ao processar a imagem de perfil do usu�rio.");
				e.printStackTrace();
			}			
		}
		
		usuarioDAO.persist(this.usuario);
		context.addMessage(null, new FacesMessage("Usu�rio Cadastrado com sucesso!"));
		context.getExternalContext().getFlash().setKeepMessages(true);
		return null;
	}

	public void alterar() {
		System.out.println("Atualizando Usu�rio");
		usuarioDAO.merge(this.usuarioLogado);
		context.addMessage(null, new FacesMessage("Usu�rio Atualizado com sucesso!"));
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

	
	public void alterarImagemPerfil() throws IOException{
		System.out.println("Alterando Imagem de Pefil do Usu�rio");
		this.usuarioLogado.setImagemPerfil(IOUtils.toByteArray(file.getInputStream()));
		usuarioDAO.merge(this.usuarioLogado);
		context.addMessage(null, new FacesMessage("Usu�rio Atualizado com sucesso!"));
		context.getExternalContext().getFlash().setKeepMessages(true);
		
	}
	
	public StreamedContent getImagemPerfil() throws Exception {
		String id = context.getExternalContext().getRequestParameterMap().get("id");
		if (!(id == null || id.equals("") || id.equals(" "))) {
			Usuario usuario = usuarioDAO.obterUsuarioPorId(Integer.parseInt(id));
			return new DefaultStreamedContent(new ByteArrayInputStream(usuario.getImagemPerfil()), "image/png");
		}

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
//			return new DefaultStreamedContent(new ByteArrayInputStream(usuarioLogado.getImagemPerfil()), "image/png");
		} else {
			// So, browser is requesting the image. Return a real
			// StreamedContent with the image bytes.
			Usuario usuario = usuarioDAO.obterUsuarioPorId(Integer.parseInt(id));
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
