package es.uc3m.baldo.opinais.adt;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class BoundedList<T> implements List<T> {

	// Queue bounding.
	private int bound;
	
	// Underlying data structure.
	private List<T> list;
	
	public BoundedList (int bound) {
		this.bound = bound;
		this.list = new LinkedList<T>();
	}
	
	@Override
	public boolean addAll (Collection<? extends T> items) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear () {
		list.clear();
	}

	@Override
	public boolean contains (Object item) {
		return list.contains(item);
	}

	@Override
	public boolean containsAll (Collection<?> item) {
		return list.containsAll(item);
	}

	@Override
	public boolean isEmpty () {
		return list.isEmpty();
	}

	@Override
	public Iterator<T> iterator () {
		return list.iterator();
	}

	@Override
	public boolean remove (Object item) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll (Collection<?> items) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll (Collection<?> items) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size () {
		return list.size();
	}

	@Override
	public Object[] toArray () {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray (T[] array) {
		return list.toArray(array);
	}

	@Override
	public boolean add (T item) {
		if (size() >= bound) {
			list.remove(0);
		}
		
		return list.add(item);
	}

	@Override
	public void add (int index, T element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T get(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T remove(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T set(int index, T element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}
	
	public T first () {
		return list.get(0);
	}
	
	public T last () {
		return list.get(list.size() - 1);
	}
}
