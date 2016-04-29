package assignment1;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Arrays;

/* ACADEMIC INTEGRITY STATEMENT
 * 
 * By submitting this file, we state that all group members associated
 * with the assignment understand the meaning and consequences of cheating,
 * plagiarism and other academic offenses under the Code of Student Conduct
 * and Disciplinary Procedures (see www.mcgill.ca/students/srr for more information).
 *
 * By submitting this assignment, we state that the members of the group
 * associated with this assignment claim exclusive credit as the authors of the
 * content of the file (except for the solution skeleton provided).
 *
 * In particular, this means that no part of the solution originates from:
 * - anyone not in the assignment group
 * - Internet resources of any kind.
 *
 * This assignment is subject to inspection by plagiarism detection software.
 *
 * Evidence of plagiarism will be forwarded to the Faculty of Science's disciplinary
 * officer.
 */

/**
 * When you run the main method, the program should print the name of the professor
 * that is the best match for the keywords described in the QUERY array using two similarity
 * metrics: the Jaccard index and the relative number of keyword matches. If no result is found,
 * the program should print out: "No result found".
 *
 * 
 */
public class Assignment1
{
	/**
	 * List of professors to search. Complete the list with all professors in the school
	 * of computer science. Choose the page that has the best description of the professor's
	 * research interests.
	 */
	private static Professor[] PROFESSORS = {
            new Professor("Martin Robillard", "http://www.cs.mcgill.ca/~martin"),
            new Professor("Gregory Dudek", "http://www.cim.mcgill.ca/~dudek/dudek_bio.html"),
            new Professor("Yang Cai", "http://www.cs.mcgill.ca/~cai/"),
            new Professor("Brigitte Pientka", "http://www.cs.mcgill.ca/~bpientka/"),

            new Professor("Gregory Dudek", "http://www.cim.mcgill.ca/~dudek/"),
            new Professor("David Avis", "http://cgm.cs.mcgill.ca/~avis/"),
            new Professor("Yang Cai", "http://www.cs.mcgill.ca/~cai/"),
            new Professor("Jackie Chi Kit Cheung", "http://www.cs.mcgill.ca/people/www.cs.mcgill.ca/~jcheung"),
            new Professor("Luc Devroye", "http://luc.devroye.org/"),
            new Professor("Wenbo He", "http://www.cs.mcgill.ca/~wenbohe/"),
            new Professor("Bettina Kemme", "http://www.cs.mcgill.ca/~kemme/"),
            new Professor("Paul Kry", "http://www.cs.mcgill.ca/~kry/"),
            new Professor("Xue Liu", "http://www.cs.mcgill.ca/~xueliu/"),
            new Professor("Prakash Panangaden", "http://www.cs.mcgill.ca/~prakash/"),
            new Professor("Joelle Pineau", "http://www.cs.mcgill.ca/~jpineau/"),
            new Professor("Bruce Reed", "http://cgm.cs.mcgill.ca/~breed/"),
//            new Professor("Derek Ruths", "http://www.ruthsresearch.org/"),
            new Professor("Denis Therien", "http://www.cs.mcgill.ca/~denis/"),
            new Professor("Hans Vangheluwe", "http://msdl.cs.mcgill.ca/people/hv"),
            new Professor("Adrian Vetta", "http://www.math.mcgill.ca/~vetta/"),
//            new Professor("Mathieu Blanchette", "http://www.mcb.mcgill.ca/~blanchem/"),
            new Professor("Xiao-Wen Chang", "http://www.cs.mcgill.ca/~chang/"),
            new Professor("Claude Crepeau", "http://www.cs.mcgill.ca/~crepeau/"),
//            new Professor("Nathan Friedman", "http://www.cs.mcgill.ca/~nathan/"),
            new Professor("Hamed Hatami", "http://www.cs.mcgill.ca/~hatami/"),
            new Professor("Laurie Hendren", "http://www.sable.mcgill.ca/~hendren/"),
            new Professor("Jörg Kienzle", "http://www.cs.mcgill.ca/~joerg/Home/Jorgs_Home.html"),
            new Professor("Michael Langer", "http://www.cim.mcgill.ca/~langer/"),
            new Professor("Muthucumaru Maheswaran", "http://www.cs.mcgill.ca/~maheswar/"),
            new Professor("Brigitte Pientka", "http://www.cs.mcgill.ca/~bpientka/"),
            new Professor("Doina Precup", "http://www.cs.mcgill.ca/~dprecup/"),
            new Professor("Martin Robillard", "http://www.cs.mcgill.ca/~martin/"),
            new Professor("Kaleem Siddiqi", "http://www.cim.mcgill.ca/~siddiqi/"),
            new Professor("Clark Verbrugge", "http://www.sable.mcgill.ca/~clump/"),
            new Professor("Jerome Waldispuhl", "http://www.cs.mcgill.ca/~jeromew/"),
            new Professor("Joseph Vybihal", "http://www.cs.mcgill.ca/~jvybihal/"),
            new Professor("Ioannis Rekleitis", "http://www.cim.mcgill.ca/~yiannis/"),
            new Professor("Louigi Addario-Berry", "http://www.math.mcgill.ca/louigi/"),
            new Professor("Daniel Levitin", "http://www.psych.mcgill.ca/faculty/levitin.html"),
            new Professor("Thomas Shultz", "http://www.tomshultz.net/"),
            new Professor("Jackie Vogel", "http://www.cs.mcgill.ca/people/faculty/profile?uid=jvogel"),
            new Professor("Guillaume Bourque", "http://genomequebec.mcgill.ca/gbourque/"),
            new Professor("Dirk Schlimm", "http://www.cs.mcgill.ca/~dirk/"),
            new Professor("Bruce Shepherd", "http://www.math.mcgill.ca/~bshepherd/"),
            new Professor("Renée Sieber", "http://www.geog.mcgill.ca/faculty/sieber/"),
            new Professor("Tim Merrett", "http://www.cs.mcgill.ca/~tim/"),
            new Professor("Monty Newborn", "http://www.cs.mcgill.ca/~newborn/"),
            new Professor("Gerald Ratzer", "http://www.cs.mcgill.ca/~ratzer/"),
//            new Professor("Renato De Mori", "http://www.researchgate.net/profile/Renato_De_Mori/"),
            new Professor("Chris Paige", "http://www.cs.mcgill.ca/~chris/"),
            new Professor("Godfried Toussaint", "http://www-cgrl.cs.mcgill.ca/~godfried/")
    };
	
