package br.com.dropper.web.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.dropper.web.builder.ArquivoBuilder;
import br.com.dropper.web.dao.ArquivoDAO;
import br.com.dropper.web.model.Arquivo;
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.util.JpaUtil;

@ManagedBean
@ApplicationScoped
public class ArquivoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -105011233399925138L;
	private UploadedFile file;
	private EntityManager em = new JpaUtil().getEntityManager();
	private ArquivoDAO arquivoDAO = new ArquivoDAO(em);

	private List<Arquivo> arquivos = arquivoDAO.obterArquivosPorUsuario(getUsuarioLogado());

	public void handleFileUpload(FileUploadEvent event) throws IOException {

		this.file = event.getFile();

		ArquivoBuilder builder = new ArquivoBuilder();
		builder.setNome(file.getFileName()).setTamanho(file.getSize()).setDataInclusao(null)
				.setData(file.getInputstream()).setUsuario(getUsuarioLogado());

		Arquivo arquivo = builder.construct();
		arquivoDAO.persist(arquivo);

		FacesMessage message = new FacesMessage("Arquivo ", event.getFile().getFileName() + " cadastrado com sucesso!");
		FacesContext.getCurrentInstance().addMessage(null, message);

		atualizaListaArquivo();
	}

	private void atualizaListaArquivo() {
		this.arquivos = arquivoDAO.obterArquivosPorUsuario(getUsuarioLogado());
	}

	private Usuario getUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		return usuario;
	}

	// Setters & Getters

	public List<Arquivo> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<Arquivo> arquivos) {
		this.arquivos = arquivos;
	}
	
	
//	public StreamedContent getArquivo() throws Exception {
//
//		FacesContext context = FacesContext.getCurrentInstance();
//		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
//			// So, we're rendering the view. Return a stub StreamedContent so
//			// that it will generate right URL.
//			return new DefaultStreamedContent();
//		} else {
//			// So, browser is requesting the image. Return a real
//			// StreamedContent with the image bytes.
//			String id = context.getExternalContext().getRequestParameterMap().get("id");
//			Arquivo arquivo = arquivoDAO.obterArquivoPorId(Integer.parseInt(id));
//
//			return new DefaultStreamedContent(new ByteArrayInputStream(arquivo.getData()), "image/png");
//		}
//
//	}

	public void remover(Arquivo arquivo) {
		System.out.println("Vai remover o arquivo: " + arquivo.getNome() + " - " + arquivo.getId());
		arquivo = arquivoDAO.obterArquivoPorId(arquivo.getId());
		arquivoDAO.remove(arquivo);

		atualizaListaArquivo();
	}

	public StreamedContent download(Arquivo arquivo) throws IOException {
		System.out.println("Vai realizar o download da imagem: " + arquivo.getNome() + " - " + arquivo.getId());
		arquivo = arquivoDAO.obterArquivoPorId(arquivo.getId());
		
		// TODO download
		StreamedContent file = new DefaultStreamedContent(new ByteArrayInputStream(arquivo.getData()), null,
				arquivo.getNome());

		// return
		return file;

	}

}
