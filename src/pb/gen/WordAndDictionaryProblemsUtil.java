package pb.gen;

import java.util.*;

public class WordAndDictionaryProblemsUtil {

    public static void main(String[] args) {

    }

    public static LinkedList<String> transformBFS(String start, String end,
                                                  HashSet<String> dict) {
        LinkedList<String> q = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        Map<String, String> wordBackTrack = new HashMap<>();
        q.add(start);
        visited.add(start);
        while (!q.isEmpty()) {
            String currentWord = q.poll();
            for (String neighbour : getOneEditAwayWords(currentWord, dict)) {
                if (neighbour.equals(end)) {
                    LinkedList<String> path = new LinkedList<>();
                    path.add(neighbour);
                    while (currentWord != null) {
                        path.add(currentWord);
                        currentWord = wordBackTrack.get(currentWord);
                    }
                    return path;
                }
                if (!visited.contains(neighbour)) {
                    wordBackTrack.put(neighbour, currentWord);
                    q.add(neighbour);
                    visited.add(neighbour);
                }
            }
        }
        return null;
    }

    public static LinkedList<String> transformDFS(String start, String end,
                                                  HashSet<String> dict) {
        return transformDFS(new HashSet<String>(), start, end, dict);
    }

    private static LinkedList<String> transformDFS(HashSet<String> visited,
                                                   String start, String end, HashSet<String> dict) {
        if (start.equals(end)) {
            LinkedList<String> path = new LinkedList<>();
            path.add(end);
            return path;
        }

        if (visited.contains(start))
            return null;

        visited.add(start);

        for (String v : getOneEditAwayWords(start, dict)) {
            LinkedList<String> path = transformDFS(visited, v, end, dict);
            if (path != null) {
                path.add(start);
                return path;
            }
        }

        return null;
    }

    private static ArrayList<String> getOneEditAwayWords(String word,
                                                         HashSet<String> dict) {
        ArrayList<String> oneEditAwayWords = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String s = word.substring(0, i) + c + word.substring(i + 1);
                if (dict.contains(s))
                    oneEditAwayWords.add(s);
            }
        }
        return oneEditAwayWords;
    }

    private static class WordNode {
        String word;
        int numSteps;

        public WordNode(String word, int numSteps) {
            this.word = word;
            this.numSteps = numSteps;
        }
    }

    public static class Solution {
        public int ladderLength(String beginWord, String endWord, Set<String> wordDict) {
            LinkedList<WordNode> queue = new LinkedList<WordNode>();
            queue.add(new WordNode(beginWord, 1));
            wordDict.add(endWord);

            while (!queue.isEmpty()) {
                WordNode top = queue.remove();
                String word = top.word;

                if (word.equals(endWord)) {
                    return top.numSteps;
                }

                char[] arr = word.toCharArray();
                for (int i = 0; i < arr.length; i++) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        char temp = arr[i];
                        if (arr[i] != c) {
                            arr[i] = c;
                        }

                        String newWord = new String(arr);
                        if (wordDict.contains(newWord)) {
                            queue.add(new WordNode(newWord, top.numSteps + 1));
                            wordDict.remove(newWord);
                        }
                        arr[i] = temp;
                    }
                }
            }
            return 0;
        }
    }
}
