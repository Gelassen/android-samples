package com.example.interview.storage;


import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {

    public final static String AUTHORITY = "com.example.interview";
    public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private final static String _ID = BaseColumns._ID;
    private final static String _PK_AUTOINCREMENT = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private final static String _TYPE_INTEGER = "INTEGER";

    public final static Uri contentUri(final Class klass) {
        Uri.Builder builder = CONTENT_URI.buildUpon();
        builder.appendPath(klass.getSimpleName());
        return builder.build();
    }

    /* reflection based schema management */
    /*
     * Copyright (C) 2012 Random Android Code Snippets
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     *      http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */
    /*package*/ static interface SqlEntity { };

    /** Simple marker class */
    public static class Table implements SqlEntity { }

    /*package*/ static class View implements SqlEntity { }

    /*package*/ public static class Join implements SqlEntity {
        final static String getJoinTable(Class<? extends Join> join) {
            try {
                return (String) join.getField("JOIN").get(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*package*/ final static String getTableName(final Class<? extends SqlEntity> table) {
        return table.getSimpleName();
    }

//    "source":"https://video.xx.fbcdn.net/v/t43.1792-2/14150942_1776455732634297_1815505872_n.mp4?efg=eyJybHIiOjE1MDAsInJsYSI6MTAyNCwidmVuY29kZV90YWciOiJzdmVfaGQifQ%3D%3D&rl=1500&vabr=418&oh=73568272c1705924dc034759d1147bc0&oe=57E78D90",
//            "thumbnails":{ },
//            "id":"1114853028599763"

    public final static class VideoTable extends Table {
        public final static String ID = _ID;
        final static String _SQL_ID_TYPE = _PK_AUTOINCREMENT;
        public final static String VIDEO_SOURCE = "VIDEO_SOURCE";
    }

//    "id":"1114854498599616",
//            "height":360,
//            "scale":1,
//            "uri":"https://scontent.xx.fbcdn.net/v/t15.0-10/14270859_1114854531932946_1329933356_n.jpg?oh=21bc5f7481a55e4e556df9e932bbe0da&oe=5864F572",
//            "width":640,
//            "is_preferred":false

    public final static class ThumbnailTable extends Table {
        public final static String ID = _ID;
        final static String _SQL_ID_TYPE = _PK_AUTOINCREMENT;
        public final static String THUMBNAIL_SOURCE = "THUMBNAIL_SOURCE";
        public final static String VIDEO_ID = "VIDEO_ID";
        final static String _SQL_VIDEO_ID_TYPE = _TYPE_INTEGER;
        public final static String HEIGHT = "HEIGHT";
        final static String _SQL_HEIGHT_TYPE = _TYPE_INTEGER;
        public final static String WIDTH = "WIDTH";
        final static String _SQL_WIDTH_TYPE = _TYPE_INTEGER;
        public final static String SCALE = "SCALE";
        final static String _SQL_SCALE_TYPE = _TYPE_INTEGER;
        public final static String IS_PREFERRED = "IS_PREFERRED";
        final static String _SQL_IS_PREFERRED_TYPE = _TYPE_INTEGER;
    }
}
