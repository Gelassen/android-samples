package com.example.interview;

import com.example.interview.model.VideoItem;
import com.example.interview.model.api.ThumbnailData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 10/3/2016.
 */
public class Test {

    public static String getUri() {
        return "https://video.xx.fbcdn.net/v/t43.1792-2/14150942_1776455732634297_1815505872_n.mp4?efg=eyJybHIiOjE1MDAsInJsYSI6MTAyNCwidmVuY29kZV90YWciOiJzdmVfaGQifQ%3D%3D&rl=1500&vabr=418&oh=5fd4e1739c433c6b4b10a8b169b333bd&oe=57F413D0";
    }

    public static String getThumb() {
//        return "http://goo.gl/gEgYUd";
        return "https://scontent.xx.fbcdn.net/v/t15.0-10/12323506_999675336784200_1959389375_n.jpg?oh=c9c1a9fde3e2e7178087802dea4e144f&oe=58A70575";
    }

    public static List<VideoItem> getTestModel() {
        List<VideoItem> result = new ArrayList<>();
        int testCount = 10;
        for (int i = 0; i < testCount; i++) {
            VideoItem videoItem = new VideoItem();
            videoItem.setSource("https://currentcnmedia.s3.cn-north-1.amazonaws.com.cn/8ddc44af073c41cf8a78e6c725004b57.mp4");

            ThumbnailData thumbnail = new ThumbnailData();
            thumbnail.setUri("https://currentcnmedia.s3.cn-north-1.amazonaws.com.cn/eb679478-e870-4839-a4c4-b60d3c4d4cec.jpg");
            videoItem.setThumbnail(thumbnail);
            result.add(videoItem);
        }

        return result;
    }
}
