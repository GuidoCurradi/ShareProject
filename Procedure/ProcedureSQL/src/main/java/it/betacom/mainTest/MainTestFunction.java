package it.betacom.mainTest;

import java.util.List;

import it.betacom.richiamiProcedure.DBFunction;

public class MainTestFunction {
	
	public static void main (String[] args) {
		DBFunction dbf = new DBFunction();
		List<String> resultList = dbf.getNazione("Italia");
	      for (String result : resultList) {
	          System.out.print(result + " | ");
	       }
	}

}
