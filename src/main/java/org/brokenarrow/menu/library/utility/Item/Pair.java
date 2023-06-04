package org.brokenarrow.menu.library.utility.Item;

public class Pair<K, V> {

	private final K firstValue;
	private final V secondValue;


	public Pair(K firstValue, V secondValue) {

		this.firstValue = firstValue;
		this.secondValue = secondValue;
	}


	public K getFirst() {
		return firstValue;
	}

	public V getSecond() {
		return secondValue;
	}

	@Override
	public String toString() {
		return firstValue + "_" + secondValue;
	}
}
