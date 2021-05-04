/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuokratoimistotapplication;

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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import vuokratoimistotapplication.Luokat.Tyontekija;

/**
 * FXML Controller class
 *
 * @author matty & Hoang
 */
public class TyontekijoidenHallintaViewController implements Initializable {

    @FXML
    private MenuItem menuClose;
    @FXML
    private TextField txfID;
    @FXML
    private TextField txfEtunimi;
    @FXML
    private TextField txfSukunimi;
    @FXML
    private Button btLisaa;
    @FXML
    private Button btMuokka;
    @FXML
    private Button btPoista;
    @FXML
    private TableView<Tyontekija> tbvTyontekijat;
    @FXML
    private TableColumn<Tyontekija, Integer> colID;
    @FXML
    private TableColumn<Tyontekija, String> colEtunimi;
    @FXML
    private TableColumn<Tyontekija, String> colSukunimi;

    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    Statement st;
    
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
  
    public ObservableList<Tyontekija> getTyontekijaList() throws ClassNotFoundException, SQLException{
        ObservableList<Tyontekija> tyontekijaList = FXCollections.observableArrayList();
        Connect();
        String sql = "SELECT tyontekijaID,etunimi,sukunimi FROM tyontekija"; 
        
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
               Tyontekija tekijat = new Tyontekija();
               tekijat.setTyontekijaID(rs.getInt("tyontekijaID"));
               tekijat.setEtunimi(rs.getString("etunimi"));
               tekijat.setSukunimi(rs.getString("sukunimi"));
             
               tyontekijaList.add(tekijat);
               vuokratoimistoDatabase.closeConnection(conn);
            }
        } catch (SQLException e){
        }
        return tyontekijaList;
    }
    
    public void showTyontekijat() throws ClassNotFoundException, SQLException{
        ObservableList<Tyontekija> list = getTyontekijaList();
            colID.setCellValueFactory(new PropertyValueFactory<>("tyontekijaID"));
            colEtunimi.setCellValueFactory(new PropertyValueFactory<>("etunimi"));
            colSukunimi.setCellValueFactory(new PropertyValueFactory<>("sukunimi"));           
            tbvTyontekijat.setItems(list);
            vuokratoimistoDatabase.closeConnection(conn);
    }
    @FXML
    private void menuCloseClicked(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void btLisaaClicked(ActionEvent event) throws ClassNotFoundException, SQLException {
        Connect();
        int tyontekijaID = Integer.parseInt(txfID.getText());
        String etunimi = txfEtunimi.getText();
        String sukunimi = txfSukunimi.getText();
        
         try {
            pst = conn.prepareStatement("INSERT INTO tyontekija (tyontekijaID, etunimi, sukunimi) VALUES (?, ?, ?)");
            
            pst.setInt(1, tyontekijaID);
            pst.setString(2, etunimi);
            pst.setString(3, sukunimi);
             
            int status = pst.executeUpdate();
            
            if (status == 1) {
                Alert alert = new Alert (Alert.AlertType.INFORMATION);
                alert.setTitle ("Onnistu");
                alert.setHeaderText("Työntekija");
                alert.setContentText("Työntekija lisääminen onnistui");
                alert.showAndWait();
                
                tbvTyontekijat.refresh();
                showTyontekijat();
                
                txfID.setText("");
                txfEtunimi.setText("");
                txfSukunimi.setText("");
                
                System.out.println("\t>> Lisätty työntekija " + tyontekijaID);
                vuokratoimistoDatabase.closeConnection(conn);
            
            } else {             
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setTitle ("Ei onnistu");
                alert.setHeaderText("Työntekija");
                alert.setContentText("Työntekija lisääminen ei onnistu");
                alert.showAndWait();
             
            }           
        }catch(SQLException e) {
                
        }
    }
    
    @FXML
    private void btMuokkaClicked(ActionEvent event) throws ClassNotFoundException, SQLException {
      Connect();
        int tyontekijaID = Integer.parseInt(txfID.getText());
        String etunimi = txfEtunimi.getText();
        String sukunimi = txfSukunimi.getText(); 
        
        try {
            
            pst = conn.prepareStatement("UPDATE tyontekija SET etunimi=?, sukunimi=? WHERE tyontekijaID=?");
           
            pst.setString(1, etunimi);
            pst.setString(2, sukunimi);
            pst.setInt(3, tyontekijaID);
            
           int status =  pst.executeUpdate(); 
           if (status == 1) {
           Alert alert = new Alert (Alert.AlertType.INFORMATION);
                alert.setTitle ("Onnistu");
                alert.setHeaderText("Työntekija");
                alert.setContentText("Työntekija muokaaminen onnistu");
                alert.showAndWait();  
            
            tbvTyontekijat.refresh();
            showTyontekijat(); 
            
            txfID.setText("");
            txfEtunimi.setText("");
            txfSukunimi.setText("");
            
            System.out.println("\t>> Muokattu työntekija " + tyontekijaID);
            vuokratoimistoDatabase.closeConnection(conn);               
           } else {             
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setTitle ("Ei onnistu");
                alert.setHeaderText("Työntekija");
                alert.setContentText("Työntekija lisääminen ei onnistu");
                alert.showAndWait();
             
            }
                     
        }catch(SQLException e) {   
            throw e;
        }                
    }

    @FXML
    private void btPoistaClicked(ActionEvent event) throws ClassNotFoundException, SQLException, Exception {
        Connect();
        int  tyontekijaID = Integer.parseInt(txfID.getText());
         
        try {
            pst = conn.prepareStatement("DELETE FROM tyontekija WHERE tyontekijaID=?");           
            pst.setInt(1, tyontekijaID);   
            
            rs = pst.executeQuery();
            
            tbvTyontekijat.refresh();
            showTyontekijat(); 
            
            txfID.setText("");
            txfEtunimi.setText("");
            txfSukunimi.setText("");
            
             Alert alert = new Alert (Alert.AlertType.INFORMATION);
                   alert.setTitle ("Onnistu");
                   alert.setHeaderText("Työntekija");
                   alert.setContentText("Työntekija poistaminen onnistu");
                   alert.showAndWait();  
            
            System.out.println("\t>> Poistu työntekija " + tyontekijaID);    
            vuokratoimistoDatabase.closeConnection(conn);  
 
            if (rs == null) { 
                throw new Exception ("Työtekija poistaminen ei onnistu");
            }
         } catch (SQLException e) {throw e;}
       
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connect();
            System.out.println("\t>> Yhteys ok");
            showTyontekijat();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TyontekijoidenHallintaViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }    
}
