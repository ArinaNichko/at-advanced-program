package org.example.session;

import org.example.exception.TestExecutionException;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;

public class SessionImpl implements Session {

    private static final ConcurrentHashMap<SessionKey, Object> dataMap = new ConcurrentHashMap<>();

    @Override
    public void put(SessionKey key, Object o) {
        checkAllNotNull(key, o);
        dataMap.put(key, o);
    }

    @Override
    public <T> T get(SessionKey key, Class<T> asType) {
        checkAllNotNull(key, asType);
        checkIfExist(key);
        return asType.cast(dataMap.get(key));
    }

    @Override
    public Object get(SessionKey key) {
        checkAllNotNull(key);
        checkIfExist(key);
        return dataMap.get(key);
    }

    public void clear() {
        dataMap.clear();
    }

    public void checkIfExist(SessionKey key) {
        if (!dataMap.containsKey(key)) {
            throw new TestExecutionException(String.format("Entry with key = %s not found!", key));
        }
    }

    private void checkAllNotNull(Object... args) {
        Arrays.asList(args).forEach(o -> {
            if (isNull(o)) {
                throw new TestExecutionException("Parameter cannot be null");
            }
        });
    }
}
