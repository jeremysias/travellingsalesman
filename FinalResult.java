public class FinalResult {
	private int startingPoint;
	private int distance;
	
	public int getPoint(){
		return startingPoint;
	}
	
	public int getDistance(){
		return distance;
	}
	
	public void setStart(int start){
		startingPoint = start;
	}
	
	public void setDistance(int d){
		distance = d;
	}
	
	public void print(){
		System.out.println(startingPoint +" "+distance);
	}
	
	FinalResult(int p, int d){
		startingPoint = p;
		distance = d;
	}
}
