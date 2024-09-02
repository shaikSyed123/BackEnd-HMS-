package com.tsarit.form_1.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.tsarit.form_1.model.Appointment;
import com.tsarit.form_1.model.userData;
import com.tsarit.form_1.repository.AppointmentRepository;
import com.tsarit.form_1.repository.userRepository;

@Service
public class AppointmentService {
	
	
	 private String loggedInUserEmail;

	    public void storeLoggedInUserEmail(String emailid) {
	        this.loggedInUserEmail = emailid;
	    }

	    public String getLoggedInUserEmail() {
	    	System.out.println("loggedInUserEmail is :"+loggedInUserEmail);
	        return this.loggedInUserEmail;
	        
	    }

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private userRepository userRepository;

	public List<Appointment> getAllAppointments() {
		return appointmentRepository.findAll();
	}
	
	 public void updateLastLoginDate() {
	 String username = getLoggedInUserEmail();
	        userData user = userRepository.findByemailid(username);
	        user.setLastLoginTimeDate(LocalDateTime.now());
	        userRepository.save(user);
	    }

	    public LocalDateTime getLastLoginDate() {
	    String username = getLoggedInUserEmail();
	    	userData user = userRepository.findByemailid(username);
	        return user.getLastLoginTimeDate();
	    }

	public String getLoggedInUsername() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			String username = getLoggedInUserEmail();
             System.out.println("username :"+username );
			// Sanitize the username to create a valid table name
			String sanitizedUsername = username.replaceAll("[^a-zA-Z0-9]", "_");
			String tableName = "user_" + sanitizedUsername;

			System.out.println("Sanitized Username: " + sanitizedUsername);
			System.out.println("Table Name: " + tableName);

