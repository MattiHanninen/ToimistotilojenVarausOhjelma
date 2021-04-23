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
public class Tyontekija {
    
    private int tyontekijaID;
    private String etunimi;
    private String sukunimi;
    
    public Tyontekija(){
        
    }

    //Setterit ja getterit
    public void setTyontekijaID(int tyontekijaID) {
        this.tyontekijaID = tyontekijaID;
    }

    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }
    
    public int getTyontekijaID() {
        return tyontekijaID;
    }

    public String getEtunimi() {
        return etunimi;
    }

    public String getSukunimi() {
        return sukunimi;
    }

    
    @Override
    public String toString() {
        return (tyontekijaID + " " + etunimi + " " + sukunimi);
    }
    
}
