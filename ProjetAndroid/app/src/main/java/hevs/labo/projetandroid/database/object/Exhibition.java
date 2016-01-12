package hevs.labo.projetandroid.database.object;

/**
 * Created by Anthony on 30/11/2015.
 */
public class Exhibition {
    private String artist;
    private String artwork;
    private String room;

    public Exhibition() {}

    public Exhibition(String artist, String artwork, String room) {
        this.artist = artist;
        this.artwork = artwork;
        this.room = room;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtwork() {
        return artwork;
    }

    public void setArtwork(String artwork) {
        this.artwork = artwork;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
