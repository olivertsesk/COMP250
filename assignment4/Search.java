package a4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



/* ACADEMIC INTEGRITY STATEMENT
 * 
 * Paste the block here.
 */



/** A Simple Search Engine exploring subnetwork of McGill University's webpages.
 * 	
 *	Complete the code provided as part of the assignment package.
 *  
 *  Do not change any of the function signatures. However, you can write additional helper functions 
 *  and test functions if you want.
 *  
 *  Do not define any new classes. Do not import any data structures. 
 *  
 *  Make sure your entire solution is in this file.
 *  
 *  We have simplified the task of exploring the network. Instead of doing the search online, we've
 *  saved the result of an hour of real-time graph traversal on the McGill network into two csv files.
 *  The first csv file "vertices.csv" contains the vertices (webpages) on the network and the second csv 
 *  file "edges.csv" contains the links between vertices. Note that the links are directed edges.
 *  
 *  An edge (v1,v2) is link from v1 to v2. It is NOT a link from v2 to v1.
 * 
 */

public class Search {

    private ArrayList<Vertex> graph;
    private ArrayList<Vertex> BFS_inspector;
    private ArrayList<Vertex> DFS_inspector;
    private Comparator<SearchResult> comparator = new WordOccurrenceComparator();
    private PriorityQueue<SearchResult> wordOccurrenceQueue;

    /**
     * You don't have to modify the constructor. It only initializes the graph
     * as an arraylist of Vertex objects
     */
    public Search(){
        graph = new ArrayList<Vertex>();
    }

    /**
     * Used to invoke the command line search interface. You only need to change
     * the 2 filepaths and toggle between "DFS" and "BFS" implementations.
     */
    public static void main(String[] args) {
        String pathToVertices = "vertices.csv";
        String pathToEdges = "edges.csv";

        Search mcgill_network = new Search();
        mcgill_network.loadGraph(pathToVertices, pathToEdges);

        Scanner scan = new Scanner(System.in);
        String keyword;

        do{
            System.out.println("\nEnter a keyword to search: ");
            keyword = scan.nextLine();

            if(keyword.compareToIgnoreCase("EXIT") != 0){
                mcgill_network.search(keyword, "BFS");		//You should be able to change between "BFS" and "DFS"
                mcgill_network.displaySearchResults();
            }

        } while(keyword.compareToIgnoreCase("EXIT") != 0);

        System.out.println("\n\nExiting Search...");
        scan.close();
    }

    /**
     * Do not change this method. You don't have to do anything here.
     * @return size of the graph
     */
    public int getGraphSize(){
        return this.graph.size();
    }

    /**
     * This method will either call the BFS or DFS algorithms to explore your graph and search for the
     * keyword specified. You do not have to implement anything here. Do not change the code.
     * @param pKeyword keyword to compare
     * @param pType search type
     */
    public void search(String pKeyword, String pType){
        resetVertexVisits();
        wordOccurrenceQueue = new PriorityQueue<SearchResult>(1000, comparator);
        BFS_inspector = new ArrayList<Vertex>();
        DFS_inspector = new ArrayList<Vertex>();

        if(pType.compareToIgnoreCase("BFS") == 0){
            Iterative_BFS(pKeyword);
        }
        else{
            Iterative_DFS(pKeyword);
        }
    }

    /**
     * This method is called when a new search will be performed. It resets the visited attribute
     * of all vertices in your graph. You do not need to do anything here.
     */
    public void resetVertexVisits(){
        for(Vertex k : graph){
            k.resetVisited();
        }
    }

    /**
     * Do not change the code of this method. This is used for testing purposes. It follows the
     * your graph search traversal track to ensure a BFS implementation is performed.
     * @return textual version of the BFS traversal order
     */
    public String getBFSInspector(){
        String result = "";
        for(Vertex k : BFS_inspector){
            result = result + "," + k.getURL();
        }

        return result;
    }

    /**
     * Do not change the code of this method. This is used for testing purposes. It follows the
     * your graph search traversal track to ensure a DFS implementation is performed.
     * @return textual version of the DFS traversal order
     */
    public String getDFSInspector(){
        String result = "";
        for(Vertex k : DFS_inspector){
            result = result + "," + k.getURL();
        }
        return result;
    }

