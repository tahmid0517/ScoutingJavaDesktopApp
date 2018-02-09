package Util;

import java.util.StringTokenizer;

public class DerivedVariable extends Variable {
	public String formula;
	public DerivedVariable(String name, int type, int index, String formula){
		super(name,type,index);
		this.formula = formula;
	}
	public DerivedVariable(String nameTypeAndFormula, int index){
		super("a/2",-1);
		StringTokenizer st = new StringTokenizer(nameTypeAndFormula,"/");
		super.name = st.nextToken();
		super.type = Integer.parseInt(st.nextToken());
		this.formula = st.nextToken();
		super.index = index;
	}
}
