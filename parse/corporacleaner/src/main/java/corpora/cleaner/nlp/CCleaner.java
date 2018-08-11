package corpora.cleaner.nlp;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * ccleaner is to clean corpora that's already formatted a lil... ejemplo: I have the partitions in concated files
 * that are how I like it already toLowerCase and I just want them to not be so
 * skeleton code from standfordcorenlp since you know... i'm importing that stuff already
 * @author kibbey
 */
public class CCleaner {

    protected StanfordCoreNLP pipeline;
    private static final String fprefix ="../../../corpora/";
    //batch of files that I have that I know I want to clean and they're not nicely spaced so list that thanng
    private static final String[] files = {"y1975to1984.txt", "y1985to1990.txt", "y1991to1995.txt",
                                    "y1996to1999.txt", "y2000to2003.txt", "y2004to2006.txt",
                                    "y2007to2008.txt", "y2009to2010.txt", "y2011to2012.txt",
                                    "y2013to2014.txt"};


    public CCleaner() {
        // Create StanfordCoreNLP object properties, with POS tagging
        // (required for lemmatization), and lemmatization
        Properties props;
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");

        // StanfordCoreNLP loads a lot of models, so you probably
        // only want to do this once per execution
        this.pipeline = new StanfordCoreNLP(props);
    }

    /**
     * provided by standford nlp -> takes a string produces a list of tokens according to that lemma dilemma
     * @param documentText pretty obvious what it is
     */
    public List<String> lemmatize(String documentText)
    {
        List<String> lemmas = new LinkedList<String>();

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(documentText);

        // run all Annotators on this text
        this.pipeline.annotate(document);

        // Iterate over all of the sentences found
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            // Iterate over all tokens in a sentence
            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // Retrieve and add the lemma for each word into the list of lemmas
                lemmas.add(token.get(CoreAnnotations.LemmaAnnotation.class));
            }
        }

        return lemmas;
    }

    /**
     * helper so I can print out a list of lemmas to given file
     * @param lemmas them word tokens
     * @param out that printer pass in
     */
    private static synchronized void toFile(List<String> lemmas, PrintWriter out) {
        lemmas.stream()
              .filter(l->!Pattern.matches("\\p{Punct}", l.substring(0,1))) //verbose filter
              .forEach(l->out.print(l+" "));
    }

    /**
     * sorry coding practices I'm wrapping all responsibilities into one file
     * @param args see HelloWorld.java
     */
    public static void main(String[] args) {
        CCleaner myCleaner = new CCleaner();

        for (int i=2; i<files.length; i++) { //for all files
            String currFile = files[i];

            try (  PrintWriter out = new PrintWriter(new FileOutputStream("lem_"+currFile, true))) { // append so watch out for duplicates)
                System.out.println("accessing all lines of "+currFile);
                Files.lines(Paths.get(fprefix+""+currFile), StandardCharsets.UTF_8)
                       .parallel()
                        .forEach(ln->{
                            List<String> myRes = myCleaner.lemmatize(ln);
                            myRes.add("\n");//end of that line
                            toFile(myRes, out);
                        });
            } catch (IOException e) {
                System.err.println("ahhhh io " +e.getMessage());
            }
            System.out.println("done with "+currFile);
        }
    }

}
