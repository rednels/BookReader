package com.vcokey.xs8reader.reader.model;

import android.support.v4.util.CircularArray;

import java.util.ArrayList;

/**
 * Created by vcokey on 2015/8/28.
 */
public class ReaderPager {

    ArrayList<String> prePages = new ArrayList<>();
    ArrayList<String> curPages = new ArrayList<>();
    ArrayList<String> nextPages = new ArrayList<>();

    CircularArray<ArrayList<String>> pagesCache = new CircularArray<>(3);

    public void a(){
        pagesCache.addFirst(prePages);
    }
}
