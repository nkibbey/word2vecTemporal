package pairwise.diseases;

//import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * TLDR; I got terms and a db, I want pairwise cosine history for all terms that I can w/said list -> have fun
 *
 * there will be some hypocrisy in this project and it pertains to the following...
 * I am building something with the principles of flexibility, loose coupling, etc
 * But the whole reason I made this project was to simply get the pairwise cosine similarity between
 * all terms in list A that can be computed because I have their embeddings over their partitions in mypsqldb
 * which in itself is very specific so if you want to implement more than pairwising...
 * I suggest using half of this class as a driver class and the other half for a specific pairwising method
 * @author nick kibbey
 */
public class Pairwiser {

    private DBStuff mydb;

    /**
     * makes pairwiser object but doesn't add anything to the good terms
     */
    public Pairwiser() {
        mydb = DBStuff.getInstance();
    }

    private void endDB() {
        mydb.endConnection();
    }

    /**
     * I got my disease list from trung (thanks) and now I want that stuff in the form you might find it in my db
     * some implementation specific stuff here
     * @param fname refers to file that my terms are from
     * @return set of terms from given file
     */
    public Set<String> retrieveMyTerms(String fname) {
        Lemmatizer myLem = new Lemmatizer();
        try {
            return Files.lines(Paths.get(fname), StandardCharsets.UTF_8)
                    .parallel()
                    .map(ln->ln.split("\t")[0])
                    .map(ln->myLem.lemmatize(ln))
                    .flatMap(ln->ln.stream())
                    .collect(Collectors.toSet());

        } catch (IOException e) {
            System.err.println("problem reading "+fname+"\n retrieve terms stopped..."+e);
        }
        return new HashSet<>();
    }

    /**
     * method to refine set of terms so we only have terms that the db has info on
     * @param myTerms describes terms of a given list
     * @param ps describes partitions
     * @param source describes source category
     * @return subset of myTerms characterized by also belonging to dbTerms set
     */
    public Set<String> refineMyTerms(Set<String> myTerms, Set<String> ps, String source) {
        return myTerms.stream()
                .parallel()
                .filter(term-> termExistsInAllPartitionsInDatabase(term, ps, source).get())
                .collect(Collectors.toSet());
    }

    /**
     * supplier helper to let refine terms parallelize but main thing is if it's in our data set across desired
     * partitions then we say thats a good term
     * @param term kinda obvious
     * @param partitions kinda obvious
     * @param source kinda obvious -- these are the names in the w2vdb as well
     * @return true if it's available false if doesn't meet conditions
     */
    private Supplier<Boolean> termExistsInAllPartitionsInDatabase(String term, Set<String> partitions, String source){
        return ()-> {
            Connection conn = mydb.getConnection();
            try {

                PreparedStatement stmt = conn.prepareStatement("select term, partition from terms where term = ? and source = ?");
                stmt.setString(1, term);
                stmt.setString(2, source);
                ResultSet rs = stmt.executeQuery();

                int count =0;
                Set<String> gotPartitions = new HashSet<>();
                while (rs.next()) {
                    gotPartitions.add(rs.getString("partition"));
                }
                return gotPartitions.containsAll(partitions) && partitions.containsAll(gotPartitions);
            } catch (SQLException e) {
                System.err.print("refine -> stmt error" + e);
            }
            return false;
        };
    }

    /**
     * So I want pairwise history between the two and that currently computes to: where them cosines at tho?
     * @param terms all terms that these histories come from
     * @param partitions all partitions for said history
     * @param source source for these partitions to come from
     * @return hopefully i implement a my printable object scenario where all is mejor for this pair history obj
     */
    public Set<PairHistory> retrieveHistories(Set<String> terms, Set<String> partitions, String source) {
        Set<TermVectorHistory> tvhs = new HashSet<>();
        Set<PairHistory> pairs = new TreeSet<>();

        for (String term: terms) {
            tvhs.add(termHistorify(term, partitions, source));
        }

        TermVectorHistory uno = new TermVectorHistory("gaucher!!!!"); // one pair

        for (TermVectorHistory finder: tvhs) {
            if (finder.getTerm().equals("gaucher")) {
                uno = finder;
                break;
            }
        }

        for (TermVectorHistory dos: tvhs) {
            pairs.add(new PairHistory(uno, dos));
        }

        return pairs;
    }


