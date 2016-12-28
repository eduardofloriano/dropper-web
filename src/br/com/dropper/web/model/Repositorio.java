package br.com.dropper.web.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

//
@NamedQueries({
	@NamedQuery(name = "obterRepositorioPorUsuario", query = "select u from Usuario u where u.email = :pEmail and u.senha = :pSenha"),
	//@NamedQuery(name = "obterEspacoLivrePorUsuario", query = "select r.espacoTotal - SUM(select i.tamanho from imagem where i.usuario = r.usuario) from repositorio r where r.usuario = :pUsuario")
	@NamedQuery(name = "obterEspacoLivrePorUsuario", query = ""
			+ " select sum(i.tamanho)"
			+ " from Imagem i"		
			+ " where i.usuario = :pUsuario")
})
@Entity
@SequenceGenerator(name = "SEQ_REPOSITORIO", sequenceName = "SEQ_REPOSITORIO", initialValue = 1, allocationSize = 1)
public class Repositorio {

	public static final String OBTER_REPOSITORIO_POR_USUARIO = "obterRepositorioPorUsuario";
	public static final String OBTER_ESPACO_LIVRE_POR_USUARIO = "obterEspacoLivrePorUsuario";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REPOSITORIO")
	private Integer id;

	@OneToOne
	private Usuario usuario;
	
	//2GB
	//private Long espacoTotal = 2000000000L;
	//20MB
	private Long espacoTotal = 20000000L;

	
	public Repositorio(Usuario usuario){
		this.usuario = usuario;
	}
	
	public Repositorio(){
		
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Long getEspacoTotal() {
		return espacoTotal;
	}

	public void setEspacoTotal(Long espacoTotal) {
		this.espacoTotal = espacoTotal;
	}
	
	
	
}
