package katya.server.DBInterface;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBConnectable {
    void handleQuery(SQLConsumer<Connection> queryBody) throws SQLException;

    <T> T handleQuery(SQLFunction<Connection, T> queryBody) throws SQLException;
}
