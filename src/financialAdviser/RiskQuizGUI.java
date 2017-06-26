package financialAdviser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class RiskQuizGUI extends JFrame {

	JPanel pLeft = new JPanel();
	JPanel pRight = new JPanel();
	JPanel pBottom = new JPanel();
	JPanel pUserName = new JPanel();
	private ClientHandlerAgent myAgent;
	private JTextField username;
	private int Q1, Q2, Q3, Q4, Q5, Q6;

	public RiskQuizGUI(ClientHandlerAgent r) {
		super(r.getLocalName());
		myAgent = r;
		
		pLeft.setLayout(new GridLayout(6, 1));
		pRight.setLayout(new GridLayout(6, 5));
		pUserName.setLayout(new GridLayout(1, 6));
		
		
		pUserName.add(new JLabel("Username:"));
		username = new JTextField(15);
		pUserName.add(username);

		//Add Question
		pLeft.add(new JLabel("Q1: People who know me would describe me as a cautious person."));
		//Create the radio buttons.
		final JRadioButton stronglyAgree1 = new JRadioButton("Strongly agree");
		stronglyAgree1.setActionCommand(stronglyAgree1.getText());
		final JRadioButton agree1 = new JRadioButton("Agree");
		agree1.setActionCommand(agree1.getText());
		final JRadioButton noStrongOpinion1 = new JRadioButton("No strong opinion");
		noStrongOpinion1.setActionCommand(noStrongOpinion1.getText());
		final JRadioButton disagree1 = new JRadioButton("Disagree");
		disagree1.setActionCommand(disagree1.getText());
		final JRadioButton stronglyDisagree1 = new JRadioButton("Strongly disagree");
		stronglyDisagree1.setActionCommand(stronglyDisagree1.getText());
		noStrongOpinion1.setSelected(true);

		stronglyAgree1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {         
				System.out.println("stronglyAgree");
			}           
		});
		agree1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("agree");
			}           
		});
		noStrongOpinion1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("No strong opinion");
			}           
		});
		disagree1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("disagree");
			}           
		});
		stronglyDisagree1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("Strongly disagree");
			}           
		});

		ButtonGroup group1 = new ButtonGroup();
		group1.add(stronglyAgree1);
		group1.add(agree1);
		group1.add(noStrongOpinion1);
		group1.add(disagree1);
		group1.add(stronglyDisagree1);

		pRight.add(stronglyAgree1);
		pRight.add(agree1);
		pRight.add(noStrongOpinion1);
		pRight.add(disagree1);
		pRight.add(stronglyDisagree1);

		//Add Question
		pLeft.add(new JLabel("Q2: I feel comfortable about investing in the stock market."));

		//Create the radio buttons.
		final JRadioButton stronglyAgree2 = new JRadioButton("Strongly agree");
		stronglyAgree2.setActionCommand(stronglyAgree2.getText());
		final JRadioButton agree2 = new JRadioButton("Agree");
		agree2.setActionCommand(agree2.getText());
		final JRadioButton noStrongOpinion2 = new JRadioButton("No strong opinion");
		noStrongOpinion2.setActionCommand(noStrongOpinion2.getText());
		final JRadioButton disagree2 = new JRadioButton("Disagree");
		disagree2.setActionCommand(disagree2.getText());
		final JRadioButton stronglyDisagree2 = new JRadioButton("Strongly disagree");
		stronglyDisagree2.setActionCommand(stronglyDisagree2.getText());
		noStrongOpinion2.setSelected(true);

		stronglyAgree2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {         
				System.out.println("stronglyAgree");
			}           
		});
		agree2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("agree");
			}           
		});
		noStrongOpinion2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("No strong opinion");
			}           
		});
		disagree2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("disagree");
			}           
		});
		stronglyDisagree2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("Strongly disagree");
			}           
		});

		ButtonGroup group2 = new ButtonGroup();
		group2.add(stronglyAgree2);
		group2.add(agree2);
		group2.add(noStrongOpinion2);
		group2.add(disagree2);
		group2.add(stronglyDisagree2);

		pRight.add(stronglyAgree2);
		pRight.add(agree2);
		pRight.add(noStrongOpinion2);
		pRight.add(disagree2);
		pRight.add(stronglyDisagree2);

		//Add Question
		pLeft.add(new JLabel("Q3: I generally look for the safest type of investment, even if that means lower returns."));

		//Create the radio buttons.
		final JRadioButton stronglyAgree3 = new JRadioButton("Strongly agree");
		stronglyAgree3.setActionCommand(stronglyAgree3.getText());
		final JRadioButton agree3 = new JRadioButton("Agree");
		agree3.setActionCommand(agree3.getText());
		final JRadioButton noStrongOpinion3 = new JRadioButton("No strong opinion");
		noStrongOpinion3.setActionCommand(noStrongOpinion3.getText());
		final JRadioButton disagree3 = new JRadioButton("Disagree");
		disagree3.setActionCommand(disagree3.getText());
		final JRadioButton stronglyDisagree3 = new JRadioButton("Strongly disagree");
		stronglyDisagree3.setActionCommand(stronglyDisagree3.getText());
		noStrongOpinion3.setSelected(true);

		stronglyAgree3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {         
				System.out.println("stronglyAgree");
			}           
		});
		agree3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("agree");
			}           
		});
		noStrongOpinion3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("No strong opinion");
			}           
		});
		disagree3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("disagree");
			}           
		});
		stronglyDisagree3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("Strongly disagree");
			}           
		});

		ButtonGroup group3 = new ButtonGroup();
		group3.add(stronglyAgree3);
		group3.add(agree3);
		group3.add(noStrongOpinion3);
		group3.add(disagree3);
		group3.add(stronglyDisagree3);

		pRight.add(stronglyAgree3);
		pRight.add(agree3);
		pRight.add(noStrongOpinion3);
		pRight.add(disagree3);
		pRight.add(stronglyDisagree3);

		//Add Question
		pLeft.add(new JLabel("Q4: Usually it takes me a long time to make up my mind on finnancial matters."));

		//Create the radio buttons.
		final JRadioButton stronglyAgree4 = new JRadioButton("Strongly agree");
		stronglyAgree4.setActionCommand(stronglyAgree4.getText());
		final JRadioButton agree4 = new JRadioButton("Agree");
		agree4.setActionCommand(agree4.getText());
		final JRadioButton noStrongOpinion4 = new JRadioButton("No strong opinion");
		noStrongOpinion4.setActionCommand(noStrongOpinion4.getText());
		final JRadioButton disagree4 = new JRadioButton("Disagree");
		disagree4.setActionCommand(disagree4.getText());
		final JRadioButton stronglyDisagree4 = new JRadioButton("Strongly disagree");
		stronglyDisagree4.setActionCommand(stronglyDisagree4.getText());
		noStrongOpinion4.setSelected(true);

		stronglyAgree4.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {         
				System.out.println("stronglyAgree");
			}           
		});
		agree4.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("agree");
			}           
		});
		noStrongOpinion4.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("No strong opinion");
			}           
		});
		disagree4.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("disagree");
			}           
		});
		stronglyDisagree4.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("Strongly disagree");
			}           
		});

		ButtonGroup group4 = new ButtonGroup();
		group4.add(stronglyAgree4);
		group4.add(agree4);
		group4.add(noStrongOpinion4);
		group4.add(disagree4);
		group4.add(stronglyDisagree4);

		pRight.add(stronglyAgree4);
		pRight.add(agree4);
		pRight.add(noStrongOpinion4);
		pRight.add(disagree4);
		pRight.add(stronglyDisagree4);

		//Add Question
		pLeft.add(new JLabel("Q5: I associate the word ‘risk’ with the idea of ‘opportunity’."));

		//Create the radio buttons.
		final JRadioButton stronglyAgree5 = new JRadioButton("Strongly agree");
		stronglyAgree5.setActionCommand(stronglyAgree5.getText());
		final JRadioButton agree5 = new JRadioButton("Agree");
		agree5.setActionCommand(agree5.getText());
		final JRadioButton noStrongOpinion5 = new JRadioButton("No strong opinion");
		noStrongOpinion5.setActionCommand(noStrongOpinion5.getText());
		final JRadioButton disagree5 = new JRadioButton("Disagree");
		disagree5.setActionCommand(disagree5.getText());
		final JRadioButton stronglyDisagree5 = new JRadioButton("Strongly disagree");
		stronglyDisagree5.setActionCommand(stronglyDisagree5.getText());
		noStrongOpinion5.setSelected(true);

		stronglyAgree5.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {         
				System.out.println("stronglyAgree");
			}           
		});
		agree5.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("agree");
			}           
		});
		noStrongOpinion5.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("No strong opinion");
			}           
		});
		disagree5.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("disagree");
			}           
		});
		stronglyDisagree5.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("Strongly disagree");
			}           
		});

		ButtonGroup group5 = new ButtonGroup();
		group5.add(stronglyAgree5);
		group5.add(agree5);
		group5.add(noStrongOpinion5);
		group5.add(disagree5);
		group5.add(stronglyDisagree5);

		pRight.add(stronglyAgree5);
		pRight.add(agree5);
		pRight.add(noStrongOpinion5);
		pRight.add(disagree5);
		pRight.add(stronglyDisagree5);

		//Add Question
		pLeft.add(new JLabel("Q6: I prefer the safety of keeping my money in the bank."));

		//Create the radio buttons.
		final JRadioButton stronglyAgree6 = new JRadioButton("Strongly agree");
		stronglyAgree6.setActionCommand(stronglyAgree6.getText());
		final JRadioButton agree6 = new JRadioButton("Agree");
		agree6.setActionCommand(agree6.getText());
		final JRadioButton noStrongOpinion6 = new JRadioButton("No strong opinion");
		noStrongOpinion6.setActionCommand(noStrongOpinion6.getText());
		final JRadioButton disagree6 = new JRadioButton("Disagree");
		disagree6.setActionCommand(disagree6.getText());
		final JRadioButton stronglyDisagree6 = new JRadioButton("Strongly disagree");
		stronglyDisagree6.setActionCommand(stronglyDisagree6.getText());
		noStrongOpinion6.setSelected(true);

		stronglyAgree6.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {         
				System.out.println("stronglyAgree");
			}           
		});
		agree6.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("agree");
			}           
		});
		noStrongOpinion6.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("No strong opinion");
			}           
		});
		disagree6.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("disagree");
			}           
		});
		stronglyDisagree6.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				System.out.println("Strongly disagree");
			}           
		});

		ButtonGroup group6 = new ButtonGroup();
		group6.add(stronglyAgree6);
		group6.add(agree6);
		group6.add(noStrongOpinion6);
		group6.add(disagree6);
		group6.add(stronglyDisagree6);

		pRight.add(stronglyAgree6);
		pRight.add(agree6);
		pRight.add(noStrongOpinion6);
		pRight.add(disagree6);
		pRight.add(stronglyDisagree6);

		getContentPane().add(pUserName, BorderLayout.NORTH);
		getContentPane().add(pLeft, BorderLayout.WEST);
		getContentPane().add(pRight, BorderLayout.EAST);


		
		//		p.add(new JLabel("Q7: I find investment and other financial matters easy to understand."));
		//		
		//		p.add(new JLabel("Q8: I am willing to take substantial financial risk to earn substantial returns."));
		//		
		//		p.add(new JLabel("Q9: I have little experience of investing in stocks and shares."));
		//		
		//		p.add(new JLabel("Q10: When it comes to investing, I’d rather be safe than sorry."));
		//		
		//		p.add(new JLabel("Q11: I’d rather take my chances with high risk / high return investments than have to increase the amount I am saving."));
		//		
		//		p.add(new JLabel("Q12: I am concerned by the uncertainty of stock market investment."));
		

		JButton addButton = new JButton("Add");
		addButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					String name = username.getText().trim();
					String Q1String = group1.getSelection().getActionCommand();
					String Q2String = group2.getSelection().getActionCommand();
					String Q3String = group3.getSelection().getActionCommand();
					String Q4String = group4.getSelection().getActionCommand();
					String Q5String = group5.getSelection().getActionCommand();
					String Q6String = group6.getSelection().getActionCommand();
					System.out.println(Q1String + ", " + Q2String + ", " + Q3String + ", " +
					Q4String + ", " + Q5String + ", " +  Q6String);
					
					// Need to change this & change the underlying data structure.
					ArrayList<String> answers = new ArrayList<String>();
					answers.add(Q1String);
					answers.add(Q2String);
					answers.add(Q3String);
					answers.add(Q4String);
					answers.add(Q5String);
					answers.add(Q6String);
					
					r.updateClientList(name, answers);
					
					username.setText("");
					group1.clearSelection();
					group2.clearSelection();
					group3.clearSelection();
					group4.clearSelection();
					group5.clearSelection();
					group6.clearSelection();
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(RiskQuizGUI.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		} );

		pBottom.add(addButton);
		getContentPane().add(pBottom, BorderLayout.SOUTH);
		
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
