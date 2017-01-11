package br.com.dropper.web.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.persistence.EntityManager;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.dropper.web.builder.VideoBuilder;
import br.com.dropper.web.dao.VideoDAO;
import br.com.dropper.web.model.Video;
import br.com.dropper.web.model.Arquivo;
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.util.JpaUtil;

@ManagedBean
@ApplicationScoped
public class VideoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1759708926235041459L;
	private UploadedFile file;
	private EntityManager em = new JpaUtil().getEntityManager();
	private VideoDAO videoDAO = new VideoDAO(em);

	private List<Video> videos = videoDAO.obterVideosPorUsuario(getUsuarioLogado());

	public void handleFileUpload(FileUploadEvent event) throws IOException {

		this.file = event.getFile();

		VideoBuilder builder = new VideoBuilder();
		builder.setNome(file.getFileName()).setTamanho(file.getSize()).setDataInclusao(null)
				.setData(file.getInputstream()).setUsuario(getUsuarioLogado());

		Video video = builder.construct();
		videoDAO.persist(video);

		FacesMessage message = new FacesMessage("Video ", event.getFile().getFileName() + " cadastrado com sucesso!");
		FacesContext.getCurrentInstance().addMessage(null, message);

		atualizaListaVideo();
	}

	private void atualizaListaVideo() {
		this.videos = videoDAO.obterVideosPorUsuario(getUsuarioLogado());
	}

	private Usuario getUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		return usuario;
	}

	// Setters & Getters

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

//	public StreamedContent getVideo() throws Exception {
//
//		FacesContext context = FacesContext.getCurrentInstance();
//		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
//            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
//            return new DefaultStreamedContent();
//        }
//        else {
//            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
//            String id = context.getExternalContext().getRequestParameterMap().get("id");
//            Video video = videoDAO.obterVideoPorId(Integer.parseInt(id));
//            
//            return new DefaultStreamedContent(new ByteArrayInputStream(video.getData()), "image/png");
//        }
//
//	}

	
	
	public void remover(Video video) {
		System.out.println("Vai remover o arquivo: " + video.getNome() + " - " + video.getId());
		video = videoDAO.obterVideoPorId(video.getId());
		videoDAO.remove(video);

		atualizaListaVideo();
	}

	public StreamedContent download(Video video) throws IOException {
		System.out.println("Vai realizar o download da imagem: " + video.getNome() + " - " + video.getId());
		video = videoDAO.obterVideoPorId(video.getId());
		
		// TODO download
		StreamedContent file = new DefaultStreamedContent(new ByteArrayInputStream(video.getData()), null,
				video.getNome());

		// return
		return file;

	}
	
}