	/**
	 * A set of keywords describing an area of interest. Does not have to be sorted, 
	 * but must not contain any duplicates.
	 */
	private static String[] QUERY = {"programming", "engineering", "design", "computer", "McGill"};
	
	/**
	 * Words with low information content that we want to exclude from the similarity
	 * measure.
	 * 
	 * This array should always be sorted. Don't change anything for the assignment submissions,
	 * but afterwards if you want to keep playing with this code, there are some other words
	 * that would obviously be worth adding.
	 */
	private static final  String[] STOP_WORDS = {"a", "an", "and", "at", "by", "for", "from", "he", "his",
		"in", "is", "it", "my", "of", "on", "she", "the", "this", "to", "with" };

	/**
	 * Your program starts here. You should not need to do anything here besides
	 * removing the first two statements once you have copied the required statement
	 * and dealing with the case where there are no results.
	 */
	public static void main(String[] args) throws IOException
	{
        System.out.println("Fetching web content");

        // for all profs
        // fetch content and parse to string array
        // remove stop words and store in prof instance
        for (Professor aProf : PROFESSORS){
            aProf.setWebContent(removeStopWords(obtainWordsFromPage(aProf.getWebPageUrl())));
        }

        System.out.println("Content fetched");
        Arrays.sort(QUERY);
    	String[] queryWithoutStopWords = removeStopWords(QUERY);
		System.out.println("Jaccard: " + bestMatchJaccard(queryWithoutStopWords));
		System.out.println("Relhits: " + bestMatchRelHits(queryWithoutStopWords));

	}
	
