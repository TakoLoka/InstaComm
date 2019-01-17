package hw1.demirtas.tarik.com.instacomm;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class CommentsDB {
    public static final String TABLE_NAME="comments";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_TEXT = "text";
    public static final String FIELD_PREMIUM = "premium";
    public static final String FIELD_TYPE = "type";

    public static final String CREATE_TABLE_SQL = "CREATE TABLE "+
            TABLE_NAME+" ("+FIELD_ID+" number, "+FIELD_TEXT+" text, "+ FIELD_PREMIUM +" number, "+
            FIELD_TYPE +" number);";
    public static final String DROP_TABLE_SQL = "DROP TABLE if exists "+TABLE_NAME;


    public static List<Comment> getAllComments(DatabaseHelper db){
        Cursor cursor = db.getAllRecords(TABLE_NAME, null);
        //Cursor cursor  db.getAllRecordsMethod2("SELECT * FROM "+TABLE_NAME, null)
        List<Comment> data=new ArrayList<>();
        Comment anItem = null;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String text = cursor.getString(1);
            int premium = cursor.getInt(2);
            int type = cursor.getInt(3);

            anItem= new Comment(id, text, premium, type);
            data.add(anItem);
        }
        return data;
    }

    public static long insertComment(DatabaseHelper db, int id, String text, int premium, int type){
        ContentValues contentValues = new ContentValues( );
        contentValues.put(FIELD_ID, id);
        contentValues.put(FIELD_TEXT, text);
        contentValues.put(FIELD_PREMIUM, premium);
        contentValues.put(FIELD_TYPE, type);

        long res = db.insert(TABLE_NAME,contentValues);

        return res;
    }
}
