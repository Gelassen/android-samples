package com.dataart.school;

import android.provider.BaseColumns;

public class Schema {
    
    public static final String TAG = "AndroidSchool";
    
    public static final int VERSION = 3;
    
    public class Stores implements BaseColumns {
    
        private Stores() {}
        
        public static final String ID = "ID";
    
        public static final String TABLE_NAME = "STORES";
        
        public static final String STORE_NAME = "NAME";
        
        public static final String ADDRESS = "ADDRESS";
        
        public static final String PHONE = "PHONE";
        
        public static final String LATITUDE = "LATITUDE";
        
        public static final String LONGITUDE = "LONGITUDE";
            
    }

}
