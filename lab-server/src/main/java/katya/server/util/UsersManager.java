package katya.server.util;

import katya.common.util.Request;
import katya.common.util.Response;
import katya.common.util.ResponseBuilder;
import katya.server.DB.DBManager;

import java.sql.SQLException;

public class UsersManager {

    private final DBManager dbManager;

    public UsersManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public Response registerNewUser(Request request) {
        try {
            if (!dbManager.checkUsersExistence(request.getUsername())) {
                dbManager.addUser(request.getUsername(), request.getPassword());
                return new Response(new ResponseBuilder()
                        .withMessageToResponse("Регистрация прошла успешно"));
            } else {
                return new Response(new ResponseBuilder()
                        .withMessageToResponse("Пользователь с таким именем уже существует")
                        .withSuccessToResponse(false));
            }
        } catch (SQLException e) {
            return new Response(new ResponseBuilder()
                    .withMessageToResponse("Ошибка при работе с базой данных")
                    .withSuccessToResponse(false));
        }
    }

    public Response loginUser(Request request) {
        try {
            dbManager.validateUser(request.getUsername(), request.getPassword());
            return new Response(new ResponseBuilder().withMessageToResponse("Вход выполнен"));
        } catch (IllegalArgumentException e) {
            return new Response(new ResponseBuilder()
                    .withMessageToResponse(e.getMessage())
                    .withSuccessToResponse(false));
        } catch (SQLException e) {
            return new Response(new ResponseBuilder()
                    .withMessageToResponse("Ошибка при работе с базой данных")
                    .withSuccessToResponse(false));
        }
    }
}

