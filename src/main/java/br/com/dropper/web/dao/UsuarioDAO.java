package br.com.dropper.web.dao;

import java.util.ArrayList;
import java.util.List;

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
			em.getTransaction().begin();
			usuario = query.getSingleResult();
			em.getTransaction().commit();
		} catch (NoResultException e) {
			return null;
		}
		return usuario;
	}
	
	public Usuario obterUsuarioPorId( Integer id) {
		em.getTransaction().begin();
		Usuario result =  em.find(Usuario.class, id);
		em.getTransaction().commit();
		return result;
	}

	public List<Usuario> obterTodosUsuarios(){
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		TypedQuery<Usuario> query = em.createNamedQuery(Usuario.OBTER_TODOS_USUARIOS, Usuario.class);
		
		try {
			em.getTransaction().begin();
			usuarios = query.getResultList();
			em.getTransaction().commit();
		} catch (NoResultException e) {
			return null;
		}
		return usuarios;
	}
	
}
