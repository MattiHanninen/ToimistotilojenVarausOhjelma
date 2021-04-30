/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuokratoimistotapplication.Luokat;

import java.sql.Date;

/**
 *
 * @author hoang & janne
 */
public class Lasku {
    
    private int laskuID;
    private int asiakasID;
    private Date erapaiva;
    private Date maksupaiva;
    private int summa;
    private int maksettu;
    private String laskutusTyyppi;
    
    public Lasku(){
        
    }
    
    public Lasku (int laskuID, int asiakasID, Date erapaiva, Date maksupaiva, int summa, int maksettu, String laskutusTyyppi){
        this.laskuID = laskuID;
        this.asiakasID = asiakasID;
        this.erapaiva = erapaiva;
        this.maksupaiva = maksupaiva;
        this.summa = summa;
        this.maksettu = maksettu;
        this.laskutusTyyppi = laskutusTyyppi;
    }
    
    //Getterit ja setterit
    public int getLaskuID() {
        return laskuID;
    }
    
    public int getAsiakasID() {
        return asiakasID;
    }

    public Date getErapaiva() {
        return erapaiva;
    }

    public Date getMaksupaiva() {
        return maksupaiva;
    }

    public int getSumma() {
        return summa;
    }

    public int getMaksettu() {
        return maksettu;
    }

    public String getLaskutusTyyppi() {
        return laskutusTyyppi;
    }
    

    public void setLaskuID(int laskuID) {
        this.laskuID = laskuID;
    }
    
    public void setAsiakasID(int asiakasID) {
        this.asiakasID = asiakasID;
    }

    public void setErapaiva(Date erapaiva) {
        this.erapaiva = erapaiva;
    }

    public void setMaksupaiva(Date maksupaiva) {
        this.maksupaiva = maksupaiva;
    }

    public void setSumma(int summa) {
        this.summa = summa;
    }

    public void setMaksettu(int maksettu) {
        this.maksettu = maksettu;
    }

    public void setLaskutusTyyppi(String laskutusTyyppi) {
        this.laskutusTyyppi = laskutusTyyppi;
    }
    
    @Override
    public String toString() {
        return (laskuID + " " + asiakasID + " " + erapaiva + " " + maksupaiva + " " + summa + " " + maksettu + " " + laskutusTyyppi);
    }
    
}
