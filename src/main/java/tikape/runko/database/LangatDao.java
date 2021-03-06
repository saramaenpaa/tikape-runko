package tikape.runko.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
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
        String alue = rs.getString("alue");
        String aikaleima = rs.getString("aikaleima");

        Langat o = new Langat(viestiNro, otsikko, alue, aikaleima);

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
            String alue = rs.getString("alue");
            String aikaleima = rs.getString("aikaleima");

            langat.add(new Langat(viestiNro, otsikko, alue, aikaleima));
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
            String alue = rs.getString("alue");
            String aikaleima = rs.getString("aikaleima");

            langat.add(new Langat(viestiNro, otsikko, alue, aikaleima));
        }

        rs.close();
        stmt.close();
        connection.close();

        return langat;
    }

    public List<Langat> findAllFrom(String key) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT Langat.* FROM Langat, Keskustelualue "
                + "WHERE Langat.alue=Keskustelualue.alueenNimi AND Keskustelualue.alueenNimi = ? "
                + "ORDER BY Langat.aikaleima DESC"
                + ";"
        );
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        List<Langat> langat = new ArrayList<>();
        while (rs.next()) {
            Integer viestiNro = rs.getInt("viestiNro");
            String otsikko = rs.getString("otsikko");
            String alue = rs.getString("alue");
            String aikaleima = rs.getString("aikaleima");

            langat.add(new Langat(viestiNro, otsikko, alue, aikaleima));
        }

        rs.close();
        stmt.close();
        connection.close();

        return langat;
    }

    public Integer lankojenMaaraFrom(String key) throws SQLException {
        // Laskee lankojen määrät tietyssä alueessa, ei kuitenkaan laske kaikki vastaukset
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) AS total FROM Langat WHERE Langat.alue = ?");
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

    public void lisaa(String otsikko, String alue) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:foorumi.db");
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Langat (otsikko, alue) "
                + "VALUES (?, ?)");
        stmt.setString(1, otsikko);
        stmt.setString(2, "" + alue);
        stmt.execute();

        conn.close();

    }

}
