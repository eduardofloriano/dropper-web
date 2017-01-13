package br.com.dropper.web.bean;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.util.JpaUtil;

@ManagedBean
@SessionScoped
public class SocialBean implements Serializable {

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

	public void carregarImagemPerfil() {

	}

	public String cadastrar() {
		System.out.println("Persistindo Usu�rio: " + usuario.getEmail());

		if (file != null) {
			try {
				this.usuario.setImagemPerfil(IOUtils.toByteArray(file.getInputstream()));
				System.out.println("Possu� imagem de perfil! " + file.getFileName());
			} catch (Exception e) {
				System.out.println("Ocorreu um erro ao processar a imagem de perfil do usu�rio.");
				e.printStackTrace();
			}
		}

		usuarioDAO.persist(this.usuario);

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Usu�rio Cadastrado com sucesso!"));
		context.getExternalContext().getFlash().setKeepMessages(true);
		return null;
	}

	public void alterar() {
		System.out.println("Atualizando Usu�rio");
		usuarioDAO.merge(this.usuarioLogado);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Usu�rio Atualizado com sucesso!"));
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
		// String id =
		// context.getExternalContext().getRequestParameterMap().get("id");
		// if (!(id == null || id.equals("") || id.equals(" "))) {
		// Imagem imagem = imagemDAO.obterImagemPorId(Integer.parseInt(id));
		// return new DefaultStreamedContent(new
		// ByteArrayInputStream(imagem.getData()), "image/png");
		// }

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so
			// that it will generate right URL.
			// return new DefaultStreamedContent();
			return new DefaultStreamedContent(new ByteArrayInputStream(usuarioLogado.getImagemPerfil()), "image/png");
		} else {
			// So, browser is requesting the image. Return a real
			// StreamedContent with the image bytes.
			// Imagem imagem = imagemDAO.obterImagemPorId(Integer.parseInt(id));
			return new DefaultStreamedContent(new ByteArrayInputStream(usuarioLogado.getImagemPerfil()), "image/png");
		}

	}

	public List<Usuario> getAmigos() {
		List<Usuario> amigosTotal = new ArrayList<Usuario>();

		Usuario usuarioLogado = getUsuarioLogado();
		Usuario usuarioPrincipal = usuarioDAO.obterUsuarioPorId(usuarioLogado.getId());

		amigosTotal.addAll(usuarioPrincipal.getAmigos());
		amigosTotal.addAll(usuarioPrincipal.getAmigoDe());
		return amigosTotal;
	}

	public List<Usuario> getAmigosSugestao() {
		
		List<Usuario> amigosSugestao = usuarioDAO.obterTodosUsuarios();
		List<Usuario> amigosTotal = new ArrayList<Usuario>();
		
		Usuario usuarioLogado = getUsuarioLogado();
		Usuario usuarioPrincipal = usuarioDAO.obterUsuarioPorId(usuarioLogado.getId());
		
		amigosTotal.addAll(usuarioPrincipal .getAmigos());
		amigosTotal.addAll(usuarioPrincipal .getAmigoDe());
		
		amigosSugestao.removeAll(amigosTotal);
		amigosSugestao.remove(usuarioPrincipal);
		
		return  amigosSugestao;
	}

	public StreamedContent getAmigo() throws Exception {

		FacesContext context = FacesContext.getCurrentInstance();
		String id = context.getExternalContext().getRequestParameterMap().get("id");
		if (!(id == null || id.equals("") || id.equals(" "))) {
			Usuario usuario = usuarioDAO.obterUsuarioPorId(Integer.parseInt(id));
			return new DefaultStreamedContent(new ByteArrayInputStream(
					usuario.getImagemPerfil() == null ? usuario.getImagemPerfilDefault() : usuario.getImagemPerfil()),
					"image/png");
		}

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real
			// StreamedContent with the image bytes.
			Usuario usuario = usuarioDAO.obterUsuarioPorId(Integer.parseInt(id));
			return new DefaultStreamedContent(new ByteArrayInputStream(
					usuario.getImagemPerfil() == null ? usuario.getImagemPerfilDefault() : usuario.getImagemPerfil()),
					"image/png");
		}

	}

}
