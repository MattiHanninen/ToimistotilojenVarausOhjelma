/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuokratoimistotapplication.Luokat;

/**
 *
 * @author hoang & janne
 */
public class Palvelu {
    
    private int palvelunID;
    private String palvelunNimi;
    private int palvelunHinta;
    private String palvelunKuvaus;
    
    public Palvelu(){
        
    }
    
    // Konstruktori parametreilla
    public Palvelu(int palvelunID, String palvelunNimi, int palvelunHinta, String palvelunKuvaus) {
        this.palvelunID = palvelunID;
        this.palvelunNimi = palvelunNimi;
        this.palvelunHinta = palvelunHinta;
        this.palvelunKuvaus = palvelunKuvaus;
    }

    //Setterit ja getterit
    public void setPalvelunID(int palvelunID) {
        this.palvelunID = palvelunID;
    }

    public void setPalvelunNimi(String palvelunNimi) {
        this.palvelunNimi = palvelunNimi;
    }

    public void setPalvelunHinta(int palvelunHinta) {
        this.palvelunHinta = palvelunHinta;
    }

    public void setPalvelunKuvaus(String palvelunKuvaus) {
        this.palvelunKuvaus = palvelunKuvaus;
    }
   


    public int getPalvelunID() {
        return palvelunID;
    }

    public String getPalvelunNimi() {
        return palvelunNimi;
    }

    public int getPalvelunHinta() {
        return palvelunHinta;
    }

    public String getPalvelunKuvaus() {
        return palvelunKuvaus;
    }
    
     @Override
    public String toString() {
        return (palvelunID + " " + palvelunNimi + " " + palvelunHinta + " " + palvelunKuvaus);
    }
    
}
