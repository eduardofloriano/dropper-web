package br.com.dropper.web.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.model.Video;

public class VideoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAO<Video> dao;
	
	@Inject
	private EntityManager em;
	
	@PostConstruct
	public void init(){
		this.dao = new DAO<Video>(Video.class, em);
	}
	
	public Video findById(Integer id) {
		return dao.findById(id);
	}

	public void persist(Video t) {
		dao.persist(t);
	}

	public void remove(Video t) {
		dao.remove(t);
	}

	public void merge(Video t) {
		dao.merge(t);
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
			em.getTransaction().begin();
			List<Video> result = query.getResultList();
			em.getTransaction().commit();
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
