package Math;

import java.util.ArrayList;
import java.util.Arrays;

final public class Matrix 
{
	
	//row: getter
	//get
	
	//Instance variables
	private Double [][] matrix;
	private int columns;
	private int rows;
	
	//Constructors	
	public Matrix(Double[][] matrix)
	{
		this.matrix = matrix;
		this.rows = matrix.length;
		this.columns = matrix[0].length;
	}
	
	public Matrix(int[][] data)
	{
		Double[][] matrix = new Double[data.length][data[0].length];
		for (int row_no = 0; row_no < data.length; row_no++)
		{
			for (int column_no = 0; column_no < data[0].length; column_no++)
			{
				matrix[row_no][column_no] = (double) data[row_no][column_no];
			}
		}
		this.matrix = matrix;
		this.rows = matrix.length;
		this.columns = matrix[0].length;
	}
	
	
	//Matrix operations
	public static Matrix addition(Matrix A, Matrix B)
	{
		if (A.get_rows() == B.get_rows() && A.get_columns() == B.get_columns())
		{
			Double[][] new_matrix = new Double[A.get_rows()][B.get_columns()];
			for (int row_no = 0; row_no < A.get_rows(); row_no++)
			{
				for (int column_no = 0; column_no < B.get_columns(); column_no++)
				{
					new_matrix[row_no][column_no] = A.get(row_no, column_no) + B.get(row_no, column_no);
				}
			}
			return new Matrix(new_matrix);
		}
		else
		{
			throw new IllegalArgumentException("invalid matrices.");
		}
	}
	
	public static Matrix subtraction(Matrix A, Matrix B)
	{
		if (A.get_rows() == B.get_rows() && A.get_columns() == B.get_columns())
		{
			Double[][] new_matrix = new Double[A.get_rows()][B.get_columns()];
			for (int row_no = 0; row_no < A.get_rows(); row_no++)
			{
				for (int column_no = 0; column_no < B.get_columns(); column_no++)
				{
					new_matrix[row_no][column_no] = A.get(row_no, column_no) - B.get(row_no, column_no);
				}
			}
			return new Matrix(new_matrix);
		}
		else
		{
			throw new IllegalArgumentException("invalid matrices.");
		}
	}
	
	public static Matrix scalar_multiplication(Matrix A, Double scalar)
	{
		Double[][] new_matrix = new Double[A.get_rows()][A.get_columns()];
		for (int row_no = 0; row_no < A.get_rows(); row_no++)
		{			
			for (int column_no = 0; column_no < A.get_columns(); column_no++)
			{
				new_matrix[row_no][column_no] = A.get(row_no, column_no) * scalar;				}
			}
		return new Matrix(new_matrix);
	}
	
	public static Matrix matrix_multiplication(Matrix A, Matrix B)
	{
		if (A.columns == B.rows)
		{
			Double[][] new_matrix = new Double[A.rows][B.columns];
			for (int A_row_no = 0; A_row_no < A.rows; A_row_no++)
			{
				for (int B_column_no = 0; B_column_no < B.columns; B_column_no++)
				{
					Double new_item = 0.0;
					for (int item_no = 0; item_no < A.columns; item_no++)
					{
						new_item = new_item + (A.get(A_row_no, item_no) * B.get(item_no, B_column_no));
					}
					new_matrix[A_row_no][B_column_no] = new_item;
				}
			}
			return new Matrix(new_matrix);
		}	
		else 		
		{
			throw new IllegalArgumentException("invalid matrices.");
		}
	}

	
	//Inverse, transpose
	public Matrix inverse()
	{
		if (rows == 2 && columns == 2)
		{
			Double[][] new_matrix = new Double[rows][columns];	
			new_matrix[0][0] = matrix[1][1] * Math.pow(determinant(), -1);
			new_matrix[0][1] = matrix[0][1] * (-1 * Math.pow(determinant(), -1));
			new_matrix[1][0] = matrix[1][0] * (-1 * Math.pow(determinant(), -1));		
			new_matrix[1][1] = matrix[0][0] * Math.pow(determinant(), -1);
			return new Matrix(new_matrix);
		}
		else if (is_square())
		{
			Matrix adj_matrix = adjoint();
			return scalar_multiplication(adj_matrix, Math.pow(determinant(), -1));
		}
		else
		{
			throw new IllegalArgumentException("this matrix has no inverse.");
		}
	}
	
	public Matrix adjoint()
	{
		if (is_square())
		{
			Double[][] new_matrix = new Double[rows][columns];
			for (int row_no = 0; row_no < rows; row_no++)
			{
				for (int column_no = 0; column_no < columns; column_no++)
				{
					new_matrix[row_no][column_no] = cofactor(row_no, column_no);
				}
			}
			return new Matrix(new_matrix).transpose();
		}
		else
		{
			throw new IllegalArgumentException("this matrix has no adjoint matrix.");
		}
	}
	
	public Matrix transpose()
	{
		Double[][] new_matrix = new Double[columns][rows];
		for (int A_row_no = 0; A_row_no < rows; A_row_no++)
		{				
			for (int A_column_no = 0; A_column_no < columns; A_column_no++)
			{
				new_matrix[A_column_no][A_row_no] = matrix[A_row_no][A_column_no];
			}
		}
		return new Matrix(new_matrix);
	}
	
