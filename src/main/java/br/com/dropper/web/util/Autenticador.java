package br.com.dropper.web.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.dropper.web.model.Usuario;

public class Autenticador implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		
		FacesContext context = event.getFacesContext();
		String nomePagina = context.getViewRoot().getViewId();
		
		
		//TODO: rola usar um strategy ou um chain aqui?
		if(nomePagina.equals("/login.xhtml")){
			return;
		}
		
		if(nomePagina.equals("/cadastroUsuario.xhtml")){
			return;
		}
		
		Usuario usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		if(usuario != null){
			return;
		}
		
		//retornar para pagina de login
		NavigationHandler handler = context.getApplication().getNavigationHandler();
		handler.handleNavigation(context, null, "/login?faces-redirect=true");
		context.renderResponse();
		
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		System.out.println("FASE: " + arg0.getPhaseId());
		
	}

	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return PhaseId.RESTORE_VIEW;
	}

}
