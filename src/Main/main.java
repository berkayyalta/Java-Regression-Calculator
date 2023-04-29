//By Berkay

package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import Regression.Regression;

//Class Main
public class main 
{

	//Main Method
	public static void main(String[] args) throws Exception 
	{
		//Initialize the JFrame
		JFrame frame = new JFrame("Regression Calculator");
		frame.setLocationRelativeTo(null);
		frame.setSize(600, 360);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Initialize the JPanel
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		//Title Label
		JLabel label_title = new JLabel("Enter Data");
		label_title.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 24));
		label_title.setBounds(15, 5, label_title.getPreferredSize().width+10, 30);
		
		//Label by Berkay
		JLabel by_berkay = new JLabel("by Berkay");
		by_berkay.setBounds(515, 15, 60, 20);
		by_berkay.setForeground(Color.BLUE);
		
		//Separator 1
		JSeparator sep1 = new JSeparator(SwingConstants.HORIZONTAL);
		sep1.setBackground(Color.GRAY);
		sep1.setBounds(10, 45, 570, 1);
		
		//JTextArea for X values
		JTextArea x_val_text_area = new JTextArea("Enter X Values\nFormat : x00, x01, x02; x10, x11, x12; x20, x21, x22 ...");
		x_val_text_area.setToolTipText("X Values");
		//JScrollPane for X values
		JScrollPane x_scrollBar = new JScrollPane(x_val_text_area,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		x_scrollBar.setBounds(15, 60, 345, 90);
		
		//TextArea for Y values
		JTextArea y_val_text_area = new JTextArea("Enter Y Values\nFormat : y0, y1, y2 ...");
		y_val_text_area.setToolTipText("Y Values");
		//JScrollPane for X values
		JScrollPane y_scrollBar = new JScrollPane(y_val_text_area,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		y_scrollBar.setBounds(15, 160, 345, 60);
		
		//TextArea for values to be predicted
		JTextArea pred_text_area = new JTextArea("X Values for Prediction\nFormat : x0, x1, x2 ...\nYou may leave empty");
		pred_text_area.setToolTipText("X Values for Prediction");
		//JScrollPane for X values
		JScrollPane pred_scrollBar = new JScrollPane(pred_text_area,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pred_scrollBar.setBounds(380, 60, 190, 65);
		
		//Spinner for the degree
		SpinnerNumberModel  spinner_model = new SpinnerNumberModel(1, 1, 8, 1);
		JSpinner spinner_degree = new JSpinner(spinner_model);
		spinner_degree.setToolTipText("Enter Degree");
		spinner_degree.setBounds(380, 133, 190, 20);
		
		//Spinner for the decimal_places
		SpinnerNumberModel spinner_model_dp = new SpinnerNumberModel(4, 1, 16, 1);
		JSpinner spinner_decimal_places = new JSpinner(spinner_model_dp);
		spinner_decimal_places.setToolTipText("Enter Decimal Places");
		spinner_decimal_places.setBounds(380, 163, 190, 20);
		
		//Separator 2
		JSeparator sep2 = new JSeparator(SwingConstants.HORIZONTAL);
		sep2.setBackground(Color.GRAY);
		sep2.setBounds(10, 230, 570, 10);
		
		//Title - Y
		JLabel y = new JLabel("Y = ");
		y.setFont(new Font("Serif", Font.BOLD, 24));
		y.setBounds(15, 240, y.getPreferredSize().width+10, 30);
		
		//Title - Prediction
		JLabel pred = new JLabel("Prediction : ");
		pred.setFont(new Font("Serif", Font.BOLD, 24));
		pred.setBounds(15, 275, pred.getPreferredSize().width+10, 30);
		
		//Calculate Button
		JButton calc_button = new JButton("Calculate");
		calc_button.setBounds(380, 193, 190, 25);
		calc_button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				boolean predict = true;
				//Check X and Y arrays
				ArrayList<double[]> x_values = getXvalues(frame, x_val_text_area);
				double[] y_values = getYvalues(frame, y_val_text_area);
				if (x_values == null || y_values == null || x_values.get(0).length != y_values.length)
				{
					JOptionPane.showMessageDialog(frame, "Invalid X or Y Value Input", "Error", JOptionPane.ERROR_MESSAGE);		
				}
				//Check prediction array
				ArrayList<Double> pred_values = getPredValues(frame, pred_text_area);
				if (pred_values == null || pred_values.size() != x_values.size())
				{
					predict = false;
				}
				try
				{
					//Calculate
					Regression regr = new Regression(x_values, y_values, (int) spinner_degree.getValue(), (int) spinner_decimal_places.getValue());
					//Display the result
					y.setText(regr.toString());
					y.setToolTipText(regr.toString());
					y.setSize(y.getPreferredSize().width+10, 30);
					//Set font size to fit
					if (y.getWidth() > 575)
					{
						while (y.getWidth() > 555)
						{
							int prev_font_size = y.getFont().getSize();
							y.setFont(new Font("Serif", Font.BOLD, prev_font_size-1));
							y.setSize(y.getPreferredSize().width+10, 30);
						}
					}
					else
					{
						while (y.getFont().getSize() < 24 && y.getWidth() < 555)
						{
							int prev_font_size = y.getFont().getSize();
							y.setFont(new Font("Serif", Font.BOLD, prev_font_size+1));
							y.setSize(y.getPreferredSize().width+10, 30);
						}
					}
					//Display the prediction
					if (predict)
					{
						pred.setText("Prediction : " + regr.predict(pred_values).toString());
						pred.setToolTipText(regr.predict(pred_values).toString());
						pred.setSize(pred.getPreferredSize().width+10, 30);
					}
					else
					{
						pred.setText("Prediction : No input for prediction");
						pred.setToolTipText("No input for prediction");
						pred.setSize(pred.getPreferredSize().width+10, 30);
					}	
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(frame, ex, "Exception", JOptionPane.ERROR_MESSAGE);		
				}			
			}
		});
		
		//Add the items
		panel.add(label_title);
		panel.add(by_berkay);
		panel.add(sep1);
		panel.add(x_scrollBar);
		panel.add(y_scrollBar);
		panel.add(pred_scrollBar);
		panel.add(spinner_degree);
		panel.add(spinner_decimal_places);
		panel.add(sep2);
		panel.add(y);
		panel.add(pred);
		panel.add(calc_button);
		frame.add(panel);
		frame.revalidate();	
	}
	
	/**
	 * Method to get the content of x_val_text_area as an array
	 * 
	 * @return : array
	 */
	private static ArrayList<double[]> getXvalues(JFrame frame, JTextArea x_val_text_area)
	{
		try
		{
			//Get the Text
			String input = x_val_text_area.getText().trim();
			//Generate the string array for x_value_arrays
			int num_semicolons = input.length() - input.replace(";", "").length();
			input = x_val_text_area.getText().trim();
			String[] x_value_arrays = new String[num_semicolons+1];
			//Fill the array "x_value_arrays"
			for (int index = 0; index < x_value_arrays.length; index++)
			{
				if (index == x_value_arrays.length-1)
				{
					x_value_arrays[index] = input;
				}
				else
				{
					x_value_arrays[index] = input.substring(0, input.indexOf(';'));
					input = input.substring(input.indexOf(';')+1);
				}		
			}
			//Check if the size of x_value_arrays are equal
			int x_value_array_num_commas = x_value_arrays[0].length() - x_value_arrays[0].replace(",", "").length();
			for (int index = 1; index < x_value_arrays.length; index++)
			{
				if (x_value_array_num_commas != x_value_arrays[index].length() - x_value_arrays[index].replace(",", "").length())
				{				
					return null;
				}
			}
			//Convert string x_value_arrays to double x_value_arrays
			int x_value_array_size = x_value_array_num_commas + 1;
			ArrayList<double[]> x_values = new ArrayList<double[]>();
			for (String current_x_value_array_ite : x_value_arrays)
			{
				String current_x_value_array = current_x_value_array_ite;
				//Generate the array
				double[] current_final_x_value_array = new double[x_value_array_size];
				//Fill the array
				for (int index = 0; index < current_final_x_value_array.length; index++)
				{
					if (index == current_final_x_value_array.length-1)
					{
						current_final_x_value_array[index] = Double.valueOf(current_x_value_array);
					}
					else
					{
						current_final_x_value_array[index] = Double.valueOf(current_x_value_array.substring(0, current_x_value_array.indexOf(',')));
						current_x_value_array = current_x_value_array.substring(current_x_value_array.indexOf(',')+1);
					}		
				}
				//Add to the ArrayList
				x_values.add(current_final_x_value_array);
			}
			//Return
			return x_values;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Method to get the content of y_val_text_area as an array
	 * 
	 * @return : array
	 */
	private static double[] getYvalues(JFrame frame, JTextArea y_val_text_area)
	{
		try
		{
			//Get the text
			String input = y_val_text_area.getText().trim();
			//Generate the array
			int num_commas = input.length() - input.replace(",", "").length();
			input = y_val_text_area.getText().trim();
			double[] y_values = new double[num_commas+1];
			//Fill the array
			for (int index = 0; index < y_values.length; index++)
			{
				if (index == y_values.length-1)
				{
					y_values[index] = Double.valueOf(input);
				}
				else
				{
					y_values[index] = Double.valueOf(input.substring(0, input.indexOf(',')));
					input = input.substring(input.indexOf(',')+1);
				}		
			}
			//Return
			return y_values;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Method to get the content of pred_text_area as an array
	 * 
	 * @return : array
	 */
	private static ArrayList<Double> getPredValues(JFrame frame, JTextArea pred_text_area)
	{
		try
		{
			//Get the text
			String input = pred_text_area.getText().trim();
			//Generate the array
			int num_commas = input.length() - input.replace(",", "").length();
			input = pred_text_area.getText().trim();
			ArrayList<Double> pred_values = new ArrayList<Double>();
			//Fill the array
			for (int index = 0; index < num_commas+1; index++)
			{
				if (index == num_commas)
				{
					pred_values.add(Double.valueOf(input));
				}
				else
				{
					pred_values.add(Double.valueOf(input.substring(0, input.indexOf(','))));
					input = input.substring(input.indexOf(',')+1);
				}		
			}
			//Return
			return pred_values;
		}
		catch (Exception e)
		{		
			return null;
		}
	}
	
}
