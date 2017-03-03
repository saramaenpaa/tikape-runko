package tikape.runko.database;

import java.net.URI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;
    //dataBaseAddress-merkkijonoon syötetään tietokannan URL-osoite. Tässä "jdbc:sqlite:foorumi.db".

    //konstruktori:
    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
        
        init();
    }

    

    private void init() {
        List<String> lauseet = null;
        if (this.databaseAddress.contains("postgres")) {
            lauseet = postgreLauseet();
        } else {
            lauseet = sqliteLauseet();
        }

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
    
        //getConnection hakee yhteyttä tietokannan URL-osoitteeseen.
//    public Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(databaseAddress);
//    }
        
    public Connection getConnection() throws SQLException {
        if (this.databaseAddress.contains("postgres")) {
            try {
                URI dbUri = new URI(databaseAddress);

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                return DriverManager.getConnection(dbUrl, username, password);
            } catch (Throwable t) {
                System.out.println("Error: " + t.getMessage());
                t.printStackTrace();
            }
        }

        return DriverManager.getConnection(databaseAddress);
    }
    
    //Määrittelee ja palauttaa listan kaikista SQL:ään kirjoitettavista lauseista.
    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

//            lista.add("CREATE TABLE Keskustelualue (alueenNimi varchar(30) PRIMARY KEY, kuvaus varchar(150));");
//            lista.add("CREATE TABLE Langat (viestiNro integer PRIMARY KEY, otsikko varchar(30), alue, aikaleima timestamp DATE DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY(alue) REFERENCES Keskustelualue(alueenNimi);");
//            lista.add("CREATE TABLE Vastaukset (viestiNro integer PRIMARY KEY, lanka, teksti varchar(300), aikaleima timestamp DATE DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (lanka) REFERENCES Langat(viestiNro);");
//
//
//            lista.add("CREATE TABLE Opiskelija (id integer SERIAL PRIMARY KEY, nimi varchar(255));");
//            lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Platon');");
//            lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Aristoteles');");
//            lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Homeros');");
//
//            lista.add("CREATE TABLE Keskustelualue (alueenNimi varchar(30) SERIAL PRIMARY KEY, kuvaus varchar(150));");
//            lista.add(
//                "CREATE TABLE Langat (\n" +
//                "viestiNro integer SERIAL PRIMARY KEY,\n" +
//                "otsikko varchar(30),\n" +
//                "alue,\n" +
//                "aikaleima timestamp DATE DEFAULT CURRENT_TIMESTAMP,\n" +
//                "FOREIGN KEY(alue) REFERENCES Keskustelualue(alueenNimi)\n" +
//                ");"
//            );
//            lista.add(
//                "CREATE TABLE Vastaukset (\n" +
//                "viestiNro integer SERIAL PRIMARY KEY,\n" +
//                "lanka,\n" +
//                "teksti varchar(300),\n" +
//                "aikaleima timestamp DATE DEFAULT CURRENT_TIMESTAMP,\n" +
//                "FOREIGN KEY (lanka) REFERENCES Langat(viestiNro)\n" +
//                ");"
//            );
//
//        lista.add("CREATE TABLE Keskustelualue (alueenNimi varchar(30) PRIMARY KEY, kuvaus varchar(150));");
//        lista.add("CREATE TABLE Langat (viestiNro integer PRIMARY KEY, otsikko varchar(30), alue, aikaleima timestamp DATE DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY(alue) REFERENCES Keskustelualue(alueenNimi);");
//        lista.add("CREATE TABLE Vastaukset (viestiNro integer PRIMARY KEY, lanka, teksti varchar(300), aikaleima timestamp DATE DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (lanka) REFERENCES Langat(viestiNro);");
//       
//        
//        lista.add("CREATE TABLE Opiskelija (id integer PRIMARY KEY, nimi varchar(255));");
//        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Platon');");
//        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Aristoteles');");
//        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Homeros');");
//
//        lista.add("CREATE TABLE Keskustelualue (alueenNimi varchar(30) PRIMARY KEY, kuvaus varchar(150));");
//        lista.add(
//            "CREATE TABLE Langat (\n" +
//            "viestiNro integer PRIMARY KEY,\n" +
//            "otsikko varchar(30),\n" +
//            "alue,\n" +
//            "aikaleima timestamp DATE DEFAULT CURRENT_TIMESTAMP,\n" +
//            "FOREIGN KEY(alue) REFERENCES Keskustelualue(alueenNimi)\n" +
//            ");"
//        );
//        lista.add(
//            "CREATE TABLE Vastaukset (\n" +
//            "viestiNro integer PRIMARY KEY,\n" +
//            "lanka,\n" +
//            "teksti varchar(300),\n" +
//            "aikaleima timestamp DATE DEFAULT CURRENT_TIMESTAMP,\n" +
//            "FOREIGN KEY (lanka) REFERENCES Langat(viestiNro)\n" +
//            ");"
//        );

        return lista;
        
    }
    
        private List<String> postgreLauseet() {
            ArrayList<String> lista = new ArrayList<>();
            
//            lista.add("CREATE TABLE Keskustelualue (alueenNimi varchar(30) PRIMARY KEY, kuvaus varchar(150));");
//            lista.add("CREATE TABLE Langat (viestiNro integer PRIMARY KEY, otsikko varchar(30), alue, aikaleima timestamp DATE DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY(alue) REFERENCES Keskustelualue(alueenNimi);");
//            lista.add("CREATE TABLE Vastaukset (viestiNro integer PRIMARY KEY, lanka, teksti varchar(300), aikaleima timestamp DATE DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (lanka) REFERENCES Langat(viestiNro);");
//
//
//            lista.add("CREATE TABLE Opiskelija (id integer SERIAL PRIMARY KEY, nimi varchar(255));");
//            lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Platon');");
//            lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Aristoteles');");
//            lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Homeros');");
//
//            lista.add("CREATE TABLE Keskustelualue (alueenNimi varchar(30) SERIAL PRIMARY KEY, kuvaus varchar(150));");
//            lista.add(
//                "CREATE TABLE Langat (\n" +
//                "viestiNro integer SERIAL PRIMARY KEY,\n" +
//                "otsikko varchar(30),\n" +
//                "alue,\n" +
//                "aikaleima timestamp DATE DEFAULT CURRENT_TIMESTAMP,\n" +
//                "FOREIGN KEY(alue) REFERENCES Keskustelualue(alueenNimi)\n" +
//                ");"
//            );
//            lista.add(
//                "CREATE TABLE Vastaukset (\n" +
//                "viestiNro integer SERIAL PRIMARY KEY,\n" +
//                "lanka,\n" +
//                "teksti varchar(300),\n" +
//                "aikaleima timestamp DATE DEFAULT CURRENT_TIMESTAMP,\n" +
//                "FOREIGN KEY (lanka) REFERENCES Langat(viestiNro)\n" +
//                ");"
//            );

        return lista;
    }
        

}
