
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import tikape.runko.domain.Keskustelualue;
import tikape.runko.domain.Langat;

public class LangatDao implements Dao<Langat, Integer> {
    
    private Database database;
    private Keskustelualue keskustelualue;
    private Langat langat;

    public LangatDao(Database database) {
        this.database = database;
    }

    @Override
    public Langat findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Langat WHERE viestiNro = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        
        Integer viestiNro = rs.getInt("viestiNro");
        String otsikko = rs.getString("otsikko");
        Keskustelualue alue = keskustelualue;


        Langat o = new Langat(viestiNro, otsikko, alue);
        
        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Langat> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Langat");

        ResultSet rs = stmt.executeQuery();
        List<Langat> langat = new ArrayList<>();
        while (rs.next()) {
            Integer viestiNro = rs.getInt("viestiNro");
            String otsikko = rs.getString("otsikko");
            Keskustelualue alue = keskustelualue;

            langat.add(new Langat(viestiNro, otsikko, keskustelualue));
        }

        rs.close();
        stmt.close();
        connection.close();

        return langat;
    }


    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Langat> findOne2(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Langat WHERE viestiNro = ?");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        List<Langat> langat = new ArrayList<>();
        while (rs.next()) {
            Integer viestiNro = rs.getInt("viestiNro");
            String otsikko = rs.getString("otsikko");
            Keskustelualue alue = keskustelualue;

            langat.add(new Langat(viestiNro, otsikko, keskustelualue));
        }

        rs.close();
        stmt.close();
        connection.close();

        return langat;
    }
    
    public List<Langat> findAllFrom(String key) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Langat.* FROM Langat, Keskustelualue WHERE Langat.alue=Keskustelualue.alueenNimi AND Keskustelualue.alueenNimi = ?");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        List<Langat> langat = new ArrayList<>();
        while (rs.next()) {
            Integer viestiNro = rs.getInt("viestiNro");
            String otsikko = rs.getString("otsikko");
            Keskustelualue alue = keskustelualue;

            langat.add(new Langat(viestiNro, otsikko, keskustelualue));
        }

        rs.close();
        stmt.close();
        connection.close();

        return langat;
    }

}

