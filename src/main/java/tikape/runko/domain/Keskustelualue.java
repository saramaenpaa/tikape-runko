package tikape.runko.domain;

public class Keskustelualue {

    private String alueenNimi;
    private String kuvaus;
    private String viestiLkm;
    private String viimeisinViesti;

    public Keskustelualue(String alueenNimi, String kuvaus, String viestiLkm, String viimeisinViesti) {
        this.alueenNimi = alueenNimi;
        this.kuvaus = kuvaus;
        this.viestiLkm = viestiLkm;
        this.viimeisinViesti = viimeisinViesti;
    }

    public Keskustelualue(String alueenNimi, String kuvaus) {
        this.alueenNimi = alueenNimi;
        this.kuvaus = kuvaus;
        this.viestiLkm = "0";
        this.viimeisinViesti = "0000";
    }

    //Setterit.
    public void setAlueenNimi(String alueenNimi) {
        this.alueenNimi = alueenNimi;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    public void setViestiLkm(String viestiLkm) {
        this.viestiLkm = viestiLkm;
    }

    public void setViimeisinViesti(String viimeisinViesti) {
        this.viimeisinViesti = viimeisinViesti;
    }

    //Getterit.
    public String getAlueenNimi() {
        return alueenNimi;
    }

    public String getKuvaus() {
        return kuvaus;
    }

    public String getViestiLkm() {
        return viestiLkm;
    }

    public String getViimeisinViesti() {
        return viimeisinViesti;
    }

    @Override
    public String toString() {
        return this.alueenNimi + ": " + this.kuvaus + ", " + this.viestiLkm + " viestiä, viimeisin viesti lähetetty " + this.viimeisinViesti;
    }

}
