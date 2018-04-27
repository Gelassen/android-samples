package com.home.vkphotos;


import com.home.vkphotos.photos.model.Item;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static final String CACHE = "CACHE";

    public static final String TAG = "TAG";

    public static class Constants {
        public static String TOKEN = "TOKEN";
    }

    public static List<Item> generateDataset() {
        List<Item> data = new ArrayList<>();
        data.add(buildItem("https://pp.userapi.com/c846420/v846420468/2c2a3/T8dOgSt_0BE.jpg"));
        data.add(buildItem("https://pp.userapi.com/c846420/v846420468/2c299/iC9V3VjqOzQ.jpg"));
        data.add(buildItem("https://pp.userapi.com/c846420/v846420468/2c28f/ueoXb2Pd4Cg.jpg"));
        data.add(buildItem("https://pp.userapi.com/c846420/v846420468/2c285/v4b94ejGZ6g.jpg"));
        data.add(buildItem("https://pp.userapi.com/c846420/v846420468/2c27b/3ksRgfkWbk8.jpg"));
        data.add(buildItem("https://pp.userapi.com/c846420/v846420468/2c26d/vzAVl1vJOcY.jpg"));
        data.add(buildItem("https://pp.userapi.com/c846420/v846420468/2c264/lr-bSKIXQTQ.jpg"));
        data.add(buildItem("https://pp.userapi.com/c846420/v846420468/2c25a/PXxnm3g1LM4.jpg"));
        data.add(buildItem("https://pp.userapi.com/c846420/v846420468/2c250/p4NAZNcXF8o.jpg"));
        data.add(buildItem("https://pp.userapi.com/c846420/v846420468/2c246/7sgg1ig2LR4.jpg"));
        data.add(buildItem("https://pp.userapi.com/c846420/v846420468/2c23c/awsq6Xkj-Dg.jpg"));
        data.add(buildItem("https://pp.userapi.com/c846420/v846420468/2c232/sTZ7brAdfLk.jpg"));
        data.add(buildItem("https://pp.userapi.com/c846420/v846420468/2c228/3BxuNrEIhQw.jpg"));
        data.add(buildItem("https://pp.userapi.com/c846420/v846420468/2c21f/Myn6FLE7O8k.jpg"));
        data.add(buildItem("https://pp.userapi.com/c846420/v846420468/2c216/Q6rHwkHs8wo.jpg"));
        data.add(buildItem("https://pp.userapi.com/c846420/v846420468/2c20c/e5sjMOUDh5I.jpg"));
        data.add(buildItem("https://sun1-4.userapi.com/c840727/v840727085/76d91/N7YA7qlMXfc.jpg"));
        data.add(buildItem("https://sun1-3.userapi.com/c840727/v840727085/76d88/ogVvJDJEzKI.jpg"));
        data.add(buildItem("https://sun1-1.userapi.com/c840727/v840727085/76d7f/OAT7EvQe3tg.jpg"));
        data.add(buildItem("https://sun1-3.userapi.com/c840727/v840727085/76d76/cTp0iZHdBes.jpg"));
        return data;
    }

    private static Item buildItem(String url) {
        Item item = new Item();
        item.setPhoto604(url);
        return item;
    }
}
