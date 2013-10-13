/**
 * 
 */
package louyi.siteclicker;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UserAgentGenerator {

    private static final Random RANDOM = new Random();

    private static List<String> Candidates = Arrays
            .asList("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_4) AppleWebKit/536.25 (KHTML, like Gecko) Version/6.0 Safari/536.25", // Safari
                                                                                                                                       // 6.0
                                                                                                                                       // (Mac)
                    "Mozilla/5.0 (Windows; Windows NT 6.1) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2", // Safari
                                                                                                                                    // 5.1.7
                                                                                                                                    // (Windows)
                    "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)", // IE 9
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_4) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.46 Safari/536.5", // Google
                                                                                                                                             // Chrome
                                                                                                                                             // 19
                                                                                                                                             // (Mac)
                    "Mozilla/5.0 (Windows; Windows NT 6.1) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.46 Safari/536.5", // Google
                                                                                                                                    // Chrome
                                                                                                                                    // 19
                                                                                                                                    // (Windows)
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:11.0) Gecko/20100101 Firefox/11.0", // Filefox
                                                                                                         // 11.0
                                                                                                         // (Mac)
                    "Mozilla/5.0 (Windows NT 6.1; rv:11.0) Gecko/20100101 Firefox/11.0" // Firefox 11.0 (Windows)
            );

    public static String generate() {
        return Candidates.get(RANDOM.nextInt(Candidates.size()));
    }

}
