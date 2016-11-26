package com.sauloaguiar.neonapplication.data;

import android.util.ArrayMap;

import com.sauloaguiar.neonapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sauloaguiar on 11/25/16.
 */
public class InMemoryFriendsRepository implements FriendsRepository {

    private ArrayMap<String, Friend> cachedFriends;

    public InMemoryFriendsRepository() {
        cachedFriends = new ArrayMap<>();
        generateFriends();
    }

    @Override
    public void getAllFriends(FriendsServiceCallback<List<Friend>> callback) {
        List<Friend> friends = new ArrayList<>(cachedFriends.values());
        callback.onLoaded(friends);
    }

    @Override
    public void getFriend(String friendId, FriendsServiceCallback<Friend> callback) {
        callback.onLoaded(cachedFriends.get(friendId));
    }

    private void generateFriends(){
        int friendId = 0;
        Friend f = new Friend(friendId, "Daniele Targuerian", "(12) 99878-9989", R.drawable.womam_1);
        cachedFriends.put(String.valueOf(friendId), f);
        friendId++;

        f = new Friend(friendId, "Alexandre Santos", "(31)98767-1234", R.drawable.men_1);
        cachedFriends.put(String.valueOf(friendId), f);
        friendId++;

        f = new Friend(friendId, "Angelica Batista", "(11)99887-9980", R.drawable.womam_2);
        cachedFriends.put(String.valueOf(friendId), f);
        friendId++;

        f = new Friend(friendId, "Marina Olinda", "(55)98712-3123", R.drawable.womam_3);
        cachedFriends.put(String.valueOf(friendId), f);
        friendId++;

        f = new Friend(friendId, "Joao das Neves", "(31)86796-1231", R.drawable.men_2);
        cachedFriends.put(String.valueOf(friendId), f);
        friendId++;

        f = new Friend(friendId, "Alberto Rangel", "(11)98989-1234", R.drawable.men_3);
        cachedFriends.put(String.valueOf(friendId), f);
        friendId++;

        f = new Friend(friendId, "Sebastiao Sauro", "(88)88779-4332", R.drawable.men_4);
        cachedFriends.put(String.valueOf(friendId), f);
        friendId++;

        f = new Friend(friendId, "Ingrid Santos", "(41)97989-1244", R.drawable.womam_4);
        cachedFriends.put(String.valueOf(friendId), f);
        friendId++;

        f = new Friend(friendId, "Alexandre Santos", "(85)99980-9291", R.drawable.men_5);
        cachedFriends.put(String.valueOf(friendId), f);
        friendId++;
    }

}
