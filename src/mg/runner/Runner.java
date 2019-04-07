package mg.runner;

import java.sql.SQLException;

import mg.com.server;

public class Runner {

	public static void main(String[] args) {
		try {
			new server(5100);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
