/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package janne;

/**
 *
 * @author R01
 * @version 1.0 21.4.2021
 */
public class Palvelu {
    private int PalvelunID;
    private String PalvelunNimi;
    private int PalvelunHinta;
    private String PalvelunKuvaus;
    
    public Palvelu(){
        
    }

    //Setterit ja getterit
    public void setPalvelunID(int PalvelunID) {
        this.PalvelunID = PalvelunID;
    }

    public void setPalvelunNimi(String PalvelunNimi) {
        this.PalvelunNimi = PalvelunNimi;
    }

    public void setPalvelunHinta(int PalvelunHinta) {
        this.PalvelunHinta = PalvelunHinta;
    }

    public void setPalvelunKuvaus(String PalvelunKuvaus) {
        this.PalvelunKuvaus = PalvelunKuvaus;
    }
   


    public int getPalvelunID() {
        return PalvelunID;
    }

    public String getPalvelunNimi() {
        return PalvelunNimi;
    }

    public int getPalvelunHinta() {
        return PalvelunHinta;
    }

    public String getPalvelunKuvaus() {
        return PalvelunKuvaus;
    }
    
     @Override
    public String toString() {
        return (PalvelunID + " " + PalvelunNimi + " " + PalvelunHinta + " " + PalvelunKuvaus);
    }
}
