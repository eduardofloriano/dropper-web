package br.com.dropper.web.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.dropper.web.model.Audio;
import br.com.dropper.web.model.Usuario;

public class AudioDAO implements Serializable{

	private static final long serialVersionUID = 1L;

	private DAO<Audio> dao;
	
	@Inject
	private EntityManager em;
	
	@PostConstruct
	public void init(){
		this.dao = new DAO<Audio>(Audio.class, em);
	}
	
	public Audio findById(Integer id) {
		return dao.findById(id);
	}

	public void persist(Audio t) {
		dao.persist(t);
	}

	public void remove(Audio t) {
		dao.remove(t);
	}

	public void merge(Audio t) {
		dao.merge(t);
	}

	public Audio obterArquivoPorNome(Audio audio) {
		TypedQuery<Audio> query = em.createNamedQuery(Audio.OBTER_AUDIO_POR_NOME, Audio.class);
		query.setParameter("pNome", audio.getNome());
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Audio> obterAudiosPorUsuario(Usuario usuario) {
		TypedQuery<Audio> query = em.createNamedQuery(Audio.OBTER_AUDIOS_POR_USUARIO, Audio.class);
		query.setParameter("pUsuario", usuario);
		try {
			em.getTransaction().begin();
			List<Audio> result = query.getResultList();
			em.getTransaction().commit();
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}

}
