package Main;

import java.math.BigInteger;
import java.util.Arrays;

import Math.Matrix;
import Regression.Regression;

public class main {

	public static void main(String[] args) throws Exception {
		
		Double[][] a1 = {{1.0, 2.0}, {3.0, 5.0}};
		Double[][] a2 = {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}, {7.0, 8.0, 10.0}};
		Double[][] a3 = {{1.0, 2.0}, {3.0, 4.0}};
		
		Matrix A = new Matrix(a1);
		Matrix B = new Matrix(a2);
		
		Double[] x = {49.0, 69.0, 89.0, 99.0, 109.0};
		Double[] y = {124.0, 95.0, 71.0, 45.0, 18.0};
		
		Regression regr = new Regression(x, y, 4);
		System.out.println(regr.predict(49.0));
		System.out.println(regr);
		
	}
	
}
