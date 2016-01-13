package hevs.labo.projetandroid.database.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hevs.labo.projetandroid.database.ArtGalleryContract;
import hevs.labo.projetandroid.database.SQLiteHelper;
import hevs.labo.projetandroid.database.object.Sync;

/**
 * Created by Anthony on 12/01/2016.
 */
public class SyncDataSource {
    private SQLiteDatabase db;
    private Context context;

    public SyncDataSource(Context context){
        SQLiteHelper sqliteHelper = SQLiteHelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
        this.context = context;
    }

    /**
     * Insert new sync
     */
    public long createSync(Sync sync) {
        long id;
        ContentValues values = new ContentValues();
        values.put(ArtGalleryContract.Sync.KEY_OBJECT_ID, sync.getObjectId());
        values.put(ArtGalleryContract.Sync.KEY_OBJECT_TYPE, sync.getType().toString());
        values.put(ArtGalleryContract.Sync.KEY_OBJECT_TABLE, sync.getTable().toString());

        id = this.db.insert(ArtGalleryContract.Sync.TABLE_SYNC, null, values);

        return id;
    }

    /**
     * Get insert artist sync
     */
    public List<Sync> getSyncInsertArtist() {
        List<Sync> syncList = new ArrayList<Sync>();
        String sql = "SELECT * FROM " + ArtGalleryContract.Sync.TABLE_SYNC
                + " WHERE " + ArtGalleryContract.Sync.KEY_OBJECT_TYPE + " = '" + Sync.Type.insert + "' "
                + " AND " + ArtGalleryContract.Sync.KEY_OBJECT_TABLE + " = '" + Sync.Table.artist + "' "
                + " ORDER BY " + ArtGalleryContract.Sync.KEY_OBJECT_ID;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {
                Sync sync = new Sync();

                sync.setId(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_ID)));
                sync.setObjectId(cursor.getLong(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_ID)));
                sync.setTable(Sync.Table.valueOf(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_TABLE))));
                sync.setType(Sync.Type.valueOf(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_TYPE))));

                syncList.add(sync);
            }
            while(cursor.moveToNext());
        }

        return syncList;
    }

    /**
     * Get insert artwork sync
     */
    public List<Sync> getSyncInsertArtwork() {
        List<Sync> syncList = new ArrayList<Sync>();
        String sql = "SELECT * FROM " + ArtGalleryContract.Sync.TABLE_SYNC
                + " WHERE " + ArtGalleryContract.Sync.KEY_OBJECT_TYPE + " = '" + Sync.Type.insert + "' "
                + " AND " + ArtGalleryContract.Sync.KEY_OBJECT_TABLE + " = '" + Sync.Table.artwork + "' "
                + " ORDER BY " + ArtGalleryContract.Sync.KEY_OBJECT_ID;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {
                Sync sync = new Sync();

                sync.setId(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_ID)));
                sync.setObjectId(cursor.getLong(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_ID)));
                sync.setTable(Sync.Table.valueOf(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_TABLE))));
                sync.setType(Sync.Type.valueOf(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_TYPE))));

                syncList.add(sync);
            }
            while(cursor.moveToNext());
        }

        return syncList;
    }

    /**
     * Get insert room sync
     */
    public List<Sync> getSyncInsertRoom() {
        List<Sync> syncList = new ArrayList<Sync>();
        String sql = "SELECT * FROM " + ArtGalleryContract.Sync.TABLE_SYNC
                + " WHERE " + ArtGalleryContract.Sync.KEY_OBJECT_TYPE + " = '" + Sync.Type.insert + "' "
                + " AND " + ArtGalleryContract.Sync.KEY_OBJECT_TABLE + " = '" + Sync.Table.room + "' "
                + " ORDER BY " + ArtGalleryContract.Sync.KEY_OBJECT_ID;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {
                Sync sync = new Sync();

                sync.setId(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_ID)));
                sync.setObjectId(cursor.getLong(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_ID)));
                sync.setTable(Sync.Table.valueOf(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_TABLE))));
                sync.setType(Sync.Type.valueOf(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_TYPE))));

                syncList.add(sync);
            }
            while(cursor.moveToNext());
        }

        return syncList;
    }

    /**
     * Get update artist sync
     */
    public List<Sync> getSyncUpdateArtist() {
        List<Sync> syncList = new ArrayList<Sync>();
        String sql = "SELECT * FROM " + ArtGalleryContract.Sync.TABLE_SYNC
                + " WHERE " + ArtGalleryContract.Sync.KEY_OBJECT_TYPE + " = '" + Sync.Type.update + "' "
                + " AND " + ArtGalleryContract.Sync.KEY_OBJECT_TABLE + " = '" + Sync.Table.artist + "' "
                + " ORDER BY " + ArtGalleryContract.Sync.KEY_OBJECT_ID;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {
                Sync sync = new Sync();

                sync.setId(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_ID)));
                sync.setObjectId(cursor.getLong(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_ID)));
                sync.setTable(Sync.Table.valueOf(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_TABLE))));
                sync.setType(Sync.Type.valueOf(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_TYPE))));

                syncList.add(sync);
            }
            while(cursor.moveToNext());
        }

        return syncList;
    }

    /**
     * Get update artwork sync
     */
    public List<Sync> getSyncUpdateArtwork() {
        List<Sync> syncList = new ArrayList<Sync>();
        String sql = "SELECT * FROM " + ArtGalleryContract.Sync.TABLE_SYNC
                + " WHERE " + ArtGalleryContract.Sync.KEY_OBJECT_TYPE + " = '" + Sync.Type.update + "' "
                + " AND " + ArtGalleryContract.Sync.KEY_OBJECT_TABLE + " = '" + Sync.Table.artwork + "' "
                + " ORDER BY " + ArtGalleryContract.Sync.KEY_OBJECT_ID;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {
                Sync sync = new Sync();

                sync.setId(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_ID)));
                sync.setObjectId(cursor.getLong(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_ID)));
                sync.setTable(Sync.Table.valueOf(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_TABLE))));
                sync.setType(Sync.Type.valueOf(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_TYPE))));

                syncList.add(sync);
            }
            while(cursor.moveToNext());
        }

        return syncList;
    }

    /**
     * Get update room sync
     */
    public List<Sync> getSyncUpdateRoom() {
        List<Sync> syncList = new ArrayList<Sync>();
        String sql = "SELECT * FROM " + ArtGalleryContract.Sync.TABLE_SYNC
                + " WHERE " + ArtGalleryContract.Sync.KEY_OBJECT_TYPE + " = '" + Sync.Type.update + "' "
                + " AND " + ArtGalleryContract.Sync.KEY_OBJECT_TABLE + " = '" + Sync.Table.room + "' "
                + " ORDER BY " + ArtGalleryContract.Sync.KEY_OBJECT_ID;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {
                Sync sync = new Sync();

                sync.setId(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_ID)));
                sync.setObjectId(cursor.getLong(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_ID)));
                sync.setTable(Sync.Table.valueOf(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_TABLE))));
                sync.setType(Sync.Type.valueOf(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_TYPE))));

                syncList.add(sync);
            }
            while(cursor.moveToNext());
        }

        return syncList;
    }

    /**
     * Get delete artist sync
     */
    public List<Sync> getSyncDeleteArtist() {
        List<Sync> syncList = new ArrayList<Sync>();
        String sql = "SELECT * FROM " + ArtGalleryContract.Sync.TABLE_SYNC
                + " WHERE " + ArtGalleryContract.Sync.KEY_OBJECT_TYPE + " = '" + Sync.Type.delete + "' "
                + " AND " + ArtGalleryContract.Sync.KEY_OBJECT_TABLE + " = '" + Sync.Table.artist + "' "
                + " ORDER BY " + ArtGalleryContract.Sync.KEY_OBJECT_ID;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {
                Sync sync = new Sync();

                sync.setId(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_ID)));
                sync.setObjectId(cursor.getLong(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_ID)));
                sync.setTable(Sync.Table.valueOf(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_TABLE))));
                sync.setType(Sync.Type.valueOf(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_TYPE))));

                syncList.add(sync);
            }
            while(cursor.moveToNext());
        }

        return syncList;
    }

    /**
     * Get delete artwork sync
     */
    public List<Sync> getSyncDeleteArtwork() {
        List<Sync> syncList = new ArrayList<Sync>();
        String sql = "SELECT * FROM " + ArtGalleryContract.Sync.TABLE_SYNC
                + " WHERE " + ArtGalleryContract.Sync.KEY_OBJECT_TYPE + " = '" + Sync.Type.delete + "' "
                + " AND " + ArtGalleryContract.Sync.KEY_OBJECT_TABLE + " = '" + Sync.Table.artwork + "' "
                + " ORDER BY " + ArtGalleryContract.Sync.KEY_OBJECT_ID;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {
                Sync sync = new Sync();

                sync.setId(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_ID)));
                sync.setObjectId(cursor.getLong(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_ID)));
                sync.setTable(Sync.Table.valueOf(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_TABLE))));
                sync.setType(Sync.Type.valueOf(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_TYPE))));

                syncList.add(sync);
            }
            while(cursor.moveToNext());
        }

        return syncList;
    }

    /**
     * Get delete room sync
     */
    public List<Sync> getSyncDeleteRoom() {
        List<Sync> syncList = new ArrayList<Sync>();
        String sql = "SELECT * FROM " + ArtGalleryContract.Sync.TABLE_SYNC
                + " WHERE " + ArtGalleryContract.Sync.KEY_OBJECT_TYPE + " = '" + Sync.Type.delete + "' "
                + " AND " + ArtGalleryContract.Sync.KEY_OBJECT_TABLE + " = '" + Sync.Table.room + "' "
                + " ORDER BY " + ArtGalleryContract.Sync.KEY_OBJECT_ID;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {
                Sync sync = new Sync();

                sync.setId(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_ID)));
                sync.setObjectId(cursor.getLong(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_ID)));
                sync.setTable(Sync.Table.valueOf(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_TABLE))));
                sync.setType(Sync.Type.valueOf(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Sync.KEY_OBJECT_TYPE))));

                syncList.add(sync);
            }
            while(cursor.moveToNext());
        }

        return syncList;
    }

    /**
     *  Update a sync
     */
    public int updateSync(Sync sync){
        ContentValues values = new ContentValues();

        values.put(ArtGalleryContract.Sync.KEY_OBJECT_TYPE, sync.getType().toString());
        values.put(ArtGalleryContract.Sync.KEY_OBJECT_TABLE, sync.getTable().toString());
        values.put(ArtGalleryContract.Sync.KEY_OBJECT_ID, sync.getObjectId());

        return this.db.update(ArtGalleryContract.Sync.TABLE_SYNC, values, ArtGalleryContract.Sync.KEY_ID + " = ?",
                new String[] { String.valueOf(sync.getId()) });
    }

    /**
     * Delete a sync
     */
    public void deleteSync(long id){
        this.db.delete(ArtGalleryContract.Sync.TABLE_SYNC, ArtGalleryContract.Sync.KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}
