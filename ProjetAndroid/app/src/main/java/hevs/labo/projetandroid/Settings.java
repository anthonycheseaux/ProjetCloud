package hevs.labo.projetandroid;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hevs.labo.projetandroid.backend.artistApi.model.Artist;
import hevs.labo.projetandroid.backend.artworkApi.model.Artwork;
import hevs.labo.projetandroid.backend.roomApi.model.Room;
import hevs.labo.projetandroid.database.adapter.ArtistDataSource;
import hevs.labo.projetandroid.database.adapter.ArtworkDataSource;
import hevs.labo.projetandroid.database.adapter.RoomDataSource;
import hevs.labo.projetandroid.database.adapter.SyncDataSource;
import hevs.labo.projetandroid.database.object.Sync;

public class Settings extends AppCompatActivity {

    Locale myLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();

        String lang = conf.locale.getDisplayLanguage();
        lang = lang.substring(0,2);

        RadioButton radioButton;

        if(lang.equals("fr"))
        {
            radioButton = (RadioButton) findViewById(R.id.rb_settings_french);
            radioButton.setChecked(true);
        }
        else
        {
            radioButton = (RadioButton) findViewById(R.id.rb_settings_english);
            radioButton.setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){

            case R.id.accueil_menu:
                Intent intenthome = new Intent(this, MainActivity.class);
                startActivity(intenthome);
                return true;

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
        }

