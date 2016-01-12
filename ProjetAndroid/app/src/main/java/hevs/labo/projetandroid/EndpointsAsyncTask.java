package hevs.labo.projetandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.List;

import hevs.labo.projetandroid.backend.artistApi.ArtistApi;
import hevs.labo.projetandroid.backend.artistApi.model.Artist;
import hevs.labo.projetandroid.backend.artworkApi.ArtworkApi;
import hevs.labo.projetandroid.backend.artworkApi.model.Artwork;
import hevs.labo.projetandroid.backend.roomApi.RoomApi;
import hevs.labo.projetandroid.backend.roomApi.model.Room;

/**
 * Created by Anthony on 12/01/2016.
 */
public class EndpointsAsyncTask extends AsyncTask<Pair<Context, List<List>>, Void, String> {

    //API Object
    private static ArtistApi artistApi = null;
    private static ArtworkApi artworkApi= null;
    private static RoomApi roomApi = null;

    //List of each backend entity
    private List<Artist> artistList;
    private List<Artwork> artworkList;
    private List<Room> roomList;

    private Context context;
    private static final String TAG = EndpointsAsyncTaskArtist.class.getName();

    @Override
    protected String doInBackground(Pair<Context, List<List>>... params) {

        if (artistApi == null ) {
            // Only do this once
            ArtistApi.Builder builder = new ArtistApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            artistApi = builder.build();
        }

        if (artworkApi == null ) {
            // Only do this once
            ArtworkApi.Builder builder = new ArtworkApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            artworkApi = builder.build();
        }

        if (roomApi == null ) {
            // Only do this once
            RoomApi.Builder builder = new RoomApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            roomApi = builder.build();
        }

        context = params[0].first;
        List<List> list = params[0].second;

        artistList = list.get(0);
        artworkList = list.get(1);
        roomList = list.get(2);

        try {
            if (artistList != null) {
                for(Artist artist : artistList) {
                    artistApi.insert(artist).execute();
                }
            }

            if (artworkList != null) {
                for(Artwork artwork : artworkList) {
                    artworkApi.insert(artwork).execute();
                }
            }

            if (roomList != null) {
                for(Room room : roomList) {
                    roomApi.insert(room).execute();
                }
            }

            return "Successfull";

        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
