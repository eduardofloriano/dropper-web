package br.com.dropper.web.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@NamedQuery(name = "obterUsuarioPorEmail", query = "select u from Usuario u where u.email = :pEmail and u.senha = :pSenha")
@Entity
@SequenceGenerator(name = "SEQ_USUARIO", sequenceName = "SEQ_USUARIO", initialValue = 1, allocationSize = 1)
public class Usuario {

	public static final String OBTER_USUARIO_POR_EMAIL = "obterUsuarioPorEmail";	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO")
	private Integer id;
	
	private String nome;
	private String sobrenome;
	
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dataInclusao", insertable=false)
	private Date dataInclusao = new Date();
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dataInclusao", insertable=false, updatable=false)
	private Date dataAlteracao;
	
	private Integer telefone;
	private String endereco;	
	private String email;
	private String senha;

	
//	@Transient
	@ManyToMany(cascade=CascadeType.ALL)	
	@JoinTable(name = "amigos", joinColumns = {
			 @JoinColumn(name = "usuario1", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
			 @JoinColumn(name = "usuario2", referencedColumnName = "id", nullable = false)})
	private List<Usuario> amigos = new ArrayList<Usuario>();
	
	@ManyToMany(mappedBy = "amigos")
	private List<Usuario> amigoDe = new ArrayList<>();
	
	@Lob
	@Column(length=500000) //5MB
	private byte[] imagemPerfil;
		
	@OneToOne(cascade=CascadeType.ALL,
			optional=false,
			fetch=FetchType.EAGER,
			orphanRemoval=true)
	private Repositorio repositorio = new Repositorio(this);
	
	@Transient
	private Long espacoDisponivel;
	
	
	@PreUpdate
	@PrePersist
	public void atualizaDataAlteracao(){
		dataAlteracao = new Date();
	}
	
	//Getters e Setters
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Integer getTelefone() {
		return telefone;
	}

	public void setTelefone(Integer telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public byte[] getImagemPerfil() {
		return imagemPerfil;
	}

	public void setImagempergil(byte[] imagemPerfil) {
		this.imagemPerfil = imagemPerfil;
	}

	public Repositorio getRepositorio() {
		return repositorio;
	}

	public void setRepositorio(Repositorio repositorio) {
		this.repositorio = repositorio;
	}

	public Long getEspacoDisponivel() {
		return espacoDisponivel;
	}

	public void setEspacoDisponivel(Long espacoDisponivel) {
		this.espacoDisponivel = espacoDisponivel;
	}

	public void setImagemPerfil(byte[] imagemPerfil) {
		this.imagemPerfil = imagemPerfil;
	}

	public List<Usuario> getAmigos() {
		return amigos;
	}

	public void setAmigos(List<Usuario> amigos) {
		this.amigos = amigos;
	}

	public List<Usuario> getAmigoDe() {
		return amigoDe;
	}

	public void setAmigoDe(List<Usuario> amigoDe) {
		this.amigoDe = amigoDe;
	}
	

}
