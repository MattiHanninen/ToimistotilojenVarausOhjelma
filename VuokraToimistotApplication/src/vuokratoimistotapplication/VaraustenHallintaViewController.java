/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuokratoimistotapplication;

import java.net.URL;
import java.util.ResourceBundle;
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
 * @author matty & Hoang
 */
public class VaraustenHallintaViewController implements Initializable {

    @FXML
    private MenuItem menuClose;
    @FXML
    private TableView<?> tbvVaraus;
    @FXML
    private TableColumn<?, ?> colVarausID;
    @FXML
    private TableColumn<?, ?> colAloitusPaiva;
    @FXML
    private TableColumn<?, ?> colLopetusPaiva;
    @FXML
    private TableColumn<?, ?> colAsiakasID;
    @FXML
    private TableColumn<?, ?> colToimipisteID;
    @FXML
    private TextField txtVarausID;
    @FXML
    private TextField txfAloitusPaiva;
    @FXML
    private TextField txfLopetusPaiva;
    @FXML
    private TextField txfAsiakasID;
    @FXML
    private TextField txfToimipisteID;
    @FXML
    private Button btHaku;
    @FXML
    private Button btLisaa;
    @FXML
    private Button btMuokka;
    @FXML
    private Button btPoista;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void menuClosedClicked(ActionEvent event) {
    }

    @FXML
    private void btHakuClicked(ActionEvent event) {
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
