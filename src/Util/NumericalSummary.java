package Util;

import java.util.StringTokenizer;

public class NumericalSummary {
	public double mean,standardDev,range;
	public String teamNum;
	public int matchesPlayed;
	public NumericalSummary(String num,String s,int matchesPlayed){
		StringTokenizer st = new StringTokenizer(s,"/");
		mean = Double.parseDouble(st.nextToken());
		standardDev = Double.parseDouble(st.nextToken());
		range = Double.parseDouble(st.nextToken());
		teamNum = num;
		this.matchesPlayed = matchesPlayed;
	}
}
