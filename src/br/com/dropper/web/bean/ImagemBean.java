package br.com.dropper.web.bean;

import java.io.IOException;
import java.util.List;

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
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.util.JpaUtil;

@ManagedBean
@ViewScoped
public class ImagemBean {

	private UploadedFile file;
	private EntityManager em = new JpaUtil().getEntityManager();
	private ImagemDAO imagemDAO = new ImagemDAO(em);

	private List<Imagem> imagens;

	public void handleFileUpload(FileUploadEvent event) throws IOException {

		this.file = event.getFile();

		ImagemBuilder builder = new ImagemBuilder();
		builder.setNome(file.getFileName()).setTamanho(file.getSize()).setDataInclusao(null)
				.setData(file.getInputstream()).setUsuario(getUsuarioLogado());

		Imagem imagem = builder.construct();
		imagemDAO.persist(imagem);

		FacesMessage message = new FacesMessage("Imagem ", event.getFile().getFileName() + " cadastrada com sucesso!");
		FacesContext.getCurrentInstance().addMessage(null, message);

		atualizaListaImagem();
	}

	private void atualizaListaImagem() {
		this.imagens = imagemDAO.obterImagensPorUsuario(getUsuarioLogado());
	}

	private Usuario getUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		return usuario;
	}

	// Setters & Getters

	public List<Imagem> getImagens() {
		return imagens;
	}

	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}
}
