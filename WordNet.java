import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordNet {
    private final ArrayList<Set<String>> synsets;
    private final Map<String, Set<Integer>> nounToSynset;   // map individual nouns to numbers of all the synsets it belongs
    private final ArrayList<String> oriSynsets;     // synsets, each represented as a string of words
    private final Digraph wordNet;
    private final int n;    // number of synsets
    private final SAP sapCounter;

    // private method for reading the synsets file
    private void readSynsets(String synsets){
        In in = new In(synsets);
        String[] fields;
        while (!in.isEmpty()){
            fields = in.readLine().split(",");
            oriSynsets.add(fields[1]);
            Set<String> temp = new HashSet<>();
            for (String s: fields[1].split(" ")){
                temp.add(s);
                if (!nounToSynset.containsKey(s)){
                    Set<Integer> newSet = new HashSet<>();
                    newSet.add(Integer.parseInt(fields[0]));
                    nounToSynset.put(s, newSet);
                } else {
                    Set<Integer> oldSet = nounToSynset.get(s);
                    oldSet.add(Integer.parseInt(fields[0]));
                }
            }
            synsets.add(temp);
        }
    }

    // private method for reading the hypernyms file
    private void readHypernyms(String hypernyms){
        In in = new In(hypernyms);
        String[] fields;
        while(!in.isEmpty()){
            fields = in.readLine().split(",");
            int hypo = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++){
                int hyper = Integer.parseInt(fields[i]);
                wordNet.addEdge(hypo, hyper);
            }
        }
    }

    // method to tell if the graph has cycles or not
    private void hasCycle(){
        DirectedCycle cycle = new DirectedCycle(wordNet);
        if (cycle.hasCycle()){
            throw new IllegalArgumentException();
        }
    }

    // method to tell if the graph has root or not
    private void hasRoots(){
        int roots = 0;
        for (int i = 0; i < n; i++){
            if (wordNet.outdegree(i)==0){
                roots++;
                if (roots>=2){
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }
        synsets = new ArrayList<Set<String>>();
        nounToSynset = new HashMap<>();
        oriSynsets = new ArrayList<>();
        readSynsets(synsets);
        n = synsets.size();
        wordNet = new Digraph(n);
        readHypernyms(hypernyms); // this actually does the graph construction

        // making sure arguments make a rooted DAG
        hasCycle();
        hasRoots();
        sapCounter = new SAP(wordNet);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return nounToSynset.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        if (word == null){
            throw new IllegalArgumentException();
        }
        return nounToSynset.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if (!isNoun(nounA)||!isNoun(nounB)){
            throw new IllegalArgumentException();
        }
        return sapCounter.length(nounToSynset.get(nounA), nounToSynset.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if (!isNoun(nounA)||!isNoun(nounB)){
            throw new IllegalArgumentException();
        }
        return oriSynsets.get(sapCounter.ancestor(nounToSynset.get(nounA), nounToSynset.get(nounB)));
    }

    public static void main(String[] args) {return;}
}
