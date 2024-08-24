import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wn;

    ////////////////////////////////////////////////////////////////////////////

    public Outcast(WordNet wordnet){
        wn = wordnet;
    }

    public String outcast(String[] nouns){
        int[] distances = new int[nouns.length];
        for(int i = 0; i < nouns.length; i++){
            for (int j = 0; j < nouns.length; j++){
                if (j==i) continue;
                distances[i] += wn.distance(nouns[i], nouns[j]);
            }
        }
        int idx = 0;
        int maximum = distances[0];
        for (int i = 1; i < distances.length; i++){
            if (distances[i]>maximum){
                idx = i;
                maximum = distances[i];
            }
        }
        return nouns[idx];
    }

    // test client
    // takes from the command line the name of a synset file,
    // the name of a hypernym file, followed by the names of outcast files,
    // and prints out an outcast in each file
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
