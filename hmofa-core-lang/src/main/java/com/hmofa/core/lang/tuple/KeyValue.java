package com.hmofa.core.lang.tuple;

import java.util.Collection;
import java.util.Iterator;

import com.hmofa.core.lang.tuple.parent.ValueKey;
import com.hmofa.core.lang.tuple.parent.ValueValue;
import com.hmofa.core.exception.IllegalArgumentException;

public class KeyValue<K, V> extends Tuple implements ValueKey<K>, ValueValue<V> {

	public V getValue() {
		return value;
	}

	public K getKey() {
		return key;
	}

	public int getSize() {
		return 2;
	}

	public KeyValue(final K key, final V value) {
		super(key, value);
		this.key = key;
		this.value = value;
	}

	private final K key;
	private final V value;

	private static final long serialVersionUID = -4696533467462080175L;

	public static <X> KeyValue<X, X> fromArray(final X[] array) {
		if (array == null) {
			throw new IllegalArgumentException("Array cannot be null");
		}
		if (array.length != 2) {
			throw new IllegalArgumentException("Array must have exactly 2 elements in order to create a KeyValue. Size is " + array.length);
		}
		return new KeyValue<X, X>(array[0], array[1]);
	}

	public static <X> KeyValue<X, X> fromCollection(final Collection<X> collection) {
		return fromIterable(collection);
	}

	public static <X> KeyValue<X, X> fromIterable(final Iterable<X> iterable) {
		return fromIterable(iterable, 0, true);
	}

	public static <X> KeyValue<X, X> fromIterable(final Iterable<X> iterable, int index) {
		return fromIterable(iterable, index, false);
	}

	private static <X> KeyValue<X, X> fromIterable(final Iterable<X> iterable, int index, final boolean exactSize) {

		if (iterable == null) {
			throw new IllegalArgumentException("Iterable cannot be null");
		}

		boolean tooFewElements = false;

		X element0 = null;
		X element1 = null;

		final Iterator<X> iter = iterable.iterator();

		int i = 0;
		while (i < index) {
			if (iter.hasNext()) {
				iter.next();
			} else {
				tooFewElements = true;
			}
			i++;
		}

		if (iter.hasNext()) {
			element0 = iter.next();
		} else {
			tooFewElements = true;
		}
		if (iter.hasNext()) {
			element1 = iter.next();
		} else {
			tooFewElements = true;
		}
		if (tooFewElements && exactSize) {
			throw new IllegalArgumentException("Not enough elements for creating a KeyValue (2 needed)");
		}
		if (iter.hasNext() && exactSize) {
			throw new IllegalArgumentException("Iterable must have exactly 2 available elements in order to create a KeyValue.");
		}
		return new KeyValue<X, X>(element0, element1);
	}
}
