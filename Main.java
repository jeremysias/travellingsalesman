import java.io.*;
import java.util.Scanner;
import java.lang.Math;
//Travelling Salesman   By Jeremy Sias
public class Main {
	
	
	final static int ARRAYSIZE = 10;
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static int[] getInput(String fileName) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(fileName));
		int number[] = new int[100];
		int i = 0;
		while (sc.hasNext()){
			if (sc.hasNextInt()){
				number[i] = sc.nextInt();
				i++;
				if(i >= number.length)
					number = doubleSize(number);
			}
		}
		return number; 
	}
		
	public static int[] doubleSize(int[] a){
		int newSize = a.length * 2;
		int[]temp = new int[newSize];
		for(int i = 0; i < a.length; i++){
			temp[i] = a[i];
		}
		return temp;
	}
	
	
	//TODO problem here?
	public static int[] splitXY(int[] input, int i){
	//	for(int k = 0; k<input.length; k++){
	//		System.out.println(input[k]);
	//	}
		int j = 0;
		int length = input.length/3 ;
		int[] temp = new int[length]; 
		//while(input[i] != 0){
		//	temp[j] = input[i]; 
		//	i = i+3;
		//	j++;
		//}
		
		while (j < length){
			
			if(input[i] == 0){
				if (j+2 < length){
					if(input[i+1] == 0 && input[i+2] ==0){
						return temp;
					}
				}
			}
			temp[j] = input[i];
			i = i+3;
			j++;
		}
		return temp;
	}
		
	public static int findDistance(int x1, int y1, int x2, int y2){
		//finds the distance between two coordinates
		double square = ((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
		double distance = Math.sqrt(square);
		distance = distance + .5;
		int result = (int)distance;
		return result;
	}
	
	public static Node buildLinks(int[]x,int[]y){
		Node n = new Node(0, x[0], y[0]);
		Node nptr = n;
		int i = 1;
		while(x[i] !=0 && y[i] !=0){
			nptr.addNodeAfter(i, x[i], y[i]);
			nptr = nptr.getLink();
			i++;
		}
		
		
		return n;
	}
	
	//creates an array of Points based on the input
	public static Point[] buildPoints(int[]x, int[]y, int length){
		Point[] n = new Point[length];
		for (int i = 0; i < length; i++){
			n[i] = new Point(i, x[i], y[i], ARRAYSIZE);
		}
		
		return n;
	}
	
	public static int findLength(int[] inputX, int[] inputY){
		int i = 0;
	
	//	for(int j = 0; j<inputX.length; j++){
		//	System.out.println(j+" X: "+inputX[j]+ " Y: "+inputY[j]);
	//	}
		System.out.println("Xlength = "+inputX.length+ "Ylength = "+inputY.length);
		while(i < inputX.length){
			if(inputX[i] == 0 && inputY[i] == 0){
				if(i+1 < inputX.length){
					if(inputX[i+1] ==0 && inputY[i+1] == 0){
						return i;
					}
				}
			}
			i++;
		}
		
		return i;
	}
	
	public static void printPoints(Point[] p){
		for(int i = 0; i < p.length; i++){
			p[i].print();
		}
	}
	
	public static int bruteForce(Point[] p){
		int totalDistance = 0;
		int currentTotal = 0;
		int currentLength = 0;
		//for every point:
		for(int i = 1; i <p.length; i++){
			currentTotal = findDistance(p[0].getX(), p[0].getY(), p[i].getX(), p[i].getY());
			int k = i+1;
			for(int j = 0; i<p.length; j++){
				currentLength = findDistance(p[i].getX(), p[i].getY(), p[k].getX(), p[k].getY());
				currentTotal = currentTotal + currentLength;
				
			}
		}
		
		
		return totalDistance;
	}
	
	//returns nearestPoint and shortest Distance of an unused point
	public static int[] findNearest(Point[] p, int pos){
		int[] result = new int[2];
		int distance;
		int shortestDistance = 100000000;
		int k = pos+1;
		int nearestPoint = 0;
		for (int i = 0; i<p.length-1; i++){
			if (k >= p.length){
				k = 0;
			}
			if(p[k].hasBeenUsed() == false){
				distance =  findDistance(p[pos].getX(), p[pos].getY(), p[k].getX(), p[k].getY());
				if(distance < shortestDistance){
					shortestDistance = distance;
					nearestPoint = k;
				}
			}
			
			k++;
		}
		result[0] = nearestPoint;
		result[1] = shortestDistance;
		return result;
	}
	
	public static int nearestNeighborMethod(Point[] p, int start){
		int total = 0;
		//current[0] is the current position
		//current[1] is the distance of the shortest edge found
		int[] current = {start, 0};
		p[start].setUsed(true);
		for(int i=0; i<p.length-1; i++){
			
			System.out.print(current[0] + " ");
			current = findNearest(p, current[0]);
			p[current[0]].setUsed(true);
			total = current[1] + total;
		//	System.out.println(current[0] + " " +current[1]);
			
		}
		System.out.print(current[0] + " ");
		p[start].setUsed(false);
		current = findNearest(p, current[0]);
		total = current[1]+total;
	//	System.out.println(current[0] +" "+ current[1]);
		//return total distance and an array of each edge and their length
		return total;
	}
	
	public static int tryAllStartsNearestNeighbor(Point[]p){
		int total;
		int bestTotal = 2000000000;
		for (int i=0; i<p.length; i++){
			
			total = nearestNeighborMethod(p,i);
			//System.out.println("Attempt " + i + ": " + total);
			if(bestTotal > total)
				bestTotal = total;
			resetAllPoints(p);
		}
		return bestTotal;
	}
	
	public static void resetAllPoints(Point[]p){
		for(int i=0;i<p.length;i++){
			p[i].setUsed(false);
			p[i].setTimesUsed(0);
		}
	}
	
	public static Edge[] findAllEdges(Point[]p){
		int currentDistance;
		int length = (p.length * (p.length-1))/2;
		int largestDistance = 0;
		Edge[] E = new Edge[length];
		int edgeCount = 0;
		
		for(int i = 0;i<p.length-1;i++){
			//j = i+1 removes duplicates
			for(int j = i+1; j<p.length; j++){
				currentDistance = findDistance(p[i].getX(), p[i].getY(), p[j].getX(),p[j].getY());
				//may be able to improve 
				if(currentDistance > largestDistance){
					largestDistance = currentDistance; 
				}
				E[edgeCount] = new Edge(i, j, currentDistance);
				edgeCount ++;
				
			}
		}
	//	System.out.println("Reducing Edges");
	//	Edge[] returnE = reduceEdges(E, largestDistance);
		
		return E;
	}
	
	public static Edge[] reduceEdges(Edge[] e, int largest){
		Edge[]d = new Edge[e.length];
		System.out.println("Original Length: " + e.length);
		int high = 2*largest / 3;
		int count=0;
		for(int i =0; i<e.length; i++){
			if(e[i].getDistance() < high){
				d[count] = e[i];
				count++;
			}
		}
		e = null;
		Edge[] r = new Edge[count];
		for(int i = 0; i<count; i++){
			r[i] = d[i];
		}
		d = null;
		
		System.out.println("Edges reduced to: "+r.length);
		return r;
		
	}
	
	public static void printAllEdges(Edge[] e){
		for (int i = 0; i<e.length; i++){
			System.out.print(i+ " ");
			e[i].print();
		}
	}
	
	public static Edge[] MergeSortEdges(Edge[] e, int low, int high){
		
		//System.out.println("Marker");
		int length = high - low +1;
		Edge[] d = new Edge[length];
		if(high == low){
		
			d[0] = e[low];
			return d;
		}
		else if(high == low+1){
			if(e[high].getDistance() < e[low].getDistance()){
				d[0] = e[high];
				d[1] = e[low];
			}
			else{
				d[0] = e[low];
				d[1] = e[high];
			}
		}
		else{
			int mid = (high+low)/2;
		//	System.out.println("high: " + high + " low: "+ low + " mid: " + mid);
			Edge[] d1 = MergeSortEdges(e, low, mid);
			Edge[] d2 = MergeSortEdges(e, mid+1, high);
			int count1 = 0;
			int count2 = 0;
			for(int i = 0; i< d.length; i++){
				if(count1 == d1.length){
					d[i] = d2[count2];
					count2 ++;
				}
				else if(count2 == d2.length){
					d[i] = d1[count1];
					count1 ++;
				}
				else{
					if(d1[count1].getDistance() < d2[count2].getDistance()){
						d[i] = d1[count1];
						count1 ++;
					}
					else{
						d[i] = d2[count2];
						count2 ++;
					}
				}
			}
		}
		return d;
	}
	
	public static Path[] doublePath(Path[] a){
		int newSize = a.length * 2;
		Path[]temp = new Path[newSize];
		for(int i = 0; i < a.length; i++){
			temp[i] = a[i];
		}
		return temp;
	}

	
	public static Edge[] chooseBestEdges(Edge[] e, Point[] p){
		int length = p.length;
		resetAllPoints(p);
		Edge[] d = new Edge[length];
	//	d[0] = e[start];
		int current = 0;
		
	//	p[d[0].getPointA()].setTimesUsed(1);
	//	p[d[0].getPointB()].setTimesUsed(1);
		int i = 0;
		Path[] path = new Path[10];
		int pathCount = 0;
		while(current <length-1){
			Edge test = e[i];
			
		/*	if(totalDistance > bestTotal[0]){
				System.out.println("attempt thrown out at: "+ current);
				best[0] = false;
				return d;
			}*/
			if(test.isExcluded() == false){
				//Edge curr = d[current];
				int testA = test.getPointA();
				int testB = test.getPointB();
				//int currA = curr.getPointA();
				//int currB = curr.getPointB();
				int edgeTest = testEdges(p,testA,testB);
				if(edgeTest == 0){ //neither path has been used, make new path
					path[pathCount] = new Path(testA, testB);
					p[testA].setPathNumber(pathCount);
					p[testB].setPathNumber(pathCount);
					pathCount++;
					if(pathCount == path.length){
						path = doublePath(path);
					}
					
					d[current] = e[i]; 
					p[testA].use();
					p[testB].use();
				//	System.out.println(current+" "+testA +" "+ testB+ " "+d[current].getDistance() + " " +p[testA].getTimesUsed() + " "+p[testB].getTimesUsed());
					current ++;
				}
				//if(edgeTest == 1){// one of the paths have been used twice, do nothing
				else if(edgeTest == 3){// testA has been used. add testB to the path
					int Apath = p[testA].getPathNumber();
					path[Apath].addPoint(testB);
					p[testB].setPathNumber(Apath);
				
					d[current] = e[i]; 
					p[testA].use();
					p[testB].use();
				//	System.out.println(current+" "+testA +" "+ testB+ " "+d[current].getDistance() + " " +p[testA].getTimesUsed() + " "+p[testB].getTimesUsed());
					current ++;
				}
				else if(edgeTest == 4){//testB has been used. add testA to the path
					int Bpath = p[testB].getPathNumber();
					path[Bpath].addPoint(testA);
					p[testA].setPathNumber(Bpath);
					
					d[current] = e[i]; 
					p[testA].use();
					p[testB].use();
					//System.out.println(current+" "+testA +" "+ testB+ " "+d[current].getDistance() + " " +p[testA].getTimesUsed() + " "+p[testB].getTimesUsed());
					current ++;
				}
				else if(edgeTest == 2){//both tests have been used.  Check to see if they are in the same path, if not, combine paths
					int Apath = p[testA].getPathNumber();
					int Bpath = p[testB].getPathNumber();
					if( Apath != Bpath ){
						if(path[Apath].getCount() >= path[Bpath].getCount()){//if A >= B, add B to A
							
							for(int j = 0; j < path[Bpath].getCount(); j++){
								int Jpoint = path[Bpath].getPoint(j);
								path[Apath].addPoint(Jpoint);
								p[Jpoint].setPathNumber(Apath);
								
							}
							path[Bpath].reset();
						}
						else{//if B >A, add A to B
							for(int j = 0; j < path[Apath].getCount(); j++){
								int Jpoint = path[Apath].getPoint(j);
								path[Bpath].addPoint(Jpoint);
								p[Jpoint].setPathNumber(Bpath);
														
							}
							path[Apath].reset();
							
						}
						
						d[current] = e[i]; 
						p[testA].use();
						p[testB].use();
						//System.out.println(current+" "+testA +" "+ testB+ " "+d[current].getDistance() + " " +p[testA].getTimesUsed() + " "+p[testB].getTimesUsed());
						current ++;
						
					}
					
				}
			}
				
				
			
			i++;
			if(i >= e.length){
				System.out.println("Unable to complete cycle");
				current = 100000000;
			}
		}
	//	System.out.println("total attempts: " + i);
		i=0;
		boolean found = false;
		
		if(current < 100000000){
			while(found == false){
				if(i == e.length){
					System.out.println("Unable to complete last cycle");
					return d;
				}
				Point A = p[e[i].getPointA()];
				Point B = p[e[i].getPointB()];
				if(A.getTimesUsed() < 2 && B.getTimesUsed()<2){
					d[current] = e[i];
				
					found = true;
					//System.out.println(A.getId() + " "+B.getId() + " "+ d[current].getDistance());
				}
			i++;
			}
		}
		
			
		
		
		return d;
	}
	
	public static int testEdges(Point[]p, int testA, int testB){
		//return 0 if neither point has been used -- make new path
		//return 1 if either point has been used twice
		//return 2 if both points have been used -- test to see if both are in the same path, if not, combine the two paths
		//return 3 if testA has been used -- add testB to the path
		//return 4 if testB has been used -- add testA to the path
				if(p[testA].getTimesUsed() == 0 && p[testB].getTimesUsed() == 0){
					return 0;
				}
				else if(p[testA].getTimesUsed() == 2 || p[testB].getTimesUsed() == 2){
					return 1;
				}
				else if(p[testA].getTimesUsed() ==1  && p[testB].getTimesUsed() ==1 ){
					return 2;
				}
				else if(p[testA].getTimesUsed() ==1){
					return 3;
				}
				else{// if(p[testB].getTimesUsed() == 1){
					return 4;
				}
			
				
	}
	
	//does not matter where you start
	public static  Edge[] tryCombos(Edge[] e, Point[] p, int rounds, int exclusionRange, long startTime){
		
		
		int[] bestExclusion = new int[rounds];
		int length = exclusionRange;// (e.length / (bestExclusion.length *10000)) ;
		
		int increaseRound = 0;
		int exclusionCounter;
		int[] bestTotal = new int[1];
		int[] attempt = new int[1];
		int[] bestAttempt = new int[1];
		attempt[0] = 0;
		bestTotal[0]= 2000000000;
		Edge[] bestPath = new Edge[0];
		int currentBest = 0;
		exclusionCounter = -1;
		long stopTime;
		long testTime;
		if(e.length > 100000000){
			stopTime = 270000;
		}
		else if(e.length > 10000000){
			stopTime = 290000;
		}
		else if(e.length > 3000000){
			stopTime = 295000;
		}
		else{
			stopTime = 299000;
		}
		
		int minI[] = new int[1];
		minI[0] = 0;
	//	int currentMinI = 1;
		int maxI = length;
		for(int j = 0; j<bestExclusion.length; j++){
			
		//	int maxI = (j+1) * length;
			exclusionCounter++;
			
		//adjust the round length
			if(j >0){
				if(minI[0] > 3 * maxI / 4){
					maxI = 2 * minI[0];
				}
				else if(minI[0] < maxI / 4){
					maxI = maxI / 2;
				}
			}
			
			
			
			if(maxI > e.length){
				maxI = e.length;
			}
			
			for(int i = 0; i< maxI; i++){
				
				initEdges(e);
				for(int k = 0; k<j; k++){
					
						e[bestExclusion[k]].setExcluded(true);
					
				}
				e[i].setExcluded(true);
				bestPath = runE(e, p, bestPath, bestTotal, attempt, bestAttempt, bestExclusion, exclusionCounter, i, minI);
				testTime = System.currentTimeMillis();
				long currentTime = testTime - startTime;
				if(currentTime > stopTime){
					System.out.println("Total Attempts: "+ attempt[0]);
					System.out.println("Best Attempt happened at: " + bestAttempt[0]);
					System.out.println("Program halted at: " + currentTime);
					return bestPath;
				}
				
			}
			System.out.println("Round: "+ j +" Attempts so far: "+ attempt[0] + " Best: "+bestTotal[0] + " Best Search this round found at: " + minI[0]);
			
			//if no improvement, double the round length for 3 rounds and then quit if nothing better is found
			if(currentBest == bestTotal[0]){
				maxI = maxI * 4; //will be divided by 2 next round
				increaseRound ++;
				if(increaseRound == 3){
					j = bestExclusion.length;
				}
			
			}
			else{
				increaseRound = 0;
			}
			currentBest = bestTotal[0];
		}
		
		System.out.println("Total Attempts: "+ attempt[0]);
		System.out.println("Best Attempt happened at: " + bestAttempt[0]);
		return bestPath;
		
		//bestPath = runE(e, p, bestPath, bestTotal, attempt);
		
		//return true if the edge does NOT complete a cycle
		//return bestPath;
	}
	
	public static Edge[] runE(Edge[] e, Point[]p, Edge[] bestPath, int[] bestTotal, int []attempt, int[]bestAttempt, int[]bestExclusion, int exclusionCounter, int i, int[] minI){
		
		boolean best = false;
		Edge[] d = chooseBestEdges(e, p);
		int totalDistance = edgeTotal(d);
		if(totalDistance < bestTotal[0]){
			best= true;
			bestTotal[0] = totalDistance;
		}
		else{
			best= false;
		}
		
	//	System.out.println("Attempt "+ attempt[0]+": "+currentTotal);
		
		if(best == true){
	//		System.out.println("found best");
			bestPath = d;
			bestAttempt[0] = attempt[0];
			bestExclusion[exclusionCounter] = i;
			minI[0] = i;
		}
		attempt[0] = attempt[0] + 1;
		return bestPath;
	}
	
	public static void excludeXatYdivZ(Edge[]e, int x, int y, int z, int inc){
		//y must be greater than z*2
		initEdges(e);
		int length = y*e.length / z;
	//	System.out.println("length: "+length + " x: " + x);
		for(int i = length; i < length + x; i = i + inc){
			if(i < e.length)
				e[i].setExcluded(true);
		}
	}
	
	//public static void exclude
	public static void exclude50at66(Edge[]e){
		int length = 2 *e.length/3;
		for(int i = length; i < length + 50; i++){
			e[i].setExcluded(true);
		}
		
	}
	
	public static void excludeEvens(Edge[]e){
		for(int i = 1; i< e.length; i= i+2){
			e[i].setExcluded(true);
		}
	}
	
	public static void exclude5Evens(Edge[]e){
		for(int i = 1; i< 11; i= i+2){
			e[i].setExcluded(true);
		}
	}
	
	public static void excludeOdds(Edge[]e){
		for(int i = 0; i< e.length; i= i+2){
			e[i].setExcluded(true);
		}
	}
	
	public static void initEdges(Edge[]e){
		for(int i = 0; i<e.length; i++){
			e[i].setExcluded(false);
		}
	}
	
	public static int testEdgeTotal(Edge[] e, int count){
		int total = 0;
		for(int i = 0; i<count; i++){
			total = e[i].getDistance() + total;
		}
		return total;
	}
	public static int edgeTotal(Edge[]e){
		int total = 0;
		for(int i = 0; i<e.length; i++){
			if (e[i] == null){
				return 2000000000;
			}
			total = e[i].getDistance() + total;
		}
		return total;
	}
	
	public static void quickSort(Edge[] e){
		QuickSort sorter = new QuickSort();
		sorter.sort(e);
		
	}
	
	
	public static int[] arrangeTour(Edge[] e){
		int[] f = new int[e.length];
		initEdges(e);
		f[0] = e[0].getPointA();
		e[0].setExcluded(true);
		int currentPair = e[0].getPointB();
		//System.out.println(f[0] + " " + currentPair);
		for(int i = 1; i< e.length; i++){
			boolean found = false;
			int j = 0;
		//	System.out.println("i = " + i);
			while(found == false){
				//System.out.println("j = "+ j);
				if(e[j].isExcluded() == false){
					
					if (currentPair == e[j].getPointA()){
						f[i] = e[j].getPointA();
						currentPair = e[j].getPointB();
						e[j].setExcluded(true);
						found = true;
					//	System.out.println(f[i] + " " + currentPair);
						
					}
					else if(currentPair == e[j].getPointB()){
						f[i] = e[j].getPointB();
						currentPair = e[j].getPointA();
						e[j].setExcluded(true);
						found = true;
				//		System.out.println(f[i] + " " + currentPair);
					}
				}
				j++;
			}
		}
		return f;
	}
	
	public static void printTour(int[] t){
		for(int i = 0; i<t.length; i++){
			System.out.println(t[i]);
		}
	}
	
	public static void output(int[] f, int totalDistance, String fileName){
		try{
			PrintWriter out = new PrintWriter(new FileWriter(fileName));
				out.println(totalDistance);
				for(int i = 0; i<f.length; i++){
					out.println(f[i]);
				}
				out.close();
		
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args)  {
		
		
		long mainStartTime = System.currentTimeMillis();
		int[] points;
		try {
			points = getInput(args[0]);
		} catch (FileNotFoundException e) {
			System.out.println("File not Found");
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		int[]x = splitXY(points, 1);
		int[]y = splitXY(points, 2);
		int arrayLength = findLength(x,y);
		
		
		System.out.println("number of points:" + arrayLength);
		
		System.out.println("Building Points Array");
		Point pointsArray[] = buildPoints(x,y,arrayLength);
		x = null;
		y = null;
		points = null;
		
	
	//	int totalDistance = tryAllStartsNearestNeighbor(pointsArray);
	//	System.out.println("Best Nearest Neighbor total: " + totalDistance );
		System.out.println("Finding all edges");
		Edge[] E = findAllEdges(pointsArray);
		
		//printAllEdges(E);
		System.out.println("length of E: " + E.length);
		System.out.println("Sorting Edges");
		
	
		long startTime = System.currentTimeMillis();
		
		quickSort(E);
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Quicksort time taken: " + elapsedTime + " milliseconds");

		
		//printAllEdges(E);
		System.out.println("length of E: " + E.length);
		System.out.println("Trying Combos");
		
		int exclusionRounds;
		int roundRange;
		
		if(E.length > 100000000){
			roundRange = 10;
			exclusionRounds = 1;
		}
		else if(E.length > 1000000){
			roundRange = 1600; //1600
			exclusionRounds = 100; //2
		}
		else if(E.length > 500000){
			roundRange = 15000;  //9000
			exclusionRounds = 100;  //3
		}
		else if(E.length > 250000){
			roundRange = 15000;
			exclusionRounds = 100;
		}
		else if(E.length > 100000){
			roundRange = 15000;
			exclusionRounds = 100;
		}
		
		
		
		else if(E.length > 10000){
			roundRange = 6000;
			exclusionRounds = 100;
			
		}
		else{
			roundRange = E.length;
			exclusionRounds = E.length / 4;
			
		}
		
		Edge[] d = tryCombos(E, pointsArray, exclusionRounds, roundRange, mainStartTime);
		
		
		
		int [] f = arrangeTour(d);
		int totalDistance = edgeTotal(d);
	//	printAllEdges(d);
		System.out.println(totalDistance);
	//	printTour(f);
		String outputTest;
		if(args.length <2){
			outputTest =  "outputTest.txt";
		}
		else{
			outputTest = args[1];
		}
		output(f, totalDistance, outputTest);
		long stopMainTime = System.currentTimeMillis();
		long elapsedMainTime = stopMainTime - mainStartTime;
		elapsedMainTime = elapsedMainTime / 1000;
		System.out.println("Total time taken: " + elapsedMainTime + "seconds");
		
	}
	

}
