import java.util.Random;

public class AverageTreeDepths {

	public static void main(String[] args){
		Random random = new Random();
		System.out.println("Average Depth:");
		for(int i=1;i<1001;i++){
			BinarySearchTree<Integer> tempTree = new BinarySearchTree<Integer>();
			for(int n=0;n<i;n++){
				int p = random.nextInt(1000);
				tempTree.add(p);
			}
			System.out.println(tempTree.averageDepth());
		}
	}
	
}
