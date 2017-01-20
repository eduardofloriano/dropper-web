package br.com.dropper.web.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.dropper.web.dao.UsuarioDAO;
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.util.JpaUtil;

@Named
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext context;

	@Inject
	private Usuario usuario;
	
	//TODO: Persistencia e Transacao controladas por EJB
	private EntityManager em = new JpaUtil().getEntityManager();
	private UsuarioDAO usuarioDAO = new UsuarioDAO(em);

	public Usuario getUsuario() {
		return usuario;
	}

	public String autenticar() {
		Usuario usuario = usuarioDAO.obterUsuarioPorEmail(this.usuario);

		if (usuario == null) {
			context.addMessage(null, new FacesMessage("Usuario não encontrado."));
			context.getExternalContext().getFlash().setKeepMessages(true);
			return "login?faces-redirect=true";
		} else {
			context.getExternalContext().getSessionMap().put("usuarioLogado", usuario);
			return "dashboardImagem.xhtml?faces-redirect=true";
		}
	}

	public String cadastrarUsuario() {
		System.out.println("Redirecionando para cadastroUsuario.xhtml");
		return "cadastroUsuario.xhtml?faces-redirect=true";
	}

	public String logout() {
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		context.getExternalContext().invalidateSession();
		return "login.xhtml?faces-redirect=true";
	}

}
