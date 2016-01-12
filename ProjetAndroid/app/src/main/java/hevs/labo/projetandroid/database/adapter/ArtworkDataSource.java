package hevs.labo.projetandroid.database.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hevs.labo.projetandroid.database.ArtGalleryContract;
import hevs.labo.projetandroid.database.SQLiteHelper;
import hevs.labo.projetandroid.database.object.Artwork;

/**
 * Created by Darlène on 07.11.2015.
 */
public class ArtworkDataSource {

    private SQLiteDatabase db;
    private Context context;


    public ArtworkDataSource(Context context){
        SQLiteHelper sqLiteHelper = SQLiteHelper.getInstance(context);
        db = sqLiteHelper.getWritableDatabase();
        this.context = context;
    }

    /**
     * Insert new artwork
     */
    public long createArtwork(Artwork artwork) {
        long id;
        ContentValues values = new ContentValues();

        values.put(ArtGalleryContract.Artwork.KEY_NAME, artwork.getName());
        values.put(ArtGalleryContract.Artwork.KEY_CREATION_YEAR, artwork.getCreationYear());
        values.put(ArtGalleryContract.Artwork.KEY_TYPE, artwork.getType());
        values.put(ArtGalleryContract.Artwork.KEY_DESCRIPTION, artwork.getDescription());
        values.put(ArtGalleryContract.Artwork.KEY_EXPOSED, artwork.getExposed());
        values.put(ArtGalleryContract.Artwork.KEY_IMAGE_PATH, artwork.getImage_path());
        values.put(ArtGalleryContract.Artwork.KEY_ARTIST_ID, artwork.getForeign_key_Artist_id());
        values.put(ArtGalleryContract.Artwork.KEY_ROOM_ID, artwork.getForeign_key_Room_id());

        id = this.db.insert(ArtGalleryContract.Artwork.TABLE_ARTWORK, null, values);

        return id;
    }

