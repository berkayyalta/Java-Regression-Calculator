package Main;

import java.util.ArrayList;

import Regression.Regression;

public class main {

	public static void main(String[] args) throws Exception 
	{
		
		double[][] a1 = {{1.0, 2.0}, {3.0, 5.0}};
		double[][] a2 = {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}, {7.0, 8.0, 10.0}};
		double[][] a3 = {{1.0, 2.0}, {3.0, 4.0}};
		
		double[] x1 = {49.0, 69.0, 89.0, 99.0, 109.0, 121.0, 135.0, 145.0};
		double[] x2 = {10.0, 15.0, 35.0, 40.0, 55.0, 65.0, 75.0, 80.0};
		double[] x3 = {240.0, 200.0, 180.0, 140.0, 120.0, 100.0, 80.0, 30.0};
		
		ArrayList<double[]> x = new ArrayList<double[]>();
		x.add(x1);
		x.add(x2);
		x.add(x3);
		double[] y = {124.0, 95.0, 71.0, 45.0, 18.0, 8.0, 4.0, 1.0};
		
		Regression regr = new Regression(x, y, 2);
		System.out.println(regr);
		
		ArrayList<Double> pred = new ArrayList<Double>();
		pred.add(20.0);
		pred.add(50.0);
		pred.add(120.0);
		System.out.println(regr.predict(pred));
		
	}
	
}
