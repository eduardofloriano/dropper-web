package br.com.dropper.web.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.dropper.web.builder.AudioBuilder;
import br.com.dropper.web.dao.AudioDAO;
import br.com.dropper.web.model.Audio;
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.util.JpaUtil;

@ManagedBean
@ApplicationScoped
public class AudioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -105011233399925138L;
	private UploadedFile file;
	private EntityManager em = new JpaUtil().getEntityManager();
	private AudioDAO audioDAO = new AudioDAO(em);

	private List<Audio> audios = audioDAO.obterAudiosPorUsuario(getUsuarioLogado());

	public void handleFileUpload(FileUploadEvent event) throws IOException {

		this.file = event.getFile();

		AudioBuilder builder = new AudioBuilder();
		builder.setNome(file.getFileName()).setTamanho(file.getSize()).setDataInclusao(null)
				.setData(file.getInputstream()).setUsuario(getUsuarioLogado());

		Audio audio = builder.construct();
		audioDAO.persist(audio);

		FacesMessage message = new FacesMessage("Audio ", event.getFile().getFileName() + " cadastrado com sucesso!");
		FacesContext.getCurrentInstance().addMessage(null, message);

		atualizaListaAudios();
	}

	private void atualizaListaAudios() {
		this.audios = audioDAO.obterAudiosPorUsuario(getUsuarioLogado());
	}

	private Usuario getUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		return usuario;
	}

	// Setters & Getters

	public List<Audio> getAudios() {
		return audios;
	}

	public void setAudios(List<Audio> audios) {
		this.audios = audios;
	}
	
	
//	public StreamedContent getArquivo() throws Exception {
//
//		FacesContext context = FacesContext.getCurrentInstance();
//		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
//			// So, we're rendering the view. Return a stub StreamedContent so
//			// that it will generate right URL.
//			return new DefaultStreamedContent();
//		} else {
//			// So, browser is requesting the image. Return a real
//			// StreamedContent with the image bytes.
//			String id = context.getExternalContext().getRequestParameterMap().get("id");
//			Arquivo arquivo = arquivoDAO.obterArquivoPorId(Integer.parseInt(id));
//
//			return new DefaultStreamedContent(new ByteArrayInputStream(arquivo.getData()), "image/png");
//		}
//
//	}

	public void remover(Audio audio) {
		System.out.println("Vai remover o audio: " + audio.getNome() + " - " + audio.getId());
		audio= audioDAO.obterAudioPorId(audio.getId());
		audioDAO.remove(audio);

		atualizaListaAudios();
	}

	public StreamedContent download(Audio audio) throws IOException {
		System.out.println("Vai realizar o download do audio: " + audio.getNome() + " - " + audio.getId());
		audio = audioDAO.obterAudioPorId(audio.getId());
		
		// TODO download
		StreamedContent file = new DefaultStreamedContent(new ByteArrayInputStream(audio.getData()), null,
				audio.getNome());

		// return
		return file;

	}

}
