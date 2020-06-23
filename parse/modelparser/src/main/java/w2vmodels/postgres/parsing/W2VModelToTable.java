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
 * shamefully written by nicholas kibbey -> nicholas.kibbey@nih.gov
 * @TODO write loggers for debugging and tests if you wanna be like that...
 */
public class W2VModelToTable {
    public static final String DB_NAME = "w2vdb";
    public static final String MODEL_PATH = "../../models/";
    public enum OPTIONS {BASH, CSV}

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
            //Class.forName(DB_DRIVER);
            c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            c.setAutoCommit(true);
        } /*catch (ClassNotFoundException e) {
            System.err.println("class not found");
        }*/ catch (SQLException e) {
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

    /**
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
                        if (i == 0)
                            term = lst[i];
                        else
                            embedding[i-1] = Float.parseFloat(lst[i]);
                    }
                    pstmt.setString(1, term);
                    pstmt.setString(2, partition);
                    pstmt.setString(3, source);
                    pstmt.setArray(4, dbConnection.createArrayOf("float4", embedding));
                    pstmt.addBatch();
                }
                count++;
            }
            pstmt.executeBatch();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("prepared statement gone wild");
        } catch (FileNotFoundException e) {
            System.err.println("el file no existe boludo");
        } catch (IOException e) {
            System.err.println("problema de io cuando se usa readline");
        }

    }


    /**
     * helper method to print
     * @param fw file writer for output
     * @param toPrint text to be outputted
     * @throws RuntimeException because no bueno si no se funciona...
     */
    private static void writeToFile(FileWriter fw, String toPrint) {
        try {
            fw.write(String.format("%s%n", toPrint));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * takes list of word2vec and writes a bash program to insert all elements given
     * CREATE TABLE terms (
     *   tid serial PRIMARY KEY,
     *   term VARCHAR(50) NOT NULL,
     *   partition VARCHAR(50) NOT NULL,
     *   source VARCHAR(32),
     *   embedding REAL [] NOT NULL
     * );
     * @param models are list of filenames already inside @MODEL_PATH & well formed word2vec models of consistent style
     * @param source identifying source for db for all models
     * @param outName name of file to be outputted
     * @param options added so we can either bash insert insert insert or csv format
     *                bash currently works when adjusted so insert statements do one set of values at a time or <100
     */
    public static void w2vModelstoTable(List<String> models, String source, String outName, OPTIONS options) {
        int errorSpot = 0;

        try {
            /* initial read in */
            BufferedReader brInitial = new BufferedReader(new FileReader(MODEL_PATH +""+models.get(0)));
            String text = brInitial.readLine(); // just get first line to format all

            System.out.println(text);
            final int features = Integer.parseInt(text.split(" ")[1]);// get num features for model
            brInitial.close();

            errorSpot = 1;

            /* batching through all models given */
            for (int i=0; i<models.size(); i++) { // no for each because I want to keep track of start middle end
                final FileWriter fw;
                String partition = models.get(i);
                Stream<String> currFullLines = Files.lines(Paths.get( MODEL_PATH + "" + partition)); // get

               if (i == 0) { //bash and csv both write to files for data
                    fw = new FileWriter(outName, false);
                    String headr = "";
                    if (options == OPTIONS.BASH) {
                        headr = "#!/usr/bin/env bash \n";
                    } else if (options == OPTIONS.CSV) {

                    }
                    writeToFile(fw, headr);
                } else {
                    fw = new FileWriter(outName, true);
                }

                currFullLines.forEach((l) -> {//in short i'm bad at streams but this reformats all the trained values into vectors for @DB_NAME
                    String[] currLine = l.split(" ");
                    if (currLine.length == features+1) {
                        String addVal = "";
                        if (options == OPTIONS.BASH) {
                            addVal = "psql -d " + DB_NAME + " -c \"INSERT INTO terms(term, partition, source, embedding) VALUES\n";
                            for (int j = 0; j < currLine.length; j++) {
                                if (j == 0) {
                                    addVal += "('" + currLine[0] + "', '" + partition + "', '" + source + "', '{";
                                } else if (j < currLine.length - 1) {
                                    addVal += currLine[j] + ", ";
                                } else {
                                    addVal += currLine[j] + "}');\" ";//will leave final line with extra ',' -> reason for placeholder
                                }
                            }
                        } else if (options == OPTIONS.CSV) {

                        }
                        writeToFile(fw, addVal);
                    }
                });


                if (i == models.size()-1) { // after all values and final model
                    String endTerm = "('TMP', 'SORRY', 'HARDCODE', '{}' );\"\n"; // placeholder
                    writeToFile(fw, endTerm);
                }
                fw.close();
                currFullLines.close();

            }
        } catch (IOException e) {
            if (errorSpot == 0)
                System.err.println("Couldn't open first model with well formatted w2v header " + e);
            else
                System.err.println("error in reading through models" + e);
        }
    }

    public static void main(String[] args) {
        List<String> models = new ArrayList<>();

        models.add("partialclean_2017");
        batchPreparedW2VDB(models, "pubmed2018");

    }

}