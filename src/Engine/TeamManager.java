package Engine;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import UI.ErrorMessage;
import Util.FileReader;
import Util.MatchData;
import Util.Paths;
import Util.Team;
import Util.TeamListSorter;
public class TeamManager {
	static HashMap<String,Team> teams;
	public static String[] teamNumbers;
	public static void init()throws IOException{
		initTeamList();
		initDirectories();
	}
	public static void initTeamList()throws IOException{
		teams = new HashMap<String,Team>();
		ArrayList<String> teamList = FileReader.readList(Paths.teamList);
		for(int i = 0;i<teamList.size();i++){
			teams.put(teamList.get(i),new Team(teamList.get(i)));
		}
		teamNumbers = TeamListSorter.sort(teamList);
	}
	public static void initDirectories(){
		new File(Paths.data).mkdir();
		new File(Paths.teamData).mkdir();
		for(int i = 0;i<teamNumbers.length;i++){
			teams.get(teamNumbers[i]).initDirectories();
		}
	}
	public static void submitData(MatchData data){
		try{
			teams.get(data.name).updateMatchData(data);
		}
		catch(IOException e){}
		catch(NullPointerException i){
			ErrorMessage.popUp("ERROR: MIGHT'VE TYPED IN WRONG TEAM NUMBER.");
		}
	}
	public static Team getTeam(String num){
		return teams.get(num);
	}
}
