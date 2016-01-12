package hevs.labo.projetandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hevs.labo.projetandroid.database.adapter.ArtistDataSource;
import hevs.labo.projetandroid.database.adapter.ArtworkDataSource;
import hevs.labo.projetandroid.database.adapter.RoomDataSource;
import hevs.labo.projetandroid.database.object.Artist;
import hevs.labo.projetandroid.database.object.Artwork;
import hevs.labo.projetandroid.database.object.Room;

public class Card_exhibition extends AppCompatActivity {

    int artworkId;
    Artwork artwork;
    Artist artist;
    Room room;
    TextView tvArtist, tvRoom, tvArtwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_exhibition);

        Intent intent = new Intent();

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            artworkId =extras.getInt("id_artworkRecup");
        }

        ArtworkDataSource awds = new ArtworkDataSource(this);
        artwork = awds.getArtworkById(artworkId);

        ArtistDataSource atds = new ArtistDataSource(this);
        artist = atds.getArtistById(artwork.getForeign_key_Artist_id());

        RoomDataSource rds = new RoomDataSource(this);
        room = rds.getRoomById(artwork.getForeign_key_Room_id());

        tvArtist = (TextView) findViewById(R.id.tv_card_exhibition_artist_name);

        tvArtwork = (TextView) findViewById(R.id.tv_card_exhibition_artwork_name);
        tvArtwork.setText(artwork.getName());
        tvRoom = (TextView) findViewById(R.id.tv_card_exhibition_room_name);
        tvRoom.setText(room.getName());
        tvArtist = (TextView) findViewById(R.id.tv_card_exhibition_artist_name);
        tvArtist.setText(artist.getFirstname() + " " + artist.getLastname() + " " + artist.getPseudo());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_exhibition, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){

            case R.id.list_exhibition_menu:
                Intent intenthome = new Intent(this, List_exhibition.class);
                startActivity(intenthome);
                return true;

            case R.id.deleteExhibition_menu:

                ArtworkDataSource awds = new ArtworkDataSource(this);
                ArtistDataSource atds = new ArtistDataSource(this);
                RoomDataSource rds = new RoomDataSource(this);

                List<Artwork> artworkList = awds.getArtworksExposed();
                boolean roomOccupated = false;
                boolean artistExposed = false;

                for(int i=0; i<artworkList.size(); i++) {
                    if(artworkList.get(i).getId() != artwork.getId()) {
                        if(artworkList.get(i).getForeign_key_Room_id() == artwork.getForeign_key_Room_id()) {
                            roomOccupated = true;
                        }
                        if(artworkList.get(i).getForeign_key_Artist_id() == artwork.getForeign_key_Artist_id()) {
                            artistExposed = true;
                        }
                    }
                }

                if(roomOccupated == false) {
                    room.setSelected(false);
                    rds.updateRoom(room);
                }
                if(artistExposed == false) {
                    artist.setExposed(false);
                    atds.updateArtist(artist);
                }

                artwork.setExposed(false);
                awds.updateArtwork(artwork);
                Toast toast = Toast.makeText(this, R.string.exhibitionDeleted, Toast.LENGTH_LONG);
                toast.show();

                Intent backToListExhibition = new Intent(this, List_exhibition.class);
                startActivity(backToListExhibition);
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }
}
