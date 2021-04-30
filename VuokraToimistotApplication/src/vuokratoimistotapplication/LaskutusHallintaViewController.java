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
import java.sql.Date;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


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
    private TableColumn<Lasku, Integer> colLaskuID;
    @FXML
    private TableColumn<Lasku, Integer> colAsiakasID;
    @FXML
    private TableColumn<Lasku, Date> colErapaiva;
    @FXML
    private TableColumn<Lasku, Date> colMaksupaiva;
    @FXML
    private TableColumn<Lasku, Integer> colSumma;
    @FXML
    private TableColumn<Lasku, Integer> colMaksettu;
    @FXML
    private TableColumn<Lasku, String> colLaskutustyyppi;
    @FXML
    private Button btnLisaa;
    @FXML
    private Button btnMuokkaa;
    @FXML
    private Button btnPoista;
    @FXML
    private TableView<Lasku> tableLasku;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        updateTableviewLasku();
        
        //prompt text ei näy ekassa textfielissä, koska se valitaan ekaksi controlliksi, siksi setFocusTraversable(false) metodi.
        txfLaskuID.setFocusTraversable(false);
        txfAsiakasID.setFocusTraversable(false);
        txfErapaiva.setFocusTraversable(false);
        txfMaksupaiva.setFocusTraversable(false);
        txfSumma.setFocusTraversable(false);
        txfMaksettu.setFocusTraversable(false);
        txfLaskutusTyyppi.setFocusTraversable(false);
    }    
    
     //tableview syöttää tauluun tiedot
    public void updateTableviewLasku(){
        
        colLaskuID.setCellValueFactory(new PropertyValueFactory<>("laskuID"));
        colAsiakasID.setCellValueFactory(new PropertyValueFactory<>("asiakasID"));
        colErapaiva.setCellValueFactory(new PropertyValueFactory<>("erapaiva"));
        colMaksupaiva.setCellValueFactory(new PropertyValueFactory<>("maksupaiva"));
        colSumma.setCellValueFactory(new PropertyValueFactory<>("summa"));
        colMaksettu.setCellValueFactory(new PropertyValueFactory<>("maksettu"));
        colLaskutustyyppi.setCellValueFactory(new PropertyValueFactory<>("laskutusTyyppi"));
        
        try {
            // Luodaan Connection String olemassa olevaan tietokantaan
            Connection conn = vuokratoimistoDatabase.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");
            
            // Otetaan tietokanta kayttoon
            vuokratoimistoDatabase.useDatabase(conn, "karelia_vuokratoimistot_R01");
            
            // Haetaan tiedot tietokannasta
            ResultSet namesResult = selectLasku(conn);
            
            // Lisätään uudet person luokan ilmentymät TableView komponenttiin
            while (namesResult.next()) {
                Lasku person = new Lasku(namesResult.getInt("laskuID"), namesResult.getInt("asiakasID"), namesResult.getDate("erapaiva"),
                        namesResult.getDate("maksupaiva"), namesResult.getInt("summa"), namesResult.getInt("maksettu"), namesResult.getString("laskutusTyyppi"));
                tableLasku.getItems().add(person);
            }
            
            //Suljetaan yhteys
            vuokratoimistoDatabase.closeConnection(conn);
            
        } catch (SQLException ex) {
            Logger.getLogger(AsiakkaidenHallintaViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     /** Tyhjennetään asiakas tableview*/
    public void clearTableviewLasku(){
        for (int i=0;i<tableLasku.getItems().size();i++){
            tableLasku.getItems().clear();
        }
    }
    
    public static void deleteLasku(Connection c, int laskuID) throws SQLException {
        PreparedStatement ps = c.prepareStatement( 
        ("DELETE FROM lasku WHERE laskuID=?")
                
    );
    
        //parametri jonka mukaan poistetaan
        ps.setInt(1, laskuID);
    
        //toteutetaan delete toiminto
        ps.execute();
        System.out.println("\t>> poistettu opiskelija_id " + laskuID);
   
    }
    
    //muokataan opiskelijaa
    public static void editLasku(Connection c, int laskuID, int asiakasID, String erapaiva, String maksupaiva,
            int summa, int maksettu, String laskutusTyyppi) throws SQLException {
        
        //PreparedStatement ps = c.prepareStatement(
        //("UPDATE lasku SET asiakasID=?, erapaiva=?, maksupaiva=?, summa=?, maksettu=?, tilausTapa=? WHERE laskuID=?")
        //);
        // ("UPDATE asiakas SET etunimi=?, sukunimi=?, yritys=? WHERE asiakasID=?"));
        
         PreparedStatement ps = c.prepareStatement(
        ("UPDATE lasku SET asiakasID=?, erapaiva=STR_TO_DATE(?, '%d.%m.%Y'), maksupaiva=STR_TO_DATE(?, '%d.%m.%Y'), summa=?, maksettu=?, laskutusTyyppi=? WHERE laskuID=?")
        );
        //"INSERT INTO lasku (laskuID, asiakasID, erapaiva, maksupaiva, summa, maksettu,laskutusTyyppi)"
        // + "VALUES(?, ?, STR_TO_DATE(?, '%d.%m.%Y'), STR_TO_DATE(?, '%d.%m.%Y'), ?, ?, ?)"
      
        //Laitetaan oikeat parametrit
        ps.setInt(1,asiakasID);
        ps.setString(2,erapaiva);
        ps.setString(3,maksupaiva);
        ps.setInt(4,summa);
        ps.setInt(5,maksettu);
        ps.setString(6,laskutusTyyppi);
        ps.setInt(7,laskuID); 
   
        //Toteutetaan muutokset
        ps.execute();
    
        System.out.println("\t>> Päivitetty laskuID tiedot: " + laskuID);
        
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
    private void btnLisaaClicked(ActionEvent event) throws SQLException {
        // Luodaan Connection String olemassa olevaan tietokantaan
        Connection conn = vuokratoimistoDatabase.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");
             
        // Otetaan tietokanta kayttoon
        vuokratoimistoDatabase.useDatabase(conn, "karelia_vuokratoimistot_R01");
        
        //Syötetään tiedot ruudulta 
        vuokratoimistoDatabase.addLasku(conn, Integer.parseInt(txfLaskuID.getText()), Integer.parseInt(txfAsiakasID.getText()), txfErapaiva.getText(),
                txfMaksupaiva.getText(), Integer.parseInt(txfSumma.getText()), Integer.parseInt(txfMaksettu.getText()), txfLaskutusTyyppi.getText());
                                 
        //Tyhjennetään aiempi Laskujen tableview
        clearTableviewLasku();
        
        //Paivitetaan Laskujen tableview taulu
        updateTableviewLasku();
        
        //Suljetaan yhteys
        vuokratoimistoDatabase.closeConnection(conn);
    }

    @FXML
    private void btnMuokkaaClicked(ActionEvent event) throws SQLException {
         // Luodaan Connection String olemassa olevaan tietokantaan
        Connection conn = vuokratoimistoDatabase.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");
               
        // Otetaan tietokanta kayttoon
        vuokratoimistoDatabase.useDatabase(conn, "karelia_vuokratoimistot_R01");
        
        //muokkaa asiakkaan tiedot
        
        editLasku(conn, Integer.parseInt(txfLaskuID.getText()), Integer.parseInt(txfAsiakasID.getText()), txfErapaiva.getText(),
                txfMaksupaiva.getText(), Integer.parseInt(txfSumma.getText()), Integer.parseInt(txfMaksettu.getText()), txfLaskutusTyyppi.getText());
        
        //Tyhjennetään aiempi Laskujen tableview
        clearTableviewLasku();
        
        //Paivitetaan Laskujen tableview taulu
        updateTableviewLasku();
        
        //Suljetaan yhteys
        vuokratoimistoDatabase.closeConnection(conn);
    }

    @FXML
    private void btnPoistaClicked(ActionEvent event) throws SQLException {
         // Luodaan Connection String olemassa olevaan tietokantaan
        Connection conn = vuokratoimistoDatabase.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");
               
        // Otetaan tietokanta kayttoon
        vuokratoimistoDatabase.useDatabase(conn, "karelia_vuokratoimistot_R01");
        
        //Poistetaan asiakkaan tiedot
        deleteLasku(conn, Integer.parseInt(txfLaskuID.getText()));
        
        //Tyhjennetään aiempi Laskujen tableview
        clearTableviewLasku();
        
        //Paivitetaan Laskujen tableview taulu
        updateTableviewLasku();
        
        //Suljetaan yhteys
        vuokratoimistoDatabase.closeConnection(conn);
      
    }

    /**
     * TableViewta klikatessa hiirellä kopioituvat tiedot textfieldeihin
     * @param event Lasku-taulukkoa klikataan
     * @throws SQLException 
     */
    @FXML
    private void tblLaskuClickedFillTextfield(MouseEvent event) throws SQLException {
        // Luodaan Connection String olemassa olevaan tietokantaan
        Connection conn = vuokratoimistoDatabase.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");
             
        // Otetaan tietokanta kayttoon
        vuokratoimistoDatabase.useDatabase(conn, "karelia_vuokratoimistot_R01");
   
        Lasku user = tableLasku.getSelectionModel().getSelectedItem();
          
        PreparedStatement pst = conn.prepareStatement(
        ("SELECT * from lasku where laskuID =?")
        );
        
        pst.setInt(1, user.getLaskuID());
        ResultSet rs = pst.executeQuery();
           
           while(rs.next()){
               txfLaskuID.setText(rs.getString("laskuID"));
               txfAsiakasID.setText(rs.getString("asiakasID"));
               txfErapaiva.setText(rs.getString("erapaiva"));
               txfMaksupaiva.setText(rs.getString("maksupaiva"));
               txfSumma.setText(rs.getString("summa"));
               txfMaksettu.setText(rs.getString("maksettu"));
               txfLaskutusTyyppi.setText(rs.getString("laskutusTyyppi"));
           }
    
        //Suljetaan yhteys
        vuokratoimistoDatabase.closeConnection(conn);
    }
    
}
