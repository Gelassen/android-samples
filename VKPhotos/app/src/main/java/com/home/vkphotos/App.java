package com.home.vkphotos;


import com.home.vkphotos.photos.Item;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static final String CACHE = "CACHE";

    public static final String TAG = "TAG";

    public static List<Item> generateDataset() {
        List<Item> results = new ArrayList<>();
        for (int id = 0; id < 20; id++) {
            Item item = new Item();
            item.setId(10);
            item.setPhoto1280("https://pp.userapi.com/c9880/v9880860/2a95/NKGeLSfevhg.jpg");
            results.add(item);
        }
        return results;
    }

    public static class Constants {
        public static String TOKEN = "TOKEN";
    }
}