        return (super.onOptionsItemSelected(item));
    }

    public void show_list_room(View view){
        Intent intent = new Intent(this, List_room.class);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rb_settings_english:
                if (checked)
                    setLocale("en");
                    break;

            case R.id.rb_settings_french:
                if (checked)
                    setLocale("fr");
                    break;
        }
    }

    public void save_language(View view){
        Intent intent = new Intent(this, List_room.class);
        startActivity(intent);
    }

    public void sync(View view){
        //new EndpointsAsyncTaskHello().execute(new Pair<Context, String>(this, "Manfred"));

        //ArtistDataSource ads = new ArtistDataSource(getApplicationContext());
        //List<Artist> artistList = ads.getAllArtistsBackend();
        //list.add(artistList);

        //ArtworkDataSource awds = new ArtworkDataSource(getApplicationContext());
        //List<Artwork> artworkList = awds.getAllArtworksBackend();
        //listInsert.add(artworkList);

        //RoomDataSource rds = new RoomDataSource(getApplicationContext());
        //List<Room> roomList = rds.getAllRoomsBackend();
        //listInsert.add(roomList);


        List<List> listInsert = new ArrayList<>();
        listInsert.add(getInsertArtist());
        listInsert.add(getInsertArtwork());
        listInsert.add(getInsertRoom());
        new EndpointsAsyncTaskInsert().execute(new Pair<Context, List<List>>(this, listInsert));


        List<List> listUpdate = new ArrayList<>();
        listUpdate.add(getUpdateArtist());
        listUpdate.add(getUpdateArtwork());
        listUpdate.add(getUpdateRoom());
        new EndpointsAsyncTaskUpdate().execute(new Pair<Context, List<List>>(this, listUpdate));


        List<List> listDelete = new ArrayList<>();
        listDelete.add(getDeleteArtist());
        listDelete.add(getDeleteArtwork());
        listDelete.add(getDeleteRoom());
        new EndpointsAsyncTaskDelete().execute(new Pair<Context, List<List>>(this, listDelete));

    }

    private List<Artist> getInsertArtist() {
        SyncDataSource sds = new SyncDataSource(getApplicationContext());
        ArtistDataSource ads = new ArtistDataSource(getApplicationContext());

        List<Sync> syncArtist = sds.getSyncInsertArtist();
        List<Artist> artistList = new ArrayList<>();

        for(int i=0; i<syncArtist.size(); i++) {
            artistList.add(ads.getArtistByIdBackend(syncArtist.get(i).getObjectId()));
            sds.deleteSync(syncArtist.get(i).getId());
        }

        return artistList;
    }

    private List<Artwork> getInsertArtwork() {
        SyncDataSource sds = new SyncDataSource(getApplicationContext());
        ArtworkDataSource ads = new ArtworkDataSource(getApplicationContext());

        List<Sync> syncArtwork = sds.getSyncInsertArtwork();
        List<Artwork> artworkList = new ArrayList<>();

        for(int i=0; i<syncArtwork.size(); i++) {
            artworkList.add(ads.getArtworkByIdBackend(syncArtwork.get(i).getObjectId()));
            sds.deleteSync(syncArtwork.get(i).getId());
        }

        return artworkList;
    }

    private List<Room> getInsertRoom() {
        SyncDataSource sds = new SyncDataSource(getApplicationContext());
        RoomDataSource rds = new RoomDataSource(getApplicationContext());

        List<Sync> syncRoom = sds.getSyncInsertRoom();
        List<Room> artworkList = new ArrayList<>();

        for(int i=0; i<syncRoom.size(); i++) {
            artworkList.add(rds.getRoomByIdBackend(syncRoom.get(i).getObjectId()));
            sds.deleteSync(syncRoom.get(i).getId());
        }

        return artworkList;
    }

    private List<Artist> getUpdateArtist() {
        SyncDataSource sds = new SyncDataSource(getApplicationContext());
        ArtistDataSource ads = new ArtistDataSource(getApplicationContext());

        List<Sync> syncArtist = sds.getSyncUpdateArtist();
        List<Artist> artistList = new ArrayList<>();

        for(int i=0; i<syncArtist.size(); i++) {
            artistList.add(ads.getArtistByIdBackend(syncArtist.get(i).getObjectId()));
            sds.deleteSync(syncArtist.get(i).getId());
        }

        return artistList;
    }

    private List<Artwork> getUpdateArtwork() {
        SyncDataSource sds = new SyncDataSource(getApplicationContext());
        ArtworkDataSource ads = new ArtworkDataSource(getApplicationContext());

        List<Sync> syncArtwork = sds.getSyncUpdateArtwork();
        List<Artwork> artworkList = new ArrayList<>();

        for(int i=0; i<syncArtwork.size(); i++) {
            artworkList.add(ads.getArtworkByIdBackend(syncArtwork.get(i).getObjectId()));
            sds.deleteSync(syncArtwork.get(i).getId());
        }

        return artworkList;
    }

    private List<Room> getUpdateRoom() {
        SyncDataSource sds = new SyncDataSource(getApplicationContext());
        RoomDataSource ads = new RoomDataSource(getApplicationContext());

        List<Sync> syncRoom = sds.getSyncUpdateRoom();
        List<Room> roomList = new ArrayList<>();

        for(int i=0; i<syncRoom.size(); i++) {
            roomList.add(ads.getRoomByIdBackend(syncRoom.get(i).getObjectId()));
            sds.deleteSync(syncRoom.get(i).getId());
        }

        return roomList;
    }

    private List<Artist> getDeleteArtist() {
        SyncDataSource sds = new SyncDataSource(getApplicationContext());

        List<Sync> syncArtist = sds.getSyncDeleteArtist();
        List<Artist> artistList = new ArrayList<>();

        for(int i=0; i<syncArtist.size(); i++) {
            artistList.add(new Artist().setId(syncArtist.get(i).getObjectId()));
            sds.deleteSync(syncArtist.get(i).getId());
        }

        return artistList;
    }

    private List<Artwork> getDeleteArtwork() {
        SyncDataSource sds = new SyncDataSource(getApplicationContext());

        List<Sync> syncArtwork = sds.getSyncDeleteArtwork();
        List<Artwork> artworkList = new ArrayList<>();

        for(int i=0; i<syncArtwork.size(); i++) {
            artworkList.add(new Artwork().setId(syncArtwork.get(i).getObjectId()));
            sds.deleteSync(syncArtwork.get(i).getId());
        }

        return artworkList;
    }

    private List<Room> getDeleteRoom() {
        SyncDataSource sds = new SyncDataSource(getApplicationContext());

        List<Sync> syncRoom = sds.getSyncDeleteRoom();
        List<Room> roomList = new ArrayList<>();

        for(int i=0; i<syncRoom.size(); i++) {
            roomList.add(new Room().setId(syncRoom.get(i).getObjectId()));
            sds.deleteSync(syncRoom.get(i).getId());
        }

        return roomList;
    }

    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, Settings.class);
        startActivity(refresh);
    }
}
