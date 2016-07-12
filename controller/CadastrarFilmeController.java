package controller;

import bean.Filme;
import dao.FilmeDAO;
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

public class CadastrarFilmeController implements Initializable {

    @FXML
    private Label labelFilmeCodigo;
    @FXML
    private Label labelFilmeGenero;
    @FXML
    private Label labelFilmeNome;
    @FXML
    private Label labelFilmeSinopse;
    @FXML
    private TextField textFieldFilmeCodigo;
    @FXML
    private TextField textFieldFilmeGenero;
    @FXML
    private TextField textFieldFilmeNome;
    @FXML
    private TextField textFieldFilmeSinopse;
    @FXML
    private Button botaoConfirmar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage;
    private boolean botaoConfirmarClicado = false;
    private Filme filme;

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

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
        textFieldFilmeCodigo.setText(filme.getCodigo());
        if (!filme.getCodigo().isEmpty()) {
            textFieldFilmeCodigo.setDisable(true);
        }
        textFieldFilmeGenero.setText(filme.getGenero());
        textFieldFilmeNome.setText(filme.getNome());
        textFieldFilmeSinopse.setText(filme.getSinopse());
    }

    @FXML
    public void acaoBotaoConfirmar() {

        if (validarEntradaDeDados() && validarNaoExisteFilme(textFieldFilmeCodigo.getText())) {
            filme.setCodigo(textFieldFilmeCodigo.getText());
            filme.setGenero(textFieldFilmeGenero.getText());
            filme.setNome(textFieldFilmeNome.getText());
            filme.setSinopse(textFieldFilmeSinopse.getText());
            botaoConfirmarClicado = true;
            dialogStage.close();
        }
    }

    @FXML
    public void acaoBotaoCancelar() {
        dialogStage.close();
    }

    private boolean validarNaoExisteFilme(String codigo) {
        if (filme.getCodigo().equalsIgnoreCase(codigo)) {
            return true;
        }
         
        try {
            FilmeDAO filmeDAO = new FilmeDAO();
            if (filmeDAO.buscarPorCodigo(codigo) == null) {
                return true;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro no cadastro");
                alert.setHeaderText("Campos inválidos, por favor, corrija...");
                alert.setContentText("O filme já está cadastrado!");
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

        if (textFieldFilmeCodigo.getText() == null || textFieldFilmeCodigo.getText().length() == 0) {
            errorMessage += "Código inválido!\n";
        }
        if (textFieldFilmeGenero.getText() == null || textFieldFilmeGenero.getText().length() == 0) {
            errorMessage += "Genero inválido!\n";
        }
        if (textFieldFilmeNome.getText() == null || textFieldFilmeNome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        if (textFieldFilmeSinopse.getText() == null || textFieldFilmeSinopse.getText().length() == 0) {
            errorMessage += "Sinopse inválida!\n";
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
