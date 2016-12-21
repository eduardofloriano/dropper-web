package br.com.dropper.web.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.com.dropper.web.dao.UsuarioDAO;
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.util.JpaUtil;

@ManagedBean
@ViewScoped
public class UsuarioBean {

	private EntityManager em = new JpaUtil().getEntityManager();
	private UsuarioDAO usuarioDAO = new UsuarioDAO(em);

	private Usuario usuario = new Usuario();

	

	public Usuario getUsuario() {
		return usuario;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}



	public String cadastrar(){
		System.out.println("Persistindo Usuário: " + usuario.getEmail());
		usuarioDAO.persist(this.usuario);
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Usuário Cadastrado com sucesso!"));
		context.getExternalContext().getFlash().setKeepMessages(true);
		return null;
	}

	
}
