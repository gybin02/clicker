/**
 *
 */
package louyi.siteclicker;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;

/**
 * @author siyudu
 *
 */
public class HttpClientFactory {

    public static HttpClient createHttpClient() {
        // TODO
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, UserAgentGenerator.generate());
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 30);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 30);
        return httpClient;
    }

}
