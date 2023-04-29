//By Berkay

package Math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

//Class Matrix
final public class Matrix 
{
	
	//Instance variables
	private BigDecimal [][] matrix;
	private int columns;
	private int rows;
	
	//The followings are the constructors for the class Matrix
	/**
	 *  This constructor takes a BigDecimal array as a parameter and 
	 *  initializes the instance variables "matrix", "rows" and "columns"
	 *  
	 *  @param matrix : 2D array that will be the matrix
	 */
	public Matrix(BigDecimal[][] matrix)
	{
		//If the array is empty
		if (matrix.length > 0 && matrix[0].length > 0)
		{
			//New 2D BigDecimal array
			BigDecimal[][] new_matrix = new BigDecimal[matrix.length][matrix[0].length];
			for (int row_no = 0; row_no < matrix.length; row_no++)
			{
				for (int column_no = 0; column_no < matrix[0].length; column_no++)
				{
					BigDecimal num = matrix[row_no][column_no];
					if (num != null)
					{
						new_matrix[row_no][column_no] = num;
					}
					else
					{
						throw new IllegalArgumentException("there is a null value in the array.");
					}
				}
			}
			//Initialize the variables
			this.matrix = new_matrix;
			this.rows = new_matrix.length;
			this.columns = new_matrix[0].length;
		}
		else
		{
			throw new IllegalArgumentException("empty array.");
		}
	}
	
	//Basic matrix operations
	/** 	
	 * 	The method addition_subtraction is the main method that will be used for 
	 * 	the methods addition and subtraction
	 * 
	 *	@param A 			: the first Matrix object
	 *	@param B 			: the second Matrix object
	 *	@param operation 	: "addition" or "subtraction"
	 *	@return 			: a new Matrix object
	 */
	private static Matrix addition_subtraction(Matrix A, Matrix B, String operation)
	{
		//Do if the Matrices are valid for Matrix addition
		if (A.get_rows() == B.get_rows() && A.get_columns() == B.get_columns())
		{
			BigDecimal[][] new_matrix = new BigDecimal[A.get_rows()][B.get_columns()];
			//Do for all the items in the Matrices
			for (int row_no = 0; row_no < A.get_rows(); row_no++)
			{
				for (int column_no = 0; column_no < B.get_columns(); column_no++)
				{
					//Get both numbers from the Matrices
					BigDecimal num1 = A.get(row_no, column_no);
					BigDecimal num2 = B.get(row_no, column_no);
					//Addition
					if (operation.equals("addition"))
					{
						new_matrix[row_no][column_no] = num1.add(num2);
					}
					//Subtraction
					else if (operation.equals("subtraction"))
					{
						new_matrix[row_no][column_no] = num1.subtract(num2);
					}
				}
			}
			//Return the new Matrix
			return new Matrix(new_matrix);
		}
		//Otherwise, throw exception
		else
		{
			throw new IllegalArgumentException("invalid matrices.");
		}
	}
	
	//Addition of two given Matrices, returns a new Matrix
	public static Matrix addition(Matrix A, Matrix B)
	{
		//Use the method addition_subtraction and return the Matrix it returned
		return addition_subtraction(A, B, "addition");
	}
	
	//Subtraction of two given Matrices, returns a new Matrix
	public static Matrix subtraction(Matrix A, Matrix B)
	{
		//Use the method addition_subtraction and return the Matrix it returned
		return addition_subtraction(A, B, "subtraction");
	}
	
	/**
	 *  This method multiplies every items in the given Matrix A by the 
	 *  given scalar
	 *  
	 *  @param A 		: the Matrix
	 *  @param scalar 	: the number that the items in Matrix A will be multiplied by
	 *  @return 		: a new Matrix object
	 */
	public static Matrix scalar_multiplication(Matrix A, BigDecimal scalar, boolean divide)
	{
		if (!scalar.equals(BigDecimal.ZERO) || !divide)
		{
			BigDecimal[][] new_matrix = new BigDecimal[A.get_rows()][A.get_columns()];
			//Do for all the items in the Matrix
			for (int row_no = 0; row_no < A.get_rows(); row_no++)
			{			
				for (int column_no = 0; column_no < A.get_columns(); column_no++)
				{
					//Multiplication
					BigDecimal fraction = A.get(row_no, column_no);
					if (divide)
					{
						new_matrix[row_no][column_no] = fraction.divide(scalar, 16, RoundingMode.HALF_UP);	
					}
					else
					{
						new_matrix[row_no][column_no] = fraction.multiply(scalar);	
					}						
				}
			}
			//Return the new Matrix
			return new Matrix(new_matrix);
		}
		else
		{
			throw new IllegalArgumentException("the scalar cannot be 0 in division.");
		}
	}
	