			return tableName;
//		}
//		return null;
	}
	
	public userData data() { 
		String email = loggedInUserEmail; // Assuming 'emailid' is used as the principal
		System.out.println("Email retrieved from authentication: " + email);
	    
	    userData user = userRepository.findByemailid(email);  // Query based on email
	    if (user == null) {
	        System.out.println("No user found with email: " + email);
	    }
	    return user;
	}


	public void createUserTable(String username) {
		username = getLoggedInUsername();
		String tableName = username;

		String checkTableQuery = "SHOW TABLES LIKE ?";
		List<String> tables = jdbcTemplate.queryForList(checkTableQuery, new Object[] { tableName }, String.class);

		 if (tables.size() > 1) {
		        throw new IllegalStateException("Multiple tables found with the name: " + tableName);
		    }
		// If the table does not exist, create it
		if (tables.isEmpty()) {
			String createTableQuery = "CREATE TABLE " + tableName + " ("
				    + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
				    + "firstName VARCHAR(100) NOT NULL, "
				    + "lastName VARCHAR(100), "
				    + "email VARCHAR(100), "
				    + "phoneNumber VARCHAR(15), "
				    + "aadharNumber VARCHAR(20), "
				    + "address TEXT, "
				    + "gender VARCHAR(10), "
				    + "disease VARCHAR(255), "
				    + "otherDisease VARCHAR(255), "
				    + "day VARCHAR(20), "
				    + "month VARCHAR(20), "
				    + "year VARCHAR(20), "
				    + "age VARCHAR(20), "
				    + "modeOfPayment VARCHAR(50), "
				    + "amount DECIMAL(10,2), "
				    + "upiTransactionNo VARCHAR(100), "
				    + "netBankingTransactionId VARCHAR(100), "
				    + "netBankingScreenshot LONGBLOB, "
				    + "accountTransactionId VARCHAR(100), "
				    + "accountDocument LONGBLOB, "
				    + "reference VARCHAR(255), "
				    + "insurance VARCHAR(100), "
				    + "otherPayment VARCHAR(100), "
				    + "weight DECIMAL(5,2), "
				    + "bp VARCHAR(10), "
				    + "appointmentTaken VARCHAR(20), "
				    + "appointmentDetails TEXT, "
				    + "modeOfPatient VARCHAR(50), "
				    + "bedAssign VARCHAR(20), "
				    + "bedDetails TEXT, "
				    + "bedNo VARCHAR(10), "
				    + "bedDays VARCHAR(30), "
				    + "date DATE, "
				    + "time TIME"
				    + ")";

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			jdbcTemplate.execute(createTableQuery);
			System.out.println("Created table for user: " + tableName);
			
			 // Create the second table with a foreign key
        String createSecondTableQuery = "CREATE TABLE " + tableName + "_treatment ("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                + "patientTreatment TEXT, "
                + "prescription TEXT, "
                + "tabletName VARCHAR(255), "
                + "otherTabletName VARCHAR(255), "
                + "injection VARCHAR(255), "
                + "mg VARCHAR(255), "
                + "tabletCount VARCHAR(255), "
                + "tests TEXT, "
                + "doctorAdvice TEXT, "
                + "patientId BIGINT, "
                + "FOREIGN KEY (userId) REFERENCES " + tableName + "(id) ON DELETE CASCADE"
                + ")";

        jdbcTemplate.execute(createSecondTableQuery);
        System.out.println("Created treatment table for user: " + tableName);
		} else {
			System.out.println("Table already exists: " + tableName);
		}
	}

	public void saveUserData( Map<String, String> userData) {

	   String username = getLoggedInUsername();
	    String tableName = username;
	    System.out.println("Received username: " + username);

	    if (username == null) {
	        throw new IllegalArgumentException("Username cannot be null");
	    }

	    LocalDate currentDate = LocalDate.now();
	    LocalTime currentTime = LocalTime.now();

	    String insertQuery = "INSERT INTO " + tableName + " ("
	            + "firstName, lastName, email, phoneNumber, aadharNumber, address, gender, disease, otherDisease, day, month, year, age, modeOfPayment, amount, "
	            + "upiTransactionNo, netBankingTransactionId, netBankingScreenshot, accountTransactionId, accountDocument, reference, insurance, otherPayment, "
	            + "weight, bp, appointmentTaken, appointmentDetails, modeOfPatient, bedAssign, bedDetails, bedNo, bedDays, date, time"
	            + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

	    Object[] aparams = {
	            userData.get("firstName"), userData.get("lastName"), userData.get("email"), userData.get("phoneNumber"),
	            userData.get("aadharNumber"), userData.get("address"), userData.get("gender"), userData.get("disease"),
	            userData.get("otherDisease"), userData.get("day"), userData.get("month"), userData.get("year"),
	            userData.get("age"), userData.get("modeOfPayment"), userData.get("amount"), userData.get("upiTransactionNo"),
	            userData.get("netBankingTransactionId"), userData.get("netBankingScreenshot"), userData.get("accountTransactionId"),
	            userData.get("accountDocument"), userData.get("reference"), userData.get("insurance"), userData.get("otherPayment"),
	            userData.get("weight"), userData.get("bp"), userData.get("appointmentTaken"), userData.get("appointmentDetails"),
	            userData.get("modeOfPatient"), userData.get("bedAssign"), userData.get("bedDetails"), userData.get("bedNo"),
	            userData.get("bedDays"), currentDate, currentTime
	    };

	    // Debugging: Print the parameters array to check the values
	    System.out.println("Parameters length: " + aparams.length);
	    for (int i = 0; i < aparams.length; i++) {
	        System.out.println("Parameter " + (i+1) + ": " + aparams[i]);
	    }

      try {
	       jdbcTemplate.update(insertQuery, aparams);
	
         } catch (Exception e) {
	      // TODO: handle exception
	      e.printStackTrace();
          }
	    System.out.println("Data saved for table: " + tableName);
	}
	
	public void savetreatment( Map<String, String> userData) {
		
		String username=getLoggedInUsername()+"_treatment";
		 String sql = "INSERT INTO "+ username
		 		+ "( patientTreatment, prescription, tabletName, otherTabletName, injection, mg, tabletCount, tests, doctorAdvice, patientId) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		 Object[] aparams = {
		       userData.get("patientTreatment"),userData.get("prescription"),userData.get("tabletName"),userData.get("otherTabletName"),userData.get("injection"),
		       userData.get("mg"),userData.get("tabletCount"),userData.get("tests"),userData.get("doctorAdvice"),userData.get("patientId"),};
		 try {
		       jdbcTemplate.update(sql, aparams);
		
	         } catch (Exception e) {
		      // TODO: handle exception
		      e.printStackTrace();
	          }
		    System.out.println("Data saved for table: " + username);
	}
	
	public List<Map<String, Object>> fetch(int id){
	   String username = getLoggedInUsername();
	   String fetchQuery = "SELECT * FROM "+ username +" WHERE id = ?";
	   try {
			// Execute the query and return the results as a List of Maps
			return jdbcTemplate.queryForList(fetchQuery,id);
		} catch (Exception e) {
			e.printStackTrace(); // Print stack trace for debugging
			throw new RuntimeException("Error fetching data from table " + username, e);
		}
	}
	public userData fetch(){
		   String username = getLoggedInUserEmail();
		    return  userRepository.findByemailid(username);
		     
		}
	public userData fetch2(String email){
//		String username = getLoggedInUserEmail();
		return  userRepository.findByemailid(email);
		
	}
	

	// Method to fetch data from a specific user's table
	public List<Map<String, Object>> fetchData(String username
//			String keyword
			) {

		username = getLoggedInUsername();
		String tableName = username;
		System.out.println("Received username: " + username);

		// Construct the SQL query to fetch data from the user's table
		String fetchQuery = "SELECT * FROM " + tableName;

		String query = "SELECT * FROM " + tableName + " WHERE firstName LIKE ? " + "OR lastName LIKE ? "
				+ "OR CAST(id AS CHAR) LIKE ? " + "OR CAST(age AS CHAR) LIKE ? " + "OR CAST(weight AS CHAR) LIKE ? "
				+ "OR CAST(phonenumber AS CHAR) LIKE ? " + "OR email LIKE ? " + "OR CAST(date AS CHAR) LIKE ? "
				+ "OR CAST(time AS CHAR) LIKE ?";
//		if (keyword != null) {
//			try {
//				String keywordPattern = "%" + keyword + "%";
//				return jdbcTemplate.queryForList(query, keywordPattern, keywordPattern, keywordPattern, keywordPattern,
//						keywordPattern, keywordPattern, keywordPattern, keywordPattern, keywordPattern);
//			} catch (Exception e) {
//				e.printStackTrace(); // Print stack trace for debugging
//				throw new RuntimeException("Error fetching data from table " + tableName, e);
//			}
//		} else {
			try {
				// Execute the query and return the results as a List of Maps
				return jdbcTemplate.queryForList(fetchQuery);
			} catch (Exception e) {
				e.printStackTrace(); // Print stack trace for debugging
				throw new RuntimeException("Error fetching data from table " + tableName, e);
			}
//		}

	}

	public void delete( int id) {
		String username = getLoggedInUsername();
		System.out.println("Received username: " + username);
		String query = "DELETE FROM " + username + " WHERE id = ?";
		try {
			// Execute the DELETE query
			int rowsAffected = jdbcTemplate.update(query, id);

			if (rowsAffected > 0) {
				System.out.println("Record with id " + id + " was successfully deleted from table " + username);
			} else {
				System.out.println("No record found with id " + id + " in table " + username);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error executing delete operation for table " + username, e);
		}

	}

	public List<Map<String, Object>> getAppointmentsByDate(String username, LocalDate date) {
		username = getLoggedInUsername();
		String query = "SELECT * FROM " + username + " WHERE date = ?";
		return jdbcTemplate.queryForList(query, date);
	}

	public long countTodayAppointments(String username) {
		username =getLoggedInUsername();
		
		System.out.println("countTodayAppointments :"+username);
		System.out.println("Today pa :"+username);
		LocalDate today = LocalDate.now();
		String query = "SELECT COUNT(*) FROM " + username + " WHERE date = ?";
		return jdbcTemplate.queryForObject(query, Long.class, today);
	}

	public long TotalPatientCount(String username) {
		username = getLoggedInUsername();
		System.out.println("Total pa :"+username);
		String query = "SELECT COUNT(*) FROM " + username;
		return jdbcTemplate.queryForObject(query, Long.class);
	}
	public long TotalAmount(String username) {
	 username=getLoggedInUsername();
		String query="SELECT SUM(amount) FROM "+ username;
		return jdbcTemplate.queryForObject(query, Long.class);
	}
	public long TodayTotalAmount(String username) {
		 username=getLoggedInUsername();
		LocalDate today = LocalDate.now();
		String query="SELECT SUM(amount) FROM "+ username+" WHERE date = ?";
		 Long totalAmount = jdbcTemplate.queryForObject(query, Long.class, today);
		    return totalAmount != null ? totalAmount : 0; // Return 0 if the result is null
	}
	public List<Map<String, Object>> TotalPatient() {
		String username = getLoggedInUsername();
		System.out.println("Total pa :"+username);
		LocalDate today = LocalDate.now();
		String query = "SELECT * FROM " + username+" WHERE date = ?";
		return jdbcTemplate.queryForList(query,today);
	}
	public Map<String, Object> getMonthlyAndYearlyTotalAmount() {
	    String username = getLoggedInUsername();

	    // SQL query to get the total amount grouped by each month
	    String monthlyQuery =
	            "SELECT month, SUM(totalAmount) AS totalAmount " +
	            "FROM ( " +
	            "    SELECT DATE_FORMAT(date, '%Y-%m') AS month, SUM(amount) AS totalAmount " +
	            "    FROM `" + username + "` " +
	            "    GROUP BY DATE_FORMAT(date, '%Y-%m') " + // Group by year and month
	            ") AS subquery " +
	            "GROUP BY month " +
	            "ORDER BY STR_TO_DATE(month, '%Y-%m')";

	    // SQL query to get the total amount grouped by each year
	    String yearlyQuery =
	            "SELECT year, SUM(totalAmount) AS totalAmount " +
	            "FROM ( " +
	            "    SELECT DATE_FORMAT(date, '%Y') AS year, SUM(amount) AS totalAmount " +
	            "    FROM `" + username + "` " +
	            "    GROUP BY DATE_FORMAT(date, '%Y') " + // Group by year
	            ") AS subquery " +
	            "GROUP BY year " +
	            "ORDER BY year";

	    // Fetch the data using JdbcTemplate
	    List<Map<String, Object>> monthWiseData = jdbcTemplate.queryForList(monthlyQuery);
	    List<Map<String, Object>> yearWiseData = jdbcTemplate.queryForList(yearlyQuery);

	    // Format data to match the desired structure
	    List<Map<String, Object>> formattedMonthWise = new ArrayList<>();
	    for (Map<String, Object> record : monthWiseData) {
	        formattedMonthWise.add(Map.of(
	            "month", record.get("month"),
	            "totalAmount", record.get("totalAmount")
	        ));
	    }

	    List<Map<String, Object>> formattedYearWise = new ArrayList<>();
	    for (Map<String, Object> record : yearWiseData) {
	        formattedYearWise.add(Map.of(
	            "year", record.get("year"),
	            "amount", record.get("totalAmount")
	        ));
	    }

	    // Prepare the final response map
	    Map<String, Object> response = new HashMap<>();
	    response.put("monthWise", formattedMonthWise);
	    response.put("yearWise", formattedYearWise);

	    return response;
	}


	public Map<String, Object> getAppointmentById(String username, int id) {
		username = getLoggedInUsername();
		System.out.println("Finding appointment with ID: " + id);

		// Fetch the appointment by ID
		String query = "SELECT * FROM " + username + " WHERE id = ?";
		List<Map<String, Object>> result = jdbcTemplate.queryForList(query, id);

		if (result.isEmpty()) {
			throw new NoSuchElementException("Appointment not found with id " + id);
		}

		// Return the first (and presumably only) appointment
		return result.get(0);
	}

	public void updateAppointment(Map<String, String> userData,int id) {
		String username =getLoggedInUsername();
		System.out.println("Updating appointment with ID: "+id );
		
		String updateQuery1 =" UPDATE " + username 
        + " SET firstName = ?, lastName=?, email=?, phoneNumber=?, aadharNumber=?, address=?, gender=?, disease=?, otherDisease=?, day=?, month=?, year=?, age=?, modeOfPayment=?, amount=?, "
        + "upiTransactionNo=?, netBankingTransactionId=?, netBankingScreenshot=?, accountTransactionId=?, accountDocument=?, reference=?, insurance=?, otherPayment=?, "
        + "weight=?, bp=?, appointmentTaken=?, appointmentDetails=?, modeOfPatient=?, bedAssign=?, bedDetails=?, bedNo=?, bedDays=?, date=?, time=?  WHERE id = ?";

		 LocalDate currentDate = LocalDate.now();
		    LocalTime currentTime = LocalTime.now();
		 Object[] aparams = {
		            userData.get("firstName"), userData.get("lastName"), userData.get("email"), userData.get("phoneNumber"),
		            userData.get("aadharNumber"), userData.get("address"), userData.get("gender"), userData.get("disease"),
		            userData.get("otherDisease"), userData.get("day"), userData.get("month"), userData.get("year"),
		            userData.get("age"), userData.get("modeOfPayment"), userData.get("amount"), userData.get("upiTransactionNo"),
		            userData.get("netBankingTransactionId"), userData.get("netBankingScreenshot"), userData.get("accountTransactionId"),
		            userData.get("accountDocument"), userData.get("reference"), userData.get("insurance"), userData.get("otherPayment"),
		            userData.get("weight"), userData.get("bp"), userData.get("appointmentTaken"), userData.get("appointmentDetails"),
		            userData.get("modeOfPatient"), userData.get("bedAssign"), userData.get("bedDetails"), userData.get("bedNo"),
		            userData.get("bedDays"), currentDate, currentTime,id
		    };
		jdbcTemplate.update(updateQuery1, aparams);
	}
	
	public void forgotPassword(String email,String password,String repetepassword) {
//		String email=getLoggedInUserEmail();
	    userData user=userRepository.findByemailid(email);

	    if (user == null ) {
	        throw new RuntimeException("Invalid or expired reset token.");
	    }
	    if (password == null ) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
	    user.setPassword(password);
	    user.setRepetepassword(repetepassword);
	    userRepository.save(user);
	}



}
