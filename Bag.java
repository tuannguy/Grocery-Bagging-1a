import java.util.ArrayList;

/*
 * @author Tuan Nguyen
 */
public class Bag {
	private int maxSize;
	private int currentSize;
	private int numBagItems;
	private ArrayList<Item> bagItems;
	
	public Bag() {
		maxSize = 0;
		currentSize = 0;
		numBagItems = 0;
		bagItems = new ArrayList<Item>();
	}
	
	public Bag(int maxSize) {
		this.maxSize = maxSize;
		currentSize = 0;
		numBagItems = 0;
		bagItems = new ArrayList<Item>();
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

	public int getBagNumItems() {
		return numBagItems;
	}


	public ArrayList<Item> getBagItems() {
		return bagItems;
	}

	public void setBagItems(ArrayList<Item> bagItems) {
		this.bagItems = bagItems;
		numBagItems = bagItems.size();
	}
	
    public void addItemToBag(Item item) {
    	bagItems.add(item);
    	numBagItems++;
    	currentSize += item.getSize();
    }
    
    public boolean isEmpty() {
    	return bagItems.isEmpty() && currentSize == 0;
    }
    
	public String toString() {
		String ret = "";
		for (int v = 0; v < numBagItems-1; v++) {
			ret += bagItems.get(v).getName() + "\t";
		}
		ret += bagItems.get(numBagItems-1).getName();
		return ret;
	}
}
