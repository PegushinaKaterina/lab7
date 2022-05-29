package BDInterface;

import katya.common.util.Response;

import java.io.IOException;
import java.net.SocketAddress;

public interface SocketWorkerInterface {
    RequestWithAddress listenForRequest() throws IOException, ClassNotFoundException;
    void sendResponse(Response response, SocketAddress address) throws IOException;
    void stopSocketWorker() throws IOException;
}