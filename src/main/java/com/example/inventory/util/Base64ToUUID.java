package manageStorage.log.util;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

public class Base64ToUUID {

    public static UUID fromBase64(String base64) {
        byte[] bytes = Base64.getDecoder().decode(base64);
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long high = bb.getLong();
        long low = bb.getLong();
        return new UUID(high, low);
    }
}