package br.com.dropper.web.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.model.Video;

public class VideoDAO extends DAO<Video> {

	public VideoDAO(EntityManager em) {
		this.em = em;
	}

	public Video obterVideoPorNome(Video video) {
		TypedQuery<Video> query = em.createNamedQuery(Video.OBTER_VIDEO_POR_NOME, Video.class);
		query.setParameter("pNome", video.getNome());
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Video> obterVideosPorUsuario(Usuario usuario) {
		TypedQuery<Video> query = em.createNamedQuery(Video.OBTER_VIDEOS_POR_USUARIO, Video.class);
		query.setParameter("pUsuario", usuario);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public Video obterVideoPorId(Integer id){
		return em.find(Video.class, id);
	}

}
