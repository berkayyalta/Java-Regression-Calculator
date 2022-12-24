package Math;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Fraction {

	//Instance variables
	private BigInteger numerator;
	private BigInteger denominator;
	
	//The followings are the constructors for the class Fraction
	/**
	 * 	This constructor sets the numerator as the parameter number and
	 *  the denominator as 1
	 *  
	 *  @param number : the numerator of the Fraction
	 */
	public Fraction(int number)
	{
		//Initialize the numerator and denominator
		this.numerator = BigInteger.valueOf(number);
		this.denominator = BigInteger.ONE;
	}
	
	/**
	 *  This constructor sets the numerator and denominator in format of an
	 *  integer divided by a power of 10
	 *  
	 *  @param number : the number that will be used for the numerator and denominator
	 */
	public Fraction(Double number)
	{	
		//Convert number to string and initialize the string decimal_part
		String number_string = String.valueOf(number);		
		String decimal_part = number_string.substring(number_string.indexOf('.')+1);	
		//Initialize the numerator
		String numerator = number_string.replace(".","");
		this.numerator = new BigInteger(numerator);	
		//Initialize the denominator
		String denominator = String.valueOf( (int) Math.pow(10, decimal_part.length()));
		this.denominator = new BigInteger(denominator);
	}	
	
	/**
	 *  This constructor sets the numerator and denominator by the given two BigInteger
	 *  objects
	 *  
	 *  @param numerator 	: the numerator of the fraction
	 *  @param denominator 	: the denominator of the fraction
	 */
	public Fraction(BigInteger numerator, BigInteger denominator)
	{
		//Do if the denominator is not 0
		if (!denominator.equals(BigInteger.ZERO))
		{
			//Initialize numerator and denominator
			this.numerator = numerator;
			this.denominator = denominator;
		}
		else
		{
			throw new IllegalArgumentException("denominator cannot be 0.");
		}
	}
	
	//The following 3 methods are for addition and subtraction operations 
	/** 	
	 * 	The method addition_subtraction is the main method that will be used for 
	 * 	the methods addition and subtraction
	 * 
	 *	@param f1 			: the first Fraction object
	 *	@param f2 			: the second Fraction object
	 *	@param operation 	: "addition" or "subtraction"
	 *	@return 			: a new Fraction object
	 */
	private static Fraction addition_subtraction(Fraction f1, Fraction f2, String operation)
	{
		//New fractions
		Fraction fraction1 = new Fraction(f1.getNumerator(), f1.getDenominator());
		Fraction fraction2 = new Fraction(f2.getNumerator(), f2.getDenominator());
		//Expand the fractions
		fraction1.expand(fraction2.getDenominator());
		fraction2.expand(fraction1.getDenominator().divide(fraction2.getDenominator()));		
		//New numerator and denominator
		BigInteger numerator = null;
		if (operation.equals("addition"))
		{
			numerator = fraction1.getNumerator().add(fraction2.getNumerator());
		}
		else if (operation.equals("subtraction"))
		{
			numerator = fraction1.getNumerator().subtract(fraction2.getNumerator());
		}
		BigInteger denominator = fraction1.getDenominator();
		//Simplify and return the new fraction
		Fraction result = new Fraction(numerator, denominator);
		while (true)
		{
			boolean simplified = result.simplify();
			if (simplified == false)
			{
				break;
			}
		}
		return result;
	}
	
	//Addition of two given fractions, returns a new fraction
	public static Fraction addition(Fraction f1, Fraction f2)
	{
		//Use the method addition_subtraction and return the Fraction it returned
		return addition_subtraction(f1, f2, "addition");
	}
	
	//Subtraction of two given fractions, returns a new fraction
	public static Fraction subtraction(Fraction f1, Fraction f2)
	{
		//Use the method addition_subtraction and return the Fraction it returned
		return addition_subtraction(f1, f2, "subtraction");
	}
	
	//The following 3 methods are for multiplication and division operations 
	/** 	
	 * 	The method multiplication_division is the main method that will be used for 
	 * 	the methods multiplication and division
	 * 
	 *	@param f1 			: the first Fraction object
	 *	@param f2 			: the second Fraction object
	 *	@param operation 	: "multiplication" or "division"
	 *	@return 			: a new Fraction object
	 */
	private static Fraction multiplication_division(Fraction f1, Fraction f2, String operation)
	{
		//New numerator and denominator
		BigInteger numerator = null;
		BigInteger denominator = null;
		if (operation.equals("multiplication"))
		{
			numerator = f1.getNumerator().multiply(f2.getNumerator());
			denominator = f1.getDenominator().multiply(f2.getDenominator());
		}
		else if (operation.equals("division"))
		{
			numerator = f1.getNumerator().multiply(f2.getDenominator());
			denominator = f1.getDenominator().multiply(f2.getNumerator());
		}
		//Simplify and return the new fraction
		Fraction result = new Fraction(numerator, denominator);
		while (true)
		{
			boolean simplified = result.simplify();
			if (simplified == false)
			{
				break;
			}
		}
		return result;
	}
	
	//Multiplication of two given fractions, returns a new fraction
	public static Fraction multiplication(Fraction f1, Fraction f2)
	{
		//Use the method multiplication_division and return the Fraction it returned
		return multiplication_division(f1, f2, "multiplication");
	}
	
	//Division of two given fractions, returns a new fraction
	public static Fraction division(Fraction f1, Fraction f2)
	{
		//Use the method multiplication_division and return the Fraction it returned
		return multiplication_division(f1, f2, "division");
	}
	
	/**
	 *  The method expand expands the Fraction : multiplies both numerator
	 *  and denominator by the given constant
	 *  
	 *  @param constant : the number that the numerator and denominator will be 
	 *  multiplied by
	 */
	public void expand(BigInteger constant)
	{
		//Do if constant is not 0
		if (!constant.equals(BigInteger.ZERO)) 
		{
			//Multiply the numerator and the denominator by the BigInteger constant
			this.numerator = this.numerator.multiply(constant);
			this.denominator = this.denominator.multiply(constant);
		}
		else
		{
			throw new IllegalArgumentException("Fraction cannot be expanded by 0.");
		}
	}
	
	/**
	 *  The method simplifies the Fraction by the first common factor of
	 *  the numerator and denominator, returns true if the Fraction is 
	 *  simplified and false if there aren't common Factors so the Fraction
	 *  cannot be simplified more
	 *  
	 *  @return : boolean
	 */
	public boolean simplify()
	{
		boolean simplified = false;	
		//Iteration to find the first common factor
		for (int num = 2; num <= this.numerator.intValue(); num++)
		{	
			//Factor of numerator
			BigInteger div_res_num = this.numerator.divide(BigInteger.valueOf(num));	
			boolean factor_of_numerator = this.numerator.equals(div_res_num.multiply(BigInteger.valueOf(num)));			
			//Factor of denominator
			BigInteger div_res_denom = this.denominator.divide(BigInteger.valueOf(num));
			boolean factor_of_denominator = this.denominator.equals(div_res_denom.multiply(BigInteger.valueOf(num)));
			//Simplify
			if (factor_of_numerator && factor_of_denominator)
			{
				//Simplify
				this.numerator = div_res_num;
				this.denominator = div_res_denom;
				//Break
				simplified = true;
				break;
			}
		}
		//Return
		return simplified;
	}
	
	/**
	 *  This method changes the Fraction as the Fraction to 
	 *  the power of the parameter pow
	 *  
	 *  @param pow : pow
	 */
	public void pow(int pow)
	{
		//Pow numerator 
		BigInteger numerator = this.numerator;
		for (int i = 1; i < pow; i++)
		{
			numerator = numerator.multiply(this.numerator);
		}		
		this.numerator = numerator;
		//Pow denominator
		BigInteger denominator = this.denominator;
		for (int i = 1; i < pow; i++)
		{
			denominator = denominator.multiply(this.denominator);
		}	
		this.denominator = denominator;
	}
	
	/**
	 *  Inverse of the function
	 *  Numerator = denominator, denominator = numerator
	 */
	public void inverse()
	{	
		//Inverse
		BigInteger numerator = this.numerator;
		this.numerator = this.denominator;
		this.denominator = numerator;
	}
	
	//Getter method for the instance variable BigInteger numerator
	public BigInteger getNumerator()
	{
		//Return
		return this.numerator;
	}
	
	//Getter method for the instance variable BigInteger denominator
	public BigInteger getDenominator()
	{
		//Return
		return this.denominator;
	}
	
	/**
	 *  This method does the division first then returns the result instead of
	 *  directly returning the fraction.
	 *  
	 *  WARNING: THIS METHOD MAY NOT RETURN THE EXCAT VALUE DUE TO
	 *  THE ROUNDING
	 *  
	 *  @return 	: string
	 */
	public String divide()
	{
		//Convert numerator and denominator to BigDecimal
		BigDecimal numerator = new BigDecimal(this.numerator);
		BigDecimal denominator = new BigDecimal(this.denominator);
		//Divide
		BigDecimal result = null;
		try 
		{
			result = numerator.divide(denominator);
		}
		catch (Exception e)
		{
			result = numerator.divide(denominator, RoundingMode.HALF_UP);
		}
		//Return the result as a string
		String str = String.valueOf(result);
		return "~" + str;
	}
	
	/**
	 *  This is the toString method of the class Fraction, returns the string
	 *  to be printed. Format :" ( this.numerator / this.denominator ) "
	 *  
	 *  @return		: String
	 */
	public String toString()
	{
		//The string to be returned
		String str = "( " + this.numerator + " / " + this.denominator + " )";
		//Return the string
		return str;
	}
	
}
