/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuokratoimistotapplication;

import java.io.File;
import java.io.IOException;
import vuokratoimistotapplication.Luokat.Lasku;
import vuokratoimistotDatabase.vuokratoimistoDatabase;

import java.net.URL;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


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
    @FXML
    private TextField txfEtsiLaskunID;
    @FXML
    private TextField txfEmail;
    @FXML
    private TextField txfOsoite;
    @FXML
    private TextField txfToimipiste;
    @FXML
    private TextField txfPalvelu;
    @FXML
    private Button btnTeeLasku;
    @FXML
    private Button btnMallilasku;
    @FXML
    private WebView webView;
    
    //Laskun muuttujat
    private static final String CUSTOMER_NAME = "__CUST_NAME__";
    private static final String CUSTOMER_ADDRESS = "__CUST_ADDR__";
    private static final String CUSTOMER_EMAIL = "__CUST_EMAIL__";
    private static final String INVOICE_DATE = "__DATE__";
    private static final String INVOICE_DUE_DATE = "__DUE_DATE__";
    private static final String TABLE_CONTENT = "__TABLE__";
    
    //omat
    private static final String CUSTOMER_VIITENUMERO = "__CUST_VIITENUMERO__";

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
       try {
        ps.execute();
        } catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Laskun tietojen muokkaaminen");
            alert.setHeaderText("Virhe");
            alert.setContentText("Laskun muokkaaminen epäonnistui");
            alert.showAndWait();
        }
    
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
              // txfErapaiva.setText(rs.getString("erapaiva")).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
               txfMaksupaiva.setText(rs.getString("maksupaiva"));
               txfSumma.setText(rs.getString("summa"));
               txfMaksettu.setText(rs.getString("maksettu"));
               txfLaskutusTyyppi.setText(rs.getString("laskutusTyyppi"));
           }
    //txtSuoritusDate.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) -- Matin vinkki
        //Suljetaan yhteys
        vuokratoimistoDatabase.closeConnection(conn);
    }

    @FXML
    private void etsiLaskunidKeyReleased(KeyEvent event) throws SQLException {
        // Luodaan Connection String olemassa olevaan tietokantaan
        Connection conn = vuokratoimistoDatabase.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");
             
        // Otetaan tietokanta kayttoon
        vuokratoimistoDatabase.useDatabase(conn, "karelia_vuokratoimistot_R01");
        
            if(txfEtsiLaskunID.getText().equals("")){
                clearTableviewLasku();
                updateTableviewLasku();
            }
            else{
                clearTableviewLasku();
                Statement stmt = conn.createStatement();
                ResultSet rset = stmt.executeQuery(
               "SELECT * FROM lasku where laskuID LIKE '%"+txfEtsiLaskunID.getText()+"%'");
                    
                  while(rset.next()){
                      Lasku person = new Lasku(rset.getInt("laskuID"), rset.getInt("asiakasID"), rset.getDate("erapaiva"), rset.getDate("maksupaiva"),
                              rset.getInt("summa"), rset.getInt("maksettu"),rset.getString("laskutusTyyppi"));
                        tableLasku.getItems().add(person);
                        
                        //Laskujen tableview täyttö
                        colLaskuID.setCellValueFactory(new PropertyValueFactory<>("laskuID"));
                        colAsiakasID.setCellValueFactory(new PropertyValueFactory<>("asiakasID"));
                        colErapaiva.setCellValueFactory(new PropertyValueFactory<>("erapaiva"));
                        colMaksupaiva.setCellValueFactory(new PropertyValueFactory<>("maksupaiva"));
                        colSumma.setCellValueFactory(new PropertyValueFactory<>("summa"));
                        colMaksettu.setCellValueFactory(new PropertyValueFactory<>("maksettu"));
                        colLaskutustyyppi.setCellValueFactory(new PropertyValueFactory<>("laskutusTyyppi"));
            }
           
            vuokratoimistoDatabase.closeConnection(conn);
                     
            }
    }

    @FXML
    private void closeLaskuWindow(ActionEvent event) {
        Stage stage = (Stage) tableLasku.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void editClearTextfield(ActionEvent event) {
        txfLaskuID.setText("");
        txfAsiakasID.setText("");
        txfErapaiva.setText("");
        txfMaksupaiva.setText("");
        txfSumma.setText("");
        txfMaksettu.setText("");
        txfLaskutusTyyppi.setText("");
    }

    @FXML
    private void btnTeeLaskuClicked(ActionEvent event) throws IOException {
        // 1. kopioi pohja
        File source = new File("./src/vuokratoimistotapplication/laskujenHTML/mallilasku.html");
        File dest = new File("./src/vuokratoimistotapplication/laskujenHTML/teeLasku.html");
        Files.copy(source.toPath(), dest.toPath(), REPLACE_EXISTING);

        // 2. Korvaa pohjassa olevat paikat tiedoilla
        String content = new String(Files.readAllBytes(dest.toPath()), UTF_8);
       // content = content.replaceAll(CUSTOMER_NAME, "TEPPO TAPUTTAJA");
      // content = content.replaceAll(CUSTOMER_NAME, txtEtunimi_o.getText());
      //Asiakas
        content = content.replaceAll(CUSTOMER_NAME, txfAsiakasID.getText());
      //erapaiva
        content = content.replaceAll(INVOICE_DUE_DATE, txfErapaiva.getText());
        //Email
        content = content.replaceAll(CUSTOMER_EMAIL, txfEmail.getText());
        //CUSTOMER_ADDRESS
        content = content.replaceAll(CUSTOMER_ADDRESS, txfOsoite.getText());
        //INVOICE_DATE
        content = content.replaceAll(INVOICE_DATE, txfMaksupaiva.getText());
        //CUSTOMER_VIITENUMERO
        content = content.replaceAll(CUSTOMER_VIITENUMERO, txfLaskuID.getText());
        
        //txtEtunimi_o.getText();
       // content = content.replaceAll(TABLE_CONTENT, createRow("Siivous", "Siivouspalvelu","40.00","10.00","400.00" ));
       //txfToimispiste
        content = content.replaceAll(TABLE_CONTENT, createRow(txfToimipiste.getText(), txfPalvelu.getText(), txfSumma.getText() ));
        
        // 3. Lataa muodostettu lasku
        Files.write(dest.toPath(), content.getBytes(UTF_8));
        
        // 4. Näytä muodostettu lasku
        loadWebPage(dest.toPath().toString());
    }
    
    private String createRow(String service, String desc, String total) {
         return "<tr>" +
                "<td class=\"service\">" + service + "</td>" + 
                "<td class=\"desc\">" + desc + "</td>" +
                "<td class=\"total\">" + total + "€</td>"
                ;
    }
    

    @FXML
    private void btnMallilaskuClicked(ActionEvent event) {
        loadWebPage("./src/vuokratoimistotapplication/laskujenHTML/mallilasku.html");
    }
    
    private void loadWebPage(String path){
        WebEngine engine = webView.getEngine();
        File f = new File(path);
        engine.load(f.toURI().toString());
    }
    
}
