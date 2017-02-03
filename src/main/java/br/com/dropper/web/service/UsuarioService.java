package br.com.dropper.web.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.dropper.web.dao.UsuarioDAO;
import br.com.dropper.web.model.Usuario;

public class UsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioDAO usuarioDAO;
	
	
	public void gravarUsuario(Usuario usuario){
		usuarioDAO.persist(usuario);
	}
	
	public Usuario buscarUsuarioManaged(Usuario usuario){
		return usuarioDAO.findById(usuario.getId());
	}
	
	public Usuario buscarUsuarioManagedPorId(Integer id){
		return usuarioDAO.findById(id);
	}
	
	public void removerUsuarioManaged(Usuario usuario){
		usuarioDAO.remove(usuario);		
	}
	
	public void alterarUsuarioManaged(Usuario usuario){
		usuarioDAO.merge(usuario);		
	}
	
	public Usuario obterUsuarioPorEmail(Usuario usuarioLogin) {
		return usuarioDAO.obterUsuarioPorEmail(usuarioLogin);
	}

	public List<Usuario> obterTodosUsuarios() {
		return usuarioDAO.obterTodosUsuarios();
	}
	
	public List<Usuario> obterListaUsuarios(int limite){
		return usuarioDAO.obterListaUsuarios(limite);
	}

}
