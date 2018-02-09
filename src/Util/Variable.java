package Util;

import java.util.StringTokenizer;

public class Variable {
	public static final int typeBoolean = 0;
	public static final int typeNumerical = 1;
	public String name;
	public int type, index;
	public Variable(String name, int type, int index){
		this.name = name;
		this.type = type;
		this.index = index;
	}
	public Variable(String nameAndType, int index){
		StringTokenizer st = new StringTokenizer(nameAndType,"/");
		this.name = st.nextToken();
		this.type = Integer.parseInt(st.nextToken());
		this.index = index;
	}
}
