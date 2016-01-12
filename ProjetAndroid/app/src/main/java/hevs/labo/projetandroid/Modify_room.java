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

public class Modify_room extends AppCompatActivity {

    private EditText roomName;
    private EditText sizeRoom;
    private ImageButton btn_changePictureRoom;
    private ImageView pictureRoomToModify;
    private Room roomToModify;
    private Uri selectedImageRoom;
    private Bitmap bitmap;
    private boolean isPicture = false;
    private CheckBox checkoccupatedRoom;
    private int id_room_modif;

    private static final int RESULT_LOAD_ROOM_IMAGE_MODIFY = 1;
    private  int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_room);

        RoomDataSource roomDataSource = new RoomDataSource(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            id =extras.getInt("id_RoomRecup");
        }

        roomToModify = roomDataSource.getRoomById(id);

        btn_changePictureRoom = (ImageButton) findViewById(R.id.imageButton_btnDownloadRoomModify);
        btn_changePictureRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_ROOM_IMAGE_MODIFY);
            }
        });


        roomName = (EditText) findViewById(R.id.et_modify_room_name);
        roomName.setText(roomToModify.getName());

        sizeRoom = (EditText) findViewById(R.id.et_modify_room_size);
        sizeRoom.setText(String.valueOf(roomToModify.getSize()));

        pictureRoomToModify = (ImageView) findViewById(R.id.imageView_photoRoomModify);
        File imgFile = new  File(roomToModify.getImage_path());

        if(imgFile.exists()) {
            Context context = getApplicationContext();
            Uri uri = Uri.fromFile(imgFile);
            pictureRoomToModify.setImageURI(uri);
        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (requestCode == RESULT_LOAD_ROOM_IMAGE_MODIFY && resultCode == RESULT_OK && null != data) {

                selectedImageRoom = data.getData();
                pictureRoomToModify.setImageURI(selectedImageRoom);
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
        int nameRoom = roomToModify.getId();

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "artiste"+nameRoom+".jpg");

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modify_room, menu);
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


            case R.id.saveroommodified_menu:

                String imagepath ="";

                if(isPicture == true){
                    imagepath = saveToInternalStorage(bitmap);
                } else {
                    imagepath =  roomToModify.getImage_path();
                }


                RoomDataSource roomDataSource = new RoomDataSource(this);

                EditText et = (EditText) findViewById(R.id.et_modify_room_name);
                roomToModify.setName(et.getText().toString());

                et = (EditText) findViewById(R.id.et_modify_room_size);
                roomToModify.setSize(Double.parseDouble(et.getText().toString()));

                roomToModify.setImage_path(imagepath);


                roomDataSource.updateRoom(roomToModify);

                SQLiteHelper sqlHelper = SQLiteHelper.getInstance(this);
                sqlHelper.getWritableDatabase().close();

                startActivity(new Intent(this, List_room.class));

                Toast toast = Toast.makeText(this, R.string.roomModified, Toast.LENGTH_LONG);
                toast.show();



                return true;
        }

        return (super.onOptionsItemSelected(item));
    }
}
