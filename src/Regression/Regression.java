//By Berkay

package Regression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import Math.M2A;
import Math.Matrix;

//Class Regression
final public class Regression 
{

	//Class fields
	private int degree;
	private boolean multiple;
	private BigDecimal[] betas;
	
	//Regression Class Constructors
	/**
	 * A constructor for linear/polynomial regression
	 * 
	 * @param x : x array
	 * @param y : y array
	 * @param degree
	 */
	public Regression(double[] x, double[] y, int degree, int decimal_places)
	{
		if (x.length == y.length)
		{
			//Convert x and y to BigDecimals
			BigDecimal[] new_x = new BigDecimal[x.length];
			for (int i = 0; i < x.length; i++)
			{
				new_x[i] = new BigDecimal(x[i]);
			}
			BigDecimal[] new_y = new BigDecimal[y.length];
			for (int i = 0; i < y.length; i++)
			{
				new_y[i] = new BigDecimal(y[i]);
			}
			//Initialize
			this.degree = degree;
			this.multiple = false;
			//double[] x to ArrayList
			ArrayList<BigDecimal[]> AL = new ArrayList<BigDecimal[]>();
			AL.add(new_x);
			//Calculate
			Matrix X = x_matrix(AL);
			Matrix Y = y_matrix(new_y);		
			calculate(X, Y, decimal_places);
		}
		else
		{
			throw new IllegalArgumentException("X and Y arrays must be at the same size.");
		} 
	}
	
	/**
	 * A constructor for multiple linear/polynomial regression
	 * 
	 * @param x : x arrays
	 * @param y : y array
	 * @param degree
	 */
	public Regression(ArrayList<double[]> x, double[] y, int degree, int decimal_places)
	{
		//First length check
		int x_len = x.get(0).length;
		for (double[] x_array : x)
		{
			if (x_array.length != x_len)
			{
				throw new IllegalArgumentException("invalid x arrays.");
			}
		}
		//Second length check
		if (x_len == y.length)
		{
			//Convert x and y to BigDecimals
			ArrayList<BigDecimal[]> new_x = new ArrayList<BigDecimal[]>();	
			for (int ar_no = 0; ar_no < x.size(); ar_no++)
			{
				BigDecimal[] current_ar = new BigDecimal[x.get(ar_no).length];
				for (int it_no = 0; it_no < x.get(ar_no).length; it_no++)
				{
					current_ar[it_no] = new BigDecimal(x.get(ar_no)[it_no]);
				}
				new_x.add(current_ar);
			}
			BigDecimal[] new_y = new BigDecimal[y.length];
			for (int i = 0; i < y.length; i++)
			{
				new_y[i] = new BigDecimal(y[i]);
			}
			//Initialize
			this.degree = degree;
			this.multiple = true;
			//Calculate
			Matrix X = x_matrix(new_x);
			Matrix Y = y_matrix(new_y);		
			calculate(X, Y, decimal_places);
		}
		else
		{
			throw new IllegalArgumentException("X and Y arrays must be at the same size.");
		} 
	}
	
	//Class methods to calculate the regression
	/**
	 * A method to generate the X matrix
	 * 
	 * @param x : x array
	 * @return	: Matrix
	 */
	private Matrix x_matrix(ArrayList<BigDecimal[]> x)
	{
		BigDecimal[][] x_matrix = new BigDecimal[x.get(0).length][1];
		for (int row_no = 0; row_no < x.get(0).length; row_no++)
		{
			x_matrix[row_no][0] = new BigDecimal(1);
		}		
		Matrix X = new Matrix(x_matrix);		
		for (int d = 1; d <= degree; d++)
		{
			for (BigDecimal[] x_array : x)
			{
				X = M2A.add_column(X, pow_column(x_array, d));
			}	
		}
		return X;
	}
	