	/**
     * Returns the name of the professor that is the best match according to
     * the Jaccard similarity index, or the empty String if there are no such
     * professors. pQuery must not include duplicate or stop
     * words, and must be sorted before being passed into this function.
     *
     * @param pQuery query arguments to search for
	 */
	public static String bestMatchJaccard(String[] pQuery) throws IOException
	{
		assert pQuery != null;
        double [] hits = new double[PROFESSORS.length];
        int maxHitIndex = 0;

        for (int i = 0; i<PROFESSORS.length; i++){
            hits[i] = jaccardIndex(PROFESSORS[i].getWebContent(), pQuery);
            maxHitIndex = (hits[i] > hits[maxHitIndex]) ? i : maxHitIndex ;
        }

        if (hits[maxHitIndex] == 0)
            return "No Results Found";

        return PROFESSORS[maxHitIndex].getName();

	}
	
	/**
	 * Returns the size of the intersection between pDocument and pQuery.
	 * pDocument can contain duplicates, pQuery cannot. Both arrays must 
	 * be sorted in alphabetical order before being passed into this function.
     * @param pDocument the document to count hits from
     * @param pQuery query arguments to search for
	 */
	public static int intersectionSize(String[] pDocument, String[] pQuery)
	{
		assert pDocument != null && pQuery != null;
        String [] intersection = new String[Math.min(pDocument.length, pQuery.length)];

        int i = 0, j = 0, k = 0;

        while(i < pDocument.length && j < pQuery.length){
            int diff = pDocument[i].compareTo(pQuery[j]);
            if (diff == 0){
                //elements are the same
                intersection[k]=pDocument[i];
                i++ ;
                j++ ;
                k++;
            }
            else if (diff > 0){
                j++;
            }
            else {
                i++;
            }
        }

        int count = 0;
        i=0;
        while (i < intersection.length && intersection[i] != null ){
            count++;
            i++;
        }
        return count;
	}
	
	/**
     * Returns the name of the professor that is the best match according to
     * the RelHits (relative hits) similarity index, computed as numberOfHits/size of the document.
     * Returns the empty string if no professor is found.
     * pQuery must not include duplicate or stop words, and must be sorted before
     * being passed into this function.
     *
     * @param pQuery query arguments to search for
	 */
	public static String bestMatchRelHits(String[] pQuery) throws IOException
	{
        double [] hits = new double[PROFESSORS.length];
        int maxHitIndex = 0;
		for (int i = 0; i<PROFESSORS.length; i++){
            String [] content = PROFESSORS[i].getWebContent();
            hits[i] = numberOfHits(content, pQuery)/ (double) content.length;

            maxHitIndex = (hits[i] > hits[maxHitIndex]) ? i : maxHitIndex ;
        }

        if (hits[maxHitIndex] == 0)
            return "No Results Found";

        return PROFESSORS[maxHitIndex].getName();
    }
	
	/**
	 * Returns the Jaccard similarityIndex between pDocument and pQuery,
	 * that is, |intersection(pDocument,pQuery)|/|union(pDocument,pQuery)|
     *
     * @param pDocument the document to count hits from
     * @param pQuery query arguments to search for
	 */
	public static double jaccardIndex(String[] pDocument, String[] pQuery)
	{
		assert pDocument != null && pQuery != null;

        double intersection = intersectionSize(pDocument, pQuery);
        double union = unionSize(pDocument,pQuery);

        return intersection/union;

	}
	
	/**
	 * Returns the size of the union between pDocument and pQuery. 
	 * pDocument can contain duplicates, pQuery cannot. Both arrays must 
	 * be sorted in alphabetical order before being passed into this 
	 * function.
	 */
	public static int unionSize(String[] pDocument, String[] pQuery)
	{
		assert pDocument != null && pQuery != null;

        String[] combined = mergeSort(pDocument, pQuery);

        int size = 0;
        for (int l = 0; l<combined.length; l++){
            if (l==0)
                size ++;
            else if (!combined[l].equals(combined[l-1]))
                size++;
        }
        return size;
	}


