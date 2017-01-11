package br.com.dropper.web.bean;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.persistence.EntityManager;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.dropper.web.dao.RepositorioDAO;
import br.com.dropper.web.dao.UsuarioDAO;
import br.com.dropper.web.model.Imagem;
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.util.JpaUtil;

@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6755567510858433234L;
	private EntityManager em = new JpaUtil().getEntityManager();
	private UsuarioDAO usuarioDAO = new UsuarioDAO(em);
	RepositorioDAO repositorioDAO = new RepositorioDAO(em);

	private Usuario usuario = new Usuario();
	private Usuario usuarioLogado = new Usuario();

	private UploadedFile file;
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Usuario getUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		return this.usuarioLogado;
	}

	public void carregarImagemPerfil(){
		
	}
	
	public String cadastrar() {
		System.out.println("Persistindo Usuário: " + usuario.getEmail());
		
		if(file != null){
			try {
				this.usuario.setImagemPerfil(IOUtils.toByteArray(file.getInputstream()));
				System.out.println("Possuí imagem de perfil! " + file.getFileName());
			} catch (Exception e) {
				System.out.println("Ocorreu um erro ao processar a imagem de perfil do usuário.");
				e.printStackTrace();
			}			
		}
		
		usuarioDAO.persist(this.usuario);

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Usuário Cadastrado com sucesso!"));
		context.getExternalContext().getFlash().setKeepMessages(true);
		return null;
	}

	public void alterar() {
		System.out.println("Atualizando Usuário");
		usuarioDAO.merge(this.usuarioLogado);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Usuário Atualizado com sucesso!"));
		context.getExternalContext().getFlash().setKeepMessages(true);
	}

	public Long getEspacoDisponivel() {
		if (getUsuarioLogado() != null) {
			Long espacoTotal = usuarioLogado.getRepositorio().getEspacoTotal();
			// Long espacoOcupado =
			// repositorioDAO.obterEspacoOcupadoPorUsuario(usuarioLogado);
			// Long espadoDisponivel = espacoTotal - espacoOcupado;
			// return ((espacoTotal - espacoOcupado) * 100) / espacoTotal;

			Long espacoOcupado = repositorioDAO.obterEspacoOcupadoImagemPorUsuario(usuarioLogado)
					+ repositorioDAO.obterEspacoOcupadoArquivoPorUsuario(usuarioLogado)
					+ repositorioDAO.obterEspacoOcupadoVideoPorUsuario(usuarioLogado);

			return (espacoOcupado * 100) / espacoTotal;
		}
		return 1L;
	}

	
	public StreamedContent getImagemPerfil() throws Exception {

		FacesContext context = FacesContext.getCurrentInstance();
//		String id = context.getExternalContext().getRequestParameterMap().get("id");
//		if (!(id == null || id.equals("") || id.equals(" "))) {
//			Imagem imagem = imagemDAO.obterImagemPorId(Integer.parseInt(id));
//			return new DefaultStreamedContent(new ByteArrayInputStream(imagem.getData()), "image/png");
//		}

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so
			// that it will generate right URL.
//			return new DefaultStreamedContent();
			return new DefaultStreamedContent(new ByteArrayInputStream(usuarioLogado.getImagemPerfil()), "image/png");
		} else {
			// So, browser is requesting the image. Return a real
			// StreamedContent with the image bytes.
//			Imagem imagem = imagemDAO.obterImagemPorId(Integer.parseInt(id));
			return new DefaultStreamedContent(new ByteArrayInputStream(usuarioLogado.getImagemPerfil()), "image/png");
		}

	}
	
}
