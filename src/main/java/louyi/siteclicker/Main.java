package louyi.siteclicker;

import java.io.IOException;
import java.util.List;

import louyi.siteclicker.executor.Executor;
import louyi.siteclicker.executor.Executor.Config;

/**
 * @author : yi.lou
 */
public class Main {
    public static void main(String[] args) throws IOException {
        List<Config> configs = Config.readConfig();
        for (Config config : configs) {
            Executor executor = new Executor(config);
            executor.execute();
        }
    }
}
