
package tikape.runko.domain;
import java.util.Calendar;
import java.util.Date;


public class Langat {
    private Integer viestiNro;
    private String otsikko;
    private String alue;
    private String aikaleima;

    public Langat(Integer viestiNro, String otsikko, String alue, String aikaleima) {
        this.viestiNro = viestiNro;
        this.otsikko = otsikko;
        this.alue = alue;
        this.aikaleima = aikaleima;
    }

    public Langat(Integer viestiNro, String otsikko, String alue) {
        this.viestiNro = viestiNro;
        this.otsikko = otsikko;
        this.alue = alue;
        this.aikaleima = Calendar.getInstance().getTime().toString();
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
    
    public String getAikaleima() {
        return aikaleima;
    }
    
}
