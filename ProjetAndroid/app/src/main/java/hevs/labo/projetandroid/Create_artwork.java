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
import java.util.List;
import java.util.Locale;
import java.util.Random;

import hevs.labo.projetandroid.database.SQLiteHelper;
import hevs.labo.projetandroid.database.adapter.ArtistDataSource;
import hevs.labo.projetandroid.database.adapter.ArtworkDataSource;
import hevs.labo.projetandroid.database.object.Artist;
import hevs.labo.projetandroid.database.object.Artwork;

public class Create_artwork extends AppCompatActivity implements View.OnClickListener {

    private Artwork artwork;
    private static final int RESULT_LOAD_ARTWORKCREATE_IMAGE = 1;


    /**Gérer les images : */
    private Uri selectedImageArtwork;
    private Bitmap bitmap;
    private boolean isPicture;

    ImageView imageArtworkToUpload;
    ImageButton bUploadImageArtwork;

    List<Artist> spinnerResources;
    String[] resourcesSpinnerNameArtists;

    private SimpleDateFormat dateFormat;
    private DatePickerDialog fromDatePickerDialog;

    EditText creationYearEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_artwork);

        ArtistDataSource artistds = new ArtistDataSource(this);

        imageArtworkToUpload = (ImageView) findViewById(R.id.imageView_photoArtworkCreate);
        bUploadImageArtwork = (ImageButton) findViewById(R.id.imageButton_btnDownloadArtworkCreate);

        imageArtworkToUpload.setOnClickListener(this);
        bUploadImageArtwork.setOnClickListener(this);

        Spinner spinnerArtist = (Spinner) findViewById(R.id.spinner_ArtistArtworkCreated);
        spinnerResources = artistds.getAllArtists();
        resourcesSpinnerNameArtists = new String[spinnerResources.size()];

        for(int i = 0; i<spinnerResources.size(); i++){
            resourcesSpinnerNameArtists[i] = spinnerResources.get(i).getId()+ "  " + spinnerResources.get(i).getLastname() ;
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,  resourcesSpinnerNameArtists);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArtist.setAdapter(adapter1);

        //the type_array is in the strings.xml
        Spinner spinner =  (Spinner) findViewById(R.id.spinner_typeArtworkCreate);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_artwork_array, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //here we create the dialog for the date
        dateFormat = new SimpleDateFormat("yyyy", Locale.US);

        creationYearEditText = (EditText) findViewById(R.id.editText_realisationArtworkCreate);
        creationYearEditText.setInputType(InputType.TYPE_NULL);
        creationYearEditText.requestFocus();

        creationYearEditText.setOnClickListener(this);

        Calendar newCalendar =  Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                creationYearEditText.setText(dateFormat.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (requestCode == RESULT_LOAD_ARTWORKCREATE_IMAGE && resultCode == RESULT_OK && null != data) {

                selectedImageArtwork = data.getData();
                imageArtworkToUpload.setImageURI(selectedImageArtwork);
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageArtwork);

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
        int randomnum = 1+ (int)(Math.random()*11000);

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
            case R.id.imageView_photoArtworkCreate:
                onLoad();
                break;
            case R.id.imageButton_btnDownloadArtworkCreate:
                onLoad();
                break;
            case R.id.editText_realisationArtworkCreate:
                fromDatePickerDialog.show();
                break;
        }
    }

    private void onLoad() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_ARTWORKCREATE_IMAGE);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_artwork, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){

            case R.id.list_artwork_menu:
                startActivity(new Intent(this, List_artwork.class));
                return true;

            case R.id.cancelartworkcreated_menu:
                startActivity(new Intent(this, List_artwork.class));
                return true;

            case R.id.saveartworkcreated_menu:

                String imagepath = saveToInternalStorage(bitmap);
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;


                artwork = new Artwork();
                ArtworkDataSource ads = new ArtworkDataSource(this);

                EditText et = (EditText) findViewById(R.id.editText_nameArtworkCreate);
                artwork.setName(et.getText().toString());

                Spinner spinnerArtistFk = (Spinner) findViewById(R.id.spinner_ArtistArtworkCreated);
                String recupArtist = spinnerArtistFk.getSelectedItem().toString();
                String parts[] = recupArtist.split(" ");
                String idRecupArtist = parts[0];
                int fkArtist = Integer.parseInt(idRecupArtist);
                Log.e("Artiste", "id Artiste recup dans liste pour artwork: " + fkArtist);

                artwork.setForeign_key_Artist_id(fkArtist);

                Toast toastpict = Toast.makeText(context, idRecupArtist, duration);
                //toastpict.show();

                et = (EditText) findViewById(R.id.editText_realisationArtworkCreate);
                artwork.setCreationYear(et.getText().toString());

                Spinner spinner = (Spinner) findViewById(R.id.spinner_typeArtworkCreate);
                String recup = spinner.getSelectedItem().toString();
                artwork.setType(recup);

                et = (EditText) findViewById(R.id.edit_text_descriptionArtworkCreate);
                artwork.setDescription(et.getText().toString());

                artwork.setImage_path(imagepath);

                artwork.setExposed(false);


                artwork.setId((int) ads.createArtwork(artwork));

                //close db instance
                SQLiteHelper sqlHelper = SQLiteHelper.getInstance(this);
                sqlHelper.getWritableDatabase().close();

                startActivity(new Intent(this, List_artwork.class));

                Toast toast = Toast.makeText(this, R.string.artworkAdded, Toast.LENGTH_LONG);
                toast.show();

                return true;
        }

        return (super.onOptionsItemSelected(item));
    }
}
