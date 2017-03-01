package tikape.runko.database;

import java.sql.Connection;
import java.sql.DriverManager;
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

    public List<Keskustelualue> findAllFrom(String key) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Langat WHERE alueenNimi.Keskustelualueet = ?");
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

    public Integer viestienMaara() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) AS total FROM Keskustelualue");

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

    //Palauttaa listan muotoa "alueenNimi, kuvaus, vastausten lukumäärä, viimeisin vastaus".
    public List<String[]> findAllPlusViestimaaratPlusViimeisinVastaus() throws SQLException {
        Connection conn = this.database.getConnection();

        //Metodin sydän.
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT Keskustelualue.*, Vastaukset.COUNT(*), Vastaukset.MAX(aikaleima)"
                + "FROM Keskustelualue"
                + "LEFT JOIN Langat ON Keskustelualue.alueenNimi=Langat.alue"
                + "LEFT JOIN Vastaukset ON Langat.viestiNro=Vastaukset.lanka"
                + "GROUP BY Keskustelualue.alueenNimi");

        ResultSet rs = stmt.executeQuery();
        
        List<String[]> alueetPlusVLkmPlusViimV = new ArrayList<>();
        
        while (rs.next()) {
            String[] arvo = new String[4];
            arvo[0] = rs.getString("alueenNimi");
            arvo[1] = rs.getString("kuvaus");
            arvo[2] = rs.getString("COUNT(Vastaukset.viestiNro)");
            arvo[3] = rs.getString("MAX(Vastaukset.aikaleima)");

            alueetPlusVLkmPlusViimV.add(arvo);
        }

        rs.close();
        stmt.close();
        conn.close();

        return alueetPlusVLkmPlusViimV;
    }

    public void lisaa(String alueenNimi) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:foorumi.db");
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Keskustelualue (alueenNimi) "
                + "VALUES (?)");
        stmt.setString(1, alueenNimi);
        stmt.execute();

        conn.close();

    }

    public void lisaa(String alueenNimi, String kuvaus) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:foorumi.db");
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Keskustelualue (alueenNimi, kuvaus) "
                + "VALUES (?, ?)");
        stmt.setString(1, alueenNimi);
        stmt.setString(2, kuvaus);
        stmt.execute();

        conn.close();

    }

}
