package br.com.dropper.web.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.dropper.web.builder.ImagemBuilder;
import br.com.dropper.web.dao.ImagemDAO;
import br.com.dropper.web.model.Imagem;
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.transaction.Transacional;
import br.com.dropper.web.util.ImageUtil;

@Named
@SessionScoped
public class ImagemBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext context;

	@Inject
	private ImagemBuilder builder;
	
	@Inject
	private ImageUtil imageUtil;
	
	private UploadedFile file;

	// TODO: Persistencia e Transacao controladas por EJB
	@Inject
	private ImagemDAO imagemDAO;

	private List<Imagem> imagens;

	@PostConstruct
	public void init() {
		atualizaListaImagem();
	}

	@Transacional
	private void atualizaListaImagem() {
		this.imagens = imagemDAO.obterImagensPorUsuario(getUsuarioLogado());
	}

	private Usuario getUsuarioLogado() {
		Usuario usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		return usuario;
	}

	@Transacional
	public void handleFileUpload(FileUploadEvent event) throws IOException {
		this.file = event.getFile();

		builder.setNome(file.getFileName()).setTamanho(file.getSize()).setDataInclusao(null)
				.setData(file.getInputstream()).setUsuario(getUsuarioLogado());

		Imagem imagem = builder.construct();
		imagemDAO.persist(imagem);

		FacesMessage message = new FacesMessage("Imagem ", event.getFile().getFileName() + " cadastrada com sucesso!");
		context.addMessage(null, message);

		atualizaListaImagem();
	}

	@Transacional
	public void remover(Imagem imagem) {
		System.out.println("Vai remover a imagem: " + imagem.getNome() + " - " + imagem.getId());
		imagem = imagemDAO.findById(imagem.getId());
		imagemDAO.remove(imagem);

		atualizaListaImagem();
	}

	@Transacional
	public StreamedContent download(Imagem imagem) throws IOException {
		System.out.println("Vai realizar o download da imagem: " + imagem.getNome() + " - " + imagem.getId());
		imagem = imagemDAO.findById(imagem.getId());

		// TODO download
		StreamedContent file = new DefaultStreamedContent(new ByteArrayInputStream(imagem.getData()), "image/png",
				imagem.getNome());

		// return
		return file;

	}

	@Transacional
	public StreamedContent getImagem() throws Exception {
		String id = context.getExternalContext().getRequestParameterMap().get("id");
		if (!(id == null || id.equals("") || id.equals(" "))) {
			Imagem imagem = imagemDAO.findById(Integer.parseInt(id));
			byte[] imagemResized = imageUtil.resize(new ByteArrayInputStream(imagem.getData()), 350, 350, "png");
			return new DefaultStreamedContent(new ByteArrayInputStream(imagemResized), "image/png");
		}

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real
			// StreamedContent with the image bytes.
			Imagem imagem = imagemDAO.findById(Integer.parseInt(id));
			byte[] imagemResized = imageUtil.resize(new ByteArrayInputStream(imagem.getData()), 350, 350, "png");
			return new DefaultStreamedContent(new ByteArrayInputStream(imagemResized), "image/png");
		}

	}

	// Setters & Getters
	public List<Imagem> getImagens() {
		return imagens;
	}

	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}

}
