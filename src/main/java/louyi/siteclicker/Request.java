/**
 *
 */
package louyi.siteclicker;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import louyi.siteclicker.exception.RequestException;
import louyi.siteclicker.exception.RequestStatusException;
import louyi.siteclicker.helper.HttpClientHelper;

import org.apache.http.ConnectionClosedException;
import org.apache.http.Consts;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * @author siyudu
 * 
 */
public class Request {

    private static final Logger logger = LoggerFactory.getLogger(Request.class);

    private final HttpClient httpClient;

    private final RequestContext context;

    private URI uri;

    private Charset charset = Consts.UTF_8;

    private final Map<String, String> headers = new HashMap<String, String>();

    private final List<NameValuePair> parameters = new ArrayList<NameValuePair>();

    public Request(HttpClient httpClient, RequestContext context) {
        this.httpClient = httpClient;
        if (context == null) {
            this.context = new RequestContext();
        } else {
            this.context = context;
        }
    }

    public Request param(String key, Object value) {
        parameters.add(new BasicNameValuePair(key, String.valueOf(value)));
        return this;
    }

    public Request param(NameValuePair nameValuePair) {
        parameters.add(nameValuePair);
        return this;
    }

    public Request params(Map<String, Object> params) {

        if (CollectionUtils.isEmpty(params)) {
            return this;
        }

        for (Entry<String, Object> entry : params.entrySet()) {
            Object value = entry.getValue();
            if (value != null) {
                parameters.add(new BasicNameValuePair(entry.getKey(), value.toString()));
            }
        }
        return this;
    }

    public Request referer(String referer) {
        headers.put(HttpHeaders.REFERER, referer);
        return this;
    }

    public Request uri(String uri) {
        this.uri = URI.create(uri);
        return this;
    }

    public Request charSet(String charset) {
        this.charset = Charset.forName(charset);
        return this;
    }

    public String get() throws RequestStatusException, RequestException, IOException {
        this.validate();
        HttpEntity entity = null;
        logger.info("**** thread : '{}' use proxy : '{}' to access '{}' ****", Thread.currentThread().getName(),
                httpClient.getParams().getParameter(ConnRouteParams.DEFAULT_PROXY), uri);
        try {
            HttpGet httpGet = new HttpGet(uri);
            for (Entry<String, String> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
            if (this.context.isTrackCookie()) {
                HttpClientHelper.setCookie(httpClient, this.context.getCookies());
            }
            HttpResponse response = httpClient.execute(httpGet);
            if (this.context.isTrackCookie()) {
                // TODO give this implement a 2nd thought
                this.context.setCookies(HttpClientHelper.extractCookie(httpClient));
            }
            if (this.context.isTrackHeader()) {
                this.context.setHeaders(Arrays.asList(response.getAllHeaders()));
            }
            entity = response.getEntity();
            return toString(response, entity);
        } catch (ParseException e) {
            throw new RequestException(e);
        } catch (SocketTimeoutException e) {
            throw new RequestException(e);
        } catch (ConnectionClosedException e) {
            throw new louyi.siteclicker.exception.ConnectionClosedException("connectionClosed for uri: '" + uri + "'",
                    e);
        } catch (ConnectTimeoutException e) {
            throw new RequestException(e);
        } finally {
            consume(entity);
        }
    }

    public String post() throws RequestStatusException, RequestException, IOException {
        this.validate();
        HttpEntity entity = null;
        try {
            HttpPost post = new HttpPost(uri);
            if (!CollectionUtils.isEmpty(parameters)) {
                post.setEntity(new UrlEncodedFormEntity(parameters, Consts.UTF_8.displayName()));
            }
            for (Entry<String, String> entry : headers.entrySet()) {
                post.addHeader(entry.getKey(), entry.getValue());
            }
            HttpResponse response = httpClient.execute(post);
            entity = response.getEntity();
            return toString(response, entity);
        } catch (ParseException e) {
            throw new RequestException(e);
        } catch (SocketTimeoutException e) {
            throw new RequestException(e);
        } catch (ConnectTimeoutException e) {
            throw new RequestException(e);
        } finally {
            consume(entity);
        }
    }

    private void validate() {
        Assert.notNull(this.uri, "uri is required");
        Assert.notNull(this.httpClient, "httpClient is required");
    }

    private String toString(HttpResponse response, HttpEntity entity) throws RequestException, ParseException,
            IOException {
        StatusLine status = response.getStatusLine();
        if (status == null) {
            throw new RequestException("status is empty");
        } else if (status.getStatusCode() != 200) {
            throw new RequestStatusException(status.getStatusCode());
        }
        return toString(entity, charset);
    }

    /**
     * 直接将使用EntityUtils里的源代码里有个bug 会抛出ConnectionClosedException
     */
    public static String toString(final HttpEntity entity, final Charset defaultCharset) throws IOException,
            ParseException {
        if (entity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }
        InputStream instream = entity.getContent();
        if (instream == null) {
            return null;
        }
        try {
            if (entity.getContentLength() > Integer.MAX_VALUE) {
                throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
            }
            int i = (int) entity.getContentLength();
            if (i < 0) {
                i = 4096;
            }
            Charset charset = getContentCharSet(entity);
            if (charset == null) {
                charset = defaultCharset;
            }
            if (charset == null) {
                charset = HTTP.DEF_CONTENT_CHARSET;
            }
            Reader reader = new InputStreamReader(instream, charset);
            CharArrayBuffer buffer = new CharArrayBuffer(i);
            char[] tmp = new char[1024];
            int l;
            while ((l = reader.read(tmp)) != -1) {
                buffer.append(tmp, 0, l);
            }
            return buffer.toString();
        } finally {
            instream.close();
        }
    }

    public static Charset getContentCharSet(final HttpEntity entity) throws ParseException {
        if (entity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }
        Charset charset = null;
        if (entity.getContentType() != null) {
            HeaderElement values[] = entity.getContentType().getElements();
            if (values.length > 0) {
                NameValuePair param = values[0].getParameterByName("charset");
                if (param != null) {
                    charset = Charset.forName(param.getValue());
                }
            }
        }
        return charset;
    }

    public static void consume(HttpEntity entity) {
        try {
            EntityUtils.consume(entity);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
