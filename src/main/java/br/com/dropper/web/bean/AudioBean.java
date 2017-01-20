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

@Named
@SessionScoped
public class AudioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext context;

	@Inject
	private AudioBuilder builder;

	private UploadedFile file;
	private EntityManager em = new JpaUtil().getEntityManager();
	private AudioDAO audioDAO = new AudioDAO(em);

	private List<Audio> audios;

	@PostConstruct
	public void init() {
		atualizaListaAudios();
	}

	private void atualizaListaAudios() {
		this.audios = audioDAO.obterAudiosPorUsuario(getUsuarioLogado());
	}

	private Usuario getUsuarioLogado() {
		Usuario usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		return usuario;
	}

	public void handleFileUpload(FileUploadEvent event) throws IOException {

		this.file = event.getFile();

		builder.setNome(file.getFileName()).setTamanho(file.getSize()).setDataInclusao(null)
				.setData(file.getInputstream()).setUsuario(getUsuarioLogado());

		Audio audio = builder.construct();
		audioDAO.persist(audio);

		FacesMessage message = new FacesMessage("Audio ", event.getFile().getFileName() + " cadastrado com sucesso!");
		context.addMessage(null, message);

		atualizaListaAudios();
	}

	public void remover(Audio audio) {
		System.out.println("Vai remover o audio: " + audio.getNome() + " - " + audio.getId());
		audio = audioDAO.obterAudioPorId(audio.getId());
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

	// Setters & Getters
	public List<Audio> getAudios() {
		return audios;
	}

	public void setAudios(List<Audio> audios) {
		this.audios = audios;
	}

}
