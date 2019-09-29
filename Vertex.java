import java.util.ArrayList;

/*
 * Orginal code from: https://algosdotorg.wordpress.com/maximal-cliquesbronkerbosch-without-pivot-java/
 */
class Vertex implements Comparable<Vertex> {
	int x; 

	int degree; 
	ArrayList<Vertex> nbrs = new ArrayList<Vertex>(); 

	public int getX() {
		return x; 
	} 

	public void setX(int x) {
		this.x = x; 
	} 

	public int getDegree() {
		return degree; 
	} 

	public void setDegree(int degree) {
		this.degree = degree; 
	} 

	public ArrayList<Vertex> getNbrs() { 
		return nbrs; 
	} 

	public void setNbrs(ArrayList<Vertex> nbrs) {
		this.nbrs = nbrs; 
	} 

	public void addNbr(Vertex y) {
		this.nbrs.add(y); 
		if (!y.getNbrs().contains(y)) { 
			y.getNbrs().add(this); 
			y.degree++; 
		} 
		this.degree++; 

	} 

	public void removeNbr(Vertex y) {
		this.nbrs.remove(y); 
		if (y.getNbrs().contains(y)) { 
			y.getNbrs().remove(this); 
			y.degree--; 
		} 
		this.degree--; 

	} 

	@Override 
	public int compareTo(Vertex o) {
		if (this.degree < o.degree) {
			return -1; 
		} 
		if (this.degree > o.degree) {
			return 1;
		} 
		return 0; 
	} 
} 