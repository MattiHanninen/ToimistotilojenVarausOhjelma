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
    private int toimipisteID;
    private String toimipisteNimi;
    private int vuorokausiHinta;
    private int toimipisteKoko;
    
    public Toimipiste(){
        
    }

    //Setterit ja getterit
    public void setToimipisteID(int toimipisteID) {
        this.toimipisteID = toimipisteID;
    }

    public void setToimipisteNimi(String toimipisteNimi) {
        this.toimipisteNimi = toimipisteNimi;
    }

    public void setVuorokausiHinta(int vuorokausiHinta) {
        this.vuorokausiHinta = vuorokausiHinta;
    }

    public void setToimipisteKoko(int toimipisteKoko) {
        this.toimipisteKoko = toimipisteKoko;
    }

    public int getToimipisteID() {
        return toimipisteID;
    }

    public String getToimipisteNimi() {
        return toimipisteNimi;
    }

    public int getVuorokausiHinta() {
        return vuorokausiHinta;
    }

    public int getToimipisteKoko() {
        return toimipisteKoko;
    }

    @Override
    public String toString() {
        return (toimipisteID + " " + toimipisteNimi + " " + vuorokausiHinta + " " + toimipisteKoko);
    }
   
    
}
