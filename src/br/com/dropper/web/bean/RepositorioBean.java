package br.com.dropper.web.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.primefaces.model.chart.PieChartModel;

import br.com.dropper.web.dao.RepositorioDAO;
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.util.JpaUtil;

@ManagedBean
@ViewScoped
public class RepositorioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6755567510858433234L;
	private EntityManager em = new JpaUtil().getEntityManager();
	RepositorioDAO repositorioDAO = new RepositorioDAO(em);

	private Usuario usuario = new Usuario();
	private Usuario usuarioLogado = new Usuario();
	private PieChartModel repositorioPieChartModel;
	

	@PostConstruct
    public void init() {
        createPieModel();
    }
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public PieChartModel getRepositorioPieChartModel() {
		return repositorioPieChartModel;
	}

	public void setRepositorioPieChartModel(PieChartModel repositorioPieChartModel) {
		this.repositorioPieChartModel = repositorioPieChartModel;
	}

	public Usuario getUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		return this.usuarioLogado;
	}

	
	public Long getEspacoDisponivel() {
		if (getUsuarioLogado() != null) {
			Long espacoTotal = usuarioLogado.getRepositorio().getEspacoTotal();
			
			Long espacoOcupado = repositorioDAO.obterEspacoOcupadoImagemPorUsuario(usuarioLogado)
					+ repositorioDAO.obterEspacoOcupadoArquivoPorUsuario(usuarioLogado)
					+ repositorioDAO.obterEspacoOcupadoVideoPorUsuario(usuarioLogado)
					+ repositorioDAO.obterEspacoOcupadoAudioPorUsuario(usuarioLogado);

			return (espacoOcupado * 100) / espacoTotal;
		}
		return 1L;
	}

	
	private void createPieModel() {
		repositorioPieChartModel = new PieChartModel();        
		
		if(getUsuarioLogado() != null){
			Long espacoTotal = usuarioLogado.getRepositorio().getEspacoTotal();		
			
			repositorioPieChartModel.set("Imagens", repositorioDAO.obterEspacoOcupadoImagemPorUsuario(usuarioLogado));
			repositorioPieChartModel.set("Arquivos", repositorioDAO.obterEspacoOcupadoArquivoPorUsuario(usuarioLogado));
			repositorioPieChartModel.set("Videos", repositorioDAO.obterEspacoOcupadoVideoPorUsuario(usuarioLogado));
			repositorioPieChartModel.set("Audios", repositorioDAO.obterEspacoOcupadoAudioPorUsuario(usuarioLogado));
	         
			repositorioPieChartModel.setTitle("Repositorio");
			repositorioPieChartModel.setLegendPosition("e");
			repositorioPieChartModel.setFill(false);
			repositorioPieChartModel.setShowDataLabels(true);
			repositorioPieChartModel.setDiameter(150);
		}
		
		
    }
	
}
