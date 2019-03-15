package mg.runner;

import java.sql.SQLException;

import mg.com.server;
import mg.ui.enterance;

public class Runner {

	public static void main(String[] args) {
		new enterance().setVisible(true);
		new enterance().setVisible(true);
		try {
			new server(5000);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
