package hevs.labo.projetandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import hevs.labo.projetandroid.database.adapter.ArtistDataSource;
import hevs.labo.projetandroid.database.adapter.ArtworkDataSource;
import hevs.labo.projetandroid.database.object.Artist;
import hevs.labo.projetandroid.database.object.Artwork;

public class List_artwork extends AppCompatActivity {
    ListView listView;
    String[] tabArtworkCreated;
    List<Artwork> arl;
    String expo;
    Artist artistPickByForeign_Key;
    int idArtistForeign_Key;
    String nameArtistByFK;

    ArtworkAdapter artworkAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_artwork);

        ArtworkDataSource ads = new ArtworkDataSource(this);
        ArtistDataSource artds = new ArtistDataSource(this);

        arl = ads.getAllArtworks();

        View header = getLayoutInflater().inflate(R.layout.header_artwork, null);

        artworkAdapter = new ArtworkAdapter(this.getApplicationContext(), arl);

        listView = (ListView) findViewById(R.id.list_artwork);

        listView.setClickable(true);
        listView.addHeaderView(header);

        listView.setAdapter(artworkAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Artwork aw = arl.get(position - 1);

                sendArtworkCard(aw.getId());

            }
        });

    }

        public void sendArtworkCard(int id) {

        Intent intentaw = new Intent(this, Card_artwork.class);
        intentaw.putExtra("id_artworkRecup", id);
        startActivity(intentaw);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_artwork, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.accueil_menu:
                Intent intenthome = new Intent(this, MainActivity.class);
                startActivity(intenthome);
                return true;

            case R.id.artist_menu:
                Intent intentartist = new Intent(this, List_artist.class);
                startActivity(intentartist);
                return true;

            case R.id.exposition_menu:
                Intent intentexhibition = new Intent(this, List_exhibition.class);
                startActivity(intentexhibition);
                return true;

            case R.id.parametres_menu:
                Intent intentsettings = new Intent(this, Settings.class);
                startActivity(intentsettings);
                return true;

            case R.id.addArtwork_menu:
                Intent intentaddArtworkmenu = new Intent(this, Create_artwork.class);
                startActivity(intentaddArtworkmenu);
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }


    public class ArtworkAdapter extends BaseAdapter {

        ArtworkDataSource ads;
        ArtistDataSource ards;
        List<Artwork> listartadap;
        String[] artworks;

        public ArtworkAdapter(Context context, List<Artwork> listartw){
            ads = new ArtworkDataSource(context);
            ards = new ArtistDataSource(context);
            listartadap = getDataForListView();
        }


        public List<Artwork> getDataForListView() {
            List<Artwork> listArtwork;
            listArtwork = ads.getAllArtworks();

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
                LayoutInflater inflater = (LayoutInflater) List_artwork.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.activity_list_artwork_adapter, parent, false);
            }

            TextView t1 = (TextView)convertView.findViewById(R.id.label1_NameArtwork);
            TextView t2 = (TextView) convertView.findViewById(R.id.label2_ArtistArtwork);
            ImageView i3 = (ImageView) convertView.findViewById(R.id.logo_artworkExposed);

            Artwork r = listartadap.get(position);

            t1.setText(r.getName());

            int searchLastname =  r.getForeign_key_Artist_id();

            Artist artistForLastname = ards.getArtistById(searchLastname);

            t2.setText(artistForLastname.getLastname());

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
            return listartadap.get(position);}

    }
}
