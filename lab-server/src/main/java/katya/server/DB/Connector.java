package katya.server.DB;

import katya.server.DBInterface.SQLConsumer;
import katya.server.DBInterface.SQLFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector {

    private final String dbUrl = "jdbc:postgresql://localhost:5432/studs";
    private final String user = "postgres";
    private final String pass = "338862";

    public Connector() {
        try {
            Class.forName("org.postgresql.Driver");
            initializeDB();
        } catch (ClassNotFoundException e) {
            System.out.println("No DB driver!");
            System.exit(1);
        } catch (SQLException e) {
            System.out.println("Error occurred during initializing tables!" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void handleQuery(SQLConsumer<Connection> queryBody) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbUrl, user, pass)) {
            queryBody.accept(connection);
        }
    }

    public <T> T handleQuery(SQLFunction<Connection, T> queryBody) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbUrl, user, pass)) {
            return queryBody.apply(connection);
        }
    }


    private void initializeDB() throws SQLException {

        Connection connection = DriverManager.getConnection(dbUrl, user, pass);

        Statement statement = connection.createStatement();

        statement.execute("CREATE SEQUENCE IF NOT EXISTS human_being_id_seq INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1");

        statement.execute("CREATE SEQUENCE IF NOT EXISTS users_id_seq INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1");

        statement.execute("CREATE TABLE IF NOT EXISTS users "
                + "("
                + "login varchar(255) NOT NULL UNIQUE CHECK(login<>''),"
                + "password varchar(255) NOT NULL CHECK(password<>''),"
                + "id BIGINT PRIMARY KEY DEFAULT nextval('users_id_seq')"
                + ");");

        statement.execute("CREATE TABLE IF NOT EXISTS human_being "
                + "("
                + "id BIGINT PRIMARY KEY DEFAULT nextval('human_being_id_seq'),"
                + "creationDate date NOT NULL,"
                + "name VARCHAR(50) NOT NULL CHECK(name<>''),"
                + "x INT NOT NULL CHECK(x <= 877),"
                + "y INT NOT NULL,"
                + "real_hero BOOLEAN NOT NULL,"
                + "has_toothpick BOOLEAN NOT NULL,"
                + "impact_speed FLOAT CHECK(impact_speed > -484),"
                + "soundtrack_name VARCHAR(150) NOT NULL,"
                + "minutes_of_waiting INTEGER NOT NULL,"
                + "weapon_type varchar(7) NOT NULL CHECK(weapon_type = 'PISTOL' "
                + "OR weapon_type = 'SHOTGUN' "
                + "OR weapon_type = 'BAT'),"
                + "cool BOOLEAN,"
                + "owner_id BIGINT NOT NULL REFERENCES users (id)"
                + ");");
        connection.close();
    }
}
