/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuokratoimistotapplication;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import vuokratoimistotDatabase.vuokratoimistoDatabase;
import vuokratoimistotapplication.Luokat.Palvelu;
import static vuokratoimistotapplication.PaavalikkoViewController.closeConnection;

/**
 * FXML Controller class
 *
 * @author matty
 */
public class PalveluidenHallintaViewController implements Initializable {

    @FXML
    private TableView<Palvelu> tblViewPalvelu;
    @FXML
    private TextField txtPalvelunID;
    @FXML
    private TextField txtPalvelunNimi;
    @FXML
    private TextField txtPalvelunHinta;
    @FXML
    private TextField txtPalvelunKuvaus;
    @FXML
    private TableColumn<Palvelu, Integer> palvelunIDColumn;
    @FXML
    private TableColumn<Palvelu, String> palvelunNimiColumn;
    @FXML
    private TableColumn<Palvelu, Integer> palvelunHintaColumn;
    @FXML
    private TableColumn<Palvelu, String> palvelunKuvausColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Alustetaan columnit
        palvelunIDColumn.setCellValueFactory(new PropertyValueFactory<>("palvelunID"));
        palvelunNimiColumn.setCellValueFactory(new PropertyValueFactory<>("palvelunNimi"));
        palvelunHintaColumn.setCellValueFactory(new PropertyValueFactory<>("palvelunHinta"));
        palvelunKuvausColumn.setCellValueFactory(new PropertyValueFactory<>("palvelunKuvaus"));
        
        fillTableView();
    }    

    @FXML
    private void menuAddNewClicked(ActionEvent event) {
        
                try {

            // Aukaistaan tietokantayhteys
            Connection conn = vuokratoimistoDatabase.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");

            // Asetetaan oikea tietokanta yhteydessa valituksi
            vuokratoimistoDatabase.useDatabase(conn, "karelia_vuokratoimistot_R01");

            // Haetaan tiedot tekstikentista
            vuokratoimistoDatabase.addPalvelu(conn, Integer.parseInt(txtPalvelunID.getText()), txtPalvelunNimi.getText(),
                    Integer.parseInt(txtPalvelunHinta.getText()), txtPalvelunKuvaus.getText());

            // Tyhjennetaan TableView komponentti
            clearTableview();

            // Lisataan tiedot TableView komponenttiin uudelleen
            fillTableView();

            // Suljetaan tietokantayhteys
            vuokratoimistoDatabase.closeConnection(conn);
        }
        
        // Napataan kiinni mahdolliset SQL poikkeukset
        catch (SQLException ex) {
            System.out.println("Catchiin meni");
            java.util.logging.Logger.getLogger(ToimipisteidenHallintaViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void menuCloseClicked(ActionEvent event) {
        
        // Yritetaan sulkea tietokantayhteys
        try {
            Connection conn = DriverManager.getConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");
            closeConnection(conn);
        }

        // Napataan kiinni mahdolliset SQL poikkeukset
        catch (SQLException ex) {
            System.out.println("Catchiin meni");
            java.util.logging.Logger.getLogger(PaavalikkoViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Suljetaan ikkkuna    
        Stage stage = (Stage) tblViewPalvelu.getScene().getWindow();
        stage.close();
    }
    

    @FXML
    private void menuEditClicked(ActionEvent event) {
        
        try {

            // Aukaistaan tietokantayhteys
            Connection conn = vuokratoimistoDatabase.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");

            // Asetetaan oikea tietokanta yhteydessa valituksi
            vuokratoimistoDatabase.useDatabase(conn, "karelia_vuokratoimistot_R01");

            // Haetaan tiedot tekstikentista
            vuokratoimistoDatabase.updatePalvelu(conn, Integer.parseInt(txtPalvelunID.getText()), txtPalvelunNimi.getText(),
                    Integer.parseInt(txtPalvelunHinta.getText()), txtPalvelunKuvaus.getText());

            // Tyhjennetaan TableView komponentti
            clearTableview();

            // Lisataan tiedot TableView komponenttiin uudelleen
            fillTableView();

            // Suljetaan tietokantayhteys
            vuokratoimistoDatabase.closeConnection(conn);
        }

        // Napataan kiinni mahdolliset SQL poikkeukset
        catch (SQLException ex) {
            System.out.println("Catchiin meni");
            java.util.logging.Logger.getLogger(ToimipisteidenHallintaViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void menuDeleteClicked(ActionEvent event) {
        
          try {

            // Aukaistaan tietokantayhteys
            Connection conn = vuokratoimistoDatabase.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");

            // Asetetaan oikea tietokanta yhteydessa valituksi
            vuokratoimistoDatabase.useDatabase(conn, "karelia_vuokratoimistot_R01");

            // Haetaan tiedot tekstikentista
            vuokratoimistoDatabase.deletePalvelu(conn, Integer.parseInt(txtPalvelunID.getText()));

            // Tyhjennetaan TableView komponentti
            clearTableview();

            // Lisataan tiedot TableView komponenttiin uudelleen
            fillTableView();

            // Suljetaan tietokantayhteys
            vuokratoimistoDatabase.closeConnection(conn);
        }

        // Napataan kiinni mahdolliset SQL poikkeukset
        catch (SQLException ex) {
            System.out.println("Catchiin meni");
            java.util.logging.Logger.getLogger(ToimipisteidenHallintaViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    // Metodi joka tayttaa TableView komponentin tietokannan tiedoilla
    public void fillTableView() {

        try {

            // Aukaistaan tietokantayhteys
            Connection conn = vuokratoimistoDatabase.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");

            // Asetetaan oikea tietokanta yhteydessa valituksi
            vuokratoimistoDatabase.useDatabase(conn, "karelia_vuokratoimistot_R01");

            // Haetaan tiedot tietokannasta
            ResultSet palveluInfoResult = vuokratoimistoDatabase.selectPalvelu(conn);

            while (palveluInfoResult.next()) {
                Palvelu palvelu = new Palvelu(palveluInfoResult.getInt("palvelunID"), palveluInfoResult.getString("palvelunNimi"),
                        palveluInfoResult.getInt("palvelunHinta"), palveluInfoResult.getString("palvelunKuvaus"));
                tblViewPalvelu.getItems().add(palvelu);
            }

            // Suljetaan tietokantayhteys
            vuokratoimistoDatabase.closeConnection(conn);
        }

        // Napataan kiinni mahdolliset SQL poikkeukset
        catch (SQLException ex) {
            System.out.println("Catchiin meni");
            java.util.logging.Logger.getLogger(ToimipisteidenHallintaViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    /**
     * Metodi joka tyhjentaa TableView komponentin
     */
    public void clearTableview() {
        for (int i = 0; i < tblViewPalvelu.getItems().size(); i++) {
            tblViewPalvelu.getItems().clear();
        }
    }
    
}
