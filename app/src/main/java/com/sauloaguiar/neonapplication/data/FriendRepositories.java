package com.sauloaguiar.neonapplication.data;

/**
 * Created by sauloaguiar on 11/25/16.
 */

public class FriendRepositories {

    private FriendRepositories() {}

    private static FriendsRepository repository = null;

    public synchronized static FriendsRepository getInMemoryRepo() {
        if(null == repository) {
            repository = new InMemoryFriendsRepository();
        }
        return repository;
    }


}
