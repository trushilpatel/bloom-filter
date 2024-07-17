package bloomfilter;

import bloomfilter.redis.RedisConfigs;

import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
              try {
                  RedisConfigs configs = new RedisConfigs("redis.properties");
              } catch (IOException e) {
                  System.out.println("Failed to load Redis Configuration: " + e.getMessage());
              }
    }
}