package cat.urv.deim.asm.p3.shared;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseCredentials {

    public static final String DATABASE_NAME = "DevCommunity.db";
    public static final int DATABASE_VERSION = 1;
    public static final String AUTHORITY = "cat.urv.deim.asm.p3.shared";
    public static final String PATH_EVENTS = "events";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH_EVENTS);

    public DatabaseCredentials() {
    }

    public static abstract class Events implements BaseColumns {
        public static final String TABLE_EVENTS = "events";
        public static final String EVENTS_ID = "_ID";
        public static final String EVENTS_DESCRIPTION = "description";
        public static final String EVENTS_IMAGEURL = "imageURL";
        public static final String EVENTS_NAME = "name";
        public static final String EVENTS_TYPE = "type";
        public static final String EVENTS_WEBURL = "webURL";
        public static final String EVENTS_TAGS = "tags";

        public static final String SQL_CREATE_EVENTS = "CREATE TABLE " +
                TABLE_EVENTS +
                " (" +
                EVENTS_ID + " INTEGER PRIMARY KEY," +
                EVENTS_DESCRIPTION + " TEXT," +
                EVENTS_IMAGEURL + " TEXT," +
                EVENTS_NAME + " TEXT," +
                EVENTS_TYPE + " TEXT," +
                EVENTS_WEBURL + " TEXT," +
                EVENTS_TAGS + " TEXT )";

        public static final String SQL_DELETE_EVENTS = "DROP TABLE IF EXISTS " + TABLE_EVENTS;

        public static final String[] ALL_COLUMNS =
                {EVENTS_ID, EVENTS_DESCRIPTION, EVENTS_IMAGEURL, EVENTS_NAME, EVENTS_TYPE, EVENTS_WEBURL, EVENTS_TAGS};

    }
}
