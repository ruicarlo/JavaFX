package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;


public class BoxExternoController implements Initializable {

    @FXML
    private MenuItem menuItemVendas;
    @FXML
    private MenuItem menuItemFilmes;
    @FXML
    private MenuItem menuItemSalas;
    @FXML
    private MenuItem menuItemSecoes;
    @FXML
    private MenuItem menuItemRelatoriosVendasPorSecao;
    @FXML
    private AnchorPane boxInterno;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    @FXML
    public void acaoMenuItemVendas() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/ListaVendas.fxml"));
        boxInterno.getChildren().setAll(a);
    }

    @FXML
    public void acaoMenuItemFilmes() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/ListaFilmes.fxml"));
        boxInterno.getChildren().setAll(a);
    }
    
    @FXML
    public void acaoMenuItemSalas() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/ListaSalas.fxml"));
        boxInterno.getChildren().setAll(a);
    }
    
    @FXML
    public void acaoMenuItemSecoes() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/ListaSecoes.fxml"));
        boxInterno.getChildren().setAll(a);
    }    
    
    @FXML
    
    public void acaoMenuItemRelatorioVendaPorSecao() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/ListaRelatorioVendaPorSecao.fxml"));
        boxInterno.getChildren().setAll(a);
    }
    
}
