package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KeskustelualueDao;
import tikape.runko.database.LangatDao;
import tikape.runko.database.OpiskelijaDao;
import tikape.runko.domain.Keskustelualue;
import tikape.runko.domain.Langat;

public class Main {
    // Ensimmäinen muokkaus. Huimaa! t:Juho
    // Toinen muokkaus. Hurraa! t. Sara
    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();

        KeskustelualueDao keskustelualueDao = new KeskustelualueDao(database);
        
        LangatDao langatDao = new LangatDao(database);
        
        for (Langat e: langatDao.findAll()) {
            System.out.println(e.getViestiNro() + " " + e.getOtsikko());
           // Tulostaa lankojen nimet + kuvaukset
        }
        
        for (Keskustelualue e: keskustelualueDao.findAll()) {
            System.out.println(e.getAlueenNimi() + " - " + e.getKuvaus());
           // Tulostaa alueiden nimet + kuvaukset


//        get("/", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("viesti", "tervehdys");
//
//            return new ModelAndView(map, "index");
//        }, new ThymeleafTemplateEngine());
//
//        get("/opiskelijat", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelijat", opiskelijaDao.findAll());
//
//            return new ModelAndView(map, "opiskelijat");
//        }, new ThymeleafTemplateEngine());
//
//        get("/opiskelijat/:id", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));
//
//            return new ModelAndView(map, "opiskelija");
//        }, new ThymeleafTemplateEngine());
        }
    }
}
