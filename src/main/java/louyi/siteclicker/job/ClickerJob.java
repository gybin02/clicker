package louyi.siteclicker.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import louyi.siteclicker.Request;
import louyi.siteclicker.RequestContext;
import louyi.siteclicker.util.CollectionUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRouteParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : yi.lou
 */
public class ClickerJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClickerJob.class);

    private static final Random RANDOM = new Random();

    private final JobConfig config;

    public ClickerJob(JobConfig config) {
        this.config = config;
    }

    public void run(HttpClient httpClient) {
        RequestContext requestContext = new RequestContext();
        requestContext.setTrackCookie(false);
        requestContext.setTrackHeader(false);
        try {
            new Request(httpClient, requestContext).uri(config.getTargetUrl()).get();
            clickRelevantUrl(httpClient);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    private void clickRelevantUrl(HttpClient httpClient) {
        List<String> urls = pickRelevantUrlRandomly();
        for (String url : urls) {
            RequestContext requestContext = new RequestContext();
            requestContext.setTrackCookie(false);
            requestContext.setTrackHeader(false);
            try {
                new Request(httpClient, requestContext).uri(url).referer(config.getTargetUrl()).get();
            } catch (Exception e) {
                LOGGER.warn(e.getMessage(), e);
            }
        }

    }

    private List<String> pickRelevantUrlRandomly() {
        if (CollectionUtils.isEmpty(config.getRelevantUrls())) {
            return Collections.emptyList();
        }

        List<String> picked = new ArrayList<String>();

        int total = RANDOM.nextInt(config.getRelevantUrls().size()) + 1;
        for (int i = 0; i < total; i++) {
            picked.add(config.getRelevantUrls().get(RANDOM.nextInt(total)));
        }

        return picked;
    }

    public static class JobConfig {

        private String targetUrl;

        private List<String> relevantUrls;

        public void setTargetUrl(String targetUrl) {
            this.targetUrl = targetUrl;
        }

        public void setRelevantUrls(List<String> relevantUrls) {
            this.relevantUrls = relevantUrls;
        }

        public String getTargetUrl() {
            return targetUrl;
        }

        public List<String> getRelevantUrls() {
            return relevantUrls;
        }

        @Override
        public String toString() {
            return "[target url=" + targetUrl + "]," + " [relevant url=" + StringUtils.join(relevantUrls, ",") + "]";
        }
    }
}
