package hevs.labo.projetandroid.database;

import android.provider.BaseColumns;

/**
 * Created by Anthony on 07/11/2015.
 */
public final class ArtGalleryContract {

    //Default constructor, never used
    public ArtGalleryContract () {}

    //Artist
    public static abstract class Artist implements BaseColumns {
        //Table name
        public static final String TABLE_ARTIST = "artist";

        //Record column name
        public static final String KEY_ID = "id";
        public static final String KEY_FIRSTNAME = "firstname";
        public static final String KEY_LASTNAME = "lastname";
        public static final String KEY_PSEUDO = "pseudo";
        public static final String KEY_BIRTH = "birth";
        public static final String KEY_DEATH = "death";
        public static final String KEY_MOVEMENT = "movement";
        public static final String KEY_EXPOSED = "exposed";
        public static final String KEY_IMAGE_PATH = "image_path";

        //Table artist create statement
        public static final String CREATE_TABLE_ARTIST = "CREATE TABLE "
                + TABLE_ARTIST + "("
                + Artist.KEY_ID + " INTEGER PRIMARY KEY, "
                + Artist.KEY_FIRSTNAME + " TEXT, "
                + Artist.KEY_LASTNAME + " TEXT, "
                + Artist.KEY_PSEUDO + " TEXT, "
                + Artist.KEY_BIRTH + " DATETIME, "
                + Artist.KEY_DEATH + " DATETIME, "
                + Artist.KEY_MOVEMENT + " TEXT, "
                + Artist.KEY_EXPOSED + " BOOLEAN, "
                + Artist.KEY_IMAGE_PATH + " TEXT"
                + ");";
    }

    //Artwork
    public static abstract class Artwork implements BaseColumns {
        //Table name
        public static final String TABLE_ARTWORK = "artwork";

        //Record column name
        public static final String KEY_ID = "id";
        public static final String KEY_NAME = "name";
        public static final String KEY_TYPE = "type";
        public static final String KEY_CREATION_YEAR = "creation_year";
        public static final String KEY_DESCRIPTION = "description";
        public static final String KEY_EXPOSED = "exposed";
        public static final String KEY_IMAGE_PATH = "image_path";
        public static final String KEY_ARTIST_ID = "artist_id";
        public static final String KEY_ROOM_ID = "room_id";

        //Table artist create statement
        public static final String CREATE_TABLE_ARTWORK = "CREATE TABLE "
                + TABLE_ARTWORK + "("
                + Artwork.KEY_ID + " INTEGER PRIMARY KEY, "
                + Artwork.KEY_NAME + " TEXT, "
                + Artwork.KEY_TYPE + " TEXT, "
                + Artwork.KEY_CREATION_YEAR + " TEXT, "
                + Artwork.KEY_DESCRIPTION + " TEXT, "
                + Artwork.KEY_EXPOSED + " BOOLEAN, "
                + Artwork.KEY_IMAGE_PATH + " TEXT, "
                + Artwork.KEY_ARTIST_ID + " INTEGER, "
                + Artwork.KEY_ROOM_ID + " INTEGER, "
                + "FOREIGN KEY (" + Artwork.KEY_ARTIST_ID + ") REFERENCES " + Artist.TABLE_ARTIST + " (" + Artist.KEY_ID + "), "
                + "FOREIGN KEY (" + Artwork.KEY_ROOM_ID + ") REFERENCES " + Room.TABLE_ROOM + " (" + Room.KEY_ID + ")"
                + ");";
    }

    //Room
    public static abstract class Room implements BaseColumns {
        //Table name
        public static final String TABLE_ROOM = "room";

        //Record column name
        public static final String KEY_ID = "id";
        public static final String KEY_NAME = "name";
        public static final String KEY_SIZE = "size";
        public static final String KEY_IMAGE_PATH = "image_path";
        public static final String KEY_OCCUPATED = "occupated";

        //Table artist create statement
        public static final String CREATE_TABLE_ROOM = "CREATE TABLE "
                + TABLE_ROOM + "("
                + Room.KEY_ID + " INTEGER PRIMARY KEY, "
                + Room.KEY_NAME + " TEXT, "
                + Room.KEY_SIZE + " DOUBLE, "
                + Room.KEY_OCCUPATED + " BOOLEAN, "
                + Room.KEY_IMAGE_PATH + " TEXT "
                + ");";
    }
}
