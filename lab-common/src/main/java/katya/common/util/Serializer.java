package katya.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public final class Serializer {
    private Serializer() {
    }

    public static ByteBuffer serializeRequest(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        try (byteArrayOutputStream; objectOutputStream) {
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            return ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        }
    }

    public static byte[] serializeResponse(Response response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        try (byteArrayOutputStream; objectOutputStream) {
            objectOutputStream.writeObject(response);
            objectOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        }
    }
}
