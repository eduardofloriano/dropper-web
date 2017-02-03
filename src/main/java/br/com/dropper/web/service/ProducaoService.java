package br.com.dropper.web.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.dropper.web.dao.UsuarioDAO;

public class ProducaoService implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final int NUMERO_MAXIMO_USUARIOS = 10;
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	public boolean isLimiteCadastroUsuario(){
		if (usuarioDAO.obterTodosUsuarios().size() > NUMERO_MAXIMO_USUARIOS) {
			System.out.println("Atingiu o limite de usuarios cadastrados");
			return true;
		}else{
			return false;
		}
	}
	
	
	
	
}
