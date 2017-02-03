package br.com.dropper.web.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.dropper.web.dao.ArquivoDAO;
import br.com.dropper.web.model.Arquivo;
import br.com.dropper.web.model.Usuario;

public class ArquivoService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private ArquivoDAO arquivoDAO;

	public Arquivo buscarArquivoPorId(Integer id) {
		return arquivoDAO.findById(id);
	}

	public void gravarArquivo(Arquivo t) {
		arquivoDAO.persist(t);
	}

	public void removerArquivo(Arquivo t) {
		arquivoDAO.remove(t);
	}

	public void alterarArquivo(Arquivo t) {
		arquivoDAO.merge(t);
	}

	public Arquivo obterArquivoPorNome(Arquivo arquivo) {
		return arquivoDAO.obterArquivoPorNome(arquivo);
	}

	public List<Arquivo> obterArquivosPorUsuario(Usuario usuario) {
		return arquivoDAO.obterArquivosPorUsuario(usuario);
	}
	
	
}
