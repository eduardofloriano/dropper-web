package br.com.dropper.web.test;

import javax.persistence.EntityManager;

import br.com.dropper.web.dao.UsuarioDAO;
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.util.JpaUtil;

public class AmigoTest {

	public static void main(String[] args) {

		System.out.println("Inciando Teste de Amigo");

		JpaUtil jpaUtil = new JpaUtil();
		EntityManager em = jpaUtil.getEntityManager();		
		UsuarioDAO usuarioDAO = new UsuarioDAO(em);
		
		Usuario usuario1 = em.find(Usuario.class, 3);
		System.out.println("Usuario 1: " + usuario1.getNome());

		Usuario usuario2 = em.find(Usuario.class, 2);
		System.out.println("Usuario 1: " + usuario2.getNome());

		usuario1.getAmigos().add(usuario2);
		System.out.println("Usuario 1 adicionando Usuario 2 como amigo.");

		System.out.println("**************");

		System.out.println("Amigos do Usuario 1: ");
		for (Usuario amigo : usuario1.getAmigos()) {
			System.out.println(amigo.getNome());
		}

		System.out.println("**************");

		System.out.println("Amigos do Usuario 2: ");
		for (Usuario amigo : usuario2.getAmigos()) {
			System.out.println(amigo.getNome());
		}
		
		usuarioDAO.merge(usuario1);
		usuarioDAO.merge(usuario2);
		
		em.close();
		jpaUtil.close();
	}

}
