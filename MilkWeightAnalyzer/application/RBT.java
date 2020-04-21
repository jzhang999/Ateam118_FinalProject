package application;

import java.util.ArrayList;
import java.util.List;

/**
 * A red black tree to store the data in the project. This red black tree has
 * all the functions except deletion because we don't need to delete node in
 * this project.
 * 
 * @author Xinrui Liu
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public class RBT<K extends Comparable<K>, V> {

	//Constants that represents the color of the nodes.
    public static final int RED = 0;
    public static final int BLACK = 1;
    
    private Node root;// The root of this RBT
	private int size;// The number of keys in this RBT
	
	/**
	 * An inner class that represents a single node of this RBT
	 * @author Xinrui Liu
	 *
	 */
	private class Node {
		private K key;// The key of this node
		private V value;// The value of this node
		private int color;// The color of this node
		private Node leftChild;// The left child of this node
		private Node rightChild;// The right child of this node
		
		/**
		 * Constructs a node with the given key and value.
		 * @param key
		 * @param value
		 */
		private Node(K key, V value) {
			this.key = key;
			this.value = value;
			this.color = RED;
			this.leftChild = null;
			this.rightChild = null;
		}
	}
	
	/**
	 * Constructs an empty RBT.
	 */
    public RBT() {
    	this.root = null;
    	this.size = 0;
    }
    
    /**
     * Returns the keys of the data structure in sorted order.
     * In the case of binary search trees, the visit order is: L V R
     * 
     * If the SearchTree is empty, an empty list is returned.
     * 
     * @return List of Keys in-order
     */
    public List<K> getInOrderTraversal() {
    	List<K> result = new ArrayList<K>();
    	getInOrderTraversalH(root, result);
    	return result;
    }
    
    /**
     * Recursive helper method for getInOrderTraversal().
     * 
     * @param cur the current node in the recursion
     * @param result the result ArrayList<K>
     */
    private void getInOrderTraversalH(Node cur, List<K> result) {
    	if (cur == null) {
    		return;
    	}
    	getInOrderTraversalH(cur.leftChild, result);
    	result.add(cur.key);
    	getInOrderTraversalH(cur.rightChild, result);
    }
    
    /** 
     * Add the key,value pair to the data structure and increase the number of keys.
     * 
     * @param key the key of the key value pair
     * @param value the value of the key value pair
     */
    public void insert(K key, V value) {
    	if (size==0) {
    		root = new Node(key, value);
    		root.color = BLACK;
    		size++;
    		return;
    	}
        root = insertH(root, key, value);
        root.color = BLACK;
        size++;
    }
    
    /**
     * Recursive helper method for insert(K key, V value).
     * 
     * @param cur current node in the recursion
     * @param key the key of the new node
     * @param value the value of the new node
     * @return the current node
     */
    private Node insertH(Node cur, K key, V value) {
    	if (cur == null) {
    		return new Node(key,value);
    	} 
    	else if (key.compareTo(cur.key) < 0) {
    		cur.leftChild = insertH(cur.leftChild, key, value);
    		cur = rebalance(cur);
    		return cur;
    	}
    	else {
    		cur.rightChild = insertH(cur.rightChild, key, value);
    		cur = rebalance(cur);
    		return cur;
    	}
    }
    
    /**
     * Helper method that rebalance the RBT
     * 
     * @param g the grandparent node
     * @return the new root of this sub-tree
     */
    private Node rebalance(Node g) {
    	Node p1 = g.leftChild;
    	Node p2 = g.rightChild;
    	if (p1 != null) {
    		Node k1 = p1.leftChild;
    		Node k2 = p1.rightChild;
    		if (k1 != null && k1.color == RED && p1.color == RED) {
    			if (p2 != null && p2.color == RED) {
    				recolor(g);
    				return g;
    			}
    			else {
    				g = rightRotate(g);
    				return g;
    			}
    		}
    		else if (k2 != null && k2.color == RED && p1.color == RED) {
    			if (p2 != null && p2.color == RED) {
    				recolor(g);
    				return g;
    			}
    			else {
    				g = leftRightRotate(g);
    				return g;
    			}
    		}
    	}
    	if (p2 != null) {
    		Node k1 = p2.leftChild;
    		Node k2 = p2.rightChild;
    		if (k1 != null && k1.color == RED && p2.color == RED) {
    			if (p1 != null && p1.color == RED) {
    				recolor(g);
    				return g;
    			}
    			else {
    				g = rightLeftRotate(g);
    				return g;
    			}
    		}
    		else if (k2 != null && k2.color == RED && p2.color == RED) {
    			if (p1 != null && p1.color == RED) {
    				recolor(g);
    				return g;
    			}
    			else {
    				g = leftRotate(g);
    				return g;
    			}
    		}
    	}
    	return g;
    }
    
    /**
     * Helper method that complete the right-rotate of the tri-node restructure
     * @param g the grandparent node
     * @return the new root of this subtree
     */
    private Node rightRotate(Node g) {
    	Node p = g.leftChild;
//    	Node k = p.leftChild;
    	Node temp = p.rightChild;
    	p.rightChild = g;
    	g.leftChild = temp;
    	p.color = BLACK;
    	g.color = RED;
    	return p;
    }
    
    /**
     * Helper method that complete the left-rotate of the tri-node restructure
     * @param g the grandparent node
     * @return the new root of this subtree
     */
    private Node leftRotate(Node g) {
    	Node p = g.rightChild;
//    	Node k = p.rightChild;
    	Node temp = p.leftChild;
    	p.leftChild = g;
    	g.rightChild = temp;
    	p.color = BLACK;
    	g.color = RED;
    	return p;
    }
    
    /**
     * Helper method that complete the right-left-rotate of the tri-node restructure
     * @param g the grandparent node
     * @return the new root of this subtree
     */
    private Node rightLeftRotate(Node g) {
    	Node p = g.rightChild;
    	Node k = p.leftChild;
    	Node tempLeft = k.leftChild;
    	Node tempRight = k.rightChild;
    	k.leftChild = g;
    	k.rightChild = p;
    	g.rightChild = tempLeft;
    	p.leftChild = tempRight;
    	k.color = BLACK;
    	g.color = RED;
    	return k;
    }
    
    /**
     * Helper method that complete the left-right-rotate of the tri-node restructure
     * @param g the grandparent node
     * @return the new root of this subtree
     */
    private Node leftRightRotate(Node g) {
    	Node p = g.leftChild;
    	Node k = p.rightChild;
    	Node tempLeft = k.leftChild;
    	Node tempRight = k.rightChild;
    	k.leftChild = p;
    	k.rightChild = g;
    	p.rightChild = tempLeft;
    	g.leftChild = tempRight;
    	k.color = BLACK;
    	g.color = RED;
    	return k;
    }
    
    /**
     * Helper method that recolor the nodes to maintain red property
     * @param g the grandparent node
     */
    private void recolor(Node g) {
    	g.color = RED;
    	g.leftChild.color = BLACK;
    	g.rightChild.color = BLACK;
    }
    
    /**
     * Returns the value associated with the specified key.
     *
     * @param key the key of the target
     * @return the value of the given key
     * @throws IllegalArgumentException if the key is not found.
     */
    public V get(K key) {
        V target = getH(root, key);
        return target;
    }
    
    /**
     * Recursive helper method for get(K key).
     * 
     * @param cur current node in the recursion
     * @param key the key of the node that we need to find
     * @return the value of the target node
     * @throws IllegalArgumentException if the key is not found.
     */
    private V getH(Node cur, K key) {
    	if (cur == null) {
    		throw new IllegalArgumentException("Key not found.");
    	} 
    	else if (key.compareTo(cur.key) == 0) {
    		return cur.value;
    	}
    	else if (key.compareTo(cur.key) < 0) {
    		return getH(cur.leftChild, key);
    	}
    	else {
    		return getH(cur.rightChild, key);
    	}
    }

    /** 
     * Returns true if the key is in the data structure
     * 
     * @return true if the key is in the red black tree, false otherwise
     */
    public boolean contains(K key) { 
        return containsH(root, key);
    }
    
    /**
     * Recursive helper method for contains().
     * 
     * @param cur the current node in the recursion
     * @param key the key of the target node
     * @return true if node is found, false otherwise
     */
    private boolean containsH(Node cur, K key) {
    	if (cur == null) {
    		return false;
    	} 
    	else if (key.compareTo(cur.key) == 0) {
    		return true;
    	}
    	else if (key.compareTo(cur.key) < 0) {
    		return containsH(cur.leftChild, key);
    	}
    	else {
    		return containsH(cur.rightChild, key);
    	}
    }
    
    /**
     * Accessor of the size of the tree.
     * 
     * @return the nubmer of keys in the tree.
     */
    public int size() {
    	return size;
    }
}
