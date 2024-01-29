package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.UserModel;

public class UserDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/user?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "Thuan0101#";
	
	private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (name, email, country) VALUES "
			+ " (?, ?, ?);";

	private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id =?";
	private static final String SELECT_ALL_USERS = "select * from users";
	private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
	private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ?, country =? where id = ?;";
	
	public UserDAO() {
		super();
	}
	
	protected Connection getConnection () {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public void insert(UserModel userModel) throws SQLException {
		System.out.println(INSERT_USERS_SQL);
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
			preparedStatement.setString(1, userModel.getName());
			preparedStatement.setString(2, userModel.getEmail());
			preparedStatement.setString(3, userModel.getCountry());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update (UserModel userModel) {
		System.out.println(UPDATE_USERS_SQL);
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL);
			preparedStatement.setString(1, userModel.getName());
			preparedStatement.setString(2, userModel.getEmail());
			preparedStatement.setString(3, userModel.getCountry());
			preparedStatement.setInt(4, userModel.getId());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete (int id) {
		System.out.println(DELETE_USERS_SQL);
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL);
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<UserModel> selectAllUsers () {
		System.out.println(SELECT_ALL_USERS);
		List<UserModel> list = new ArrayList<UserModel>();
		try {
			// Step 1: Establishing a Connection
			Connection connection = getConnection();
			// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();
			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				list.add(new UserModel(id, name, email, country));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public UserModel selectUser (int id) {
		System.out.println(SELECT_USER_BY_ID);
		UserModel user = null;
		try {
			// Step 1: Establishing a Connection
			Connection connection = getConnection();
			// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();
			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				user = new UserModel(id, name, email, country);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
}
