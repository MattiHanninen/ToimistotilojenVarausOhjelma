/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuokratoimistotapplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import vuokratoimistotDatabase.vuokratoimistoDatabase;
import vuokratoimistotapplication.Luokat.Asiakas;
import vuokratoimistotapplication.Luokat.Toimipiste;
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
    private ComboBox<Toimipiste> comboBoxToimipiste;
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

    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    Statement st;
    @FXML
    private MenuItem menuItemSave;
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
    private TableColumn<?, ?> colSumma;
  
 
     
   
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
    public ObservableList<Toimipiste> getToimipisteList() throws ClassNotFoundException, SQLException{

        ObservableList<Toimipiste> toimipisteList = FXCollections.observableArrayList();
        Connect();
        
        String sql = "SELECT toimipisteID, toimipisteNimi, vuorokausiHinta,toimipisteKoko FROM toimipiste"; 
        
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
               Toimipiste toimipiste = new Toimipiste(rs.getInt("toimipisteID"), rs.getString("toimipisteNimi"),
                        rs.getInt("vuorokausiHinta"), rs.getInt("toimipisteKoko"));
               toimipisteList.add(toimipiste);
               comboBoxToimipiste.setItems(toimipisteList);
               vuokratoimistoDatabase.closeConnection(conn);
            }
        } catch (SQLException e){
        }
        return toimipisteList;
    }
//    public ObservableList<Varaus> getVarausList() throws ClassNotFoundException, SQLException{
//        ObservableList<Varaus> varausList = FXCollections.observableArrayList();
//        Connect();
//        String sql = "SELECT varausID, aloitusPaiva, lopetusPaiva, asiakasID, toimipisteID FROM varaus"; 
//        
//        try {
//            st = conn.createStatement();
//            rs = st.executeQuery(sql);
//            while (rs.next()){
//               Varaus varaukset = new Varaus();
//               varaukset.setVarausID(rs.getInt("varausID"));
//               varaukset.setAloitusPaiva(rs.getDate("aloitusPaiva"));
//               varaukset.setLopetusPaiva(rs.getDate("lopetusPaiva"));
//               varaukset.setAsiakasID(rs.getInt("asiakasID"));
//               varaukset.setToimipisteID(rs.getInt("toimipisteID"));
//           
//               varausList.add(varaukset);
//               vuokratoimistoDatabase.closeConnection(conn);
//            }
//        } catch (SQLException e){
//        }
//        return varausList;
//    }
//  
//    
//    
//    public void showVaraus() throws ClassNotFoundException, SQLException{
//        ObservableList<Varaus> list = getVarausList();
//            colVarausID.setCellValueFactory(new PropertyValueFactory<>("varausID"));
//            colAloitusPaiva.setCellValueFactory(new PropertyValueFactory<>("aloitusPaiva"));
//            colLopetusPaiva.setCellValueFactory(new PropertyValueFactory<>("lopetusPaiva"));
//            colAsiakasID.setCellValueFactory(new PropertyValueFactory<>("asiakasID"));
//            colToimipisteID.setCellValueFactory(new PropertyValueFactory<>("toimipisteID"));
//            
//            tbvVaraus.setItems(list);
//            vuokratoimistoDatabase.closeConnection(conn);
//    }

    
    // Sulkea hallinta ikkuna
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
        Stage stage = (Stage) tbvVaraus.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btEtsiClicked(ActionEvent event) throws ClassNotFoundException, SQLException {
       Connect();
         
         //Luoda varaus lista
         ObservableList<VuokrattutTilatTaulu> HakuList = FXCollections.observableArrayList();
         if(datePickerAloitusPaiva.getValue().equals("")){
                showVarattu();
                
         } else {
            pst = conn.prepareStatement(
                    
                    "SELECT toimipiste.toimipisteNimi, asiakas.yritys,asiakas.etunimi,asiakas.sukunimi,  varaus.asiakasID, varaus.aloitusPaiva, varaus.lopetusPaiva, lasku.summa"
                        + "  FROM varaus, toimipiste, asiakas,lasku "
                + "WHERE varaus.toimipisteID = toimipiste.toimipisteID AND varaus.asiakasID = asiakas.asiakasID AND lasku.asiakasID = asiakas.asiakasID AND varaus.aloitusPaiva LIKE '%" 
                            + datePickerAloitusPaiva.getValue()+ "%'"   
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
           
                //add varaus to List
               HakuList.add(vuokrattut);
              
                colToimipste.setCellValueFactory(new PropertyValueFactory<>("toimipisteNimi"));
//               colID.setCellValueFactory(new PropertyValueFactory<>("toimipisteID"));
            colYritys.setCellValueFactory(new PropertyValueFactory<>("yritys"));
           colEtunimi.setCellValueFactory(new PropertyValueFactory<>("etunimi"));
            colSukunimi.setCellValueFactory(new PropertyValueFactory<>("sukunimi"));
            
            colAloitusPvm.setCellValueFactory(new PropertyValueFactory<>("aloitusPaiva"));
            colLopetusPvm.setCellValueFactory(new PropertyValueFactory<>("lopetusPaiva"));
            colSumma.setCellValueFactory(new PropertyValueFactory<>("summa"));
            
                tbvVuokratutTaulut.setItems(HakuList);
                vuokratoimistoDatabase.closeConnection(conn);   

            }  
    }
    }
 
   
