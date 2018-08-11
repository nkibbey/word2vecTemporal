package pairwise.diseases;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class PairHistory implements Comparable<PairHistory>{
    private TermVectorHistory term1;
    private TermVectorHistory term2;
    private SimpleRegression myMeta;

    public PairHistory(TermVectorHistory term1, TermVectorHistory term2) {
        this.term1 = term1;
        this.term2 = term2;
        myMeta = term1.getSimpleRegression(term2);
    }

    public SimpleRegression getMyMeta() {
        return myMeta;
    }

    public String[] getPairName() { return new String[]{term1.getTerm(), term2.getTerm()}; }

    public double getSlope() { return myMeta.getSlope(); }

    @Override
    public String toString() {
        return term1.getTerm()+"\t"+term2.getTerm()+"\t"+myMeta.getSlope();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PairHistory) {
            PairHistory pobj = (PairHistory) obj;
            boolean sharest1 = term1.equals(pobj.term1) || term1.equals(pobj.term2);
            boolean sharest2 = term2.equals(pobj.term1) || term2.equals(pobj.term2);
            return sharest1 && sharest2;//instead of a million parenthesis
        }
        return false;
    }

    @Override
    public int compareTo(PairHistory pairHistory) {
        Double c1 = myMeta.getSlope();
        Double c2 = pairHistory.myMeta.getSlope();
        return c1.compareTo(c2);
    }
}
