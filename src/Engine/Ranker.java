package Engine;
import Util.BooleanSummary;
import Util.NumericalSummary;
public class Ranker {
	public static NumericalSummary[] rank(NumericalSummary[] arr){
		for(int i = 0;i<arr.length;i++){
			for(int j = 0;j<arr.length-1;j++){
				if(arr[j].mean < arr[j+1].mean){
					NumericalSummary temp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = temp;
				}
			}
		}
		return arr;
	}
	public static BooleanSummary[] rank(BooleanSummary[] arr){
		for(int i = 0;i<arr.length;i++){
			for(int j = 0;j<arr.length-1;j++){
				if(arr[j].success < arr[j+1].success){
					BooleanSummary temp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = temp;
				}
			}
		}
		return arr;
	}
}
