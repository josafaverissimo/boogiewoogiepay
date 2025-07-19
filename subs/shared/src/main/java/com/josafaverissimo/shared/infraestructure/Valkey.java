package com.josafaverissimo.shared.infraestructure;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.josafaverissimo.shared.enums.EnvVarEnum;
import com.josafaverissimo.shared.enums.ValkeyQueueEnum;

import io.valkey.Jedis;
import io.valkey.JedisPool;
import io.valkey.JedisPoolConfig;
import io.valkey.exceptions.JedisConnectionException;

public final class Valkey {
  private static Valkey instance;
  private final JedisPoolConfig jedisPoolConfig;
  private final JedisPool jedisPool;
  private final Logger logger;

  private Valkey() {
    jedisPoolConfig = new io.valkey.JedisPoolConfig();
    jedisPoolConfig.setMaxTotal(4);
    jedisPoolConfig.setMaxIdle(4);
    jedisPoolConfig.setMinIdle(1);

    var valkeyHost = AppEnv.get(EnvVarEnum.VALKEY_HOST).orElse("bg-valkey");
    int valkeyPort = 0;
    var rawValkeyPort = AppEnv.get(EnvVarEnum.VALKEY_PORT).orElse("6379");

    logger = LoggerFactory.getLogger(Valkey.class);

    try {
      valkeyPort = Integer.valueOf(rawValkeyPort);

    } catch(Exception e) {
      logger.error(
        String.format("Valkey port is not a valid int: %s", rawValkeyPort)
      );

      valkeyPort = 6379;
    }

    logger.info(
      String.format(
        "Trying to connect to valkey: %s, %d", valkeyHost, valkeyPort
      )
    );

    jedisPool = new JedisPool(jedisPoolConfig, valkeyHost, valkeyPort);
  }

  public static Valkey getInstance() {
    if(Valkey.instance == null) {
      Valkey.instance = new Valkey();
    }

    return Valkey.instance;
  }

  private boolean withJedis(Consumer<Jedis> consumer) {
    try (Jedis jedis = this.jedisPool.getResource()) {
      consumer.accept(jedis);

      return true;
    } catch(Exception e) {
      logger.error(String.format("Failed to connect on Valkey: %s", e.toString()));

      return false;
    }
  }

  public boolean pushQueue(ValkeyQueueEnum queue, String data) {
    return withJedis(jedis -> jedis.lpush(queue.getQueue(), data));
  }
  
  public boolean subscribeQueue(
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
