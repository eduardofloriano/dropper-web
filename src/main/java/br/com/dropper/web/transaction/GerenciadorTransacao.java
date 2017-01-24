package br.com.dropper.web.transaction;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transacional
@Interceptor
public class GerenciadorTransacao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	@AroundInvoke
	public Object executaTransacao(InvocationContext contexto) throws Exception{
		
		System.out.println("Executando interceptor executaTransacao");
		
		em.getTransaction().begin();
		Object resultado = contexto.proceed();
		em.getTransaction().commit();
		
		System.out.println("Terminando interceptor executaTransacao");
		
		return resultado;
	}

}
