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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author matty & janne
 */
public class AsiakkaidenHallintaViewController implements Initializable {

    @FXML
    private Label lblAsiakkaat;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtEtunimi;
    @FXML
    private TextField txtSukunimi;
    @FXML
    private TextField txtYritys;
    @FXML
    private Button btnMuokkaa;
    @FXML
    private Button btnLisaa;
    @FXML
    private Button btnPoista;
    @FXML
    private TableView <?> tableAsiakas;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colEtunimi;
    @FXML
    private TableColumn<?, ?> colSukunimi;
    @FXML
    private TableColumn<?, ?> colYritys;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        // TODO
    }    

    @FXML
    private void LisaaAsiakas(ActionEvent event) {
    }

    @FXML
    private void MuokkaaAsiakas(ActionEvent event) {
    }

    @FXML
    private void PoistaAsiakas(ActionEvent event) {
    }
    
}
