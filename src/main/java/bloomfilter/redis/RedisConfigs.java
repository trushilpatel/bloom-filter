package bloomfilter.redis;

import java.io.IOException;
import java.io.InputStream;
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
