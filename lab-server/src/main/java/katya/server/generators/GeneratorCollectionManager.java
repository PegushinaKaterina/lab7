package katya.server.generators;

import BD.Connector;
import katya.common.entites.Coordinates;
import katya.common.entites.HumanBeing;
import katya.common.entites.HumanBeingBuilder;
import katya.common.entites.WeaponType;
import katya.server.entites.CollectionManager;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

public class GeneratorCollectionManager {
    private final CollectionManager collectionManager;

    public LinkedList<HumanBeing> loadCollection() throws DatabaseException {
        return Connector.handleQuery((Connection connection) -> {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM human_being");
            LinkedList<HumanBeing> resultSet = new LinkedList<>();
            while (result.next()) {
                String stringWeaponType = result
                        .getString("weaponType");
                WeaponType weaponType = WeaponType.valueOf(stringWeaponType);
                Coordinates coordinates = new Coordinates(result.getInt("x"),
                        result.getInt("y"));
                HumanBeing humanBeing = new HumanBeing(new HumanBeingBuilder()
                        .withName()
                        .)
                MusicBand musicBand = new MusicBand(
                        result.getDate("creationDate").toLocalDate(),
                        result.getLong("id"),
                        result.getString("name"),
                        bandsCoordinates,
                        result.getLong("numberOfParticipants"),
                        result.getString("description"),
                        musicGenre,
                        bandsStudio);
                resultSet.add(musicBand);
            }
            return resultSet;
        });
    }


    public GeneratorCollectionManager() throws FileNotFoundException {
        collectionManager = new CollectionManager();
        HumanBeing.getGeneratorHumanBeing().changeState(new BDGeneratorHumanBeing());
        Connector.handleQuery((Connection connection) -> {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM human_being");
            int i = 1;
            while (result.next()) {
                HumanBeing.getGeneratorHumanBeing().generateHumanBeing();
                collectionManager.add(HumanBeing.getGeneratorHumanBeing().getHumanBeing());
            }
        });
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
