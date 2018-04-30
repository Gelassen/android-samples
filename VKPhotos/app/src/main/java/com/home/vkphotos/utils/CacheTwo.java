package com.home.vkphotos.utils;

import java.util.TreeMap;

public class CacheTwo<T> {

    private static final int IN_MEMORY_CACHE_LIMIT = 100;

    private int limit = IN_MEMORY_CACHE_LIMIT;
    private TreeMap<String, T> map = new TreeMap<>();

    public void onLowMemory() {
        map.clear();
    }

    public CacheTwo() {}

    public CacheTwo(final int maxSize) {
        limit = maxSize;
    }

    public T getFromMemoryCache(final String key) {
        if (!map.containsKey(key)) return null;
        return (T) map.get(key);
    }

    public void addInMemoryCache(String key, T bitmap) {
        if (map.size() > limit) {
            final int threshold = (int) (0.75 * limit);
            while (map.size() > threshold) {
                map.remove(map.lastKey());
            }
            map.put(key, bitmap);
        } else {
            map.put(key, bitmap);
        }
    }

    public void clean() {
        map.clear();
    }
}