//Vuokrattu Tilat ObservableList
    public ObservableList<VuokrattutTilatTaulu> getVuokrattutTilatList() throws ClassNotFoundException, SQLException {
         ObservableList<VuokrattutTilatTaulu> vuokrattutTilatList = FXCollections.observableArrayList();
         Connect();
        String sql = "SELECT toimipiste.toimipisteNimi, asiakas.yritys,asiakas.etunimi,asiakas.sukunimi,  varaus.asiakasID, varaus.aloitusPaiva, varaus.lopetusPaiva, lasku.summa"
                        + "  FROM varaus, toimipiste, asiakas,lasku "
                + "WHERE varaus.toimipisteID = toimipiste.toimipisteID AND varaus.asiakasID = asiakas.asiakasID AND lasku.asiakasID = asiakas.asiakasID"
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
            colToimipste.setCellValueFactory(new PropertyValueFactory<>("toimipisteNimi"));
//               colID.setCellValueFactory(new PropertyValueFactory<>("toimipisteID"));
            colYritys.setCellValueFactory(new PropertyValueFactory<>("yritys"));
           colEtunimi.setCellValueFactory(new PropertyValueFactory<>("etunimi"));
            colSukunimi.setCellValueFactory(new PropertyValueFactory<>("sukunimi"));
            
            colAloitusPvm.setCellValueFactory(new PropertyValueFactory<>("aloitusPaiva"));
            colLopetusPvm.setCellValueFactory(new PropertyValueFactory<>("lopetusPaiva"));
            colSumma.setCellValueFactory(new PropertyValueFactory<>("summa"));
            
           
            
            tbvVuokratutTaulut.setItems(Tilattulist);
            vuokratoimistoDatabase.closeConnection(conn);
    }
    
    @FXML
    private void menuItemSaveClicked(ActionEvent event) throws IOException {
        Stage secondaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Tallentaa varaus lista");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showSaveDialog(secondaryStage);
        if (file != null) {
        saveFile(tbvVuokratutTaulut.getItems(),file);
        }
            
        }
        
    public void saveFile (ObservableList<VuokrattutTilatTaulu>observableVuokrattutList, File file) throws IOException{
     try {
         
         BufferedWriter outWriter = new BufferedWriter(new FileWriter(file));
             for (VuokrattutTilatTaulu vuokrattut: observableVuokrattutList ){
                 outWriter.write(vuokrattut.toString());
                 outWriter.newLine();
             }
             System.out.println(observableVuokrattutList.toString());
         
            
        }catch(IOException ex){
            Alert ioAlert = new Alert(AlertType.ERROR, "OOPS", ButtonType.OK);
            ioAlert.setContentText("Jotain virhe");
            ioAlert.showAndWait();
            if (ioAlert.getResult() == ButtonType.OK){
                ioAlert.close();
            }
        }
        }
     
    @FXML
    private void CBoxToimipiste(ActionEvent event) {
    }
    
    @FXML
    private void hakuIDInput(KeyEvent event) throws ClassNotFoundException, SQLException {
        
        
    }
    
    /**
     * Initializes the controller class.
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connect();
            getToimipisteList();
            showVarattu() ;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(VuokratutTilatRaportointiViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }

   


   

   

    
}

       

