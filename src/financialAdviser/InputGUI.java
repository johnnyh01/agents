package financialAdviser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputGUI extends JFrame {
	
	
	private ClientHandlerAgent myAgent;
	private JTextField username, annualIncome, totalSavings, totalDebt, housingEquity;
	
	public InputGUI(ClientHandlerAgent r) {
		super(r.getLocalName());

		myAgent = r;
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(5, 2));
		p.add(new JLabel("Username:"));
		username = new JTextField(15);
		p.add(username);
		p.add(new JLabel("Annual Income:"));
		annualIncome = new JTextField(15);
		p.add(annualIncome);
		p.add(new JLabel("Total Savings:"));
		totalSavings = new JTextField(15);
		p.add(totalSavings);
		p.add(new JLabel("Total Debt:"));
		totalDebt = new JTextField(15);
		p.add(totalDebt);
		p.add(new JLabel("Housing Equity:"));
		housingEquity = new JTextField(15);
		p.add(housingEquity);
		getContentPane().add(p, BorderLayout.CENTER);

		JButton addButton = new JButton("Add");
		addButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					String name = username.getText().trim();
					String income = annualIncome.getText().trim();
					String savings = totalSavings.getText().trim();
					String debt = totalDebt.getText().trim();
					String equity = housingEquity.getText().trim();
					
					ArrayList<String> answers = new ArrayList<String>();
					answers.add(name);
					answers.add(income);
					answers.add(savings);
					answers.add(debt);
					answers.add(equity);
					
					r.updateClientList(name, answers);
					
					username.setText("");
					annualIncome.setText("");
					totalSavings.setText("");
					totalDebt.setText("");
					housingEquity.setText("");
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(InputGUI.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		} );

				p = new JPanel();
				p.add(addButton);
				getContentPane().add(p, BorderLayout.SOUTH);

				// Make the agent terminate when the user closes 
				// the GUI using the button on the upper right corner	
				addWindowListener(new	WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						// terminate something when the user closes the JFrame
					}
				} );

				setResizable(false);
	}

	public void showGui() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int)screenSize.getWidth() / 2;
		int centerY = (int)screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}	
}