	/**
	 * A method to add the powered columns to the X matrix
	 * 
	 * @param array : existing column of the X Matrix at index 1
	 * @param dg 	: degree
	 * @return		: array
	 */
	private BigDecimal[] pow_column(BigDecimal[] array, int dg)
	{
		BigDecimal[] new_array = new BigDecimal[array.length];
		for (int item_no = 0; item_no < array.length; item_no++)
		{
			BigDecimal new_item = array[item_no].pow(dg);
			new_array[item_no] = new_item;
		}
		return new_array;
	}
	
	/**
	 * A method to generate the Y matrix
	 * 
	 * @param y : y array
	 * @return	: Matrix
	 */
	private Matrix y_matrix(BigDecimal[] y)
	{
		BigDecimal[][] y_matrix = new BigDecimal[y.length][1];
		for (int item_no = 0; item_no < y.length; item_no++)
		{
			y_matrix[item_no][0] = y[item_no];
		}
		return new Matrix(y_matrix);
	}
	
	/**
	 * Method to calculate the regression
	 * 
	 * @param X : X Matrix
	 * @param Y : Y Matrix
	 */
	private void calculate(Matrix X, Matrix Y, int decimal_places)
	{
		try 
		{			
			//Calculate
			Matrix T1 = Matrix.matrix_multiplication(X.transpose(), X).inverse();
			Matrix T2 = Matrix.matrix_multiplication(X.transpose(), Y);
			Matrix B = Matrix.matrix_multiplication(T1, T2);	
			//Save
			betas = new BigDecimal[B.get_rows()];
			for (int beta_no = 0; beta_no < betas.length; beta_no++)
			{
				betas[beta_no] = B.get(beta_no, 0).setScale(decimal_places, RoundingMode.HALF_UP);
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	//Methods to Predict
	/**
	 * Predict method for normal regressions
	 * 
	 * @param x : x value to be predicted
	 * @return	: BigDecimal
	 */
	public BigDecimal predict(double x)
	{
		//If is not a multiple regression
		if (!multiple)
		{
			BigDecimal result = betas[0];
			for (int d = 1; d <= degree; d++)
			{
				double current_x = Math.pow(x, d);
				result = result.add(betas[d].multiply(new BigDecimal(current_x)));
			}
			return result;
		}
		else
		{
			throw new IllegalArgumentException("this is a multiple regression.");
		}
	}
	
	/**
	 * Predict method for multiple regressions
	 * 
	 * @param x : x values to be predicted
	 * @return	: BigDecimal
	 */
	public BigDecimal predict(ArrayList<Double> x)
	{
		//If is a multiple regression
		if (multiple)
		{
			int beta_no = 0;
			BigDecimal result = betas[beta_no];
			beta_no++;
			for (int d = 1; d <= degree; d++)
			{
				for (Double num : x)
				{
					double current_num = Math.pow(num, d);
					result = result.add(betas[beta_no].multiply(new BigDecimal(current_num)));
					beta_no++;
				}
			}
			return result;
		}
		else
		{
			throw new IllegalArgumentException("this is not a multiple regression.");
		}
	}
	
	//The getter method for the instance variable BigDecimal array "betas"
	public BigDecimal[] get_betas()
	{
		return betas;
	}
	
	/**
	 *  This is the toString method of the class Regression, returns the string
	 *  to be printed. 
	 *  
	 *  @return		: String
	 */
	public String toString()
	{
		//Print regression
		if (!multiple)
		{
			String str = "Y = " + betas[0] + "*X^" + 0;
			for (int arg = 1; arg < degree+1; arg++)
			{
				str += " + (" + betas[arg] + "*X^" + arg + ")";
			}		
			return str;
		}
		//Print multiple regression
		else
		{
			int beta_no = 0;
			String str = "Y = " + betas[beta_no] + "*X^" + 0;
			beta_no++;
			for (int arg = 1; arg < degree+1; arg++)
			{
				for (int xvar = 1; xvar <= (betas.length-1)/degree; xvar++)
				{
					str += " + (" + betas[beta_no] + "*X" + xvar + "^" + arg + ")";
					beta_no++;
				}
			}		
			return str;
		}
	}
	
}
