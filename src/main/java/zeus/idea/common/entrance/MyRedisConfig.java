package zeus.idea.common.entrance;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import zeus.idea.common.bizc.SysInitBizc;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * 类名：MyRedisConfig
 * 公司：-----智讯云-----
 * 功能说明：
 * Redis初始化配置
 * <p>
 * 作者：jinyang.wang      创建时间：2021/2/21 20:07
 * <p>
 * 修改人：           修改时间：
 */
@Configuration
@EnableAutoConfiguration
public class MyRedisConfig {

    @Autowired
    private SysInitBizc sysInitBizc;

    @Value("${spring.redis.database.common}")
    private int commonDB;

    @Value("${spring.redis.database.notekey}")
    private int notekeyDB;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.lettuce.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.lettuce.pool.max-wait}")
    private long maxWait;

    @Bean
    public GenericObjectPoolConfig getPoolConfig(){
        // 配置redis连接池
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWait);
        return poolConfig;
    }

    @Bean(name = "redisTemplate0")
    public StringRedisTemplate getTest1RedisTemplate(){
        StringRedisTemplate srt = getStringRedisTemplate(0);
        ValueOperations vo = srt.opsForValue();

        List<Map<String, String>> retList = sysInitBizc.queryNoteKey();
        for (Map<String, String> for_map : retList) {
            vo.set(for_map.get("key"), for_map.get("value"));
        }

        return srt;
    }

    @Bean(name = "redisTemplate1")
    public StringRedisTemplate getTest2RedisTemplate(){
        // 构建工厂对象
        return getStringRedisTemplate(1);
    }

    /**
     * 方法功能说明：生成Redis连接
     *
     * @param database
     * @return
     *
     * 作者:jinyang.wang     创建日期:2021/3/13 22:22
     *
     * 修改人:          修改日期:
     */
    private StringRedisTemplate getStringRedisTemplate(int database) {
        // 构建工厂对象
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        configuration.setPassword(RedisPassword.of(password));
        LettucePoolingClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(timeout)).poolConfig(getPoolConfig()).build();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration, clientConfiguration);
        // 设置使用的redis数据库
        factory.setDatabase(database);
        // 重新初始化工厂
        factory.afterPropertiesSet();
        return new StringRedisTemplate(factory);
    }

}