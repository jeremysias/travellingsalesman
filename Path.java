
public class Path {
	private int[] points;
	private int count;
	
	
	public int getCount(){
		return count;
	}
	
	public int[] getPoints(){
		return points;
	}
	
	public int getPoint(int pos){
		return points[pos];
	}
	
	public void addPoint(int newPoint){
		points[count] = newPoint;
		count++;
		if(count == points.length){
			points = doubleSize(points);
		}
	}
	
	public static int[] doubleSize(int[] a){
		int newSize = a.length * 2;
		int[]temp = new int[newSize];
		for(int i = 0; i < a.length; i++){
			temp[i] = a[i];
		}
		return temp;
	}
	
	public void reset(){
		for(int i = 0; i<count; i++){
			points[i] = 0;
			
		}
		count = 0;
	}
	
	public Path(int a, int b){
		count = 0;
		points = new int[10];
		addPoint(a);
		addPoint(b);
	}
	
	
	
	public void print(){
		for(int i = 0; i< count; i++){
			System.out.print(points[i] + " ");
		}
	}
	
}
