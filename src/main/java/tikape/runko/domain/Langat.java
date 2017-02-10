
package tikape.runko.domain;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Langat {
    private Integer viestiNro;
    private String otsikko;
    private Keskustelualue alue;
    private Date aikaleima;
    private Calendar kalenteri;

    public Langat(Integer viestiNro, String otsikko, Keskustelualue alue, Date aikaleima) {
        this.viestiNro = viestiNro;
        this.otsikko = otsikko;
        this.alue = alue;
        this.aikaleima = aikaleima;
    }

    public Langat(Integer viestiNro, String otsikko, Keskustelualue alue) {
        this.viestiNro = viestiNro;
        this.otsikko = otsikko;
        this.alue = alue;
        Calendar start = Calendar.getInstance();
        this.aikaleima = start.getTime();
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
    
    public Date getAikaleima() {
        
        return aikaleima;
    }
    
}
