package controller;

import bean.Filme;
import bean.Sala;
import bean.Secao;
import dao.SecaoDAO;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import repositorio.RepositorioFilme;
import repositorio.RepositorioSala;
import repositorio.RepositorioSecao;
import util.DateUtil;

public class ListaSecoesController implements Initializable {

    @FXML
    private TableView<Secao> tabelaSecao;

    @FXML
    private TableColumn<Secao, Integer> colunaSecaoCodigo;

    @FXML
    private TableColumn<Secao, Integer> colunaSalaNumero;

    @FXML
    private TableColumn<Secao, String> colunaFilmeNome;

    @FXML
    private TableColumn<Secao, String> colunaSecaoHorario;
    
    @FXML
    private TableColumn<Secao, Integer> colunaSecaoLugaresLivres;

    @FXML
    private Label labelSecaoCodigo;

    @FXML
    private Label labelSalaNumero;

    @FXML
    private Label labelFilmeNome;

    @FXML
    private Label labelSecaoHorario;

    @FXML
    private Button botaoSecaoInserir;

    @FXML
    private Button botaoSecaoApagar;

    @FXML
    private Button botaoSecaoAlterar;

    private RepositorioSecao repositorioSecao = new RepositorioSecao();

    private ObservableList<Secao> observableListaSecao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTabela();
        tabelaSecao.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarItemTabela(newValue));
    }

    public void selecionarItemTabela(Secao secao) {
        if (secao != null) {
            labelSecaoCodigo.setText(String.valueOf(secao.getCodigo()));
            labelSalaNumero.setText(String.valueOf(secao.getSala().getNumero()));
            labelFilmeNome.setText(secao.getFilme().getNome());
            labelSecaoHorario.setText(DateUtil.HourToString(secao.getHorario()));
        } else {
            labelSecaoCodigo.setText("");
            labelSalaNumero.setText("");
            labelFilmeNome.setText("");
            labelSecaoHorario.setText("");
        }
    }

    private void carregarTabela() {
        try {
            repositorioSecao.setLista(new SecaoDAO().buscarTodos().getLista());
            colunaSecaoCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
            colunaSalaNumero.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Secao, Integer>, ObservableValue<Integer>>() {
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Secao, Integer> cell) {
                    final Secao secao = cell.getValue();
                    final SimpleObjectProperty<Integer> simpleObject = new SimpleObjectProperty(secao.getSala().getNumero());
                    return simpleObject;
                }
            });
            colunaFilmeNome.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Secao, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Secao, String> cell) {
                    final Secao secao = cell.getValue();
                    final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty(secao.getFilme().getNome());
                    return simpleObject;
                }
            });
            colunaSecaoHorario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Secao, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Secao, String> cell) {
                    final Secao secao = cell.getValue();
                    final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty(DateUtil.HourToString(secao.getHorario()));
                    return simpleObject;
                }
            });
            colunaSecaoLugaresLivres.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Secao, Integer>, ObservableValue<Integer>>() {
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Secao, Integer> cell) {
                    try {
                        final Secao secao = cell.getValue();
                        final SimpleObjectProperty<Integer> simpleObject = new SimpleObjectProperty(new SecaoDAO().buscarLugaresPorCodigo(secao.getCodigo()));
                        return simpleObject;
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ListaSecoesController.class.getName()).log(Level.SEVERE, null, ex);
                        final SimpleObjectProperty<Integer> simpleObject = new SimpleObjectProperty(0);
                        return simpleObject;
                    }
                }
            });
            observableListaSecao = FXCollections.observableArrayList(repositorioSecao.getLista());
            tabelaSecao.setItems(observableListaSecao);
        } catch (Exception e) {
            Logger.getLogger(ListaSecoesController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    public void acaoBotaoApagar() throws IOException, ClassNotFoundException {
        Secao secao = tabelaSecao.getSelectionModel().getSelectedItem();
        if (secao != null) {
            SecaoDAO secaoDAO = new SecaoDAO();
            secaoDAO.setSecao(secao);
            secaoDAO.excluir();
            carregarTabela();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Escolha uma seção na grid");
            alert.show();
        }
    }

    @FXML
    public void acaoBotaoInserir() throws IOException, ClassNotFoundException {
        Secao secao = new Secao(new Sala(0, 0), new Filme("", "", "", ""), new Date(), 0);
        boolean botaoConfirmarClicado = abrirJanelaCadastroSecaoDialog(secao);
        if (botaoConfirmarClicado) {
            SecaoDAO secaDAO = new SecaoDAO();
            secaDAO.setSecao(secao);
            secaDAO.salvar();
            carregarTabela();
        }
    }

    @FXML
    public void acaoBotaoAlterar() throws IOException, ClassNotFoundException {
        Secao secao = tabelaSecao.getSelectionModel().getSelectedItem();
        if (secao != null) {
            boolean botaoConfirmarClicado = abrirJanelaCadastroSecaoDialog(secao);
            if (botaoConfirmarClicado) {
                SecaoDAO secaoDAO = new SecaoDAO();
                secaoDAO.setSecao(secao);
                secaoDAO.atualizar();
                carregarTabela();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Escolha uma seçao na grid");
            alert.show();
        }
    }

    public boolean abrirJanelaCadastroSecaoDialog(Secao secao) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CadastrarSecaoController.class.getResource("/view/CadastrarSecao.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Seções");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        CadastrarSecaoController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setSecao(secao);

        dialogStage.showAndWait();

        return controller.isBotaoConfirmarClicado();

    }
}
