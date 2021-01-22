package cat.urv.deim.asm.p3.shared;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseCredentials {

    public static final String DATABASE_NAME = "DevCommunity.db";
    public static final int DATABASE_VERSION = 5;
    public static final String AUTHORITY = "cat.urv.deim.asm.p3.shared";
    public static final String PATH_NEWS = "news";
    public static final String PATH_ARTICLES = "articles";
    public static final String PATH_EVENTS = "events";
    public static final String PATH_CALENDAR = "calendar";
    public static final String PATH_FAQS = "faqs";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public DatabaseCredentials() {
    }

    public static abstract class News implements BaseColumns {
        public static final Uri CONTENT_URI_NEWS = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_NEWS);

        public static final String CONTENT_LIST_TYPE_NEWS =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_NEWS;

        public static final String TABLE_NEWS = "news";
        public static final String NEWS_ID = "_ID";
        public static final String NEWS_DATE = "date";
        public static final String NEWS_DATEUPDATE = "dateUpdate";
        public static final String NEWS_IMAGEURL = "imageURL";
        public static final String NEWS_SUBTITLE = "subtitle";
        public static final String NEWS_TAGS = "tags";
        public static final String NEWS_TEXT = "text";
        public static final String NEWS_TITLE = "title";

        public static final String SQL_CREATE_NEWS = "CREATE TABLE " +
                TABLE_NEWS +
                " (" +
                NEWS_ID + " INTEGER PRIMARY KEY," +
                NEWS_DATE + " TEXT," +
                NEWS_DATEUPDATE + " TEXT," +
                NEWS_IMAGEURL + " TEXT," +
                NEWS_SUBTITLE + " TEXT," +
                NEWS_TAGS + " TEXT," +
                NEWS_TEXT + " TEXT," +
                NEWS_TITLE + " TEXT )";

        public static final String SQL_DELETE_NEWS = "DROP TABLE IF EXISTS " + TABLE_NEWS;
    }

    public static abstract class Articles implements BaseColumns {
        public static final Uri CONTENT_URI_ARTICLES = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ARTICLES);

        public static final String CONTENT_LIST_TYPE_ARTICLES =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_ARTICLES;

        public static final String TABLE_ARTICLES = "articles";
        public static final String ARTICLES_ID = "_ID";
        public static final String ARTICLES_ABSTRACTTEXT = "abstractText";
        public static final String ARTICLES_AUTHOR = "author";
        public static final String ARTICLES_DATE = "date";
        public static final String ARTICLES_DATEUPDATE = "dateUpdate";
        public static final String ARTICLES_DESCRIPTION = "description";
        public static final String ARTICLES_IMAGEURL = "imageURL";
        public static final String ARTICLES_TAGS = "tags";
        public static final String ARTICLES_TEXT = "text";
        public static final String ARTICLES_TITLE = "title";

        public static final String SQL_CREATE_ARTICLES = "CREATE TABLE " +
                TABLE_ARTICLES +
                " (" +
                ARTICLES_ID + " INTEGER PRIMARY KEY," +
                ARTICLES_ABSTRACTTEXT + " TEXT," +
                ARTICLES_AUTHOR + " TEXT," +
                ARTICLES_DATE + " TEXT," +
                ARTICLES_DATEUPDATE + " TEXT," +
                ARTICLES_DESCRIPTION + " TEXT," +
                ARTICLES_IMAGEURL + " TEXT," +
                ARTICLES_TAGS + " TEXT," +
                ARTICLES_TEXT + " TEXT," +
                ARTICLES_TITLE + " TEXT )";

        public static final String SQL_DELETE_ARTICLES = "DROP TABLE IF EXISTS " + TABLE_ARTICLES;
    }

    public static abstract class Events implements BaseColumns {
        public static final Uri CONTENT_URI_EVENTS = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_EVENTS);

        public static final String CONTENT_LIST_TYPE_EVENTS =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_EVENTS;
        public static final String CONTENT_ITEM_TYPE_EVENTS =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_EVENTS;

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
    }

    public static abstract class Calendar implements BaseColumns {
        public static final Uri CONTENT_URI_CALENDAR = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CALENDAR);

        public static final String CONTENT_LIST_TYPE_CALENDAR =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_CALENDAR;

        public static final String TABLE_CALENDAR = "calendar";
        public static final String CALENDAR_ID = "_ID";
        public static final String CALENDAR_DATE = "date";
        public static final String CALENDAR_DESCRIPTION = "description";
        public static final String CALENDAR_HOUR = "hour";
        public static final String CALENDAR_IMAGEURL = "imageURL";
        public static final String CALENDAR_NAME = "name";
        public static final String CALENDAR_TAGS = "tags";
        public static final String CALENDAR_VENUE = "venue";

        public static final String SQL_CREATE_CALENDAR = "CREATE TABLE " +
                TABLE_CALENDAR +
                " (" +
                CALENDAR_ID + " INTEGER PRIMARY KEY," +
                CALENDAR_DATE + " TEXT," +
                CALENDAR_DESCRIPTION + " TEXT," +
                CALENDAR_HOUR + " TEXT," +
                CALENDAR_IMAGEURL + " TEXT," +
                CALENDAR_NAME + " TEXT," +
                CALENDAR_TAGS + " TEXT," +
                CALENDAR_VENUE + " TEXT )";

        public static final String SQL_DELETE_CALENDAR = "DROP TABLE IF EXISTS " + TABLE_CALENDAR;
    }

    public static abstract class Faqs implements BaseColumns {
        public static final Uri CONTENT_URI_FAQS = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FAQS);

        public static final String CONTENT_LIST_TYPE_FAQS =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_FAQS;

        public static final String TABLE_FAQS = "faqs";
        public static final String FAQS_ID = "_ID";
        public static final String FAQS_BODY = "body";
        public static final String FAQS_TITLE = "title";

        public static final String SQL_CREATE_FAQS = "CREATE TABLE " +
                TABLE_FAQS +
                " (" +
                FAQS_ID + " INTEGER PRIMARY KEY," +
                FAQS_BODY + " TEXT," +
                FAQS_TITLE + " TEXT )";

        public static final String SQL_DELETE_FAQS = "DROP TABLE IF EXISTS " + TABLE_FAQS;
    }
}
