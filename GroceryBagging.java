import java.util.ArrayList;

/*
 * @author Tuan Nguyen
 * Original MaximalCliquesWithoutPivot code from:
 * https://algosdotorg.wordpress.com/maximal-cliquesbronkerbosch-without-pivot-java/
 */
public class GroceryBagging {
	private static int numItems;  // TODO remove static
	private static int numBags;  // TODO remove static
	private static int bagMaxSize;
	private static ArrayList<Bag> bags;  // TODO remove static
	private static ArrayList<Item> items; // TODO remove static
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
		for (int u = 0; u < items.size(); u++) {
			for (int v = u+1; v < items.size(); v++) {
				if (items.get(u).getConstraint() == 1 && items.get(u).getConstraint() == 1) {
					if ( items.get(u).checkItems(items.get(v)) && items.get(v).checkItems(items.get(u)) ) {
						vertU = graph.get(u); 
						vertV = graph.get(v); 
						vertU.addNbr(vertV);
					}
				}
				if (items.get(u).getConstraint() == 1 && items.get(u).getConstraint() == -1) {
					if ( items.get(u).checkItems(items.get(v)) && !items.get(v).checkItems(items.get(u)) ) {
						vertU = graph.get(u); 
						vertV = graph.get(v); 
						vertU.addNbr(vertV);
					}
				}
				if (items.get(u).getConstraint() == -1 && items.get(u).getConstraint() == 1) {
					if ( !items.get(u).checkItems(items.get(v)) && items.get(v).checkItems(items.get(u)) ) {
						vertU = graph.get(u); 
						vertV = graph.get(v); 
						vertU.addNbr(vertV);
					}
				}
				if (items.get(u).getConstraint() == -1 && items.get(u).getConstraint() == -1) {
					if ( !items.get(u).checkItems(items.get(v)) && !items.get(v).checkItems(items.get(u)) ) {
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

	// Union of two sets 
	private ArrayList<Vertex> union(ArrayList<Vertex> arlFirst, 
			ArrayList<Vertex> arlSecond) { 
		ArrayList<Vertex> arlHold = new ArrayList<Vertex>(arlFirst); 
		arlHold.addAll(arlSecond); 
		return arlHold; 
	} 

	// Removes the neigbours 
	private ArrayList<Vertex> removeNbrs(ArrayList<Vertex> arlFirst, Vertex v) { 
		ArrayList<Vertex> arlHold = new ArrayList<Vertex>(arlFirst); 
		arlHold.removeAll(v.getNbrs()); 
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

	public static void main(String[] args) {
		cliques = new ArrayList<Clique>();
		graph = new ArrayList<Vertex>();

		// TODO: replace with parseFile()
		numBags = 3;
		bagMaxSize = 7;
		bags = new ArrayList<Bag>();
		items = new ArrayList<Item>();

		// TODO: Cannot create all items first
		Item bread = new Item("bread");
		Item rolls = new Item("rolls");
		Item squash = new Item("squash");
		Item meat = new Item("meat");
		Item lima_beans = new Item("lima_beans");

		bread.setNumber(0);
		bread.setSize(3);
		bread.setConstraint(1);
		bread.addItems(rolls);
		items.add(bread);

		rolls.setNumber(1);
		rolls.setSize(2);
		rolls.setConstraint(1);
		rolls.addItems(bread);
		items.add(rolls);

		squash.setNumber(2);
		squash.setSize(3);
		squash.setConstraint(-1);
		squash.addItems(meat);
		items.add(squash);

		meat.setNumber(3);
		meat.setSize(5);
		meat.setConstraint(0);
		items.add(meat);

		lima_beans.setNumber(4);
		lima_beans.setSize(1);
		lima_beans.setConstraint(-2);
		lima_beans.addItems(meat);
		items.add(lima_beans);

		numItems = 5;

		createGraph(numItems);
		// replace with parseFile

		Bron_KerboschPivotExecute();
		System.out.println(cliques);

//		if (cliques.size() > numBags) {
//			System.out.println("failure");
//		} else {
//			ArrayList<Integer> cliqueItems;
//			int itemInd = 0;
//			int currNumBags = 0;
//			Item item = new Item();
//			for (Clique clique: cliques) {
//				cliqueItems = clique.getVertices();
//				while (!cliqueItems.isEmpty()) {
//					Bag bag = new Bag(bagMaxSize);
//					currNumBags++;
//					if (currNumBags > numBags) {
//						System.out.println("failure");
//						 System.exit(0);
//					}
//					while (bag.getCurrentSize() < bagMaxSize) {
//						itemInd = cliqueItems.remove(0);
//						item = items.get(itemInd);
//						if (item.getSize() > bagMaxSize) {
//							System.out.println("failure");
//							 System.exit(0);
//						}
//						if (bag.getCurrentSize() + item.getSize() <= bagMaxSize) {
//							bag.addItem(item);
//						}
//					}
//					bags.add(bag);
//				}
//			}
//		}
	}
}
