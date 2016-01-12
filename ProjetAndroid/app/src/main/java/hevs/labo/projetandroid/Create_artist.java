package hevs.labo.projetandroid;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import hevs.labo.projetandroid.database.SQLiteHelper;
import hevs.labo.projetandroid.database.adapter.ArtistDataSource;
import hevs.labo.projetandroid.database.object.Artist;

public class Create_artist extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_ARTIST_IMAGE = 1;
    int compteurPhoto;
    private Artist artist;

    /**Gérer les images : */
    private Uri selectedImage;
    private Bitmap bitmap;
    private boolean isPicture;

    ImageView imageToUpload;
    ImageButton bUploadImage;

    /**Gérer les dialogues : */
    EditText birthdate;
    EditText death;

    private SimpleDateFormat dateFormat;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_artist);

        imageToUpload = (ImageView) findViewById(R.id.imageView_photoArtistCreate);
        bUploadImage = (ImageButton) findViewById(R.id.imageButton_btnDownloadArtistCreate);

        imageToUpload.setOnClickListener(this);
        bUploadImage.setOnClickListener(this);


        //the mvt_array is in the strings.xml
        Spinner spinner =  (Spinner) findViewById(R.id.spinner_mvtArtistCreate);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.mvt_array, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //here we create the dialog for the date
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        birthdate = (EditText) findViewById(R.id.editText_naissanceArtistCreate);
        birthdate.setInputType(InputType.TYPE_NULL);
        birthdate.requestFocus();

        death = (EditText) findViewById(R.id.editText_decesArtistCreate);
        death.setInputType(InputType.TYPE_NULL);

        birthdate.setOnClickListener(this);
        death.setOnClickListener(this);

        Calendar newCalendar =  Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                birthdate.setText(dateFormat.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                death.setText(dateFormat.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    //This is the function to upload the picture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (requestCode == RESULT_LOAD_ARTIST_IMAGE && resultCode == RESULT_OK && null != data) {

                selectedImage = data.getData();
                imageToUpload.setImageURI(selectedImage);
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                isPicture = true;
            } else {
                Toast.makeText(this, R.string.noImagePicked, Toast.LENGTH_LONG).show();
            }
        }catch (Exception e ){
            Log.e("error", e.toString());
            Toast.makeText(this, R.string.stgWentWrong, Toast.LENGTH_LONG).show();
        }

    }

    //This is the function to rename and save the picture
    private String saveToInternalStorage(Bitmap bitmapImage) {

        compteurPhoto++;

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


    //the onClick method deserve the different functions
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.imageView_photoArtistCreate:
                onLoad();
                break;
            case R.id.imageButton_btnDownloadArtistCreate:
                onLoad();
                break;
            case R.id.editText_naissanceArtistCreate:
                fromDatePickerDialog.show();
                break;
            case R.id.editText_decesArtistCreate:
                toDatePickerDialog.show();
                break;

        }
    }

    //To upload a picture
    private void onLoad() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_ARTIST_IMAGE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_artist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){

            case R.id.artist_menu:
                Intent intentartist = new Intent(this, List_artist.class);
                startActivity(intentartist);
                return true;

            case R.id.cancelartistcreated_menu:
                Intent intentartistcancel = new Intent(this, List_artist.class);
                startActivity(intentartistcancel);

                return true;

            case R.id.saveartistcreated_menu:

                //retourne le chemin ou est stocké le fichier
                //Here we take all the informations about the artist which is create to save it in the database
                String imagepath = saveToInternalStorage(bitmap);
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;

                /*
                Toast toastpict = Toast.makeText(context, imagepath, duration);
                toastpict.show();*/


                artist = new Artist();
                ArtistDataSource ads = new ArtistDataSource(this);

                EditText et = (EditText) findViewById(R.id.editText_nomArtistCreate);
                artist.setLastname(et.getText().toString());

                et = (EditText) findViewById(R.id.editText_prenomArtistCreate);
                artist.setFirstname(et.getText().toString());

                et = (EditText) findViewById(R.id.editText_pseudoArtistCreate);
                artist.setPseudo(et.getText().toString());

               // et = (EditText) findViewById(R.id.editText_naissanceArtistCreate);
                artist.setBirth(birthdate.getText().toString());

                et= (EditText) findViewById(R.id.editText_decesArtistCreate);
                artist.setDeath(death.getText().toString());

                Spinner spinner = (Spinner) findViewById(R.id.spinner_mvtArtistCreate);
                String recup = spinner.getSelectedItem().toString();
                artist.setMovement(recup);

                //path de la picture

                artist.setImage_path(imagepath);

                artist.setExposed(false);

                artist.setId((int) ads.createArtist(artist));

                SQLiteHelper sqlHelper = SQLiteHelper.getInstance(this);
                sqlHelper.getWritableDatabase().close();

                startActivity(new Intent(this, List_artist.class));

                Toast toast = Toast.makeText(this, R.string.artistAdded, Toast.LENGTH_LONG);
                toast.show();

                return true;
        }

        return (super.onOptionsItemSelected(item));
    }



}




