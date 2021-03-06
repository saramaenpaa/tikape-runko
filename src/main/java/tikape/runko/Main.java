package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KeskustelualueDao;
import tikape.runko.database.LangatDao;
import tikape.runko.database.VastauksetDao;

public class Main {

    public static void main(String[] args) throws Exception {
//        Database database = new Database("jdbc:sqlite:foorumi.db");

        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        
        String jdbcOsoite = "jdbc:sqlite:foorumi.db";
            // jos heroku antaa käyttöömme tietokantaosoitteen, otetaan se käyttöön
            if (System.getenv("http://immense-taiga-31363.herokuapp.com") != null) {
                jdbcOsoite = System.getenv("http://immense-taiga-31363.herokuapp.com");
                //http://immense-taiga-31363.herokuapp.com/
            } 

        Database database = new Database(jdbcOsoite);

        KeskustelualueDao keskustelualueDao = new KeskustelualueDao(database);
        LangatDao langatDao = new LangatDao(database);
        VastauksetDao vastauksetDao = new VastauksetDao(database);
        

        //Etusivu: määritellään, että etusivun URL-osoite on palvelimen osoite.
        get("/", (req, res) -> {
            //Luodaan kartta map.
            HashMap map = new HashMap<>();
            //Lisätään karttaan avaimella "keskustelualueet" lista kaikista keskustelualueista.
            map.put("keskustelualueet", keskustelualueDao.findAllPlusViestimaaratPlusViimeisinVastaus());
            map.put("viestimaarat", vastauksetDao.viestienMaara());

            return new ModelAndView(map, "etusivu");//Tämä rivi määrää, mitä html-sivua käytetään etusivuna. Tässä etusivu.html.
        }, new ThymeleafTemplateEngine());

        post("/", (req, res) -> {
            String alue = req.queryParams("alue");
            String kuvaus = req.queryParams("kuvaus");
            keskustelualueDao.lisaa(alue, kuvaus);
            res.redirect("/");
            return "ok";
        });

        //Alueiden sivujen osoitteet ovat muotoa /a/:alueenNimi."
        get("/a/:alueenNimi", (req, res) -> {
            HashMap map = new HashMap<>();
            //Alueiden omissa kartoissa on tiedot paitsi alueen omasta nimestä sekä siihen liittyvistä langoista.
            map.put("omaNimi", keskustelualueDao.findOne2(req.params("alueenNimi")).get(0));
            map.put("langat", langatDao.findAllFrom(req.params("alueenNimi")));
            
            return new ModelAndView(map, "keskustelualue");//Sivun html-tiedosto on keskustelualue.html.
        }, new ThymeleafTemplateEngine());

        post("/a/:alueenNimi", (req, res) -> {
            String vastaus = req.queryParams("Vastaus");
            langatDao.lisaa(vastaus, req.params(":alueenNimi"));
            res.redirect("/a/"+req.params(":alueenNimi"));
            return "ok";
        });
        //Lankojen sivujen osoitteet ovat muotoa /l/:viestiNro.
        get("/l/:viestiNro", (req, res) -> {
            HashMap map = new HashMap<>();
            //Langan kartassa on tiedot langan omasta nimestä sekä siihen liittyvistä vastauksista.
            map.put("omaNimi", langatDao.findOne2(Integer.parseInt(req.params("viestiNro"))).get(0));
            map.put("vastaukset", vastauksetDao.findAllFrom(Integer.parseInt(req.params("viestiNro"))));
            return new ModelAndView(map, "langat");//Sivun html-tiedosto on langat.html.
        }, new ThymeleafTemplateEngine());
        
        post("/l/:viestiNro", (req, res) -> {
            String vastaus = req.queryParams("teksti");
            vastauksetDao.lisaa(vastaus, Integer.parseInt(req.params(":viestiNro")));
            res.redirect("/l/"+req.params(":viestiNro"));
            return "ok";
        });

    }
}
