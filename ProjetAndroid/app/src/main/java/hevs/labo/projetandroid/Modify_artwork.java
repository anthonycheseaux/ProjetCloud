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

public class Modify_artwork extends AppCompatActivity implements View.OnClickListener {


    private Artwork artworktoModify;
    private EditText nameArtwork;
    private EditText creationYear;
    private EditText typeArtwork;
    private EditText descriptionArtwork;

    private ImageButton btn_changePictureArtworkToModify;
    private ImageView pictureArtworkToModify;
    private Uri selectedImage;
    private Bitmap bitmap;

    private CheckBox checkbexposedArtworkToModify;
    private List<Artist> listArtistCreatorToModify;
    String[] resourcesSpinnerNameArtistsToModify;

    private static final int RESULT_LOAD_ARTWORK_IMAGE = 1;
    private int id;
    private boolean isPicture = false;

    private SimpleDateFormat dateFormat;
    private DatePickerDialog fromDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_artwork);

        ArtworkDataSource artworkDataSource = new ArtworkDataSource(this);
        ArtistDataSource artistDataSource = new ArtistDataSource(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            id =extras.getInt("id_artworkToModify");
        }

        artworktoModify = artworkDataSource.getArtworkById(id);


        //the name of the artwork
        nameArtwork = (EditText) findViewById(R.id.editText_nameArtworkModif);
        nameArtwork.setText(artworktoModify.getName());


        //spinner
        Spinner spinnerArtist = (Spinner) findViewById(R.id.spinner_ArtistArtworkCreatedToModify);
        //creation of the data Artist for the spinner
        listArtistCreatorToModify = artistDataSource.getAllArtists();
        resourcesSpinnerNameArtistsToModify = new String[listArtistCreatorToModify.size()];

        for(int i = 0; i<listArtistCreatorToModify.size(); i++){
            resourcesSpinnerNameArtistsToModify[i] = listArtistCreatorToModify.get(i).getId()+ "  " + listArtistCreatorToModify.get(i).getLastname() ;
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,  resourcesSpinnerNameArtistsToModify);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArtist.setAdapter(adapter1);

        final Spinner spin = (Spinner) findViewById (R.id.spinner_ArtistArtworkCreatedToModify);

        //to put the precedent choice first of the spinner
        ArrayAdapter<String> adapterModifyArtist = new ArrayAdapter<String>(Modify_artwork.this, android.R.layout.simple_spinner_dropdown_item,resourcesSpinnerNameArtistsToModify);
        spin.setAdapter(adapterModifyArtist);
        int fKartistSelected = artworktoModify.getForeign_key_Artist_id();
        int index = fKartistSelected;

        String content = resourcesSpinnerNameArtistsToModify[index-1];
        Log.e("Modify_artwork", "content: " + content);
        spin.setSelection(adapterModifyArtist.getPosition(content));

        //the type_array is in the strings.xml
        Spinner spinner =  (Spinner) findViewById(R.id.spinner_typeArtworkModify);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_artwork_array, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btn_changePictureArtworkToModify = (ImageButton) findViewById(R.id.imageButton_btnDownloadArtworkModify);
        btn_changePictureArtworkToModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_ARTWORK_IMAGE);
            }
        });

        dateFormat = new SimpleDateFormat("yyyy", Locale.US);

        creationYear = (EditText) findViewById(R.id.editText_realisationArtworkModif);
        creationYear.setText(artworktoModify.getCreationYear());

        creationYear.setInputType(InputType.TYPE_NULL);
        creationYear.requestFocus();

        creationYear.setOnClickListener(this);

        Calendar newCalendar =  Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                creationYear.setText(dateFormat.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        descriptionArtwork = (EditText) findViewById(R.id.edit_text_descriptionArtworkModify);
        descriptionArtwork.setText(artworktoModify.getDescription());

        pictureArtworkToModify = (ImageView) findViewById(R.id.imageView_photoArtworkModify);


        File imgFile = new  File(artworktoModify.getImage_path());

        if(imgFile.exists()) {
            Context context = getApplicationContext();
            Uri uri = Uri.fromFile(imgFile);
            pictureArtworkToModify.setImageURI(uri);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (requestCode == RESULT_LOAD_ARTWORK_IMAGE && resultCode == RESULT_OK && null != data) {

                selectedImage = data.getData();
                pictureArtworkToModify.setImageURI(selectedImage);
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
        int nameArtwork = artworktoModify.getId();

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "artiste"+nameArtwork+".jpg");

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
        getMenuInflater().inflate(R.menu.menu_modify_artwork, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){

            case R.id.list_artwork_menu:
                Intent intentartwork = new Intent(this, List_artwork.class);
                startActivity(intentartwork);
                return true;


            case R.id.saveartworkmodified_menu:

                //voir si on doit faire new artworktoModify



                String imagepath ="";

                if(isPicture == true){
                    imagepath = saveToInternalStorage(bitmap);
                } else {
                    imagepath =  artworktoModify.getImage_path();
                }


                ArtworkDataSource artworkDataSource = new ArtworkDataSource(this);
                ArtistDataSource artistDataSource = new ArtistDataSource(this);

                EditText et = (EditText) findViewById(R.id.editText_nameArtworkModif);
                artworktoModify.setName(et.getText().toString());

                Spinner spinnerArtistFk = (Spinner) findViewById(R.id.spinner_ArtistArtworkCreatedToModify);
                String recupArtistFK = spinnerArtistFk.getSelectedItem().toString();
                String parts[] = recupArtistFK.split(" ");
                String idRecupArtist = parts[0];
                int fkArtist = Integer.parseInt(idRecupArtist);

                artworktoModify.setForeign_key_Artist_id(fkArtist);

                et = (EditText) findViewById(R.id.editText_realisationArtworkModif);
                artworktoModify.setCreationYear(et.getText().toString());

                Spinner spinner = (Spinner) findViewById(R.id.spinner_typeArtworkModify);
                String recup = spinner.getSelectedItem().toString();
                artworktoModify.setType(recup);

                et = (EditText) findViewById(R.id.edit_text_descriptionArtworkModify);
                artworktoModify.setDescription(et.getText().toString());

                artworktoModify.setImage_path(imagepath);

                artworkDataSource.updateArtwork(artworktoModify);


                SQLiteHelper sqlHelper = SQLiteHelper.getInstance(this);
                sqlHelper.getWritableDatabase().close();

                startActivity(new Intent(this, List_artwork.class));

                Toast toast = Toast.makeText(this, R.string.artworkModified, Toast.LENGTH_LONG);
                toast.show();

                return true;
        }



        return (super.onOptionsItemSelected(item));

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.editText_realisationArtworkModif:
                fromDatePickerDialog.show();
                break;
        }

    }
}
