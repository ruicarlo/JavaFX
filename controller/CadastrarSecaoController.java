package controller;

import bean.Filme;
import bean.Sala;
import bean.Secao;
import dao.FilmeDAO;
import dao.SalaDAO;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import repositorio.RepositorioFilme;
import repositorio.RepositorioSala;
import util.DateUtil;

public class CadastrarSecaoController implements Initializable {

    @FXML
    private Label labelSecaoFilme;
    @FXML
    private Label labelSecaoSala;
    @FXML
    private Label labelSecaoHorario;

    @FXML
    private ChoiceBox<Filme> comboFilmes;
    @FXML
    private ChoiceBox<Sala> comboSalas;
    @FXML
    private TextField textFieldSecaoHorario;

    @FXML
    private Button botaoConfirmar;
    @FXML
    private Button buttonCancelar;

    @FXML
    private ObservableList<Filme> observableListaFilme;

    @FXML
    private ObservableList<Sala> observableListaSala;

    private Stage dialogStage;
    private boolean botaoConfirmarClicado = false;
    private Filme filme;
    private Sala sala;
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

    public Sala getSala() {
        return sala;
    }

    public Secao getSecao() {
        return secao;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setSecao(Secao secao) {
        this.secao = secao;
        popularComboFilmes(secao.getFilme());
        popularComboSalas(secao.getSala());
        textFieldSecaoHorario.setText(DateUtil.HourToString(secao.getHorario()));

    }

    @FXML
    public void acaoBotaoConfirmar() {
        if (validarEntradaDeDados()) {
            try {
                secao.setFilme(comboFilmes.getValue());
                secao.setSala(comboSalas.getValue());
                secao.setHorario(DateUtil.stringToHour(textFieldSecaoHorario.getText()));
            } catch (ParseException ex) {
                Logger.getLogger(CadastrarSecaoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            botaoConfirmarClicado = true;
            dialogStage.close();
        }
    }

    @FXML
    public void acaoBotaoCancelar() {
        dialogStage.close();
    }

    private boolean validarEntradaDeDados() {
        String errorMessage = "";
        if (textFieldSecaoHorario.getText() == null || textFieldSecaoHorario.getText().length() == 0 || !DateUtil.verificaHora(textFieldSecaoHorario.getText())) {
            errorMessage += "Horário inválido!\n";
        }
        if (comboSalas.getValue().getNumero() == null || comboSalas.getValue().getNumero() == 0) {
            errorMessage += "Selecione uma sala!\n";
        }
        if (comboFilmes.getValue().getCodigo() == null || comboFilmes.getValue().getCodigo().length() == 0) {
            errorMessage += "Selecione um filme!\n";
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

    private void popularComboFilmes(Filme filmeEscolhido) {
        RepositorioFilme repositorioFilme = new RepositorioFilme();
        try {
            repositorioFilme.setLista(new FilmeDAO().buscarTodos().getLista());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CadastrarSecaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        observableListaFilme = FXCollections.observableArrayList(repositorioFilme.getLista());
        comboFilmes.setItems(observableListaFilme);
        comboFilmes.getSelectionModel().select(filmeEscolhido);
    }

    private void popularComboSalas(Sala salaEscolhida) {
        RepositorioSala repositorioSala = new RepositorioSala();
        try {
            repositorioSala.setLista(new SalaDAO().buscarTodos().getLista());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CadastrarSecaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        observableListaSala = FXCollections.observableArrayList(repositorioSala.getLista());
        comboSalas.setItems(observableListaSala);
        comboSalas.getSelectionModel().select(salaEscolhida);
    }
}
