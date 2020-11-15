package dunaeva.polina.finalproject.service;

import dunaeva.polina.finalproject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;


@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String,User> template;

    public void setData(String key, User value) {
        ValueOperations<String, User> ofv = template.opsForValue();
        ofv.set(key, value, 24*60*60*1000);
    }

    public User getValue(String key) {
        ValueOperations<String, User> ofv = template.opsForValue();
        return ofv.get(key);
    }

    public boolean keyExist(String key){
        return getValue(key) != null;
    }

}
