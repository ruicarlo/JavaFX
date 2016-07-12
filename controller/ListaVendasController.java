package controller;

import bean.Filme;
import bean.Sala;
import bean.Secao;
import bean.Venda;
import dao.VendaDAO;
import java.io.IOException;
import java.net.URL;
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
import repositorio.RepositorioVenda;
import util.DateUtil;

public class ListaVendasController implements Initializable {

    @FXML
    private TableView<Venda> tabelaVenda;

    @FXML
    private TableColumn<Venda, Integer> colunaVendaId;

    @FXML
    private TableColumn<Venda, Integer> colunaVendaSecaoCodigo;

    @FXML
    private TableColumn<Venda, Integer> colunaVendaSalaNumero;

    @FXML
    private TableColumn<Venda, String> colunaVendaFilmeNome;

    @FXML
    private TableColumn<Venda, String> colunaVendaSecaoHorario;

    @FXML
    private Label labelVendaId;

    @FXML
    private Label labelVendaSecaoCodigo;

    @FXML
    private Label labelVendaSalaNumero;

    @FXML
    private Label labelVendaFilmeNome;

    @FXML
    private Label labelVendaSecaoHorario;

    @FXML
    private Button botaoVendaInserir;

    @FXML
    private Button botaoVendaApagar;

    private RepositorioVenda repositorioVenda = new RepositorioVenda();

    private ObservableList<Venda> observableListaVenda;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTabela();
        tabelaVenda.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarItemTabela(newValue));
    }

    public void selecionarItemTabela(Venda venda) {
        if (venda != null) {
            labelVendaId.setText(String.valueOf(venda.getIdVenda()));
            labelVendaSecaoCodigo.setText(String.valueOf(venda.getSecao().getCodigo()));
            labelVendaSalaNumero.setText(String.valueOf(venda.getSecao().getSala().getNumero()));
            labelVendaFilmeNome.setText(venda.getSecao().getFilme().getNome());
            labelVendaSecaoHorario.setText(DateUtil.HourToString(venda.getSecao().getHorario()));
        } else {
            labelVendaId.setText("");
            labelVendaSecaoCodigo.setText("");
            labelVendaSalaNumero.setText("");
            labelVendaFilmeNome.setText("");
            labelVendaSecaoHorario.setText("");
        }
    }

    private void carregarTabela() {
        try {
            repositorioVenda.setLista(new VendaDAO().buscarTodos().getLista());
            colunaVendaId.setCellValueFactory(new PropertyValueFactory<>("idVenda"));
            colunaVendaSecaoCodigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Venda, Integer>, ObservableValue<Integer>>() {
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Venda, Integer> cell) {
                    final Venda venda = cell.getValue();
                    final SimpleObjectProperty<Integer> simpleObject = new SimpleObjectProperty(venda.getSecao().getCodigo());
                    return simpleObject;
                }
            });
            colunaVendaSalaNumero.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Venda, Integer>, ObservableValue<Integer>>() {
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Venda, Integer> cell) {
                    final Venda venda = cell.getValue();
                    final SimpleObjectProperty<Integer> simpleObject = new SimpleObjectProperty(venda.getSecao().getSala().getNumero());
                    return simpleObject;
                }
            });
            colunaVendaFilmeNome.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Venda, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Venda, String> cell) {
                    final Venda venda = cell.getValue();
                    final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty(venda.getSecao().getFilme().getNome());
                    return simpleObject;
                }
            });
            colunaVendaSecaoHorario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Venda, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Venda, String> cell) {
                    final Venda venda = cell.getValue();
                    final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty(DateUtil.HourToString(venda.getSecao().getHorario()));
                    return simpleObject;
                }
            });
            observableListaVenda = FXCollections.observableArrayList(repositorioVenda.getLista());
            tabelaVenda.setItems(observableListaVenda);
        } catch (Exception e) {
            Logger.getLogger(ListaSecoesController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    public void acaoBotaoApagar() throws IOException, ClassNotFoundException {
        Venda venda = tabelaVenda.getSelectionModel().getSelectedItem();
        if (venda != null) {
            VendaDAO vendaDAO = new VendaDAO();
            vendaDAO.setVenda(venda);
            vendaDAO.excluir();
            carregarTabela();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Escolha uma venda na grid");
            alert.show();
        }
    }

    @FXML
    public void acaoBotaoInserir() throws IOException, ClassNotFoundException {
        Venda venda = new Venda(new Secao(new Sala(0, 0), new Filme("", "", "", ""), new Date(), 0));
        boolean botaoConfirmarClicado = abrirJanelaCadastroVendaDialog(venda);
        if (botaoConfirmarClicado) {
            VendaDAO vendaDAO = new VendaDAO();
            vendaDAO.setVenda(venda);
            vendaDAO.salvar();
            carregarTabela();
        }
    }

    public boolean abrirJanelaCadastroVendaDialog(Venda venda) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CadastrarVendaController.class.getResource("/view/CadastrarVenda.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Vendas");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        CadastrarVendaController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setVenda(venda);

        dialogStage.showAndWait();

        return controller.isBotaoConfirmarClicado();

    }
}
