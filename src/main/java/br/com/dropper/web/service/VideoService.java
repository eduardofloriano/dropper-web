package br.com.dropper.web.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.dropper.web.dao.VideoDAO;
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.model.Video;

public class VideoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private VideoDAO videoDAO;

	public Video buscarVideoPorId(Integer id) {
		return videoDAO.findById(id);
	}

	public void gravarVideo(Video t) {
		videoDAO.persist(t);
	}

	public void removerVideo(Video t) {
		videoDAO.remove(t);
	}

	public void alterarVideo(Video t) {
		videoDAO.merge(t);
	}

	public Video obterVideoPorNome(Video video) {
		return videoDAO.obterVideoPorNome(video);
	}

	public List<Video> obterVideosPorUsuario(Usuario usuario) {
		return videoDAO.obterVideosPorUsuario(usuario);
	}
	
	
	
}
