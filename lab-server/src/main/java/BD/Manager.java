package BD;

import BDInterface.DBConnectable;
import katya.common.entites.HumanBeing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Manager {
    private final DBConnectable Connector;
    private final ReentrantLock reentrantLock = new ReentrantLock();

    public Manager(DBConnectable Connector) {
        this.Connector = Connector;
    }

    public Long add(HumanBeing humanBeing, String username) {
        return Connector.handleQuery((Connection connection) -> {
            String addElementQuery = "INSERT INTO human_being "
                    + "(creationDate, name, x, y, real_hero, has_toothpick, impact_speed, "
                    + "soundtrack_name, minutes_of_waiting, weapon_type, cool) "
                    + "SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, id FROM users WHERE users.login = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(addElementQuery,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, (Date) humanBeing.getCreationDate());
            preparedStatement.setString(2, humanBeing.getName());
            preparedStatement.setInt(3, humanBeing.getCoordinates().getX());
            preparedStatement.setInt(4, humanBeing.getCoordinates().getY());
            preparedStatement.setBoolean(5, humanBeing.getRealHero());
            preparedStatement.setBoolean(6, humanBeing.getHasToothpick());
            if (humanBeing.getImpactSpeed() == null) {
                preparedStatement.setNull(7, Types.DOUBLE);
            } else {
                preparedStatement.setDouble(7, humanBeing.getImpactSpeed());
            }
            preparedStatement.setString(8, humanBeing.getSoundtrackName());
            preparedStatement.setInt(9, humanBeing.getMinutesOfWaiting());
            preparedStatement.setString(10, humanBeing.getWeaponType().toString());
            if (humanBeing.getCar().getCool() == null) {
                preparedStatement.setNull(11, Types.BOOLEAN);
            } else {
                preparedStatement.setBoolean(11, humanBeing.getCar().getCool());
            }
            preparedStatement.setString(12, username);
            preparedStatement.executeUpdate();
            ResultSet result = preparedStatement.getGeneratedKeys();
            result.next();
            return result.getLong(1);
        });
    }


    public boolean update(long id, HumanBeing humanBeing, String username) {
        return Connector.handleQuery((Connection connection) -> {
            connection.createStatement().executeUpdate("BEGIN TRANSACTION;");
            String updateQuery = "UPDATE human_being "
                    + "SET name = ?, "
                    + "x = ?, "
                    + "y = ?, "
                    + "real_hero = ?, "
                    + "has_toothpick = ?, "
                    + "impact_speed = ?, "
                    + "soundtrack_name = ? "
                    + "minutes_of_waiting = ?, "
                    + "weapon_type = ?, "
                    + "cool = ? "
                    + "FROM users WHERE human_being.id = ? "
                    + "AND human_being.owner_id = users.id "
                    + "AND users.login = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, humanBeing.getName());
            preparedStatement.setInt(2, humanBeing.getCoordinates().getX());
            preparedStatement.setInt(3, humanBeing.getCoordinates().getY());
            preparedStatement.setBoolean(4, humanBeing.getRealHero());
            preparedStatement.setBoolean(5, humanBeing.getHasToothpick());
            if (humanBeing.getImpactSpeed() == null) {
                preparedStatement.setNull(6, Types.DOUBLE );
            } else {
                preparedStatement.setDouble(6, humanBeing.getImpactSpeed());
            }
            preparedStatement.setString(7, humanBeing.getSoundtrackName());
            preparedStatement.setInt(8, humanBeing.getMinutesOfWaiting());
            preparedStatement.setString(9, humanBeing.getWeaponType().toString());
            if (humanBeing.getCar().getCool() == null) {
                preparedStatement.setNull(10, Types.BOOLEAN);
            } else {
                preparedStatement.setBoolean(10, humanBeing.getCar().getCool());
            }
            preparedStatement.setLong(11, id);
            preparedStatement.setString(12, username);
            int updatedRows = preparedStatement.executeUpdate();
            connection.createStatement().execute("COMMIT;");
            return updatedRows > 0;
        });
    }

    public boolean removeById(Long id, String username) {
        return Connector.handleQuery((Connection connection) -> {
            String removeQuery = "DELETE FROM human_being USING users "
                    + "WHERE human_being.id = ? "
                    + "AND human_being.owner_id = users.id AND users.login = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(removeQuery);
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, username);

            int deletedBands = preparedStatement.executeUpdate();
            return deletedBands > 0;
        });
    }

    public List<Long> clear(String username) {
        return Connector.handleQuery((Connection connection) -> {
            String clearQuery = "DELETE FROM human_being USING users "
                    + "WHERE human_being.owner_id = users.id AND users.login = ? "
                    + "RETURNING human_being.id;";
            PreparedStatement preparedStatement = connection.prepareStatement(clearQuery);
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();
            List<Long> resultingList = new ArrayList<>();
            while (result.next()) {
                resultingList.add(result.getLong("id"));
            }
            return resultingList;
        });
    }


    public boolean removeAllByMinutesOfWaiting(int minutesOfWaiting, String username) {
        return Connector.handleQuery((Connection connection) -> {
            String removeQuery = "DELETE FROM human_being USING users "
                    + "WHERE human_being.minutes_of_waiting = ? "
                    + "AND human_being.owner_id = users.id AND users.login = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(removeQuery);
            preparedStatement.setLong(1, minutesOfWaiting);
            preparedStatement.setString(2, username);

            int deletedBands = preparedStatement.executeUpdate();
            return deletedBands > 0;
        });
    }

    public void addUser(String username, String password) {
        Connector.handleQuery((Connection connection) -> {
            String addUserQuery = "INSERT INTO users (login, password) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(addUserQuery,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, StringEncryptor.encryptString(password));

            preparedStatement.executeUpdate();
        });
    }

    public String getPassword(String username) {
        return Connector.handleQuery((Connection connection) -> {
            String getPasswordQuery = "SELECT (password) "
                    + "FROM users "
                    + "WHERE users.login = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(getPasswordQuery);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("password");
            }
            return null;
        });
    }

    public boolean checkUsersExistence(String username) {
        return Connector.handleQuery((Connection connection) -> {
            String existenceQuery = "SELECT COUNT (*) "
                    + "FROM users "
                    + "WHERE users.login = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(existenceQuery);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            return resultSet.getInt("count") > 0;
        });
    }

    public List<Long> getIdsOfUsersElements(String username){
        return Connector.handleQuery((Connection connection) -> {
            String getIdsQuery = "SELECT human_being.id FROM human_being, users "
                    + "WHERE human_being.owner_id = users.id AND users.login = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(getIdsQuery);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Long> resultingList = new ArrayList<>();
            while (resultSet.next()) {
                resultingList.add(resultSet.getLong("id"));
            }

            return resultingList;
        });
    }

    public boolean validateUser(String username, String password){
        return Connector.handleQuery((Connection connection) ->
                StringEncryptor.encryptString(password).equals(getPassword(username)));
    }
}
