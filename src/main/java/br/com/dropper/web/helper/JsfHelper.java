package br.com.dropper.web.helper;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public class JsfHelper {
	
	@Inject
	FacesContext context;
	
	public void addMessage(String messageSummary, String messageDetail){
		FacesMessage message = new FacesMessage(messageSummary, messageDetail);
		context.addMessage(null, message);
	}
	

}
