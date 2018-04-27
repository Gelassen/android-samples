package com.home.vkphotos.utils;


import java.io.File;
import java.util.Comparator;

public class CachedFileComparator implements Comparator<String> {

    @Override
    public int compare(String first, String second) {
        File firstFile = new File(first);
        File secondFile = new File(second);
        return firstFile.lastModified() < secondFile.lastModified() ? -1
                : firstFile.lastModified() == secondFile.lastModified() ? 0 : 1;
    }
}