    /**
     * This method prints the search results in order of most occurrences. It utilizes
     * a priority queue (wordOccurrenceQueue). You do not need to change the code.
     * @return the number of hits
     */
    public int displaySearchResults(){

        int count = 0;
        while(this.wordOccurrenceQueue.size() > 0){
            SearchResult r = this.wordOccurrenceQueue.remove();

            if(r.getOccurrence() > 0){
                System.out.println("Count: " + r.getOccurrence() + ", Page: " + r.getUrl());
                count++;
            }
        }

        if(count == 0) System.out.println("No results found for your search query");

        return count;

    }

    /**
     * This method returns the graph instance. You do not need to change the code.
     * @return the member graph
     */
    public ArrayList<Vertex> getGraph(){
        return this.graph;
    }

    /**
     * This method takes in the 2 file paths and creates your graph. Each Vertex must be
     * added to the graph array list. To implement an edge (v1, v2), add v2 to v1.neighbors list
     * by calling v1.addNeighbor(v2)
     * @param pVerticesFilePath file path to vertices file
     * @param pEdgesFilePath file path to edge file
     */
    public void loadGraph(String pVerticesFilePath, String pEdgesFilePath){

        // **** LOADING VERTICES *** //
        ArrayList<String[]> listVerticiesRaw = readFile(pVerticesFilePath);

        for (String[] line : listVerticiesRaw){
            Vertex newVertex;
            try {
                newVertex = new Vertex(line[0]);

                for (int i = 1; i < line.length; i++) {
                    newVertex.addWord(line[i]);
                }
            }
            catch (IndexOutOfBoundsException e){
                continue;
            }
            graph.add(newVertex);
        }
        // **** END LOADING VERTICES *** //


        // **** LOADING EDGES *** //
        ArrayList<String[]> listEdgesRaw = readFile(pEdgesFilePath);
        for (String[] line : listEdgesRaw){
            Vertex v1;
            Vertex v2;
            try {
                v1 = findVertex(line[0]);
                v2 = findVertex(line[1]);
            }
            catch (IndexOutOfBoundsException e){
                continue;
            }
            if (v1 != null && v2 != null) {
                v1.addNeighbor(v2);
            }
        }
        // **** END LOADING EDGES *** //

    }


    public ArrayList<String[]> readFile(String file){
        ArrayList<String []> lstLines = new ArrayList<String[]>();
        try
        {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String stringRead = br.readLine();

            while( stringRead != null )
            {
                lstLines.add( stringRead.split(","));
                // read the next line
                stringRead = br.readLine();
            }
            br.close();
            fr.close();
        }

        catch(IOException ioe){
            ioe.printStackTrace();
        }

        return lstLines;
	}

    public Vertex findVertex(String url){
        for (Vertex vertex : graph){
            if (vertex.getURL().equals(url)) return vertex;
        }
        return null;
    }

    /**
     * This method implements the Iterative Breadth-First Search algorithm.
     * @param pKeyword keyword to compare
     */
    public void Iterative_BFS(String pKeyword){
        ArrayList<Vertex> BFSQ = new ArrayList<Vertex>();	//This is your breadth-first search queue.
        Vertex start = graph.get(0);						//We will always start with this vertex in a graph search

        BFSQ.add(start);
        BFS_inspector.add(start);
        start.setVisited();

        while (!BFSQ.isEmpty()){
            Vertex v = BFSQ.remove(0);

            //Do other search shit in here
            //find number of occurrences in page
            int occurrence = findContains(v, pKeyword);
            SearchResult r = new SearchResult(v.getURL(), occurrence);
            wordOccurrenceQueue.add(r);

            for (Vertex vEdge : v.getNeighbors()){
                if (!vEdge.isVisited()){
                    BFSQ.add(vEdge);
                    BFS_inspector.add(vEdge);
                    vEdge.setVisited();

               }
            }
        }

    }

