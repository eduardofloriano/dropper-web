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
		
		System.out.println("[DEBUG-TRANSACTION] Executando interceptor executaTransacao");
		

		//SE JA EXISTE TRANSACAO ATIVA, ELA CONTROLARA SEU CICLO DE VIDA
		if(em.getTransaction().isActive()){
			System.out.println("[DEBUG-TRANSACTION] Ja existe uma transacao ativa, Terminando interceptor executaTransacao");
			return contexto.proceed();
		}
		
		em.getTransaction().begin();
		Object resultado = contexto.proceed();
		em.getTransaction().commit();
		
		System.out.println("[DEBUG-TRANSACTION] Terminando interceptor executaTransacao");
		
		return resultado;
	}

}
