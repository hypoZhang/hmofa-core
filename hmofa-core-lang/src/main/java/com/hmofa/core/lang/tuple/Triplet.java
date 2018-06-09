package com.hmofa.core.lang.tuple;

import java.util.Collection;
import java.util.Iterator;

import com.hmofa.core.lang.tuple.parent.Value0;
import com.hmofa.core.lang.tuple.parent.Value1;
import com.hmofa.core.lang.tuple.parent.Value2;
import com.hmofa.core.exception.IllegalArgumentException;

public class Triplet<A, B, C> extends Tuple implements Value0<A>, Value1<B>, Value2<C> {

	public C _3() {
		return val2;
	}

	public B _2() {
		return val1;
	}

	public A _1() {
		return val0;
	}

	public int getSize() {
		return 3;
	}

	public Triplet(final A value0, final B value1, final C value2) {
		super(value0, value1, value2);
		this.val0 = value0;
		this.val1 = value1;
		this.val2 = value2;
	}

	private final A val0;
	private final B val1;
	private final C val2;

	private static final long serialVersionUID = -5760731199505533477L;

	public static <X> Triplet<X, X, X> fromArray(final X[] array) {
		if (array == null) {
			throw new IllegalArgumentException("Array cannot be null");
		}
		if (array.length != 3) {
			throw new IllegalArgumentException("Array must have exactly 3 elements in order to create a Triplet. Size is " + array.length);
		}
		return new Triplet<X, X, X>(array[0], array[1], array[2]);
	}

	public static <X> Triplet<X, X, X> fromCollection(final Collection<X> collection) {
		return fromIterable(collection);
	}

	public static <X> Triplet<X, X, X> fromIterable(final Iterable<X> iterable) {
		return fromIterable(iterable, 0, true);
	}

	public static <X> Triplet<X, X, X> fromIterable(final Iterable<X> iterable, int index) {
		return fromIterable(iterable, index, false);
	}

	private static <X> Triplet<X, X, X> fromIterable(final Iterable<X> iterable, int index, final boolean exactSize) {

		if (iterable == null) {
			throw new IllegalArgumentException("Iterable cannot be null (Triplet)");
		}

		boolean tooFewElements = false;

		X element0 = null;
		X element1 = null;
		X element2 = null;

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
		if (iter.hasNext()) {
			element2 = iter.next();
		} else {
			tooFewElements = true;
		}
		if (tooFewElements && exactSize) {
			throw new IllegalArgumentException("Not enough elements for creating a Triplet (3 needed)");
		}
		if (iter.hasNext() && exactSize) {
			throw new IllegalArgumentException("Iterable must have exactly 3 available elements in order to create a Triplet.");
		}
		return new Triplet<X, X, X>(element0, element1, element2);
	}
}
