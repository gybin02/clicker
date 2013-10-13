package louyi.siteclicker.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class JsonUtils {

    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
    public static final ObjectMapper OM = new ObjectMapper();
    static {
        OM.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OM.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    }

    public static Map<String, Object> readValue(String jsonStr) {
        return readValue(jsonStr, StringUtils.EMPTY);
    }

    public static <T> T readValue(String jsonStr, Class<T> clazz) {
        return readValue(jsonStr, clazz, StringUtils.EMPTY);
    }

    public static <T> T readValue(String jsonStr, TypeReference<T> typeReference) {
        try {
            return OM.readValue(jsonStr, typeReference);
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            return null;
        }
    }

    public static <T> T readValue(String jsonStr, Class<T> clazz, String logInfo) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        T t = null;
        try {
            t = OM.readValue(jsonStr, clazz);
        } catch (IOException e) {
            log.warn(e.getMessage() + " " + logInfo, e);
        }
        return t;
    }

    public static Map<String, Object> readValue(String jsonStr, String logInfo) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isEmpty(jsonStr)) {
            return map;
        }

        try {
            map = OM.readValue(jsonStr, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            log.warn(e.getMessage() + " " + logInfo, e);
        }
        return map;
    }

    public static String writeValueAsString(Object obj) {
        return writeValueAsString(obj, StringUtils.EMPTY);
    }

    public static String writeValueAsString(Object obj, String logInfo) {
        String str = StringUtils.EMPTY;

        if (obj == null) {
            return str;
        }

        try {
            str = OM.writeValueAsString(obj);
        } catch (IOException e) {
            log.warn(e.getMessage() + " " + logInfo, e);
        }
        return str;
    }
}
