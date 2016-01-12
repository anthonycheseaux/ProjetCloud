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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;

import hevs.labo.projetandroid.database.adapter.ArtistDataSource;
import hevs.labo.projetandroid.database.adapter.ArtworkDataSource;
import hevs.labo.projetandroid.database.object.Artist;
import hevs.labo.projetandroid.database.object.Artwork;

public class Card_artist extends AppCompatActivity {

    private static final int RESULT_LOAD_ARTIST_IMAGE = 1;

    ImageView imageToUpload;
    private Uri selectedImage;
    private Bitmap bitmap;
    private boolean isPicture;


    private TextView titre;
    private TextView annee;
    private Artist artistAafficher;
    private TextView artistMouvement;

    private ImageView photoArtist;

    //liste d artwork
    ListView listView_artworkFromTheArtist;
    String[] tabArtworkByArtist;
    String expo;
    private int id;

    ArtworkArtistAdapter artworkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_artist);

        ArtistDataSource ards = new ArtistDataSource(this);
        ArtworkDataSource artworkDataSource = new ArtworkDataSource(this);

       // Intent intent = getIntent();

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            id =extras.getInt("id_artistRecup");
        }

        //here we recover the artist from the database by it's id
        artistAafficher = ards.getArtistById(id);

        //we recover the information about the artist to put in the fields which correspond to the information
        titre = (TextView) findViewById(R.id.tv_nom_artiste);
        titre.setText(artistAafficher.getFirstname() + " " +artistAafficher.getLastname());

        annee = (TextView) findViewById(R.id.tv_descriptionArtist_year);
        annee.setText(artistAafficher.getBirth() + " / " + artistAafficher.getDeath());

        artistMouvement = (TextView) findViewById(R.id.tv_descriptionArtist_descriptionmovement);
        artistMouvement.setText(artistAafficher.getMovement());

        //image
        photoArtist = (ImageView) findViewById(R.id.imageView_Artist);
      /*  photoArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_ARTIST_IMAGE);
            }
        });*/

        // file:///data/data/hevs.labo.projetandroid/app_imageDir/1779.jpg
        //file:///data/data/hevs.labo.projetandroid/app_imageDir/artiste1.jpg
        File imgFile = new  File(artistAafficher.getImage_path());
        if(imgFile.exists()) {
            Context context = getApplicationContext();
            CharSequence text = "Hello toast!";
            int duration = Toast.LENGTH_LONG;
/*
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();*/
            Uri uri = Uri.fromFile(imgFile);
            photoArtist.setImageURI(uri);


            Log.e("Artiste", "imagepath: " + uri);
        }

        listView_artworkFromTheArtist = (ListView) findViewById(R.id.list_oeuvre);
        //pour supprimer une oeuvre il faut aller directement sur la carte
        //pas possible de supprimer depuis la liste
        listView_artworkFromTheArtist.setClickable(false);

        listView_artworkFromTheArtist.setTextFilterEnabled(true);

        List<Artwork> listArtworkForThisArtist = artworkDataSource.getAllArtworksByArtist(id);

        View header = getLayoutInflater().inflate(R.layout.header_artwork_artist, null);

        listView_artworkFromTheArtist.addHeaderView(header);

        artworkAdapter = new ArtworkArtistAdapter(this.getApplicationContext(), listArtworkForThisArtist, id);
        Log.e("Artste", "imagepath: " + listArtworkForThisArtist);

        listView_artworkFromTheArtist.setAdapter(artworkAdapter);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_artist, menu);
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

            case R.id.modifyArtist_menu:

                int id_artist_modif = artistAafficher.getId();

                Intent intentmodifyArtist = new Intent(this, Modify_artist.class);
                intentmodifyArtist.putExtra("id_artist_modif", String.valueOf(id_artist_modif));
                startActivity(intentmodifyArtist);


                return true;

            case R.id.deleteArtist_menu:

                int id_artist  = artistAafficher.getId();
                ArtistDataSource artistDataSource = new ArtistDataSource(this);
                artistDataSource.deleteArtist(id_artist);

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(this, R.string.artistDeleted, duration);
                toast.show();

                Intent backToList = new Intent(this, List_artist.class);
                startActivity(backToList);
                return true;
        }



        return (super.onOptionsItemSelected(item));


    }

    public class ArtworkArtistAdapter extends BaseAdapter {

        ArtworkDataSource ads;
        ArtistDataSource ards;
        List<Artwork> listartadap;
        String[] artworks;
        int id;

        public ArtworkArtistAdapter(Context context, List<Artwork> listartwj, int id){
            ads = new ArtworkDataSource(context);
            ards = new ArtistDataSource(context);
            listartadap = getDataForListView(id);

        }

    //Difficulty : we should recuparate the artworks froms this artist in question
        public List<Artwork> getDataForListView(int id) {
            List<Artwork> listArtwork;
            listArtwork = ads.getAllArtworksByArtist(id);
            return listArtwork;
        }


        @Override
        public int getCount() {
            return listartadap.size();
        }

        @Override
        public Object getItem(int position) {
            return listartadap.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null)
            {
                LayoutInflater inflater = (LayoutInflater) Card_artist.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.activity_card_artist_listartwork_adapter, parent, false);
            }

            TextView t1 = (TextView)convertView.findViewById(R.id.label1_NameArtwork);
            TextView t2 = (TextView) convertView.findViewById(R.id.label2_CreationArtwork);
            ImageView i3 = (ImageView) convertView.findViewById(R.id.logo_artworkExposed);

            Artwork r = listartadap.get(position);

            t1.setText(r.getName());

            t2.setText(String.valueOf(r.getCreationYear()));

            if(r.getExposed() == true){
                i3.setImageDrawable(getResources().getDrawable(R.drawable.exposed));
            }
            else
            {
                i3.setImageDrawable(getResources().getDrawable(R.drawable.occuped));
            }

            return convertView;
        }


        public Artwork getArtwork(int position) {
            return listartadap.get(position);
        }


    }
}

