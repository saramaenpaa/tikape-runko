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
        
        System.out.println(vastauksetDao.viestienMaaraFrom("Kissat"));
        System.out.println(vastauksetDao.viestienMaara());
        

        //Etusivu: määritellään, että etusivun URL-osoite on palvelimen osoite ja siihen viittaava dokumentti on etusivu.html.
        get("/", (req, res) -> {
            //Luodaan kartta map.
            HashMap map = new HashMap<>();
            //Etusivulla on lista, jonka alkioiden attribuutin th:each arvo on "keskustelualue: ${keskustelualueet}".
            //Liitetään avain "keskustelualueet" List<keskustelualue>-tyypin muuttujaan, joka taas palautetaan keskustelualueDaon metodilla findAll.
            map.put("keskustelualueet", keskustelualueDao.findAll());
//            map.put("viestitAlueittain", vastauksetDao.viestienMaara());

            return new ModelAndView(map, "etusivu");//Tämä rivi määrää, mitä html-sivua käytetään etusivuna. Tässä etusivu.html.
        }, new ThymeleafTemplateEngine());
        
        get("/a/:alueenNimi", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("omaNimi", keskustelualueDao.findOne2(req.params("alueenNimi")).get(0));
            map.put("langat", langatDao.findAllFrom(req.params("alueenNimi")));
            return new ModelAndView(map, "keskustelualue");
         }, new ThymeleafTemplateEngine());
        
        get("/l/:viestiNro", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("omaNimi", langatDao.findOne2(Integer.parseInt(req.params("viestiNro"))).get(0));
            map.put("vastaukset", vastauksetDao.findAllFrom(Integer.parseInt(req.params("viestiNro"))));
            return new ModelAndView(map, "langat");
        }, new ThymeleafTemplateEngine());

    }
}
