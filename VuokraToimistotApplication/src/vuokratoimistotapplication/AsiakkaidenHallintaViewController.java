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
import java.sql.PreparedStatement;
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
import javafx.scene.layout.HBox;
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
    private HBox btnMuokkaa;
    @FXML
    private Button btnLisaa;
    @FXML
    private Button btnPoista;
    @FXML
    private TableView<Asiakas> tableAsiakas;
    
    @FXML
    private TableColumn<Asiakas, String> colEtunimi;
    @FXML
    private TableColumn<Asiakas, String> colSukunimi;
    @FXML
    private TableColumn<Asiakas, String> colYritys;
    @FXML
    private TableColumn<Asiakas, Integer> colId;
    
    //vuokratoimistoDatabase olio
    vuokratoimistoDatabase dataOlio = new vuokratoimistoDatabase();
    //resultset
    //private ResultSet rs = null;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     updateTableviewAsiakas();
       
     //prompt text ei näy ekassa textfielissä, koska se valitaan ekaksi controlliksi, siksi setFocusTraversable(false) metodi.
     txtId.setFocusTraversable(false);
     txtEtunimi.setFocusTraversable(false);
     txtSukunimi.setFocusTraversable(false);
     txtYritys.setFocusTraversable(false);
        // TODO
    }   
    //tableview syöttää tauluun tiedot
    public void updateTableviewAsiakas(){
        //Opiskelijat
        colId.setCellValueFactory(new PropertyValueFactory<>("asiakasID"));
        colEtunimi.setCellValueFactory(new PropertyValueFactory<>("etunimi"));
        colSukunimi.setCellValueFactory(new PropertyValueFactory<>("sukunimi"));
        colYritys.setCellValueFactory(new PropertyValueFactory<>("yritys"));
        
        try {
            // Luodaan Connection String olemassa olevaan tietokantaan
            Connection conn = vuokratoimistoDatabase.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");
            
            
            // Otetaan tietokanta kayttoon
            vuokratoimistoDatabase.useDatabase(conn, "karelia_vuokratoimistot_R01");
            
            // Haetaan tiedot tietokannasta
            ResultSet namesResult = vuokratoimistoDatabase.selectAsiakas(conn);
            
        // Lisätään uudet person luokan ilmentymät TableView komponenttiin
            while (namesResult.next()) {
                Asiakas person = new Asiakas(namesResult.getInt("asiakasID"), namesResult.getString("etunimi"), namesResult.getString("sukunimi"), namesResult.getString("yritys"));
                tableAsiakas.getItems().add(person);

            }
            
        //Suljetaan yhteys
            vuokratoimistoDatabase.closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(AsiakkaidenHallintaViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /** Tyhjennetään asiakas tableview*/
    public void clearTableviewAsiakas(){
        for (int i=0;i<tableAsiakas.getItems().size();i++){
            tableAsiakas.getItems().clear();
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
    /**
     *  public static ResultSet selectAsiakas(Connection c) throws SQLException {
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT asiakasID, etunimi, sukunimi, yritys FROM asiakas ORDER BY etunimi"
        );
        
        return rs;
        
    }
     
     */
    public static void deleteAsiakas(Connection c, int asiakasID) throws SQLException {
    PreparedStatement ps = c.prepareStatement( 
        ("DELETE FROM asiakas WHERE asiakasID=?")
                
    );
    //parametri jonka mukaan poistetaan
    ps.setInt(1, asiakasID);
    
    //toteutetaan delete toiminto
    ps.execute();
    System.out.println("\t>> poistettu opiskelija_id " + asiakasID);
   
    }
    //muokataan opiskelijaa
    public static void editAsiakas(Connection c, int asiakasID, String etunimi, String sukunimi, String yritys) throws SQLException {
    PreparedStatement ps = c.prepareStatement(
        ("UPDATE asiakas SET etunimi=?, sukunimi=?, yritys=? WHERE asiakasID=?")
    );

    //Laitetaan oikeat parametrit
    ps.setString(1,etunimi);
    ps.setString(2,sukunimi);
    ps.setString(3,yritys);
    ps.setInt(4,asiakasID); 
   
   
    //TToteutetaan muutokset
    ps.execute();
    
    System.out.println("\t>> Päivitetty asiakasID tiedot: " + asiakasID);
        
    }

    @FXML
    private void LisaaAsiakas(ActionEvent event) throws SQLException {
        // Luodaan Connection String olemassa olevaan tietokantaan
        Connection conn = vuokratoimistoDatabase.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");
            
            
        // Otetaan tietokanta kayttoon
        vuokratoimistoDatabase.useDatabase(conn, "karelia_vuokratoimistot_R01");
        
        //Syötetään tiedot ruudulta 
       vuokratoimistoDatabase.addAsiakas(conn, Integer.parseInt(txtId.getText()), txtEtunimi.getText(), txtSukunimi.getText(), txtYritys.getText());
     
        
        //Tyhjennetään aiempi Asiakas tableview
        clearTableviewAsiakas();
        
        //Paivitetaan Asiakas tableview taulu
        updateTableviewAsiakas();
        
        //Suljetaan yhteys
            vuokratoimistoDatabase.closeConnection(conn);
    }

    @FXML
    private void MuokkaaAsiakas(ActionEvent event) throws SQLException {
        // Luodaan Connection String olemassa olevaan tietokantaan
        Connection conn = vuokratoimistoDatabase.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");
               
        // Otetaan tietokanta kayttoon
        vuokratoimistoDatabase.useDatabase(conn, "karelia_vuokratoimistot_R01");
        
        //muokkaa asiakkaan tiedot
        
        editAsiakas(conn, Integer.parseInt(txtId.getText()), txtEtunimi.getText(),
                txtSukunimi.getText(), txtYritys.getText());
        
        //Tyhjennetään aiempi Asiakas tableview
        clearTableviewAsiakas();
        
        //Paivitetaan Asiakas tableview taulu
        updateTableviewAsiakas();
        
        //Suljetaan yhteys
            vuokratoimistoDatabase.closeConnection(conn);
    }

    @FXML
    private void PoistaAsiakas(ActionEvent event) throws SQLException {
        // Luodaan Connection String olemassa olevaan tietokantaan
        Connection conn = vuokratoimistoDatabase.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");
               
        // Otetaan tietokanta kayttoon
        vuokratoimistoDatabase.useDatabase(conn, "karelia_vuokratoimistot_R01");
        
        //Poistetaan asiakkaan tiedot
        deleteAsiakas(conn, Integer.parseInt(txtId.getText()));
        
        //Tyhjennetään aiempi Asiakas tableview
        clearTableviewAsiakas();
        
        //Paivitetaan Asiakas tableview taulu
        updateTableviewAsiakas();
        
        //Suljetaan yhteys
            vuokratoimistoDatabase.closeConnection(conn);
    }
    
}
