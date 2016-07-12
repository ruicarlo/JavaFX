package controller;

import bean.Secao;
import dao.RelatorioDAO;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import repositorio.RepositorioSecao;
import util.DateUtil;


public class ListaRelatorioVendaPorSecaoController implements Initializable {

    @FXML
    private TableView<Secao> tabelaVenda;

    @FXML
    private TableColumn<Secao, Integer> colunaVendaSecaoCodigo;

    @FXML
    private TableColumn<Secao, Integer> colunaVendaSalaNumero;

    @FXML
    private TableColumn<Secao, String> colunaVendaFilmeNome;

    @FXML
    private TableColumn<Secao, String> colunaVendaSecaoHorario;

    private RepositorioSecao repositorioSecao = new RepositorioSecao();

    private ObservableList<Secao> observableListaSecao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTabela();

    }

    private void carregarTabela() {
        try {
            repositorioSecao.setLista(new RelatorioDAO().relatorioPorSecao().getLista());
            colunaVendaSecaoCodigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Secao, Integer>, ObservableValue<Integer>>() {
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Secao, Integer> cell) {
                    final Secao secao = cell.getValue();
                    final SimpleObjectProperty<Integer> simpleObject = new SimpleObjectProperty(secao.getCodigo());
                    return simpleObject;
                }
            });
            colunaVendaSalaNumero.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Secao, Integer>, ObservableValue<Integer>>() {
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Secao, Integer> cell) {
                    final Secao secao = cell.getValue();
                    final SimpleObjectProperty<Integer> simpleObject = new SimpleObjectProperty(secao.getSala().getNumero());
                    return simpleObject;
                }
            });
            colunaVendaFilmeNome.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Secao, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Secao, String> cell) {
                    final Secao secao = cell.getValue();
                    final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty(secao.getFilme().getNome());
                    return simpleObject;
                }
            });
            colunaVendaSecaoHorario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Secao, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Secao, String> cell) {
                    final Secao secao = cell.getValue();
                    final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty(DateUtil.HourToString(secao.getHorario()));
                    return simpleObject;
                }
            });
            observableListaSecao = FXCollections.observableArrayList(repositorioSecao.getLista());
            tabelaVenda.setItems(observableListaSecao);
        } catch (Exception e) {
            Logger.getLogger(ListaSecoesController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