    /**
     * Elegant find algorithm if we look for exact match
     * find the number of occurrences of a keyword in the text of the vertex
     * @param vertex vertex with list of words
     * @param keyword keyword to compare
     * @return the number of occurrences
     */
    public int findOccurrences(Vertex vertex, String keyword){
        // sort, binary search to find word,
        // then explore before and after to find replicas

        ArrayList<String> listWords = new ArrayList<String>(vertex.getWords());
        //duplicate array list to leave initial list unchanged to not break unit tests
        Collections.sort(listWords);

        int index = Collections.binarySearch(listWords, keyword);
        if (index >= 0){
            int start = index - 1;
            int end = index + 1;

            while ((start >=0 && end < listWords.size()) &&
                    (listWords.get(start).equals(keyword) || listWords.get(end).equals(keyword) )){
                if (listWords.get(start).equals(keyword)){
                    start--;
                }
                if (listWords.get(end).equals(keyword)){
                    end++;
                }
            }
            return end-start-1;
        }

        return 0;
    }

    /**
     * boring linear search for contains matches
     * @param vertex vertex with list of words
     * @param keyword keyword to compare
     * @return the number of occurrences
     */
    public int findContains(Vertex vertex, String keyword){
        if (keyword.equals("")){
            return 0;
        }
        keyword = keyword.toLowerCase();
        int count = 0;
        for (String word : vertex.getWords()){
            if (word.contains(keyword)) count++;
        }

        return count;
    }

    /**
     * This method implements the Iterative Depth-First Search algorithm.
     * @param pKeyword keyword to compare
     */
    public void Iterative_DFS(String pKeyword){
        Stack<Vertex> DFSS = new Stack<Vertex>();	//This is your depth-first search stack.
        Vertex start = graph.get(0);				//We will always start with this vertex in a graph search

        DFSS.push(start);
        DFS_inspector.add(start);

        while (!DFSS.isEmpty()) {
            Vertex v = DFSS.pop();
            if (!v.isVisited()) {

                //find number of occurrences in page
                int occurrence = findContains(v, pKeyword);
                SearchResult r = new SearchResult(v.getURL(), occurrence);
                wordOccurrenceQueue.add(r);

                v.setVisited();

                for (Vertex vEdge : v.getNeighbors()) {
                    DFSS.push(vEdge);
                    DFS_inspector.add(vEdge);
                }
            }
        }
    }


    /**
     * This simple class just keeps the information about a Vertex together.
     * You do not need to modify this class. You only need to understand how it works.
     */
    public class Vertex{
        private String aUrl;
        private boolean visited;
        private ArrayList<String> aWords;
        private ArrayList<Vertex> neighbors;

        public Vertex(String pUrl){
            this.aUrl = pUrl;
            this.visited = false;
            this.neighbors = new ArrayList<Vertex>();
            this.aWords = new ArrayList<String>();
        }

        public String getURL(){
            return this.aUrl;
        }

        public void setVisited(){
            this.visited = true;
        }

        public void resetVisited(){
            this.visited = false;
        }

        public boolean isVisited(){
            return this.visited;
        }

        public void addWord(String pWord){
            this.aWords.add(pWord);
        }

        public ArrayList<String> getWords(){
            return this.aWords;
        }

        public ArrayList<Vertex> getNeighbors(){
            return this.neighbors;
        }

        public void addNeighbor(Vertex pVertex){
            this.neighbors.add(pVertex);
        }

        @Override
        public String toString(){
            return this.aUrl;
        }

    }

    /**
     * This simple class just keeps the information about a Search Result. It stores
     * the occurrences of your keyword in a specific page in the graph. You do not need to modify this class.
     * You only need to understand how it works.
     */
    public class SearchResult{
        private String aUrl;
        private int aWordCount;

        public SearchResult(String pUrl, int pWordCount){
            this.aUrl = pUrl;
            this.aWordCount = pWordCount;
        }

        public int getOccurrence(){
            return this.aWordCount;
        }

        public String getUrl(){
            return this.aUrl;
        }
    }

    /**
     * This class enables us to use the PriorityQueue type. The PriorityQueue needs to know how to
     * prioritize its elements. This class instructs the PriorityQueue to compare the SearchResult
     * elements based on their word occurrence values.
     * You do not need to modify this class. You only need to understand how it works.
     */
    public class WordOccurrenceComparator implements Comparator<SearchResult>{
        @Override
        public int compare(SearchResult o1, SearchResult o2){
            int x = o1.getOccurrence();
            int y = o2.getOccurrence();

            if (x > y)
            {
                return -1;
            }
            if (x < y)
            {
                return 1;
            }
            return 0;
        }
    }
}
