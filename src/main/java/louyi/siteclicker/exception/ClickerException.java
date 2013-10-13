/**
 *
 */
package louyi.siteclicker.exception;

/**
 * @author siyudu
 *
 */
public class ClickerException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 6395118761138501263L;

    /**
     *
     */
    public ClickerException() {
    }

    /**
     * @param message
     */
    public ClickerException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ClickerException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ClickerException(String message, Throwable cause) {
        super(message, cause);
    }

}
