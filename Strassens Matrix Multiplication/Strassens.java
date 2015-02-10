import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Strassens {

	private int originalSize = 0;
	private double a[][], b[][];
	boolean needsPadding = false;
	
	
	private void readFile (String args[]){
		
		Scanner s = null;

		try {
			s = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			// can't find file
			System.out.println("cant find 1st file");
			System.exit(0);
		}
		
		this.originalSize = s.nextInt();
		int entries = 2;
		
		while(entries<originalSize){
			entries = entries * 2;
		}
		
	
		
		this.a = new double[entries][entries];
		this.b = new double[entries][entries];
		
		
		for (int i = 0; i < originalSize; i ++){
			for (int j = 0; j < originalSize; j++){
				this.a[i][j] = s.nextDouble();
			}
		}
		s.close();
		
		
		
		//read 2nd file
		try {
			s = new Scanner(new File(args[1]));
		} catch (FileNotFoundException e) {
			// can't find file
			System.out.println("cant find 2nd file");
			System.exit(0);
		}

		s.nextLine();
		
		for (int i = 0; i < originalSize; i ++){
			for (int j = 0; j < originalSize; j++){
				this.b[i][j] = s.nextDouble();
			}
		}
		s.close();

	}
	
	private static double[][] addMatrix (double a[][], double b [][]){
		double result[][];
		
		result = new double [a.length][a.length];
		
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }

		
		return result;
		
	}
	
	private static double[][] subtractMatrix (double a[][], double b [][]){
		double result[][];
		
		result = new double [a.length][a.length];
		
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }

		
		return result;
		
	}

	public static void main(String args[]){
		
		Strassens s = new Strassens();
		
		s.readFile(args);
		
//		for (int i = 0; i<s.a.length; i++){
//			for (int j = 0; j<s.a.length; j++){
//				System.out.print(s.a[i][j] + " ");
//			}
//			System.out.println("");
//
//		}
//		
//		System.out.println("");
//		
//		for (int i = 0; i<s.a.length; i++){
//			for (int j = 0; j<s.a.length; j++){
//				System.out.print(s.b[i][j] + " ");
//			}
//			System.out.println("");
//
//		}
		
		double result[][] = s.StrassenRecursive(s.a, s.b);
		
		System.out.println("the result:  ");
		
		for (int i = 0; i < s.originalSize; i++){
			for (int j = 0; j < s.originalSize; j++){
				System.out.print((double)Math.round(result[i][j]*100)/100 +" ");

			}
			System.out.println("");
		}
		
	}
	
	private static void printMatrix(double a[][]){
		for (int i = 0; i < a.length; i++){
			for (int j = 0; j< a.length; j++){
				System.out.print(a[i][j] +" ");
			}
			System.out.println(" ");
		}
		
	}
	
	private double[][] StrassenRecursive(double a[][], double b[][]){
		
		if (a.length == 1){
			double result[][] = new double [1][1];
			result[0][0] = a[0][0] * b[0][0];
			return result;
			
		} else {
			double a00[][], a01[][], a10[][], a11[][];
			double b00[][], b01[][], b10[][], b11[][];
			double m1[][], m2[][], m3[][], m4[][], m5[][], m6[][], m7[][];

			a00 = new double[a.length/2][a.length/2];
			a01 = new double[a.length/2][a.length/2];
			a10 = new double[a.length/2][a.length/2];
			a11 = new double[a.length/2][a.length/2];

			b00 = new double[a.length/2][a.length/2];
			b01 = new double[a.length/2][a.length/2];
			b10 = new double[a.length/2][a.length/2];
			b11 = new double[a.length/2][a.length/2];

			for (int i = 0; i < a00.length; i++){
				for (int j = 0; j < a00.length; j++){
					a00[i][j] = a[i][j];
					b00[i][j] = b[i][j];
					
					a01[i][j] = a[i][j+a00.length];
					b01[i][j] = b[i][j+a00.length];
					
					a10[i][j] = a[i+a00.length][j];
					b10[i][j] = b[i+a00.length][j];

					a11[i][j] = a[i+a00.length][j+a00.length];
					b11[i][j] = b[i+a00.length][j+a00.length];

				}
			}
			
//			System.out.println("a00: ");
//			printMatrix(a00);
//			
//			System.out.println("a01: ");
//			printMatrix(a01);
//			
//			System.out.println("a10: ");
//			printMatrix(a00);
//			
//			System.out.println("a11: ");
//			printMatrix(a00);
//			
			
			
			double temp[][], temp1[][];
			
			temp = addMatrix(a00,a11);
			temp1 = addMatrix(b00,b11);
			m1 = StrassenRecursive(temp,temp1);
			
			temp = addMatrix(a10,a11);
			m2 = StrassenRecursive(temp,b00);
			
			temp = subtractMatrix(b01,b11);
			m3 = StrassenRecursive(a00,temp);
			
			temp = subtractMatrix(b10,b00);
			m4 = StrassenRecursive(a11,temp);
			
			temp = addMatrix(a00,a01);
			m5 = StrassenRecursive(temp,b11);
			
			temp = subtractMatrix(a10,a00);
			temp1 = addMatrix(b00,b01);
			m6 = StrassenRecursive(temp,temp1);
			
			temp = subtractMatrix(a01,a11);
			temp1 = addMatrix(b10,b11);
			m7 = StrassenRecursive(temp,temp1);

			double c[][] = new double [a.length][a.length];
			
			//1st quad
			temp = addMatrix(m1,m4);
			temp = subtractMatrix(temp,m5);
			temp = addMatrix(temp,m7);
			
			for (int i = 0; i<a.length/2; i++){
				for (int j = 0; j<a.length/2; j++){
					c[i][j]=temp[i][j];
				}
			}
			
			//2nd quad
			temp = addMatrix(m3,m5);
			
			for (int i = 0; i<a.length/2; i++){
				for (int j = 0; j<a.length/2; j++){
					c[i][j+a.length/2]=temp[i][j];
				}
			}

			//3rd quad
			
			temp = addMatrix(m2,m4);
			
			for (int i = 0; i<a.length/2; i++){
				for (int j = 0; j<a.length/2; j++){
					c[i+a.length/2][j]=temp[i][j];
				}
			}

			//4th quad
			
			temp = addMatrix(m1,m3);
			temp = subtractMatrix(temp,m2);
			temp = addMatrix(temp,m6);
			
			for (int i = 0; i<a.length/2; i++){
				for (int j = 0; j<a.length/2; j++){
					c[i+a.length/2][j+a.length/2]=temp[i][j];
				}
			}

			return c;
			
		

		}
		
	}
	
	
	
	
}
