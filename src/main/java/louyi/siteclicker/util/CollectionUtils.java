package louyi.siteclicker.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author : yi.lou
 */
public class CollectionUtils {
    public static boolean isEmpty(Collection collection) {
        return org.springframework.util.CollectionUtils.isEmpty(collection);
    }

    public static boolean isNotEmpty(Collection collection) {
        return !org.springframework.util.CollectionUtils.isEmpty(collection);
    }

    public static boolean isEmpty(Map map) {
        return org.springframework.util.CollectionUtils.isEmpty(map);
    }

    public static boolean isNotEmpty(Map map) {
        return !org.springframework.util.CollectionUtils.isEmpty(map);
    }
}
