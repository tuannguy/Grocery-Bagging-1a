import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * @author Tuan Nguyen, Lam Nguyen, ConnorWood
 * Original MaximalCliquesWithoutPivot code from:
 * https://algosdotorg.wordpress.com/maximal-cliquesbronkerbosch-without-pivot-java/
 */
public class GrocceryBagging {
	private static int numItems;
	private static int numBags;
	private static int bagMaxSize;
	private static ArrayList<Bag> bags;
	private static ArrayList<Item> items;
	private static int nodesCount; 
	private static ArrayList<Vertex> graph; 
	private static ArrayList<Clique> cliques;



	/**
	 * File parsing takes place here.
	 * @param args
	 */
	private static void parseFile(String filename) {
		int lineCount = 0;
		File file = new File(filename);
		Scanner fileScan;

		try {
			fileScan = new Scanner(file);

			while (fileScan.hasNextLine()) {
				String line = fileScan.nextLine();
				int tokenCount = 0; //helps for assembling an item list
				Scanner lineScan = new Scanner(line);
				Item item = null;
				if (lineCount > 1) numItems++;
				while (lineScan.hasNext()) {

					if (lineCount == 0) {
						numBags = lineScan.nextInt();
						lineCount++;
					} else if (lineCount == 1) {
						bagMaxSize = lineScan.nextInt();
						lineCount++;
					} else {
						String token = lineScan.next();
						if (tokenCount == 0) {
							item = new Item(token);
							items.add(item);
						} else if (tokenCount == 1) {
							item.setSize(Integer.parseInt(token));
						} else if (tokenCount == 2) {
							if (token.equals("+")) {
								item.setConstraint(1);
							}
							else if (token.equals("-")) {
								item.setConstraint(-1);
							}
							else {
								System.out.println("Error. Invalid option. Exiting. " + token);
								System.exit(1);
							}

						} else {
							item.addForbidden(token);
						}
					}

					tokenCount++;
				}
				lineScan.close();

			}
			fileScan.close();

		} catch (FileNotFoundException e) {
			System.out.print("File not found.  Exiting.");
			System.exit(1);
		}

	}

	public static void main(String[] args) {
		bags = new ArrayList<Bag>();
		items = new ArrayList<Item>();

		if (args.length < 1) {
			System.out.print("Error.  Please Include a filename.");
			System.exit(1);
		} else {
			parseFile(args[0]);
		}



		//DEPTH FIRST
		if (args.length == 1 || args[1].equals("-depth")) {
			//begin DFS
			solve(true);





		}
		// BREADTH FIRST
		else if (args[1].equals("-breath")) {
			//begin BFS
			solve(true);
		}
	}


	public static ArrayList<ArrayList<Bag>> solve(boolean b) { //BY LAM NGUYEN
		
		int currNumBag = 0;
		boolean over = false;
		int solutionCount = 0;
		
		

		ArrayList<ArrayList<Bag>> solutions = new ArrayList<ArrayList<Bag>>();
		ArrayList<Item> items2 = new ArrayList<>(items);
		ArrayList<ArrayList<Item>> patternUsed = new ArrayList<ArrayList<Item>>();

		Item[] copyItems = new Item[items.size()];
		for (int i=0; i<items.size(); i++) {
			copyItems[i] = items.get(i);
		}


		while (over == false) {
			ArrayList<Item> usedItems = new ArrayList<Item>();
			ArrayList<Bag> bags = new ArrayList<Bag>();
			boolean needPatCheck = true;
			while (usedItems.size() < items.size()) {
				Bag bag = new Bag(bagMaxSize);
				bags.add(bag);
				currNumBag++;
				if (currNumBag > numBags) {
					System.out.println("\nFailure");
					System.exit(0);
				}
				for (Item i : items2) {	//try to put every item to the bag
					boolean canBag = true;

					if (!usedItems.contains(i) && bag.getCurrentSize() + i.getSize() < bagMaxSize) { //if item is not used and fit the bag
						for (Item it : bag.getBagItems()) {
							//check for constraints
							if ((it.getConstraint() == -1 && it.checkItems(i.getName()) == true) || (it.getConstraint() == 1 && it.checkItems(i.getName()) == false)
									|| (i.getConstraint() == -1 && i.checkItems(it.getName()) == true) || (i.getConstraint() == 1 && i.checkItems(it.getName()) == false)) {
								
								canBag = false;
								break;
							}
						}
						if (usedItems.size() > 0 && needPatCheck == true) {
							for (ArrayList<Item> a : patternUsed) {
								if (a.get(items2.indexOf(i)).equals(i)) {
									canBag = false;
									needPatCheck = false;
									break;
								}
							}
						}
						if (canBag == true) {
							bag.addItemToBag(i);
							usedItems.add(i);
						}
					}
				}
			}
			System.out.println("Success");
			for (Bag bag : bags) {
				System.out.println(bag);

			}
			
			if (b == false) over = true;
			solutions.add(bags);
			patternUsed.add(usedItems);
			solutionCount++;
		}

		return solutions;
	}
}
