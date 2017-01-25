package br.com.dropper.web.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.transaction.Transacional;

public class UsuarioDAO implements Serializable{

	private static final long serialVersionUID = 1L;

	private DAO<Usuario> dao;
	
	@Inject
	private EntityManager em;
	
	@PostConstruct
	public void init(){
		this.dao = new DAO<Usuario>(Usuario.class, em); 
	}
	
	public Usuario findById(Integer id) {
		return dao.findById(id);
	}
	
	public void persist(Usuario t) {
		dao.persist(t);
	}

	public void remove(Usuario t) {
		dao.remove(t);
	}

	public void merge(Usuario t) {
		dao.merge(t);
	}

	@Transacional
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
	
	@Transacional
	public List<Usuario> obterTodosUsuarios(){
		List<Usuario> usuarios = new ArrayList<Usuario>();
		TypedQuery<Usuario> query = em.createNamedQuery(Usuario.OBTER_TODOS_USUARIOS, Usuario.class);
		
		try {
			usuarios = query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return usuarios;
	}
	
}
