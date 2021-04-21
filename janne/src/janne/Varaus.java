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
 * @version 1.0 21.4.2021
 */
public class Varaus {
    private int VarausID;
    private Date AloitusPaiva;
    private Date LopetusPaiva;
    private int AsiakasID;
    private int ToimipisteID;
    
    public Varaus(){
        
    }
    

    //Setterit ja getterit
    public void setVarausID(int VarausID) {
        this.VarausID = VarausID;
    }

    public void setAloitusPaiva(Date AloitusPaiva) {
        this.AloitusPaiva = AloitusPaiva;
    }

    public void setLopetusPaiva(Date LopetusPaiva) {
        this.LopetusPaiva = LopetusPaiva;
    }

    public void setAsiakasID(int AsiakasID) {
        this.AsiakasID = AsiakasID;
    }

    public void setToimipisteID(int ToimipisteID) {
        this.ToimipisteID = ToimipisteID;
    }
   

    public int getVarausID() {
        return VarausID;
    }

    public Date getAloitusPaiva() {
        return AloitusPaiva;
    }

    public Date getLopetusPaiva() {
        return LopetusPaiva;
    }

    public int getAsiakasID() {
        return AsiakasID;
    }

    public int getToimipisteID() {
        return ToimipisteID;
    }
    
    @Override
    public String toString() {
        return "Varaus{" + "VarausID=" + VarausID + ", AloitusPaiva=" + AloitusPaiva + ", LopetusPaiva=" + LopetusPaiva + ", AsiakasID=" + AsiakasID + ", ToimipisteID=" + ToimipisteID + '}';
    }
}
