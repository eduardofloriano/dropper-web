package br.com.dropper.web.builder;

import java.io.InputStream;
import java.util.Date;

import org.apache.commons.io.IOUtils;

import br.com.dropper.web.model.Arquivo;
import br.com.dropper.web.model.Usuario;

public class ArquivoBuilder implements ArquivoMultimidiaBuilder<Arquivo> {

	private String nome;
	private Date dataInclusao;
	private InputStream data;
	private long tamanho;
	private Usuario usuario;
	
	public ArquivoBuilder(){
		
	}
	
	public ArquivoBuilder(String nome, Date dataInclusao, InputStream data) {
		super();
		this.nome = nome;
		this.dataInclusao = dataInclusao;
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public ArquivoBuilder setNome(String nome) {
		this.nome = nome;
		return this;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public ArquivoBuilder setDataInclusao(Date dataInclusao) {
		if(dataInclusao == null){
			this.dataInclusao = new Date();
		}else{
			this.dataInclusao = dataInclusao;
		}
		
		return this;
	}

	public InputStream getData() {
		return data;
	}

	public ArquivoBuilder setData(InputStream data) {
		this.data = data;
		return this;
	}
		
	public Long getTamanho() {
		return tamanho;
	}

	public ArquivoBuilder setTamanho(Long tamanho) {
		this.tamanho = tamanho;
		return this;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public ArquivoBuilder setUsuario(Usuario usuario) {
		this.usuario = usuario;
		return this;
	}

	@Override	
	public Arquivo construct(){
		Arquivo arquivo = new Arquivo();
		arquivo.setNome(nome);
		arquivo.setTamanho(tamanho);
		arquivo.setDataInclusao(dataInclusao);
		arquivo.setUsuario(usuario);
		
		try {
			arquivo.setData(IOUtils.toByteArray(data));
		} catch (Exception e) {
			System.out.println("Nao foi possivel converter o InputStream da imagem para byteArray!");
			e.printStackTrace();
		}
		
		return arquivo;
	}

}
