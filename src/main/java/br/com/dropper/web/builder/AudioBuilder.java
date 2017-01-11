package br.com.dropper.web.builder;

import java.io.InputStream;
import java.util.Date;

import org.apache.commons.io.IOUtils;

import br.com.dropper.web.model.Audio;
import br.com.dropper.web.model.Usuario;

public class AudioBuilder implements ArquivoMultimidiaBuilder<Audio> {

	private String nome;
	private Date dataInclusao;
	private InputStream data;
	private long tamanho;
	private Usuario usuario;
	
	public AudioBuilder(){
		
	}
	
	public AudioBuilder(String nome, Date dataInclusao, InputStream data) {
		super();
		this.nome = nome;
		this.dataInclusao = dataInclusao;
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public AudioBuilder setNome(String nome) {
		this.nome = nome;
		return this;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public AudioBuilder setDataInclusao(Date dataInclusao) {
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

	public AudioBuilder setData(InputStream data) {
		this.data = data;
		return this;
	}
		
	public Long getTamanho() {
		return tamanho;
	}

	public AudioBuilder setTamanho(Long tamanho) {
		this.tamanho = tamanho;
		return this;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public AudioBuilder setUsuario(Usuario usuario) {
		this.usuario = usuario;
		return this;
	}

	@Override	
	public Audio construct(){
		Audio audio = new Audio();
		audio.setNome(nome);
		audio.setTamanho(tamanho);
		audio.setDataInclusao(dataInclusao);
		audio.setUsuario(usuario);
		
		try {
			audio.setData(IOUtils.toByteArray(data));
		} catch (Exception e) {
			System.out.println("Nao foi possivel converter o InputStream da imagem para byteArray!");
			e.printStackTrace();
		}
		
		return audio;
	}

}
