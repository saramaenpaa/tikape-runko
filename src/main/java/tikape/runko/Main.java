package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KeskustelualueDao;
import tikape.runko.database.LangatDao;
import tikape.runko.database.VastauksetDao;
import tikape.runko.domain.Keskustelualue;
import tikape.runko.domain.Langat;
import tikape.runko.domain.Vastaukset;

public class Main {
    // Ensimmäinen muokkaus. Huimaa! t:Juho
    // Toinen muokkaus. Hurraa! t. Sara
    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();

        KeskustelualueDao keskustelualueDao = new KeskustelualueDao(database);
        LangatDao langatDao = new LangatDao(database);
        VastauksetDao vastauksetDao = new VastauksetDao(database);

        //Etusivu: määritellään, että etusivun URL-osoite on palvelimen osoite.
        get("/", (req, res) -> {
            //Luodaan kartta map.
            HashMap map = new HashMap<>();
            //Lisätään karttaan avaimella "keskustelualueet" lista kaikista keskustelualueista.
            map.put("keskustelualueet", keskustelualueDao.findAll());

            return new ModelAndView(map, "etusivu");//Tämä rivi määrää, mitä html-sivua käytetään etusivuna. Tässä etusivu.html.
        }, new ThymeleafTemplateEngine());
        
        //Alueiden sivujen osoitteet ovat muotoa /a/:alueenNimi."
        get("/a/:alueenNimi", (req, res) -> {
            HashMap map = new HashMap<>();
            //Alueiden omissa kartoissa on tiedot paitsi alueen omasta nimestä sekä siihen liittyvistä langoista.
            map.put("omaNimi", keskustelualueDao.findOne2(req.params("alueenNimi")).get(0));
            map.put("langat", langatDao.findAllFrom(req.params("alueenNimi")));
            return new ModelAndView(map, "keskustelualue");//Sivun html-tiedosto on keskustelualue.html.
         }, new ThymeleafTemplateEngine());
        
        //Lankojen sivujen osoitteet ovat muotoa /l/:viestiNro.
        get("/l/:viestiNro", (req, res) -> {
            HashMap map = new HashMap<>();
            //Langan kartassa on tiedot langan omasta nimestä sekä siihen liittyvistä vastauksista.
            map.put("omaNimi", langatDao.findOne2(Integer.parseInt(req.params("viestiNro"))).get(0));
            map.put("vastaukset", vastauksetDao.findAllFrom(Integer.parseInt(req.params("viestiNro"))));
            return new ModelAndView(map, "langat");//Sivun html-tiedosto on langat.html.
        }, new ThymeleafTemplateEngine());

    }
}
