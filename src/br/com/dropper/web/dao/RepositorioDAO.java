package br.com.dropper.web.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.dropper.web.model.Repositorio;
import br.com.dropper.web.model.Usuario;

public class RepositorioDAO extends DAO<Repositorio> {

	public RepositorioDAO(EntityManager em) {
		this.em = em;
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
