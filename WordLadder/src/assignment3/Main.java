/* WORD LADDER Main.java
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
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin
			ps = System.out;			// default to Stdout
		}
		initialize();
        	ArrayList<String> start_end = parse(kb);
		
		ArrayList<String> ladderBFS = getWordLadderBFS(start_end.get(0), start_end.get(1));
		printLadder(ladderBFS, start_end);

		ArrayList<String> ladderDFS = getWordLadderDFS(start_end.get(0), start_end.get(1));
		printLadder(ladderDFS, start_end);

	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
		
		
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		String input = keyboard.nextLine().toUpperCase();
		input.trim(); //incase they have any leading or trailing spaces
		if (input.equals("/quit")){
			System.exit(0);
		}
		String[] words = input.split(" ");
		ArrayList<String> start_end = new ArrayList<String>(Arrays.asList(words));
		return start_end;
	}
	
	/**
	 * This method will return the node that contains the ending word of the ladder.
	 * @param n is the Node we are checking
	 * @param end is the string of the ending word on the ladder
	 * @param dict contains all the valid words
	 * @param history an ArrayList<String> that will contain all the words we have gone over to avoid duplicates
	 * @return the Node that contains the ending word as its name or null
	 */
	private static Node DFSTree(Node n, String end, Set<String> dict, ArrayList<String> history){
		if (n == null){
			return null;
		}
		history.add(n.getName().toUpperCase());
		if (n.getName().toUpperCase().equals(end)){
			return n;
		}
		else{
			for(int i = 0; i < n.getName().length(); i++){
				for(char j = 'a'; j <= 'z'; j++){
					char[] permutations = n.getName().toCharArray();
					if (permutations[i] != j){
						permutations[i] = j;
						String permutationString = new String(permutations).toUpperCase();
						if(dict.contains(permutationString) == true){
							if(history.contains(permutationString) == false){
								Node nn = new Node(permutationString.toUpperCase(), n);
								Node w = DFSTree(nn, end.toUpperCase(), dict, history);
								if (w != null){
									return w;
								}
							}
						}
					}
				}
			}
			return null;
		}
	}
	
	/**
	 * This method returns the word ladder based on the DFS algorithm
	 * @param start is the string of the beginning word on the ladder
	 * @param end is the string of the ending word on the ladder
	 * @return an ArrayList<String> that cointains the starting and ending words on the ladder
	 */
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		Set<String> dict = makeDictionary();
		ArrayList<String> returnVal = new ArrayList<String>();
		ArrayList<String> history = new ArrayList<String>();
		
		Node n = DFSTree(new Node(start.toUpperCase(), null), end.toUpperCase(), dict, history);
		
		if (n == null){
			return (returnVal);
		}
		
		recursiveListMaker(n, returnVal);
		
		return (returnVal);
		
	}
	
	/**
	 * This method adds the words on the ladder to the ArrayList<String> 1
	 * @param n is the ending Node of the ladder
	 * @param l is an ArrayList<String> that will contain all the words on the ladder
	 */
	private static void recursiveListMaker(Node n, ArrayList<String> l){
		if(n.getParent() != null){
			recursiveListMaker(n.getParent(), l);
		}
		if(n != null){
			l.add(n.getName());
		}
	}
	
	/**
	 * This method returns the word ladder based on the BFS algorithm
	 * @param start is the string of the beginning word on the ladder
	 * @param end is the string of the ending word on the ladder
	 * @return an ArrayList<String> of all the words that needs to be printed out for the ladder
	 */
    	public static ArrayList<String> getWordLadderBFS(String start, String end) {
		if(start.equals(end)){
			//do stuff
		}
    	
		// TODO some code
		Set<String> dict = makeDictionary();
				
		//history to avoid checking duplicates. It's hashed, so it's O(1)
		Set<String> history = new HashSet<String>();

		
	    ArrayList<String> returnVal = new ArrayList<String>();
				
		LinkedList<Node> queue = new LinkedList<Node>(); //Queue to keep track of words
		queue.add(new Node(start.toUpperCase(), null));
		
		char[] permutations;
		Node head;
		while(queue.isEmpty() == false){
			head = queue.pop();
			if(head.getName().equals(end.toUpperCase())){
				recursiveListMaker(head, returnVal);
				break;
			}
			
			if(history.contains(head.getName().toUpperCase()) == false){
				history.add(head.getName().toUpperCase());
				
				//generate permutations (neighbors/(children?)) of head
				for(int i = 0; i < head.getName().length(); i++){
					for(char j = 'a'; j <= 'z'; j++){
						permutations = head.getName().toCharArray();
						permutations[i] = j;
						String permutationString = new String(permutations).toUpperCase();
						if(history.contains(permutationString) == false){
							if(dict.contains(permutationString)){
								queue.add(new Node(permutationString, head));
							}
						}
					}
				}
			}					
		}
		return returnVal; 
	}
    
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
	
	/**
	 * This method prints out the ladder
	 * @param ladder is a ArrayList<String> that contains all the words of the ladder.
	 * @param start_end is an ArrayList<String> that contains just the beginning and ending words of the ladder.
	 */
	public static void printLadder(ArrayList<String> ladder, ArrayList<String> start_end) {
		if (ladder.size() == 0){
			System.out.println("no word ladder can be found between " + start_end.get(0).toLowerCase() + " and " + start_end.get(1).toLowerCase() + ".");
		}
		else{
	        	System.out.println("a " + (ladder.size() - 2) + "-rung word ladder exist between "+ ladder.get(0).toLowerCase() + " and " + ladder.get(ladder.size() - 1).toLowerCase() + ".");
			for(String s : ladder){
				System.out.println(s.toLowerCase());
			}
		}
	}
}
