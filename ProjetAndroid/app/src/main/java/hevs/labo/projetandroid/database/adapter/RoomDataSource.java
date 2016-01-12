package hevs.labo.projetandroid.database.adapter;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hevs.labo.projetandroid.database.ArtGalleryContract;
import hevs.labo.projetandroid.database.SQLiteHelper;
import hevs.labo.projetandroid.database.object.Room;

/**
 * Created by Darlène on 07.11.2015.
 */
public class RoomDataSource {

    private SQLiteDatabase db;
    private Context context;

    public RoomDataSource(Context context){
        SQLiteHelper sqLiteHelper = SQLiteHelper.getInstance(context);
        db = sqLiteHelper.getWritableDatabase();
        this.context = context;
    }


    /**
     * Insert a new Room
     */

    public long createRoom(Room room){
        long id;
        ContentValues values = new ContentValues();
        values.put(ArtGalleryContract.Room.KEY_NAME, room.getName());
        values.put(ArtGalleryContract.Room.KEY_SIZE, room.getSize());
        values.put(ArtGalleryContract.Room.KEY_OCCUPATED, room.isSelected());
        values.put(ArtGalleryContract.Room.KEY_IMAGE_PATH, room.getImage_path());

        id= this.db.insert(ArtGalleryContract.Room.TABLE_ROOM, null, values);
        return id;
    }


    /**
     * Find one Room by id
     */

    public Room getRoomById (long id){
        String sql = "SELECT * FROM " + ArtGalleryContract.Room.TABLE_ROOM +
                        " WHERE " + ArtGalleryContract.Room.KEY_ID + " = " +id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Room room = new Room();
        room.setId(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Room.KEY_ID)));
        room.setName(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Room.KEY_NAME)));
        room.setSize(cursor.getDouble(cursor.getColumnIndex(ArtGalleryContract.Room.KEY_SIZE)));
        room.setSelected(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Room.KEY_OCCUPATED)) != 0);
        room.setImage_path(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Room.KEY_IMAGE_PATH)));

        return room;
    }

    /**
     * Get All Rooms
     */

    public List<Room> getAllRooms(){
        List<Room> rooms = new ArrayList<>();

        String sql = "SELECT * FROM " + ArtGalleryContract.Room.TABLE_ROOM + " ORDER BY " +ArtGalleryContract.Room.KEY_NAME;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do {
                Room room = new Room();
                room.setId(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Room.KEY_ID)));
                room.setName(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Room.KEY_NAME)));
                room.setSize(cursor.getDouble(cursor.getColumnIndex(ArtGalleryContract.Room.KEY_SIZE)));
                room.setSelected(cursor.getInt(cursor.getColumnIndex(ArtGalleryContract.Room.KEY_OCCUPATED)) != 0);
                room.setImage_path(cursor.getString(cursor.getColumnIndex(ArtGalleryContract.Room.KEY_IMAGE_PATH)));

                rooms.add(room);

            }while (cursor.moveToNext());
        }

        return rooms;
    }


    /**
     * Update a room
     */

    public int updateRoom (Room room){

        ContentValues values = new ContentValues();

        values.put(ArtGalleryContract.Room.KEY_NAME, room.getName());
        values.put(ArtGalleryContract.Room.KEY_SIZE, room.getSize());
        values.put(ArtGalleryContract.Room.KEY_OCCUPATED, room.isSelected());
        values.put(ArtGalleryContract.Room.KEY_IMAGE_PATH, room.getImage_path());

        return this.db.update(ArtGalleryContract.Room.TABLE_ROOM, values, ArtGalleryContract.Room.KEY_ID + " = ?",
                new String[] {String.valueOf(room.getId())});
    }


    /**
     * Delete a Room - this will also delete all records for the room
     */

     public void deleteRoom(long id)
     {
         this.db.delete(ArtGalleryContract.Room.TABLE_ROOM, ArtGalleryContract.Room.KEY_ID + " = ?",
                 new String[]{String.valueOf(id)});
     }
}
