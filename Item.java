/**
 * A starter class for designing a single item with its basic
 * constraints.  For CS457
 * @author cwood
 *
 */
public class Item {
	
	private String name;
	private int amount;
	private int[] constraints;  //can be 0, 1, 2 to reflect nothing, +, or -
	
	
	public Item(String name, int amount, int[] constraints) {
		this.name = name;
		this.amount = amount;
		this.constraints = constraints;
	}
	
	
	//  The following methods are placeholders for now
	
	/**
	 * Return's an item's amount in a bag
	 * @return
	 */
	public int getAmount() {
		
		return this.amount;
	}
	
	/**
	 * Designed to specify amount much of an item's amount
	 * is currently in a bag
	 * @param amount
	 */
	public void setAmount(int amount) {
		
		this.amount = amount;
	}
	
	public void setConstraint(int constraint) {
		this.constraints = constraints;
	}
	
	public int[] getConstraints() {
		
		return this.constraints;
	}
	
	public String getItem() {
		
		return this.name;
	}
	
	
}
