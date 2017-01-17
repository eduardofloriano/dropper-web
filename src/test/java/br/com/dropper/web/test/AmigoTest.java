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
		
		Usuario usuario1 = em.find(Usuario.class, 8);
		System.out.println("Usuario 1: " + usuario1.getNome());

		Usuario usuario2 = em.find(Usuario.class, 7);
		System.out.println("Usuario 2: " + usuario2.getNome());

//		usuario1.getAmigos().add(usuario2);
//		System.out.println("Usuario 1 adicionando Usuario 2 como amigo.");

		System.out.println("**************");

		System.out.println(usuario1.getNome() + " possui os amigos: ");
		for (Usuario amigo : usuario1.getAmigos()) {
			System.out.println("- " + amigo.getNome());
		}

		System.out.println(usuario1.getNome() + " é amigo de : ");
		for (Usuario amigo : usuario1.getAmigoDe()) {
			System.out.println("- " + amigo.getNome());
		}
		
		System.out.println("**************");

		System.out.println(usuario2.getNome() + " possui os amigos: ");
		for (Usuario amigo : usuario2.getAmigos()) {
			System.out.println("- " + amigo.getNome());
		}

		System.out.println(usuario2.getNome() + " é amigo de : ");
		for (Usuario amigo : usuario2.getAmigoDe()) {
			System.out.println("- " + amigo.getNome());
		}
		
		
//		usuarioDAO.merge(usuario1);
//		usuarioDAO.merge(usuario2);
		
		em.close();
		jpaUtil.close();
	}

}
