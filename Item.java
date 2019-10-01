import java.util.ArrayList;

/*
 * @author Tuan Nguyen
 */
public class Item {
	private String name;
	private int size;
	private int constraint;
	private ArrayList<String> forbidden;
	private int number;
	
	public Item() {
		name = "";
		size = 0;
		constraint = 0;
		forbidden = new ArrayList<String>();
		number = -1;
	}
	
	public Item(String name) {
		this.name = name;
		size = 0;
		constraint = 0;
		forbidden = new ArrayList<String>();
		number = -1;
	}
	
	public Item(String name, int size) {
		this.name = name;
		this.size = size;
		constraint = 0;
		forbidden = new ArrayList<String>();
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

	public ArrayList<String> getForbidden() {
		return forbidden;
	}

	public void setForbidden(ArrayList<String> forbidden) {
		this.forbidden = forbidden;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public void addForbidden(String item) {
		forbidden.add(item);
	}
	
	public boolean checkItems(String item) {
		return forbidden.contains(item);
	}
	
	public String toString() {
		return name;
	}
}
