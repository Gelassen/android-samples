package com.home.xing.storage;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {

    public final static String AUTHORITY = "com.home.xing";
    public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private final static String _ID = BaseColumns._ID;
    private final static String _PK_AUTOINCREMENT = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private final static String _TYPE_INTEGER = "INTEGER";

    public final static Uri contentUri(final Class klass) {
        Uri.Builder builder = CONTENT_URI.buildUpon();
        builder.appendPath(klass.getSimpleName());
//        builder.appendQueryParameter("sqlite.conflict", Integer.toString(SQLiteDatabase.CONFLICT_REPLACE));
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
    * http://www.apache.org/licenses/LICENSE-2.0
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

    /**
     * Sample table to show principles of using reflection based schema management
     *
     * Each public row is a field. Each next row defines a type, if type doesn't defined row has
     * a default type -- "string"
     * Pattern of type name is _SQL_<name of field>_TYPE
     * If you want to define unique field you must put it at the end of table with name _SQL_UNIQUE
     * */
    public final static class RepoTable extends Table {
        public final static String ID = _ID;
        final static String _SQL_ID_TYPE = _PK_AUTOINCREMENT;
        public final static String REPO_NAME = "REPO_NAME";
        public final static String DESCRIPTION = "DESCRIPTION";
        public final static String OWNER = "OWNER";
        public final static String REPO_URL = "REPO_URL";
        public final static String OWNER_URL = "OWNER_URL";
        public final static String FORK = "FORK";
        final static String _SQL_FORK_TYPE = _TYPE_INTEGER;
        public final static String CACHED = "CACHED";
        final static String _SQL_CACHED_TYPE = _TYPE_INTEGER;
    }

}
