import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

public class Queue<T> {
	private AbstractList<Object> objs;
	private boolean threadSafe = false;
	public Queue() {
		this(false);
	}
	
	public Queue(boolean needsThreadSafe) {
		threadSafe = needsThreadSafe;
		if (needsThreadSafe)
			objs = new Vector<Object>();
		else
			objs = new ArrayList<Object>();
	}
	
	public void enqueue(T element) {
		objs.add(element);
	}
	
	public T dequeue() {
		try {
			return (T)objs.remove(0);
		} catch (Throwable t) {
			return null;
		}
	}
	
	public void consolidate() {
		if (objs instanceof Vector)
			((Vector<Object>)objs).trimToSize();
		else
			((ArrayList<Object>)objs).trimToSize();
	}
	
	public void clear() {
		objs.clear();
	}
	
	public boolean isEmpty() {
		return objs.size() == 0;
	}
	
	public int size() {
		return objs.size();
	}
	
	/**
	 * If reversed, the queue can be used as a stack (kinda).
	 */
	public void reverse() {
		ArrayList<Object> cpy = new ArrayList<Object>();
		for (int i = objs.size()-1; i > 0; i--) {
			cpy.add(objs.get(i));
		}
		objs.clear();
		objs = null;
		objs = cpy;
	}
}
