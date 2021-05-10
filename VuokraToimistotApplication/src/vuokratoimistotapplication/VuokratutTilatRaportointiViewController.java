/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuokratoimistotapplication;

import java.io.File;
import java.io.IOException;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import vuokratoimistotDatabase.vuokratoimistoDatabase;
import vuokratoimistotapplication.Luokat.Varaus;
import vuokratoimistotapplication.Luokat.VuokrattutTilatTaulu;
import static vuokratoimistotapplication.PaavalikkoViewController.closeConnection;

/**
 * FXML Controller class
 *
 * @author matty & Hoang
 */
public class VuokratutTilatRaportointiViewController implements Initializable {

    @FXML
    private ComboBox<Integer> comboBoxToimipiste;
    @FXML
    private MenuItem menuItemClose;
    @FXML
    private DatePicker datePickerAloitusPaiva;
    @FXML
    private DatePicker datePickerLopetusPaiva;
    @FXML
    private Button btEtsi;
    private TableView<Varaus> tbvVaraus;    
    private TableColumn<Varaus, Integer> colVarausID;
    private TableColumn<Varaus, Date> colAloitusPaiva;
    private TableColumn<Varaus, Date> colLopetusPaiva;
    private TableColumn<Varaus, Integer> colAsiakasID;
    private TableColumn<Varaus, Integer> colToimipisteID;

    
    @FXML
    private VBox txfHakuTulos;
    @FXML
    private TextField txfHakuID;
    private TextArea txtAreaHakuTulos;
    @FXML
    private TableView<VuokrattutTilatTaulu> tbvVuokratutTaulut;
    @FXML
    private TableColumn<VuokrattutTilatTaulu, String> colToimipste;
    @FXML
    private TableColumn<VuokrattutTilatTaulu, String> colYritys;
    @FXML
    private TableColumn<VuokrattutTilatTaulu, Date> colAloitusPvm;
    @FXML
    private TableColumn<VuokrattutTilatTaulu, Date> colLopetusPvm;
    @FXML
    private TableColumn<VuokrattutTilatTaulu, String> colEtunimi;
    @FXML
    private TableColumn<VuokrattutTilatTaulu, String> colSukunimi;
    @FXML
    private TableColumn<VuokrattutTilatTaulu, Integer> colSumma;
  
   
    @FXML
    private WebView webView;
    @FXML
    private MenuItem menuItemTemplete;
    @FXML
    private MenuItem menuItemReport;
    
     
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    Statement st;
    
    private static final String TOIMIPISTE = "__TOIMIPISTE__";
    private static final String PVM = "__DATE__";
    private static final String TABLE_CONTENT = "__TABLE__";

    
   //Avata tietokanta 
    public void Connect() throws ClassNotFoundException, SQLException{
        try {
        conn = vuokratoimistoDatabase.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");
        vuokratoimistoDatabase.useDatabase(conn, "karelia_vuokratoimistot_R01");
        //System.out.println("\t>> Yhteys ok");
        } catch (SQLException e) {
            System.out.println("\t>> Yhteys epäonistu");
         }
    }
    
     // Fill contends to the comboBox
     private void fillComboBoxToimipiste() throws ClassNotFoundException, SQLException {
        try{    
            Connect();
            // Toimipsite tiedot tietokannasta
            rs = vuokratoimistoDatabase.selectToimipiste(conn);
            while (rs.next()) {            
                comboBoxToimipiste.getItems().addAll(rs.getInt(1));
            } 
            vuokratoimistoDatabase.closeConnection(conn);
           }catch (SQLException ex) {
            java.util.logging.Logger.getLogger(LisapalvelutLaitteetRaportointiViewController.class.getName()).log(Level.SEVERE, null, ex);
           }
    }
     
