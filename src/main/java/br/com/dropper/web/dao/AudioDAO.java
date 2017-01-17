package br.com.dropper.web.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.dropper.web.model.Audio;
import br.com.dropper.web.model.Usuario;

public class AudioDAO extends DAO<Audio> {

	public AudioDAO(EntityManager em) {
		this.em = em;
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
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public Audio obterAudioPorId(Integer id){
		return em.find(Audio.class, id);
	}

}
