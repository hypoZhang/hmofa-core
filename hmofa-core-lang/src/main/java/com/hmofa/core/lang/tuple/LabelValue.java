package com.hmofa.core.lang.tuple;

import java.util.Collection;
import java.util.Iterator;

import com.hmofa.core.lang.tuple.parent.ValueLabel;
import com.hmofa.core.lang.tuple.parent.ValueValue;
import com.hmofa.core.exception.IllegalArgumentException;

public class LabelValue<L, V> extends Tuple implements ValueLabel<L>, ValueValue<V> {

	public V getValue() {
		return value;
	}

	public L getLabel() {
		return label;
	}

	public int getSize() {
		return 2;
	}

	public LabelValue(final L label, final V value) {
		super(label, value);
		this.label = label;
		this.value = value;
	}

	private final L label;
	private final V value;

	private static final long serialVersionUID = -6026904694937089410L;

	public static <X> LabelValue<X, X> fromArray(final X[] array) {
		if (array == null) {
			throw new IllegalArgumentException("Array cannot be null");
		}
		if (array.length != 2) {
			throw new IllegalArgumentException("Array must have exactly 2 elements in order to create a LabelValue. Size is " + array.length);
		}
		return new LabelValue<X, X>(array[0], array[1]);
	}

	public static <X> LabelValue<X, X> fromCollection(final Collection<X> collection) {
		return fromIterable(collection);
	}

	public static <X> LabelValue<X, X> fromIterable(final Iterable<X> iterable) {
		return fromIterable(iterable, 0, true);
	}

	public static <X> LabelValue<X, X> fromIterable(final Iterable<X> iterable, int index) {
		return fromIterable(iterable, index, false);
	}

	private static <X> LabelValue<X, X> fromIterable(final Iterable<X> iterable, int index, final boolean exactSize) {

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
			throw new IllegalArgumentException("Not enough elements for creating a LabelValue (2 needed)");
		}

		if (iter.hasNext() && exactSize) {
			throw new IllegalArgumentException("Iterable must have exactly 2 available elements in order to create a LabelValue.");
		}

		return new LabelValue<X, X>(element0, element1);

	}
}
