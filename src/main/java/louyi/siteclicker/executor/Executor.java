package louyi.siteclicker.executor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import louyi.siteclicker.ClickerTask;
import louyi.siteclicker.job.ClickerJob;
import louyi.siteclicker.job.ClickerJob.JobConfig;
import louyi.siteclicker.proxy.Proxy;
import louyi.siteclicker.proxy.ProxyDataCenter;
import louyi.siteclicker.util.JsonUtils;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.type.TypeReference;

/**
 * @author : yi.lou
 */
public class Executor {

    private final Config config;

    private final ExecutorService executorService;

    public Executor(Config config) {
        this.config = config;
        this.executorService = Executors.newFixedThreadPool(config.getExecutorConfig().getThreadCount());
    }

    public void execute() {
        ClickerJob clickerJob = new ClickerJob(config.getJobConfig());
        for (int i = 0; i < config.getExecutorConfig().getThreadCount(); i++) {
            List<Proxy> proxies = ProxyDataCenter.getInstance().getAllProxies();
            Collections.shuffle(proxies);
            ClickerTask clickerTask = new ClickerTask(clickerJob, proxies, config.getExecutorConfig()
                    .getIntervalAreaBegin(), config.getExecutorConfig().getIntervalAreaEnd());
            executorService.execute(clickerTask);
        }
    }

    public static class Config {

        private ExecutorConfig executorConfig;

        private JobConfig jobConfig;

        public ExecutorConfig getExecutorConfig() {
            return executorConfig;
        }

        public void setExecutorConfig(ExecutorConfig executorConfig) {
            this.executorConfig = executorConfig;
        }

        public JobConfig getJobConfig() {
            return jobConfig;
        }

        public void setJobConfig(JobConfig jobConfig) {
            this.jobConfig = jobConfig;
        }

        public static List<Config> readConfig() throws IOException {
            String configContent = IOUtils.toString(new FileInputStream(System.getProperty("config.file.path")));
            return JsonUtils.readValue(configContent, new TypeReference<List<Config>>() {
            });
        }

        @Override
        public String toString() {
            return executorConfig.toString() + " " + jobConfig.toString();
        }
    }

    public static class ExecutorConfig {

        private int threadCount;

        private int intervalAreaBegin;

        private int intervalAreaEnd;

        public int getThreadCount() {
            return threadCount;
        }

        public void setThreadCount(int threadCount) {
            this.threadCount = threadCount;
        }

        public int getIntervalAreaBegin() {
            return intervalAreaBegin;
        }

        public void setIntervalAreaBegin(int intervalAreaBegin) {
            this.intervalAreaBegin = intervalAreaBegin;
        }

        public int getIntervalAreaEnd() {
            return intervalAreaEnd;
        }

        public void setIntervalAreaEnd(int intervalAreaEnd) {
            this.intervalAreaEnd = intervalAreaEnd;
        }

        @Override
        public String toString() {
            return "[thread count=" + threadCount + "], [interval area begin=" + intervalAreaBegin
                    + "], [interval area end=" + intervalAreaEnd + "]";
        }
    }

}
