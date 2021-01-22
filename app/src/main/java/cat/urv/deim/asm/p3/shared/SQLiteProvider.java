package cat.urv.deim.asm.p3.shared;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.*;
import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.Articles.*;
import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.Calendar.*;
import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.Events.*;
import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.Faqs.*;
import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.News.*;

public class SQLiteProvider extends ContentProvider {

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int NEWS = 1;
    private static final int ARTICLES = 2;
    private static final int EVENTS = 3;
    private static final int EVENTS_ID = 4;
    private static final int CALENDAR = 5;
    private static final int FAQS = 6;

    static {
        uriMatcher.addURI(AUTHORITY, PATH_NEWS, NEWS);
        uriMatcher.addURI(AUTHORITY, PATH_ARTICLES, ARTICLES);
        uriMatcher.addURI(AUTHORITY, PATH_EVENTS, EVENTS);
        uriMatcher.addURI(AUTHORITY, PATH_EVENTS + "/#", EVENTS_ID);
        uriMatcher.addURI(AUTHORITY, PATH_CALENDAR, CALENDAR);
        uriMatcher.addURI(AUTHORITY, PATH_FAQS, FAQS);
    }

    private SQLiteDatabase db;
    DBOpenHelper dbOpenHelper;

    public SQLiteProvider (){
    }

    @Override
    public boolean onCreate() {
        dbOpenHelper = new DBOpenHelper(getContext().getApplicationContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        db = dbOpenHelper.getReadableDatabase();
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case NEWS:
                cursor = db.query(TABLE_NEWS, projection, selection, null, null, null, null);
                break;
            case ARTICLES:
                cursor = db.query(TABLE_ARTICLES, projection, selection, null, null, null, null);
                break;
            case EVENTS:
                cursor = db.query(TABLE_EVENTS, projection, selection, null, null, null, EVENTS_ID + " ASC");
                break;
            case CALENDAR:
                cursor = db.query(TABLE_CALENDAR, projection, selection, null, null, null, null);
                break;
            case FAQS:
                cursor = db.query(TABLE_FAQS, projection, selection, null, null, null, null);
                break;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case NEWS:
                return CONTENT_LIST_TYPE_NEWS;
            case ARTICLES:
                return CONTENT_LIST_TYPE_ARTICLES;
            case EVENTS:
                return CONTENT_LIST_TYPE_EVENTS;
            case EVENTS_ID:
                return CONTENT_ITEM_TYPE_EVENTS;
            case CALENDAR:
                return CONTENT_LIST_TYPE_CALENDAR;
            case FAQS:
                return CONTENT_LIST_TYPE_FAQS;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        db = dbOpenHelper.getWritableDatabase();
        long id;
        switch (uriMatcher.match(uri)){
            case NEWS:
                id = db.insert(TABLE_NEWS, null, values);
                if (id > 0) {
                    Uri _uri = ContentUris.withAppendedId(uri, id);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                throw new SQLException("Insertion Failed for URI :" + uri);
            case ARTICLES:
                id = db.insert(TABLE_ARTICLES, null, values);
                if (id > 0) {
                    Uri _uri = ContentUris.withAppendedId(uri, id);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                throw new SQLException("Insertion Failed for URI :" + uri);
            case EVENTS:
                id = db.insert(TABLE_EVENTS, null, values);
                if (id > 0) {
                    Uri _uri = ContentUris.withAppendedId(uri, id);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                throw new SQLException("Insertion Failed for URI :" + uri);
            case CALENDAR:
                id = db.insert(TABLE_CALENDAR, null, values);
                if (id > 0) {
                    Uri _uri = ContentUris.withAppendedId(uri, id);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                throw new SQLException("Insertion Failed for URI :" + uri);
            case FAQS:
                id = db.insert(TABLE_FAQS, null, values);
                if (id > 0) {
                    Uri _uri = ContentUris.withAppendedId(uri, id);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                throw new SQLException("Insertion Failed for URI :" + uri);

            default:
                throw new IllegalArgumentException("Values not valid" + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        db = dbOpenHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case NEWS:
                return db.delete(TABLE_NEWS, selection, selectionArgs);

            case ARTICLES:
                return db.delete(TABLE_ARTICLES, selection, selectionArgs);

            case EVENTS:
                return db.delete(TABLE_EVENTS, selection, selectionArgs);

            case EVENTS_ID:
                selection = _ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return db.delete(TABLE_EVENTS, selection, selectionArgs);

            case CALENDAR:
                return db.delete(TABLE_CALENDAR, selection, selectionArgs);

            case FAQS:
                return db.delete(TABLE_FAQS, selection, selectionArgs);

            default:
                throw new IllegalArgumentException("Deletion is not supported for" + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        db = dbOpenHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case NEWS:
                return db.update(TABLE_NEWS, values, selection, selectionArgs);

            case ARTICLES:
                return db.update(TABLE_ARTICLES, values, selection, selectionArgs);

            case EVENTS:
                return db.update(TABLE_EVENTS, values, selection, selectionArgs);

            case EVENTS_ID:
                selection = _ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return db.update(TABLE_EVENTS, values, selection, selectionArgs);

            case CALENDAR:
                return db.update(TABLE_CALENDAR, values, selection, selectionArgs);

            case FAQS:
                return db.update(TABLE_FAQS, values, selection, selectionArgs);

            default:
                throw new IllegalArgumentException("Update is not supported for" + uri);
        }
    }
}
