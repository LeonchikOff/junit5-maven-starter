package org.example.junit.dao;

import java.util.HashMap;
import java.util.Map;

public class UserDaoMock extends UserDao {
    private final Map<Integer, Boolean> answers = new HashMap<>();

    @Override
    public boolean delete(Integer userId) {
        return answers.getOrDefault(userId, false);
    }
}
