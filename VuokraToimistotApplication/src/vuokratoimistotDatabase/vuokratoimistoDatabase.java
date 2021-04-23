/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuokratoimistotDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import vuokratoimistotapplication.VuokraToimistotApplication;


/**
 *
 * @author hoang
 */
public class vuokratoimistoDatabase {
    
    //Metodi joka avaa tietokantayhteyden
    public static Connection openConnection(String connString) throws SQLException {
        Connection con = DriverManager.getConnection(connString);
        System.out.println("\t>> Yhteys ok");
        return con;
    }
    
    //Metodi joka sulije tietokantayhteyden
    public static void closeConnection(Connection c) throws SQLException {
        if (c != null) {
            c.close();
        }
        System.out.println("\t>> Tietokantayhteys suljettu");
    }
    
    //Metodi joka poistaa tietokannan, jos se on olemassa, jonka jalkeen luo uuden
    public static void createDatabase(Connection c, String db) throws SQLException {

        Statement stmt = c.createStatement();
        stmt.executeQuery("DROP DATABASE IF EXISTS " + db);
        System.out.println("\t>> Tietokanta " + db + " tuhottu");

        stmt.executeQuery("CREATE DATABASE " + db);
        System.out.println("\t>> Tietokanta " + db + " luotu");

        stmt.executeQuery("USE " + db);
        System.out.println("\t>> Kaytetaan tietokantaa " + db);

    }
    
    //Metodi joka luo uuden taulun
    public static void createTable(Connection c, String sql, String tauluNimi) throws SQLException {

        Statement stmt = c.createStatement();
        stmt.executeQuery(sql);
        System.out.println("\t>> Taulu " + tauluNimi + " luotu");
    }
    
    //Metodi joka asettaa tietokannan valituksi
    public static void useDatabase(Connection c, String db) throws SQLException {
        Statement stmt = c.createStatement();
        stmt.executeQuery("USE " + db);
        System.out.println("\t>> Käytetään tietokantaa " + db);
    }
    
    //Metodi joka lisätä tietokannan asiakaita
    private static void addAsiakas(Connection c, int asiakasID, String etunimi, String sukunimi, 
                                    String yritys)throws SQLException {   
        
        PreparedStatement ps = c.prepareStatement
        (
        "INSERT INTO asiakas (asiakasID, etunimi, sukunimi, yritys)"
         + "VALUES(?, ?, ?, ?)"
        );
        
        ps.setInt(1, asiakasID);
        ps.setString(2, etunimi);
        ps.setString(3, sukunimi);
        ps.setString(4, yritys);
    
        ps.execute();       
        System.out.println("\t>> Lisätty asiakas: " + etunimi +" "+ sukunimi);
        
    }
    
    //Metodi joka lisätä tietokannan tyontekijat
    private static void addTyontekija(Connection c, int tyontekijaID, String etunimi, String sukunimi)throws SQLException {   
        
        PreparedStatement ps = c.prepareStatement
        (
        "INSERT INTO tyontekija (tyontekijaID, etunimi, sukunimi)"
         + "VALUES(?, ?, ?)"
        );
        
        ps.setInt(1, tyontekijaID);
        ps.setString(2, etunimi);
        ps.setString(3, sukunimi);
        
        ps.execute();       
        System.out.println("\t>> Lisätty tyontekija: " + etunimi +" "+ sukunimi);
        
    }
    
    //Metodi joka lisätä tietokannan tyontekijat
    private static void addToimipiste(Connection c, int toimipisteID, String toimipisteNimi, 
                                    int vuorokausiHinta, int toimipisteKoko )throws SQLException {   
        
        PreparedStatement ps = c.prepareStatement
        (
        "INSERT INTO toimipiste (toimipisteID, toimipisteNimi, vuorokausiHinta, toimipisteKoko)"
         + "VALUES(?, ?, ?, ?)"
        );
        
        ps.setInt(1, toimipisteID);
        ps.setString(2, toimipisteNimi);
        ps.setInt(3, vuorokausiHinta);
        ps.setInt(4, toimipisteKoko);
        
        ps.execute();       
        System.out.println("\t>> Lisätty toimipiste: " + toimipisteNimi);
        
    }
    
    
    /**
     * @param args
     * @throws java.sql.SQLException
     */
    public static void main (String []args) throws SQLException{
    // Luodaan Connection String olemassa olevaan tietokantaan
        Connection conn = openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                + "3306?user=opiskelija&password=opiskelija1");

        // Luodaan tietokanta
        createDatabase(conn, "karelia_vuokratoimistot_R01");

        // Otetaan tietokanta kayttoon
        VuokraToimistotApplication.useDatabase(conn, "karelia_vuokratoimistot_R01");
        
        
        //Asiakas taulu luonti
        createTable(conn, 
                "CREATE TABLE asiakas ("
                        + "asiakasID INT NOT NULL PRIMARY KEY," 
                        + "etunimi VARCHAR (20) NOT NULL,"
                        + "sukunimi VARCHAR (30)NOT NULL,"
                        + "yritys VARCHAR (30)NOT NULL)"   ,
                "asiakas"
                );
        //Lisätä asiakas tauluun
        addAsiakas(conn, 1100, "Aava", "Pesonen", "Aava Oy");
        addAsiakas(conn, 1101, "Milla", "Saarinen", "Salo Oy");
        
        //Tyontekija taulu luonti
        createTable(conn, 
                "CREATE TABLE tyontekija ("
                        + "tyontekijaID INT NOT NULL PRIMARY KEY," 
                        + "etunimi VARCHAR (20) NOT NULL,"
                        + "sukunimi VARCHAR (30)NOT NULL)"
                        ,
                "tyontekija"
                );
        //Lisätä tyontekija tauluun
        addTyontekija(conn, 2200, "Ansa", "Niemi");
        addTyontekija(conn, 2201, "Merja", "Mäkelä");
        
        //Toimipiste taulu luonti
        createTable(conn, 
                "CREATE TABLE toimipiste ("
                        + "toimipisteID INT NOT NULL PRIMARY KEY," 
                        + "toimipisteNimi VARCHAR (30) NOT NULL,"
                        + "vuorokausiHinta INT NOT NULL,"
                        + "toimipisteKoko INT NOT NULL)"                   
                        ,
                "toimipiste"
                );
        
        addToimipiste(conn, 60160, "Linnunlahti", 1200, 65);
        addToimipiste(conn, 60100, "Rantakylä", 800, 70);
        
        
//        CREATE TABLE Toimipiste(  ToimipisteID INT NOT NULL,  
//        ToimipisteNimi VARCHAR(30) NOT NULL,  VuorokausiHinta INT NOT NULL,  
//        ToimipisteKoko INT NOT NULL,  PRIMARY KEY (ToimipisteID)); 
        
        
    }
}
