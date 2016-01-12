package hevs.labo.projetandroid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import hevs.labo.projetandroid.database.adapter.ArtistDataSource;
import hevs.labo.projetandroid.database.adapter.ArtworkDataSource;
import hevs.labo.projetandroid.database.object.Artist;
import hevs.labo.projetandroid.database.object.Artwork;

public class Card_artwork extends AppCompatActivity {

    private Artwork artworkAafficher;
    private TextView titreArtwork;
    private ImageView photoArtwork;
    private TextView realisationYear;
    private TextView artistCreator;
    private Artist artistCreatorArtist;
    private TextView typeartwork;
    private TextView descriptionartwork;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_artwork);

        ArtworkDataSource artworkdata = new ArtworkDataSource(this);
        ArtistDataSource artistData = new ArtistDataSource(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            id =extras.getInt("id_artworkRecup");
        }
        Log.e("Card ARtwork", "idartworkrecup: " + id);


        //recuparate the artwork by is id passed through the activity
        artworkAafficher = artworkdata.getArtworkById(id);

        titreArtwork= (TextView) findViewById(R.id.tv_nom_oeuvre);
        titreArtwork.setText(artworkAafficher.getName());

        photoArtwork = (ImageView) findViewById(R.id.ImageView_artworkCard);
        File imageArtworkFile = new File(artworkAafficher.getImage_path());

        if(imageArtworkFile.exists()) {
            Context context = getApplicationContext();
            Uri uri = Uri.fromFile(imageArtworkFile);
            photoArtwork.setImageURI(uri);
        }

        realisationYear = (TextView) findViewById(R.id.tv_yearOfCreation);
        realisationYear.setText(String.valueOf(artworkAafficher.getCreationYear()));

        artistCreator = (TextView)findViewById(R.id.tv_artistCardArtwork);
        //recup nom
        int idFkArtistCreator = artworkAafficher.getForeign_key_Artist_id();
        artistCreatorArtist = artistData.getArtistById(idFkArtistCreator);

        artistCreator.setText(artistCreatorArtist.getLastname());

        typeartwork = (TextView) findViewById(R.id.tv_styleCardArtwork);
        typeartwork.setText(artworkAafficher.getType());


        descriptionartwork = (TextView) findViewById(R.id.tv_descriptionCardArtwork);
        descriptionartwork.setText(artworkAafficher.getDescription());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_artwork, menu);
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

            case R.id.modifyArtwork_menu:
                Log.e("Card ARtwork", "idartworktomodif: " + id);
                Intent intentmodifyArtwork = new Intent(this, Modify_artwork.class);
                intentmodifyArtwork.putExtra("id_artworkToModify" ,id);
                startActivity(intentmodifyArtwork);
                return true;

            case R.id.deleteArtwork_menu:

                int id_artworkTodelete  = artworkAafficher.getId();
                ArtworkDataSource artworkDataSource = new ArtworkDataSource(this);
                artworkDataSource.deleteArtwork(id_artworkTodelete);

                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(this, R.string.artworkDeleted, duration);
                toast.show();

                Intent backToListArtwork = new Intent(this, List_artwork.class);
                startActivity(backToListArtwork);
                return true;

        }



        return (super.onOptionsItemSelected(item));

    }
}
