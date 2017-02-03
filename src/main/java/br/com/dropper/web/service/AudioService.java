package br.com.dropper.web.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.dropper.web.dao.AudioDAO;
import br.com.dropper.web.model.Audio;
import br.com.dropper.web.model.Usuario;

public class AudioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AudioDAO audioDAO;
	

	public Audio buscarAudioPorId(Integer id) {
		return audioDAO.findById(id);
	}

	public void gravarAudio(Audio t) {
		audioDAO.persist(t);
	}

	public void removerAudio(Audio t) {
		audioDAO.remove(t);
	}

	public void alterarAudio(Audio t) {
		audioDAO.merge(t);
	}

	public Audio obterArquivoPorNome(Audio audio) {
		return audioDAO.obterArquivoPorNome(audio);
	}

	public List<Audio> obterAudiosPorUsuario(Usuario usuario) {
		return audioDAO.obterAudiosPorUsuario(usuario);
	}
	
	
	
}
