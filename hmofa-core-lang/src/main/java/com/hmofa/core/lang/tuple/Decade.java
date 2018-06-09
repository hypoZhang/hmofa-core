package com.hmofa.core.lang.tuple;

import java.util.Collection;
import java.util.Iterator;

import com.hmofa.core.lang.tuple.parent.Value0;
import com.hmofa.core.lang.tuple.parent.Value1;
import com.hmofa.core.lang.tuple.parent.Value2;
import com.hmofa.core.lang.tuple.parent.Value3;
import com.hmofa.core.lang.tuple.parent.Value4;
import com.hmofa.core.lang.tuple.parent.Value5;
import com.hmofa.core.lang.tuple.parent.Value6;
import com.hmofa.core.lang.tuple.parent.Value7;
import com.hmofa.core.lang.tuple.parent.Value8;
import com.hmofa.core.lang.tuple.parent.Value9;
import com.hmofa.core.exception.IllegalArgumentException;

public class Decade<A, B, C, D, E, F, G, H, I, J> extends Tuple implements Value0<A>, Value1<B>, Value2<C>, Value3<D>, Value4<E>, Value5<F>,
		Value6<G>, Value7<H>, Value8<I>, Value9<J> {

	public J _10() {
		return val9;
	}

	public I _9() {
		return val8;
	}

	public H _8() {
		return val7;
	}

	public G _7() {
		return val6;
	}

	public F _6() {
		return val5;
	}

	public E _5() {
		return val4;
	}

	public D _4() {
		return val3;
	}

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
		return 10;
	}

	public Decade(final A value0, final B value1, final C value2, final D value3, final E value4, final F value5, final G value6, final H value7,
			final I value8, final J value9) {
		super(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9);
		this.val0 = value0;
		this.val1 = value1;
		this.val2 = value2;
		this.val3 = value3;
		this.val4 = value4;
		this.val5 = value5;
		this.val6 = value6;
		this.val7 = value7;
		this.val8 = value8;
		this.val9 = value9;
	}

	private final A val0;
	private final B val1;
	private final C val2;
	private final D val3;
	private final E val4;
	private final F val5;
	private final G val6;
	private final H val7;
	private final I val8;
	private final J val9;

	private static final long serialVersionUID = -3476170658408430268L;

	public static <X> Decade<X, X, X, X, X, X, X, X, X, X> fromArray(final X[] array) {
		if (array == null) {
			throw new IllegalArgumentException("Array cannot be null");
		}
		if (array.length != 10) {
			throw new IllegalArgumentException("Array must have exactly 10 elements in order to create a Decade. Size is " + array.length);
		}
		return new Decade<X, X, X, X, X, X, X, X, X, X>(array[0], array[1], array[2], array[3], array[4], array[5], array[6], array[7], array[8],
				array[9]);
	}

	public static <X> Decade<X, X, X, X, X, X, X, X, X, X> fromCollection(final Collection<X> collection) {
		return fromIterable(collection);
	}

	public static <X> Decade<X, X, X, X, X, X, X, X, X, X> fromIterable(final Iterable<X> iterable) {
		return fromIterable(iterable, 0, true);
	}

	public static <X> Decade<X, X, X, X, X, X, X, X, X, X> fromIterable(final Iterable<X> iterable, int index) {
		return fromIterable(iterable, index, false);
	}

	private static <X> Decade<X, X, X, X, X, X, X, X, X, X> fromIterable(final Iterable<X> iterable, int index, final boolean exactSize) {

		if (iterable == null) {
			throw new IllegalArgumentException("Iterable cannot be null");
		}

		boolean tooFewElements = false;

		X element0 = null;
		X element1 = null;
		X element2 = null;
		X element3 = null;
		X element4 = null;
		X element5 = null;
		X element6 = null;
		X element7 = null;
		X element8 = null;
		X element9 = null;

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
		if (iter.hasNext()) {
			element3 = iter.next();
		} else {
			tooFewElements = true;
		}
		if (iter.hasNext()) {
			element4 = iter.next();
		} else {
			tooFewElements = true;
		}
		if (iter.hasNext()) {
			element5 = iter.next();
		} else {
			tooFewElements = true;
		}
		if (iter.hasNext()) {
			element6 = iter.next();
		} else {
			tooFewElements = true;
		}
		if (iter.hasNext()) {
			element7 = iter.next();
		} else {
			tooFewElements = true;
		}
		if (iter.hasNext()) {
			element8 = iter.next();
		} else {
			tooFewElements = true;
		}
		if (iter.hasNext()) {
			element9 = iter.next();
		} else {
			tooFewElements = true;
		}
		if (tooFewElements && exactSize) {
			throw new IllegalArgumentException("Not enough elements for creating a Decade (10 needed)");
		}
		if (iter.hasNext() && exactSize) {
			throw new IllegalArgumentException("Iterable must have exactly 10 available elements in order to create a Decade.");
		}
		return new Decade<X, X, X, X, X, X, X, X, X, X>(element0, element1, element2, element3, element4, element5, element6, element7, element8,
				element9);

	}
}
