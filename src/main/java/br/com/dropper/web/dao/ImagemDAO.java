package br.com.dropper.web.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.dropper.web.model.Imagem;
import br.com.dropper.web.model.Usuario;

public class ImagemDAO extends DAO<Imagem> {

	public ImagemDAO(EntityManager em) {
		this.em = em;
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
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public Imagem obterImagemPorId(Integer id){
		return em.find(Imagem.class, id);
	}

}
