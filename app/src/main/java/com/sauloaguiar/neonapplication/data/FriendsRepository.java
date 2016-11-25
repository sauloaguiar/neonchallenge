package com.sauloaguiar.neonapplication.data;

import java.util.List;

/**
 * Created by sauloaguiar on 11/25/16.
 */
public interface FriendsRepository {

    interface FriendsServiceCallback<T> {
        void onLoaded(T friends);
    }

    void getAllFriends(FriendsServiceCallback<List<Friend>> callback);
    void getFriend(String friendId, FriendsServiceCallback<Friend> callback);

}
