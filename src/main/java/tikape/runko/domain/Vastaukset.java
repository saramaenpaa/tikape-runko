
package tikape.runko.domain;

import java.util.Calendar;

public class Vastaukset {
    private Integer viestiNro;
    private Integer lanka;
    private String teksti;
    private String aikaleima;

    public Vastaukset(Integer viestiNro, Integer lanka, String teksti, String aikaleima) {
        this.viestiNro = viestiNro;
        this.lanka = lanka;
        this.teksti = teksti;
        this.aikaleima = aikaleima;
    }
    
    public Vastaukset(Integer viestiNro, Integer lanka, String teksti) {
        this.viestiNro = viestiNro;
        this.lanka = lanka;
        this.teksti = teksti;
        this.aikaleima = Calendar.getInstance().getTime().toString();
    }

    public Integer getLanka() {
        return lanka;
    }

    public String getTeksti() {
        return teksti;
    }

    public Integer getViestiNro() {
        return viestiNro;
    }

    public void setLanka(Integer lanka) {
        this.lanka = lanka;
    }

    public void setTeksti(String teksti) {
        this.teksti = teksti;
    }

    public void setViestiNro(Integer viestiNro) {
        this.viestiNro = viestiNro;
    }
    
    public String getAikaleima() {
        return aikaleima;
    }

    
}
