import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * @author Tuan Nguyen
 * Original MaximalCliquesWithoutPivot code from:
 * https://algosdotorg.wordpress.com/maximal-cliquesbronkerbosch-without-pivot-java/
 */
public class GroceryBaggingWithParser {
	private static int numItems;
	private static int numBags;
	private static int bagMaxSize;
	private static ArrayList<Bag> bags;
	private static ArrayList<Item> items;
	private static int nodesCount; 
	private static ArrayList<Vertex> graph; 
	private static ArrayList<Clique> cliques;

	// TODO
	private void parseFile() {

	}

	static void initGraph() { 
		graph.clear(); 
		for (int i = 0; i < nodesCount; i++) {
			Vertex V = new Vertex(); 
			V.setX(i); 
			graph.add(V); 
		} 
	}

	private static void createGraph(int numItems) {

		nodesCount = numItems;

		initGraph();
		Vertex vertU;
		Vertex vertV;
		Item itemU;
		Item itemV;
		for (int u = 0; u < items.size(); u++) {
			for (int v = u+1; v < items.size(); v++) {
				itemU = items.get(u);
				itemV = items.get(v);
				if (itemU.getConstraint() == 1 && itemV.getConstraint() == 1) {
					if ( itemU.checkItems(itemV.getName()) && itemV.checkItems(itemU.getName()) ) {
						vertU = graph.get(u); 
						vertV = graph.get(v); 
						vertU.addNbr(vertV);
					}
				}
				if (itemU.getConstraint() == 1 && itemV.getConstraint() == -1) {
					if ( itemU.checkItems(itemV.getName()) && !itemV.checkItems(itemU.getName()) ) {
						vertU = graph.get(u); 
						vertV = graph.get(v); 
						vertU.addNbr(vertV);
					}
				}
				if (itemU.getConstraint() == -1 && itemV.getConstraint() == 1) {
					if ( !itemU.checkItems(itemV.getName()) && itemV.checkItems(itemU.getName()) ) {
						vertU = graph.get(u); 
						vertV = graph.get(v); 
						vertU.addNbr(vertV);
					}
				}
				if (itemU.getConstraint() == -1 && itemV.getConstraint() == -1) {
					if ( !itemU.checkItems(itemV.getName()) && !itemV.checkItems(itemU.getName()) ) {
						vertU = graph.get(u); 
						vertV = graph.get(v); 
						vertU.addNbr(vertV);
					}
				}
			}
		}
	} 

	// Finds nbr of vertex i 
	private static ArrayList<Vertex> getNbrs(Vertex v) { 
		int i = v.getX(); 
		return graph.get(i).nbrs; 
	} 

	// Intersection of two sets 
	private static ArrayList<Vertex> intersect(ArrayList<Vertex> arlFirst, 
			ArrayList<Vertex> arlSecond) { 
		ArrayList<Vertex> arlHold = new ArrayList<Vertex>(arlFirst); 
		arlHold.retainAll(arlSecond); 
		return arlHold; 
	}

	// Version without a Pivot 
	private static void Bron_KerboschWithoutPivot(ArrayList<Vertex> R, ArrayList<Vertex> P,
			ArrayList<Vertex> X, String pre) { 

		if ((P.size() == 0) && (X.size() == 0)) {
			addClique(R); 
			return; 
		} 

		ArrayList<Vertex> P1 = new ArrayList<Vertex>(P); 

		for (Vertex v : P) { 
			R.add(v); 
			Bron_KerboschWithoutPivot(R, intersect(P1, getNbrs(v)), 
					intersect(X, getNbrs(v)), pre + "\t"); 
			R.remove(v); 
			P1.remove(v); 
			X.add(v); 
		} 
	} 

	private static void Bron_KerboschPivotExecute() { 

		ArrayList<Vertex> X = new ArrayList<Vertex>(); 
		ArrayList<Vertex> R = new ArrayList<Vertex>(); 
		ArrayList<Vertex> P = new ArrayList<Vertex>(graph); 
		Bron_KerboschWithoutPivot(R, P, X, "");
	} 

