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
	@NamedQuery(name = "obterVideoPorNome", query = "select v from Video v where v.nome = :pNome"),
	@NamedQuery(name = "obterVideosPorUsuario", query = "select v from Video v where v.usuario = :pUsuario")
})
@Entity
@SequenceGenerator(name = "SEQ_VIDEO", sequenceName = "SEQ_VIDEO", initialValue = 1, allocationSize = 1)
public class Video {

	public static final String OBTER_VIDEO_POR_NOME = "obterVideoPorNome";
	public static final String OBTER_VIDEOS_POR_USUARIO = "obterVideosPorUsuario";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VIDEO")
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
	@Column(length=100000)
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
