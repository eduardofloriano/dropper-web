package br.com.dropper.web.dao;

import javax.persistence.EntityManager;

public class DAO<T> {

	protected EntityManager em;
	
	public void persist(T t){
		em.getTransaction().begin();
		em.persist(t);
		em.getTransaction().commit();
	}
	
	public void remove(T t){
		em.getTransaction().begin();
		em.remove(t);
		em.getTransaction().commit();
	}
	
	public void merge(T t){
		em.getTransaction().begin();
		em.merge(t);
		em.getTransaction().commit();
		
	}
	
}
