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

import hevs.labo.projetandroid.database.adapter.RoomDataSource;
import hevs.labo.projetandroid.database.object.Room;

public class List_room extends AppCompatActivity {

    ListView listView_room;
    //List<Room> list_room;
    Room[] listRoom;
    String[] tabRoomCreated;
    private Room roompicked;
    private ActionMode mActionMode = null;
    String occup;

    List<Room>list_room;

    RoomAdapter listeadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_room);
        //getSupportActionBar().show();

        final RoomDataSource roomDataSource = new RoomDataSource(this);

        View header = getLayoutInflater().inflate(R.layout.header_row, null);

       list_room = roomDataSource.getAllRooms();

        List<Room> temp = roomDataSource.getAllRooms();
        listRoom = new Room[temp.size()];
        temp.toArray(listRoom);

        listeadapter = new RoomAdapter(this.getApplicationContext(), list_room);


        ListView lv = (ListView) findViewById(R.id.listView_room);

        lv.addHeaderView(header);

        lv.setAdapter(listeadapter);



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Room i = listeadapter.getRoom(position - 1);
                sendCarRoom(i.getId());

            }
        });

    }

    public void sendCarRoom(int id){
        Intent intent = new Intent(this, Card_room.class);
        intent.putExtra("id_RoomRecup", id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_room, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){

            case R.id.parametres_menu:
                Intent intentsettings = new Intent(this, Settings.class);
                startActivity(intentsettings);
                return true;

            case R.id.addRoom_menu:
                Intent intentaddRoom = new Intent(this, Create_room.class);
                startActivity(intentaddRoom);
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }



    public class RoomAdapter extends BaseAdapter{


        RoomDataSource rds;
        List<Room>listroomadap;


        public RoomAdapter(Context context, List<Room> listrooma){
            rds = new RoomDataSource(context);
            listroomadap = getDataForListView();
        }


        public List<Room> getDataForListView() {
            List<Room> listRoom;
            listRoom = rds.getAllRooms();

            return listRoom;
        }

        @Override
        public int getCount() {
            return listroomadap.size();
        }


        @Override
        public Object getItem(int position) {
            return listroomadap.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

         if(convertView == null)
            {
                LayoutInflater inflater = (LayoutInflater) List_room.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.activity_list_room_adapter, parent, false);
            }

            TextView t1 = (TextView)convertView.findViewById(R.id.label1);
            TextView t2 = (TextView) convertView.findViewById(R.id.label2);
            ImageView i3 = (ImageView) convertView.findViewById(R.id.logo);

            Room r = listroomadap.get(position);

            t1.setText(r.getName());

            t2.setText(Double.toString(r.getSize()));

            if(r.isSelected() == false){
                i3.setImageDrawable(getResources().getDrawable(R.drawable.occuped));
            }
            else
            {
                i3.setImageDrawable(getResources().getDrawable(R.drawable.dispo));
            }

            return convertView;
        }


        public Room getRoom(int position) {return listroomadap.get(position);}


    }
}


