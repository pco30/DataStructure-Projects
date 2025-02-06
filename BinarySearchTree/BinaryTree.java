package cs342;

class Node {
    String key;
    int count; // Count occurrences of the word
    Node left, right;

    public Node(String item) {
        key = item;
        count = 1; // Initialize count to 1
        left = right = null;
    }
}

public class BinaryTree {
    // Insert a new node or update the count if the key already exists
    static Node insert(Node root, String key) {
        // If the tree is empty, create a new node
        if (root == null) {
            return new Node(key);
        }

        // Compare the key and insert into the correct subtree or increment the count
        if (key.equals(root.key)) {
            root.count++; // Increment count for duplicate word
        } else if (key.compareTo(root.key) < 0) {
            root.left = insert(root.left, key); // Insert into the left subtree
        } else {
            root.right = insert(root.right, key); // Insert into the right subtree
        }
        return root; // Return the updated root
    }

    // In-order traversal to display the BST
    static void Traversal(Node root) {
        if (root != null) {
            Traversal(root.left);
            System.out.println(root.key);
            Traversal(root.right);
        }
    }

    // Method to count occurrences of a word
    public int occurrences(Node root, String key) {
        if (root == null) {
            return 0; // word not in tree
        }
 
        if (key.equals(root.key)) {
            return root.count;
        } else if (key.compareTo(root.key) < 0) {
            return occurrences(root.left, key); // Search in the left subtree
        } else {
            return occurrences(root.right, key); // Search in the right subtree
        }
    }
    
    // Method to find max depth of tree
    static int Depth(Node root) {
        if (root == null)
            return 0; // empty tree

     // recursive calls to find depth of left and right subtree
        int left_tree = Depth(root.left); 
        int right_tree = Depth(root.right);

        return Math.max(left_tree, right_tree) + 1; // find max 
    }
    
    // Method to count number of unique words
    public int unique(Node root) {
        if (root == null) {
            return 0; // empty
        }

        int count = 1;

        // Recursively calls to left and right subtree
        count += unique(root.left);
        count += unique(root.right);

        return count;
    }
    
    // Method to return root word
    public String rootWord(Node root) {
    	if (root == null) {
    		return null;
    	}
    	return root.key;
    }
    
 // Method to find the words at the deepest leaves of the tree
    public void DeepestWords(Node root, int currentDepth, int max) {
        if (root == null) {
            return; 
        }

        // Print the word at max depth if current depth is max depth
        if (currentDepth == max) {
            System.out.println(root.key); 
        }

        // Recursively find words at the deepest level in the left and right subtrees
        DeepestWords(root.left, currentDepth + 1, max);
        DeepestWords(root.right, currentDepth + 1, max);
    }
    
    // Method to return total number of words in the tree
    public int total(Node root) {
        if (root == null) {
            return 0; 
        }

        // Recursive traversal to sum up the counts
        return root.count + total(root.left) + total(root.right);
    }
    
    // Method to return first word in pre-order traversal
    public String Preorder(Node root) {
    	if (root == null) {
    		return null;
    	}
    	return root.key; // Root is the first word in pre-order traversal
    }
        
 // Method to return first word in in-order traversal
    public String Inorder(Node root) {
        if (root == null) {
            return null; 
        }

        if (root.left == null) {
            return root.key; 
        }
        // Leftmost node is the first in in-order traversal
        return Inorder(root.left); // Recursively find the leftmost node
    }
    
 // Method to return first word in post-order traversal
    public String Postorder(Node root) {
        if (root == null) {
            return null; 
        }

        // Traverse to the deepest left node
        Node current = root;
        while (current.left != null || current.right != null) {
            if (current.left != null) {
                current = current.left;
            } else {
                current = current.right; 
            }
        }

        return current.key; // Leftmost right node is the first word in post-order traversal
    }
    
 // Method to find the word with the maximum occurrences
    public String most_occurences(Node root) {
        if (root == null) {
            return null; // Empty tree
        }

        String[] result = new String[1];
        int[] maxCount = new int[1]; // To store the maximum count

        // Call the helper function
        FindMostOccurences(root, result, maxCount);

        return result[0]; // Return the word with the highest count
    }

    // Method to traverse the tree and update the maximum word
    private void FindMostOccurences(Node root, String[] result, int[] maxCount) {
        if (root == null) {
            return;
        }

        // Check current position
        if (root.count > maxCount[0]) {
            maxCount[0] = root.count;
            result[0] = root.key;
        }

        // Recur for left and right trees
        FindMostOccurences(root.left, result, maxCount);
        FindMostOccurences(root.right, result, maxCount);
    }
}
