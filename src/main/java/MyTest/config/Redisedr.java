package MyTest.config;

//import org.springframework.data.redis;
//
//import java.io.Serializable;
//
//@Configuration
//@AutoConfigureAfter(RedisAutoConfiguration.class)
//public class Redisedr {
//    @Bean
//    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory redisConnectionFactory){
//        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
//        //键的序列化
//        template.setKeySerializer(new StringRedisSerializer());
//        //值的序列化
//        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        template.setConnectionFactory(redisConnectionFactory);
//        return template;
//    }
//}
