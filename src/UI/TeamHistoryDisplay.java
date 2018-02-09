package UI;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import Util.FileReader;
import Util.Team;
import Util.Variable;
import Engine.DataCollector;
import Engine.TeamManager;
import Engine.VariableManager;
public class TeamHistoryDisplay {
	static JFrame frame;
	static JPanel teamPanel;
	static JButton[] teamBtns;
	static JPanel variablePanel;
	static JButton[] variableBtns;
	static JPanel matchPanel;
	static JButton[] matchBtns;
	static JPanel tablePanel;
	static JTable table;
	static JPanel matchTablePanel;
	static JTable matchTable;
	static String teamNumber;
	static int variableIndex;
	static int matchNum;
	static boolean isInitialized = false;
	public static void init(){
		frame = new JFrame();
		frame.setTitle("Team History");
		frame.setVisible(true);
		frame.setLayout(new GridLayout(0,3));
		initTablePanel();
		initTeamPanel();
		initVariablePanel();
		initMatchPanel();
		initMatchTablePanel();
		frame.add(tablePanel);
		frame.pack();
		frame.setSize(frame.getWidth(),Math.max(500,variableBtns.length*70));
		teamNumber = null;
		variableIndex = -1;
		matchNum = -1;
		isInitialized = true;
	}
	public static void initTeamPanel(){
		teamPanel = new JPanel();
		int numOfTeams = TeamManager.teamNumbers.length;
		teamPanel.setLayout(new GridLayout(10,10));
		frame.add(teamPanel);
		teamBtns = new JButton[numOfTeams];
		for(int i = 0;i<numOfTeams;i++){
			teamBtns[i] = new JButton(TeamManager.teamNumbers[i]);
			teamPanel.add(teamBtns[i]);
			int index = i;
			teamBtns[i].addMouseListener(new MouseListener(){
				@Override
				public void mousePressed(MouseEvent e){
					teamNumber = TeamManager.teamNumbers[index];
					table.setValueAt(teamNumber,0,1);
					matchTable.setValueAt(teamNumber,0,1);
					update();
				}
				@Override
				public void mouseClicked(MouseEvent e){}
				@Override
				public void mouseReleased(MouseEvent e){}
				@Override
				public void mouseEntered(MouseEvent e){}
				@Override
				public void mouseExited(MouseEvent e){}
			});
		}
	}
	public static void initVariablePanel(){
		variablePanel = new JPanel();
		variablePanel.setLayout(new GridLayout(0,1));
		frame.add(variablePanel);
		int numOfMatchVariables = VariableManager.matchVariables.length;
		int numOfDerivedVariables = VariableManager.derivedVariables.length;
		variableBtns = new JButton[numOfMatchVariables+numOfDerivedVariables];
		for(int i = 0;i<numOfMatchVariables;i++){
			String variableName = VariableManager.matchVariables[i].name;
			variableBtns[i] = new JButton(variableName);
			variablePanel.add(variableBtns[i]);
			int index = i;
			variableBtns[i].setSize(100,100);
			variableBtns[i].addMouseListener(new MouseListener(){
				@Override
				public void mousePressed(MouseEvent e) {
					variableIndex = index;
					table.setValueAt(variableName,1,1);
					update();
				}
				@Override
				public void mouseClicked(MouseEvent e){}
				@Override
				public void mouseReleased(MouseEvent e){}
				@Override
				public void mouseEntered(MouseEvent e){}
				@Override
				public void mouseExited(MouseEvent e){}
			});
		}
		for(int i = 0;i<numOfDerivedVariables;i++){
			int index = i + numOfMatchVariables;
			String variableName = VariableManager.derivedVariables[i].name;
			variableBtns[index] = new JButton(VariableManager.derivedVariables[i].name);
			variablePanel.add(variableBtns[index]);
			variableBtns[index].addMouseListener(new MouseListener(){
				@Override
				public void mousePressed(MouseEvent e){
					variableIndex = index;
					table.setValueAt(variableName,1,1);
					update();
				}
				@Override
				public void mouseClicked(MouseEvent e) {}
				@Override
				public void mouseReleased(MouseEvent e){}
				@Override
				public void mouseEntered(MouseEvent e){}
				@Override
				public void mouseExited(MouseEvent e){}
			});
		}
	}
	public static void initTablePanel(){
		tablePanel = new JPanel();
		tablePanel.setLayout(new GridLayout(0,1));
		table = new JTable(8,2);
		tablePanel.add(table);
		table.setRowHeight(20);
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		table.setValueAt("Team Number:",0,0);
		table.setValueAt("Variable:",1,0);
		table.setValueAt("Data Set:",2,0);
		table.setValueAt("Mean:",3,0);
		table.setValueAt("Standard Deviation:",4,0);
		table.setValueAt("Range:",5,0);
		table.setValueAt("Frequency:",6,0);
		table.setValueAt("Matches Played:",7,0);
	}
	public static double[] getDataSet(String teamNum,int index){
		Team team = TeamManager.getTeam(teamNum);
		double[] dataSet = new double[team.getMatchesPlayed()];
		for(int i = 0;i<dataSet.length;i++){
			try{
				dataSet[i] = FileReader.readMatchData(team,i+1)[index];
			}
			catch(IOException e){}
		}
		return dataSet;
	}
	public static void initMatchPanel(){
		matchPanel = new JPanel();
		matchPanel.setLayout(new GridLayout(3,5));
		matchBtns = new JButton[15];
		for(int i = 0;i<15;i++){
			matchBtns[i] = new JButton("Match "+String.valueOf(i+1));
			matchPanel.add(matchBtns[i]);
			int num = i + 1;
			matchBtns[i].addMouseListener(new MouseListener(){
				@Override
				public void mousePressed(MouseEvent e){
					matchNum = num;
					matchTable.setValueAt(matchNum,1,1);
					update();
				}
				@Override
				public void mouseClicked(MouseEvent e) {}
				@Override
				public void mouseReleased(MouseEvent e){}
				@Override
				public void mouseEntered(MouseEvent e){}
				@Override
				public void mouseExited(MouseEvent e){}
			});
		}
		frame.add(matchPanel);
	}
	public static void initMatchTablePanel(){
		matchTablePanel = new JPanel();
		matchTablePanel.setLayout(new GridLayout(0,1));
		int numOfVariables = VariableManager.matchVariables.length + VariableManager.derivedVariables.length;
		matchTable = new JTable(2+numOfVariables,2);
		matchTable.setValueAt("Team Number:",0,0);
		matchTable.setValueAt("Match #:",1,0);
		int i = 0;
		for(;i<VariableManager.matchVariables.length;i++){
			matchTable.setValueAt(VariableManager.matchVariables[i].name,i+2,0);
		}
		for(int j = 0;j<VariableManager.derivedVariables.length;j++){
			matchTable.setValueAt(VariableManager.derivedVariables[j].name,j+i+2,0);
		}
		matchTablePanel.add(matchTable);
		frame.add(matchTablePanel);
	}
	public static void update(){
		if(teamNumber!=null && variableIndex!=-1){
			updateVariableTable();
		}
		if(teamNumber!=null && matchNum!=-1){
			updateMatchTable();
		}
		frame.pack();
	}
	public static void updateMatchTable(){
		matchTable.setValueAt(teamNumber,0,1);
		matchTable.setValueAt(matchNum,1,1);
		Team team = TeamManager.getTeam(teamNumber);
		if(matchNum>team.getMatchesPlayed()){
			for(int i = 2;i<matchTable.getRowCount();i++){
				matchTable.setValueAt("N/A",i,1);
			}
		}
		else{
			double[] data = null;
			try{
				data = FileReader.readMatchData(TeamManager.getTeam(teamNumber), matchNum);
			}
			catch(IOException e){}
			DecimalFormat df = new DecimalFormat("###.#");
			for(int i = 0;i<data.length;i++){
				String value = "";
				if(getVariableType(i)==Variable.typeBoolean){
					if(data[i]==0){
						value = "No";
					}
					else if(data[i]==1){
						value = "Yes";
					}
				}
				else{
					value = df.format(data[i]);
				}
				matchTable.setValueAt(value,i+2,1);
			}
		}
	}
	public static void updateVariableTable(){
		double[] set = getDataSet(teamNumber,variableIndex);
		if(set.length==0){
			for(int i = 2;i<=6;i++){
				table.setValueAt("N/A",i,1);
			}
			table.setValueAt(0,7,1);
			return;
		}
		String setString = convertSetToString(set);
		int variableType = getVariableType(variableIndex);
		if(variableType==Variable.typeBoolean){
			setString = setString.replaceAll("1","Yes");
			setString = setString.replaceAll("0","No");
		}
		table.setValueAt(setString,2,1);
		String summary = DataCollector.getIndividualSetSummary(set,variableType);
		StringTokenizer st = new StringTokenizer(summary,"/");
		if(variableType==Variable.typeNumerical){
			table.setValueAt(st.nextToken(),3,1);
			table.setValueAt(st.nextToken(),4,1);
			table.setValueAt(st.nextToken(),5,1);
			table.setValueAt("N/A",6,1);
			table.setValueAt(set.length,7,1);
		}
		else if(variableType==Variable.typeBoolean){
			table.setValueAt("N/A",3,1);
			table.setValueAt("N/A",4,1);
			table.setValueAt("N/A",5,1);
			table.setValueAt(st.nextToken(),6,1);
			table.setValueAt(st.nextToken(),7,1);
		}
	}
	public static String convertSetToString(double[] set){
		if(set.length==0){
			return "";
		}
		StringBuilder sb = new StringBuilder("");
		DecimalFormat df = new DecimalFormat("###.###");
		for(int i = 0;i<set.length-1;i++){
			sb.append(df.format(set[i]));
			sb.append(",");
		}
		sb.append(df.format(set[set.length-1]));
		return sb.toString();
	}
	public static int getVariableType(int index){
		Variable variable;
		if(index<VariableManager.matchVariables.length){
			variable = VariableManager.matchVariables[index];
		}
		else{
			variable = VariableManager.derivedVariables[index - VariableManager.matchVariables.length];
		}
		return variable.type;
	}
}
