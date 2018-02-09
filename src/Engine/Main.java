package Engine;

import java.io.IOException;

import UI.DataEntryForm;
import UI.ErrorMessage;
import UI.RankingDisplay;
import UI.TeamHistoryDisplay;

public class Main {
	public static void main(String args[]){
		try{
			VariableManager.init();
			DataEntryForm.init();
			TeamManager.init();
			RankingDisplay.init();
			TeamHistoryDisplay.init();
			ErrorMessage.init();
		}
		catch(IOException e){}
	}
}
