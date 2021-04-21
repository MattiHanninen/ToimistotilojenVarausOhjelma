/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package janne;

import java.util.Date;

/**
 *
 * @author R01
 */
public class Lasku {
    private int LaskuID;
    private Date Erapaiva;
    private Date Maksupaiva;
    private int Summa;
    private int Viitenumero;
    private String LaskutusTyyppi;
    
    public Lasku(){
        
    }
    
    //Getterit ja setterit
    public int getLaskuID() {
        return LaskuID;
    }

    public Date getErapaiva() {
        return Erapaiva;
    }

    public Date getMaksupaiva() {
        return Maksupaiva;
    }

    public int getSumma() {
        return Summa;
    }

    public int getViitenumero() {
        return Viitenumero;
    }

    public String getLaskutusTyyppi() {
        return LaskutusTyyppi;
    }
    

    public void setLaskuID(int LaskuID) {
        this.LaskuID = LaskuID;
    }

    public void setErapaiva(Date Erapaiva) {
        this.Erapaiva = Erapaiva;
    }

    public void setMaksupaiva(Date Maksupaiva) {
        this.Maksupaiva = Maksupaiva;
    }

    public void setSumma(int Summa) {
        this.Summa = Summa;
    }

    public void setViitenumero(int Viitenumero) {
        this.Viitenumero = Viitenumero;
    }

    public void setLaskutusTyyppi(String LaskutusTyyppi) {
        this.LaskutusTyyppi = LaskutusTyyppi;
    }
    
    @Override
    public String toString() {
        return (LaskuID + " " + Erapaiva + " " + Maksupaiva + " " + Summa + " " + Viitenumero + " " + LaskutusTyyppi);
    }
    
}
