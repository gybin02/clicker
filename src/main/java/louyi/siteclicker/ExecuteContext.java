/**
 *
 */
package louyi.siteclicker;

import org.apache.http.client.HttpClient;
import org.springframework.util.Assert;

/**
 *
 */
public class ExecuteContext {

    public static class ExecuteContextBuilder {

        private HttpClient httpClient;

        public ExecuteContextBuilder httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public ExecuteContext build() {
            HttpClient httpClient = this.httpClient;
            if (httpClient == null) {
                httpClient = HttpClientFactory.createHttpClient();
            }
            return new ExecuteContext(httpClient);
        }

    }

    public static ExecuteContextBuilder create() {
        return new ExecuteContextBuilder();
    }

    private ExecuteContext(HttpClient httpClient) {
        Assert.notNull(httpClient, "httpClient is required");
        this.httpClient = httpClient;
    }

    private final HttpClient httpClient;

    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    public void close() {
        // destroy resources here
    }
}
