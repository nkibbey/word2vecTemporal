package w2vmodels.postgres.parsing;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * class provides functions for word2vec vectors into a postgres database, implementation specific detailed in methods.
 * previous add to db done through insert statements but prepared are better faster stronger
 * written by nicholas kibbey -> nicholas.kibbey@nih.gov
 * @TODO write loggers for debugging and tests if you wanna be like that...
 */
public class awsW2VModelToTable {
    public static final String DB_NAME = "w2vdb";
    public static final String MODEL_PATH = "../../../models/";
 //   public enum OPTIONS {BASH, CSV}

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/"+ DB_NAME;
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_USER = "maninsert";
    private static final String DB_PASSWORD = "insert";


    /**
     * sets a connection to class defined access of db ; auto commit explicitly on
     * @return new connection
     */
    private static Connection getConnection() throws RuntimeException {
        Connection c = null;
        try {
            Class.forName(DB_DRIVER);
            c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            c.setAutoCommit(true);
        } catch (ClassNotFoundException e) {
            System.err.println("class not found");
        } catch (SQLException e) {
            System.err.println("sql exception in connecting");
        }
        if (c != null)
            System.out.println("Opened database successfully");
        else {
            System.err.println("dead connection");
            throw new RuntimeException("sad boys");
        }
        return c;
    }

    /**
     * send off a list of files to be inserted directly into the db with prepared statements in batch per file
     * @param models list of models that describe filename within predefined path of model_path
     * @param source what source do these models come from?
     */
    public static void batchPreparedW2VDB(List<String> models, String source) {
        Connection myConnection = getConnection();
        for (String m: models) {
            dbPrepareInsert(myConnection, MODEL_PATH + "" + m, m, source);
        }
        try {
            myConnection.close();
        } catch (SQLException e) {
            System.err.println("i can't close the connection :(");
        }
    }


        private static void dumbTest() {
	        try (Connection c = getConnection()) {
	            PreparedStatement pstmt = c.prepareStatement("INSERT INTO test(c1, c2) VALUES (?, ?)");
	            pstmt.setString(1,"wow");
	            pstmt.setString(2, "hi");
	            pstmt.executeUpdate();
	            pstmt.close();
		} catch (SQLException e) {
	            System.err.println("we failed again dumbtest"+e);
		}
	}

    /** NOTE: ROLE MUST BE ABLE TO ACCESS TABLE & ASSOC SERIAL TABLE IF APPLICABLE
     *  assumes table is already created and is helper function to take values from one model into table
     *  CREATE TABLE terms (
     *   tid serial PRIMARY KEY,
     *   term VARCHAR(50) NOT NULL,
     *   partition VARCHAR(50) NOT NULL,
     *   source VARCHAR(32),
     *   embedding REAL [] NOT NULL
     *  );
     * @param dbConnection because just want to connect as little times as possible
     * @param fname file name describes path to read in file
     * @param partition describes the partition label db
     * @param source describes sources label in db
     */
    private static void dbPrepareInsert(Connection dbConnection, String fname, String partition, String source) throws RuntimeException {
        try {
            PreparedStatement pstmt =  dbConnection.prepareStatement("INSERT INTO terms(term, partition, source, embedding) " +
                    "VALUES (?, ?, ?, ?)");
	    BufferedReader br = new BufferedReader(new FileReader(fname));
            String readLine;
            int count = 0;
            int features = -1;

            while ((readLine = br.readLine()) != null) {//read in w2v; first line is line num feature num, rest are vals
                if (count == 0) {
                    features = Integer.parseInt(readLine.split(" ")[1]);// get num features for model
                } else {
                    String[] lst = readLine.split(" ");

                    if (lst.length != features+1) // shouldn't happen since line is 1 term + features
                        throw new RuntimeException("poorly formatted w2v in prepare insert");

                    Float[] embedding = new Float[features];
                    String term = "HOUSTON_WE_HAVE_A_PROBLEM";

                    for (int i=0; i < lst.length; i++) {
                        if (i == 0) {
		           if (lst[i].length() >= 50)
                  	   	term = lst[i].substring(0,49);
			   else
				term = lst[i];
			} else {
                            embedding[i-1] = Float.parseFloat(lst[i]);
			}
		    }
		    System.out.println("attemptin to insert " +term+" " + partition + " source " + embedding[0]);
		    pstmt.setString(1, term);
                    pstmt.setString(2, partition);
                    pstmt.setString(3, source);
                    pstmt.setArray(4, dbConnection.createArrayOf("float4", embedding));
//		    pstmt.executeUpdate();
                    pstmt.addBatch();
                }
                count++;
            }
	    pstmt.executeBatch();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("prepared statement gone wild\n"+e);
        } catch (FileNotFoundException e) {
            System.err.println("el file no existe boludo\n"+e);
        } catch (IOException e) {
            System.err.println("problema de io cuando se usa readline\n"+e);
        }

    }

    public static void main(String[] args) {
        List<String> models = new ArrayList<>();
        String[] files = {"lem_m_1975_1984", "lem_m_1991_1995", "lem_m_2000_2003", "lem_m_2007_2008", "lem_m_2011_2012", "lem_m_1985_1990", "lem_m_1996_1999", "lem_m_2004_2006", "lem_m_2009_2010",  "lem_m_2013_2014"};
        for (int i=0; i<files.length; i++)
            models.add(files[i]);
        batchPreparedW2VDB(models, "pubmed2018");
//	dumbTest();
    }

}
