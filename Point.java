
public class Point {
	private int x;
	private int y;
	private int id;
	private boolean used;
	private boolean usedAgain;
	private int[] nearest;
	private int[] nearestId;
	private int timesUsed;
	int pathNumber;
	
	public int getPathNumber(){
		return pathNumber;
	}
	
	public void setPathNumber(int path){
		pathNumber = path;
	}
	
	public int getTimesUsed(){
		return timesUsed;
	}
	
	public void setTimesUsed(int times){
		timesUsed = times;
	}
	
	public void use(){
		timesUsed ++;
	}
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getId(){
		return id;
	}
	
	public boolean hasBeenUsed(){
		return used;
	}
	
	public boolean hasBeenUsedTwice(){
		return usedAgain;
	}
	public void setUsed(boolean b){
		used = b;
	}
	
	public void setUsedAgain(boolean b){
		usedAgain = b;
	}
	public int getNearest(int i){
		return nearest[i];
	}
	
	public int getNearestId(int i){
		return nearestId[i];
	}
	
	public void setNearest(int ID, int distance, int index){
		nearest[index] = distance;
		nearestId[index] = ID;
	}
	
	//arraySize is the maximum number of nearest points to store
	public Point(int IDin, int xin, int yin, int arraySize){
		id = IDin;
		x = xin;
		y = yin;
		nearest = new int[arraySize];
		nearestId = new int[arraySize];
		used = false;
	}
	
	public void print(){
		System.out.println(id + " "+x+" "+y);
	}
	
	
}
