package katya.server.DB;

import katya.common.entites.HumanBeing;
import katya.common.entites.HumanBeingBuilder;
import katya.common.entites.WeaponType;
import katya.server.util.StringEncryptor;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DBManager {
    private final Connector connector;

    public DBManager(Connector connector) {
        this.connector = connector;
    }

    public LinkedList<HumanBeing> loadCollection() throws SQLException {
        LinkedList<HumanBeing> humanBeings = new LinkedList<>();
        return connector.handleQuery((Connection connection) -> {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM human_being");
            while (result.next()) {
                String stringWeaponType = result
                        .getString("weapon_type");
                WeaponType weaponType = WeaponType.valueOf(stringWeaponType);
                HumanBeing humanBeing = new HumanBeing(new HumanBeingBuilder()
                        .withId(result.getLong("id"))
                        .withDate(result.getDate("creationDate").toLocalDate())
                        .withName(result.getString("name"))
                        .withCoordinates(result.getInt("x"), result.getInt("y"))
                        .withRealHero(result.getBoolean("real_hero"))
                        .withHasToothpick(result.getBoolean("has_toothpick"))
                        .withImpactSpeed(result.getDouble("impact_speed"))
                        .withSoundtrackName(result.getString("soundtrack_name"))
                        .withMinutesOfWaiting(result.getInt("minutes_of_waiting"))
                        .withWeaponType(weaponType)
                        .withCar(result.getBoolean("cool")));
                humanBeings.add(humanBeing);
            }
            return humanBeings;
        });

    }

    public Long add(HumanBeing humanBeing, String username) throws SQLException {
        return connector.handleQuery((Connection connection) -> {
            String addElementQuery = "INSERT INTO human_being "
                    + "(creationDate, name, x, y, real_hero, has_toothpick, impact_speed, "
                    + "soundtrack_name, minutes_of_waiting, weapon_type, cool, owner_id) "
                    + "SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, id FROM users WHERE users.login = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(addElementQuery,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, Date.valueOf(humanBeing.getCreationDate()));
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

    public List<Long> clear(String username) throws SQLException {
        return connector.handleQuery((Connection connection) -> {
            String clearQuery = "DELETE FROM human_being USING users "
                    + "WHERE human_being.owner_id = users.id AND users.login = ? "
                    + "RETURNING human_being.id;";
            PreparedStatement preparedStatement = connection.prepareStatement(clearQuery);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Long> idList = new ArrayList<>();
            while (resultSet.next()) {
                idList.add(resultSet.getLong("id"));
            }
            return idList;
        });
    }

    public List<Long> removeAllByMinutesOfWaiting(int minutesOfWaiting, String username) throws SQLException {
        return connector.handleQuery((Connection connection) -> {
            String removeQuery = "DELETE FROM human_being USING users "
                    + "WHERE human_being.minutes_of_waiting = ? "
                    + "AND human_being.owner_id = users.id AND users.login = ? "
                    + "RETURNING human_being.id;";
            PreparedStatement preparedStatement = connection.prepareStatement(removeQuery);
            preparedStatement.setLong(1, minutesOfWaiting);
            preparedStatement.setString(2, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Long> idList = new ArrayList<>();
            while (resultSet.next()) {
                idList.add(resultSet.getLong("id"));
            }
            return idList;
        });
    }

    public void removeById(Long id, String username) throws SQLException {
        connector.handleQuery((Connection connection) -> {
            String removeQuery = "DELETE FROM human_being USING users "
                    + "WHERE human_being.id = ? "
                    + "AND human_being.owner_id = users.id AND users.login = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(removeQuery);
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        });
    }

    public void update(long id, HumanBeing humanBeing, String username) throws SQLException {
        connector.handleQuery((Connection connection) -> {
            connection.createStatement().executeUpdate("BEGIN TRANSACTION;");
            String updateQuery = "UPDATE human_being "
                    + "SET name = ?, "
                    + "x = ?, "
                    + "y = ?, "
                    + "real_hero = ?, "
                    + "has_toothpick = ?, "
                    + "impact_speed = ?, "
                    + "soundtrack_name = ?, "
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
                preparedStatement.setNull(6, Types.DOUBLE);
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
            preparedStatement.executeUpdate();
            connection.createStatement().execute("COMMIT;");
        });
    }

    public void addUser(String username, String password) throws SQLException {
        connector.handleQuery((Connection connection) -> {
            String addUserQuery = "INSERT INTO users (login, password) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(addUserQuery,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, StringEncryptor.encryptString(password));
            preparedStatement.executeUpdate();
        });
    }

    public String getPassword(String username) throws SQLException {
        return connector.handleQuery((Connection connection) -> {
            String getPasswordQuery = "SELECT (password) "
                    + "FROM users WHERE users.login = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(getPasswordQuery);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("password");
            }
            return null;
        });
    }

    public void validateUser(String username, String password) throws SQLException, IllegalArgumentException {
        if (!connector.handleQuery((Connection connection) ->
                StringEncryptor.encryptString(password).equals(getPassword(username)))) {
            throw new IllegalArgumentException("Такого пользователя не существует");
        }
    }

    public boolean checkUsersExistence(String username) throws SQLException {
        return connector.handleQuery((Connection connection) -> {
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

    public void checkHumanBeingExistence(Long id, String username) throws SQLException {
        connector.handleQuery((Connection connection) -> {
            String existenceQuery = "SELECT COUNT (*) "
                    + "FROM human_being, users "
                    + "WHERE human_being.id = ? "
                    + "AND human_being.owner_id = users.id AND users.login = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(existenceQuery);
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, username);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            if (result.getInt("count") < 1) {
                throw new IllegalArgumentException("Человека с таким id не существует");
            }
        });
    }

    public List<Long> getIdsOfUsersElements(String username) throws SQLException {
        return connector.handleQuery((Connection connection) -> {
            String existenceQuery = "SELECT human_being.id "
                    + "FROM human_being, users "
                    + "WHERE human_being.owner_id = users.id AND users.login = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(existenceQuery);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Long> idList = new ArrayList<>();
            while (resultSet.next()) {
                idList.add(resultSet.getLong("id"));
            }
            return idList;
        });
    }

    public void removeByIds(List<Long> idListLover, String username) throws SQLException {
        connector.handleQuery((Connection connection) -> {
            connection.createStatement().executeUpdate("BEGIN TRANSACTION;");
            String removeQuery = "DELETE FROM human_being USING users "
                    + "WHERE human_being.id = ? "
                    + "AND human_being.owner_id = users.id AND users.login = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(removeQuery);
            preparedStatement.setString(2, username);
            for (Long id : idListLover) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
            connection.createStatement().execute("COMMIT;");
        });


    }

    /*public List<Long> getIdsOfUsersElements(String username){
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
    }*/

}
