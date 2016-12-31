package br.com.dropper.web.test;

import javax.persistence.EntityManager;

import br.com.dropper.web.dao.RepositorioDAO;
import br.com.dropper.web.dao.UsuarioDAO;
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.util.JpaUtil;

public class QueryTest {
	
	
	public static void main(String[] args) {
		
		EntityManager em = new JpaUtil().getEntityManager();		
		RepositorioDAO repositorioDAO = new RepositorioDAO(em);

		
		Usuario usuario = em.find(Usuario.class, 3);
		
		Long espacoTotal = usuario.getRepositorio().getEspacoTotal();
		Long espacoOcupado = repositorioDAO.obterEspacoOcupadoPorUsuario(usuario);
		long porcentagemLivre = ((espacoTotal - espacoOcupado) * 100) / espacoTotal ;
		
		System.out.println("**************");
		System.out.println("Usuario: " + usuario.getNome());
		System.out.println("Espaco Total: " + espacoTotal);
		System.out.println("Espaco Ocupado: " + espacoOcupado);
		System.out.println("Porcentagem Livre: " + porcentagemLivre);
		System.out.println("**************");
		
		
		
	}

}
