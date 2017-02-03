package br.com.dropper.web.bean;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.service.UsuarioService;
import br.com.dropper.web.transaction.Transacional;

@Named
@SessionScoped
public class SocialBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final int MAX_AMIGOS_SUGESTAO = 4;
	
	@Inject
	private FacesContext context;

	@Inject
	private UsuarioService usuarioService;

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
		this.amigosTotal = preencheListaAmigosTotal(usuarioPrincipal);
		this.amigosSugestao = preencheListaAmigosSugestao(amigosTotal, usuarioPrincipal);
	}
	
	private List<Usuario> preencheListaAmigosTotal(Usuario usuarioPrincipal){
		List<Usuario> amigosTotal = new ArrayList<Usuario>();
		amigosTotal.addAll(usuarioPrincipal.getAmigos());
		amigosTotal.addAll(usuarioPrincipal.getAmigoDe());
		return amigosTotal;
	}
	
	private List<Usuario> preencheListaAmigosSugestao(List<Usuario> amigosTotal, Usuario usuarioPrincipal){
		List<Usuario> amigosSugestao = usuarioService.obterTodosUsuarios();
		amigosSugestao.removeAll(amigosTotal);
		amigosSugestao.remove(usuarioPrincipal);
		
		Collections.shuffle(amigosSugestao);
		if(!(amigosSugestao.size() < MAX_AMIGOS_SUGESTAO)){
			return amigosSugestao.subList(0, MAX_AMIGOS_SUGESTAO);
		}else
			return amigosSugestao;
		
	}

	public Usuario getUsuarioLogado() {
		this.usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		return this.usuarioLogado;
	}

	@Transacional
	public List<Usuario> getAmigos() {
		Usuario usuarioPrincipal = usuarioService.buscarUsuarioManagedPorId(getUsuarioLogado().getId());
		this.amigosTotal = preencheListaAmigosTotal(usuarioPrincipal);
		return this.amigosTotal;
	}

	@Transacional
	public List<Usuario> getAmigosSugestao() {
		return this.amigosSugestao;
	}

	@Transacional
	public StreamedContent getAmigo() throws Exception {
		System.out.println("Inicializando getAmigo()...");
		String id = context.getExternalContext().getRequestParameterMap().get("id");
		if (!(id == null || id.equals("") || id.equals(" "))) {
			Usuario usuario = usuarioService.buscarUsuarioManagedPorId(Integer.parseInt(id));
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
			Usuario usuario = usuarioService.buscarUsuarioManagedPorId(Integer.parseInt(id));
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
		amigo = usuarioService.buscarUsuarioManagedPorId(amigo.getId());

		Usuario usuarioPrincipal = getUsuarioLogado();

		if (usuarioPrincipal.getAmigos().contains(amigo)) {
			usuarioPrincipal.getAmigos().remove(amigo);
		} else if (usuarioPrincipal.getAmigoDe().contains(amigo)) {
			usuarioPrincipal.getAmigoDe().remove(amigo);
		}

		usuarioService.alterarUsuarioManaged(usuarioPrincipal);

		System.out.println("Usuario Removido");

		this.amigosSugestao.remove(amigo);
		this.amigosTotal.add(amigo);

		System.out.println("Finalizando removerAmigo()...");
	}

	@Transacional
	public void conectarAmigo(Usuario amigo) {
		System.out.println("Inicializando conectarAmigo()...");
		System.out.println("Conectando amigo...");
		System.out.println(amigo.getNome());
		amigo = usuarioService.buscarUsuarioManagedPorId(amigo.getId());

		Usuario usuarioPrincipal = getUsuarioLogado();

		usuarioPrincipal.getAmigos().add(amigo);
		usuarioService.alterarUsuarioManaged(usuarioPrincipal);

		System.out.println("Usuario Conectado");
		
		this.amigosSugestao.remove(amigo);
		this.amigosTotal.add(amigo);
		
		if(amigosSugestao.isEmpty()){
			this.amigosSugestao = preencheListaAmigosSugestao(amigosTotal, usuarioPrincipal);
		}
		
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
