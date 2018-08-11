package pairwise.diseases;

public class LemFileNames {
    private static LemFileNames ourInstance = new LemFileNames();
    public final String[] files = {"lem_m_1975_1984", "lem_m_1985_1990", "lem_m_1991_1995", "lem_m_1996_1999",
                                    "lem_m_2000_2003", "lem_m_2004_2006", "lem_m_2007_2008", "lem_m_2009_2010",
                                    "lem_m_2011_2012", "lem_m_2013_2014"};

    public static LemFileNames getInstance() {
        return ourInstance;
    }

    private LemFileNames() {
    }
}
