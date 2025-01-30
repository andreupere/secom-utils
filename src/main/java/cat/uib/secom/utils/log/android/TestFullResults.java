package cat.uib.secom.utils.log.android;

import java.util.ArrayList;
import java.util.Iterator;

public class TestFullResults {

	private ArrayList<TestEntryResult> list;
	private Iterator<TestEntryResult> iterator;
	
	public TestFullResults() {}
	
	
	public Iterator<TestEntryResult> iterator() {
		iterator = list.iterator();
		return iterator;
	}
	
	public TestEntryResult next() {
		return iterator.next();
	}
	
	public boolean hashNext() {
		return iterator.hasNext();
	}
	
}
