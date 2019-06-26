/*
*   DbHelper
*   This class interacts with the database to save settings and retrieve statistics
* */

package vermeer.luc.siesta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static DbHelper instance;
    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Initialize database tables with appropriate values.
        sqLiteDatabase.execSQL("CREATE TABLE Settings(_id INTEGER, " +
                "productivity INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE Siestas(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "minutes INTEGER, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");

        String insertEntry = "INSERT INTO Settings (_id, productivity) VALUES (0, 3)";
        sqLiteDatabase.execSQL(insertEntry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Necessary empty function
        }

    public int getProductivity() {
        // Retrieves the productivity from the database
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT productivity FROM Settings WHERE _id=?", new String[]{"0"});
        if(cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return 2;
    }

    public void saveSiesta(int minutes) {
        // Saves siesta for analyses and statistics.
        SQLiteDatabase sqLiteDatabase = instance.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("minutes", minutes);
        sqLiteDatabase.insert("Siestas", null, content);
    }

    public Cursor getSiestas() {
        // Retrieves all Siestas from database.
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("SELECT * FROM Siestas", null);
    }

    public void saveProductivity(int productivity) {
        // Deletes old productivity from the database and saves new one.
        SQLiteDatabase sqLiteDatabase = instance.getWritableDatabase();
        sqLiteDatabase.delete("Settings", "_id=?", new String[]{"0"});

        ContentValues content = new ContentValues();
        content.put("_id", 0);
        content.put("productivity", productivity);
        sqLiteDatabase.insert("Settings", null, content);
    }

    public static DbHelper getInstance(Context context) {
        // Makes DbHelper a singleton class
        if (instance == null){
            instance = new DbHelper(context, "databaseSettings", null, 1 );
        }
        return instance;
    }
}