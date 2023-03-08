package com.marketplace.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Keys implements Map<String, String> {
    Map<String, String> keys;

    public Keys() {
        keys = new HashMap<>();
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public boolean isEmpty() {
        return keys.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return keys.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return keys.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return keys.get(key);
    }

    @Override
    public String put(String key, String value) {
        return keys.put(key, value);
    }

    @Override
    public String remove(Object key) {
        return keys.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        keys.putAll(m);
    }

    @Override
    public void clear() {
        keys.clear();
    }

    @Override
    public Set<String> keySet() {
        return keys.keySet();
    }

    @Override
    public Collection<String> values() {
        return keys.values();
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return keys.entrySet();
    }
}
