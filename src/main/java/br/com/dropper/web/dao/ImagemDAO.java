package br.com.dropper.web.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.dropper.web.model.Imagem;
import br.com.dropper.web.model.Usuario;

public class ImagemDAO implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	private DAO<Imagem> dao;

	@PostConstruct
	public void init() {
		this.dao = new DAO<Imagem>(Imagem.class, em);
	}

	public Imagem findById(Integer id) {
		return dao.findById(id);
	}

	public void persist(Imagem t) {
		dao.persist(t);
	}

	public void remove(Imagem t) {
		dao.remove(t);
	}

	public void merge(Imagem t) {
		dao.merge(t);
	}

	public Imagem obterImagemPorNome(Imagem imagem) {
		TypedQuery<Imagem> query = em.createNamedQuery(Imagem.OBTER_IMAGEM_POR_NOME, Imagem.class);
		query.setParameter("pNome", imagem.getNome());
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Imagem> obterImagensPorUsuario(Usuario usuario) {
		TypedQuery<Imagem> query = em.createNamedQuery(Imagem.OBTER_IMAGENS_POR_USUARIO, Imagem.class);
		query.setParameter("pUsuario", usuario);
		try {
			em.getTransaction().begin();
			List<Imagem> result = query.getResultList();
			em.getTransaction().commit();
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}

}
