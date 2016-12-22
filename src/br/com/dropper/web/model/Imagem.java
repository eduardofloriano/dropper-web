package br.com.dropper.web.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQuery(name = "obterImagemPorNome", query = "select i from Imagem i where i.nome = :pNome")
@Entity
@SequenceGenerator(name = "SEQ_IMAGEM", sequenceName = "SEQ_IMAGEM", initialValue = 1, allocationSize = 1)
public class Imagem {

	public static final String OBTER_IMAGEM_POR_NOME = "obterImagemPorNome";	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_IMAGEM")
	private Integer id;
	
	private String nome;
		
	@Temporal(TemporalType.DATE)
	@Column(name = "dataInclusao", insertable=false)
	private Date dataInclusao;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dataInclusao", insertable=false, updatable=false)
	private Date dataAlteracao;

	@Lob
	@Column(length=100000)
	private byte[] data;
	
	
	private Long tamanho;
	
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
	
	
	
	
}
