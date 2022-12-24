package Regression;

import java.util.ArrayList;
import Math.Matrix;

final public class Regression 
{

	//Class fields
	private int degree;
	private Double[] betas;
	
	//Constructors
	public Regression(Double[] x, Double[] y, int degree)
	{
		if (x.length == y.length)
		{
			this.degree = degree;
			ArrayList<Double[]> AL = new ArrayList<Double[]>();
			AL.add(x);
			Matrix X = x_matrix(AL);
			Matrix Y = y_matrix(y);		
			calculate(X, Y);
		}
		else
		{
			throw new IllegalArgumentException("X and Y arrays must be at the same size.");
		} 
	}
	
	public Regression(ArrayList<Double[]> x, Double[] y, int degree)
	{
		if (x.get(0).length == y.length)
		{
			this.degree = degree;
			Matrix X = x_matrix(x);
			Matrix Y = y_matrix(y);		
			calculate(X, Y);
		}
		else
		{
			throw new IllegalArgumentException("X and Y arrays must be at the same size.");
		} 
	}
	
	//Class methods
	private Matrix x_matrix(ArrayList<Double[]> x)
	{
		Double[][] x_matrix = new Double[x.get(0).length][1];
		for (int row_no = 0; row_no < x.get(0).length; row_no++)
		{
			x_matrix[row_no][0] = 1.0;
		}		
		Matrix X = new Matrix(x_matrix);		
		for (int d = 1; d <= degree; d++)
		{
			for (Double[] x_array : x)
			{
				X = X.add_column(pow_column(x_array, d));
			}	
		}
		return X;
	}
	
	private Matrix y_matrix(Double[] y)
	{
		Double[][] y_matrix = new Double[y.length][1];
		for (int item_no = 0; item_no < y.length; item_no++)
		{
			y_matrix[item_no][0] = y[item_no];
		}
		return new Matrix(y_matrix);
	}
	
	private Double[] pow_column(Double[] array, int dg)
	{
		Double[] new_array = new Double[array.length];
		for (int item_no = 0; item_no < array.length; item_no++)
		{
			new_array[item_no] = Math.pow(array[item_no], dg);
		}
		return new_array;
	}
	
	private void calculate(Matrix X, Matrix Y)
	{
		try 
		{			
			//Calculate
			Matrix T1 = Matrix.matrix_multiplication(X.transpose(), X).inverse();	
			Matrix T2 = Matrix.matrix_multiplication(X.transpose(), Y);
			Matrix B = Matrix.matrix_multiplication(T1, T2);
			//Save
			betas = new Double[B.get_rows()];
			for (int beta_no = 0; beta_no < betas.length; beta_no++)
			{
				betas[beta_no] = B.get(beta_no, 0);
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	//Predict
	public Double predict(Double x)
	{
		Double result = betas[0];
		for (int d = 1; d <= degree; d++)
		{
			result += betas[d] * Math.pow(x, d);
		}
		return result;
	}
	
	public Double predict(ArrayList<Double> x)
	{
		Double result = betas[0];
		for (int d = 1; d <= degree; d++)
		{
			for (int item_no = 0; item_no < x.size(); item_no++)
			{
				result += betas[item_no + 1] * Math.pow(x.get(item_no), d);
			}
		}
		return result;
	}
	
	//Getters, Setters
	public Double[] get_betas()
	{
		return betas;
	}
	
	public String toString()
	{
		String str = "Y = " + betas[0] + "*X^" + 0;
		for (int arg = 1; arg < degree+1; arg++)
		{
			str += " + " + betas[arg] + "*X^" + arg + " ";
		}		
		return str;
	}
	
}
