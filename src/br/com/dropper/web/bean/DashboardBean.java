package br.com.dropper.web.bean;

import java.io.IOException;
import java.io.InputStream;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class DashboardBean {

	private UploadedFile file;
	
	public void dull() {

	}

	public void handleFileUpload(FileUploadEvent event) throws IOException {
		
		this.file = event.getFile();
		
		
		InputStream input = file.getInputstream();
		
		
		
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);

	}

}
