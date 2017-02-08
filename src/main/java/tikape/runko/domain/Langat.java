
package tikape.runko.domain;
import java.util.Calendar;


public class Langat {
    private Integer viestiNro;
    private String otsikko;
    private Keskustelualue alue;
    private Calendar aikaleima;

    public Langat(Integer viestiNro, String otsikko, Keskustelualue alue, Calendar aikaleima) {
        this.viestiNro = viestiNro;
        this.otsikko = otsikko;
        this.alue = alue;
        this.aikaleima = aikaleima;
    }

    public Langat(Integer viestiNro, String otsikko, Keskustelualue alue) {
        this.viestiNro = viestiNro;
        this.otsikko = otsikko;
        this.alue = alue;
        this.aikaleima = Calendar.getInstance();
    }

    public String getOtsikko() {
        return otsikko;
    }

    public Integer getViestiNro() {
        return viestiNro;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    public void setViestiNro(Integer viestiNro) {
        this.viestiNro = viestiNro;
    }
    
    
    
}
