package Model;

import java.text.DecimalFormat;

public class Formata {
	public static String formatOut(double pVal){
		DecimalFormat format = new DecimalFormat("0.00");
		return format.format(pVal);
	}
}
