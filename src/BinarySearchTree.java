import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.Position;

public class BinarySearchTree<T> extends AbstractSet<T>
{
    private Node<T> root;
    private int size;

    private static class Node<T>
    {
	private T element;
	private Node<T> left = null;
	private Node<T> right = null;
	private Node<T> parent;
	private double across;
	private double depth;
 
	private Node(T element, Node<T> parent, double across, double depth)
	{
	    this.element = element;
	    this.parent = parent;
	    this.across = across;
	    this.depth = depth;
	}
	
	public Node<T> getParent(){
		return parent;
	}
    }

    public BinarySearchTree()
    {
	root = null;
	size = 0;
    }

    public BinarySearchTree(BinarySearchTree<T> other)
    {
	root = null;
	size = 0;
	for (T element: other)
	    add(element);
    }

    public int size()
    {
	return size;
    }

    public Iterator<T> iterator()
    {
	return new TreeIterator();
    }




    public boolean add(T element)
    {
	if (root == null) {
	    root = new Node<T>(element, null, 0.5, 0);
	    size++;
	    return true;
	} else {
	    Node<T> temp = root;
	    int comp;
	    while (true) {
		comp = ((Comparable<T>)(element)).compareTo(temp.element);
		if (comp == 0)
		    return false;
		if (comp<0) {
		    if (temp.left != null)
			temp = temp.left;
		    else {
			temp.left = new Node<T>(element, temp, temp.across-(1/(6*(temp.depth+1))), temp.depth+1);
			size++;
			return true;
		    }
		} else {
		    if (temp.right != null)
			temp = temp.right;
		    else {
			temp.right = new Node<T>(element, temp, temp.across+(1/(6*(temp.depth+1))), temp.depth+1);
			size++;
			return true;
		    }
		}
	    }
	}
    }

    public boolean remove(Object obj)
    {
	Node<T> e = getNode(obj);
	if (e == null)
	    return false;
	deleteNode(e);
	return true;
    }

    private Node<T> getNode(Object obj)
    {
	int comp;
	Node<T> e = root;
	while (e != null) {
	    comp = ((Comparable<T>)(obj)).compareTo(e.element);
	    if (comp == 0)
		return e;
	    else if (comp < 0)
		e = e.left;
	    else
		e = e.right;
	}
	return null;
    }

    public T mapAdd(T obj)
    {
	if (root == null) {
	    root = new Node<T>(obj, null, 0.5,0);
	    size++;
	    return root.element;
	}
	int comp;
	Node<T> e = root;
	Node<T> p = null;
	boolean left = true;
	while (e != null) {
	    p = e;
	    comp = ((Comparable<T>)(obj)).compareTo(e.element);
	    if (comp == 0)
		return e.element;
	    else if (comp < 0) {
		left = true;
		e = e.left;
	    } else {
		e = e.right;
		left = false;
	    }
	}
	if (left)
	    p.left = new Node<T>(obj, p, p.across-(1/(6*(p.depth+1))), p.depth+1);
	else
	    p.right = new Node<T>(obj, p, p.across-(1/(6*(p.depth+1))), p.depth+1);
	size++;
	return e.element;
    }




    public boolean contains(Object obj)
    {
	return getNode(obj) != null;
    }

    private Node<T> deleteNode(Node<T> p)
    {
	size--;
	if (p.left != null && p.right != null) {
	    Node<T> s = successor(p);
	    p.element = s.element;
	    p = s;
	}
		
	Node<T> replacement;
	if (p.left != null)
	    replacement = p.left;
	else
	    replacement = p.right;

	if (replacement != null) {
	    replacement.parent = p.parent;
	    if (p.parent == null)
		root = replacement;
	    else if (p == p.parent.left)
		p.parent.left = replacement;
	    else
		p.parent.right = replacement;
	} else if (p.parent == null) {
	    root = null;
	} else {
	    if (p == p.parent.left)
		p.parent.left = null;
	    else
		p.parent.right = null;
	}
	return p;
    }

    private Node<T> successor(Node<T> e)
    {
	if (e == null) {
	    return null;
	} else if (e.right != null) {
	    Node<T> p = e.right;
	    while (p.left != null)
		p = p.left;
	    return p;
	} else {
	    Node<T> p = e.parent;
	    Node<T> child = e;
	    while (p != null && child == p.right) {
		child = p;
		p = p.parent;
	    }
	    return p;
	}
    }

    private class TreeIterator implements Iterator<T>
    {
	private Node<T> lastReturned = null;
	private Node<T> next;

	private TreeIterator()
	{
	    next = root;
	    if (next != null)
		while (next.left != null)
		    next = next.left;
	}

	public boolean hasNext()
	{
	    return next != null;
	}

	public T next()
	{
	    if (next == null)
		throw new NoSuchElementException();
	    lastReturned = next;
	    next = successor(next);
	    return lastReturned.element;
	}

	public void remove()
	{
	    if (lastReturned == null)
		throw new IllegalStateException();
	    if (lastReturned.left != null && lastReturned.right != null)
		next = lastReturned;
	    deleteNode(lastReturned);
	    lastReturned = null;
	}
    }
    
    public float averageDepth(){
    	return totalDepth(root,0)/size;
    }
    
    public float totalDepth(Node<T> e, int depth){
    	if(e==null){
    		return 0;
    	}
    	return depth + totalDepth(e.right,depth + 1) + totalDepth(e.left,depth + 1);	
    }
    
    public void createFrame(){
    	new TreeFrame("Tree", this);
    }
    
    class TreeFrame extends JFrame {

    	TreeFrame(String s, BinarySearchTree<T> tree){ 
    		super(s);
    	    TreePanel tp = new TreePanel(tree);
    		this.setContentPane(tp);
    		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	
    		this.setSize(700,600);
    		this.setVisible(true);
        }

    }

    class TreePanel extends JPanel {

    	private BinarySearchTree<T> tree;
    	
    	public TreePanel(BinarySearchTree<T> tree) {
    		this.tree = tree;
    		repaint();
    	}
    	
    	public void paintComponent(Graphics g) {
    		Graphics2D g2d = (Graphics2D) g;
    		super.paintComponent(g);
    		for(T element : tree){
    			if(!getNode(element).equals(root)){
    				g2d.drawLine((int)((getNode(element).getParent().across)*this.getWidth())+6, (int)((this.getHeight()/6)+((getNode(element).depth-1)*(this.getHeight()/6)))+2, (int)((getNode(element).across)*this.getWidth())+6, (int)((this.getHeight()/6)+((getNode(element).depth)*(this.getHeight()/6)))-12);
    			}
    			g2d.drawString(element.toString(), (int)((getNode(element).across)*this.getWidth()), (int)((this.getHeight()/6)+((getNode(element).depth)*(this.getHeight()/6))));
    		}
    	}
       
    }
    
    
}