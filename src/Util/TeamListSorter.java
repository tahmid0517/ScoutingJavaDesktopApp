package Util;

import java.util.ArrayList;

public class TeamListSorter {
	public static String[] sort(ArrayList<String> list){
		String[] arr = new String[list.size()];
		for(int i = 0;i<list.size();i++){
			arr[i] = list.get(i);
		}
		for(int i = 0;i<arr.length;i++){
			for(int j = 0;j<arr.length-1;j++){
				String[] subSet = getMinAndMax(arr[j],arr[j+1]);
				arr[j] = subSet[0];
				arr[j+1] = subSet[1];
			}
		}
		return arr;
	}
	public static String[] getMinAndMax(String a,String b){
		String[] nameA = separateNumAndLetter(a);
		String[] nameB = separateNumAndLetter(b);
		int numA = Integer.parseInt(nameA[0]);
		int numB = Integer.parseInt(nameB[0]);
		if(numA<numB){
			return new String[]{a,b};
		}
		else if(numB<numA){
			return new String[]{b,a};
		}
		else if(numA==numB){
			if(nameA[1].equals("")){
				return new String[]{a,b};
			}
			else if(nameB[1].equals("")){
				return new String[]{b,a};
			}
			else{
				int charAVal = (int)nameA[1].charAt(0);
				int charBVal = (int)nameB[1].charAt(0);
				if(charAVal<=charBVal){
					return new String[]{a,b};
				}
				else if(charBVal<charAVal){
					return new String[]{b,a};
				}
			}
		}
		return new String[]{a,b};
	}
	public static String[] separateNumAndLetter(String s){
		String num;
		String letter = "";
		char lastDigit = s.charAt(s.length()-1);
		if(lastDigit>64 && lastDigit<91){
			letter = ""+lastDigit;
		}
		num = s.replaceAll(letter,"");
		return new String[]{num,letter};
	}
}
