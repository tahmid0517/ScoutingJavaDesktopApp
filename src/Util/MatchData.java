package Util;

import Engine.VariableManager;

public class MatchData {
	public String name;
	public double[] matchVariables, derivedVariables;
	int c;
	String note;
	public MatchData(String name){
		this.name = name;
		matchVariables = new double[VariableManager.matchVariables.length];
		derivedVariables = new double[VariableManager.derivedVariables.length];
		c = 0;
		note = "";
	}
	public void addMatchVariable(double d){
		matchVariables[c] = d;
		c++;
	}
	public void createDerivedVariables(){
		for(int i = 0;i<derivedVariables.length;i++){
			derivedVariables[i] = dealWithBrackets(VariableManager.derivedVariables[i].formula);
		}
	}
	public double getDerivedValue(String formula){
		char[] arr = formula.toCharArray();
		String s = "";
		int i;
		for(i = 0;!isOperator(arr[i]);i++){
			s+=arr[i];
		}
		double orig = getValue(s);
		while(i<arr.length){
			char operator = arr[i];
			i++;
			String j = "";
			for(;i<arr.length&&!isOperator(arr[i]);i++){
				j+=arr[i];
			}
			orig = operate(operator,orig,getValue(j));
		}
		return orig;
	}
	public double dealWithBrackets(String formula){
		if(!formula.contains("(")){
			return getDerivedValue(formula);
		}
		int i;
		for(i = 0;formula.charAt(i)!='(';i++){}
		String replaceThis = "(";
		String replaceWith = "";
		for(int j = i+1;formula.charAt(j)!=')';j++){
			replaceThis+=formula.charAt(j);
			replaceWith+=formula.charAt(j);
		}
		replaceThis+=")";
		formula = formula.replace(replaceThis,String.valueOf(getDerivedValue(String.valueOf(replaceWith))));
		return dealWithBrackets(formula);
	}
	public double getValue(String s){
		char c = s.charAt(0);
		if((int)c>96 && (int)c<173){
			return matchVariables[(int)c - 97];
		}
		return Double.parseDouble(s);
	}
	public double operate(char c,double a,double b){
		if(c=='*'){
			return a*b;
		}
		if(c=='/'){
			return a/b;
		}
		if(c=='+'){
			return a+b;
		}
		if(c=='-'){
			return a-b;
		}
		return 0;
	}
	public boolean isOperator(char c){
		if(c=='*'){
			return true;
		}
		if(c=='+'){
			return true;
		}
		if(c=='/'){
			return true;
		}
		if(c=='-'){
			return true;
		}
		return false;
	}
	public void addNote(String note){
		this.note = note;
	}
}
