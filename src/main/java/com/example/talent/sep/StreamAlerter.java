package com.example.talent.sep;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

public class StreamAlerter {
    private Deque<Character> ring;
    private HashMap<Character, Object> trie;

    private static final char END = '#';
    private int count = 0;
    private int size = 1024;

    public StreamAlerter(String[] strings) {
        ring = new LinkedList<>();
        trie = new HashMap<>();
        for (String word : strings) {
            insert(word);
        }
    }

    private void insert(String string) {
        char[] word = string.toCharArray();
        HashMap<Character, Object> curr = trie;
        int n = word.length;
        for (int i = n - 1; i >= 0; i--) {
            Character w = word[i];
            if (!curr.containsKey(w)) {
                curr.put(w, new HashMap<Character, Object>());
            }
            curr = (HashMap<Character, Object>) curr.get(w);
        }
        curr.put(END, true);
    }

    public boolean query(char ch) {
        ring.addFirst(ch);
        count += 1;
        if (count > size) {
            ring.removeLast();
        }
        HashMap<Character, Object> curr = trie;
        for (Character w : ring) {
            if (!curr.containsKey(w)) {
                return false;
            }
            curr = (HashMap<Character, Object>) curr.get(w);
            if (curr.containsKey(END)) {
                return true;
            }
        }
        return false;
    }
}
