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
public class Varaus {
    
    private int varausID;
    private Date aloitusPaiva;
    private Date lopetusPaiva;
    private int asiakasID;
    private int toimipisteID;

    public Varaus(int varausID, Date aloitusPaiva, Date lopetusPaiva, int asiakasID, int toimipisteID) {
        this.varausID = varausID;
        this.aloitusPaiva = aloitusPaiva;
        this.lopetusPaiva = lopetusPaiva;
        this.asiakasID = asiakasID;
        this.toimipisteID = toimipisteID;
    }
    
    public Varaus(){
        
    }
    
    

    //Setterit ja getterit
    public void setVarausID(int varausID) {
        this.varausID = varausID;
    }

    public void setAloitusPaiva(Date aloitusPaiva) {
        this.aloitusPaiva = aloitusPaiva;
    }

    public void setLopetusPaiva(Date lopetusPaiva) {
        this.lopetusPaiva = lopetusPaiva;
    }

    public void setAsiakasID(int asiakasID) {
        this.asiakasID = asiakasID;
    }

    public void setToimipisteID(int toimipisteID) {
        this.toimipisteID = toimipisteID;
    }
   

    public int getVarausID() {
        return varausID;
    }

    public Date getAloitusPaiva() {
        return aloitusPaiva;
    }

    public Date getLopetusPaiva() {
        return lopetusPaiva;
    }

    public int getAsiakasID() {
        return asiakasID;
    }

    public int getToimipisteID() {
        return toimipisteID;
    }
    
    @Override
    public String toString() {
        return (varausID + " " + aloitusPaiva + " " + lopetusPaiva + " " + asiakasID + " " + toimipisteID);
    }
    
}
