package br.com.dropper.web.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.dropper.web.builder.ArquivoBuilder;
import br.com.dropper.web.model.Arquivo;
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.service.ArquivoService;
import br.com.dropper.web.transaction.Transacional;

@Named
@SessionScoped
public class ArquivoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext context;

	private List<Arquivo> arquivos;

	private UploadedFile file;

	@Inject
	private ArquivoService arquivoService;

	@PostConstruct
	public void init() {
		atualizaListaArquivo();
	}

	private void atualizaListaArquivo() {
		this.arquivos = arquivoService.obterArquivosPorUsuario(getUsuarioLogado());
	}

	private Usuario getUsuarioLogado() {
		Usuario usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		return usuario;
	}

	@Transacional
	public void handleFileUpload(FileUploadEvent event) throws IOException {

		this.file = event.getFile();

		ArquivoBuilder builder = new ArquivoBuilder();
		builder.setNome(file.getFileName()).setTamanho(file.getSize()).setDataInclusao(null)
				.setData(file.getInputstream()).setUsuario(getUsuarioLogado());

		Arquivo arquivo = builder.construct();
		arquivoService.gravarArquivo(arquivo);
	
		FacesMessage message = new FacesMessage("Arquivo ", event.getFile().getFileName() + " cadastrado com sucesso!");
		context.addMessage(null, message);
	
		atualizaListaArquivo();
	}

	@Transacional
	public void remover(Arquivo arquivo) {
		System.out.println("Vai remover o arquivo: " + arquivo.getNome() + " - " + arquivo.getId());
		arquivo = arquivoService.buscarArquivoPorId(arquivo.getId());
		arquivoService.removerArquivo(arquivo);

		atualizaListaArquivo();
	}

	@Transacional
	public StreamedContent download(Arquivo arquivo) throws IOException {
		System.out.println("Vai realizar o download da imagem: " + arquivo.getNome() + " - " + arquivo.getId());
		arquivo = arquivoService.buscarArquivoPorId(arquivo.getId());
		
		// TODO download
		StreamedContent file = new DefaultStreamedContent(new ByteArrayInputStream(arquivo.getData()), null,
				arquivo.getNome());

		return file;

	}

	// Setters & Getters
	public List<Arquivo> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<Arquivo> arquivos) {
		this.arquivos = arquivos;
	}

}
