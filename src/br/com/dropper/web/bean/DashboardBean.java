package br.com.dropper.web.bean;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.dropper.web.builder.ImagemBuilder;
import br.com.dropper.web.dao.ImagemDAO;
import br.com.dropper.web.model.Imagem;
import br.com.dropper.web.util.JpaUtil;

@ManagedBean
@ViewScoped
public class DashboardBean {

	private UploadedFile file;
	private EntityManager em = new JpaUtil().getEntityManager();
	private ImagemDAO imagemDAO = new ImagemDAO(em);
	
	public void dull() {

	}

	public void handleFileUpload(FileUploadEvent event) throws IOException {
		
		this.file = event.getFile();
		
		ImagemBuilder builder = new ImagemBuilder();
		builder.setNome(file.getFileName())
		.setTamanho(file.getSize())
		.setDataInclusao(null)
		.setData(file.getInputstream());
		
		Imagem imagem = builder.construct();
		imagemDAO.persist(imagem);
		
		FacesMessage message = new FacesMessage("Imagem ", event.getFile().getFileName() + " cadastrada com sucesso!");
		FacesContext.getCurrentInstance().addMessage(null, message);

	}

}
