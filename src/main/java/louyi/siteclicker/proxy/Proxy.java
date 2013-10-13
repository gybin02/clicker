package louyi.siteclicker.proxy;

/**
 * @author : yi.lou
 */
public class Proxy {

    private final String host;

    private final int port;

    private final String schema;

    private final String userName;

    private final String password;

    public Proxy(String host, int port) {
        this(host, port, "http");
    }

    public Proxy(String host, int port, String schema) {
        this(host, port, schema, null, null);
    }

    public Proxy(String host, int port, String schema, String userName, String password) {
        this.host = host;
        this.port = port;
        this.schema = schema;
        this.userName = userName;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getSchema() {
        return schema;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
