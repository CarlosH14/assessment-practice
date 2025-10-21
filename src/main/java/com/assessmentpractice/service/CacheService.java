package com.assessmentpractice.service;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CacheService {

    private static final int MAX_ENTRIES = 100;
    
    private final Map<String, String> cache = new LinkedHashMap<String, String>(MAX_ENTRIES + 1, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            return size() > MAX_ENTRIES;
        }
    };

    public synchronized void put(String key, String value) {
        cache.put(key, value);
    }

    public synchronized String get(String key) {
        return cache.get(key);
    }

    public synchronized void clear() {
        cache.clear();
    }

    public synchronized int size() {
        return cache.size();
    }

    public synchronized Map<String, String> getAllEntries() {
        return new LinkedHashMap<>(cache);
    }
}
