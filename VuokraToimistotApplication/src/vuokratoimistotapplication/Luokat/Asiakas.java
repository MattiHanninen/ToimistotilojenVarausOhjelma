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

public class Asiakas {
    private int asiakasID;
    private String etunimi;
    private String sukunimi;
    private String yritys;
    
    public Asiakas(){
        
    }
    
    public Asiakas(int asiakasID, String etunimi, String sukunimi, String yritys){
        this.asiakasID=asiakasID;
        this.etunimi=etunimi;
        this.sukunimi=sukunimi;
        this.yritys=yritys;
    }
    


    //Getterit ja setterit
    public void setAsiakasID(int asiakasID) {
        this.asiakasID = asiakasID;
    }

    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }

    public void setYritys(String yritys) {
        this.yritys = yritys;
    }

    
    public int getAsiakasID() {
        return asiakasID;
    }

    public String getEtunimi() {
        return etunimi;
    }

    public String getSukunimi() {
        return sukunimi;
    }

    public String getYritys() {
        return yritys;
    }
    
    @Override
    public String toString() {
        return (asiakasID+" "+etunimi+" "+sukunimi+" "+yritys);
    }
            
    
}