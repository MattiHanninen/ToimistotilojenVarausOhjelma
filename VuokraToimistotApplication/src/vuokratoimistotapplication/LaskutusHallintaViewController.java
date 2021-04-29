/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuokratoimistotapplication;

import vuokratoimistotapplication.Luokat.Lasku;
import vuokratoimistotDatabase.vuokratoimistoDatabase;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    @FXML
    private Button btnLisaa;
    @FXML
    private Button btnMuokkaa;
    @FXML
    private Button btnPoista;
    @FXML
    private TableView<?> tableLasku;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //prompt text ei näy ekassa textfielissä, koska se valitaan ekaksi controlliksi, siksi setFocusTraversable(false) metodi.
        txfLaskuID.setFocusTraversable(false);
        txfAsiakasID.setFocusTraversable(false);
        txfErapaiva.setFocusTraversable(false);
        txfMaksupaiva.setFocusTraversable(false);
        txfSumma.setFocusTraversable(false);
        txfMaksettu.setFocusTraversable(false);
        txfLaskutusTyyppi.setFocusTraversable(false);
    }    
    
    //valitsee asiakkaiden tiedot tableview:n täyttämistä varten
     public static ResultSet selectLasku(Connection c) throws SQLException {
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT laskuID, asiakasID, erapaiva, maksupaiva, summa, maksettu, laskutusTyyppi FROM lasku ORDER BY laskuID"
        );
        
        return rs;
        
    }

    @FXML
    private void btnLisaaClicked(ActionEvent event) {
    }

    @FXML
    private void btnMuokkaaClicked(ActionEvent event) {
    }

    @FXML
    private void btnPoistaClicked(ActionEvent event) {
    }
    
}
