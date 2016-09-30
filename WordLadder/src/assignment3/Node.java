/* WORD LADDER Node.java
 * EE422C Project 3 submission by
 * <Kevin Wong>
 * <kw25779>
 * <16475>
 * <Quinten Zambeck>
 * <qaz62>
 * <16470>
 * Slip days used: <1>
 * Git URL: https://github.com/Quinn95/Word-Ladder.git
 * Fall 2016
 */

package assignment3;

public class Node{
	private String name;
	private Node parent;
	
	Node(String name, Node parent){
		this.name = name.toUpperCase();
		this.parent = parent;
	}
	
	public String getName(){
		return name;
	}
	
	public Node getParent(){
		return parent;
	}
}
