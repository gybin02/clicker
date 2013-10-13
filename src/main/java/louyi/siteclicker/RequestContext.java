/**
 *
 */
package louyi.siteclicker;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class RequestContext {

    private boolean trackCookie = false;

    private boolean trackHeader = false;

    private List<Cookie> cookies = new ArrayList<Cookie>();

    private List<Header> headers = new ArrayList<Header>();

    public boolean isTrackCookie() {
        return trackCookie;
    }

    public void setTrackCookie(boolean trackCookie) {
        this.trackCookie = trackCookie;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public boolean isTrackHeader() {
        return trackHeader;
    }

    public void setTrackHeader(boolean trackHeader) {
        this.trackHeader = trackHeader;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

}
