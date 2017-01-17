package br.com.dropper.web.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.dropper.web.model.Arquivo;
import br.com.dropper.web.model.Usuario;

public class ArquivoDAO extends DAO<Arquivo> {

	public ArquivoDAO(EntityManager em) {
		this.em = em;
	}

	public Arquivo obterArquivoPorNome(Arquivo arquivo) {
		TypedQuery<Arquivo> query = em.createNamedQuery(Arquivo.OBTER_ARQUIVO_POR_NOME, Arquivo.class);
		query.setParameter("pNome", arquivo.getNome());
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Arquivo> obterArquivosPorUsuario(Usuario usuario) {
		TypedQuery<Arquivo> query = em.createNamedQuery(Arquivo.OBTER_ARQUIVOS_POR_USUARIO, Arquivo.class);
		query.setParameter("pUsuario", usuario);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public Arquivo obterArquivoPorId(Integer id){
		return em.find(Arquivo.class, id);
	}

}
