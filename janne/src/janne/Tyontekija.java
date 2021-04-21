/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package janne;

/**
 *
 * @author Omistaja
 */
public class Tyontekija {
    private int TyontekijaID;
    private String Etunimi;
    private String Sukunimi;
    
    public Tyontekija(){
        
    }

    //Setterit ja getterit
    public void setTyontekijaID(int TyontekijaID) {
        this.TyontekijaID = TyontekijaID;
    }

    public void setEtunimi(String Etunimi) {
        this.Etunimi = Etunimi;
    }

    public void setSukunimi(String Sukunimi) {
        this.Sukunimi = Sukunimi;
    }
    
    public int getTyontekijaID() {
        return TyontekijaID;
    }

    public String getEtunimi() {
        return Etunimi;
    }

    public String getSukunimi() {
        return Sukunimi;
    }

    
    @Override
    public String toString() {
        return (TyontekijaID + " " + Etunimi + " " + Sukunimi);
    }
}
