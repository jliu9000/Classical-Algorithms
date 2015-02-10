import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class DynamicKnapSack {

	private int totalItems, totalWeight;
	private int itemValue[], itemWeight[];
	private int tableau[][];
	private ArrayList<Integer> finalSolution = new ArrayList<Integer>();
	private int optimumValue = 0, optimumWeight = 0;

	private void readFile(String args[]) {

		Scanner s = null;

		try {
			s = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			// can't find file
			System.out.println("cant find file");
		}

		this.totalItems = s.nextInt();
		this.totalWeight = s.nextInt();

		this.itemValue = new int[this.totalItems];
		this.itemWeight = new int[this.totalItems];
		this.tableau = new int[this.totalItems + 1][this.totalWeight + 1];

		for (int i = 0; i < totalItems; i++) {
			itemWeight[i] = s.nextInt();
		}

		for (int i = 0; i < totalItems; i++) {
			itemValue[i] = s.nextInt();
		}

	}

	public static void main(String args[]) {

		DynamicKnapSack d = new DynamicKnapSack();

		d.readFile(args);
		d.makeTableau();
		d.backSolve();

		//calculate totals
		for (Integer i : d.finalSolution) {
			d.optimumWeight += d.itemWeight[i - 1];
			d.optimumValue += d.itemValue[i - 1];
		}
		
		// print solutions
		System.out.println("Resulting table: ");
		for (int i = 0; i < d.totalItems + 1; i++) {
			System.out.print("| ");
			for (int j = 0; j < d.totalWeight + 1; j++) {
				System.out.print(d.tableau[i][j] + " ");

			}
			System.out.println("|");
		}
		System.out.println("");
		System.out.println("Max capacity: " + d.totalWeight);
		System.out.println("Optimal Knapsack (indexed on 1): "
				+ d.finalSolution);

		System.out.println("Optimal Values: " +d.formatResults(d.itemValue,d.finalSolution));
		System.out.println("Optimal Weights: " +d.formatResults(d.itemWeight,d.finalSolution));


		System.out.println("Optimum value taken: " + d.optimumValue);
		System.out.println("Optimum weight taken: " + d.optimumWeight);

	}

	private String formatResults(int values[], ArrayList<Integer> solution) {
		String temp = "[";
		if (!solution.isEmpty()) {
			for (Integer i : solution) {
				temp += values[i - 1] + ", ";
			}
			temp = temp.substring(0, temp.length() - 2);
			temp += "]";
			return temp;
		} else {
			return null;
		}
	}

	private void makeTableau() {

		int g, h;

		for (int i = 1; i <= this.totalItems; i++) {
			for (int j = 1; j <= this.totalWeight; j++) {
				if (j - this.itemWeight[i - 1] >= 0) {
					g = tableau[i - 1][j];
					h = itemValue[i - 1]
							+ tableau[i - 1][j - this.itemWeight[i - 1]];
					if (g > h) {
						tableau[i][j] = g;
					} else {
						tableau[i][j] = h;
					}
				} else {
					tableau[i][j] = tableau[i - 1][j];
				}

			}
		}

	}

	private void backSolve() {
		int i = this.totalItems;
		int j = this.totalWeight;

		if (this.tableau[totalItems][totalWeight] == 0) {

		} else {

			while (i >= 1 && j >= 1) {
				while (this.tableau[i][j] == this.tableau[i - 1][j]) {
					i--;
				}
				finalSolution.add(i);
				j = j - this.itemWeight[i - 1];
				i--;

			}

			Collections.sort(finalSolution);
		}

	}

}
