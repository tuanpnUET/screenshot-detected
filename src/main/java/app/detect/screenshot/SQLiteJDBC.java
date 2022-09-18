package app.detect.screenshot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLiteJDBC {

	public static void creatDB() {
		Connection connection = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:detectedscreen.db");

			stmt = connection.createStatement();
			String sql = "CREATE TABLE items " + "(id INTEGER PRIMARY KEY  AUTOINCREMENT," + " val1 INTEGER NOT NULL, "
					+ " val2 INTEGER NOT NULL, " + " timedown  INTEGER NOT NULL, " + " win  VARCHAR(10) NOT NULL)";
			stmt.executeUpdate(sql);
			stmt.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}

	public static List<Item> getAll() {
		Connection c = null;
		Statement stmt = null;
		List<Item> items = new ArrayList<Item>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:detectedscreen.db");
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM items;");

			while (rs.next()) {
				int id = rs.getInt("id");
				int val1 = rs.getInt("val1");
				int val2 = rs.getInt("val2");
				int timedown = rs.getInt("timedown");
				String win = rs.getString("win");
				Item item = new Item(id, val1, val2, timedown, win);
				items.add(item);
			}
			rs.close();
			stmt.close();
			c.close();

			System.out.println("items: " + items);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return items;

	}

	public static Item getById(int ID) {
		Connection c = null;
		Statement stmt = null;
		Item item = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:detectedscreen.db");
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM items where id = " + ID + ";");

			while (rs.next()) {
				int id = rs.getInt("id");
				int val1 = rs.getInt("val1");
				int val2 = rs.getInt("val2");
				int timedown = rs.getInt("timedown");
				String win = rs.getString("win");
				item = new Item(id, val1, val2, timedown, win);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return item;
	}

	public static void insert(Item item) {
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:detectedscreen.db");
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "INSERT INTO items (val1,val2,timedown,win) " + "VALUES " + item.toInsertData() + ";";

			stmt.executeUpdate(sql);

			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	public static void deleteAllData() {
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:detectedscreen.db");
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "DELETE from items";
			stmt.executeUpdate(sql);
			c.commit();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	public static void main(String args[]) {
//		SQLiteJDBC.insert(new Item(4,2,3,"white"));
// 		SQLiteJDBC.deleteAllData();
		SQLiteJDBC.getAll();
//		SQLiteJDBC.getById(19);
	}
}