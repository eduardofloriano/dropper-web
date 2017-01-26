package br.com.dropper.web.factory;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class JpaFactory {
	
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("DropperDS");
	
	@Produces
	@SessionScoped
	public EntityManager getEntityManager(){
		return emf.createEntityManager();
	}

	public void close(@Disposes EntityManager em){
		em.close();
	}
	
	@PreDestroy
	public void closeEmf(){
		emf.close();
	}
	
}
