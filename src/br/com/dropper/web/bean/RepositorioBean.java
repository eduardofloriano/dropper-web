package br.com.dropper.web.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import br.com.dropper.web.dao.ArquivoDAO;
import br.com.dropper.web.dao.AudioDAO;
import br.com.dropper.web.dao.ImagemDAO;
import br.com.dropper.web.dao.RepositorioDAO;
import br.com.dropper.web.dao.VideoDAO;
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
	private BarChartModel repositorioBarModel;

	@PostConstruct
	public void init() {
		createPieModel();
		createBarModel();
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

	public BarChartModel getRepositorioBarModel() {
		return repositorioBarModel;
	}

	public void setRepositorioBarModel(BarChartModel repositorioBarModel) {
		this.repositorioBarModel = repositorioBarModel;
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

		if (getUsuarioLogado() != null) {
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

	private void createBarModel() {
		
		repositorioBarModel = new BarChartModel();

		if (getUsuarioLogado() != null) {
			
			
			ChartSeries imagens = new ChartSeries();
			imagens.setLabel("Imagens");
			imagens.set("Imagens", new ImagemDAO(em).obterImagensPorUsuario(usuarioLogado).size());

			ChartSeries arquivos = new ChartSeries();
			arquivos.setLabel("Arquivos");
			arquivos.set("Arquivos", new ArquivoDAO(em).obterArquivosPorUsuario(usuarioLogado).size());

			ChartSeries videos = new ChartSeries();
			videos.setLabel("Videos");
			videos.set("Videos", new VideoDAO(em).obterVideosPorUsuario(usuarioLogado).size());

			ChartSeries audios = new ChartSeries();
			audios.setLabel("Audios");
			audios.set("Audios", new AudioDAO(em).obterAudiosPorUsuario(usuarioLogado).size());

			repositorioBarModel.addSeries(imagens);
			repositorioBarModel.addSeries(arquivos);
			repositorioBarModel.addSeries(videos);
			repositorioBarModel.addSeries(audios);
			
		
			repositorioBarModel.setTitle("Quantidade de Arquivos");
			repositorioBarModel.setLegendPosition("ne");

			Axis xAxis = repositorioBarModel.getAxis(AxisType.X);
//			xAxis.setLabel("Arquivos Multimídia");

			Axis yAxis = repositorioBarModel.getAxis(AxisType.Y);
//			yAxis.setLabel("Quantidade de Arquivos");
			yAxis.setMin(0);
			//yAxis.setMax(200);
			
		}
		
		
	}

}
