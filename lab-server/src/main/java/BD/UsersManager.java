package BD;

import katya.common.util.Request;
import katya.common.util.Response;

import java.io.IOException;

public class UsersManager {

    private final Manager dbManager;

    public UsersManager(Manager dbManager) {
        this.dbManager = dbManager;
    }

    public Response registerNewUser(Request request) {
        try {
            if (!dbManager.checkUsersExistence(request.getUsername())) {
                dbManager.addUser(request.getUsername(), request.getPassword());
                return new Response("Registration was completed successfully!");
            } else {
                return new Response("This username already exists!", false);
            }
        } catch (IOException e) {
            return new Response(e.getMessage()), false);
        }
    }

    public Response loginUser(Request request) {
        try {
            boolean check = dbManager.validateUser(request.getUsername(), request.getPassword());
            if (check) {
                return new Response("Login successful!");
            } else {
                return new Response("Wrong login or password!", false);
            }
        } catch (IOException e) {
            return new Response(e.getMessage(), false);
        }
    }
}

