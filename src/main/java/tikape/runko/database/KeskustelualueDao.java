
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Keskustelualue;

public class KeskustelualueDao implements Dao<Keskustelualue, String> {

    private Database database;

    public KeskustelualueDao(Database database) {
        this.database = database;
    }

    @Override
    public Keskustelualue findOne(String key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelualue WHERE alueenNimi = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        
        String nimi = rs.getString("alueenNimi");
        String kuvaus = rs.getString("kuvaus");

        Keskustelualue o = new Keskustelualue(nimi, kuvaus);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Keskustelualue> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelualue");

        ResultSet rs = stmt.executeQuery();
        List<Keskustelualue> keskustelualueet = new ArrayList<>();
        while (rs.next()) {
            String alueenNimi = rs.getString("alueenNimi");
            String kuvaus = rs.getString("kuvaus");

            keskustelualueet.add(new Keskustelualue(alueenNimi, kuvaus));
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelualueet;
    }


    @Override
    public void delete(String key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        
    public List<Keskustelualue> findOne2(String key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelualue WHERE alueenNimi = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        List<Keskustelualue> keskustelualueet = new ArrayList<>();
        while (rs.next()) {
            String alueenNimi = rs.getString("alueenNimi");
            String kuvaus = rs.getString("kuvaus");

            keskustelualueet.add(new Keskustelualue(alueenNimi, kuvaus));
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelualueet;
    }

}

