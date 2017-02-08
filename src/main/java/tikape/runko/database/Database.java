package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;
    //dataBaseAddress-merkkijonoon syötetään tietokannan URL-osoite. Tässä "jdbc:sqlite:foorumi.db".

    //konstruktori:
    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    //getConnection hakee yhteyttä tietokannan URL-osoitteeseen.
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    //Init suorittaa ne lauseet, jotka sqliteLauseet-metodi on määrännyt suoritettavaksi.
    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    //Määrittelee ja palauttaa listan kaikista SQL:ään kirjoitettavista lauseista.
    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Keskustelualue (alueenNimi varchar(30) PRIMARY KEY, kuvaus varchar(150));");
        lista.add(
            "CREATE TABLE Langat (\n" +
            "viestiNro integer PRIMARY KEY,\n" +
            "otsikko varchar(30),\n" +
            "alue,\n" +
            "aikaleima timestamp DATE DEFAULT CURRENT_TIMESTAMP,\n" +
            "FOREIGN KEY(alue) REFERENCES Keskustelualue(alueenNimi)\n" +
            ");"
        );
        lista.add(
            "CREATE TABLE Vastaukset (\n" +
            "viestiNro integer PRIMARY KEY,\n" +
            "lanka,\n" +
            "teksti varchar(300),\n" +
            "aikaleima timestamp DATE DEFAULT CURRENT_TIMESTAMP,\n" +
            "FOREIGN KEY (lanka) REFERENCES Langat(viestiNro)\n" +
            ");"
        );

        return lista;
    }
}
