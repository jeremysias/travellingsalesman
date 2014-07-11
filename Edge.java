
public class Edge {
	private int pointA;
	private int pointB;
	private int distance;
	public boolean excluded;
	
	public boolean isExcluded(){
		return excluded;
	}
	
	public void setExcluded(boolean exclude){
		excluded = exclude;
	}
	
	public int getDistance(){
		return distance;
	}
	
	public int getPointA(){
		return pointA;
	}
	
	public int getPointB(){
		return pointB;
	}
	
	public void	setA(int A){
		pointA = A;
	}
	
	public void setB(int B){
		pointB = B;
	}
	
	public void setDistance(int D){
		distance = D;
	}
	
	public Edge(int a, int b, int d){
		pointA = a;
		pointB = b;
		distance = d;
	}
	
	public void print(){
		System.out.println(pointA + " " + pointB + " " + distance);
	}
}
