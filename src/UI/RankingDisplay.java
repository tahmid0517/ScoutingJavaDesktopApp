package UI;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import Util.BooleanSummary;
import Util.FileReader;
import Util.NumericalSummary;
import Util.Variable;
import Engine.Ranker;
import Engine.TeamManager;
import Engine.VariableManager;

public class RankingDisplay {
	static JFrame frame;
	static JPanel panel;
	static JPanel btnPanel;
	static JButton[] variableBtns;
	static JTable table;
	static int currentRankings;
	static boolean isInitialized = false;
	public static void init(){
		frame = new JFrame();
		initButtonPanel();
		frame.add(btnPanel);
		initPanel();
		frame.add(panel);
		frame.setTitle("Rankings");
		frame.setVisible(true);
		frame.getContentPane().setLayout(new GridLayout(1,2));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		currentRankings = -1;
		isInitialized = true;
	}
	public static void initPanel(){
		panel = new JPanel();
	}
	public static void initButtonPanel(){
		btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(0,1));
		int numOfBtns = VariableManager.matchVariables.length+VariableManager.derivedVariables.length;
		variableBtns = new JButton[numOfBtns];
		for(int i = 0;i<VariableManager.matchVariables.length;i++){
			variableBtns[i] = new JButton(VariableManager.matchVariables[i].name);
			btnPanel.add(variableBtns[i]);
		}
		for(int i = 0;i<VariableManager.derivedVariables.length;i++){
			int index = i + VariableManager.matchVariables.length;
			variableBtns[index] = new JButton(VariableManager.derivedVariables[i].name);
			btnPanel.add(variableBtns[index]);
		}
		for(int i = 0;i<numOfBtns;i++){
			int index = i;
			variableBtns[i].addMouseListener(new MouseListener(){
				@Override
				public void mousePressed(MouseEvent e) {
					createTable(index);
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
	}
	public static void createTable(int index){
		currentRankings = index;
		if(table!=null){
			panel.remove(table);
		}
		int numOfMatchVariables = VariableManager.matchVariables.length;
		Variable variable;
		if(index<numOfMatchVariables){
			variable = VariableManager.matchVariables[index];
		}
		else {
			variable = VariableManager.derivedVariables[index - numOfMatchVariables];
		}
		frame.setTitle("Rankings: "+variable.name);
		if(variable.type==Variable.typeNumerical){
			table = new JTable(TeamManager.teamNumbers.length+1,6);
			panel.add(table);
			table.setVisible(true);
			table.setValueAt("Rank",0,0);
			table.setValueAt("Team Number",0,1);
			table.setValueAt("Mean",0,2);
			table.setValueAt("Standard Deviation",0,3);
			table.setValueAt("Range",0,4);
			table.setValueAt("Matches Played",0,5);
			table.getColumnModel().getColumn(1).setPreferredWidth(100);
			table.getColumnModel().getColumn(3).setPreferredWidth(110);
			table.getColumnModel().getColumn(5).setPreferredWidth(100);
			NumericalSummary[] summaries = null;
			try{
				summaries = Ranker.rank(FileReader.getSummaryArrayNumerical(variable.name));
				
			}
			catch(IOException e){}
			for(int i = 0;i<summaries.length;i++){
				int row = i+1;
				table.setValueAt(row,row,0);
				table.setValueAt(summaries[i].teamNum,row,1);
				table.setValueAt(summaries[i].mean,row,2);
				table.setValueAt(summaries[i].standardDev,row,3);
				table.setValueAt(summaries[i].range,row,4);
				table.setValueAt(summaries[i].matchesPlayed,row,5);
			}
		}
		else if(variable.type==Variable.typeBoolean){
			table = new JTable(TeamManager.teamNumbers.length+1,4);
			panel.add(table);
			table.setVisible(true);
			table.setValueAt("Rank",0,0);
			table.setValueAt("Team Number",0,1);
			table.setValueAt("Frequency",0,2);
			table.setValueAt("MatchesPlayed",0,3);
			table.getColumnModel().getColumn(1).setPreferredWidth(100);
			table.getColumnModel().getColumn(3).setPreferredWidth(110);
			BooleanSummary[] summaries = null; 
			try{
				summaries = Ranker.rank(FileReader.getSummaryArrayBoolean(variable.name));
			}
			catch(IOException e){}
			for(int i = 0;i<summaries.length;i++){
				int row = i+1;
				table.setValueAt(row,row,0);
				table.setValueAt(summaries[i].teamNum,row,1);
				table.setValueAt(summaries[i].success,row,2);
				table.setValueAt(summaries[i].total,row,3);
			}
		}
		frame.pack();
	}
	public static void update(){
		if(currentRankings!=-1){
			createTable(currentRankings);
		}
	}
}
