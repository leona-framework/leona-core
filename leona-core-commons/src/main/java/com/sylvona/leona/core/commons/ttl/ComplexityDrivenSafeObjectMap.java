package com.sylvona.leona.core.commons.ttl;

import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class ComplexityDrivenSafeObjectMap<T> implements Map<Object, T> {
    private final Map<Class<?>, Map<Integer, Long>> classHashMap = new HashMap<>();
    private final Map<Long, T> backingObjectMap = new HashMap<>();
    private long counter;

    @Override
    public int size() {
        return backingObjectMap.size();
    }

    @Override
    public boolean isEmpty() {
        return backingObjectMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        Class<?> cls = key.getClass();
        Map<Integer, Long> hashes = classHashMap.get(cls);
        if (hashes == null) return false;

        int objectHash = System.identityHashCode(key);
        return hashes.get(objectHash) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return backingObjectMap.containsValue(value);
    }

    @Override
    public T get(Object key) {
        return getTFromObject(key);
    }

    @Override
    public T put(Object key, T value) {
        return putFromObject(key, value);
    }

    @Override
    public T remove(Object key) {
        return removeFromObject(key);
    }

    @Override
    public void putAll(Map<?, ? extends T> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        classHashMap.clear();
        backingObjectMap.clear();
    }

    @Override
    public @NotNull Set<Object> keySet() {
        return classHashMap.values().stream().flatMap(map -> map.keySet().stream()).collect(Collectors.toSet());
    }

    @Override
    public @NotNull Collection<T> values() {
        return backingObjectMap.values();
    }

    @Override
    public @NotNull Set<Entry<Object, T>> entrySet() {
        return backingObjectMap.entrySet().stream().map(e -> new LazyEntry<>((Object)e.getKey(), e.getValue())).collect(Collectors.toSet());
    }

    private T getTFromObject(Object key) {
        Long identifier = getObjectIdentifier(key);
        if (identifier == null) return null;

        return backingObjectMap.get(identifier);
    }

    private T putFromObject(Object key, T value) {
        Class<?> cls = key.getClass();
        Map<Integer, Long> hashes = classHashMap.computeIfAbsent(cls, c -> new HashMap<>());

        int objectHash = System.identityHashCode(key);
        Long identifier = hashes.computeIfAbsent(objectHash, h -> counter++);

        return backingObjectMap.put(identifier, value);
    }

    private T removeFromObject(Object key) {
        Long identifier = getObjectIdentifier(key);
        return identifier != null ? backingObjectMap.remove(identifier) : null;
    }

    private Long getObjectIdentifier(Object key) {
        Class<?> cls = key.getClass();
        Map<Integer, Long> hashes = classHashMap.get(cls);
        if (hashes == null) return null;

        int objectHash = System.identityHashCode(key);
        return hashes.get(objectHash);
    }


    private static class LazyEntry<TKey, TValue> implements Entry<TKey, TValue> {
        private TKey key;
        private TValue value;

        public LazyEntry(TKey key, TValue value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public TValue setValue(TValue value) {
            TValue previous = this.value;
            this.value = value;
            return previous;
        }

        public TKey getKey() {
            return this.key;
        }

        public TValue getValue() {
            return this.value;
        }
    }
}
