/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuokratoimistotapplication;

import vuokratoimistotapplication.Luokat.Asiakas;
import vuokratoimistotDatabase.vuokratoimistoDatabase;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import static vuokratoimistotDatabase.vuokratoimistoDatabase.openConnection;

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
    private TableView <Asiakas> tableAsiakas;
    @FXML
    private TableColumn<Asiakas, Integer> colID;
    @FXML
    private TableColumn<Asiakas, String> colEtunimi;
    @FXML
    private TableColumn<Asiakas, String> colSukunimi;
    @FXML
    private TableColumn<Asiakas, String> colYritys;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        // TODO
    }   
    
    public void updateTableviewAsiakas(){
        //Opiskelijat
        colID.setCellValueFactory(new PropertyValueFactory<>("asiakasID"));
        colEtunimi.setCellValueFactory(new PropertyValueFactory<>("etunimi"));
        colSukunimi.setCellValueFactory(new PropertyValueFactory<>("sukunimi"));
        colYritys.setCellValueFactory(new PropertyValueFactory<>("yritys"));
        
        try {
            // Luodaan Connection String olemassa olevaan tietokantaan
            Connection conn = openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");
            
            // Otetaan tietokanta kayttoon
            VuokraToimistotApplication.useDatabase(conn, "karelia_vuokratoimistot_R01");
            
            // Haetaan tiedot tietokannasta
        //    ResultSet namesResult = selectAsiakas(conn); 
            
        } catch (SQLException ex) {
            Logger.getLogger(AsiakkaidenHallintaViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
      public static void selectAsiakas(Connection c) throws SQLException {
          Statement stmt = c.createStatement();
          ResultSet rs = stmt.executeQuery("SELECT asiakasID, etunimi, sukunimi, yritys FROM asiakas ORDER BY etunimi");
    
         System.out.println("\nNimilista:\n============ ");
         while(rs.next()){
            System.out.println(
            "[" + rs.getInt("asiakasID") +"]"
                    + rs.getString("etunimi")+": "
                    + rs.getString("sukunimi")+": "
                    + rs.getString("yritys"));
         }
    }
    */

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
