package com.hmofa.core.lang.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hmofa.core.lang.ICloneable;

public class UtilCollection {

	private UtilCollection() {
	}

	@SuppressWarnings("unchecked")
	public static final <K, V> List<K> cloneK(Map<K, V> source) {
		if (source == null)
			return null;

		List<K> target = new ArrayList<K>();
		for (Iterator<K> it = source.keySet().iterator(); it.hasNext();) {
			K k = it.next();
			target.add(k instanceof ICloneable ? (K) ((ICloneable<K>) k).clone() : k);
		}
		return target;
	}

	@SuppressWarnings("unchecked")
	public static final <K, V> List<V> cloneV(Map<K, V> source) {
		if (source == null)
			return null;

		List<V> target = new ArrayList<V>();
		for (Iterator<K> it = source.keySet().iterator(); it.hasNext();) {
			V v = source.get(it.next());
			target.add(v instanceof ICloneable ? (V) ((ICloneable<V>) v).clone() : v);
		}
		return target;
	}

	@SuppressWarnings("unchecked")
	public static final <K, V> Map<K, V> cloneMap(Map<K, V> source) {
		if (source == null)
			return null;

		Map<K, V> target = new LinkedHashMap<K, V>();
		for (Iterator<K> it = source.keySet().iterator(); it.hasNext();) {
			K k = it.next();
			V v = source.get(k);
			target.put(k, v instanceof ICloneable ? (V) ((ICloneable<V>) v).clone() : v);
		}
		return target;
	}

	@SuppressWarnings("unchecked")
	public static final <T> List<T> clone(List<T> source) {
		if (source == null)
			return null;

		List<T> target = new ArrayList<T>();
		for (T t : source) {
			T t1 = t instanceof ICloneable ? (T) ((ICloneable<T>) t).clone() : t;
			target.add(t1);
		}
		return target;
	}

	@SuppressWarnings("unchecked")
	public static final <T> Map<T, T> cloneMapKV(List<T> source) {
		if (source == null)
			return null;

		Map<T, T> target = new LinkedHashMap<T, T>();
		for (T t : source) {
			T t1 = t instanceof ICloneable ? (T) ((ICloneable<T>) t).clone() : t;
			target.put(t1, t1);
		}
		return target;
	}

	public static final <K, V> List<K> newListK(Map<K, V> source) {
		if (source == null)
			return null;

		List<K> target = new ArrayList<K>();
		for (Iterator<K> it = source.keySet().iterator(); it.hasNext();) {
			K k = it.next();
			target.add(k);
		}
		return target;
	}

	public static final <K, V> List<V> newListV(Map<K, V> source) {
		if (source == null)
			return null;

		List<V> target = new ArrayList<V>();
		for (Iterator<K> it = source.keySet().iterator(); it.hasNext();) {
			V v = source.get(it.next());
			target.add(v);
		}
		return target;
	}

	public static final <K, V> Map<K, V> newMap(Map<K, V> source) {
		if (source == null)
			return null;

		Map<K, V> target = new LinkedHashMap<K, V>();
		for (Iterator<K> it = source.keySet().iterator(); it.hasNext();) {
			K k = it.next();
			V v = source.get(k);
			target.put(k, v);
		}
		return target;
	}

	public static final <T> List<T> newList(List<T> source) {
		if (source == null)
			return null;

		List<T> target = new ArrayList<T>();
		for (T t : source) {
			target.add(t);
		}
		return target;
	}

	public static final <T> Map<T, T> newMapKV(List<T> source) {
		if (source == null)
			return null;

		Map<T, T> target = new LinkedHashMap<T, T>();
		for (T t : source) {
			target.put(t, t);
		}
		return target;
	}
}
