package Engine;
import java.io.IOException;
import java.util.ArrayList;
import Util.DerivedVariable;
import Util.FileReader;
import Util.MatchVariable;
import Util.Paths;
public class VariableManager {
	public static MatchVariable[] matchVariables;
	public static DerivedVariable[] derivedVariables;
	public static void init() throws IOException
	{
		initMatchVariables();
		initDerivedVariables();
	}
	public static void initMatchVariables() throws IOException
	{
		ArrayList<String> list = FileReader.readList(Paths.matchVariableList);
		matchVariables = new MatchVariable[list.size()];
		for(int i = 0;i<list.size();i++)
			matchVariables[i] = new MatchVariable(list.get(i),i);
	}
	public static void initDerivedVariables()throws IOException{
		ArrayList<String> list = FileReader.readList(Paths.derivedVariableList);
		derivedVariables = new DerivedVariable[list.size()];
		for(int i = 0;i<list.size();i++)
			derivedVariables[i] = new DerivedVariable(list.get(i),i);
	}
}
