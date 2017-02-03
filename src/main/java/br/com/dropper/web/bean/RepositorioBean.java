package br.com.dropper.web.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import br.com.dropper.web.dao.ArquivoDAO;
import br.com.dropper.web.dao.AudioDAO;
import br.com.dropper.web.dao.ImagemDAO;
import br.com.dropper.web.dao.VideoDAO;
import br.com.dropper.web.model.Usuario;
import br.com.dropper.web.service.RepositorioService;
import br.com.dropper.web.transaction.Transacional;

@Named
@SessionScoped
public class RepositorioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext context;

	@Inject
	private Usuario usuario;

	@Inject
	private Usuario usuarioLogado;

	// TODO: Persistencia e Transacao controladas por EJB
	@Inject
	private RepositorioService repositorioService;

	@Inject
	private ImagemDAO imagemDAO;

	@Inject
	private ArquivoDAO arquivoDAO;

	@Inject
	private AudioDAO audioDAO;

	@Inject
	private VideoDAO videoDAO;

	private PieChartModel repositorioPieChartModel;
	private BarChartModel repositorioBarModel;

	@PostConstruct
	public void init() {

		this.repositorioBarModel = new BarChartModel();
		this.repositorioPieChartModel = new PieChartModel();

		preenchePieModel();
		preencheBarModel();
	}

	public Usuario getUsuarioLogado() {
		this.usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		return this.usuarioLogado;
	}

	public Long getEspacoDisponivel() {
		if (getUsuarioLogado() != null) {
			return repositorioService.obterEspacoDisponivel(usuarioLogado);
		}
		return 1L;
	}

	private void preenchePieModel() {
		if (getUsuarioLogado() != null) {
			repositorioPieChartModel.set("Imagens", repositorioService.obterEspacoOcupadoImagemPorUsuario(usuarioLogado));
			repositorioPieChartModel.set("Arquivos", repositorioService.obterEspacoOcupadoArquivoPorUsuario(usuarioLogado));
			repositorioPieChartModel.set("Videos", repositorioService.obterEspacoOcupadoVideoPorUsuario(usuarioLogado));
			repositorioPieChartModel.set("Audios", repositorioService.obterEspacoOcupadoAudioPorUsuario(usuarioLogado));

			repositorioPieChartModel.setTitle("Repositorio");
			repositorioPieChartModel.setLegendPosition("e");
			repositorioPieChartModel.setFill(false);
			repositorioPieChartModel.setShowDataLabels(true);
			repositorioPieChartModel.setDiameter(150);
		}

	}

	@Transacional
	private void preencheBarModel() {
		if (getUsuarioLogado() != null) {
			
			ChartSeries imagens = new ChartSeries();
			imagens.setLabel("Imagens");
			imagens.set("Imagens", imagemDAO.obterImagensPorUsuario(usuarioLogado).size());

			ChartSeries arquivos = new ChartSeries();
			arquivos.setLabel("Arquivos");
			arquivos.set("Arquivos", arquivoDAO.obterArquivosPorUsuario(usuarioLogado).size());

			ChartSeries videos = new ChartSeries();
			videos.setLabel("Videos");
			videos.set("Videos", videoDAO.obterVideosPorUsuario(usuarioLogado).size());

			ChartSeries audios = new ChartSeries();
			audios.setLabel("Audios");
			audios.set("Audios", audioDAO.obterAudiosPorUsuario(usuarioLogado).size());

			repositorioBarModel.addSeries(imagens);
			repositorioBarModel.addSeries(arquivos);
			repositorioBarModel.addSeries(videos);
			repositorioBarModel.addSeries(audios);
			
			repositorioBarModel.setTitle("Quantidade de Arquivos");
			repositorioBarModel.setLegendPosition("ne");

			// Axis xAxis = repositorioBarModel.getAxis(AxisType.X);
//			xAxis.setLabel("Arquivos Multimídia");

			Axis yAxis = repositorioBarModel.getAxis(AxisType.Y);
//			yAxis.setLabel("Quantidade de Arquivos");
			yAxis.setMin(0);
			//yAxis.setMax(200);
			
			
		}
	}

	// Setters & Getters
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
}
