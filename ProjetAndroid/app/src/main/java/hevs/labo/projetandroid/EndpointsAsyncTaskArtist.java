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

import hevs.labo.projetandroid.backend.artistApi.ArtistApi;
import hevs.labo.projetandroid.backend.artistApi.model.Artist;
import hevs.labo.projetandroid.backend.myApi.MyApi;

/**
 * Created by Anthony on 12/01/2016.
 */
public class EndpointsAsyncTaskArtist extends AsyncTask<Pair<Context, Artist>, Void, String> {
    private static ArtistApi artistApi = null;
    private Context context;
    private static final String TAG = EndpointsAsyncTaskArtist.class.getName();
    private Artist artist;

    @Override
    protected String doInBackground(Pair<Context, Artist>... params) {
        if (artistApi == null) {
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

        context = params[0].first;
        artist = params[0].second;

        try {
            if (artist != null) {
                return artistApi.insert(artist).execute().getId().toString();
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return e.getMessage();
        }

        return "try again...";
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
