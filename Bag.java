import java.util.ArrayList;

/*
 * @author Tuan Nguyen
 */
public class Bag {
	private int maxSize;
	private int currentSize;
	private int numItems;
	private ArrayList<Item> items;
	
	public Bag() {
		maxSize = 0;
		currentSize = 0;
		numItems = 0;
		items = new ArrayList<Item>();
	}
	
	public Bag(int maxSize) {
		this.maxSize = maxSize;
		currentSize = 0;
		numItems = 0;
		items = new ArrayList<Item>();
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
	
	public int getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(int currentSize) {
		this.currentSize = currentSize;
	}

	public int getNumItems() {
		return numItems;
	}

	public void setNumItems(int numItems) {
		this.numItems = numItems;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
		numItems = items.size();
	}
	
    public void addItem(Item item) {
    	items.add(item);
    	numItems++;
    	currentSize += item.getSize();
    }
    
    public boolean isEmpty() {
    	return items.isEmpty() && currentSize == 0;
    }
    
	public String toString() {
		String ret = "";
		for (int v = 0; v < numItems-1; v++) {
			ret += items.get(v).getName() + "\t";
		}
		ret += items.get(numItems-1).getName();
		return ret;
	}
}
