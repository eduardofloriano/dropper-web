package br.com.dropper.web.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.dropper.web.model.Usuario;

public class UsuarioDAO extends DAO<Usuario> {

	
	public UsuarioDAO(EntityManager em){
		this.em = em;
	}
	
	
	public Usuario obterUsuarioPorEmail( Usuario usuarioLogin ){
		
		Usuario usuario = null;
		TypedQuery<Usuario> query = em.createNamedQuery(Usuario.OBTER_USUARIO_POR_EMAIL, Usuario.class);
		query.setParameter("pEmail", usuarioLogin.getEmail());
		query.setParameter("pSenha", usuarioLogin.getSenha());
		
		try {
			usuario = query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return usuario;
	}
	

	
}
