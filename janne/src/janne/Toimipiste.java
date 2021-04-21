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
public class Toimipiste {
    private int ToimipisteID;
    private String ToimipisteNimi;
    private int VuorokausiHinta;
    private int ToimipisteKoko;
    
    public Toimipiste(){
        
    }

    //Setterit ja getterit
    public void setToimipisteID(int ToimipisteID) {
        this.ToimipisteID = ToimipisteID;
    }

    public void setToimipisteNimi(String ToimipisteNimi) {
        this.ToimipisteNimi = ToimipisteNimi;
    }

    public void setVuorokausiHinta(int VuorokausiHinta) {
        this.VuorokausiHinta = VuorokausiHinta;
    }

    public void setToimipisteKoko(int ToimipisteKoko) {
        this.ToimipisteKoko = ToimipisteKoko;
    }

    public int getToimipisteID() {
        return ToimipisteID;
    }

    public String getToimipisteNimi() {
        return ToimipisteNimi;
    }

    public int getVuorokausiHinta() {
        return VuorokausiHinta;
    }

    public int getToimipisteKoko() {
        return ToimipisteKoko;
    }

    @Override
    public String toString() {
        return (ToimipisteID + " " + ToimipisteNimi + " " + VuorokausiHinta + " " + ToimipisteKoko);
    }
   
    
}
