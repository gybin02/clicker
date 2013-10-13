/**
 *
 */
package louyi.siteclicker.exception;

/**
 * @author siyudu
 * 
 */
public class RequestStatusException extends RequestException {

    /**
     *
     */
    private static final long serialVersionUID = 5791507835542614855L;
    private int httpStatus;

    /**
     *
     */
    public RequestStatusException(int httpStatus) {
        this.setHttpStatus(httpStatus);
    }

    /**
     * @param message
     * @param cause
     */
    public RequestStatusException(int httpStatus, Throwable cause) {
        super("error for httpStatus: '" + httpStatus + "'", cause);
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

}
