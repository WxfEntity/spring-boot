import com.wxf.MyApplication;
import javafx.application.Application;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MyApplication.class)
public class ApplicationTest {
    Logger logger = LoggerFactory.getLogger(Application.class);
    /*@Autowired
    private UserRepository userRepository;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, User> redisTemplate;
    @Autowired
    private MongodbUserRepository mongodbUserRepository;
    @Test
    public void contextLoads() {
        System.out.print(userRepository.findByUserName("wuxuefeng"));
        logger.info("测试日志效果");
        logger.error("错误日志");
        logger.warn("warm级别的日志");
    }
    @Test
    public  void redisTest(){
        // 保存字符串
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
        // 保存对象
        User user = new User();
        user.setEmail("@qda");
        user.setNickName("fsfs");
        user.setRegTime("fdsfsd");
        user.setPassWord("132321");
        user.setUserName("keyui");
        redisTemplate.opsForValue().set(user.getUserName(), user);
        redisTemplate.opsForValue().set(user.getUserName(), user);
        redisTemplate.opsForValue().set(user.getUserName(), user);
        Assert.assertEquals("132321", redisTemplate.opsForValue().get("keyui").getPassWord());
        Assert.assertEquals("132321", redisTemplate.opsForValue().get("keyui").getPassWord());
        Assert.assertEquals("132321", redisTemplate.opsForValue().get("keyui").getPassWord());
    }

    @Test
    public void mongondbTest(){
        mongodbUserRepository.save(new MongodbUser(1L, "didi", 30));
        mongodbUserRepository.save(new MongodbUser(2L, "mama", 40));
        mongodbUserRepository.save(new MongodbUser(3L, "kaka", 50));
        Assert.assertEquals(1, userRepository.findAll().size());
        // 删除一个User，再验证User总数
        MongodbUser u = mongodbUserRepository.findOne(1L);
        mongodbUserRepository.delete(u);
        Assert.assertEquals(2, mongodbUserRepository.findAll().size());
        // 删除一个User，再验证User总数
        u = mongodbUserRepository.findByUsername("mama");
        mongodbUserRepository.delete(u);
        Assert.assertEquals(1, mongodbUserRepository.findAll().size());
    }*/
}
