package br.com.dropper.web.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.persistence.EntityManager;

import br.com.dropper.web.dao.RepositorioDAO;
import br.com.dropper.web.dao.UsuarioDAO;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Usuario getUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");		
		return this.usuarioLogado;
	}
	
	public String cadastrar() {
		System.out.println("Persistindo Usuário: " + usuario.getEmail());
		usuarioDAO.persist(this.usuario);

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Usuário Cadastrado com sucesso!"));
		context.getExternalContext().getFlash().setKeepMessages(true);
		return null;
	}

	public void alterar(){
		System.out.println("Atualizando Usuário");
		usuarioDAO.merge(this.usuarioLogado);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Usuário Atualizado com sucesso!"));
		context.getExternalContext().getFlash().setKeepMessages(true);
	}
	
	public Long getEspacoDisponivel(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return 0L;
		}else{
			Long espacoTotal = usuarioLogado.getRepositorio().getEspacoTotal();
			Long espacoOcupado = repositorioDAO.obterEspacoOcupadoPorUsuario(usuarioLogado);
			return ((espacoTotal - espacoOcupado) * 100) / espacoTotal ;
		}
		
		
		
	}
	
}
