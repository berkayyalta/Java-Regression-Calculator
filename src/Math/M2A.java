//By Berkay

package Math;

import java.math.BigDecimal;
import java.util.ArrayList;

//Class M2
public class M2A
{
	
	/**
	 *  The method add_column adds the given column to the Matrix
	 *  
	 *  @param 	: BigDecimal[] column, the column that will be added
	 *  @return	: Matrix
	 */
	public static Matrix add_column(Matrix M, BigDecimal[] column)
	{
		//Get the variables
		BigDecimal[][] matrix = M.get_matrix();
		int rows = M.get_rows();
		int columns = M.get_columns();
		//If the given column is valid to be added
		if (column.length == rows)
		{
			//Prepare the new Matrix that the column will be added to
			BigDecimal[][] new_matrix = new BigDecimal[rows][columns+1];
			//Iterate over rows
			for (int row_no = 0; row_no < rows; row_no++)
			{
				//Iterate over columns
				for (int column_no = 0; column_no < columns; column_no++)
				{
					//Copy the current item from existing Matrix to new Matrix
					new_matrix[row_no][column_no] = matrix[row_no][column_no];
				}
			}
			//Add the column
			for (int row_no = 0; row_no < rows; row_no++)
			{
				new_matrix[row_no][columns] = column[row_no];
			}
			//Return
			return new Matrix(new_matrix);
		}
		//Otherwise, throw exception
		else
		{
			throw new IllegalArgumentException("invalid input.");
		}
	}
	
	//Methods to convert array to ArrayList and to convert ArrayList to array
	/**
	 *  The method toArrayList converts the given 2D BigDecimal ArrayList to 2D
	 *  BigDecimal array and returns it
	 *  
	 *  @param AL	: ArrayList<ArrayList<BigDecimal>>, 2D BigDecimal ArrayList that will be converted
	 *  @return		: BigDecimal[][]
	 */
	public static BigDecimal[][] toArray(ArrayList<ArrayList<BigDecimal>> AL)
	{
		//2D BigDecimal ArrayList to 1D BigDecimal ArrayList
		ArrayList<BigDecimal[]> pre_array = new ArrayList<BigDecimal[]>();
		//Iterate over rows
		for (int ATAL1 = 0; ATAL1 < AL.size(); ATAL1++)
		{
			BigDecimal[] sub_array = new BigDecimal[AL.get(ATAL1).size()];
			//Iterate over columns
			for (int ATAL2 = 0; ATAL2 < AL.get(ATAL1).size(); ATAL2++)
			{
				//Add the current item to 1D ArrayList
				sub_array[ATAL2] = AL.get(ATAL1).get(ATAL2);
			}
			pre_array.add(sub_array);
		}
		//1D BigDecimal ArrayList to 2D BigDecimal array
		BigDecimal[][] array = new BigDecimal[AL.size()][AL.get(0).size()];
		//Iterate over rows
		for (int ATAL3 = 0; ATAL3 < AL.size(); ATAL3++)
		{
			//Add the current item to 2D array
			array[ATAL3] = pre_array.get(ATAL3);
		}
		//Return the array
		return array;
	}
	
	/**
	 *  The method toArrayList converts the given 2D BigDecimal array to 2D
	 *  BigDecimal ArrayList and returns it
	 *  
	 *  @param A	: BigDecimal[][], 2D BigDecimal array that will be converted
	 *  @return		: ArrayList<ArrayList<BigDecimal>>
	 */
	public static ArrayList<ArrayList<BigDecimal>> toArrayList(BigDecimal[][] A)
	{
		//Initialize the variables
		int rows = A.length;
		int columns = A[0].length;
		//2D BigDecimal array to 1D BigDecimal ArrayList
		ArrayList<BigDecimal[]> pre_matrix = new ArrayList<BigDecimal[]>();
		//Iterate over rows
		for (int ALTA1 = 0; ALTA1 < rows; ALTA1++) 
		{
			BigDecimal[] sub_array= new BigDecimal[columns];
			//Iterate over columns
			for (int ALTA2 = 0; ALTA2 < columns; ALTA2++)
			{
				//Add the current item to the 1D ArrayList
				sub_array[ALTA2] = A[ALTA1][ALTA2];
			}
			pre_matrix.add(sub_array);
		}
		//1D BigDecimal ArrayList to 2D BigDecimal array
		ArrayList<ArrayList<BigDecimal>> matrix = new ArrayList<ArrayList<BigDecimal>>();
		//Iterate over rows
		for (int ALTA3 = 0; ALTA3 < rows; ALTA3++) 
		{
			ArrayList<BigDecimal> sub_array = new ArrayList<BigDecimal>();
			//Iterate over columns
			for (int ALTA4 = 0; ALTA4 < columns; ALTA4++)
			{
				//Add the current item to the 2D ArrayList
				sub_array.add(pre_matrix.get(ALTA3)[ALTA4]);
			}
			matrix.add(sub_array);
		}
		//Return the ArrayList
		return matrix;
	}
	
}
