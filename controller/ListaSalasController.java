package controller;

import bean.Sala;
import dao.SalaDAO;
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
import repositorio.RepositorioSala;

public class ListaSalasController implements Initializable {

    @FXML
    private TableView<Sala> tabelaSala;

    @FXML
    private TableColumn<Sala, Integer> colunaSalaNumero;

    @FXML
    private TableColumn<Sala, Integer> colunaSalaCapacidade;

    @FXML
    private Label labelSalaNumero;

    @FXML
    private Label labelSalaCapacidade;

    @FXML
    private Button botaoSalaInserir;

    @FXML
    private Button botaoSalaApagar;

    @FXML
    private Button botaoSalaAlterar;

    private RepositorioSala repositorioSala = new RepositorioSala();

    private ObservableList<Sala> observableListaSala;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTabela();
        tabelaSala.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarItemTabela(newValue));
    }

    public void selecionarItemTabela(Sala sala) {
        if (sala != null) {
            labelSalaNumero.setText(String.valueOf(sala.getNumero()));
            labelSalaCapacidade.setText(String.valueOf(sala.getCapacidade()));
        } else {
            labelSalaNumero.setText("");
            labelSalaCapacidade.setText("");

        }
    }

    private void carregarTabela() {
        try {
            repositorioSala.setLista(new SalaDAO().buscarTodos().getLista());
            colunaSalaNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
            colunaSalaCapacidade.setCellValueFactory(new PropertyValueFactory<>("capacidade"));
            observableListaSala = FXCollections.observableArrayList(repositorioSala.getLista());
            tabelaSala.setItems(observableListaSala);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void acaoBotaoApagar() throws IOException, ClassNotFoundException {
        Sala sala = tabelaSala.getSelectionModel().getSelectedItem();
        if (sala.getNumero() != 0) {
            SalaDAO salaDAO = new SalaDAO();
            salaDAO.setSala(sala);
            salaDAO.excluir();
            carregarTabela();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Escolha uma sala na grid");
            alert.show();
        }
    }
 
    @FXML
    public void acaoBotaoInserir() throws IOException, ClassNotFoundException {
        Sala sala = new Sala(0,0);
        boolean botaoConfirmarClicado = abrirJanelaCadastroSalaDialog(sala);
        if (botaoConfirmarClicado) {
            SalaDAO salaDAO = new SalaDAO();
            salaDAO.setSala(sala);
            salaDAO.salvar();
            carregarTabela();
        }
    }

    @FXML
    public void acaoBotaoAlterar() throws IOException, ClassNotFoundException {
        Sala sala  = tabelaSala.getSelectionModel().getSelectedItem();
        if (sala.getNumero() != 0) {
            boolean botaoConfirmarClicado = abrirJanelaCadastroSalaDialog(sala);
            if (botaoConfirmarClicado) {
                SalaDAO salaDAO = new SalaDAO();
                salaDAO.setSala(sala);
                salaDAO.atualizar();
                carregarTabela();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Escolha uma sala na grid");
            alert.show();
        }
    }

    public boolean abrirJanelaCadastroSalaDialog(Sala sala) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CadastrarSalaController.class.getResource("/view/CadastrarSala.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Salas");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        CadastrarSalaController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setSala(sala);
       

        dialogStage.showAndWait();

        return controller.isBotaoConfirmarClicado();

    } 
    
}
