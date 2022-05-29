package BD;

import BDInterface.SQLConsumer;
import BDInterface.SQLFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector {

    private final String dbUrl = "jdbc:postgresql://pg:5432/studs";
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

        statement.execute("CREATE TABLE IF NOT EXISTS weapon_type "
                + "("
                + "weapon_type varchar(7) PRIMARY KEY,"
                + ");");

        statement.execute("CREATE TABLE IF NOT EXISTS human_being "
                + "("
                + "id BIGINT PRIMARY KEY DEFAULT nextval('human_being_id_seq'),"
                + "creationDate date NOT NULL,"
                + "name VARCHAR(50) NOT NULL CHECK(name<>''),"
                + "x INT NOT NULL CHECK(x <= 877),"
                + "y INT NOT NULL,"
                + "real_hero REAL NOT NULL,"
                + "has_toothpick REAL NOT NULL,"
                + "impact_speed DOUBLE CHECK(impact_speed > -484),"
                + "soundtrack_name VARCHAR(150) NOT NULL,"
                + "minutes_of_waiting INTEGER NOT NULL,"
                + "weapon_type varchar(7) NOT NULL CHECK(weapon_type =  'PISTOL' "
                + "OR weapon_type = 'SHOTGUN' "
                + "OR weapon_type = 'BAT'),"
                + "cool REAL"
                + "owner_id BIGINT NOT NULL REFERENCES users (id)"
                + ");");

        /*statement.execute("CREATE TABLE IF NOT EXISTS human_being "
                + "name VARCHAR(50) NOT NULL,"
                + "x INT NOT NULL CHECK(x <= 877),"
                + "y INT NOT NULL,"
                + "real_hero REAL NOT NULL,"
                + "has_toothpick REAL NOT NULL,"
                + "impact_speed DOUBLE CHECK(impact_speed > -484),"
                + "soundtrack_name VARCHAR(150) NOT NULL,"
                + "minutes_of_waiting INTEGER NOT NULL,"
                + "weapon_type varchar(7) NOT NULL CHECK(weapon_type =  'PISTOL' "
                + "OR weapon_type = 'SHOTGUN' "
                + "OR weapon_type = 'BAT'),"
                + "cool REAL"
                + ");");

           private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
        private String name; //Поле не может быть null, Строка не может быть пустой
        private Coordinates coordinates; //Поле не может быть null
        private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
        private Boolean realHero; //Поле не может быть null
        private boolean hasToothpick;
        private Double impactSpeed; //Значение поля должно быть больше -484, Поле может быть null
        private String soundtrackName; //Поле не может быть null
        private Integer minutesOfWaiting; //Поле не может быть null
        private WeaponType weaponType; //Поле не может быть null
        private Car car; //Поле не может быть null
       */

        connection.close();
    }
}
