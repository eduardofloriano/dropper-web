package br.com.dropper.web.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.dropper.web.dao.ImagemDAO;
import br.com.dropper.web.model.Imagem;
import br.com.dropper.web.model.Usuario;

public class ImagemService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ImagemDAO imagemDAO;

	
	public Imagem buscarImagemPorId(Integer id) {
		return imagemDAO.findById(id);
	}

	public void gravarImagem(Imagem t) {
		imagemDAO.persist(t);
	}

	public void removerImagem(Imagem t) {
		imagemDAO.remove(t);
	}

	public void alterarImagem(Imagem t) {
		imagemDAO.merge(t);
	}

	public Imagem obterImagemPorNome(Imagem imagem) {
		return imagemDAO.obterImagemPorNome(imagem);
	}

	public List<Imagem> obterImagensPorUsuario(Usuario usuario) {
		return imagemDAO.obterImagensPorUsuario(usuario);
	}
	

}
