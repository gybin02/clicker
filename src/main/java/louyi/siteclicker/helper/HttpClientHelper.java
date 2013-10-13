package louyi.siteclicker.helper;

import java.util.ArrayList;
import java.util.List;

import louyi.siteclicker.proxy.Proxy;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * 
 * @author siyudu
 * 
 */
public class HttpClientHelper {

    /**
     * apply a proxy to a httpClient, and setup proxy auth info if the httpClient is an instance of
     * {@link DefaultHttpClient}
     * 
     */
    public static void applyProxy(HttpClient httpClient, Proxy proxy) {
        Assert.notNull(httpClient, "httpClient is required");
        Assert.notNull(proxy, "proxy is required");
        String host = proxy.getHost();
        int port = -1;
        if (proxy.getPort() > 0) {
            port = proxy.getPort();
        }
        String schema = null;
        if (proxy.getSchema() != null) {
            schema = proxy.getSchema();
        }
        HttpHost proxyHost = new HttpHost(host, port, schema);
        httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxyHost);
        if (httpClient instanceof DefaultHttpClient) {
            DefaultHttpClient defaultHttpClient = (DefaultHttpClient) httpClient;
            if (proxy.getUserName() != null) {
                defaultHttpClient.getCredentialsProvider().setCredentials(new AuthScope(host, port),
                        new UsernamePasswordCredentials(proxy.getUserName(), proxy.getPassword()));
            }
        }
    }

    public static void setCookie(HttpClient httpClient, List<Cookie> cookies) {
        if (CollectionUtils.isEmpty(cookies)) {
            return;
        }
        if (httpClient instanceof DefaultHttpClient) {
            DefaultHttpClient defaultHttpClient = (DefaultHttpClient) httpClient;
            CookieStore cookieStore = defaultHttpClient.getCookieStore();
            for (Cookie cookie : cookies) {
                cookieStore.addCookie(cookie);
            }
        }
    }

    public static List<Cookie> extractCookie(HttpClient httpClient) {
        List<Cookie> cookies = new ArrayList<Cookie>();
        if (httpClient instanceof DefaultHttpClient) {
            DefaultHttpClient defaultHttpClient = (DefaultHttpClient) httpClient;
            CookieStore cookieStore = defaultHttpClient.getCookieStore();
            // get Cookies
            cookies = cookieStore.getCookies();
        }
        return cookies;

    }

}
