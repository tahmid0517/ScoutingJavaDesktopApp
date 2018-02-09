package Util;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import Engine.DataCollector;
import Engine.VariableManager;

public class Team {
	public String teamNumber;
	String filePath,matchDataPath,summaryPath;
	public Team(String n){
		teamNumber = n;
		filePath = Paths.teamData+"//"+teamNumber;
		matchDataPath = filePath+"//MatchData";
		summaryPath = filePath+"//Summary";
	}
	public void initDirectories(){
		new File(filePath).mkdir();
		new File(matchDataPath).mkdir();
		new File(summaryPath).mkdir();
		try{
			initMatchData();
			initSummary();
			FileWriter.createFile(filePath+"//Notes.txt","end");
		}
		catch(IOException e){}
	}
	public void initMatchData()throws IOException{
		FileWriter.createFile(matchDataPath+"//MatchesPlayed.txt","0");
		for(int i = 1;i<=15;i++){
			FileWriter.createFile(matchDataPath+"//"+String.valueOf(i)+".txt","0");
		}
	}
	public void initSummary()throws IOException{
		for(int i = 0;i<VariableManager.matchVariables.length;i++){
			String path = summaryPath+"//"+VariableManager.matchVariables[i].name+".txt";
			String initialSummary = null;
			if(VariableManager.matchVariables[i].type==Variable.typeNumerical){
				initialSummary = "0/0/0";
			}
			else if(VariableManager.matchVariables[i].type==Variable.typeBoolean){
				initialSummary = "0/0";
			}
			FileWriter.createFile(path,initialSummary);
			//mean/standarddev/range/
		}
		for(int i = 0;i<VariableManager.derivedVariables.length;i++){
			String path = summaryPath+"//"+VariableManager.derivedVariables[i].name+".txt";
			String initialSummary = null;
			if(VariableManager.derivedVariables[i].type==Variable.typeNumerical){
				initialSummary = "0/0/0";
			}
			else if(VariableManager.derivedVariables[i].type==Variable.typeBoolean){
				initialSummary = "0/0";
			}
			FileWriter.createFile(path,initialSummary);
		}
	}
	public void updateMatchData(MatchData data)throws IOException{
		String matchesPlayedPath = matchDataPath+"//MatchesPlayed.txt";
		int matchesPlayed = getMatchesPlayed() + 1;
		FileWriter.writeLineToFile(matchesPlayedPath,String.valueOf(matchesPlayed));
		String matchPath = matchDataPath+"//"+String.valueOf(matchesPlayed)+".txt";
		StringBuilder matchVariableLine = new StringBuilder("");
		DecimalFormat df = new DecimalFormat("###.###");
		for(int i = 0;i<data.matchVariables.length;i++){
			matchVariableLine.append(df.format(data.matchVariables[i]));
			matchVariableLine.append("/");
		}
		StringBuilder derivedVariableLine = new StringBuilder("");
		for(int i = 0;i<data.derivedVariables.length;i++){
			derivedVariableLine.append(df.format(data.derivedVariables[i]));
			derivedVariableLine.append("/");
		}
		FileWriter.writeTwoLinesToFile(matchPath,matchVariableLine.toString(),derivedVariableLine.toString());
		updateDataSummaries();
		data.note = String.valueOf(getMatchesPlayed()) + ": " +data.note;
		FileWriter.writeMatchNote(filePath+"//Notes.txt",data.note);
	}
	public void updateDataSummaries()throws IOException{
		String[][] summaries = DataCollector.getSummaries(this);
		FileWriter.writeTeamSummary(this, summaries);
	}
	public int getMatchesPlayed(){
		int matchesPlayed = 0;
		try{
			matchesPlayed = FileReader.readNumber(matchDataPath+"//MatchesPlayed.txt");
		}
		catch(IOException e){}
		return matchesPlayed;
	}
}
