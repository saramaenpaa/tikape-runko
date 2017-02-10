package tikape.runko;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KeskustelualueDao;
import tikape.runko.database.LangatDao;
import tikape.runko.database.OpiskelijaDao;
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
        
        for (Langat e: langatDao.findAll()) {
            System.out.println(e.getViestiNro() + " " + e.getOtsikko() + " " + e.getAikaleima());
           // Tulostaa lankojen nimet + kuvaukset
           // Aikaleima on VÄÄRÄ, tulostaa nykyisen ajan!
        }
        
        
        
        
        for (Keskustelualue e: keskustelualueDao.findAll()) {
            System.out.println(e.getAlueenNimi() + " - " + e.getKuvaus());
 
           // Tulostaa alueiden nimet + kuvaukset
        }
        
        System.out.println("");
          
        for (Vastaukset e : vastauksetDao.findAll()) {
            System.out.println(e.getViestiNro() + " " + e.getTeksti());
            // Tulostaa vastauksien numerot, langat sekä tekstit.
        }
           
           

        //Etusivu: määritellään, että etusivun URL-osoite on palvelimen osoite ja siihen viittaava dokumentti on etusivu.html.
        get("/", (req, res) -> {
            //Luodaan kartta map.
            HashMap map = new HashMap<>();
            //Etusivulla on lista, jonka alkioiden attribuutin th:each arvo on "keskustelualue: ${keskustelualueet}".
            //Liitetään avain "keskustelualueet" List<keskustelualue>-tyypin muuttujaan, joka taas palautetaan keskustelualueDaon metodilla findAll.
            map.put("keskustelualueet", keskustelualueDao.findAll());

            return new ModelAndView(map, "etusivu");//Tämä rivi määrää, mitä html-sivua käytetään etusivuna. Tässä etusivu.html.
        }, new ThymeleafTemplateEngine());

//        get("/opiskelijat", (req, res) -> {
//            HashMap map = new HashMap<>();
////            map.put("opiskelijat", opiskelijaDao.findAll());
//
//            return new ModelAndView(map, "opiskelijat");
//        }, new ThymeleafTemplateEngine());
//
//        get("/opiskelijat/:id", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));
//
//            return new ModelAndView(map, "opiskelija");
////        }, new ThymeleafTemplateEngine());
//        }
    }
}
