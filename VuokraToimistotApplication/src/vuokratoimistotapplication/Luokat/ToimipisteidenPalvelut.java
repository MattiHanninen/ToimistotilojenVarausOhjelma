/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuokratoimistotapplication.Luokat;

/**
 *
 * @author matty
 */
public class ToimipisteidenPalvelut {
 
    private int toimipisteID;
    private int palvelunID;
    private int varausID;
    private int asiakasID;
    
   
    
    public ToimipisteidenPalvelut(){
        
    }

    public ToimipisteidenPalvelut(int toimipisteID, int palvelunID, int varausID, int asiakasID) {
        this.toimipisteID = toimipisteID;
        this.palvelunID = palvelunID;
        this.varausID = varausID;
        this.asiakasID = asiakasID;
    }

    //Setterit ja getterit
    public void setToimipisteID(int toimipisteID) {
        this.toimipisteID = toimipisteID;
    }

    public void setpalvelulnID(int palvelunID) {
        this.palvelunID = palvelunID;
    }

    public void setVarausID(int varausID) {
        this.varausID = varausID;
    }
    
    public void setAsiakasID(int asiakasID) {
        this.asiakasID = asiakasID;
    }
    

    public int getToimipisteID() {
        return toimipisteID;
    }

    public int getPalvelunID() {
        return palvelunID;
    }

    public int getVarausID() {
        return varausID;
    }
    
     public int getAsiakasID() {
        return asiakasID;
    }
 

    

    @Override
    public String toString() {
        return (toimipisteID + " " + palvelunID + " " + varausID + " " + asiakasID);
    }
    
}
