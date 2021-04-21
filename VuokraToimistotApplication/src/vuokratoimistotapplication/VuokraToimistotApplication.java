/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuokratoimistotapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author matty
 */
public class VuokraToimistotApplication extends Application {
    
    
    /*
    * Metodi joka avaa tietokantayhteyden
    */    
    public static Connection openConnection(String connString) throws SQLException {
        Connection con = DriverManager.getConnection(connString);
        System.out.println("\t>> Yhteys ok");
        return con;
    }
    
      /**
     * Metodi joka sulkee tietokantayhteyden
     * @param c Yhteys
     * @throws java.sql.SQLException SQL poikkeus
     */
    public static void closeConnection(Connection c) throws SQLException {
        if (c != null) {
            c.close();
        }
        System.out.println("\t>> Tietokantayhteys suljettu");
    }
    
     /**
     * Metodi joka poistaa tietokannan, jos se on olemassa, jonka jalkeen luo uuden
     * @param c Yhteys
     * @param db Tietokanta
     * @throws SQLException SQL poikkeus
     */

    public static void createDatabase(Connection c, String db) throws SQLException {

        Statement stmt = c.createStatement();
        stmt.executeQuery("DROP DATABASE IF EXISTS " + db);
        System.out.println("\t>> Tietokanta " + db + " tuhottu");

        stmt.executeQuery("CREATE DATABASE " + db);
        System.out.println("\t>> Tietokanta " + db + " luotu");

        stmt.executeQuery("USE " + db);
        System.out.println("\t>> Kaytetaan tietokantaa " + db);

    }
    
     /**
     * Metodi joka luo uuden taulun
     * @param c Yhteys
     * @param sql SQL lause
     * @throws SQLException SQL poikkeus
     */
    public static void createTable(Connection c, String sql) throws SQLException {

        Statement stmt = c.createStatement();
        stmt.executeQuery(sql);
        System.out.println("\t>> Taulu luotu");
    }
    
     /**
     * Metodi joka asettaa tietokannan valituksi
     * @param c Yhteys
     * @param db Tietokanta
     * @throws SQLException SQL poikkeus
     */
    public static void useDatabase(Connection c, String db) throws SQLException {
        Statement stmt = c.createStatement();
        stmt.executeQuery("USE " + db);
        System.out.println("\t>> Käytetään tietokantaa " + db);
    }
    
    
    
    
    
    
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PaavalikkoView.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Paavalikko");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        
        // Luodaan Connection String olemassa olevaan tietokantaan
        Connection conn = openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                + "3306?user=opiskelija&password=opiskelija1");

        // Luodaan tietokanta
        createDatabase(conn, "karelia_vuokratoimistot_R01");

        // Otetaan tietokanta kayttoon
        VuokraToimistotApplication.useDatabase(conn, "karelia_vuokratoimistot_R01");
        
        // Seuraavaksi taulujen luonti metodien avulla
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // Suljetaan yhteys
        closeConnection(conn);
        
        launch(args);
    }
    
}
