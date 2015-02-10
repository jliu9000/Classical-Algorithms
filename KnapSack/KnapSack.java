import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class KnapSack {

	private int items;
	private double maxWeight;
	private ArrayList<Double> sackValue = new ArrayList<Double>();
	private ArrayList<Double> sackWeight = new ArrayList<Double>();

	ArrayList<Integer> bestItems = new ArrayList<Integer>();
	

	public static void main(String args[]) {

		
		
		
		KnapSack k = new KnapSack();
		k.readFile(args);
		
		ArrayList<Integer> nextItem = new ArrayList<Integer>();
		
		for (int i = 0; i < k.sackValue.size(); i++){
			nextItem.clear();
			nextItem.add(i);
			
			k.KnapSack(i, nextItem);

		}
		
		System.out.println("best sack: " +k.bestItems);
	}

	private void readFile(String args[]) {
		Scanner s = null;
		String line;
		String input[] = null;

		try {
			s = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			// can't find file
			System.out.println("cant find file");
		}

		this.items = s.nextInt();
		this.maxWeight = s.nextDouble();

		s.nextLine();

		// add weight of items
		line = s.nextLine();
		input = line.split(" ");

		for (int i = 0; i < input.length; i++) {
			sackWeight.add(Double.parseDouble(input[i]));
		}

		// add value of items
		line = s.nextLine();
		input = line.split(" ");

		for (int i = 0; i < input.length; i++) {
			sackValue.add(Double.parseDouble(input[i]));
		}

	}

	private ArrayList<Integer> KnapSack(int currentItem, ArrayList<Integer> grabbedItems) {
		
		ArrayList<Integer> temp = new ArrayList<Integer>();
		ArrayList<Integer> grabbedItemsTest = null;
		
		if (findWeight(grabbedItems) > this.maxWeight){
			return null;
		}
		

		
		if (findValue(grabbedItems)>findValue(bestItems)){
			bestItems.clear();
			for (Integer x : grabbedItems){
				bestItems.add(x);
			}
		}

		
		
		for (int i = currentItem+1; i<items; i++){
			grabbedItemsTest = new ArrayList<Integer>(grabbedItems);
			grabbedItemsTest.add(i);
						
			
			temp = KnapSack(i, grabbedItemsTest);
			

		}
		
	
		return bestItems;
	}

	private double findValue (ArrayList<Integer> w) {

		if (w == null){
			return 0;
		}
		
		double value = 0;
		
		
		for (Integer x : w) {
			value += sackValue.get(x);
		}

		return value;
	}
	
	private double findWeight (ArrayList<Integer> w) {

		if (w == null){
			return 0;
		}
		
		double value = 0;
		
		
		for (Integer x : w) {
			value += sackWeight.get(x);
		}

		return value;
	}


}
