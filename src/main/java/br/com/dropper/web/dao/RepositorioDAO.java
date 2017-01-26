package br.com.dropper.web.dao;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.dropper.web.model.Repositorio;
import br.com.dropper.web.model.Usuario;

public class RepositorioDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	private DAO<Repositorio> dao;

	@PostConstruct
	public void init(){
		this.dao = new DAO<>(Repositorio.class, em);
	}

	public Repositorio findById(Integer id) {
		return dao.findById(id);
	}

	public void persist(Repositorio t) {
		dao.persist(t);
	}

	public void remove(Repositorio t) {
		dao.remove(t);
	}

	public void merge(Repositorio t) {
		dao.merge(t);
	}


	public Repositorio obterRepositorioPorUsuario(Usuario usuario) {
		TypedQuery<Repositorio> query = em.createNamedQuery(Repositorio.OBTER_REPOSITORIO_POR_USUARIO,
				Repositorio.class);
		query.setParameter("pUsuario", usuario);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	public Long obterEspacoOcupadoPorUsuario(Usuario usuario) {

		TypedQuery<Long> query = em.createNamedQuery(Repositorio.OBTER_ESPACO_LIVRE_POR_USUARIO, Long.class);
		query.setParameter("pUsuario", usuario);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public Long obterEspacoOcupadoImagemPorUsuario(Usuario usuario) {

		TypedQuery<Long> query = em.createNamedQuery(Repositorio.OBTER_ESPACO_IMAGEM_OCUPADO_POR_USUARIO, Long.class);
		query.setParameter("pUsuario", usuario);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public Long obterEspacoOcupadoArquivoPorUsuario(Usuario usuario) {

		TypedQuery<Long> query = em.createNamedQuery(Repositorio.OBTER_ESPACO_ARQUIVO_OCUPADO_POR_USUARIO, Long.class);
		query.setParameter("pUsuario", usuario);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public Long obterEspacoOcupadoVideoPorUsuario(Usuario usuario) {

		TypedQuery<Long> query = em.createNamedQuery(Repositorio.OBTER_ESPACO_VIDEO_OCUPADO_POR_USUARIO, Long.class);
		query.setParameter("pUsuario", usuario);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public Long obterEspacoOcupadoAudioPorUsuario(Usuario usuario) {
		TypedQuery<Long> query = em.createNamedQuery(Repositorio.OBTER_ESPACO_AUDIO_OCUPADO_POR_USUARIO, Long.class);
		query.setParameter("pUsuario", usuario);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
