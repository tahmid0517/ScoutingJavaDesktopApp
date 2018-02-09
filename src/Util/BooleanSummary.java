package Util;
import java.util.StringTokenizer;
public class BooleanSummary {
	public int success, total;
	public String teamNum;
	public BooleanSummary(String num,String s){
		StringTokenizer st = new StringTokenizer(s,"/");
		success = Integer.parseInt(st.nextToken());
		total = Integer.parseInt(st.nextToken());
		teamNum = num;
	}
}
