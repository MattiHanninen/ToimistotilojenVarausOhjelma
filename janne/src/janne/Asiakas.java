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
public class Asiakas {
    private int AsiakasID;
    private String Etunimi;
    private String Sukunimi;
    private String Yritys;
    
    public Asiakas(){
        
    }

    //Getterit ja setterit
    public void setAsiakasID(int AsiakasID) {
        this.AsiakasID = AsiakasID;
    }

    public void setEtunimi(String Etunimi) {
        this.Etunimi = Etunimi;
    }

    public void setSukunimi(String Sukunimi) {
        this.Sukunimi = Sukunimi;
    }

    public void setYritys(String Yritys) {
        this.Yritys = Yritys;
    }

    
    public int getAsiakasID() {
        return AsiakasID;
    }

    public String getEtunimi() {
        return Etunimi;
    }

    public String getSukunimi() {
        return Sukunimi;
    }

    public String getYritys() {
        return Yritys;
    }
    
    @Override
    public String toString() {
        return (AsiakasID+" "+Etunimi+" "+Sukunimi+" "+Yritys);
    }
            
    
}
