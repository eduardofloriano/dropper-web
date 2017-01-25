package br.com.dropper.web.bean;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.dropper.web.dao.UsuarioDAO;
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.transaction.Transacional;

@Named
@SessionScoped
public class SocialBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext context;

	// TODO: Persistencia e Transacao controladas por EJB
	@Inject
	private UsuarioDAO usuarioDAO;

	private Usuario usuario;
	private Usuario usuarioLogado;

	private List<Usuario> amigosTotal;
	private List<Usuario> amigosSugestao;

	public SocialBean() {
		System.out.println("Criando Bean SocialBean...");
	}

	@PostConstruct
	public void init() {
		System.out.println("Inicializando metodo init()...");
		this.usuario = new Usuario();
		this.usuarioLogado = new Usuario();
		atualizaListasAmigos(getUsuarioLogado());
		System.out.println("Finalizando metodo init()...");
	}

	private void atualizaListasAmigos(Usuario usuarioPrincipal) {
		this.amigosTotal = new ArrayList<Usuario>();
		this.amigosSugestao = usuarioDAO.obterTodosUsuarios();
		
		amigosTotal.addAll(usuarioPrincipal.getAmigos());
		amigosTotal.addAll(usuarioPrincipal.getAmigoDe());

		amigosSugestao.removeAll(amigosTotal);
		amigosSugestao.remove(usuarioPrincipal);

	}

	public Usuario getUsuarioLogado() {
		this.usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		return this.usuarioLogado;
	}

	@Transacional
	public List<Usuario> getAmigos() {
		System.out.println("Inicializando getAmigos()...");
		List<Usuario> amigosTotal = new ArrayList<Usuario>();

		Usuario usuarioLogado = getUsuarioLogado();
		Usuario usuarioPrincipal = usuarioDAO.findById(usuarioLogado.getId());

		amigosTotal.addAll(usuarioPrincipal.getAmigos());
		amigosTotal.addAll(usuarioPrincipal.getAmigoDe());

		this.amigosTotal = amigosTotal;

		System.out.println("Finalizando getAmigos()...");
		return this.amigosTotal;
	}

	@Transacional
	public List<Usuario> getAmigosSugestao() {
		System.out.println("Inicializando getAmigosSugestao()...");
		List<Usuario> amigosSugestao = usuarioDAO.obterTodosUsuarios();
		List<Usuario> amigosTotal = new ArrayList<Usuario>();

		Usuario usuarioLogado = getUsuarioLogado();
		Usuario usuarioPrincipal = usuarioDAO.findById(usuarioLogado.getId());

		amigosTotal.addAll(usuarioPrincipal.getAmigos());
		amigosTotal.addAll(usuarioPrincipal.getAmigoDe());

		amigosSugestao.removeAll(amigosTotal);
		amigosSugestao.remove(usuarioPrincipal);

		this.amigosSugestao = amigosSugestao;

		System.out.println("Finalizando getAmigosSugestao()...");
		return this.amigosSugestao;
	}

	@Transacional
	public StreamedContent getAmigo() throws Exception {
		System.out.println("Inicializando getAmigo()...");
		String id = context.getExternalContext().getRequestParameterMap().get("id");
		if (!(id == null || id.equals("") || id.equals(" "))) {
			Usuario usuario = usuarioDAO.findById(Integer.parseInt(id));
			System.out.println("Finalizando getAmigo()...");
			return new DefaultStreamedContent(new ByteArrayInputStream(
					usuario.getImagemPerfil() == null ? usuario.getImagemPerfilDefault() : usuario.getImagemPerfil()),
					"image/png");
		}

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so
			// that it will generate right URL.
			System.out.println("Finalizando getAmigo()...");
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real
			// StreamedContent with the image bytes.
			Usuario usuario = usuarioDAO.findById(Integer.parseInt(id));
			System.out.println("Finalizando getAmigo()...");
			return new DefaultStreamedContent(new ByteArrayInputStream(
					usuario.getImagemPerfil() == null ? usuario.getImagemPerfilDefault() : usuario.getImagemPerfil()),
					"image/png");
		}

	}

	@Transacional
	public void removerAmigo(Usuario amigo) {
		System.out.println("Inicializando removerAmigo()...");
		System.out.println("Removendo amigo...");
		System.out.println(amigo.getNome());
		amigo = usuarioDAO.findById(amigo.getId());

		Usuario usuarioPrincipal = getUsuarioLogado();

		if (usuarioPrincipal.getAmigos().contains(amigo)) {
			usuarioPrincipal.getAmigos().remove(amigo);
		} else if (usuarioPrincipal.getAmigoDe().contains(amigo)) {
			usuarioPrincipal.getAmigoDe().remove(amigo);
		}

		usuarioDAO.merge(usuarioPrincipal);

		System.out.println("Usuario Removido");
		atualizaListasAmigos(usuarioPrincipal);

		System.out.println("Finalizando removerAmigo()...");
	}

	@Transacional
	public void conectarAmigo(Usuario amigo) {
		System.out.println("Inicializando conectarAmigo()...");
		System.out.println("Conectando amigo...");
		System.out.println(amigo.getNome());
		amigo = usuarioDAO.findById(amigo.getId());

		Usuario usuarioPrincipal = getUsuarioLogado();

		usuarioPrincipal.getAmigos().add(amigo);
		usuarioDAO.merge(usuarioPrincipal);

		System.out.println("Usuario Conectado");
		
		atualizaListasAmigos(usuarioPrincipal);
		System.out.println("Finalizando conectarAmigo()...");
	}

	// Setters Getters
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}