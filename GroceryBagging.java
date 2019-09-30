import java.util.ArrayList;

/*
 * @author Tuan Nguyen
 * Original MaximalCliquesWithoutPivot code from:
 * https://algosdotorg.wordpress.com/maximal-cliquesbronkerbosch-without-pivot-java/
 */
public class GroceryBagging {
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

	public static void main(String[] args) {
		cliques = new ArrayList<Clique>();
		graph = new ArrayList<Vertex>();

		// TODO: replace with parseFile()
		numBags = 3;
		bagMaxSize = 7;
		bags = new ArrayList<Bag>();
		items = new ArrayList<Item>();

		Item bread = new Item("bread");
		bread.setNumber(0);
		bread.setSize(3);
		bread.setConstraint(1);
		bread.addForbidden("rolls");
		items.add(bread);

		Item rolls = new Item("rolls");
		rolls.setNumber(1);
		rolls.setSize(2);
		rolls.setConstraint(1);
		rolls.addForbidden("bread");
		items.add(rolls);

		Item squash = new Item("squash");
		squash.setNumber(2);
		squash.setSize(3);
		squash.setConstraint(-1);
		squash.addForbidden("meat");
		items.add(squash);

		Item meat = new Item("meat");
		meat.setNumber(3);
		meat.setSize(5);
		meat.setConstraint(0);
		items.add(meat);

		Item lima_beans = new Item("lima_beans");
		lima_beans.setNumber(4);
		lima_beans.setSize(1);
		lima_beans.setConstraint(-1);
		lima_beans.addForbidden("meat");
		items.add(lima_beans);

		numItems = 5;

		createGraph(numItems);
		// replace with parseFile

		// Find all maximum cliques
		Bron_KerboschPivotExecute();

		if (cliques.size() > numBags) {
			System.out.println("failure");
		} else {
			ArrayList<Integer> cliqueItems;
			int itemInd = 0;
			int currNumBags = 0;
			Item item = new Item();
			
			for (Clique clique: cliques) {
				cliqueItems = clique.getVertices();
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
}
