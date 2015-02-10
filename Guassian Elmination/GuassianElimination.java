import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GuassianElimination {

	int totalEq = 0;
	double augMatrix[][];

	private void readFile(String args[]) {

		Scanner s = null;

		try {
			s = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			// can't find file
			System.out.println("cant find file");
			System.exit(0);
		}

		this.totalEq = s.nextInt();
		this.augMatrix = new double[totalEq][totalEq + 1];

		for (int i = 0; i < this.totalEq; i++) {
			for (int j = 0; j < this.totalEq; j++) {
				this.augMatrix[i][j] = s.nextDouble();
			}

		}

		for (int i = 0; i < this.totalEq; i++) {
			this.augMatrix[i][totalEq] = s.nextDouble();
		}

	}

	private static void printMatrix(double a[][]) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out
						.print((double) Math.round(a[i][j] * 100) / 100 + " ");
			}
			System.out.println(" ");
		}

	}

	private static double[][] swap(double m[][], int a, int b) {
		double result[][] = new double[m.length][m[0].length];

		for (int i = 0; i < m[0].length; i++) {
			result[0][i] = m[a][i];
			result[1][i] = m[b][i];
		}

		for (int i = 0; i < m[0].length; i++) {
			m[a][i] = result[1][i];
			m[b][i] = result[0][i];
		}

		return m;
	}

	public static void main(String args[]) {

		GuassianElimination g = new GuassianElimination();

		g.readFile(args);

		double result[][];

		result = g.GuassianElim(g.augMatrix);

		//printMatrix(result);

		double result2[] = g.backSolve(result);

		// System.out.println("-------------------------");
		if (result2 == null) {
			System.out.println("Solution does not exist");
		} else {
			for (int i = 0; i < result2.length; i++) {
				System.out.print((double) Math.round(result2[i] * 1000) / 1000
						+ " ");
			}
		}
	}

	private double[][] GuassianElim(double a[][]) {
		int pivotRow = 0;

		for (int i = 0; i < a.length - 1; i++) {
			pivotRow = i;
			for (int j = i + 1; j < a.length; j++) {
				if (Math.abs(a[j][i]) > Math.abs(a[pivotRow][i])) {
					pivotRow = j;
				}
			}

			swap(a, i, pivotRow);

			double temp;

			for (int j = i + 1; j < a.length; j++) {
				temp = a[j][i] / a[i][i];
				for (int k = i; k < a[0].length; k++) {
					a[j][k] = a[j][k] - a[i][k] * temp;
				}

			}

		}

		return a;
	}

	private double[] backSolve(double a[][]) {
		double result[] = new double[a.length];
		double temp;

		for (int i = 0; i < a.length; i++) {
			for (int j = i; j < a.length; j++) {
				if (a[i][j] == 0)
					return null;
			}
		}

		result[result.length - 1] = 1;

		for (int i = a.length - 1; i >= 0; i--) {
			temp = a[i][a[0].length - 1];
			for (int j = i + 1; j < a.length; j++) {
				temp = temp - result[j] * a[i][j];
			}
			result[i] = temp / a[i][i];
		}

		return result;
	}
}