     // Close VuokratutTilatRaportointi scene
    @FXML
    private void menuItemCloseClicked(ActionEvent event) throws ClassNotFoundException {       
        try {
            Connect();
            closeConnection(conn);
        }
        catch (SQLException ex) {
            java.util.logging.Logger.getLogger(PaavalikkoViewController.class.getName()).log(Level.SEVERE, null, ex);
        }     
        // Suljetaan ikkkuna    
        Stage stage = (Stage) tbvVuokratutTaulut.getScene().getWindow();
        stage.close();
    }
    
 
    //Luoda Vuokrattu Tilat ObservableList
    public ObservableList<VuokrattutTilatTaulu> getVuokrattutTilatList() throws ClassNotFoundException, SQLException {
        ObservableList<VuokrattutTilatTaulu> vuokrattutTilatList = FXCollections.observableArrayList();
        Connect();
        String sql = "SELECT toimipiste.toimipisteNimi, asiakas.yritys, asiakas.etunimi,asiakas.sukunimi, varaus.asiakasID, varaus.aloitusPaiva, varaus.lopetusPaiva, lasku.summa"
                        + " FROM varaus, toimipiste, asiakas,lasku"
                        + " WHERE varaus.toimipisteID = toimipiste.toimipisteID AND varaus.asiakasID = asiakas.asiakasID AND lasku.asiakasID = asiakas.asiakasID"
                ; 
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                VuokrattutTilatTaulu vuokrattut = new VuokrattutTilatTaulu();
                vuokrattut.setToimipisteNimi(rs.getString("toimipisteNimi"));
                vuokrattut.setYritys(rs.getString("yritys"));
                vuokrattut.setEtunimi(rs.getString("etunimi"));
                vuokrattut.setSukunimi(rs.getString("sukunimi"));
                vuokrattut.setAloitusPaiva(rs.getDate("aloitusPaiva"));
                vuokrattut.setLopetusPaiva(rs.getDate("lopetusPaiva"));
                vuokrattut.setSumma(rs.getInt("summa"));
                vuokrattutTilatList.add(vuokrattut);
                vuokratoimistoDatabase.closeConnection(conn);
            }
        } catch (SQLException e){
            }
             return vuokrattutTilatList;
    }

    public void showVarattu() throws ClassNotFoundException, SQLException{
        ObservableList<VuokrattutTilatTaulu> Tilattulist =  getVuokrattutTilatList() ;
        tbvVuokratutTaulut.setItems(Tilattulist);
        vuokratoimistoDatabase.closeConnection(conn);
    }
    
    
     @FXML
    private void menuItemTempleteClicked(ActionEvent event) {
        loadWebPage("./Report/vuokratuttilat.html");
    }

    @FXML
    private void menuItemReportClicked(ActionEvent event) throws IOException {
        // 1. kopioi pohja
        File source = new File("./Report/vuokratuttilat.html");
        File dest = new File("./Report/vuokratuttilatRaporti.html");

        Files.copy(source.toPath(), dest.toPath(), REPLACE_EXISTING);

        // 2. Korvaa pohjassa olevat paikat tiedoilla
        
        String content = new String(Files.readAllBytes(dest.toPath()), UTF_8);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();
   
        content = content.replaceAll(TOIMIPISTE,colToimipste.getCellData(0) );
        content = content.replaceAll(PVM, LocalDateTime.now().toString());
        //content = content.replaceAll(TABLE_CONTENT, createRow();
         content = content.replaceAll(TABLE_CONTENT, createRow(colToimipste.getCellData(0), colYritys.getCellData(0),colAloitusPvm.getCellData(0), colLopetusPvm.getCellData(0), colSumma.getCellData(0)));
        
        // 3. Lataa muodostettu lasku
        Files.write(dest.toPath(), content.getBytes(UTF_8));
        
        // 4. Näytä muodostettu lasku
        loadWebPage(dest.toPath().toString());
    }
     // Muodostetaan laskun yksi rivi html muodossa
     private String createRow(String service, String desc, Date aloitusPvm, Date lopetusPvm, int summa) {
         return "<tr>" +
                "<td class=\"service\">" +  service + "</td>" + 
                "<td class=\"desc\">" +  desc + "</td>" +
                "<td class=\"desc\">" + aloitusPvm+ "</td>" +
                "<td class=\"desc\">" + lopetusPvm+ "</td>" +
                "<td class=\"total\">" + summa + "€</td>"
                ;
    }

     private void loadWebPage(String path){
        WebEngine engine = webView.getEngine();
        File f = new File(path);
        engine.load(f.toURI().toString());
    }

    
    // Haku ID:lla
    @FXML
    private void hakuIDInput(KeyEvent event) throws ClassNotFoundException, SQLException {
         Connect();
         
         //Luoda varaus lista
         ObservableList<VuokrattutTilatTaulu> HakuList = FXCollections.observableArrayList();
         if(txfHakuID.getText().equals("")){
                showVarattu();
                
         } else {
            pst = conn.prepareStatement(
                    "SELECT toimipiste.toimipisteNimi, asiakas.yritys,asiakas.etunimi,asiakas.sukunimi,  varaus.asiakasID, varaus.aloitusPaiva, varaus.lopetusPaiva, lasku.summa"
                        + "  FROM varaus, toimipiste, asiakas,lasku "
                        + " WHERE varaus.toimipisteID = toimipiste.toimipisteID "
                        + "AND varaus.asiakasID = asiakas.asiakasID "
                        + "AND lasku.asiakasID = varaus.asiakasID AND varaus.varausID LIKE '%" 
                        + txfHakuID.getText()+ "%'"   
            );
            rs = pst.executeQuery();
            while(rs.next()){
                VuokrattutTilatTaulu vuokrattut = new VuokrattutTilatTaulu();
                vuokrattut.setToimipisteNimi(rs.getString("toimipisteNimi"));
                vuokrattut.setYritys(rs.getString("yritys"));
                vuokrattut.setEtunimi(rs.getString("etunimi"));
                vuokrattut.setSukunimi(rs.getString("sukunimi"));
                vuokrattut.setAloitusPaiva(rs.getDate("aloitusPaiva"));
                vuokrattut.setLopetusPaiva(rs.getDate("lopetusPaiva"));
                vuokrattut.setSumma(rs.getInt("summa"));
                
                // Add vuokratut tilat lista
                HakuList.add(vuokrattut);
                tbvVuokratutTaulut.setItems(HakuList);  
            } 
           vuokratoimistoDatabase.closeConnection(conn);          
         }                  
    }

    //Etsi tietokannasta
    @FXML        
    private void btEtsiClicked(ActionEvent event) throws ClassNotFoundException, SQLException {
       Connect();
        //Luoda vuokrattutTilat lista
        ObservableList<VuokrattutTilatTaulu> HakuList = FXCollections.observableArrayList();
            
        if(comboBoxToimipiste.getValue().equals("") && datePickerAloitusPaiva.getValue().equals("") && datePickerLopetusPaiva.getValue().equals("")){
                showVarattu();   
            } 
            else {
            
            pst = conn.prepareStatement(
                    
                    "SELECT toimipiste.toimipisteNimi, asiakas.yritys, asiakas.etunimi, asiakas.sukunimi, varaus.asiakasID, varaus.aloitusPaiva, varaus.lopetusPaiva, lasku.summa "
                        + " FROM varaus, toimipiste, asiakas, lasku "
                        + " WHERE varaus.toimipisteID = toimipiste.toimipisteID AND varaus.asiakasID = asiakas.asiakasID AND lasku.asiakasID = asiakas.asiakasID "
                        + " AND toimipiste.toimipisteID = ?"
                        + " AND ((aloitusPaiva BETWEEN ? AND ?)" 
                        + " OR (lopetusPaiva BETWEEN ? AND ?))"
            );
                    pst.setInt(1, comboBoxToimipiste.getValue());
                    pst.setDate(2,java.sql.Date.valueOf(datePickerAloitusPaiva.getValue()));
                    pst.setDate(3,java.sql.Date.valueOf(datePickerLopetusPaiva.getValue()));
                    pst.setDate(4,java.sql.Date.valueOf(datePickerAloitusPaiva.getValue()));
                    pst.setDate(5,java.sql.Date.valueOf(datePickerLopetusPaiva.getValue()));   

                    rs = pst.executeQuery();            
            while(rs.next()){
               VuokrattutTilatTaulu vuokrattut = new VuokrattutTilatTaulu();
               vuokrattut.setToimipisteNimi(rs.getString("toimipisteNimi"));
               vuokrattut.setYritys(rs.getString("yritys"));
               vuokrattut.setEtunimi(rs.getString("etunimi"));
               vuokrattut.setSukunimi(rs.getString("sukunimi"));               
               vuokrattut.setAloitusPaiva(rs.getDate("aloitusPaiva"));
               vuokrattut.setLopetusPaiva(rs.getDate("lopetusPaiva"));
               vuokrattut.setSumma(rs.getInt("summa"));
           
                //add varaus to List
                HakuList.add(vuokrattut);
                tbvVuokratutTaulut.setItems(HakuList); 
                
               }if (HakuList.isEmpty()) {
                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("Haun tulokset:");
                   alert.setHeaderText("Ei tietoja");
                   alert.setContentText("Ei ole varauksia valitulla aikavälillä.");
                   alert.showAndWait();
            }  
            vuokratoimistoDatabase.closeConnection(conn);
        }
    }
    
    /**
     * Initializes the controller class.
     */
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                colToimipste.setCellValueFactory(new PropertyValueFactory<>("toimipisteNimi"));
                colYritys.setCellValueFactory(new PropertyValueFactory<>("yritys"));
                colEtunimi.setCellValueFactory(new PropertyValueFactory<>("etunimi"));
                colSukunimi.setCellValueFactory(new PropertyValueFactory<>("sukunimi"));
                colAloitusPvm.setCellValueFactory(new PropertyValueFactory<>("aloitusPaiva"));
                colLopetusPvm.setCellValueFactory(new PropertyValueFactory<>("lopetusPaiva"));
                colSumma.setCellValueFactory(new PropertyValueFactory<>("summa"));
        try {
            Connect();
            showVarattu();
            fillComboBoxToimipiste();          
                
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(VuokratutTilatRaportointiViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }


}

       

