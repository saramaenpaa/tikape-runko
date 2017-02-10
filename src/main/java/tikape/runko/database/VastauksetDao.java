
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
        String teksti = rs.getString("teksti");
        Langat lanka = langat;


        Vastaukset o = new Vastaukset(viestiNro, lanka, teksti);
        
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
            String teksti = rs.getString("teksti");
            Langat lanka = langat;

            vastaukset.add(new Vastaukset(viestiNro, lanka, teksti));
        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
    }
    
    public void Aikaleima(Integer key)  throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:foorumi.db");

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT aikaleima FROM Vastaukset WHERE viestiNro =" +key);

        while (rs.next()) {
            Timestamp aikaleima = rs.getTimestamp("aikaleima");

            System.out.println(aikaleima);
        }

        stmt.close();
        rs.close();

        connection.close();
//       Ei toimi
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
