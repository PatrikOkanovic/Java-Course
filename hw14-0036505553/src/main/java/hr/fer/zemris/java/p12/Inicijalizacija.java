package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.p12.dao.sql.model.Poll;
import hr.fer.zemris.java.p12.dao.sql.model.PollOption;
/**
 * {@link ServletContextListener} used to create tables Polls and PollOPtions and fill them with data
 * from the definition texts in the homework, but only if the tables do not exist
 * or are empty. 
 * Also creates connection with the database for the user and password read from
 * dbsettings.properties
 * 
 * @author Patrik Okanovic
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {
	
	/**
	 * Used for getting real path
	 */
	private static ServletContext con;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		con = sce.getServletContext();
		
		String fileName = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(fileName));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		String dbName=prop.getProperty("name");
		String connectionURL = "jdbc:derby://"+prop.getProperty("host")+":"+
				prop.getProperty("port")+"/" + dbName;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);
		cpds.setUser(prop.getProperty("user"));
		cpds.setPassword(prop.getProperty("password"));
		cpds.setInitialPoolSize(5);
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		
		Connection currentConnection = null;
		try {
			currentConnection = cpds.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			createDatabases(currentConnection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			fillDatabase(currentConnection);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	/**
	 * Fills the database Polls and then calls fillPoolOptions to fill PollOptions
	 * 
	 * @param connection {@link Connection}
	 * @throws IOException
	 * @throws SQLException
	 */
	private void fillDatabase(Connection connection) throws IOException, SQLException {
		List<Poll> polls = getPolls();
		PreparedStatement pst = null;
//		polls.forEach((p)->System.out.println(p.getMessage() + p.getTitle() + p.getDefinitionOfPollOptions()));
		if(tableSize(connection,"Polls") < polls.size()) {
//			System.out.println("PROSLOOOO");
			for(Poll poll : polls) {
				pst = connection.prepareStatement("INSERT INTO Polls (title, message) values (?,?)",
						Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, poll.getTitle());
				pst.setString(2, poll.getMessage());
				
				int numberOfAffectedRows = pst.executeUpdate();//must be 1
				if(numberOfAffectedRows != 1) {
					throw new RuntimeException("Number of affected rows must be one");
					
				}
				
				ResultSet rset = pst.getGeneratedKeys();
				
				try {
					if(rset != null && rset.next()) {
						long newID = rset.getLong(1);
						List<PollOption> pollOptions = getPollOptions(poll.getDefinitionOfPollOptions());
						fillPoollOptions(connection,newID,pollOptions);
					}
				} finally {
					try { rset.close(); } catch(SQLException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		
		if(tableSize(connection,"PollOptions") == 0) {
			//this means something is wrong with tables, drop everything and restart application
			throw new RuntimeException("PollOptions cannot be empty, drop everything and restart application");
			
		}
		
	}

	/**
	 * Returns the size of the given table, moreover returns
	 * number of rows in a table.
	 * 
	 * @param connection
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	private int tableSize(Connection connection, String tableName) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rset = null;
		
		try {
			pst = connection.prepareStatement("SELECT COUNT(*) AS NUM FROM "+tableName);
			
			rset = pst.executeQuery();
			rset.next();
			int num = rset.getInt("NUM");
			return num;
		} finally {
			try { 
				rset.close(); 
			} catch(Exception ignorable) {}
			try { 
				pst.close(); 
			} catch(Exception ignorable) {}
		}
	}

	/**
	 * Fills pollOptions with the given data.
	 * 
	 * @param connection {@link Connection}
	 * @param newID ID of the pollID
	 * @param pollOptions list of {@link PollOption}
	 */
	private void fillPoollOptions(Connection connection, long newID, List<PollOption> pollOptions) {
		PreparedStatement pst = null;
//		System.out.println(pollOptions);
		try {
			for(PollOption pollOption : pollOptions) {
				pst = connection.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)");
				pst.setString(1, pollOption.getOptionTitle());
				pst.setString(2, pollOption.getOptionLink());
				pst.setLong(3,newID);
				pst.setLong(4,pollOption.getVotesCount());
				
				pst.executeUpdate();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Reads {@link PollOption} from the text file given with the path title.
	 * 
	 * @param title path of the file
	 * @return
	 * @throws IOException
	 */
	private List<PollOption> getPollOptions(String title) throws IOException {
		String fileName = con.getRealPath(title);
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		List<PollOption> pollOptions = new ArrayList<>();
		
		for(String line : lines) {
			String[] lineSplitted = line.split("\t");
			pollOptions.add(new PollOption(-1,lineSplitted[0],lineSplitted[1],0,-1));//id will be changed later, generated by the database
			
		}
		return pollOptions;
	}

	/**
	 * Reads the polls definition and returns a list of them from a text file.
	 * 
	 * @return
	 * @throws IOException
	 */
	private List<Poll> getPolls() throws IOException {
		String fileName = con.getRealPath("/WEB-INF/polls-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		List<Poll> polls = new ArrayList<>();
		
		for(String line : lines) {
			String[] lineSplitted = line.split("\t");
			polls.add(new Poll(-1,lineSplitted[0],lineSplitted[1],lineSplitted[2]));//id will be changed later
			
		}
		return polls;
	}

	

	/**
	 * Used to create tables Polls and PollOptions only if they
	 * do not already exist.
	 * 
	 * @param connection
	 * @throws SQLException
	 */
	private void createDatabases(Connection connection) throws SQLException {
		PreparedStatement pst = null;
		
		try {
			pst = connection.prepareStatement("CREATE TABLE Polls " + 
					" (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " + 
					" title VARCHAR(150) NOT NULL, " + 
					" message CLOB(2048) NOT NULL" + 
					")");
			pst.execute();
		} catch(SQLException e) {
			if( e.getSQLState().equals("X0Y32")) {
		        return; // That's OK
		    }
		    throw e;
		} finally {
			try { 
				pst.close();
			} catch(Exception ignorable) {}
		}
		
		try {
			pst = connection.prepareStatement("CREATE TABLE PollOptions " + 
					" (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " + 
					" optionTitle VARCHAR(100) NOT NULL, " + 
					" optionLink VARCHAR(150) NOT NULL, " + 
					" pollID BIGINT, " + 
					" votesCount BIGINT, " + 
					" FOREIGN KEY (pollID) REFERENCES Polls(id) " + 
					")");
			pst.execute();
		} catch(SQLException e) {
			if( e.getSQLState().equals("X0Y32")) {
		        return; // That's OK
		    }
		    throw e;
		} finally {
			try { 
				pst.close(); 
			} catch(Exception ignorable) {}
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}