package br.com.dropper.web.bean;

import java.util.List;

import br.com.dropper.web.builder.ArquivoMultimidiaBuilder;
import br.com.dropper.web.dao.DAO;

public abstract class ArquivoMultimidiaBean<T> {

	List<T> listaArquivos;
	
	public abstract void setBuilder(ArquivoMultimidiaBuilder<T> builder);
	public abstract void setDAO(DAO<T> dao);
	
	
}
