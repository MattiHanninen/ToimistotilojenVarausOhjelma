/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuokratoimistotapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author matty
 */
public class TyontekijoidenHallintaViewController implements Initializable {

    @FXML
    private MenuItem menuClose;
    @FXML
    private TextField txfID;
    @FXML
    private TextField txfEtunimi;
    @FXML
    private TextField txfSukunimi;
    @FXML
    private Button btLisaa;
    @FXML
    private Button btMuokka;
    @FXML
    private Button btPoista;
    @FXML
    private TableView<?> tbvTyontekijat;
    @FXML
    private TableColumn<?, ?> colID;
    @FXML
    private TableColumn<?, ?> colEtunimi;
    @FXML
    private TableColumn<?, ?> colSukunimi;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void menuCloseClicked(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void btLisaaClicked(ActionEvent event) {
        
    }

    @FXML
    private void btMuokkaClicked(ActionEvent event) {
    }

    @FXML
    private void btPoistaClicked(ActionEvent event) {
    }
    
}
