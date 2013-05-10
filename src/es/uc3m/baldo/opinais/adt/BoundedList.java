package es.uc3m.baldo.opinais.adt;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * BoundedList.
 * <p>A bounded list is a particular type of list whose size is bounded.</p>
 * <p>If the maximum number of items is exceeded, then the oldest item
 * in the list is removed.</p>
 * <p>This list may be used for some evolutionary algorithms to set an
 * stop condition based on stagnation.</p>
 *
 * @param <T> the type of the objects stored in this list.
 * 
 * @see java.util.List
 * 
 * @author Alejandro Baldominos
 */
public class BoundedList<T> implements List<T> {

	/*
	 *  Queue bounding.
	 */
	private int bound;
	
	/*
	 *  Underlying data structure.
	 */
	private List<T> list;
	
	/**
	 * <p>Builds a new bounded list.</p>
	 * @param bound the maximum number of items this list
	 * can store.
	 */
	public BoundedList (int bound) {
		this.bound = bound;
		this.list = new LinkedList<T>();
	}
	
	/**
	 * {@inheritDoc}
	 * <p>This operation is not supported in a bounded list.</p>
	 */
	@Override
	public boolean addAll (Collection<? extends T> items) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear () {
		list.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains (Object item) {
		return list.contains(item);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsAll (Collection<?> item) {
		return list.containsAll(item);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty () {
		return list.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<T> iterator () {
		return list.iterator();
	}

	/**
	 * {@inheritDoc}
	 * <p>This operation is unsupported in a bounded list.</p>
	 */
	@Override
	public boolean remove (Object item) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * <p>This operation is unsupported in a bounded list.</p>
	 */
	@Override
	public boolean removeAll (Collection<?> items) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * <p>This operation is unsupported in a bounded list.</p>
	 */
	@Override
	public boolean retainAll (Collection<?> items) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size () {
		return list.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray () {
		return list.toArray();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray (T[] array) {
		return list.toArray(array);
	}

	/**
	 * {@inheritDoc}
	 * <p>If the bounded list is full, the oldest item
	 * is removed.</p>
	 */
	@Override
	public boolean add (T item) {
		if (size() >= bound) {
			list.remove(0);
		}
		
		return list.add(item);
	}

	/**
	 * {@inheritDoc}
	 * <p>This operation is unsupported in a bounded list.</p>
	 */
	@Override
	public void add (int index, T element) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * <p>This operation is unsupported in a bounded list.</p>
	 */
	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * <p>This operation is unsupported in a bounded list.</p>
	 */
	@Override
	public T get(int index) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * <p>This operation is unsupported in a bounded list.</p>
	 */
	@Override
	public int indexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * <p>This operation is unsupported in a bounded list.</p>
	 */
	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * <p>This operation is unsupported in a bounded list.</p>
	 */
	@Override
	public ListIterator<T> listIterator() {
		return list.listIterator();
	}

	/**
	 * {@inheritDoc}
	 * <p>This operation is unsupported in a bounded list.</p>
	 */
	@Override
	public ListIterator<T> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * <p>This operation is unsupported in a bounded list.</p>
	 */
	@Override
	public T remove(int index) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * <p>This operation is unsupported in a bounded list.</p>
	 */
	@Override
	public T set(int index, T element) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * <p>This operation is unsupported in a bounded list.</p>
	 */
	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * <p>Returns the first element in the list, i.e., the
	 * oldest element.</p>
	 */
	public T first () {
		return list.get(0);
	}
	
	/**
	 * <p>Returns the last element in the list, i.e., the
	 * newest element.</p>
	 */
	public T last () {
		return list.get(list.size() - 1);
	}
}
