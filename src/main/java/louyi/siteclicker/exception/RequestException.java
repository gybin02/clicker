/**
 *
 */
package louyi.siteclicker.exception;

/**
 * 请求错误
 * 
 * @author siyudu
 * 
 */
public class RequestException extends ClickerException {

    /**
     *
     */
    private static final long serialVersionUID = 7885135910272498742L;

    public RequestException() {
        super();
    }

    public RequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestException(String message) {
        super(message);
    }

    public RequestException(Throwable cause) {
        super(cause);
    }

}
