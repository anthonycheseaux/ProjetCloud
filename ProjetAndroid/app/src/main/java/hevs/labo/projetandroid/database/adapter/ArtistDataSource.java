package hevs.labo.projetandroid.database.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hevs.labo.projetandroid.database.ArtGalleryContract;
import hevs.labo.projetandroid.database.SQLiteHelper;
import hevs.labo.projetandroid.database.object.Artist;

/**
 * Created by Anthony on 07/11/2015.
 */
public class ArtistDataSource {
    private SQLiteDatabase db;
    private Context context;

    public ArtistDataSource(Context context){
        SQLiteHelper sqliteHelper = SQLiteHelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
        this.context = context;
    }

    /**
     * Insert new artist
     */
    public long createArtist(Artist artist) {
        long id;
        ContentValues values = new ContentValues();
        values.put(ArtGalleryContract.Artist.KEY_FIRSTNAME, artist.getFirstname());
        values.put(ArtGalleryContract.Artist.KEY_LASTNAME, artist.getLastname());
        values.put(ArtGalleryContract.Artist.KEY_PSEUDO, artist.getPseudo());
        values.put(ArtGalleryContract.Artist.KEY_BIRTH, artist.getBirth());
        values.put(ArtGalleryContract.Artist.KEY_DEATH, artist.getDeath());
        values.put(ArtGalleryContract.Artist.KEY_MOVEMENT, artist.getMovement());
        values.put(ArtGalleryContract.Artist.KEY_IMAGE_PATH, artist.getImage_path());
        values.put(ArtGalleryContract.Artist.KEY_EXPOSED, artist.isExposed());

        id = this.db.insert(ArtGalleryContract.Artist.TABLE_ARTIST, null, values);

        return id;
    }

    /**
     * Find one Artist by id
     */
    public Artist getArtistById(long id) {
        String sql = "SELECT * FROM " + ArtGalleryContract.Artist.TABLE_ARTIST +
                " WHERE " + ArtGalleryContract.Artist.KEY_ID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Artist artist = new Artist();
        artist.setId(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_ID)));
        artist.setFirstname(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_FIRSTNAME)));
        artist.setLastname(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_LASTNAME)));
        artist.setPseudo(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_PSEUDO)));
        artist.setBirth(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_BIRTH)));
        artist.setDeath(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_DEATH)));
        artist.setMovement(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_MOVEMENT)));
        artist.setImage_path(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_IMAGE_PATH)));
        artist.setExposed(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_EXPOSED)) == 1);

        return artist;
    }

    public Artist getArtistByName(String name){
        String sql = "SELECT * FROM " + ArtGalleryContract.Artist.TABLE_ARTIST +
                " WHERE " + ArtGalleryContract.Artist.KEY_FIRSTNAME + " = " + name;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Artist artist = new Artist();
        artist.setId(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_ID)));
        artist.setFirstname(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_FIRSTNAME)));
        artist.setLastname(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_LASTNAME)));
        artist.setPseudo(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_PSEUDO)));
        artist.setBirth(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_BIRTH)));
        artist.setDeath(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_DEATH)));
        artist.setMovement(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_MOVEMENT)));
        artist.setImage_path(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_IMAGE_PATH)));
        artist.setExposed(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_EXPOSED)) == 1);

        return artist;
    }
    /**
     * Get all artists
     */
    public List<Artist> getAllArtists() {
        List<Artist> artists = new ArrayList<Artist>();
        String sql = "SELECT * FROM " + ArtGalleryContract.Artist.TABLE_ARTIST + " ORDER BY "
                + ArtGalleryContract.Artist.KEY_FIRSTNAME +", " + ArtGalleryContract.Artist.KEY_LASTNAME;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {
                Artist artist = new Artist();

                artist.setId(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_ID)));
                artist.setFirstname(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_FIRSTNAME)));
                artist.setLastname(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_LASTNAME)));
                artist.setPseudo(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_PSEUDO)));
                artist.setBirth(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_BIRTH)));
                artist.setDeath(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_DEATH)));
                artist.setMovement(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_MOVEMENT)));
                artist.setImage_path(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_IMAGE_PATH)));
                artist.setExposed(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artist.KEY_EXPOSED)) == 1);

                artists.add(artist);
            }
            while(cursor.moveToNext());
        }

        return artists;
    }

    /**
     *  Update an artist
     */
    public int updateArtist(Artist artist){
        ContentValues values = new ContentValues();

        values.put(ArtGalleryContract.Artist.KEY_FIRSTNAME, artist.getFirstname());
        values.put(ArtGalleryContract.Artist.KEY_LASTNAME, artist.getLastname());
        values.put(ArtGalleryContract.Artist.KEY_PSEUDO, artist.getPseudo());
        values.put(ArtGalleryContract.Artist.KEY_BIRTH, artist.getBirth());
        values.put(ArtGalleryContract.Artist.KEY_DEATH, artist.getDeath());
        values.put(ArtGalleryContract.Artist.KEY_MOVEMENT, artist.getMovement());
        values.put(ArtGalleryContract.Artist.KEY_IMAGE_PATH, artist.getImage_path());
        values.put(ArtGalleryContract.Artist.KEY_EXPOSED, artist.isExposed());

        return this.db.update(ArtGalleryContract.Artist.TABLE_ARTIST, values, ArtGalleryContract.Artist.KEY_ID + " = ?",
                new String[] { String.valueOf(artist.getId()) });
    }

    /**
     * Delete an artist this will also delete all the artwork rrelated to this artist
     */
    public void deleteArtist(long id){

        //before deleting an Artist we should delete all Artwork from this Artist
        //because the table Artwork contain the id of the Artist as foreign key

        //delete Artworks from this Artist
        this.db.delete(ArtGalleryContract.Artwork.TABLE_ARTWORK, ArtGalleryContract.Artwork.KEY_ARTIST_ID + " = ?",
                new  String[]{String.valueOf(id)});

        //delete this Artist
        this.db.delete(ArtGalleryContract.Artist.TABLE_ARTIST, ArtGalleryContract.Artist.KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}





