package Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import Engine.TeamManager;
import Engine.VariableManager;

public class FileReader {
	public static ArrayList<String> readList(String path) throws IOException{
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader r = new BufferedReader(new java.io.FileReader(path));
		String line = r.readLine();
		while(!line.contains("end")){
			list.add(line);
			line = r.readLine();
		}
		r.close();
		return list;
	}
	public static String readOneLine(String path)throws IOException{
		BufferedReader r = new BufferedReader(new java.io.FileReader(path));
		String string = r.readLine();
		r.close();
		return string;
	}
	public static int readNumber(String path)throws IOException{
		return Integer.parseInt(readOneLine(path));
	}
	public static double[] readMatchData(Team team,int matchNum)throws IOException{
		String path = team.matchDataPath+"//"+String.valueOf(matchNum)+".txt";
		BufferedReader r = new BufferedReader(new java.io.FileReader(path));
		double[] data = new double[VariableManager.matchVariables.length+VariableManager.derivedVariables.length];
		String dataString = r.readLine()+r.readLine();
		r.close();
		StringTokenizer st = new StringTokenizer(dataString,"/");
		for(int i = 0;i<data.length;i++){
			data[i] = Double.parseDouble(st.nextToken());
		}
		return data;
	}
	public static NumericalSummary[] getSummaryArrayNumerical(String variable)throws IOException{
		NumericalSummary[] arr = new NumericalSummary[TeamManager.teamNumbers.length];
		for(int i = 0;i<arr.length;i++){
			String teamNum = TeamManager.teamNumbers[i];
			String path = Paths.teamData+"//"+teamNum+"//Summary//"+variable+".txt";
			arr[i] = new NumericalSummary(teamNum,readOneLine(path),TeamManager.getTeam(teamNum).getMatchesPlayed());
		}
		return arr;
	}
	public static BooleanSummary[] getSummaryArrayBoolean(String variable)throws IOException{
		BooleanSummary[] arr = new BooleanSummary[TeamManager.teamNumbers.length];
		for(int i = 0;i<arr.length;i++){
			String teamNum = TeamManager.teamNumbers[i];
			String path = Paths.teamData+"//"+teamNum+"//Summary//"+variable+".txt";
			arr[i] = new BooleanSummary(teamNum,readOneLine(path));
		}
		return arr;
	}
}