	//Scalar multiplication with a parameter type of double
	public static Matrix scalar_multiplication(Matrix A, double scalar, boolean divide)
	{
		//Return
		return scalar_multiplication(A, new BigDecimal(scalar), divide);
	}
	
	/**
	 *  This method multiplies the given Matrices and return a new Matrix
	 *  as the result of the Matrix multiplication
	 *  
	 *  @param A 	: first Matrix
	 *  @param B 	: second Matrix
	 *  @return 	: a new Matrix object
	 */
	public static Matrix matrix_multiplication(Matrix A, Matrix B)
	{
		//Do if the Matrices are valid for Matrix multiplication
		if (A.columns == B.rows)
		{
			BigDecimal[][] new_matrix = new BigDecimal[A.rows][B.columns];
			//Do for all the items in the Matrices
			for (int A_row_no = 0; A_row_no < A.rows; A_row_no++)
			{
				for (int B_column_no = 0; B_column_no < B.columns; B_column_no++)
				{
					BigDecimal new_item = new BigDecimal(0);
					for (int item_no = 0; item_no < A.columns; item_no++)
					{
						//Get both numbers from the Matrices
						BigDecimal num1 = A.get(A_row_no, item_no);
						BigDecimal num2 = B.get(item_no, B_column_no);
						//Do the operation
						new_item = new_item.add(num1.multiply(num2));
					}
					new_matrix[A_row_no][B_column_no] = new_item;
				}
			}
			//Return the new Matrix
			return new Matrix(new_matrix);
		}	
		//Otherwise, throw exception
		else 		
		{
			throw new IllegalArgumentException("invalid matrices.");
		}
	}

	/**
	 *  The method inverse returns the inverse of the Matrix, the Matrix
	 *  must be a square to have an inverse in this program
	 * 
	 *  @return : Matrix
	 */
	public Matrix inverse()
	{
		//Inverse of a square Matrix
		if (rows == columns)
		{
			Matrix adj_matrix = adjoint();
			//Return the new Matrix
			return scalar_multiplication(adj_matrix, determinant(), true);
		}
		//If the Matrix is not square
		else
		{
			throw new IllegalArgumentException("this matrix has no inverse.");
		}
	}
	
	/**
	 *  The method adjoint returns the adjoint of the Matrix, the Matrix
	 *  must be a square to have an adj in this program
	 *  
	 *  @return : Matrix
	 */
	public Matrix adjoint()
	{
		//Do if the Matrix is square
		if (rows == columns)
		{
			BigDecimal[][] new_matrix = new BigDecimal[rows][columns];
			//Fill the new Matrix that will be the adjoint Matrix
			for (int row_no = 0; row_no < rows; row_no++)
			{
				for (int column_no = 0; column_no < columns; column_no++)
				{
					new_matrix[row_no][column_no] = cofactor(row_no, column_no);
				}
			}
			//Return the new Matrix
			return new Matrix(new_matrix).transpose();
		}
		//Otherwise, throw exception
		else
		{
			throw new IllegalArgumentException("this matrix has no adjoint matrix.");
		}
	}
	
	/**
	 *  The method transpose returns the transpose of the Matrix
	 *  
	 *  @return : Matrix
	 */
	public Matrix transpose()
	{
		BigDecimal[][] new_matrix = new BigDecimal[columns][rows];
		//Transpose of the Matrix
		for (int A_row_no = 0; A_row_no < rows; A_row_no++)
		{				
			for (int A_column_no = 0; A_column_no < columns; A_column_no++)
			{
				new_matrix[A_column_no][A_row_no] = matrix[A_row_no][A_column_no];
			}
		}
		//Return the new Matrix
		return new Matrix(new_matrix);
	}
	
