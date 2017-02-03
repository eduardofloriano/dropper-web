package br.com.dropper.web.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.service.UsuarioService;
import br.com.dropper.web.transaction.Transacional;

@Named
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext context;

	@Inject
	private Usuario usuario;
	
	@Inject
	private UsuarioService usuarioService;
	
	private boolean modalBoasVindas;
	
	@PostConstruct
	public void init(){
		modalBoasVindas = true;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	@Transacional
	public String autenticar() {

		Usuario usuario = usuarioService.obterUsuarioPorEmail(this.usuario);

		if (usuario == null) {
			context.addMessage(null, new FacesMessage("Usuario não encontrado."));
			context.getExternalContext().getFlash().setKeepMessages(true);
			modalBoasVindas = false;
			return "login?faces-redirect=true";
		} else {
			context.getExternalContext().getSessionMap().put("usuarioLogado", usuario);
			return "dashboardImagem.xhtml?faces-redirect=true";
		}
	}
	
	public String cadastrarUsuario(){
		System.out.println("Redirecionando para cadastroUsuario.xhtml");
		return "cadastroUsuario.xhtml?faces-redirect=true";
	}

	public String logout() {
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		context.getExternalContext().invalidateSession();
		return "login.xhtml?faces-redirect=true";
	}

	public boolean isModalBoasVindas() {
		return modalBoasVindas;
	}

	public void setModalBoasVindas(boolean modalBoasVindas) {
		this.modalBoasVindas = modalBoasVindas;
	}
	
}
