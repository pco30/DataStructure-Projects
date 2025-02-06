package cs342;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileTextReader {

	public FileTextReader() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		BufferedReader br = null;
		FileReader fr = null;
		String fileName = "C:/Users/HYACINTH OSEJI/Downloads/Linked_lists.txt"; 
		String [] words = null;
		BinaryTree mytree = new BinaryTree();
		Node root = null;
		
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
		    String line;
		    while ((line = br.readLine()) != null) {
		    	line = line.replaceAll("[^a-zA-Z0-9 ]+", "");
		    	line = line.toLowerCase();
		    	words = line.split(" ");
		    	
		    	for (String word: words)
		    		root = mytree.insert(root, word);
		    	
		    }
		    		    
		} catch (Exception e) {
		    System.err.format("Exception occurred trying to read the file: "+fileName+".\n");
		    e.printStackTrace();
		} finally {
			if (fr!=null) fr.close();
			if (br !=null) br.close();
		}
		
//	    mytree.Traversal(root); // Print out binary tree
    	System.out.println("The word transylvania occurs " + mytree.occurrences(root,"transylvania") + " time(s).");
    	System.out.println("The word harker occurs " + mytree.occurrences(root,"harker") + " time(s).");
    	System.out.println("The word renfield occurs " + mytree.occurrences(root,"renfield") + " time(s).");
    	System.out.println("The word vampire occurs " + mytree.occurrences(root,"vampire") + " time(s).");
    	System.out.println("The word expostulate occurs " + mytree.occurrences(root,"expostulate") + " time(s).");
	    System.out.println("The max depth of the binary search tree is " + mytree.Depth(root));
	    System.out.println("The number of unique words in the binary search tree is " + mytree.unique(root));
	    System.out.println("The word at the root of the binary search tree is '" + mytree.rootWord(root) + "'");
	    System.out.println("The total number of words in the binary search tree is " + mytree.total(root));
	    System.out.println("The most occuring word in the binary search tree is " + mytree.most_occurences(root));
	    System.out.println("The first word in a pre-order traversal of the binary search tree is " + mytree.Preorder(root) + ".");
	    System.out.println("The first word in an in-order traversal of the binary search tree is " + mytree.Inorder(root) + ".");
	    System.out.println("The first word in a post-order traversal of the binary search tree is " + mytree.Postorder(root) + ".");
	    
	    int max = mytree.Depth(root);
	    System.out.print("The word at the max depth is ");
	    mytree.DeepestWords(root, 1, max);
		
	}

}
