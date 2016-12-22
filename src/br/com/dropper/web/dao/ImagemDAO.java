package br.com.dropper.web.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.dropper.web.model.Imagem;

public class ImagemDAO extends DAO<Imagem> {

	
	public ImagemDAO(EntityManager em){
		this.em = em;
	}
	
	
	public Imagem obterImagemPorNome( Imagem imagem ){		

		TypedQuery<Imagem> query = em.createNamedQuery(Imagem.OBTER_IMAGEM_POR_NOME, Imagem.class);
		query.setParameter("pNome", imagem.getNome());
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}		
	}
	

	
}
