package hevs.labo.projetandroid;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import hevs.labo.projetandroid.database.SQLiteHelper;
import hevs.labo.projetandroid.database.adapter.RoomDataSource;
import hevs.labo.projetandroid.database.object.Room;

public class Create_room extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_ROOM_IMAGE = 1;

    private Room room;

    //Gérer image room
    private Uri selectedImageRoom;
    private Bitmap bitmap;
    private boolean isPicture = false;

    private ImageButton saveRoom;
    private ImageView imageRoom;

    private EditText name;
    private EditText size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        saveRoom = (ImageButton) findViewById(R.id.imageButton_btnDownloadRoomCreate);
        imageRoom = (ImageView) findViewById(R.id.imageView_photoRoomCreate);

        saveRoom.setOnClickListener(this);
        imageRoom.setOnClickListener(this);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (requestCode == RESULT_LOAD_ROOM_IMAGE && resultCode == RESULT_OK && null != data) {

                selectedImageRoom = data.getData();
                imageRoom.setImageURI(selectedImageRoom);
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageRoom);

                isPicture = true;
            } else {
                Toast.makeText(this, R.string.noImagePicked, Toast.LENGTH_LONG).show();
            }
        }catch (Exception e ){
            Log.e("error", e.toString());
            Toast.makeText(this, R.string.stgWentWrong, Toast.LENGTH_LONG).show();
        }

    }

    private String saveToInternalStorage(Bitmap bitmapImage) {



        Random rd = new Random();
        int randomnum = 1+ (int)(Math.random()*4000);

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, randomnum+".jpg");

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mypath.getPath();
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.imageView_photoRoomCreate:
                onLoad();
                break;
            case R.id.imageButton_btnDownloadRoomCreate:
                onLoad();
                break;
        }
    }

    private void onLoad() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_ROOM_IMAGE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_room, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){

            case R.id.rooms_menu:
                Intent intenthome = new Intent(this, List_room.class);
                startActivity(intenthome);
                return true;

            case R.id.cancelroomcreated_menu:
                Intent intentCancel = new Intent(this, List_room.class);
                startActivity(intentCancel);
                return true;

            case R.id.saveroomcreated_menu:

                String imagepath = saveToInternalStorage(bitmap);
                Context context = getApplicationContext();
               /* int duration = Toast.LENGTH_SHORT;
                Toast toastpict = Toast.makeText(context, imagepath, duration);
                toastpict.show();*/

                room = new Room();
                RoomDataSource roomDataSource = new RoomDataSource(this);


                EditText et = (EditText) findViewById(R.id.et_create_room_name);
                room.setName(et.getText().toString());

                et = (EditText) findViewById(R.id.et_create_room_size);
                room.setSize(Double.parseDouble(et.getText().toString()));

                room.setImage_path(imagepath);

                room.setSelected(false);

                room.setId((int) roomDataSource.createRoom(room));

                SQLiteHelper sqlHelper = SQLiteHelper.getInstance(this);
                sqlHelper.getWritableDatabase().close();

                startActivity(new Intent(this, List_room.class));

                Toast toast = Toast.makeText(this, R.string.roomAdded, Toast.LENGTH_LONG);
                toast.show();

                return true;
        }

        return (super.onOptionsItemSelected(item));
    }
}
