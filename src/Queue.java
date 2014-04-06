import java.util.ArrayList;

public class Queue<T> {
	private ArrayList<Object> objs;
	public Queue() {
		objs = new ArrayList<Object>();
	}
	
	public void enqueue(T element) {
		objs.add(element);
	}
	
	public T dequeue() {
		return (T)objs.remove(0);
	}
	
	public void consolidate() {
		objs.trimToSize();
	}
	
	public void clear() {
		objs.clear();
	}
	
	public boolean isEmpty() {
		return objs.size() == 0;
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
