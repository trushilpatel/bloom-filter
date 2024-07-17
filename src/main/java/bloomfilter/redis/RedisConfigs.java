package bloomfilter.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class RedisConfigs {
    private final String host;
    private final int port;
    private final int timeout;
    private final String password;
    private final int maxTotal;
    private final int maxIdle;
    private final int minIdle;
    private final boolean testOnBorrow;
    private final boolean testOnReturn;
    private final boolean testWhileIdle;
    private final int timeBetweenEvictionRunsMillis;
    private final int minEvictableIdleTimeMillis;
    private final int numTestsPerEvictionRun;

    // Configure using docker compose env files
    public RedisConfigs() {
        Map<String, String> env = System.getenv();

        this.host = env.getOrDefault("REDIS_HOST", "localhost");
        this.port = Integer.parseInt(env.getOrDefault("REDIS_PORT", "6379"));
        this.timeout = Integer.parseInt(env.getOrDefault("REDIS_TIMEOUT", "2000"));
        this.password = env.getOrDefault("REDIS_PASSWORD", "");
        this.maxTotal = Integer.parseInt(env.getOrDefault("REDIS_MAXTOTAL", "128"));
        this.maxIdle = Integer.parseInt(env.getOrDefault("REDIS_MAXIDLE", "128"));
        this.minIdle = Integer.parseInt(env.getOrDefault("REDIS_MINIDLE", "16"));
        this.testOnBorrow = Boolean.parseBoolean(env.getOrDefault("REDIS_TESTONBORROW", "true"));
        this.testOnReturn = Boolean.parseBoolean(env.getOrDefault("REDIS_TESTONRETURN", "true"));
        this.testWhileIdle = Boolean.parseBoolean(env.getOrDefault("REDIS_TESTWHILEIDLE", "true"));
        this.timeBetweenEvictionRunsMillis = Integer.parseInt(env.getOrDefault("REDIS_TIMEEVICTIONRUNS", "30000"));
        this.minEvictableIdleTimeMillis = Integer.parseInt(env.getOrDefault("REDIS_MINEVICTIONIDLE", "60000"));
        this.numTestsPerEvictionRun = Integer.parseInt(env.getOrDefault("REDIS_NUMTESTSEVICTION", "-1"));
    }

    // configure using properties file
    public RedisConfigs(String propertiesFilePath) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(propertiesFilePath)) {
            if (input == null) {
                throw new IOException("Properties file not found: " + propertiesFilePath);
            }
            properties.load(input);
        }
        this.host = properties.getProperty("redis.host");
        this.port = Integer.parseInt(properties.getProperty("redis.port"));
        this.timeout = Integer.parseInt(properties.getProperty("redis.timeout"));
        this.password = properties.getProperty("redis.password");
        this.maxTotal = Integer.parseInt(properties.getProperty("redis.maxTotal"));
        this.maxIdle = Integer.parseInt(properties.getProperty("redis.maxIdle"));
        this.minIdle = Integer.parseInt(properties.getProperty("redis.minIdle"));
        this.testOnBorrow = Boolean.parseBoolean(properties.getProperty("redis.testOnBorrow"));
        this.testOnReturn = Boolean.parseBoolean(properties.getProperty("redis.testOnReturn"));
        this.testWhileIdle = Boolean.parseBoolean(properties.getProperty("redis.testWhileIdle"));
        this.timeBetweenEvictionRunsMillis = Integer.parseInt(properties.getProperty("redis.timeBetweenEvictionRunsMillis"));
        this.minEvictableIdleTimeMillis = Integer.parseInt(properties.getProperty("redis.minEvictableIdleTimeMillis"));
        this.numTestsPerEvictionRun = Integer.parseInt(properties.getProperty("redis.numTestsPerEvictionRun"));
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }

    public int getTimeout() {
        return timeout;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public int getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public int getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public int getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }
}
