import java.io.File;

/**
 * Driver class for the Grocery Bagging problem for 457.
 * @author cwood
 *
 *Currently placeholder methods
 */
public class Groceries {

	public static void main(String[] args) {
		String filename = args[0];
		
		if (args[1] == "-depth") {
			; //execute DFS with filename
		} else if (args[1] == "-breadth") {
			;  //execute BFS with filename
		}

	}
	
	/**
	 * parse file here
	 * @param filename
	 */
	private void openFile(String filename) {
		File file = new File(filename);
	}

}
