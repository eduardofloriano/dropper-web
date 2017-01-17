package br.com.dropper.web.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.persistence.EntityManager;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.dropper.web.builder.ImagemBuilder;
import br.com.dropper.web.dao.ImagemDAO;
import br.com.dropper.web.model.Imagem;
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.util.JpaUtil;

@ManagedBean
@SessionScoped
public class ImagemBean implements Serializable {

	public ImagemBean(){
		System.out.println("Criando um managedBean");
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4639661786842341443L;
	private UploadedFile file;
	private EntityManager em = new JpaUtil().getEntityManager();
	private ImagemDAO imagemDAO = new ImagemDAO(em);

	private List<Imagem> imagens = imagemDAO.obterImagensPorUsuario(getUsuarioLogado());

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

	public void remover(Imagem imagem) {
		System.out.println("Vai remover a imagem: " + imagem.getNome() + " - " + imagem.getId());
		imagem = imagemDAO.obterImagemPorId(imagem.getId());
		imagemDAO.remove(imagem);

		atualizaListaImagem();
	}

	public StreamedContent download(Imagem imagem) throws IOException {
		System.out.println("Vai realizar o download da imagem: " + imagem.getNome() + " - " + imagem.getId());
		imagem = imagemDAO.obterImagemPorId(imagem.getId());

		// TODO download
		StreamedContent file = new DefaultStreamedContent(new ByteArrayInputStream(imagem.getData()), "image/png",
				imagem.getNome());

		// return
		return file;

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

	public StreamedContent getImagem() throws Exception {

		FacesContext context = FacesContext.getCurrentInstance();
		String id = context.getExternalContext().getRequestParameterMap().get("id");
		if (!(id == null || id.equals("") || id.equals(" "))) {
			Imagem imagem = imagemDAO.obterImagemPorId(Integer.parseInt(id));
			return new DefaultStreamedContent(new ByteArrayInputStream(imagem.getData()), "image/png");
		}

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real
			// StreamedContent with the image bytes.
			Imagem imagem = imagemDAO.obterImagemPorId(Integer.parseInt(id));
			return new DefaultStreamedContent(new ByteArrayInputStream(imagem.getData()), "image/png");
		}

	}

}
