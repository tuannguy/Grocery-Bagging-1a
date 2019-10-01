import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * program determines way(s) to bag groceries that has constraints on what items can
 * be bagged with what, how much you can put in a bag, and the number of bags available.
 */
public class GroceryBagging {
	private static int numBags;
	private static int bagMaxSize;
	private static ArrayList<Bag> bags;
	private static ArrayList<Item> items;

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
			System.out.print("File not found. Exiting.");
			System.exit(1);
		}

	}

	private static void dfs() {

		Item itemU;
		Item itemV;
		int currSize;
		Boolean goodPartition;
		Boolean goodBag;
		Bag newBag;

		for (int blocks = 1; blocks <= numBags; ++blocks) {
			// Iterate through each partition
			for (List<List<Item>> partition : new Partition<>(items, blocks)) {

				goodPartition = true;

				// Iterate through each group in a partition
				for (List<Item> itemGroup : partition) {
					// Check to see if this itemGroup can be put in a bag
					currSize = 0;
					goodBag = true;
					newBag = new Bag(bagMaxSize);
					for (int u = 0; u < itemGroup.size(); u++) {
						itemU = itemGroup.get(u);

						// add items to a new bag just in case
						newBag.addItemToBag(itemU);

						// Size constraint
						currSize += itemU.getSize();
						if (currSize > bagMaxSize) {
							goodPartition = false;
							break;
						}
						// Items constraint
						for (int v = u+1; v < itemGroup.size(); v++) {
							itemV = itemGroup.get(v);
							if (itemU.getConstraint() == 1 && !itemU.checkItems(itemV.getName()) ) {
								goodBag = false;
								break;
							}
							if (itemU.getConstraint() == -1 && itemU.checkItems(itemV.getName()) ) {
								goodBag = false;
								break;
							}
							if (itemV.getConstraint() == 1 && !itemV.checkItems(itemU.getName()) ) {
								goodBag = false;
								break;
							}
							if (itemV.getConstraint() == -1 && itemV.checkItems(itemU.getName()) ) {
								goodBag = false;
								break;
							}
						}
						if (!goodBag) {
							goodPartition = false;
							break;
						}
					}
					if (goodBag && goodPartition) {
						bags.add(newBag);
					}
				}
				if (!goodPartition) {
					bags.clear();
				} else {
					System.out.println("success");
					for (Bag bag : bags) {
						System.out.println(bag);
					}
					return;
				}
			}
		}
		System.out.println("failure");
	}

	private static void bfs() {

		boolean ret = false;
		Item itemU;
		Item itemV;
		int currSize;
		Boolean goodPartition;
		Boolean goodBag;
		Bag newBag;

		for (int blocks = 1; blocks <= numBags; ++blocks) {
			// Iterate through each partition
			for (List<List<Item>> partition : new Partition<>(items, blocks)) {

				goodPartition = true;

				// Iterate through each group in a partition
				for (List<Item> itemGroup : partition) {
					// Check to see if this itemGroup can be put in a bag
					currSize = 0;
					goodBag = true;
					newBag = new Bag(bagMaxSize);
					for (int u = 0; u < itemGroup.size(); u++) {
						itemU = itemGroup.get(u);

						// add items to a new bag just in case
						newBag.addItemToBag(itemU);

						// Size constraint
						currSize += itemU.getSize();
						if (currSize > bagMaxSize) {
							goodPartition = false;
							break;
						}
						// Items constraint
						for (int v = u+1; v < itemGroup.size(); v++) {
							itemV = itemGroup.get(v);
							if (itemU.getConstraint() == 1 && !itemU.checkItems(itemV.getName()) ) {
								goodBag = false;
								break;
							}
							if (itemU.getConstraint() == -1 && itemU.checkItems(itemV.getName()) ) {
								goodBag = false;
								break;
							}
							if (itemV.getConstraint() == 1 && !itemV.checkItems(itemU.getName()) ) {
								goodBag = false;
								break;
							}
							if (itemV.getConstraint() == -1 && itemV.checkItems(itemU.getName()) ) {
								goodBag = false;
								break;
							}
						}
						if (!goodBag) {
							goodPartition = false;
							break;
						}
					}
					if (goodBag && goodPartition) {
						bags.add(newBag);
					}
				}
				if (goodPartition) {
					System.out.println("success");
					for (Bag bag : bags) {
						System.out.println(bag);
					}
					ret = true;
				}
				bags.clear();
			}
		}
		if (!ret) {
			System.out.println("failure");
		}
	}

	public static void main(String[] args) {

		bags = new ArrayList<Bag>();
		items = new ArrayList<Item>();

		if (args.length < 1) {
			System.out.print("Usage: prog_name filename [-depth | -breadth]");
			System.exit(0);
		} else {
			parseFile(args[0]);
		}

		//DEPTH FIRST
		if (args.length == 2 && args[1].equals("-depth")) {
			dfs();

		}
		// BREADTH FIRST
		else if (args.length == 2 && args[1].equals("-breath")) {
			bfs();
		}
	}
}
