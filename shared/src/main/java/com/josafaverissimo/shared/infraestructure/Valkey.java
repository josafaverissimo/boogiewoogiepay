package com.josafaverissimo.shared.infraestructure;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.josafaverissimo.shared.enums.ValkeyQueueEnum;

import io.valkey.Jedis;
import io.valkey.JedisPool;
import io.valkey.JedisPoolConfig;

public final class Valkey {
  private final static JedisPoolConfig jedisPoolConfig;
  private final static JedisPool jedisPool;
  private final static Logger logger;

  static {
    jedisPoolConfig = new io.valkey.JedisPoolConfig();
    jedisPoolConfig.setMaxTotal(32);
    jedisPoolConfig.setMaxIdle(32);
    jedisPoolConfig.setMinIdle(16);

    jedisPool = new JedisPool(jedisPoolConfig, "localhost", 9927);

    logger = LoggerFactory.getLogger(Valkey.class);
  }

  private Valkey() {}

  private static boolean withJedis(Consumer<Jedis> consumer) {
    try (Jedis jedis = jedisPool.getResource()) {
      consumer.accept(jedis);

      return true;
    } catch(Exception e) {
      logger.error(String.format("Failed to connect on Valkey: %s", e.toString()));

      return false;
    }
  }

  public static boolean pushQueue(ValkeyQueueEnum queue, String data) {
    return withJedis(jedis -> jedis.lpush(queue.getQueue(), data));
  }
  
  public static boolean subscribeQueue(
    ValkeyQueueEnum queue,
    Consumer<Optional<String>> consumer
  ) {
    return withJedis(jedis -> {
      List<String> result = jedis.brpop(0, queue.getQueue());

      if (result == null || result.size() < 2) {
        consumer.accept(Optional.empty());

        return;
      }

      consumer.accept(Optional.of(result.get(1)));
    });
  }
}
