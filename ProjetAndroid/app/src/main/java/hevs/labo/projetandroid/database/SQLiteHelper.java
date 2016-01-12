package hevs.labo.projetandroid.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anthony on 07/11/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    //Infos about the database
    private static final String DATABASE_NAME = "art_gallery";
    private static final int DATABASE_VERSION = 1;
    private static SQLiteHelper instance;

    //use a singleton, only one instance of the db
    private SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    public static SQLiteHelper getInstance (Context context) {
        if(instance == null) {
            instance = new SQLiteHelper(context.getApplicationContext());
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ArtGalleryContract.Artist.CREATE_TABLE_ARTIST);
        db.execSQL(ArtGalleryContract.Room.CREATE_TABLE_ROOM);
        db.execSQL(ArtGalleryContract.Artwork.CREATE_TABLE_ARTWORK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop old db
        db.execSQL("DROP TABLE IF EXISTS " + ArtGalleryContract.Artist.TABLE_ARTIST);
        db.execSQL("DROP TABLE IF EXISTS " + ArtGalleryContract.Room.TABLE_ROOM);
        db.execSQL("DROP TABLE IF EXISTS " + ArtGalleryContract.Artwork.TABLE_ARTWORK);

        //create new tables
        onCreate(db);
    }
}
