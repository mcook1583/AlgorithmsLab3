import java.util.Iterator;


public class BinarySearchTreeTest {

	public static void main(String[] args) {
		BinarySearchTree<Integer> TestTree = new BinarySearchTree<Integer>();

		// construct a tree with the values 16,5,59,86,11 and 32
		TestTree.add(16);
		TestTree.add(5);
		TestTree.add(59);
		TestTree.add(86);
		TestTree.add(11);
		TestTree.add(32);

		// loops through to 100 only printing items in the tree
		for (int i = 0; i <= 100; i++) {
			if (TestTree.contains(i)) {
				System.out.println(i + " is in the tree.");
			}
		}
		// if the numbers added above are all printed then both the add and
		// contains methods work

		// prints the current size of the tree
		System.out.println("The tree has a size of " + TestTree.size());

		// remove one of the values
		TestTree.remove(59);

		System.out.println(59 + " removed.");
		// size should decrease by 1
		System.out.println("The tree has a size of " + TestTree.size());
		// all values from before minus the removed should be printed
		for (int i = 0; i <= 100; i++) {
			if (TestTree.contains(i)) {
				System.out.println(i + " is in the tree.");
			}
		}
		// if both the previous act as expected then the delete method works

		// should print all the values in the tree in ascending order
		Iterator<Integer> TestTreeIterator = TestTree.iterator();
		while (TestTreeIterator.hasNext()) {
			System.out.println(TestTreeIterator.next());
		}
		

		TestTree.add(8);
		TestTree.add(61);
		TestTree.add(106);
		TestTree.add(29);
		TestTree.add(31);
		TestTree.add(1);

		TestTree.add(21);
		TestTree.createFrame();
	}


	
	
}
