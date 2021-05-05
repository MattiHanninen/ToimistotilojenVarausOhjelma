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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import vuokratoimistotDatabase.vuokratoimistoDatabase;
import vuokratoimistotapplication.Luokat.Toimipiste;
import vuokratoimistotapplication.Luokat.Varaus;
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
    @FXML
    private TableView<Varaus> tbvVaraus;    
    @FXML
    private TableColumn<Varaus, Integer> colVarausID;
    @FXML
    private TableColumn<Varaus, Date> colAloitusPaiva;
    @FXML
    private TableColumn<Varaus, Date> colLopetusPaiva;
    @FXML
    private TableColumn<Varaus, Integer> colAsiakasID;
    @FXML
    private TableColumn<Varaus, Integer> colToimipisteID;

    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    Statement st;
    @FXML
    private MenuItem menuItemSave;
     
   
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
public ObservableList<Varaus> getVarausList() throws ClassNotFoundException, SQLException{
        ObservableList<Varaus> varausList = FXCollections.observableArrayList();
        Connect();
        String sql = "SELECT varausID, aloitusPaiva, lopetusPaiva, asiakasID, toimipisteID FROM varaus"; 
        
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
               Varaus varaukset = new Varaus();
               varaukset.setVarausID(rs.getInt("varausID"));
               varaukset.setAloitusPaiva(rs.getDate("aloitusPaiva"));
               varaukset.setLopetusPaiva(rs.getDate("lopetusPaiva"));
               varaukset.setAsiakasID(rs.getInt("asiakasID"));
               varaukset.setToimipisteID(rs.getInt("toimipisteID"));
           
               varausList.add(varaukset);
               vuokratoimistoDatabase.closeConnection(conn);
            }
        } catch (SQLException e){
        }
        return varausList;
    }
  
    
    
    public void showVaraus() throws ClassNotFoundException, SQLException{
        ObservableList<Varaus> list = getVarausList();
            colVarausID.setCellValueFactory(new PropertyValueFactory<>("varausID"));
            colAloitusPaiva.setCellValueFactory(new PropertyValueFactory<>("aloitusPaiva"));
            colLopetusPaiva.setCellValueFactory(new PropertyValueFactory<>("lopetusPaiva"));
            colAsiakasID.setCellValueFactory(new PropertyValueFactory<>("asiakasID"));
            colToimipisteID.setCellValueFactory(new PropertyValueFactory<>("toimipisteID"));
            
            tbvVaraus.setItems(list);
            vuokratoimistoDatabase.closeConnection(conn);
    }

    /**
     * Initializes the controller class.
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connect();
            getToimipisteList();
            showVaraus();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(VuokratutTilatRaportointiViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }
    
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
         ObservableList<Varaus> List = FXCollections.observableArrayList();
         
         if(comboBoxToimipiste.getEditor().getText().equals("")){
                showVaraus();
         }      
                else if (datePickerAloitusPaiva.getEditor().getText().equals("")){
                        showVaraus();
                        }
                else if (datePickerLopetusPaiva.getEditor().getText().equals("")){
                        showVaraus();
                        }
                
         else {
            
            String aloitusPaiva = datePickerAloitusPaiva.getEditor().getText();
            String lopetusPaiva = datePickerLopetusPaiva.getEditor().getText();
//            
//            pst = conn.prepareStatement(
//                    " SELECT varaus.varausID,varaus.aloitusPaiva, varaus.lopetusPaiva,varaus.asiakasID, varaus.toimipisteID"
//            + "FROM varaus WHERE toimipisteID LIKE '%" + comboBoxToimipiste.getEditor().getText()+ "%' AND aloitusPaiva <= STR_TO_DATE(?, '%d.%m.%Y') AND lopetusPaiva >= = STR_TO_DATE(?, '%d.%m.%Y') "     
//                   
//            );
                        pst = conn.prepareStatement(
                    " SELECT varausID,aloitusPaiva, lopetusPaiva,asiakasID, toimipisteID"
            + "FROM varaus WHERE aloitusPaiva <= STR_TO_DATE(?, '%d.%m.%Y') AND lopetusPaiva >= = STR_TO_DATE(?, '%d.%m.%Y')"     
                   
            );


            pst.setString(1, aloitusPaiva);
            pst.setString(2, lopetusPaiva);
            
            rs = pst.executeQuery();
            while(rs.next()){
                Varaus varaukset = new Varaus();
                varaukset.setVarausID(rs.getInt("varausID"));
                varaukset.setAloitusPaiva(rs.getDate("aloitusPaiva"));
                varaukset.setLopetusPaiva(rs.getDate("lopetusPaiva"));
                varaukset.setAsiakasID(rs.getInt("asiakasID"));
                varaukset.setToimipisteID(rs.getInt("toimipisteID"));
           
                //add varaus to List
                List.add(varaukset);
              
                colVarausID.setCellValueFactory(new PropertyValueFactory<>("varausID"));
                colAloitusPaiva.setCellValueFactory(new PropertyValueFactory<>("aloitusPaiva"));
                colLopetusPaiva.setCellValueFactory(new PropertyValueFactory<>("lopetusPaiva"));
                colAsiakasID.setCellValueFactory(new PropertyValueFactory<>("asiakasID"));
                colToimipisteID.setCellValueFactory(new PropertyValueFactory<>("toimipisteID"));
            
                tbvVaraus.setItems(List);
                vuokratoimistoDatabase.closeConnection(conn);   

            }  
         }           
    }

    @FXML
    private void menuItemSaveClicked(ActionEvent event) throws IOException {
        Stage secondaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Tallentaa varaus lista");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showSaveDialog(secondaryStage);
        if (file != null) {
        saveFile(tbvVaraus.getItems(),file);
        }
            
        }
        
    public void saveFile (ObservableList<Varaus>observableVarausList, File file) throws IOException{
     try {
         
         BufferedWriter outWriter = new BufferedWriter(new FileWriter(file));
             for (Varaus varaus:observableVarausList ){
                 outWriter.write(varaus.toString());
                 outWriter.newLine();
             }
             System.out.println(observableVarausList.toString());
         
            
        }catch(IOException ex){
            Alert ioAlert = new Alert(AlertType.ERROR, "OOPS", ButtonType.OK);
            ioAlert.setContentText("Jotain virhe");
            ioAlert.showAndWait();
            if (ioAlert.getResult() == ButtonType.OK){
                ioAlert.close();
            }
        }
        }
    }
       

