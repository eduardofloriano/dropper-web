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
		// @NamedQuery(name = "obterEspacoLivrePorUsuario", query =
		// "select r.espacoTotal - SUM(select i.tamanho from imagem where i.usuario = r.usuario) from repositorio r where r.usuario = :pUsuario")
		@NamedQuery(name = "obterEspacoLivrePorUsuario", query = ""
				+ " select " + " coalesce(sum(i.tamanho),0) "
				+ " from Imagem i " + " where i.usuario = :pUsuario"),
		@NamedQuery(name = "obterEspacoImagemOcupadoPorUsuario", query = ""
				+ " select " + " coalesce(sum(i.tamanho),0) "
				+ " from Imagem i " + " where i.usuario = :pUsuario"),
		@NamedQuery(name = "obterEspacoArquivoOcupadoPorUsuario", query = ""
				+ " select " + " coalesce(sum(a.tamanho),0) "
				+ " from Arquivo a " + " where a.usuario = :pUsuario"),
		@NamedQuery(name = "obterEspacoVideoOcupadoPorUsuario", query = ""
				+ " select " + " coalesce(sum(v.tamanho),0) "
				+ " from Video v " + " where v.usuario = :pUsuario"),
		@NamedQuery(name = "obterEspacoAudioOcupadoPorUsuario", query = ""
				+ " select " + " coalesce(sum(a.tamanho),0) "
				+ " from Audio a " + " where a.usuario = :pUsuario") })
@Entity
@SequenceGenerator(name = "SEQ_REPOSITORIO", sequenceName = "SEQ_REPOSITORIO", initialValue = 1, allocationSize = 1)
public class Repositorio {

	public static final String OBTER_REPOSITORIO_POR_USUARIO = "obterRepositorioPorUsuario";
	public static final String OBTER_ESPACO_LIVRE_POR_USUARIO = "obterEspacoLivrePorUsuario";
	public static final String OBTER_ESPACO_IMAGEM_OCUPADO_POR_USUARIO = "obterEspacoImagemOcupadoPorUsuario";
	public static final String OBTER_ESPACO_ARQUIVO_OCUPADO_POR_USUARIO = "obterEspacoArquivoOcupadoPorUsuario";
	public static final String OBTER_ESPACO_VIDEO_OCUPADO_POR_USUARIO = "obterEspacoVideoOcupadoPorUsuario";
	public static final String OBTER_ESPACO_AUDIO_OCUPADO_POR_USUARIO = "obterEspacoAudioOcupadoPorUsuario";

	// 2GB
	// private Long espacoTotal = 2000000000L;
	// 20MB
	public static final Long ESPACO_TOTAL_INICIAL_PADRAO = 20000000L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REPOSITORIO")
	private Integer id;

	@OneToOne
	private Usuario usuario;

	private Long espacoTotal = ESPACO_TOTAL_INICIAL_PADRAO;

	public Repositorio(Usuario usuario) {
		this.usuario = usuario;
	}

	public Repositorio() {

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
