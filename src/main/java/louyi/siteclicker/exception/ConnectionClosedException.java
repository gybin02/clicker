/**
 *
 */
package louyi.siteclicker.exception;

/**
 * @author siyudu
 *
 */
public class ConnectionClosedException extends RequestException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ConnectionClosedException() {
        super();
    }

    public ConnectionClosedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionClosedException(String message) {
        super(message);
    }

    public ConnectionClosedException(Throwable cause) {
        super(cause);
    }

}
