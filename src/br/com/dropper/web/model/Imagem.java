package br.com.dropper.web.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
	@NamedQuery(name = "obterImagemPorNome", query = "select i from Imagem i where i.nome = :pNome"),
	@NamedQuery(name = "obterImagensPorUsuario", query = "select i from Imagem i where i.usuario = :pUsuario")
})
@Entity
@SequenceGenerator(name = "SEQ_IMAGEM", sequenceName = "SEQ_IMAGEM", initialValue = 1, allocationSize = 1)
public class Imagem {

	public static final String OBTER_IMAGEM_POR_NOME = "obterImagemPorNome";
	public static final String OBTER_IMAGENS_POR_USUARIO = "obterImagensPorUsuario";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_IMAGEM")
	private Integer id;
	
	private String nome;
	
	private Long tamanho;
		
	@Temporal(TemporalType.DATE)
	@Column(name = "dataInclusao", insertable=false)
	private Date dataInclusao;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dataInclusao", insertable=false, updatable=false)
	private Date dataAlteracao;

	@Lob
	@Column(length=200000) //2MB
	private byte[] data;
	
	@ManyToOne
	private Usuario usuario;
	
	//Getters e Setters
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Long getTamanho() {
		return tamanho;
	}

	public void setTamanho(long tamanho) {
		this.tamanho = tamanho;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
	
}
