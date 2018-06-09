package com.hmofa.core.lang.tuple;

import java.util.Collection;
import java.util.Iterator;

import com.hmofa.core.lang.tuple.parent.Value0;
import com.hmofa.core.lang.tuple.parent.Value1;
import com.hmofa.core.exception.IllegalArgumentException;

public class Pair<A, B> extends Tuple implements Value0<A>, Value1<B> {

	public int getSize() {
		return 2;
	}

	public A _1() {
		return val0;
	}

	public B _2() {
		return val1;
	}

	public Pair(final A value0, final B value1) {
		super(value0, value1);
		this.val0 = value0;
		this.val1 = value1;
	}

	private final A val0;
	private final B val1;

	private static final long serialVersionUID = -6232870682028370145L;

	public static <X> Pair<X, X> fromArray(final X[] array) {
		if (array == null) {
			throw new IllegalArgumentException("Array cannot be null");
		}
		if (array.length != 2) {
			throw new IllegalArgumentException("Array must have exactly 2 elements in order to create a Pair. Size is " + array.length);
		}
		return new Pair<X, X>(array[0], array[1]);
	}

	public static <X> Pair<X, X> fromCollection(final Collection<X> collection) {
		return fromIterable(collection);
	}

	public static <X> Pair<X, X> fromIterable(final Iterable<X> iterable) {
		return fromIterable(iterable, 0, true);
	}

	public static <X> Pair<X, X> fromIterable(final Iterable<X> iterable, int index) {
		return fromIterable(iterable, index, false);
	}

	private static <X> Pair<X, X> fromIterable(final Iterable<X> iterable, int index, final boolean exactSize) {

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
			throw new IllegalArgumentException("Not enough elements for creating a Pair (2 needed)");
		}
		if (iter.hasNext() && exactSize) {
			throw new IllegalArgumentException("Iterable must have exactly 2 available elements in order to create a Pair.");
		}
		return new Pair<X, X>(element0, element1);
	}
}
