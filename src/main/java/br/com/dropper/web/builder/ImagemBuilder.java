package br.com.dropper.web.builder;

import java.io.InputStream;
import java.util.Date;

import org.apache.commons.io.IOUtils;

import br.com.dropper.web.model.Imagem;
import br.com.dropper.web.model.Usuario;

public class ImagemBuilder implements ArquivoMultimidiaBuilder<Imagem> {

	private String nome;
	private Date dataInclusao;
	private InputStream data;
	private long tamanho;
	private Usuario usuario;
	
	public ImagemBuilder(){
		
	}
	
	public ImagemBuilder(String nome, Date dataInclusao, InputStream data) {
		super();
		this.nome = nome;
		this.dataInclusao = dataInclusao;
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public ImagemBuilder setNome(String nome) {
		this.nome = nome;
		return this;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public ImagemBuilder setDataInclusao(Date dataInclusao) {
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

	public ImagemBuilder setData(InputStream data) {
		this.data = data;
		return this;
	}
		
	public Long getTamanho() {
		return tamanho;
	}

	public ImagemBuilder setTamanho(Long tamanho) {
		this.tamanho = tamanho;
		return this;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public ImagemBuilder setUsuario(Usuario usuario) {
		this.usuario = usuario;
		return this;
	}

	public Imagem construct(){
		Imagem imagem = new Imagem();
		imagem.setNome(nome);
		imagem.setTamanho(tamanho);
		imagem.setDataInclusao(dataInclusao);
		imagem.setUsuario(usuario);
		
		try {
			imagem.setData(IOUtils.toByteArray(data));
		} catch (Exception e) {
			System.out.println("Nao foi possivel converter o InputStream da imagem para byteArray!");
			e.printStackTrace();
		}
		
		return imagem;
	}

}