    /**
     * start making objects that are the history in java memory for that string term into something with stuff that's
     * usable
     * @param term string to be queried
     * @param partitions all partitions that compose history
     * @param source source for which all of this is under
     * @return a new term vector history that has all info set from embeddings labeled by partition
     */
    private TermVectorHistory termHistorify(String term, Set<String> partitions, String source){
        TermVectorHistory tvh = new TermVectorHistory(term);
        Connection conn = mydb.getConnection();
        try {

            PreparedStatement stmt = conn.prepareStatement("select term, partition, embedding from terms where term = ? and source = ?");
            stmt.setString(1, term);
            stmt.setString(2, source);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) { // add on the fly the given info on a term into an object we can use
                Array arr= rs.getArray("embedding");
                Float[] arr1=(Float[])(arr.getArray());
                tvh.addPartition(rs.getString("partition"),arr1);
            }
            return tvh; // now has all the info
        } catch (SQLException e) {
            System.err.print("refine -> stmt error" + e);
        }
        return null; // shouldn't happen
    }

    private static Map<String[], Double> top5(Map<String[], Double> currTops,
                                                         String[] nTerms, Double nVal ) {
        currTops.put(nTerms, nVal);

        Double lVal = 0.0;
        String[] lKey = null;

        for (Map.Entry<String[], Double> e: currTops.entrySet()) {
            if (Math.abs(e.getValue()) < Math.abs(lVal)) {
                lVal = e.getValue();
                lKey = e.getKey();
            }
        }

        if (currTops.size() > 5) {
            currTops.remove(lKey);
        }

        return currTops;
    }
    /**
     * method to handle how info is printed; currently I only am looking at info between a pair of terms
     * @param ps describe the pairs that can be printed
     */
    private static void printInfo(Set<PairHistory> ps) {
        if (ps != null) {
            ps.stream()
                    .filter(s->s.getMyMeta().getRSquare()>.3)
                    .sorted(Comparator.comparing(s->(Math.abs(s.getSlope()) * -1)))
                    .limit(25)
                    .forEach(r->{
                        String[] names = r.getPairName();
                        System.out.println(names[0] + "\t" +names[1] + "\t" + r.getSlope());
                    });
            /*
            double h = 0;
            double l = 0;
            Map<String[], Double> hTops = new HashMap<>();
            Map<String[], Double> lTops = new HashMap<>();

            for (PairHistory p : ps) {
                double s = p.getSlope();

                if (s>h) {
                    h=s;
                    hTops = top5(hTops, p.getPairName(), h);
                } else if (s<l) {
                    l=s;
                    lTops = top5(lTops, p.getPairName(), l);
                }
            }
            for (Map.Entry<String[], Double> entry : hTops.entrySet()) {
                System.out.println(entry.getKey()[0]+"\t"+entry.getKey()[1]+"\t"+entry.getValue());
            } for (Map.Entry<String[], Double> entry : lTops.entrySet()) {
                System.out.println(entry.getKey()[0]+"\t"+entry.getKey()[1]+"\t"+entry.getValue());
            }*/
        }
    }

    /**
     * helper to print out a list of lemmas to given file
     * @param lemmas them word tokens
     * @param out that printer pass in
     */
    private static synchronized void toFile(List<String> lemmas, PrintWriter out) {
        lemmas.stream()
              .filter(l->!Pattern.matches("\\p{Punct}", l.substring(0,1))) //verbose filter
              .forEach(l->out.print(l+" "));
    }

    /**
     * sorry coding practices I'm wrapping multiple responsibilities into one file
     * @param args see HelloWorld.java
     */
    public static void main(String[] args) {
        //----------------INIT-works
        Pairwiser myPairwiser = new Pairwiser();
        String diseaseFile = "betterDiseases.txt";
        String source = "pubmed2018";
        LemFileNames lfn = LemFileNames.getInstance();
        Set<String> partitionNames = Arrays.stream(lfn.files).collect(Collectors.toSet());
        Set<String> partitions = Arrays.stream(lfn.files).collect(Collectors.toSet());

        //----------------PARSE DISEASE LIST-works
        Set<String> myTerms = myPairwiser.retrieveMyTerms(diseaseFile);
        System.out.println("Got "+myTerms.size()+" terms\ndoing refine");
      
        //----------------REDUCE TO USABLE TERMS-works
        myTerms = myPairwiser.refineMyTerms(myTerms, partitionNames, source);
        System.out.println("After refine\nGot "+myTerms.size()+" terms");

        //----------------GET INFO-works
        Set<PairHistory> pairInfo = myPairwiser.retrieveHistories(myTerms, partitions, source);

        //----------------USE INFO-playing with options
        printInfo(pairInfo);

        //----------------DONE
        myPairwiser.endDB();
    }

}
