
package tikape.runko.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import tikape.runko.domain.Keskustelualue;
import tikape.runko.domain.Langat;
import tikape.runko.domain.Vastaukset;

public class VastauksetDao implements Dao<Vastaukset, Integer> {
    
    private Database database;
    private Keskustelualue alue;
    private Langat langat;

    public VastauksetDao(Database database) {
        this.database = database;
    }

    @Override
    public Vastaukset findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaukset WHERE viestiNro = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        
        Integer viestiNro = rs.getInt("viestiNro");
        Integer lanka = rs.getInt("lanka");
        String teksti = rs.getString("teksti");
        String aikaleima = rs.getString("aikaleima");


        Vastaukset o = new Vastaukset(viestiNro, lanka, teksti, aikaleima);
        
        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Vastaukset> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaukset");

        ResultSet rs = stmt.executeQuery();
        List<Vastaukset> vastaukset = new ArrayList<>();
        while (rs.next()) {
            Integer viestiNro = rs.getInt("viestiNro");
            Integer lanka = rs.getInt("lanka");
            String teksti = rs.getString("teksti");
            String aikaleima = rs.getString("aikaleima");
            
            vastaukset.add(new Vastaukset(viestiNro, lanka, teksti, aikaleima));
        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Vastaukset> findOne2(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaukset WHERE viestiNro = ?");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        List<Vastaukset> vastaukset = new ArrayList<>();
        while (rs.next()) {
            Integer viestiNro = rs.getInt("viestiNro");
            Integer lanka = rs.getInt("lanka");
            String teksti = rs.getString("teksti");

            vastaukset.add(new Vastaukset(viestiNro, lanka, teksti));
        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
    }
    
    public List<Vastaukset> findAllFrom(Integer key) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Vastaukset.* FROM Vastaukset, Langat WHERE Vastaukset.lanka=Langat.viestiNro AND Langat.viestiNro = ?");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        List<Vastaukset> vastaukset = new ArrayList<>();
        while (rs.next()) {
            Integer viestiNro = rs.getInt("viestiNro");
            Integer lanka = rs.getInt("lanka");
            String teksti = rs.getString("teksti");
            String aikaleima = rs.getString("aikaleima");

            vastaukset.add(new Vastaukset(viestiNro, lanka, teksti, aikaleima));
        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
    }
    
    public List<Integer> viestienMaara() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Keskustelualue.alueenNimi, COUNT(Vastaukset.viestiNro) AS total FROM Vastaukset, Langat, Keskustelualue WHERE Vastaukset.lanka = Langat.viestiNro AND Langat.alue = Keskustelualue.alueenNimi GROUP BY Keskustelualue.alueenNimi");
        
        ResultSet rs = stmt.executeQuery();
        List<Integer> lista = new ArrayList<>();
        while (rs.next()) {
            Integer montako = rs.getInt("total");
            lista.add(montako);
        }

        rs.close();
        stmt.close();
        connection.close();

        return lista;
    }    
    
    public Integer viestienMaaraFrom(String key) throws SQLException {
        // Laskee vastauksien määrät tietyssä langassa
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) AS total FROM Vastaukset, Langat WHERE Vastaukset.lanka = Langat.viestiNro AND Langat.alue = ?");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        List<Integer> lista = new ArrayList<>();
        while (rs.next()) {
            Integer montako = rs.getInt("total");
            lista.add(montako);
        }

        rs.close();
        stmt.close();
        connection.close();

        return lista.get(0);
    }    
    
    public void lisaa(String lanka, String vastaus) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:foorumi.db");
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vastaukset (lanka, vastaus) "
                + "VALUES (?, ?)");
        stmt.setString(1, lanka);
        stmt.setString(2, vastaus);
        stmt.execute();

        conn.close();

    }
     


}
