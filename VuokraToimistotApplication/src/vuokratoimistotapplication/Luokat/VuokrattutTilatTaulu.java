/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuokratoimistotapplication.Luokat;

import java.util.Date;

/**
 *
 * @author hoang
 */
public class VuokrattutTilatTaulu {

    

    private String toimipisteNimi;
    private int toimipisteID;
    private Date aloitusPaiva;
    private Date lopetusPaiva;
    private String etunimi;
    private String sukunimi;
    private String yritys;
    private int summa;
    

    public VuokrattutTilatTaulu() {
    }
     
    public VuokrattutTilatTaulu(String toimipisteNimi, int toimipisteID, String sukunimi, String yritys, Date aloitusPaiva, Date lopetusPaiva, int summa) {
        this.toimipisteNimi = toimipisteNimi;
        this.toimipisteID = toimipisteID;
        this.aloitusPaiva = aloitusPaiva;
        this.lopetusPaiva = lopetusPaiva;
        this.sukunimi = sukunimi;
        this.yritys = yritys;
        this.summa = summa;
    }

    public String getToimipisteNimi() {
        return toimipisteNimi;
    }

    public void setToimipisteNimi(String toimipisteNimi) {
        this.toimipisteNimi = toimipisteNimi;
    }

    public int getToimipisteID() {
        return toimipisteID;
    }

    public void setToimipisteID(int toimipisteID) {
        this.toimipisteID = toimipisteID;
    }
    
    public void setSumma(int summa){
    this.summa = summa;
    }

    public Date getAloitusPaiva() {
        return aloitusPaiva;
    }

    public void setAloitusPaiva(Date aloitusPaiva) {
        this.aloitusPaiva = aloitusPaiva;
    }

    public Date getLopetusPaiva() {
        return lopetusPaiva;
    }

    public void setLopetusPaiva(Date lopetusPaiva) {
        this.lopetusPaiva = lopetusPaiva;
    }

    public String getEtunimi() {
        return etunimi;
    }

    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    public String getSukunimi() {
        return sukunimi;
    }

    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }

    public String getYritys() {
        return yritys;
    }

    public void setYritys(String yritys) {
        this.yritys = yritys;
    }

    public int getSumma() {
        return summa;
    }
    
    @Override
    public String toString() {
        return "VuokrattutTilatTaulu {" + " toimipisteNimi=" + toimipisteNimi + ", toimipisteID=" 
                + toimipisteID + ",  aloitusPaiva=" + aloitusPaiva + ", lopetusPaiva=" 
                + lopetusPaiva + ", etunimi=" + etunimi + ", sukunimi=" + sukunimi + ", yritys=" 
                + yritys + ", summa=" + summa + '}';
    }

}
