package me.jduv.java.util;

/**
 * Defines a function that picks a key from an object. This is used primarly to convert
 * lists to maps.
 * 
 * @param <K>
 *            The type of keys.
 * @param <V>
 *            The type of objects.
 */
public abstract interface KeySelector<K, V> {
    /**
     * Picks a key for the target object.
     * 
     * @param target
     *            The target object.
     * @return The key value.
     */
    public abstract K getKey(V target);
}
