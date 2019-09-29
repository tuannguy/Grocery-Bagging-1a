import java.util.ArrayList;

/*
 * @author Tuan Nguyen
 */
public class Item {
	private String name;
	private int size;
	private int constraint;
	private ArrayList<Item> items;
	private int number;
	
	public Item() {
		name = "";
		size = 0;
		constraint = 0;
		items = new ArrayList<Item>();
		number = -1;
	}
	
	public Item(String name) {
		this.name = name;
		size = 0;
		constraint = 0;
		items = new ArrayList<Item>();
		number = -1;
	}
	
	public Item(String name, int size) {
		this.name = name;
		this.size = size;
		constraint = 0;
		items = new ArrayList<Item>();
		number = -1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public int getConstraint() {
		return constraint;
	}

	public void setConstraint(int constraint) {
		this.constraint = constraint;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public void addItems(Item item) {
		items.add(item);
	}
	
	public boolean checkItems(Item item) {
		return items.contains(item);
	}
}
