package co.scndfndtion.diagmas.diagnostic;

import jade.core.AID;

import java.util.Hashtable;
import java.util.Set;

public class Diagnosticator {
	public static String analyze(Hashtable<AID,String> answerList) {
		System.out.println("--->Analyzing ");
		String result=null;
		if(answerList!=null ){
			
			if(answerList.size()>0 && answerList.keySet()!=null){
			Set<AID> keys = answerList.keySet();
			for(AID key: keys){
				System.out.println("Value of "+key+" is: "+ answerList.get(key));
				result += "Value of "+key+" is: "+ answerList.get(key) + " \n";
			}
			}
		}else{
			System.out.println("null answerlist");
		}
		System.out.println("result:"+result);
		
		return result;
	}
}
