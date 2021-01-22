package cat.urv.deim.asm.p3.shared;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.*;
import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.Articles.*;
import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.Calendar.*;
import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.Events.*;
import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.Faqs.SQL_CREATE_FAQS;
import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.Faqs.TABLE_FAQS;
import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.News.*;


public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_NEWS);
        db.execSQL(SQL_CREATE_ARTICLES);
        db.execSQL(SQL_CREATE_EVENTS);
        db.execSQL(SQL_CREATE_CALENDAR);
        db.execSQL(SQL_CREATE_FAQS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALENDAR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAQS);
        onCreate(db);
    }
}
