
package tikape.runko.domain;

import java.util.Calendar;

public class Vastaukset {
    private Integer viestiNro;
    private Langat lanka;
    private String teksti;
    private Calendar aikaleima;

    public Vastaukset(Integer viestiNro, Langat lanka, String teksti, Calendar aikaleima) {
        this.viestiNro = viestiNro;
        this.lanka = lanka;
        this.teksti = teksti;
        this.aikaleima = aikaleima;
    }
    
    public Vastaukset(Integer viestiNro, Langat lanka, String teksti) {
        this.viestiNro = viestiNro;
        this.lanka = lanka;
        this.teksti = teksti;
        this.aikaleima = Calendar.getInstance();
    }

    public Langat getLanka() {
        return lanka;
    }

    public String getTeksti() {
        return teksti;
    }

    public Integer getViestiNro() {
        return viestiNro;
    }

    public void setLanka(Langat lanka) {
        this.lanka = lanka;
    }

    public void setTeksti(String teksti) {
        this.teksti = teksti;
    }

    public void setViestiNro(Integer viestiNro) {
        this.viestiNro = viestiNro;
    }
    
    public Calendar getAikaleima() {
        return aikaleima;
        //Tulostaa aikaleiman väärin
    }
    
    
}
