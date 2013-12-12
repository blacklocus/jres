package com.blacklocus.misc;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;

/**
 * A map that ignores null keys or values. The key won't be added at all if it is null or its value is null.
 * Only applies to {@link #put(Object, Object)}.
 * <p>
 * Use {@link #of(Object, Object)}... as a transparent replacement of {@link ImmutableMap#of()} where nulls are
 * possible, because ImmutableMap will not accept null values anyways.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class NoNullsMap<K, V> extends HashMap<K, V> {

    public static <K, V> ImmutableMap<K, V> of(K k1, V v1) {
        NoNullsMap<K, V> map = new NoNullsMap<K, V>(1);
        map.put(k1, v1);
        return ImmutableMap.copyOf(map);

    }

    public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        NoNullsMap<K, V> map = new NoNullsMap<K, V>(3);
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return ImmutableMap.copyOf(map);
    }

    private NoNullsMap(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Ignores null keys or values. The key won't be added at all if it is null or its value is null.
     */
    @Override
    public V put(K key, V value) {
        return key != null && value != null ? super.put(key, value) : null;
    }
}
