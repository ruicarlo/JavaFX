package controller;

import bean.Sala;
import dao.FilmeDAO;
import dao.SalaDAO;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastrarSalaController implements Initializable {

    @FXML
    private Label labelSalaNumero;
    @FXML
    private Label labelSalaCapacidade;
    @FXML
    private TextField textFieldSalaNumero;
    @FXML
    private TextField textFieldSalaCapacidade;
    @FXML
    private Button botaoConfirmar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage;
    private boolean botaoConfirmarClicado = false;
    private Sala sala;

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

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
        if (sala.getNumero() != 0) {
            textFieldSalaNumero.setText(String.valueOf(sala.getNumero()));
            textFieldSalaNumero.setDisable(true);
            textFieldSalaCapacidade.setText(String.valueOf(sala.getCapacidade()));
        } else {
            textFieldSalaNumero.setText("");
            textFieldSalaCapacidade.setText("");
        }
    }

    @FXML
    public void acaoBotaoConfirmar() {

        if (validarEntradaDeDados() && validarNaoExisteSala(Integer.parseInt(textFieldSalaNumero.getText()))) {
            sala.setNumero(Integer.parseInt(textFieldSalaNumero.getText()));
            sala.setCapacidade(Integer.parseInt(textFieldSalaCapacidade.getText()));
            botaoConfirmarClicado = true;
            dialogStage.close();
        }
    }

    @FXML
    public void acaoBotaoCancelar() {
        dialogStage.close();
    }

    private boolean validarNaoExisteSala(Integer numero) {
        if (sala.getNumero().equals(numero)) {
            return true;
        }

        try {
            SalaDAO salaDAO = new SalaDAO();
            if (salaDAO.buscarPorNumero(numero) == null) {
                return true;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro no cadastro");
                alert.setHeaderText("Campos inválidos, por favor, corrija...");
                alert.setContentText("A sala já está cadastrada!");
                alert.show();
                return false;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CadastrarFilmeController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (textFieldSalaNumero.getText() == null || textFieldSalaNumero.getText().length() == 0 || textFieldSalaNumero.getText().equals("0")) {
            errorMessage += "Número inválido!\n";
        }
        if (textFieldSalaCapacidade.getText() == null || textFieldSalaCapacidade.getText().length() == 0 || textFieldSalaCapacidade.getText().equals("0")) {
            errorMessage += "Capacidade inválido!\n";
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
}
