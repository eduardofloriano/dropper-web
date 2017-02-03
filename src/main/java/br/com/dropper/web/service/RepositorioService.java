package br.com.dropper.web.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.dropper.web.dao.RepositorioDAO;
import br.com.dropper.web.model.Repositorio;
import br.com.dropper.web.model.Usuario;

public class RepositorioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private RepositorioDAO repositorioDAO;

	public Long obterEspacoDisponivel(Usuario usuarioLogado) {
		try {
			Long espacoTotal = usuarioLogado.getRepositorio().getEspacoTotal();
			Long espacoOcupado = repositorioDAO.obterEspacoOcupadoImagemPorUsuario(usuarioLogado)
					+ repositorioDAO.obterEspacoOcupadoArquivoPorUsuario(usuarioLogado)
					+ repositorioDAO.obterEspacoOcupadoVideoPorUsuario(usuarioLogado);

			return (espacoOcupado * 100) / espacoTotal;
		} catch (Exception e) {
			System.out.println("Ocorreu um erro ao executar a operação obterEspacoDisponivel.");
			e.printStackTrace();
			return 1L;
		}
	}//end obterEspacoDisponivel
	
	public Repositorio obterRepositorioPorUsuario(Usuario usuario) {
		return repositorioDAO.obterRepositorioPorUsuario(usuario);
	}

	public Long obterEspacoOcupadoPorUsuario(Usuario usuario) {
		return repositorioDAO.obterEspacoOcupadoPorUsuario(usuario);
	}

	public Long obterEspacoOcupadoImagemPorUsuario(Usuario usuario) {
		return repositorioDAO.obterEspacoOcupadoImagemPorUsuario(usuario);
	}

	public Long obterEspacoOcupadoArquivoPorUsuario(Usuario usuario) {
		return repositorioDAO.obterEspacoOcupadoArquivoPorUsuario(usuario);
	}

	public Long obterEspacoOcupadoVideoPorUsuario(Usuario usuario) {
		return repositorioDAO.obterEspacoOcupadoVideoPorUsuario(usuario);
	}
	
	public Long obterEspacoOcupadoAudioPorUsuario(Usuario usuario) {
		return repositorioDAO.obterEspacoOcupadoAudioPorUsuario(usuario);

	}

}