	public Matrix sub_matrix(int RN, int CN)
	{			
		if (is_square() && RN >= 0 && RN < rows && CN >= 0 && CN < columns)
		{
			ArrayList<ArrayList<Double>> AL_F = toArrayList(matrix);
			//Remove the row and the column by index
			AL_F.remove(RN);
			for (int remove_column = 0; remove_column < AL_F.size(); remove_column++)
			{
				AL_F.get(remove_column).remove(CN);
			}
			//Return 
			return new Matrix(toArray(AL_F));
		}
		else
		{
			throw new IllegalArgumentException("invalid matrix or parameters.");
		}
	}
	

	//Determinant
	public Double determinant()
	{
		if (is_square())
		{
			if (rows == 2 && columns == 2)
			{
				return (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
			}
			else 
			{
				Double DET = 0.0;
				//Calculates for the first row of the matrix
				for (int column_no = 0; column_no < columns; column_no++)
				{
					DET += (matrix[0][column_no] * (cofactor(0, column_no)));
				}
				return DET;
			}
		}
		else 
		{
			throw new IllegalArgumentException("this is not a square matrix.");
		}
	}
	
	public Double cofactor(int row, int column)
	{
		if (is_not_empty() && is_square())
		{
			Double cofactor = Math.pow(-1, row+column) * sub_matrix(row, column).determinant();
			return cofactor;
		}
		else
		{
			throw new IllegalArgumentException("invalid matrix.");
		}
	}
	
	
	//Getters, Setters
	public Double get(int row, int column)
	{
		try
		{
			return matrix[row][column];
		}
		catch (Exception E)
		{
			throw new IllegalArgumentException("invalid indices.");
		}
	}
	
	public int get_rows()
	{
		return rows;
	}
	
	public int get_columns()
	{
		return columns;
	}
	
	
	//Is Valid
	public boolean is_square()
	{
		return rows == columns;
	}
	
	public boolean is_not_empty()
	{
		return rows > 0 && columns > 0;
	}
	
	
	//Columns
	public Matrix add_column(Double[] column)
	{
		if (column.length == rows || rows == 0)
		{
			Double[][] new_matrix = new Double[rows][columns+1];
			for (int row_no = 0; row_no < rows; row_no++)
			{
				for (int column_no = 0; column_no < columns; column_no++)
				{
					new_matrix[row_no][column_no] = matrix[row_no][column_no];
				}
			}
			for (int row_no = 0; row_no < rows; row_no++)
			{
				new_matrix[row_no][columns] = column[row_no];
			}
			return new Matrix(new_matrix);
		}
		else
		{
			throw new IllegalArgumentException("invalid input.");
		}
	}
	
	public Matrix add_column(Double num, int row_num)
	{
		Double[] column = new Double[row_num];
		for (int item_no = 0; item_no < row_num; item_no++)
		{
			column[item_no] = num;
		}
		return add_column(column);
	}
	
	public Matrix add_column(Double num)
	{
		return add_column(num, rows);
	}
	

	//toArray, toArrayList
	public Double[][] toArray(ArrayList<ArrayList<Double>> AL)
	{
		ArrayList<Double[]> pre_array = new ArrayList<Double[]>();
		for (int ATAL1 = 0; ATAL1 < AL.size(); ATAL1++)
		{
			Double[] sub_array = new Double[AL.get(ATAL1).size()];
			for (int ATAL2 = 0; ATAL2 < AL.get(ATAL1).size(); ATAL2++)
			{
				sub_array[ATAL2] = AL.get(ATAL1).get(ATAL2);
			}
			pre_array.add(sub_array);
		}
		Double[][] array = new Double[AL.size()][AL.get(0).size()];
		for (int ATAL3 = 0; ATAL3 < AL.size(); ATAL3++)
		{
			array[ATAL3] = pre_array.get(ATAL3);
		}
		return array;
	}
	
	public ArrayList<ArrayList<Double>> toArrayList(Double[][] A)
	{
		ArrayList<Double[]> pre_matrix = new ArrayList<Double[]>();
		for (int ALTA1 = 0; ALTA1 < rows; ALTA1++) 
		{
			Double[] sub_array= new Double[columns];
			for (int ALTA2 = 0; ALTA2 < columns; ALTA2++)
			{
				sub_array[ALTA2] = A[ALTA1][ALTA2];
			}
			pre_matrix.add(sub_array);
		}
		ArrayList<ArrayList<Double>> matrix = new ArrayList<ArrayList<Double>>();
		for (int ALTA3 = 0; ALTA3 < rows; ALTA3++) 
		{
			ArrayList<Double> sub_array = new ArrayList<Double>();
			for (int ALTA4 = 0; ALTA4 < columns; ALTA4++)
			{
				sub_array.add(pre_matrix.get(ALTA3)[ALTA4]);
			}
			matrix.add(sub_array);
		}
		return matrix;
	}
	
	
	//toString
	public String toString()
	{
		String matrix_toString = "";
		for (int row_no = 0; row_no < rows; row_no++)
		{
			String row_toString = "|";
			for (int column_no = 0; column_no < columns; column_no++)
			{
				row_toString += " " + matrix[row_no][column_no];
			}
			matrix_toString += row_toString + "|\n";
		}
		return matrix_toString;
	}
	
	
}
