package br.com.dropper.web.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.dropper.web.model.Arquivo;
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.transaction.Transacional;

public class ArquivoDAO implements Serializable{

	private static final long serialVersionUID = 1L;

	private DAO<Arquivo> dao;
	
	@Inject
	private EntityManager em;

	@PostConstruct
	public void init() {
		this.dao = new DAO<Arquivo>(Arquivo.class, em);
	}

	public Arquivo findById(Integer id) {
		return dao.findById(id);
	}

	public void persist(Arquivo t) {
		dao.persist(t);
	}

	public void remove(Arquivo t) {
		dao.remove(t);
	}

	public void merge(Arquivo t) {
		dao.merge(t);
	}

	@Transacional
	public Arquivo obterArquivoPorNome(Arquivo arquivo) {
		TypedQuery<Arquivo> query = em.createNamedQuery(Arquivo.OBTER_ARQUIVO_POR_NOME, Arquivo.class);
		query.setParameter("pNome", arquivo.getNome());
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transacional
	public List<Arquivo> obterArquivosPorUsuario(Usuario usuario) {
		TypedQuery<Arquivo> query = em.createNamedQuery(Arquivo.OBTER_ARQUIVOS_POR_USUARIO, Arquivo.class);
		query.setParameter("pUsuario", usuario);
		try {
			List<Arquivo> result = query.getResultList();
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}

}
