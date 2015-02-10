
public class DijkstrasVertex {

	private String p;
	int v;
	private double d;
	
	DijkstrasVertex(){
		
	}
	
	DijkstrasVertex(int v1, double d1, String p1){
		this.v = v1;
		this.d = d1;
		this.p = p1;
	}
	
	DijkstrasVertex(DijkstrasVertex dv){
		this.v = dv.getV();
		this.d = dv.getD();
		this.p = dv.getP();
	}


	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}
	
	public String toString(){
		return "v: " +v +" d = " +d +" p = " +p;
	}
	
	
}
