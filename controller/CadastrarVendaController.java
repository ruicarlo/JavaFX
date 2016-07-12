package controller;

import bean.Secao;
import bean.Venda;
import dao.SecaoDAO;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import repositorio.RepositorioSecao;
import util.DateUtil;

public class CadastrarVendaController implements Initializable {

    @FXML
    private Label labelSecao;

    @FXML
    private ChoiceBox<Secao> comboSecoes;

    @FXML
    private Button botaoConfirmar;

    @FXML
    private Button buttonCancelar;

    @FXML
    private ObservableList<Secao> observableListaSecao;

    private Stage dialogStage;
    private boolean botaoConfirmarClicado = false;
    private Venda venda;
    private Secao secao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isBotaoConfirmarClicado() {
        return botaoConfirmarClicado;
    }

    public void setBotaoConfirmarClicado(boolean botaoConfirmarClicado) {
        this.botaoConfirmarClicado = botaoConfirmarClicado;
    }

    public Secao getSecao() {
        return secao;
    }

    public Venda getVemda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
        popularComboSecoes();
    }

    @FXML
    public void acaoBotaoConfirmar() {
        if (validarEntradaDeDados()) {
            venda.setSecao(comboSecoes.getValue());
            botaoConfirmarClicado = true;
            dialogStage.close();
        }
    }

    @FXML
    public void acaoBotaoCancelar() {
        dialogStage.close();
    }
 
    private boolean validarDisponibilidadeLugar(int codigo) {
        try {
            SecaoDAO secaoDAO =  new SecaoDAO();
            return secaoDAO.buscarLugaresPorCodigo(codigo) > 0;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CadastrarVendaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private boolean validarEntradaDeDados() {
        String errorMessage = "";
        if (comboSecoes.getValue().getCodigo() == 0) {
            errorMessage += "Selecione uma seção!\n";
        } else if(!validarDisponibilidadeLugar(comboSecoes.getValue().getCodigo())) {
            errorMessage += "Ingressos esgotados para esta seção!\n";
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

    private void popularComboSecoes() {
        RepositorioSecao repositorioSecao = new RepositorioSecao();
        try {
            repositorioSecao.setLista(new SecaoDAO().buscarTodos().getLista());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CadastrarSecaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        observableListaSecao = FXCollections.observableArrayList(repositorioSecao.getLista());
        comboSecoes.setItems(observableListaSecao);
        comboSecoes.getSelectionModel().select(0);
    }

}
