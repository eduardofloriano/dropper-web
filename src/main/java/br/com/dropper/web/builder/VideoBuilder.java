package br.com.dropper.web.builder;

import java.io.InputStream;
import java.util.Date;

import org.apache.commons.io.IOUtils;

import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.model.Video;

public class VideoBuilder implements ArquivoMultimidiaBuilder<Video> {

	private String nome;
	private Date dataInclusao;
	private InputStream data;
	private long tamanho;
	private Usuario usuario;
	
	public VideoBuilder(){
		
	}
	
	public VideoBuilder(String nome, Date dataInclusao, InputStream data) {
		super();
		this.nome = nome;
		this.dataInclusao = dataInclusao;
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public VideoBuilder setNome(String nome) {
		this.nome = nome;
		return this;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public VideoBuilder setDataInclusao(Date dataInclusao) {
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

	public VideoBuilder setData(InputStream data) {
		this.data = data;
		return this;
	}
		
	public Long getTamanho() {
		return tamanho;
	}

	public VideoBuilder setTamanho(Long tamanho) {
		this.tamanho = tamanho;
		return this;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public VideoBuilder setUsuario(Usuario usuario) {
		this.usuario = usuario;
		return this;
	}

	public Video construct(){
		Video video = new Video();
		video.setNome(nome);
		video.setTamanho(tamanho);
		video.setDataInclusao(dataInclusao);
		video.setUsuario(usuario);
		
		try {
			video.setData(IOUtils.toByteArray(data));
		} catch (Exception e) {
			System.out.println("Nao foi possivel converter o InputStream da Video para byteArray!");
			e.printStackTrace();
		}
		
		return video;
	}

}
