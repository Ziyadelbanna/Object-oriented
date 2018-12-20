package eg.edu.alexu.csd.oop.calculator.cs27;

import java.awt.EventQueue;

import javax.swing.JFrame;

import eg.edu.alexu.csd.oop.calculator.Calculator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.JLabel;

public class CalculatorApplication extends JFrame {

	private JFrame frame;
	private JTextField textField;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JButton btnNewButton_5;
	private JButton btnNewButton_6;
	private JButton btnNewButton_7;
	private JButton btnNewButton_8;
	private JButton btnNewButton_9;
	private JButton btnNewButton_19;
	private String result;
	private boolean save = false;
	private boolean prev = false;
	private boolean next = false;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalculatorApplication window = new CalculatorApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CalculatorApplication() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {

		Calculator calc = new CalculatorUnit();

		frame = new JFrame();
		frame.setBounds(100, 100, 545, 425);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		textField.setBounds(12, 11, 284, 75);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		
		JButton btnNewButton = new JButton("1");
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setFont(new Font("Bernard MT Condensed", Font.BOLD, 22));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String string;
				if (textField.getText().equals(result) || textField.getText().equals("There is no next operation.")
						|| textField.getText().equals("No previous operation.")
						|| textField.getText().equals("There is no memory.")) {
					string = new String();
					textField.setText(string);
				}
				string = textField.getText();
				textField.setText(string + 1);

			}
		});
		btnNewButton.setBounds(12, 86, 97, 79);
		frame.getContentPane().add(btnNewButton);

		btnNewButton_1 = new JButton("2");
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setFont(new Font("Bernard MT Condensed", Font.BOLD, 22));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String string;
				if (textField.getText().equals(result) || textField.getText().equals("There is no next operation.")
						|| textField.getText().equals("No previous operation.")
						|| textField.getText().equals("There is no memory.")) {
					string = new String();
					textField.setText(string);
				}

				string = textField.getText();
				textField.setText(string + 2);

			}
		});
		btnNewButton_1.setBounds(108, 86, 97, 79);
		frame.getContentPane().add(btnNewButton_1);

		btnNewButton_2 = new JButton("3");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String string;
				if (textField.getText().equals(result) || textField.getText().equals("There is no next operation.")
						|| textField.getText().equals("No previous operation.")
						|| textField.getText().equals("There is no memory.")) {
					string = new String();
					textField.setText(string);
				}
				string = textField.getText();
				textField.setText(string + 3);
			}
		});
		btnNewButton_2.setForeground(Color.BLACK);
		btnNewButton_2.setBackground(Color.WHITE);
		btnNewButton_2.setFont(new Font("Bernard MT Condensed", Font.BOLD, 22));
		btnNewButton_2.setBounds(201, 86, 97, 79);
		frame.getContentPane().add(btnNewButton_2);

		btnNewButton_3 = new JButton("4");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String string;
				if (textField.getText().equals(result) || textField.getText().equals("There is no next operation.")
						|| textField.getText().equals("No previous operation.")
						|| textField.getText().equals("There is no memory.")) {
					string = new String();
					textField.setText(string);
				}
				string = textField.getText();
				textField.setText(string + 4);

			}
		});
		btnNewButton_3.setForeground(Color.BLACK);
		btnNewButton_3.setBackground(Color.WHITE);
		btnNewButton_3.setFont(new Font("Bernard MT Condensed", Font.BOLD, 22));
		btnNewButton_3.setBounds(12, 153, 97, 85);
		frame.getContentPane().add(btnNewButton_3);

		btnNewButton_4 = new JButton("7");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String string;
				if (textField.getText().equals(result) || textField.getText().equals("There is no next operation.")
						|| textField.getText().equals("No previous operation.")
						|| textField.getText().equals("There is no memory.")) {
					string = new String();
					textField.setText(string);
				}
				string = textField.getText();
				textField.setText(string + 7);
			}
		});
		btnNewButton_4.setForeground(Color.BLACK);
		btnNewButton_4.setBackground(Color.WHITE);
		btnNewButton_4.setFont(new Font("Bernard MT Condensed", Font.BOLD, 22));
		btnNewButton_4.setBounds(12, 225, 97, 79);
		frame.getContentPane().add(btnNewButton_4);

		btnNewButton_5 = new JButton("5");
		btnNewButton_5.setForeground(Color.BLACK);
		btnNewButton_5.setBackground(Color.WHITE);
		btnNewButton_5.setFont(new Font("Bernard MT Condensed", Font.BOLD, 22));
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String string;
				if (textField.getText().equals(result) || textField.getText().equals("There is no next operation.")
						|| textField.getText().equals("No previous operation.")
						|| textField.getText().equals("There is no memory.")) {
					string = new String();
					textField.setText(string);
				}
				string = textField.getText();
				textField.setText(string + 5);
			}
		});
		btnNewButton_5.setBounds(108, 153, 97, 85);
		frame.getContentPane().add(btnNewButton_5);

		btnNewButton_6 = new JButton("8");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String string;
				if (textField.getText().equals(result) || textField.getText().equals("There is no next operation.")
						|| textField.getText().equals("No previous operation.")
						|| textField.getText().equals("There is no memory.")) {
					string = new String();
					textField.setText(string);
				}
				string = textField.getText();
				textField.setText(string + 8);
			}
		});
		btnNewButton_6.setForeground(Color.BLACK);
		btnNewButton_6.setBackground(Color.WHITE);
		btnNewButton_6.setFont(new Font("Bernard MT Condensed", Font.BOLD, 22));
		btnNewButton_6.setBounds(108, 225, 97, 79);
		frame.getContentPane().add(btnNewButton_6);

		btnNewButton_7 = new JButton("6");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String string;
				if (textField.getText().equals(result) || textField.getText().equals("There is no next operation.")
						|| textField.getText().equals("No previous operation.")
						|| textField.getText().equals("There is no memory.")) {
					string = new String();
					textField.setText(string);
				}
				string = textField.getText();
				textField.setText(string + 6);
			}
		});
		btnNewButton_7.setForeground(Color.BLACK);
		btnNewButton_7.setBackground(Color.WHITE);
		btnNewButton_7.setFont(new Font("Bernard MT Condensed", Font.BOLD, 22));
		btnNewButton_7.setBounds(201, 153, 97, 85);
		frame.getContentPane().add(btnNewButton_7);

		btnNewButton_8 = new JButton("0");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String string;
				if (textField.getText().equals(result) || textField.getText().equals("There is no next operation.")
						|| textField.getText().equals("No previous operation.")
						|| textField.getText().equals("There is no memory.")) {
					string = new String();
					textField.setText(string);
				}
				string = textField.getText();
				textField.setText(string + 0);
			}
		});
		btnNewButton_8.setForeground(Color.BLACK);
		btnNewButton_8.setBackground(Color.WHITE);
		btnNewButton_8.setFont(new Font("Bernard MT Condensed", Font.BOLD, 22));
		btnNewButton_8.setBounds(108, 303, 190, 75);
		frame.getContentPane().add(btnNewButton_8);

		btnNewButton_9 = new JButton("9");
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String string;
				if (textField.getText().equals(result)) {
					string = new String();
					textField.setText(string);
				}
				string = textField.getText();
				textField.setText(string + 9);
			}
		});
		btnNewButton_9.setForeground(Color.BLACK);
		btnNewButton_9.setBackground(Color.WHITE);
		btnNewButton_9.setFont(new Font("Bernard MT Condensed", Font.BOLD, 22));
		btnNewButton_9.setBounds(201, 225, 97, 79);
		frame.getContentPane().add(btnNewButton_9);

		JButton btnNewButton_10 = new JButton("SAVE");
		btnNewButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save = true;
				calc.save();
			}
		});
		btnNewButton_10.setForeground(Color.BLACK);
		btnNewButton_10.setFont(new Font("Snap ITC", Font.BOLD | Font.ITALIC, 20));
		btnNewButton_10.setBounds(297, 86, 110, 79);
		frame.getContentPane().add(btnNewButton_10);

		JButton btnNewButton_11 = new JButton("LOAD");
		btnNewButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String current;
				calc.load();
				current = calc.current();
				if (!save) {
					textField.setText("There is no memory.");
				} else {
					textField.setText(current);
				}
			}
		});
		btnNewButton_11.setForeground(Color.BLACK);
		btnNewButton_11.setFont(new Font("Snap ITC", Font.BOLD | Font.ITALIC, 20));
		btnNewButton_11.setBounds(406, 86, 107, 79);
		frame.getContentPane().add(btnNewButton_11);

		JButton btnNewButton_12 = new JButton("NEXT");
		btnNewButton_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String current;
				current = calc.next();
				if (current == null) {
					textField.setText("There is no next operation.");
				} else {
					textField.setText(current);
				}

			}
		});
		btnNewButton_12.setForeground(Color.BLACK);
		btnNewButton_12.setFont(new Font("Snap ITC", Font.BOLD | Font.ITALIC, 20));
		btnNewButton_12.setBounds(297, 153, 110, 85);
		frame.getContentPane().add(btnNewButton_12);

		JButton btnNewButton_13 = new JButton("PREV");
		btnNewButton_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String current;
				current = calc.prev();
				if (current == null) {
					textField.setText("No previous operation.");
				} else {
					textField.setText(current);
				}
			}

		});
		btnNewButton_13.setForeground(Color.BLACK);
		btnNewButton_13.setFont(new Font("Snap ITC", Font.BOLD | Font.ITALIC, 20));
		btnNewButton_13.setBounds(406, 153, 107, 85);
		frame.getContentPane().add(btnNewButton_13);

		JButton btnNewButton_14 = new JButton("+");
		btnNewButton_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String string = textField.getText();
				textField.setText(string + '+');
			}
		});
		btnNewButton_14.setForeground(Color.BLACK);
		btnNewButton_14.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton_14.setBounds(297, 228, 110, 43);
		frame.getContentPane().add(btnNewButton_14);

		JButton btnNewButton_15 = new JButton("-");
		btnNewButton_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String string = textField.getText();
				textField.setText(string + '-');
			}
		});
		btnNewButton_15.setForeground(Color.BLACK);
		btnNewButton_15.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton_15.setBounds(406, 234, 107, 37);
		frame.getContentPane().add(btnNewButton_15);

		JButton btnNewButton_16 = new JButton("*");
		btnNewButton_16.setForeground(Color.BLACK);
		btnNewButton_16.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String string = textField.getText();
				textField.setText(string + '*');
			}
		});
		btnNewButton_16.setBounds(297, 269, 110, 35);
		frame.getContentPane().add(btnNewButton_16);

		JButton btnNewButton_17 = new JButton("/");
		btnNewButton_17.setForeground(Color.BLACK);
		btnNewButton_17.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton_17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String string = textField.getText();
				textField.setText(string + '/');

			}
		});
		btnNewButton_17.setBounds(406, 268, 107, 36);
		frame.getContentPane().add(btnNewButton_17);

		JButton btnNewButton_18 = new JButton("=");
		btnNewButton_18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String string = textField.getText();

				calc.input(string);
				result = calc.getResult();
				textField.setText(result);

			}
		});
		btnNewButton_18.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton_18.setBounds(297, 303, 216, 75);
		frame.getContentPane().add(btnNewButton_18);

		btnNewButton_19 = new JButton("AC");
		btnNewButton_19.setBackground(Color.RED);
		btnNewButton_19.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String string = new String();
				textField.setText(string);

			}
		});
		btnNewButton_19.setFont(new Font("Bernard MT Condensed", Font.BOLD, 22));
		btnNewButton_19.setBounds(297, 11, 216, 75);
		frame.getContentPane().add(btnNewButton_19);

		JLabel label = new JLabel("New label");
		label.setBounds(68, 43, 56, 16);
		frame.getContentPane().add(label);

		JButton button = new JButton(".");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String string;
				if (textField.getText().equals(result) || textField.getText().equals("There is no next operation.")
						|| textField.getText().equals("No previous operation.")
						|| textField.getText().equals("There is no memory.")) {
					string = new String();
					textField.setText(string);
				}
				string = textField.getText();
				textField.setText(string + '.');
			}
		});
		button.setForeground(Color.BLACK);
		button.setFont(new Font("Bernard MT Condensed", Font.BOLD, 22));
		button.setBackground(Color.WHITE);
		button.setBounds(12, 299, 97, 79);
		frame.getContentPane().add(button);

	}
}
