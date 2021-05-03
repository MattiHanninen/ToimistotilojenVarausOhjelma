/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuokratoimistotapplication;

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
import vuokratoimistotDatabase.vuokratoimistoDatabase;
import vuokratoimistotapplication.Luokat.Varaus;

/**
 * FXML Controller class
 *
 * @author matty & Hoang
 */
public class VaraustenHallintaViewController implements Initializable {

    @FXML
    private MenuItem menuClose;
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
    @FXML
    private TextField txfAloitusPaiva;
    @FXML
    private TextField txfLopetusPaiva;
    @FXML
    private TextField txfAsiakasID;
    @FXML
    private TextField txfToimipisteID;
    @FXML
    private Button btLisaa;
    @FXML
    private Button btMuokka;
    @FXML
    private Button btPoista;

     Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    Statement st;
    private Varaus varausOlio = new Varaus();
    @FXML
    private TextField txfVarausID;
    
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

    @FXML
    private void menuClosedClicked(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void btLisaaClicked(ActionEvent event) throws ClassNotFoundException, SQLException {
        Connect();
        int varausID = Integer.parseInt(txfVarausID.getText());
        String aloitusPaiva = txfAloitusPaiva.getText();
        String lopetusPaiva = txfAloitusPaiva.getText();
        int asiakasID = Integer.parseInt(txfAsiakasID.getText());
        int toimipisteID = Integer.parseInt(txfToimipisteID.getText());
        
        
         try {
            pst = conn.prepareStatement(
                    "INSERT INTO varaus (varausID, aloitusPaiva, lopetusPaiva, asiakasID, toimipisteID) "
                    + "VALUES (?, STR_TO_DATE(?, '%d.%m.%Y'), STR_TO_DATE(?, '%d.%m.%Y'), ?, ?)"
            );
            
            pst.setInt(1, varausID);
            pst.setString(2, aloitusPaiva);
            pst.setString(3, lopetusPaiva);
            pst.setInt(4, asiakasID);
            pst.setInt(5, toimipisteID);
            
             
            int status = pst.executeUpdate();
            
            if (status == 1) {
                Alert alert = new Alert (Alert.AlertType.INFORMATION);
                alert.setTitle ("Onnistu");
                alert.setHeaderText("Varaus");
                alert.setContentText("Varaus lisääminen onnistui");
                alert.showAndWait();
                
                tbvVaraus.refresh();
                showVaraus();
                
                txfVarausID.setText("");
                txfAloitusPaiva.setText("");
                txfLopetusPaiva.setText("");
                txfAsiakasID.setText("");
                txfToimipisteID.setText("");
                System.out.println("\t>> Lisätty varaus " + varausID);
                vuokratoimistoDatabase.closeConnection(conn);
            
            } else {             
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setTitle ("Ei onnistu");
                alert.setHeaderText("Varaus");
                alert.setContentText("Varaus lisääminen ei onnistu");
                alert.showAndWait();
             
            }           
        }catch(SQLException e) {
                
        }
    }

    @FXML
    private void btMuokkaClicked(ActionEvent event) throws ClassNotFoundException, SQLException {
        Connect();
        int varausID = Integer.parseInt(txfVarausID.getText());
        String aloitusPaiva = txfAloitusPaiva.getText();
        String lopetusPaiva = txfAloitusPaiva.getText();
        int asiakasID = Integer.parseInt(txfAsiakasID.getText());
        int toimipisteID = Integer.parseInt(txfToimipisteID.getText());
        
        try {
            
            pst = conn.prepareStatement("UPDATE varaus SET aloitusPaiva = STR_TO_DATE(?, '%d.%m.%Y'), "
                    + "lopetusPaiva = STR_TO_DATE(?, '%d.%m.%Y'), asiakasID = ?, toimipisteID = ? WHERE varausID = ?");

            pst.setString(1, aloitusPaiva);
            pst.setString(2, lopetusPaiva);
            pst.setInt(3, asiakasID);
            pst.setInt(4, toimipisteID);
            pst.setInt(5, varausID);
             
           int status =  pst.executeUpdate(); 
           if (status == 1) {
           Alert alert = new Alert (Alert.AlertType.INFORMATION);
                alert.setTitle ("Onnistu");
                alert.setHeaderText("Varaus");
                alert.setContentText("Varaus muokaaminen onnistu");
                alert.showAndWait();  
            
                tbvVaraus.refresh();
                showVaraus();
                
                txfVarausID.setText("");
                txfAloitusPaiva.setText("");
                txfLopetusPaiva.setText("");
                txfAsiakasID.setText("");
                txfToimipisteID.setText("");
                
                System.out.println("\t>> Muokattu varaus " + varausID);
                
                vuokratoimistoDatabase.closeConnection(conn);
                
           } else {             
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setTitle ("Ei onnistu");
                alert.setHeaderText("Varaus");
                alert.setContentText("Varaus muokaaminen ei onnistu");
                alert.showAndWait();
             
            }
                     
        }catch(SQLException e) {   
            throw e;
        }
    }

    @FXML
    private void btPoistaClicked(ActionEvent event) throws ClassNotFoundException, Exception {
        Connect();
        int  varausID = Integer.parseInt(txfVarausID.getText());
         
        try {
            pst = conn.prepareStatement("DELETE FROM varaus WHERE varausID=?");           
            pst.setInt(1, varausID);   
            
            rs = pst.executeQuery();
            
                tbvVaraus.refresh();
                showVaraus();
                
                txfVarausID.setText("");
                txfAloitusPaiva.setText("");
                txfLopetusPaiva.setText("");
                txfAsiakasID.setText("");
                txfToimipisteID.setText("");
                
                System.out.println("\t>> Poista varaus " + varausID);
                vuokratoimistoDatabase.closeConnection(conn);
            
             Alert alert = new Alert (Alert.AlertType.INFORMATION);
                   alert.setTitle ("Onnistu");
                   alert.setHeaderText("Varaus");
                   alert.setContentText("Varaus poistaminen onnistu");
                   alert.showAndWait();  
            
            System.out.println("\t>> Poistu varaus " + varausID);    
            vuokratoimistoDatabase.closeConnection(conn);  
 
            if (rs == null) { 
                throw new Exception ("Varaus poistaminen ei onnistu");
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
            showVaraus();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(VaraustenHallintaViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
}
