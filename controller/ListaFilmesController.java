package controller;

import bean.Filme;
import dao.FilmeDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import repositorio.RepositorioFilme;

public class ListaFilmesController implements Initializable {

    @FXML
    private TableView<Filme> tabelaFilme;

    @FXML
    private TableColumn<Filme, String> colunaFilmeCodigo;

    @FXML
    private TableColumn<Filme, String> colunaFilmeGenero;

    @FXML
    private TableColumn<Filme, String> colunaFilmeNome;

    @FXML
    private Label labelFilmeCodigo;

    @FXML
    private Label labelFilmeGenero;

    @FXML
    private Label labelFilmeNome;

    @FXML
    private Label labelFilmeSinopse;

    @FXML
    private Button botaoFilmeInserir;

    @FXML
    private Button botaoFilmeApagar;

    @FXML
    private Button botaoFilmeAlterar;

    private RepositorioFilme repositorioFilme = new RepositorioFilme();

    private ObservableList<Filme> observableListaFilme;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTabela();
        tabelaFilme.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarItemTabela(newValue));
    }

    public void selecionarItemTabela(Filme filme) {
        if (filme != null) {
            labelFilmeCodigo.setText(filme.getCodigo());
            labelFilmeGenero.setText(filme.getGenero());
            labelFilmeNome.setText(filme.getNome());
            labelFilmeSinopse.setText(filme.getSinopse());
        } else {
            labelFilmeCodigo.setText("");
            labelFilmeGenero.setText("");
            labelFilmeNome.setText("");
            labelFilmeSinopse.setText("");
        }
    }

    private void carregarTabela() {
        try {
            repositorioFilme.setLista(new FilmeDAO().buscarTodos().getLista());
            colunaFilmeCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
            colunaFilmeGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
            colunaFilmeNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            observableListaFilme = FXCollections.observableArrayList(repositorioFilme.getLista());
            tabelaFilme.setItems(observableListaFilme);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void acaoBotaoApagar() throws IOException, ClassNotFoundException {
        Filme filme = tabelaFilme.getSelectionModel().getSelectedItem();
        if (filme != null) {
            FilmeDAO filmeDAO = new FilmeDAO();
            filmeDAO.setFilme(filme);
            filmeDAO.excluir();
            carregarTabela();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Escolha um filme na grid");
            alert.show();
        }
    }

    @FXML
    public void acaoBotaoInserir() throws IOException, ClassNotFoundException {
        Filme filme = new Filme("", "", "", "");
        boolean botaoConfirmarClicado = abrirJanelaCadastroFilmeDialog(filme);
        if (botaoConfirmarClicado) {
            FilmeDAO filmeDAO = new FilmeDAO();
            filmeDAO.setFilme(filme);
            filmeDAO.salvar();
            carregarTabela();
        }
    }

    @FXML
    public void acaoBotaoAlterar() throws IOException, ClassNotFoundException {
        Filme filme = tabelaFilme.getSelectionModel().getSelectedItem();
        if (filme != null) {
            boolean botaoConfirmarClicado = abrirJanelaCadastroFilmeDialog(filme);
            if (botaoConfirmarClicado) {
                FilmeDAO filmeDAO = new FilmeDAO();
                filmeDAO.setFilme(filme);
                filmeDAO.atualizar();
                carregarTabela();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Escolha um filme na grid");
            alert.show();
        }
    }

    public boolean abrirJanelaCadastroFilmeDialog(Filme filme) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CadastrarFilmeController.class.getResource("/view/CadastrarFilme.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Filmes");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        CadastrarFilmeController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setFilme(filme);

        dialogStage.showAndWait();

        return controller.isBotaoConfirmarClicado();

    }
}
