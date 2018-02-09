package UI;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Util.MatchData;
import Util.Variable;
import Engine.TeamManager;
import Engine.VariableManager;
public class DataEntryForm {
	static JLabel[] labels;
	static JTextField[] textFields;
	static HashMap<Integer,JComboBox<String>> comboBoxes;
	static JFrame frame;
	static JPanel panel;
	static Font labelFont;
	static Font dataFont;
	static JButton submitButton;
	static boolean[] isNumerical;
	static JTextField notes;
	public static void init(){
		initLabelsAndTextFields();
		frame = new JFrame();
		frame.setTitle("Data Entry Form");
		initFonts();
		notes = new JTextField();
		initPanel();
		frame.add(panel);
		frame.setVisible(true);
		frame.getContentPane().setLayout(new GridLayout(0,1));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(480,90*labels.length+90);
		
	}
	public static void submitData(){
		MatchData data = new MatchData(textFields[0].getText());
		for(int i = 1;i<labels.length;i++){
			if(isNumerical[i]){
				data.addMatchVariable(Double.parseDouble(filterWhiteSpace(textFields[i].getText())));
			}
			else{
				data.addMatchVariable((double)comboBoxes.get(i).getSelectedIndex());
			}
		}
		data.addNote(notes.getText());
		data.createDerivedVariables();
		TeamManager.submitData(data);
		resetFields();
		if(RankingDisplay.isInitialized){
			RankingDisplay.update();
		}
		if(TeamHistoryDisplay.isInitialized){
		}
	}
	public static void resetFields(){
		for(int i = 0;i<textFields.length;i++){
			if(isNumerical[i]){
				textFields[i].setText("");
			}
			else{
				comboBoxes.get(i).setSelectedIndex(0);
			}
		}
		notes.setText("");
	}
	public static String filterWhiteSpace(String s){
		String newString = s.replaceAll(" ","");
		return newString;
	}
	public static void initPanel(){
		panel = new JPanel(new GridLayout(0,1));
		for(int i = 0;i<labels.length;i++){
			panel.add(labels[i]);
			if(isNumerical[i]){
				panel.add(textFields[i]);
			}
			else{
				panel.add(comboBoxes.get(i));
			}
		}
		panel.add(new JLabel("Notes:"));
		panel.add(notes);
		submitButton = new JButton("SUBMIT");
		submitButton.setFont(labelFont);
		panel.add(submitButton);
		submitButton.addMouseListener(new MouseListener(){
			@Override
			public void mousePressed(MouseEvent e) {
				try{
					submitData();
				}
				catch(Exception i){
					ErrorMessage.popUp("Messed up somewhere. Try again.");
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}			
		});
	}
	public static void initLabelsAndTextFields(){
		int length = VariableManager.matchVariables.length+1;
		labels = new JLabel[length];
		textFields = new JTextField[length];
		comboBoxes = new HashMap<Integer,JComboBox<String>>();
		isNumerical = new boolean[length];
		isNumerical[0] = true;
		labels[0] = new JLabel("TeamNumber");
		textFields[0] = new JTextField("");
		for(int i = 1;i<labels.length;i++){
			labels[i] = new JLabel(VariableManager.matchVariables[i-1].name);
			if(VariableManager.matchVariables[i-1].type==Variable.typeNumerical){
				textFields[i] = new JTextField(0);
				isNumerical[i] = true;
			}
			else{
				comboBoxes.put(i, new JComboBox<String>(new String[]{"NO","YES"}));
				isNumerical[i] = false;
			}
		}
	}
	public static void initFonts(){
		labelFont = new Font("Optimus",Font.PLAIN,20);
		dataFont = new Font("Times New Roman",Font.PLAIN,16);
		for(int i = 0;i<labels.length;i++){
			labels[i].setFont(labelFont);
			if(isNumerical[i]){
				textFields[i].setFont(dataFont);
			}
			else{
				comboBoxes.get(i).setFont(dataFont);
			}
		}
	}
}
