
public class Bag {

	
	private Item[] items;
	private int capacity;
	
	
	
	public Bag(int capacity) {
		this.capacity = capacity;
	}
	
	/**
	 * Placeholder method for printing the contents of each
	 * bag.  Note: prints the names of each item only.
	 */
	public void printBag() {
		Item[] itemCopy = new Item[items.length];
		System.arraycopy(items, 0, itemCopy, 0, items.length);
		
		for (int i = 0; i < itemCopy.length; i++) {
			System.out.print(itemCopy[i].getItem() + "\t");
		}
	}
}
