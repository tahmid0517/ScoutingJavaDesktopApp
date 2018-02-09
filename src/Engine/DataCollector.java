package Engine;

import java.io.IOException;
import java.text.DecimalFormat;
import Util.FileReader;
import Util.Team;
import Util.Variable;
public class DataCollector {
	public static String[][] getSummaries(Team team)throws IOException{
		return calculateSummaries(collectTeamData(team),team.getMatchesPlayed());
	}
	public static double[][] collectTeamData(Team team){
		int numOfMatchedVariables = VariableManager.matchVariables.length;
		int numOfDerivedVariables = VariableManager.derivedVariables.length;
		int totalNumOfVariables = numOfMatchedVariables+numOfDerivedVariables;
		int matchesPlayed = team.getMatchesPlayed();
		double[][] data = new double[matchesPlayed][totalNumOfVariables];
		for(int i = 0;i<matchesPlayed;i++)
		{
			try{
				data[i] = FileReader.readMatchData(team,i+1);
			}
			catch(IOException e){}
		}
		return data;
	}
	public static String[][] calculateSummaries(double[][] data, int numOfMatches){
		int numOfVariables = Math.max(VariableManager.matchVariables.length,VariableManager.derivedVariables.length);
		String[][] summaries = new String[2][numOfVariables];
		for(int i = 0;i<VariableManager.matchVariables.length;i++){
			int type = VariableManager.matchVariables[i].type;
			double[] dataSet = new double[numOfMatches];
			for(int j = 0;j<numOfMatches;j++){
				dataSet[j] = data[j][i];
			}
			summaries[0][i] = getIndividualSetSummary(dataSet,type);
		}
		for(int i = 0;i<VariableManager.derivedVariables.length;i++){
			int type = VariableManager.derivedVariables[i].type;
			double[] dataSet = new double[numOfMatches];
			for(int j = 0;j<numOfMatches;j++){
				dataSet[j] = data[j][i+VariableManager.matchVariables.length];
			}
			summaries[1][i] = getIndividualSetSummary(dataSet,type);
		}
		return summaries;
	}
	public static String getIndividualSetSummary(double[] data, int type){
		if(type==Variable.typeBoolean){
			int successful = 0;
			for(int i = 0;i<data.length;i++){
				if(data[i]==1){
					successful++;
				}
			}
			return String.valueOf(successful)+"/"+String.valueOf(data.length);
		}
		else if(type==Variable.typeNumerical){
			double total = 0;
			double max = Double.MIN_VALUE;
			double min = Double.MAX_VALUE;
			for(int i = 0;i<data.length;i++){
				total+=data[i];
				max = Math.max(max,data[i]);
				min = Math.min(min,data[i]);
			}
			double range = max - min;
			double mean = total/data.length;
			double varianceTotal = 0;
			for(int i = 0;i<data.length;i++){
				varianceTotal+=((data[i]-mean)*(data[i]-mean));
			}
			double standardDev = Math.sqrt(varianceTotal/data.length);
			DecimalFormat df = new DecimalFormat("###.###");
			return df.format(mean)+"/"+df.format(standardDev)+"/"+df.format(range);
		}
		return "";
	}
}