	private static void addClique(ArrayList<Vertex> R) {
		Clique clique = new Clique();
		for (Vertex v : R) {  
			clique.addVertex(v.getX());
		} 
		cliques.add(clique);
	} 
	
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
							if (token == "+")
							item.setConstraint(0);
							else if (token == "-")
							item.setConstraint(1);
							else {
								System.out.println("Error. Invalid option. Exiting.");
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
		cliques = new ArrayList<Clique>();
		graph = new ArrayList<Vertex>();
		bags = new ArrayList<Bag>();
		items = new ArrayList<Item>();
		
		if (args[0] == null) {
			System.out.print("Error.  Please Include a filename.");
			System.exit(1);
		} else {
			parseFile(args[0]);
		}
		
		if (args[1] == "-depth") {
			; //begin DFS
		} else if (args[1] == "-breadth") {
			; //begin BFS
		}

		Item bread = new Item("0");
		bread.setNumber(0);
		bread.setSize(1);
		bread.setConstraint(1);
		bread.addForbidden("1");
		bread.addForbidden("2");
		bread.addForbidden("4");
		items.add(bread);

		Item rolls = new Item("1");
		rolls.setNumber(1);
		rolls.setSize(1);
		rolls.setConstraint(1);
		rolls.addForbidden("0");
		rolls.addForbidden("3");
		items.add(rolls);

		Item squash = new Item("2");
		squash.setNumber(2);
		squash.setSize(1);
		squash.setConstraint(1);
		squash.addForbidden("0");
		squash.addForbidden("3");
		items.add(squash);

		Item meat = new Item("3");
		meat.setNumber(3);
		meat.setSize(1);
		meat.setConstraint(1);
		meat.addForbidden("1");
		meat.addForbidden("2");
		meat.addForbidden("5");
		items.add(meat);

		Item lima_beans = new Item("4");
		lima_beans.setNumber(4);
		lima_beans.setSize(1);
		lima_beans.setConstraint(1);
		lima_beans.addForbidden("0");
		lima_beans.addForbidden("5");
		items.add(lima_beans);

		Item lima = new Item("5");
		lima.setNumber(5);
		lima.setSize(1);
		lima.setConstraint(1);
		lima.addForbidden("3");
		lima.addForbidden("4");
		items.add(lima);

		numItems = 6;

		createGraph(numItems);

		// Find all maximum cliques
		Bron_KerboschPivotExecute();


		ArrayList<Integer> cliqueItems;
		int itemInd = 0;
		int currNumBags = 0;
		Item item = new Item();
		Clique clique = new Clique();
		int numCliques = 0;

		while (!cliques.isEmpty()) {
			clique = cliques.remove(0);
			cliqueItems = clique.getVertices();
			for (int i : cliqueItems) {
				numCliques = 0;
				while (numCliques < cliques.size()) {
					if (cliques.get(numCliques).contains(i)) {
						cliques.remove(numCliques);
						numCliques--;
					}
					numCliques++;
				}
			}
			while (!cliqueItems.isEmpty()) {
				Bag bag = new Bag(bagMaxSize);
				currNumBags++;
				if (currNumBags > numBags) {
					System.out.println("failure");
					System.exit(0);
				}
				while (!cliqueItems.isEmpty() && bag.getCurrentSize() < bagMaxSize) {
					itemInd = cliqueItems.remove(0);
					item = items.get(itemInd);
					if (item.getSize() > bagMaxSize) {
						System.out.println("failure");
						System.exit(0);
					}
					if (bag.getCurrentSize() + item.getSize() <= bagMaxSize) {
						bag.addItemToBag(item);
					}
				}
				bags.add(bag);
			}
		}

		System.out.println("success");
		for (Bag bag : bags) {
			System.out.println(bag);

		}
	}
}
