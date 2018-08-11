package pairwise.diseases;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TermVectorHistory {

    private String term;
    private Map<String,Float[]> partitionHistories = new TreeMap<>();

    /**
     * initialized with name of term in question
     * @param term pretty obvious
     */
    public TermVectorHistory(String term){
        this.term=term;
    }

    //low key could be bad that I'm doing a getter that's shallow -> don't depend or change this fella
    private Map<String, Float[]> getPartitionHistories() {
        return partitionHistories;
    }


    public String getTerm() {
        return term;
    }

    /**
     * updates partition histories
     * @param name name of term
     * @param vec embedding for term
     * @return this object
     */
    public TermVectorHistory addPartition(String name, Float[] vec){
        this.partitionHistories.put(name,vec);
        return this;
    }

    /**
     * returns a nice ordered cosine history between the two elements
     * GIVEN THAT you can compare the partition names by string ascending and that they both have some num partitions
     * @param tvh other tvh to compare with
     * @return cosine history between the two
     */
    public Double[] getCosineHistory(TermVectorHistory tvh) {
        if (partitionHistories.size() != tvh.getPartitionHistories().size()) {
            throw new RuntimeException("can't create history with different number of fields");
        }

        Object[] raw = partitionHistories.keySet().stream()
                .sorted()//using a tree map we technically already should be sorted but i don't know
                .map(k-> getCosineForKeyAndOther(k, tvh.getPartitionHistories()))
                .toArray();

        Double[] history = new Double[partitionHistories.size()];

        for (int i=0; i<raw.length; i++)
            history[i] = Double.valueOf(raw[i].toString()); //not the world's greatest fix

        return history;
    }

    /**
     * I hate float but sql is sql unfortunately they get stored that way :(
     * @param currKey we use currkey because process must be in sorted order
     * @param theirPart the other term history partition piece
     * @return cosine similarity between two vectors
     */
    private Double getCosineForKeyAndOther(String currKey, Map<String, Float[]> theirPart) {
        Float[] arr1 = theirPart.get(currKey);
        Float[] arr2 = this.partitionHistories.get(currKey);

        double sim=0;
        double m1=0;
        double m2=0;

        for(int i=0;i<arr1.length;i++){
            sim+=arr1[i]*arr2[i];
            m1+=arr1[i]*arr1[i];
            m2+=arr2[i]*arr2[i];
        }

        m1=Math.sqrt(m1);
        m2=Math.sqrt(m2);

        return sim/(m1*m2);
    }

    public SimpleRegression getSimpleRegression(TermVectorHistory other) {
        SimpleRegression reg = new SimpleRegression();
        Double[] yList = getCosineHistory(other);

        for (int i=0; i<yList.length; i++) {
            reg.addData(i, yList[i]);
        }


        return reg;
    }

    /**
     * returns true when of the same class and shares same type of info in terms and partition names
     * @param obj other object
     * @return true of false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TermVectorHistory) {
            TermVectorHistory tobj = (TermVectorHistory) obj;
            if (term.equals(tobj.getTerm())) {
                Set<String> myParts = partitionHistories.keySet();
                Set<String> theirParts = tobj.getPartitionHistories().keySet();

                return (myParts.size() == theirParts.size() && myParts.containsAll(theirParts));
            }
        }

        return false;
    }

    /**
     * toString
     * @return term: [name] p: [partition] e-> [first embedding] ....
     */
    @Override
    public String toString(){
        String ans = "";
        ans+="Term: "+term;
        int counter = 0;
        for (String key: partitionHistories.keySet()) {
            ans += " p"+counter+": "+key+" e->"+partitionHistories.get(key)[0]+"\n";
            counter++;
        }
        return ans;
    }
}
