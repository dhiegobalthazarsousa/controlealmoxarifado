package View;

import Controler.REST;
import Model.Model;

public class Main {
	public static void main(String[] args) {
		
		Model model = new Model();
		REST controller = new REST(model);
		controller.makeRoute();
	}
}
