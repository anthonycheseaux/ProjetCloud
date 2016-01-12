package hevs.labo.projetandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import hevs.labo.projetandroid.database.adapter.ArtistDataSource;
import hevs.labo.projetandroid.database.adapter.ArtworkDataSource;
import hevs.labo.projetandroid.database.adapter.RoomDataSource;
import hevs.labo.projetandroid.database.object.Artwork;
import hevs.labo.projetandroid.database.object.Exhibition;

public class List_exhibition extends AppCompatActivity {

    ListView listView_exhibition;
    List<Artwork> artworks;
    List<Exhibition> exhibitions;
    String[] tabExhibitionCreated;
    private Exhibition exhibitionpicked;
    private ActionMode mActionMode = null;
    String expo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exhibition);

        final ArtistDataSource ards = new ArtistDataSource(this);
        final ArtworkDataSource awds = new ArtworkDataSource(this);
        final RoomDataSource rds = new RoomDataSource(this);

        List<Artwork> artworkList = awds.getArtworksExposed();
        artworks = awds.getArtworksExposed();

        listView_exhibition = (ListView) findViewById(R.id.list_exposition);

        listView_exhibition.setClickable(true);

        listView_exhibition.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artwork a = artworks.get(position);

                Intent intent = new Intent(List_exhibition.this, Card_exhibition.class);
                intent.putExtra("id_artworkRecup", a.getId());
                startActivity(intent);
            }
        });

        listView_exhibition.setChoiceMode(listView_exhibition.CHOICE_MODE_SINGLE);
        listView_exhibition.setTextFilterEnabled(true);

        if(artworkList == null){
            return;
        }

        tabExhibitionCreated = new String[artworkList.size()];

        for(int i = 0; i < artworkList.size(); i++)
        {
            tabExhibitionCreated[i]=
                    rds.getRoomById(artworkList.get(i).getForeign_key_Room_id()).getName() + "      |      " +
                    ards.getArtistById(artworkList.get(i).getForeign_key_Artist_id()) + "      |       " +
                      artworkList.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, tabExhibitionCreated);

        listView_exhibition.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_exhibition, menu);
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

            case R.id.parametres_menu:
                Intent intentsettings = new Intent(this, Settings.class);
                startActivity(intentsettings);
                return true;

            case R.id.addExhibition_menu:
                Intent intentaddExhibition = new Intent(this, Create_exhibition.class);
                startActivity(intentaddExhibition);
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }
}
