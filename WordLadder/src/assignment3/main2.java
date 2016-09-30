/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL:
 * Fall 2016
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	
	private static void recursiveListMaker(Node n, ArrayList<String> l){
		if(n.getParent() != null){
			recursiveListMaker(n.getParent(), l);
		}
		l.add(n.getName());
	}
	
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

		// TODO methods to read in words, output ladder

        getWordLadderDFS("smart", "money");
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
		// TO DO
        System.out.print("Enter starting and ending word: ");
        String input = keyboard.nextLine();
        input.trim(); //incase they have any leading or trailing spaces
        if (!input.equals("/quit")){
            String[] words = input.split(" ");
            ArrayList<String> start_end = new ArrayList<String>(Arrays.asList(words));
            return start_end;
        }
        return null;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// Return empty list if no ladder.
		// TODO some code
		Set<String> dict = makeDictionary();
		
		ArrayList<Node> history = new ArrayList<Node>();
		LinkedList<Node> queue = new LinkedList<Node>(); //Queue to keep track of words
		Node head = new Node(start, null);
		queue.add(head);
		
		char[] permutations;

		for(int i = 0; i < head.getName().length(); i++){
			for(char j = 'a'; j <= 'z'; j++){
				permutations = head.getName().toCharArray();
				permutations[i] = j;
				String permutationString = new String(permutations).toUpperCase();
				if(dict.contains(permutationString)){
					if(history.contains(permutationString) == false){
						queue.add(new Node(permutationString, head));
					}
				}
			}
		}
		
		boolean inTree = DFS(queue, end, history);
		
		if (!inTree){
			System.out.println("No word ladder");
		}
		
		
		
		// TODO more code
		
		return null; // replace this line later with real return
	}
	
	public static boolean DFS(LinkedList<Node> queue, String end, ArrayList<Node> history){
		Node head = queue.pop();
		if (head == null){
			return false;
		}
		history.add(head);
		if (head.getName().equals(end)){
			return true;
		}
		else
			while (!history.contains(head)){
				boolean found = DFS(queue, end, history);
				if (found) { 
					ArrayList<String> words = new ArrayList<String>();
					recursiveListMaker(head, words);
					System.out.println(words);
					return true; 
				}
			}
		return false;
	}		

	
	
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
		queue.add(new Node(start, null));
		
		char[] permutations;
		Node head;
		while(queue.isEmpty() == false){
			head = queue.pop();
			System.out.println(head.getName());
			if(head.getName().equals(end.toUpperCase())){
				System.exit(3);
			}
			
			if(history.contains(head.getName().toUpperCase()) == false){
				history.add(head.getName().toUpperCase());
				
				//generate permutations (neighbors/(children?)) of head
				for(int i = 0; i < head.getName().length(); i++){
					for(char j = 'a'; j <= 'z'; j++){
						permutations = head.getName().toCharArray();
						permutations[i] = j;
						String permutationString = new String(permutations).toUpperCase();
						if(dict.contains(permutationString)){
							if(history.contains(permutationString) == false){
								queue.add(new Node(permutationString, head));
							}
						}
					}
				}
				// TODO stuff
			}
		
		
					
		}
		
		// TODO more code
		//if we look for a word in dict, we need to remove it.
		return null; // replace this line later with real return
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
	
	public static void printLadder(ArrayList<String> ladder) {
		
	}
	
	// TODO
	// Other private static methods here
}
