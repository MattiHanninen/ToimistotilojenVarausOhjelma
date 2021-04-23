/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuokratoimistotapplication.Luokat;

import java.util.Date;

/**
 *
 * @author hoang & janne
 */
public class Lasku {
    
    private int laskuID;
    private Date erapaiva;
    private Date maksupaiva;
    private int summa;
    private int viitenumero;
    private String laskutusTyyppi;
    
    public Lasku(){
        
    }
    
    //Getterit ja setterit
    public int getLaskuID() {
        return laskuID;
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

    public int getViitenumero() {
        return viitenumero;
    }

    public String getLaskutusTyyppi() {
        return laskutusTyyppi;
    }
    

    public void setLaskuID(int laskuID) {
        this.laskuID = laskuID;
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

    public void setViitenumero(int viitenumero) {
        this.viitenumero = viitenumero;
    }

    public void setLaskutusTyyppi(String laskutusTyyppi) {
        this.laskutusTyyppi = laskutusTyyppi;
    }
    
    @Override
    public String toString() {
        return (laskuID + " " + erapaiva + " " + maksupaiva + " " + summa + " " + viitenumero + " " + laskutusTyyppi);
    }
    
}
