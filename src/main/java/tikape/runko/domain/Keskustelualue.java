
package tikape.runko.domain;


public class Keskustelualue {
    
    private String alueenNimi;
    private String kuvaus;

    public Keskustelualue(String alueenNimi, String kuvaus) {
        this.alueenNimi = alueenNimi;
        this.kuvaus = kuvaus;
    }

    public void setAlueenNimi(String alueenNimi) {
        this.alueenNimi = alueenNimi;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    public String getAlueenNimi() {
        return alueenNimi;
    }

    public String getKuvaus() {
        return kuvaus;
    }
    
    
}
