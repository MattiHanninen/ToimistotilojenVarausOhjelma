/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuokratoimistotapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Janne & matty
 */
public class LaskutusHallintaViewController implements Initializable {

    @FXML
    private TextField txfLaskuID;
    @FXML
    private TextField txfAsiakasID;
    @FXML
    private TextField txfErapaiva;
    @FXML
    private TextField txfMaksupaiva;
    @FXML
    private TextField txfSumma;
    @FXML
    private TextField txfMaksettu;
    @FXML
    private TextField txfLaskutusTyyppi;
    @FXML
    private TableColumn<?, ?> colLaskuID;
    @FXML
    private TableColumn<?, ?> colAsiakasID;
    @FXML
    private TableColumn<?, ?> colErapaiva;
    @FXML
    private TableColumn<?, ?> colMaksupaiva;
    @FXML
    private TableColumn<?, ?> colSumma;
    @FXML
    private TableColumn<?, ?> colMaksettu;
    @FXML
    private TableColumn<?, ?> colLaskutustyyppi;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
