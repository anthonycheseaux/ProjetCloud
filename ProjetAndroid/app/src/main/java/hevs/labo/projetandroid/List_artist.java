package hevs.labo.projetandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
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
import hevs.labo.projetandroid.database.object.Artist;

public class List_artist extends AppCompatActivity{

    ListView listView_artist;
    List<Artist> list_artist;
    String[] tabArtistCreated;
    private Artist artistpicked;
    private ActionMode mActionMode = null;
    String occup_expo;

    ArtistAdapter listadapter_artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_artist);
        //getSupportActionBar().show();


        final ArtistDataSource artistDataSource = new ArtistDataSource(this);

        View header = getLayoutInflater().inflate(R.layout.header_artist, null);

        list_artist = artistDataSource.getAllArtists();

        listadapter_artist = new ArtistAdapter(this.getApplicationContext(), list_artist);


        ListView lv = (ListView) findViewById(R.id.list_artist);

        lv.addHeaderView(header);

        lv.setAdapter(listadapter_artist);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artist i = listadapter_artist.getArtist(position -1);
                sendCardArtist(i.getId());
            }
        });
    }



    public void sendCardArtist(int id){
        Intent intent = new Intent(this, Card_artist.class);
        intent.putExtra("id_artistRecup", id);
        startActivity(intent);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_artist, menu);
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

            case R.id.addArtist_menu:
                Intent intentaddArtistmenu = new Intent(this, Create_artist.class);
                startActivity(intentaddArtistmenu);
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }



    public class ArtistAdapter extends BaseAdapter{

        ArtistDataSource ards;
        List<Artist> listartistdap;

        public ArtistAdapter(Context context, List<Artist> listartist){
            ards = new ArtistDataSource(context);
            listartistdap = getDataForListView();
        }


        public List<Artist> getDataForListView() {
            List<Artist> listArtist;
            listArtist = ards.getAllArtists();

            return listArtist;
        }


        @Override
        public int getCount() {
            return listartistdap.size();
        }

        @Override
        public Object getItem(int position) {
            return listartistdap.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null)
            {
                LayoutInflater inflater = (LayoutInflater) List_artist.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.activity_list_artist_adapter, parent, false);
            }

            TextView t1 = (TextView)convertView.findViewById(R.id.label1_lastname_artist);
            TextView t2 = (TextView) convertView.findViewById(R.id.label2_pseudo_artist);
            ImageView i3 = (ImageView) convertView.findViewById(R.id.logo_exposed);

            Artist r = listartistdap.get(position);

            t1.setText(r.getLastname());

            t2.setText(r.getPseudo());

            if(r.isExposed() == true){
                i3.setImageDrawable(getResources().getDrawable(R.drawable.exposed));
            }
            else
            {
                i3.setImageDrawable(getResources().getDrawable(R.drawable.notexposed));
            }

            return convertView;
        }


        public Artist getArtist(int position) {return listartistdap.get(position);}


    }
}
