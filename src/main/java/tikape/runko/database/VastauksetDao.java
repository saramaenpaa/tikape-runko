
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


    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}