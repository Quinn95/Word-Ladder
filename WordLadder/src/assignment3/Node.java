package assignment3;

public class Node{
	private String name;
	private Node parent;
	private boolean visited = false;
	
	
	Node(String name, Node parent){
		this.name = name;
		this.parent = parent;
	}
	
	public String getName(){
		return name;
	}
	
	public Node getParent(){
		return parent;
	}
	
	public boolean isVisited(){
		return visited;
	}
	
	public void setVisisted(){
		this.visited = true;
	}

}
