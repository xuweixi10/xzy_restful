package xzydemo.demo.persistence;

import java.util.ArrayList;

public interface UserFriendsMapper {
    void addFriend(String userAccount,String friendAccount);
    void friendAdd(String userAccount,String friendAccount);
    void deleteFriend(String userAccount);
    void friendDelete(String friendAccount);
    ArrayList<String> getFriends(String userAccount);
    String getFriendName(String friendAccount);
}