    /**
     * Given two arrays of strings, this will return an array containing
     * the sorted values of the combined arrays
     * @param first first array to merge
     * @param second second array to merge
     * @return the merge and sorted combination of the arrays
     */
    public static String[] mergeSort(String[] first, String[] second){
        String[] combined = new String[first.length + second.length];
        int i = 0, j = 0, k = 0;
        while ( i < first.length || j < second.length){


            if (j==second.length ){
                //reached the end of query, add rest of pDoc
                combined[k] = first[i];
                i++;
            }
            else if (i==first.length ) {
                //reached the end of document, add rest of query
                combined[k] = second[j];
                j++;
            }
            //compareTo used to determine if string should be before or after
            else if (first[i].compareTo(second[j]) <= 0) {
                //item1<item2 in which case item1 should be the next element in the array
                combined[k] = first[i];
                i++;
            }
            else {
                // in which case item2 should be next in the array
                combined[k] = second[j];
                j++;
            }
            k++;
        }
        return combined;
    }


    /**
     * Returns the number of times that any word in pQuery is found in pDocument
     * for any word, and including repetitions. For example, if pQuery contains
     * "design" and "design" is found 3 times in pDocument, this would return 3.
     * Both pDocument and pQuery should be sorted in alphabetical order before
     * being passed into this function.
     * @param pDocument the document to count hits from
     * @param pQuery query arguments to search for
     */
    public static int numberOfHits(String[] pDocument, String[] pQuery)
    {
        assert pDocument != null && pQuery != null;
        int count = 0;
        for (String aPDocument : pDocument) {
            int index = Arrays.binarySearch(pQuery, aPDocument);

            if (! (index < 0) ) {
                count++;
            }

        }
        return count;
    }
	
	/**
	 * Returns a new array of words that contains all the words in pKeyWords
	 * that are not in the array of stop words. The order of the original 
	 * array should not be modified except by removing words. If the array is sorted,
	 * the resulting array should also be sorted.
	 * @param pKeyWords The array to trim from stop words
	 * @return A new array without the stop words.
	 */
	public static String[] removeStopWords(String[] pKeyWords)
	{
		assert pKeyWords != null;
        String [] newArray = new String[pKeyWords.length];
        int indexNew=0;
        //all elements in pKeyWords that cannot be found in STOP_WORDS are copied in a newArray
        for (String pKeyWord : pKeyWords) {
            int index = Arrays.binarySearch(STOP_WORDS, pKeyWord);
            if (index < 0) {
                newArray[indexNew] = pKeyWord;
                indexNew++;
            }
        }
        //at this point, indexNew is the length of populated cells and newArray contains only relevant words.

		return Arrays.copyOfRange(newArray, 0, indexNew);
	}
	
	/**
	 * Obtains all the words in a page (including duplicates), but excluding punctuation and
	 * extraneous whitespaces and tabs. The results should be sorted in alphabetical order
	 * and all be completely in lower case.
	 * Consider using String.replaceAll(...) to complete this method.
	 * @throws IOException if we can't download the page (e.g., you're off-line)
     * @param pUrl url of the web page to fetch and parse
	 */
	public static String[] obtainWordsFromPage(String pUrl) throws IOException
	{
		String inputString = Jsoup.connect(pUrl).get().text();

        //strip of non alpha numerical characters, multiple spaces and set all lowercase
        inputString = inputString.replaceAll("([^\\w\\s])", " ");
        inputString = inputString.replaceAll("([ ]+)", " ");
        inputString = inputString.toLowerCase();

        String [] inArray = inputString.split("([ ])");
        Arrays.sort(inArray);

        return inArray;
	}
}

/**
 * This simple class just keeps the information about
 * a professor together. Do not modify this class.
 */
class Professor
{
	private String aName;
	private String aWebPageUrl;
    private String [] aWebContent;
	
	public Professor(String pName, String pWebPageUrl)
	{
		aName = pName;
		aWebPageUrl = pWebPageUrl;
    }
	
	public String getName()
	{
		return aName;
	}
	
	public String getWebPageUrl()
	{
		return aWebPageUrl;
	}

    public void setWebContent(String[] aWebContent) {
        this.aWebContent = aWebContent;
    }

    public String[] getWebContent() {
        return aWebContent;
    }
}
