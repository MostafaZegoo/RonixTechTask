package com.example.mostafa.ronixtechtask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase_Users {

    private UserHelper helper;

    DataBase_Users(Context context){

        helper = new UserHelper(context);

    }

    public String getAllLocations () {

        SQLiteDatabase db = helper.getWritableDatabase();

        String[] Columns = {UserHelper.ID,
                UserHelper.EMAIL,
                UserHelper.PASSWORD};

        Cursor cursor = db.query(UserHelper.TABLE_NAME, Columns, null, null, null, null, null);

        StringBuffer buffer = new StringBuffer();

        // looping through all rows and adding to list
        while (cursor.moveToNext()) {

            int cid = cursor.getInt(0);
            String email = cursor.getString(1);
            String password = cursor.getString(2);

            buffer.append(cid).append(" ").append(email).append(" ").append(password).append("\n");

        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return buffer.toString();

    }

    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                UserHelper.ID
        };
        SQLiteDatabase db = helper.getWritableDatabase();
        // selection criteria
        String selection = UserHelper.EMAIL + " =?" + " AND " + UserHelper.PASSWORD + " =?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /*
          Here query function is used to fetch records from user table this function works like we use sql query.
          SQL query equivalent to this query function is
          SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(UserHelper.TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        cursor.moveToFirst();

        int cursorCount = cursor.getCount();

        cursor.close();

        db.close();

        return cursorCount > 0;

    }

    public void insert (String first_name, String last_name, String email, String phone, String password, String confirm_password) {

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(UserHelper.FIRST_NAME, first_name);

        contentValues.put(UserHelper.LAST_NAME, last_name);

        contentValues.put(UserHelper.EMAIL, email);

        contentValues.put(UserHelper.PHONE, phone);

        contentValues.put(UserHelper.PASSWORD, password);

        contentValues.put(UserHelper.CONFIRM_PASSWORD, confirm_password);

        db.insert(UserHelper.TABLE_NAME, null, contentValues);
    }

    static class UserHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "Users.db";

        private static final String TABLE_NAME = "user";

        private static final int DATABASE_VERSION = 1;

        private static final String ID = "_id";

        private static final String FIRST_NAME = "First_name";

        private static final String LAST_NAME = "Last_name";

        private static final String EMAIL = "Email";

        private static final String PHONE = "Phone";

        private static final String PASSWORD = "Password";

        private static final String CONFIRM_PASSWORD = "Confirm_password";

        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME
                +" ("+ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+FIRST_NAME
                +" TEXT,"+LAST_NAME
                +" TEXT,"+EMAIL
                +" TEXT,"+PHONE
                +" TEXT,"+PASSWORD
                +" TEXT,"+CONFIRM_PASSWORD
                +" TEXT);";

        private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME;

        private Context context;

        UserHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context=context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {

                db.execSQL(CREATE_TABLE);

            } catch (SQLException e) {

                Message.message(context,"" + e);

            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {

                db.execSQL(DROP_TABLE);
                onCreate(db);

            } catch (SQLException e) {

                Message.message(context,"" + e);

            }

        }
    }

}
