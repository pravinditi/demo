package com.iqsolutions.mydemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Created by Apeksha Gunjal on 10-Oct-19.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "test.db";
    private static final int VERSION = 1;
    private Context context;
    private String TAG = DatabaseHelper.class.getSimpleName();
    private static DatabaseHelper databaseHelper;
    private static SQLiteDatabase database;
    public static final String DATABASE_PATH = "data/data/com.iqsolutions.mydemo/test.db";

    public static Context myContext;
    private static DatabaseHelper databaseInstance;
    private static final int DB_Version = 1;
    public static long inserted;


    String success;
    private DatabaseHelper mDatabaseHelper;

    String route_no, trip_id, bus_service_name, bus_service_code, trip_direction, trip_no, ticket_no, from_name, from_code, till_name, till_code, full_ticket_count, half_ticket_count, luggage_ticket_count, total_amount, transaction_date, transaction_time, asc_amt, bsc_amt, octroi_amt, toll_amt, it_amt, reservartion_chrgs, waybill_no;
    String ticket_type;


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
//        preference = LocalSharedPreference.getInstance(context);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null)
            databaseHelper = new DatabaseHelper(context);
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "inside Database onCreate...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVesrion) {
        Log.d(TAG, "inside Database onUpgrade...oldVersion: " + oldVersion + " newVesrion: " + newVesrion);
    }

    public SQLiteDatabase openDatabase() {
        //String dbPath = myContext.getDatabasePath(DB_NAME).getPath();
        if (database != null && database.isOpen())
            return database;

        database = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
        return database;
    }

    public void closeDatabase() {
        if (database != null && database.isOpen())
            database.close();
    }

    public boolean InsertWithQuery(String query) {
        openDatabase();
        boolean insertValue = false;
        //myDatabase =this.getWritableDatabase();

        try {
            openDatabase();
            database.execSQL(query);
            insertValue = true;
        } catch (Exception e) {
            Log.e("Ex InsertionMasters", e.toString());
            insertValue = false;
        } finally {
            closeDatabase();
            return insertValue;
        }

    }



}
