package louyi.siteclicker.proxy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyDataCenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyDataCenter.class);

    private List<Proxy> allProxies;

    private ProxyDataCenter() {
        this.allProxies = loadProxies();
    }

    private static class SingletonHolder {
        private static final ProxyDataCenter INSTANCE = new ProxyDataCenter();
    }

    public static ProxyDataCenter getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public List<Proxy> getAllProxies() {
        return allProxies;
    }

    private List<Proxy> loadProxies() {
        List<Proxy> proxyList = new ArrayList<Proxy>();
        InputStream is = null;
        try {
            is = new FileInputStream(System.getProperty("proxy.file.path"));
            Properties prop = new Properties();
            prop.load(is);
            for (Entry<Object, Object> entry : prop.entrySet()) {
                String host = String.class.cast(entry.getKey());
                int port = NumberUtils.toInt(String.class.cast(entry.getValue()));
                proxyList.add(new Proxy(host, port));
            }
        } catch (IOException e) {
            LOGGER.warn(e.getMessage(), e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignore) {
                }
            }
        }
        return proxyList;
    }

}
