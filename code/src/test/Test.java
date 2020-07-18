package test;


import java.util.Date;
import javax.persistence.Persistence;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zhangshuting
 */
public class Test {
    static JpaController controller=new JpaController(Persistence.createEntityManagerFactory("TestPU"));
    public static void main(String[] args) throws Exception {
		create("Irene","hello");
		//read("Irene");
		//update("Irene", "test", new Date(100, 1, 1));
		//delete("Irene");
	}
	private static void create(String username, String password) throws Exception {
		User user = new User(username);
		user.setPassword(password);
		controller.create(user);		
	}
	private static void read(String username) {
		User user = controller.findUser(username);
		System.out.println(user + " " + user.getPassword());
	}
	private static void update(String username, String password, Date date) throws Exception {
		User customer = controller.findUser(username);
		customer.setPassword(password);
		customer.setEntry(date);
		controller.edit(customer);
	}

	private static void delete(String username) throws NonexistentEntityException, JpaController.NonexistentEntityException {
		controller.destroy(username);
	}
        public class NonexistentEntityException extends Exception {
            public NonexistentEntityException(String message, Throwable cause) {
                super(message, cause);
            }
            public NonexistentEntityException(String message) {
                super(message);
            }
        }
}

    /*String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    String database = "jdbc:derby://localhost:1527/test";
    String username = "shuting";
    String password = "0000";
    String querySQL = "select * from entrydata";
    String insertSQL = "INSERT INTO entrydata(USERNAME,PASSWORD,ENTRYDATE) VALUES ('Daisy', 'testtest','2017-01-01')";
    Connection connection;
    
    public Test() throws FileNotFoundException, ClassNotFoundException, SQLException {
        Class.forName(driver); 
        connection = DriverManager.getConnection(database, username, password);
  //      testInsert();
///        testInsert();
        testPreparedStatement();
        this.connection.close();
    }
    private void testInsert() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(insertSQL);
            
       
        statement.close();

    }
     private void testPreparedStatement() throws SQLException, FileNotFoundException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO entrydata(username,password,entrydate) VALUES (?, ?, ?)");
        ps.setString(1, "Sophia");
        ps.setString(2, "testtest");
        ps.setTimestamp(3, new Timestamp(100, 11,28,0,0,0,0));

        System.out.println("exec");
        ps.execute();
//        connection.commit();

    }
    
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, SQLException {
        new Test();
    } 
}*/
