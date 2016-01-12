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

public class Modify_artist extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_ARTIST_IMAGE = 1;

    private EditText lastname;
    private EditText firstname;
    private EditText pseudo;

    private Spinner spinner;

    private ImageButton btn_changePicture;
    private ImageView pictureArtistToModify;

    private Artist artistToModify;
    private Uri selectedImage;
    private Bitmap bitmap;
    private CheckBox checkbexposed;
    private int id_artist_modif;
    private boolean isPicture = false;



    private SimpleDateFormat dateFormat;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private EditText birthdate;
    private EditText deathdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_artist);

        /**Action sur le bouton de load picture*/
        btn_changePicture = (ImageButton) findViewById(R.id.imageButton_btnDownloadArtistModify);
        btn_changePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_ARTIST_IMAGE);
            }
        });

        ArtistDataSource ards = new ArtistDataSource(this);


        Intent intent = getIntent();
        String id = intent.getStringExtra("id_artist_modif");
        id_artist_modif = Integer.parseInt(id);

        //récupérer les données de l'artiste à modifier pour afficher
        artistToModify = ards.getArtistById(id_artist_modif);

        lastname = (EditText) findViewById(R.id.editText_lastnameArtistModiy);
        lastname.setText(artistToModify.getLastname());

        firstname = (EditText) findViewById(R.id.editText_firstsnameArtistModify);
        firstname.setText(artistToModify.getFirstname());

        pseudo = (EditText) findViewById(R.id.editText_pseudoArtistModify);
        pseudo.setText(artistToModify.getPseudo());

        birthdate = (EditText) findViewById(R.id.editText_birthArtistModify);
        birthdate.setText(artistToModify.getBirth());

        deathdate = (EditText) findViewById(R.id.editText_deathArtistModify);
        deathdate.setText(artistToModify.getDeath());


        //here we create the dialog for the date
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        birthdate.setInputType(InputType.TYPE_NULL);
        birthdate.requestFocus();

        deathdate = (EditText) findViewById(R.id.editText_deathArtistModify);
        deathdate.setInputType(InputType.TYPE_NULL);

        birthdate.setOnClickListener(this);
        deathdate.setOnClickListener(this);

        /*initialiser le spinner avec le choix précédemment choisi à la création*/
        spinner = (Spinner) findViewById(R.id.spinner_mvtArtistModify);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.mvt_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        final Spinner spin = (Spinner) findViewById (R.id.spinner_mvtArtistModify);
        String[] groupe = getResources().getStringArray(R.array.mvt_array);

        ArrayAdapter<String> ad = new ArrayAdapter<String>(Modify_artist.this, android.R.layout.simple_spinner_dropdown_item,groupe);
        spin.setAdapter(ad);
        spin.setSelection(ad.getPosition(artistToModify.getMovement()));


        /*imageView où est chargée l image choisie a la création*/
        pictureArtistToModify = (ImageView) findViewById(R.id.imageView_photoArtistModify);
        File imgFile = new  File(artistToModify.getImage_path());

        if(imgFile.exists()) {
            Context context = getApplicationContext();
            Uri uri = Uri.fromFile(imgFile);
            pictureArtistToModify.setImageURI(uri);
        }

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
                deathdate.setText(dateFormat.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (requestCode == RESULT_LOAD_ARTIST_IMAGE && resultCode == RESULT_OK && null != data) {

                selectedImage = data.getData();
                pictureArtistToModify.setImageURI(selectedImage);
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

    private String saveToInternalStorage(Bitmap bitmapImage) {



        Random rd = new Random();
        int randomnum = 1+ (int)(Math.random()*4000);
        int namePicture = artistToModify.getId();

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "artiste"+namePicture+".jpg");

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
        getMenuInflater().inflate(R.menu.menu_modify_artist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.artist_menu:
                Intent intentartist = new Intent(this, List_artist.class);
                startActivity(intentartist);
                return true;

            case R.id.saveartistmodified_menu:

                String imagepath ="";

                if(isPicture == true){
                     imagepath = saveToInternalStorage(bitmap);
                } else {
                   imagepath =  artistToModify.getImage_path();
                }


                ArtistDataSource ads = new ArtistDataSource(this);



                EditText et = (EditText) findViewById(R.id.editText_lastnameArtistModiy);
                artistToModify.setLastname(et.getText().toString());

                et = (EditText) findViewById(R.id.editText_firstsnameArtistModify);
                artistToModify.setFirstname(et.getText().toString());

                et = (EditText) findViewById(R.id.editText_pseudoArtistModify);
                artistToModify.setPseudo(et.getText().toString());

                et = (EditText) findViewById(R.id.editText_birthArtistModify);
                artistToModify.setBirth(et.getText().toString());

                et= (EditText) findViewById(R.id.editText_deathArtistModify);
                artistToModify.setDeath(et.getText().toString());

                Spinner spinner = (Spinner) findViewById(R.id.spinner_mvtArtistModify);
                String recup = spinner.getSelectedItem().toString();
                artistToModify.setMovement(recup);

                Log.e("Artste", "imagepath: " + imagepath);
                artistToModify.setImage_path(imagepath);

                ads.updateArtist(artistToModify);

                SQLiteHelper sqlHelper = SQLiteHelper.getInstance(this);
                sqlHelper.getWritableDatabase().close();

                startActivity(new Intent(this, List_artist.class));

                Toast toast = Toast.makeText(this, R.string.artistModified, Toast.LENGTH_LONG);
                toast.show();

                return true;

        }

        return (super.onOptionsItemSelected(item));

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.editText_birthArtistModify:
                fromDatePickerDialog.show();
                break;
            case R.id.editText_deathArtistModify:
                toDatePickerDialog.show();
                break;

        }

    }
}
