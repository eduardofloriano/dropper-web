package br.com.dropper.web.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.dropper.web.builder.VideoBuilder;
import br.com.dropper.web.dao.VideoDAO;
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.model.Video;

@Named
@SessionScoped
public class VideoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext context;

	@Inject
	private VideoBuilder builder;

	private UploadedFile file;

	// TODO: Persistencia e Transacao controladas por EJB
	@Inject
	private VideoDAO videoDAO;

	private List<Video> videos;

	@PostConstruct
	public void init() {
		atualizaListaVideo();
	}

	private void atualizaListaVideo() {
		this.videos = videoDAO.obterVideosPorUsuario(getUsuarioLogado());
	}

	public void handleFileUpload(FileUploadEvent event) throws IOException {

		this.file = event.getFile();

		builder.setNome(file.getFileName()).setTamanho(file.getSize()).setDataInclusao(null)
				.setData(file.getInputstream()).setUsuario(getUsuarioLogado());

		Video video = builder.construct();
		videoDAO.persist(video);

		FacesMessage message = new FacesMessage("Video ", event.getFile().getFileName() + " cadastrado com sucesso!");
		context.addMessage(null, message);

		atualizaListaVideo();
	}

	private Usuario getUsuarioLogado() {
		Usuario usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		return usuario;
	}

	public void remover(Video video) {
		System.out.println("Vai remover o arquivo: " + video.getNome() + " - " + video.getId());
		video = videoDAO.findById(video.getId());
		videoDAO.remove(video);

		atualizaListaVideo();
	}

	public StreamedContent download(Video video) throws IOException {
		System.out.println("Vai realizar o download da imagem: " + video.getNome() + " - " + video.getId());
		video = videoDAO.findById(video.getId());

		// TODO download
		StreamedContent file = new DefaultStreamedContent(new ByteArrayInputStream(video.getData()), null,
				video.getNome());

		// return
		return file;
	}

	// Setters & Getters

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

}
