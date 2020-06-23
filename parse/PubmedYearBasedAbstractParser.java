import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.InputSource;

public class PubmedYearBasedAbstractParser {

	public static final int START_LIB = 700;
	public static final int END_LIB = 928;
	public static int curr_lib = START_LIB;

	public static void main(String argv[]) {
		while (curr_lib <= END_LIB) {	
			try {
				Map<String,PrintWriter> writers = new HashMap<>();

				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();

				DefaultHandler handler = new DefaultHandler() {
				
					boolean inAbstract = false;
					boolean inPubdate = false;
					boolean inYear = false;
					boolean inMedlineYear = false;
				
					String abstractText =null;
					String year = null;
				

					public void startElement(String uri, String localName, String qName, Attributes attributes)
							throws SAXException {
					
						if (qName.equals("AbstractText")){
							inAbstract=true;
						}
						if (qName.equals("PubDate")){
							inPubdate=true;
						}
						if (inPubdate && qName.equals("Year")){
							inYear=true;
						}
					
						if (inPubdate && qName.equals("MedlineDate")){
							inYear=true;
						}
					
						if (qName.equals("PubmedArticle")){
						
						}
					
					//PubDate
					//got start
					//System.out.println("Start:" + qName);

					}

					public void endElement(String uri, String localName, String qName) throws SAXException {
						if (qName.equals("AbstractText")){
							inAbstract=false;
						}
						if (qName.equals("PubDate")){
							inPubdate=false;
						}
						if (qName.equals("Year")){
							inYear=false;
						}
					
						if (qName.equals("MedlineDate")){
							inMedlineYear=false;
						}
					
						if (qName.equals("PubmedArticle")){
							if(abstractText != null){
								writers.computeIfAbsent(year, (k)->{
									try {
										return new PrintWriter(new FileOutputStream("pubmed_" + year + ".txt", true));
									} catch (FileNotFoundException e) {
										throw new RuntimeException(e);
									}
								}).println(abstractText);
							}
							year = null;
							abstractText=null;
						}
						//got end
						//System.out.println("End:" + qName);
					}

					public void characters(char ch[], int start, int length) throws SAXException {

						if (inYear){
							year = new String(ch, start, length);
						}
					
						if (year == null && inMedlineYear){
							year = (new String(ch, start, length)).split(" ")[0];
						}
					
						//content
						if (inAbstract){
						abstractText = new String(ch, start, length);
						}

					}

				};
				String fname = "files/pubmed18n"+(String.format("%04d", curr_lib)) + ".xml";
				FileInputStream fis = new FileInputStream(fname);
				InputSource is = new InputSource(fis);
				saxParser.parse(is, handler);
				System.out.println(fname);
				writers.values()
			       		.stream()
			       		.forEach(w->{
			    	   	w.close();
			       		});

			} catch (Exception e) {
				e.printStackTrace();
			}
			curr_lib++;
		}
	}

}
