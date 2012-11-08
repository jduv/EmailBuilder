package me.jduv.java.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Some basic collections utility methods.
 */
public class Collections {
    /**
     * Checks to see if the target list is empty, and if it is returns null.
     * 
     * @param <T>
     *            The type of objects inside the list.
     * @param list
     *            The list to check.
     * @return Null if the list is null or empty, else the list.
     */
    public static <T> List<T> nonEmptyListOrNull(List<T> list) {
        return isNullOrEmpty(list) ? null : list;
    }

    /**
     * Checks to see if the target map is empty, and if it is returns null.
     * 
     * @param <T>
     *            The type of objects inside the list.
     * @param map
     *            The map to check.
     * @return Null if the map is null or empty, else the map.
     */
    public static <K, V> Map<K, V> nonEmptyMapOrNull(Map<K, V> map) {
        return isNullOrEmpty(map) ? null : map;
    }

    /**
     * Gets the first, and hopefully only, element in the target list. If the list has
     * more than one element, this method will return null.
     * 
     * @param <T>
     *            The type of objects the list holds.
     * @param list
     *            The list.
     * @return The only object in the list, or null if the list is null or has more than
     *         one element in it.
     */
    public static <T> T singleOrNull(List<T> list) {
        return hasOneElement(list) ? list.get(0) : null;
    }

    /**
     * Simple method to return if a collection has a single element or not.
     * 
     * @return True if the target collection has one element, false if it's null or has
     *         many elements.
     */
    public static boolean hasOneElement(List<?> list) {
        return list != null && list.size() == 1;
    }

    /**
     * Simple method to return is a collection is null or empty.
     * 
     * @param list
     *            The list to test.
     * @return True if the target collection is null or has no elements, false otherwise.
     */
    public static boolean isNullOrEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * Simple method to return if the Map is null or empty.
     * 
     * @param map
     *            The map to test.
     * @return True if the map is null or has no elements, false otherwise.
     */
    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Converts the target collection into a list.
     * 
     * @param <T>
     *            The type of objects contained within the list and collection.
     * @param collection
     *            The collection to convert.
     * @return A list containing all the values inside the target collection.
     */
    public static <T> List<T> toList(Collection<T> collection) {
        List<T> list = null;
        if (collection != null) {
            if (collection instanceof List) {
                list = (List<T>) collection;
            }
            else {
                list = new ArrayList<T>(collection);
            }
        }
        return list;
    }

    /**
     * Converts the target list into a map using the key selector interface to pick the
     * key.
     * 
     * @param <K>
     *            The type of keys.
     * @param <V>
     *            The type of values.
     * @param keySelector
     *            The key selector.
     * @param list
     *            The list of values to convert.
     * @return A map of the target values.
     */
    public static <K, V> Map<K, V> toMap(KeySelector<K, V> keySelector, List<V> list) {
        if (keySelector == null) {
            throw new IllegalArgumentException("Key selector cannot be null!");
        }

        Map<K, V> map = new HashMap<K, V>();
        if (list != null) {
            for (V value : list) {
                map.put(keySelector.getKey(value), value);
            }
        }

        return map;
    }

    /**
     * Returns an unmodifiable copy of the target list. If the list is null or empty, then
     * this method will return null.
     * 
     * @param <T>
     *            The type of objects.
     * @param list
     *            The list.
     * @return An unmodifiable list created from the input or null if the input list is
     *         null or empty.
     */
    public static <T> List<T> unmodifiableListOrNull(List<? extends T> list) {
        return Collections.isNullOrEmpty(list) ? null : java.util.Collections.unmodifiableList(list);
    }

    /**
     * Returns an unmodifiable copy of the target map. If the list is null or empty, then
     * this method will return null.
     * 
     * @param <K>
     *            The key types.
     * @param <V>
     *            The value types.
     * @param map
     *            The map.
     * @return An unmodifiable map created from the input or null if the input map is null
     *         or empty.
     */
    public static <K, V> Map<K, V> unmodifiableMapOrNull(Map<? extends K, ? extends V> map) {
        return Collections.isNullOrEmpty(map) ? null : java.util.Collections.unmodifiableMap(map);
    }

    /**
     * Returns an unmodifiable copy of the target set. If the list is null or empty, then
     * this method will return null.
     * 
     * @param <T>
     *            The type of objects.
     * @param map
     *            The map.
     * @return An unmodifiable set created from the input or null if the input map is null
     *         or empty.
     */
    public static <T> Set<T> unmodifiableSetOrNull(Set<? extends T> set) {
        return Collections.isNullOrEmpty(set) ? null : java.util.Collections.unmodifiableSet(set);
    }
}
