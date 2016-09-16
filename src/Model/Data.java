package Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Data {

	public static String getData() {
		return new SimpleDateFormat("dd/MMMM/yyyy").format(new Date()).toString();
	}
	
	public static String getHora(){
		return new SimpleDateFormat("HH:mm").format(new Date()).toString();
	}
	
	public static String getAno(){
		return new SimpleDateFormat("yyyy").format(new Date()).toString();
	}
}
