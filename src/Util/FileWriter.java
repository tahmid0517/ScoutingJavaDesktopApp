package Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import Engine.VariableManager;
public class FileWriter {
	public static void createFile(String path,String string) throws IOException{
		if(!new File(path).exists()){
			writeLineToFile(path,string);
		}
	}
	public static void writeLineToFile(String path, String string)throws IOException{
		PrintWriter w = new PrintWriter(path,"UTF-8");
		w.println(string);
		w.close();
	}
	public static void writeTwoLinesToFile(String path,String line1,String line2)throws IOException{
		PrintWriter w = new PrintWriter(path,"UTF-8");
		w.println(line1);
		w.println(line2);
		w.close();
	}
	public static void writeMatchNote(String path,String line)throws IOException{
		BufferedReader r = new BufferedReader(new java.io.FileReader(path));
		String sb = "";
		String read = r.readLine();
		System.out.println(read);
		while(!read.contains("end")){
			sb+=(read+"\n");
			read = r.readLine();
			System.out.println(read);
		}
		r.close();
		PrintWriter w = new PrintWriter(path,"UTF-8");
		System.out.println(sb);
		w.println(sb);
		w.println(line);
		w.println("end");
		w.close();
	}
	public static void writeTeamSummary(Team team,String[][] summaries)throws IOException{
		String path = team.filePath+"//Summary";
		for(int i = 0;i<summaries[0].length;i++){
			String summaryPath = path+"//"+VariableManager.matchVariables[i].name+".txt";
			writeLineToFile(summaryPath,summaries[0][i]);
		}
		for(int i = 0;i<VariableManager.derivedVariables.length;i++){
			String summaryPath = path+"//"+VariableManager.derivedVariables[i].name+".txt";
			writeLineToFile(summaryPath,summaries[1][i]);
		}
	}
}
