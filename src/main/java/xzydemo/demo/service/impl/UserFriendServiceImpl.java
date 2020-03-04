package xzydemo.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xzydemo.demo.persistence.UserFriendsMapper;
import xzydemo.demo.persistence.UserMapper;
import xzydemo.demo.service.UserFriendService;

import java.util.ArrayList;

@Service
public class UserFriendServiceImpl implements UserFriendService {
    @Autowired
    UserFriendsMapper userFriendsMapper;
    @Override
    public void addFriend(String userAccount, String friendAccount) {
        userFriendsMapper.addFriend(userAccount,friendAccount);
        userFriendsMapper.friendAdd(userAccount,friendAccount);
    }

    @Override
    public void deleteFriend(String userAccount, String friendAccount) {
        userFriendsMapper.friendDelete(friendAccount);
        userFriendsMapper.deleteFriend(userAccount);
    }

    @Override
    public ArrayList<String> getFriends(String userAccount) {
        return userFriendsMapper.getFriends(userAccount);
    }

    @Override
    public String getFriendName(String friendAccount) {
       return userFriendsMapper.getFriendName(friendAccount);
    }
}
