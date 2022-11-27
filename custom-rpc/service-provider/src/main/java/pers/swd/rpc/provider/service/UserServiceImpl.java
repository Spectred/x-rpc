package pers.swd.rpc.provider.service;

import pers.swd.rpc.api.IUserService;
import pers.swd.rpc.pojo.User;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements IUserService {

    Map<Integer, User> userMap = new HashMap<>();

    @Override
    public User getById(int id) {
        if (userMap.size() == 0) {
            User user1 = new User();
            user1.setId(1);
            user1.setName("Jack");
            User user2 = new User();
            user1.setId(2);
            user2.setName("Rose");
            userMap.put(user1.getId(), user1);
            userMap.put(user2.getId(), user2);
        }
        return userMap.get(id);
    }
}
