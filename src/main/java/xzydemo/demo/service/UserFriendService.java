package xzydemo.demo.service;

import java.util.ArrayList;

public interface UserFriendService {
    void addFriend(String userAccount,String friendAccount);
    void deleteFriend(String userAccount,String friendAccount);
    ArrayList<String> getFriends(String userAccount);
    String getFriendName(String friendAccount);
}
