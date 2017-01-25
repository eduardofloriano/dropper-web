package br.com.dropper.web.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;

public class DAO<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Class<T> classe;
	private EntityManager em;
	
	
	public DAO(Class<T> classe, EntityManager em) {
		this.classe = classe;
		this.em = em;
	}
	
	public T findById(Integer id){
		return em.find(classe, id);
	}
	
	public void persist(T t){
		em.persist(t);
	}
	
	public void remove(T t){
		em.remove(t);
	}
	
	public void merge(T t){
		em.merge(t);
		
	}
	
	
}
