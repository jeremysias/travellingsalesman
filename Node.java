import java.util.*;
public class Node {

		private int x;
		private int y;
		private int id;
		private Node link;
		
		public int getX(){
			return x;
		}
		public int getY(){
			return y;
		}
		public int getId(){
			return id;
		}
		
		public Node getLink(){
			return link;
		}
		
		public void setInfo(int Idin, int Xin, int Yin){
			id = Idin;
			x = Xin;
			y = Yin;
		}
		
		public void setLink(Node input){
			link = input;
		}
			
		public Node(){
			link = null;
		}
		
		public Node(int Idin, int Xin, int Yin){
			id = Idin;
			x = Xin;
			y = Yin;
			link = null;
		}
		
		public void addNodeAfter(int Idin, int Xin, int Yin){
			link = new Node(Idin, Xin, Yin);
		}
		
		public void removeNodeAfter(){
			link = link.link;
		}
			
		public void printNode(Node input){
			//while ()
		}
}
