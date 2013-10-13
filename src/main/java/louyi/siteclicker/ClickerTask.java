package louyi.siteclicker;

import java.util.List;
import java.util.Random;

import louyi.siteclicker.helper.HttpClientHelper;
import louyi.siteclicker.job.ClickerJob;
import louyi.siteclicker.proxy.Proxy;

import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : yi.lou
 */
public class ClickerTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClickerTask.class);

    private static final Random RANDOM = new Random();

    private static final Random SLEEP_RANDOM = new Random();

    private ClickerJob clickerJob;

    private List<Proxy> proxies;

    private int intervalAreaBegin;

    private int intervalAreaEnd;

    public ClickerTask(ClickerJob clickerJob, List<Proxy> proxies, int intervalAreaBegin, int intervalAreaEnd) {
        this.proxies = proxies;
        this.clickerJob = clickerJob;
        this.intervalAreaBegin = intervalAreaBegin;
        this.intervalAreaEnd = intervalAreaEnd;
    }

    @Override
    public void run() {
        while (true) {
            Proxy proxy = pickProxy();
            HttpClient httpClient = HttpClientFactory.createHttpClient();
            HttpClientHelper.applyProxy(httpClient, proxy);
            clickerJob.run(httpClient);
            Integer interval = pickInterval();
            LOGGER.info("**** thread : '{}' is gonna sleep for {} ms.", Thread.currentThread().getName(), interval);
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                LOGGER.warn(e.getMessage(), e);
            }
        }
    }

    private Proxy pickProxy() {
        return proxies.get(RANDOM.nextInt(proxies.size()));
    }

    private Integer pickInterval() {
        return SLEEP_RANDOM.nextInt(intervalAreaEnd - intervalAreaBegin) + intervalAreaBegin;
    }
}
