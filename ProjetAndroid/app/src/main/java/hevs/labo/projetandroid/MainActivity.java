package hevs.labo.projetandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import hevs.labo.projetandroid.database.adapter.ArtistDataSource;
import hevs.labo.projetandroid.database.adapter.ArtworkDataSource;
import hevs.labo.projetandroid.database.adapter.RoomDataSource;
import hevs.labo.projetandroid.database.object.Artist;
import hevs.labo.projetandroid.database.object.Artwork;
import hevs.labo.projetandroid.database.object.Room;

public class MainActivity extends AppCompatActivity {

    private TextView numberofArtworkexposed;
    private TextView numberofArtistexposed;
    private TextView numberofRoomoccupated;
    List<Artist> nbArtist;
    List<Artwork> nbArtwork;
    List<Room> nbRoom;

    List<Room> roomoccupated;
    List<Artist> artistexposed;
    List<Artwork> artworkexposed;

    int cptartist;
    int cptroom;
    int cptartwork;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArtworkDataSource artworkDataSource = new ArtworkDataSource(this);
        ArtistDataSource artistDataSource = new ArtistDataSource(this);
        RoomDataSource roomDataSource = new RoomDataSource(this);

        //We should only keep the ones which are exposed or occupated not all the data

        numberofArtworkexposed = (TextView) findViewById(R.id.nbExposedArtwork);
        numberofArtistexposed = (TextView) findViewById(R.id.nbExposedArtist);
        numberofRoomoccupated = (TextView) findViewById(R.id.nbRoomUsed);

        nbArtist = artistDataSource.getAllArtists();
        nbArtwork = artworkDataSource.getAllArtworks();
        nbRoom =  roomDataSource.getAllRooms();


        for(int i = 0; i<nbArtist.size(); i++){
            if(nbArtist.get(i).isExposed()== true)
            {
                cptartist++;
            }
        }

        for(int i = 0; i<nbArtwork.size(); i++){
            if(nbArtwork.get(i).getExposed() == true)
            {
                cptartwork++;
            }
        }

        for (int i = 0; i<nbRoom.size(); i++)
        {
            if(nbRoom.get(i).isSelected()== true)
            {
                cptroom++;
            }
        }



        numberofArtistexposed.setText(String.valueOf(cptartist));
        numberofArtworkexposed.setText(String.valueOf(cptartwork));
        numberofRoomoccupated.setText(String.valueOf(cptroom));




    }

    public void show_list_artist(View view) {
        Intent intent = new Intent(this, List_artist.class);
        startActivity(intent);
    }

    public void show_list_artwork(View view) {
        Intent intent = new Intent(this, List_artwork.class);
        startActivity(intent);
    }

    public void show_list_exhibition(View view) {
        Intent intent = new Intent(this, List_exhibition.class);
        startActivity(intent);
    }

    public void show_settings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

            case R.id.list_artwork_menu:
                Intent intentartwork = new Intent(this, List_artwork.class);
                startActivity(intentartwork);
                return true;

            case R.id.exposition_menu:
                Intent intentexhibition = new Intent(this, List_exhibition.class);
                startActivity(intentexhibition);
                return true;

            case R.id.parametres_menu:
                Intent intentsettings = new Intent(this, Settings.class);
                startActivity(intentsettings);
                return true;
        }


        return (super.onOptionsItemSelected(item));
    }
}
