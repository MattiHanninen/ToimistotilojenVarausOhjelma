
package vuokratoimistotDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        System.out.println("\n" + "\t>> Taulu " + tauluNimi + " luotu");
    }
    
    //Metodi joka asettaa tietokannan valituksi
    public static void useDatabase(Connection c, String db) throws SQLException {
        Statement stmt = c.createStatement();
        stmt.executeQuery("USE " + db);
        System.out.println("\t>> Käytetään tietokantaa " + db);
    }
    
    //valitsee asiakkaiden tiedot tableview:n täyttämistä varten
     public static ResultSet selectAsiakas(Connection c) throws SQLException {
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT asiakasID, etunimi, sukunimi, yritys FROM asiakas ORDER BY etunimi"
        );
        
        return rs;
        
    }
     
     
     
    // Metodi joka palauttaa opiskelijan tiedot tietokannasta
     public static ResultSet selectToimipiste(Connection c) throws SQLException {
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT toimipisteID, toimipisteNimi, vuorokausiHinta, toimipisteKoko FROM toimipiste ORDER BY toimipisteID"
        );
        
        return rs;
        
    }
    
    //Metodi joka lisätä tietokannan asiakaita
    public static void addAsiakas(Connection c, int asiakasID, String etunimi, String sukunimi, 
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
    public static void addTyontekija(Connection c, int tyontekijaID, String etunimi, String sukunimi)throws SQLException {   
        
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
    public static void addToimipiste(Connection c, int toimipisteID, String toimipisteNimi, 
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
    
    //Metodi joka lisätä tietokannan varaukset 
    public static void addVaraus(Connection c, int varausID, String aloitusPaiva,String lopetusPaiva,
                                    int asiakasID, int toimipisteID )throws SQLException {   
        
        PreparedStatement ps = c.prepareStatement
        (
        "INSERT INTO varaus (varausID, aloitusPaiva, lopetusPaiva, asiakasID, toimipisteID)"
         + "VALUES(?, STR_TO_DATE(?, '%d.%m.%Y'), STR_TO_DATE(?, '%d.%m.%Y'), ?, ?)"
        );
        
        ps.setInt(1, varausID);
        ps.setString(2, aloitusPaiva);
        ps.setString(3, lopetusPaiva);
        ps.setInt(4, asiakasID);
        ps.setInt(5, toimipisteID);
            
        ps.execute();       
        System.out.println("\t>> Lisätty varaus: " + varausID );
        
    }
    
        //Metodi joka lisätä tietokannan laskut
    public static void addLasku(Connection c, int laskuID, String erapaiva, String maksupaiva,
                                    int summa, int viitenumero, String laskutusTyyppi)throws SQLException {   
        
        PreparedStatement ps = c.prepareStatement
        (
        "INSERT INTO lasku (laskuID, erapaiva, maksupaiva, summa, viitenumero,laskutusTyyppi)"
         + "VALUES(?, STR_TO_DATE(?, '%d.%m.%Y'), STR_TO_DATE(?, '%d.%m.%Y'), ?, ?, ?)"
        );
        
        ps.setInt(1, laskuID);
        ps.setString(2, erapaiva);
        ps.setString(3, maksupaiva);
        ps.setInt(4, summa);
        ps.setInt(5, viitenumero);
        ps.setString(6, laskutusTyyppi);
            
        ps.execute();       
        System.out.println("\t>> Lisätty lasku: " + laskuID + " " + laskutusTyyppi);
        
    }
    //Metodi joka lisätä tietokannan varaukset 
    public static void addPalvelu(Connection c, int palvelunID, String palvelunNimi,int palvelunHinta)throws SQLException {   
        
        PreparedStatement ps = c.prepareStatement
        (
        "INSERT INTO palvelu (palvelunID, palvelunNimi, palvelunHinta)"
         + "VALUES(?, ?, ?)"
        );
        
        ps.setInt(1, palvelunID);
        ps.setString(2, palvelunNimi);
        ps.setInt(3, palvelunHinta);
            
        ps.execute();       
        System.out.println("\t>> Lisätty palvelu: " + palvelunID + " " + palvelunNimi);
        
    }
    
    
    //Metodi joka lisätä tietokannan varaukset 
    public static void addToimipisteidenPalvelut(Connection c, int toimipisteID, int palvelunID)throws SQLException {   
        
        PreparedStatement ps = c.prepareStatement
        (
        "INSERT INTO toimipisteidenPalvelut(toimipisteID, palvelunID)"
         + "VALUES(?, ?)"
        );
        
        ps.setInt(1, toimipisteID);
        ps.setInt(2, palvelunID);
                  
        ps.execute();       
        System.out.println("\t>> Lisätty toimipisteidenPalvelut: " + palvelunID );       
    }
    
    //Metodi joka lisätä tietokannan laskun maksajat 
    public static void addlaskunMaksaja(Connection c, int asiakasID, int laskuID)throws SQLException {   
        
        PreparedStatement ps = c.prepareStatement
        (
        "INSERT INTO laskunMaksaja(asiakasID, laskuID)"
         + "VALUES(?, ?)"
        );
        
        ps.setInt(1, asiakasID);
        ps.setInt(2, laskuID);
                  
        ps.execute();       
        System.out.println("\t>> Lisätty laskunMaksaja taulu: " + asiakasID + " " + laskuID );       
    }
    
    // Metodi joka lisätä tietokannan laskun maksajat 
    public static void addLaskunKasittelija(Connection c, int tyontekijaID, int laskuID)throws SQLException {   
        
        PreparedStatement ps = c.prepareStatement
        (
        "INSERT INTO laskunKasittelija(tyontekijaID, laskuID)"
         + "VALUES(?, ?)"
        );
        
        ps.setInt(1, tyontekijaID);
        ps.setInt(2, laskuID);
                  
        ps.execute();       
        System.out.println("\t>> Lisätty laskunMaksaja taulu: " + tyontekijaID + " " + laskuID );       
    }
    
    
    
    // Metodi joka lisätä tietokannan laskun maksajat 
    public static void addVarauksenKasittelija(Connection c, int tyontekijaID, int varausID)throws SQLException {   
        
        PreparedStatement ps = c.prepareStatement
        (
        "INSERT INTO varauksenKasittelija(tyontekijaID, varausID)"
         + "VALUES(?, ?)"
        );
        
        ps.setInt(1, tyontekijaID);
        ps.setInt(2, varausID);
                  
        ps.execute();       
        System.out.println("\t>> Lisätty laskunMaksaja taulu: " + tyontekijaID + " " + varausID );       
    }
    
    
    // Metodi joka muokkaa toimipisteen tietoja
    public static void updateToimipiste(Connection c, int toimipisteID, String toimipisteNimi, int vuorokausiHinta, int toimipisteKoko) throws SQLException {
    
        PreparedStatement ps = c.prepareStatement(
        ("UPDATE toimipiste SET toimipisteNimi = ?, vuorokausiHinta = ?, toimipisteKoko = ? WHERE toimipisteID = ?")
        );

        // Syotetaan tiedot parametreilla
        ps.setString(1, toimipisteNimi);
        ps.setInt(2, vuorokausiHinta);
        ps.setInt(3, toimipisteKoko);
        ps.setInt(4, toimipisteID);
   
   
        // Toteutetaan muutokset
        ps.execute();
    
    System.out.println("\t>> Päivitetty asiakasID tiedot: " + toimipisteID);
    
    }
    
    
    
    // Metodi joka poistaa toimipisteen tiedot
    public static void deleteToimipiste(Connection c, int toimipisteID) throws SQLException {
    
        PreparedStatement ps = c.prepareStatement( 
        ("DELETE FROM toimipiste WHERE toimipisteID = ?")           
        );
        
        // Syotetaan tiedot paremetreilla
        ps.setInt(1, toimipisteID);
    
        // Suoritetaan poisto
        ps.execute();
        System.out.println("\t>> poistettu Toimipiste, jonka ID on " + toimipisteID);
   
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
       vuokratoimistoDatabase.useDatabase(conn, "karelia_vuokratoimistot_R01");
        
        
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
        
        //Lasku taulu luonti
        createTable(conn, 
                "CREATE TABLE lasku ("
                        + "laskuID INT NOT NULL PRIMARY KEY," 
                        + "erapaiva DATE NOT NULL,"
                        + "maksupaiva DATE NOT NULL,"
                        + "summa INT NOT NULL,"       
                        + "viitenumero INT NOT NULL,"
                        + "laskutusTyyppi VARCHAR(30) NOT NULL)"
                        ,
                "lasku"
                );
        addLasku(conn, 55025, "12.3.2021", "10.3.2021", 599, 123789, "paperilasku");
        addLasku(conn, 56030, "28.1.2021", "2.1.2020", 1010, 345234, "sähköpostilasku");
        
        
        
        //Palvelu taulu luonti
        createTable(conn, 
                "CREATE TABLE palvelu ("
                        + "palvelunID INT NOT NULL PRIMARY KEY," 
                        + "palvelunNimi VARCHAR(30) NOT NULL,"
                        + "palvelunHinta INT NOT NULL)"        
                        ,
                "palvelu"
                );
        addPalvelu(conn, 1, "Yksilö" , 199);
        addPalvelu(conn, 2, "Ekologi", 299);

        //Varaus taulu luonti
        createTable(conn, 
                "CREATE TABLE varaus ("
                        + "varausID INT NOT NULL PRIMARY KEY," 
                        + "aloitusPaiva DATE NOT NULL,"
                        + "lopetusPaiva DATE NOT NULL,"
                        + "asiakasID INT NOT NULL," 
                        + "toimipisteID INT NOT NULL,"
                        + "FOREIGN KEY (asiakasID) REFERENCES asiakas(asiakasID) ON DELETE CASCADE,"
                        + "FOREIGN KEY (toimipisteID) REFERENCES toimipiste(toimipisteID) ON DELETE CASCADE)"
                        ,
                "varaus"
                );

        addVaraus(conn, 3000, "10.3.2021", "12.3.2021", 1100, 60160);
        addVaraus(conn, 3001, "12.4.2021", "15.4.2021", 1101, 60100);
        
        //ToimipistePalvelu taulu luonti
        createTable(conn, 
                "CREATE TABLE toimipisteidenPalvelut ("
                        + "toimipisteID INT NOT NULL," 
                        + "palvelunID INT NOT NULL,"
                        + "PRIMARY KEY (toimipisteID, palvelunID),"                      
                        + "FOREIGN KEY (toimipisteID) REFERENCES toimipiste(toimipisteID) ON DELETE CASCADE,"
                        + "FOREIGN KEY (palvelunID) REFERENCES palvelu(palvelunID) ON DELETE CASCADE)"
                        ,
                "toimipisteidenPalvelut"
                );
        addToimipisteidenPalvelut(conn, 60160, 1);
        addToimipisteidenPalvelut(conn, 60100, 2);
        
        //LaskunMaksaja taulu luonti
        createTable(conn, 
                "CREATE TABLE laskunMaksaja ("
                        + "asiakasID INT NOT NULL," 
                        + "laskuID INT NOT NULL,"
                        + "PRIMARY KEY (asiakasID, laskuID),"                      
                        + "FOREIGN KEY (asiakasID) REFERENCES asiakas(asiakasID) ON DELETE CASCADE,"
                        + "FOREIGN KEY (laskuID) REFERENCES lasku(laskuID) ON DELETE CASCADE)"
                        ,
                "laskunMaksaja"
                );
        addlaskunMaksaja(conn, 1100, 55025);
        addlaskunMaksaja(conn, 1101, 56030);
        
        //LaskunKasittelija taulu luonti
        createTable(conn, 
                "CREATE TABLE laskunKasittelija ("
                        + "tyontekijaID INT NOT NULL," 
                        + "laskuID INT NOT NULL,"
                        + "PRIMARY KEY (tyontekijaID, laskuID),"                      
                        + "FOREIGN KEY (tyontekijaID) REFERENCES tyontekija(tyontekijaID) ON DELETE CASCADE,"
                        + "FOREIGN KEY (laskuID) REFERENCES lasku(laskuID) ON DELETE CASCADE)"
                        ,
                "laskunKasittelija"
                );
        addLaskunKasittelija(conn, 2200, 55025);
        addLaskunKasittelija(conn, 2201, 56030);
        
        
        //VarauksenKasittelija taulu luonti
        createTable(conn, 
                "CREATE TABLE varauksenKasittelija ("
                        + "tyontekijaID INT NOT NULL," 
                        + "varausID INT NOT NULL,"
                        + "PRIMARY KEY (tyontekijaID, varausID),"                      
                        + "FOREIGN KEY (tyontekijaID) REFERENCES tyontekija(tyontekijaID) ON DELETE CASCADE,"
                        + "FOREIGN KEY (varausID) REFERENCES varaus(varausID) ON DELETE CASCADE)"
                        ,
                "varauksenKasittelija"
                );
        addVarauksenKasittelija(conn, 2200, 3000);
        addVarauksenKasittelija(conn, 2201, 3001);
        
       
    }
}