    /**
     * Find one artwork by id
     */
    public Artwork getArtworkById(long id) {
        String sql = "SELECT * FROM " + ArtGalleryContract.Artwork.TABLE_ARTWORK +
                " WHERE " + ArtGalleryContract.Artwork.KEY_ID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Artwork artwork = new Artwork();
        artwork.setId(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_ID)));
        artwork.setName(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_NAME)));
        artwork.setType(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_TYPE)));
        artwork.setCreationYear(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_CREATION_YEAR)));
        artwork.setDescription(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_DESCRIPTION)));
        artwork.setImage_path(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_IMAGE_PATH)));
        artwork.setExposed(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_EXPOSED)) == 1);
        artwork.setForeign_key_Artist_id(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_ARTIST_ID)));
        artwork.setForeign_key_Room_id(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_ROOM_ID)));

        return artwork;
    }

    /**
     * Get all artwork
     */
    public List<Artwork> getAllArtworksByArtist(int id_Artist) {
        List<Artwork> artworks = new ArrayList<Artwork>();
        String sql = "SELECT * FROM " + ArtGalleryContract.Artwork.TABLE_ARTWORK +
                " WHERE " + ArtGalleryContract.Artwork.KEY_ARTIST_ID + " = " + id_Artist +
        " ORDER BY " + ArtGalleryContract.Artwork.KEY_NAME;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {
                Artwork artwork = new Artwork();

                artwork.setId(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_ID)));
                artwork.setName(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_NAME)));
                artwork.setType(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_TYPE)));
                artwork.setCreationYear(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_CREATION_YEAR)));
                artwork.setDescription(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_DESCRIPTION)));
                artwork.setImage_path(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_IMAGE_PATH)));
                artwork.setExposed(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_EXPOSED)) == 1);
                artwork.setForeign_key_Artist_id(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_ARTIST_ID)));
                artwork.setForeign_key_Room_id(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_ROOM_ID)));

                artworks.add(artwork);
            }
            while(cursor.moveToNext());
        }

        return artworks;
    }

    public List<Artwork> getAllArtworks() {
        List<Artwork> artworks = new ArrayList<Artwork>();
        String sql = "SELECT * FROM " + ArtGalleryContract.Artwork.TABLE_ARTWORK +
                " ORDER BY " + ArtGalleryContract.Artwork.KEY_NAME;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {
                Artwork artwork = new Artwork();

                artwork.setId(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_ID)));
                artwork.setName(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_NAME)));
                artwork.setType(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_TYPE)));
                artwork.setCreationYear(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_CREATION_YEAR)));
                artwork.setDescription(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_DESCRIPTION)));
                artwork.setImage_path(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_IMAGE_PATH)));
                artwork.setExposed(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_EXPOSED)) == 1);
                artwork.setForeign_key_Artist_id(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_ARTIST_ID)));
                artwork.setForeign_key_Room_id(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_ROOM_ID)));

                artworks.add(artwork);
            }
            while(cursor.moveToNext());
        }

        return artworks;
    }


    public List<Artwork> getArtworksExposed() {
        List<Artwork> artworks = new ArrayList<Artwork>();
        String sql = "SELECT * FROM " + ArtGalleryContract.Artwork.TABLE_ARTWORK +
                " WHERE " + ArtGalleryContract.Artwork.KEY_EXPOSED + " = 1" +
                " ORDER BY " + ArtGalleryContract.Artwork.KEY_NAME;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {
                Artwork artwork = new Artwork();

                artwork.setId(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_ID)));
                artwork.setName(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_NAME)));
                artwork.setType(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_TYPE)));
                artwork.setCreationYear(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_CREATION_YEAR)));
                artwork.setDescription(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_DESCRIPTION)));
                artwork.setImage_path(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_IMAGE_PATH)));
                artwork.setExposed(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_EXPOSED)) == 1);
                artwork.setForeign_key_Artist_id(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_ARTIST_ID)));
                artwork.setForeign_key_Room_id(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_ROOM_ID)));

                artworks.add(artwork);
            }
            while(cursor.moveToNext());
        }

        return artworks;
    }

    public List<Artwork> getArtworksNonExposed() {
        List<Artwork> artworks = new ArrayList<Artwork>();
        String sql = "SELECT * FROM " + ArtGalleryContract.Artwork.TABLE_ARTWORK +
                " WHERE " + ArtGalleryContract.Artwork.KEY_EXPOSED + " = 0" +
                " ORDER BY " + ArtGalleryContract.Artwork.KEY_NAME;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {
                Artwork artwork = new Artwork();

                artwork.setId(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_ID)));
                artwork.setName(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_NAME)));
                artwork.setType(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_TYPE)));
                artwork.setCreationYear(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_CREATION_YEAR)));
                artwork.setDescription(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_DESCRIPTION)));
                artwork.setImage_path(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_IMAGE_PATH)));
                artwork.setExposed(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_EXPOSED)) == 1);
                artwork.setForeign_key_Artist_id(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_ARTIST_ID)));
                artwork.setForeign_key_Room_id(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Artwork.KEY_ROOM_ID)));

                artworks.add(artwork);
            }
            while(cursor.moveToNext());
        }

        return artworks;
    }

    /**
     *  Update an artwork
     */
    public int updateArtwork(Artwork artwork){
        ContentValues values = new ContentValues();

        values.put(ArtGalleryContract.Artwork.KEY_NAME, artwork.getName());
        values.put(ArtGalleryContract.Artwork.KEY_TYPE, artwork.getType());
        values.put(ArtGalleryContract.Artwork.KEY_CREATION_YEAR, artwork.getCreationYear());
        values.put(ArtGalleryContract.Artwork.KEY_DESCRIPTION, artwork.getDescription());
        values.put(ArtGalleryContract.Artwork.KEY_IMAGE_PATH, artwork.getImage_path());
        values.put(ArtGalleryContract.Artwork.KEY_EXPOSED, artwork.getExposed());
        values.put(ArtGalleryContract.Artwork.KEY_ARTIST_ID, artwork.getForeign_key_Artist_id());
        values.put(ArtGalleryContract.Artwork.KEY_ROOM_ID, artwork.getForeign_key_Room_id());

        return this.db.update(ArtGalleryContract.Artwork.TABLE_ARTWORK, values, ArtGalleryContract.Artwork.KEY_ID + " = ?",
                new String[] { String.valueOf(artwork.getId()) });
    }

    /**
     * Delete an artwork
     */
    public void deleteArtwork(long id){

        //delete the artwork
        this.db.delete(ArtGalleryContract.Artwork.TABLE_ARTWORK, ArtGalleryContract.Artwork.KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}
