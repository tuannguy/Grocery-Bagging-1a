import java.util.ArrayList;

/*
 * @author Tuan Nguyen
 */
public class Clique {
	private int numVertices;
	private ArrayList<Integer> vertices;
	
	public Clique() {
		numVertices = 0;
		vertices = new ArrayList<Integer>();
	}

	public int getNumVertices() {
		return numVertices;
	}

	public void setNumVertices(int numVertices) {
		this.numVertices = numVertices;
	}

	public ArrayList<Integer> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Integer> vertices) {
		this.vertices = vertices;
		numVertices = vertices.size();
	}
	
	public void addVertex(int v) {
		vertices.add(v);
		numVertices++;
	}
	
	@SuppressWarnings("deprecation")
	public void removeVertex(int v) {
		vertices.remove(new Integer(v));
		numVertices--;
	}
	
	public String toString() {
		String ret = "";
		for (int v = 0; v < numVertices-1; v++) {
			ret += v + " ";
		}
		ret += vertices.get(numVertices-1);
		return ret;
	}
}
