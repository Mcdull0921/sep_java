package com.example.talent.sep;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class StreamAlerterTests {

    @DisplayName("检查字符流中的敏感词")
    @Test
    public void testQuery() {
        StreamAlerter as = new StreamAlerter(new String[]{"赌博", "游戏", "摇头丸", "XXX"});
        assertFalse(as.query('a'));
        assertFalse(as.query('赌'));
        assertTrue(as.query('博'));
        assertFalse(as.query('游'));
        assertTrue(as.query('戏'));
        assertFalse(as.query('摇'));
        assertFalse(as.query('头'));
        assertTrue(as.query('丸'));
        assertFalse(as.query('X'));
        assertFalse(as.query('X'));
        assertTrue(as.query('X'));
    }

    @DisplayName("检查很长时间的字符流")
    @Test
    public void testLongTimeQuery() {
        StreamAlerter as = new StreamAlerter(new String[]{"abc", "xyz"});
        Random random = new Random();
        long start = System.currentTimeMillis();
        long randomTime = 0,trieRingTime = 0;
        int count = 0;
        for (int i = 0; i < 1_000_000_000; i++) { // Integer.MAX_VALUE
            long t1 = System.currentTimeMillis();
            char ch = (char) ('a' + random.nextInt(26));
            long t2 = System.currentTimeMillis();
            if (as.query(ch)) count += 1;
            long t3 = System.currentTimeMillis();
            randomTime += t2 - t1;
            trieRingTime += t3 -t2;
        }
        long end = System.currentTimeMillis();
        // TODO 按照您的实际情况写运行时间，并在提交时说明这两种情况下耗时情况
        // on my Laptop,
        // total time is about 25s, Random use 11s, Trie+Ring use 14s
        System.out.printf("on my Laptop,\ntotal time is about %ds, Random use %ds, Trie+Ring use %ds\n", (end - start)/1000, randomTime/1000, trieRingTime/1000);
        assertNotEquals(0, count); // count > 0
    }
}