	/**
	 *  The method sub_matrix returns the sub_matrix of the Matrix at 
	 *  the row RN and column CN
	 *  
	 *  @param RN	: row no
	 *  @param CN	: column no
	 *  @return 	: Matrix
	 */
	public Matrix sub_matrix(int RN, int CN)
	{			
		//If the Matrix is valid to have a sub Matrix
		if (rows == columns && RN >= 0 && RN < rows && CN >= 0 && CN < columns)
		{
			if (rows == 1 && columns == 1)
			{
				BigDecimal[][] m = {{BigDecimal.ONE}};
				return new Matrix(m);
			}
			else
			{
				ArrayList<ArrayList<BigDecimal>> AL_F = M2A.toArrayList(matrix);
				//Remove the row and the column by index
				AL_F.remove(RN);
				for (int remove_column = 0; remove_column < AL_F.size(); remove_column++)
				{
					AL_F.get(remove_column).remove(CN);
				}
				//Return 
				return new Matrix(M2A.toArray(AL_F));
			}
		}
		//Otherwise, throw exception
		else
		{
			throw new IllegalArgumentException("invalid matrix or parameters.");
		}
	}
	
	/**
	 *  The method determinant returns the determinant of the Matrix
	 *  
	 *  @return : BigDecimal
	 */
	public BigDecimal determinant()
	{
		//Do if the Matrix is square
		if (rows == columns)
		{
			//If the Matrix is 1x1
			if (rows == 1 && columns == 1)
			{
				//Return the inverse of an 1x1 Matrix
				return matrix[0][0];
			}
			//Otherwise
			else 
			{
				BigDecimal DET = new BigDecimal(0);
				//Calculates for the first row of the matrix
				for (int column_no = 0; column_no < columns; column_no++)
				{
					DET = DET.add(matrix[0][column_no].multiply(cofactor(0, column_no)));
				}
				//Return the inverse of the Matrix
				return DET;
			}
		}
		//Otherwise, throw exception
		else 
		{
			throw new IllegalArgumentException("this is not a square matrix.");
		}
	}
	
	/**
	 *  The method cofactor returns the cofactor of the Matrix at the
	 *  row RN and column CL
	 *  
	 *  @param RN 	: row no
	 *  @param CN 	: column no
	 *  @return		: Matrix
	 */
	public BigDecimal cofactor(int RN, int CN)
	{
		//If the Matrix is valid
		if (rows == columns)
		{
			//Cofactor
			BigDecimal cofactor = new BigDecimal(Math.pow(-1, RN+CN)).multiply(sub_matrix(RN, CN).determinant());
			//Return
			return cofactor;
		}
		//Otherwise, throw exception
		else
		{
			throw new IllegalArgumentException("invalid matrix.");
		}
	}
	
	//Getter and Setter methods of the class Matrix
	/** 
	 *  The method get returns the item of the Matrix at the position
	 *  of RN, CN
	 *  
	 *  @param RN 	: row no
	 *  @param CN 	: column no
	 *  @return		: BigDecimal
	 */
	public BigDecimal get(int RN, int CN)
	{
		//Try to return the BigDecimal at the position row,column
		try
		{
			//Return
			return matrix[RN][CN];
		}
		//Throw exception if any of the given indices is invalid
		catch (Exception E)
		{
			throw new IllegalArgumentException("invalid indices.");
		}
	}
	
	//Getter method for the instance variable int rows
	public int get_rows()
	{
		//Return
		return rows;
	}
	
	//Getter method for the instance variable int columns
	public int get_columns()
	{
		//Return
		return columns;
	}
	
	//Getter method for the instance variable matrix
	public BigDecimal[][] get_matrix()
	{
		//Return
		return matrix;
	}
	
	/**
	 *  This is the toString method of the class Matrix, returns the string
	 *  to be printed. Format :" | r0c0, r0c1, r0c2... | \n |r1c0, r1c1, r1c2... | "
	 *  
	 *  @return		: String
	 */
	public String toString()
	{
		//The longest number
		int len = 0;
		for (BigDecimal[] row : this.matrix)
		{
			for (BigDecimal num : row)
			{
				if (num.toString().length() > len)
				{
					len = num.toString().length();
				}
			}
		}
		//The string to be returned
		String matrix_toString = "";
		//Iterate over rows
		for (int row_no = 0; row_no < rows; row_no++)
		{
			String row_toString = "|";
			//Iterate over columns
			for (int column_no = 0; column_no < columns; column_no++)
			{
				//Add item to the string
				row_toString += " " + matrix[row_no][column_no];
				for (int i = 0; i < len-(matrix[row_no][column_no].toString().length()); i++)
				{
					row_toString += " ";
				}
				row_toString += " ";
			}
			matrix_toString += row_toString + "|\n";
		}
		//Return the string
		return matrix_toString;
	}
	
}
