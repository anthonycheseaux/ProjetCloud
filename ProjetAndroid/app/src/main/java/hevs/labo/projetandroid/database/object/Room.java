package hevs.labo.projetandroid.database.object;

/**
 * Created by Anthony on 28/10/2015.
 */
public class Room {

    private int id;
    private String name;
    private double size;
    private String image_path;
    private boolean selected;
    private int cloud;

    public Room() {}

    public Room(String name, double size, boolean selected, String image_path, int cloud) {
        this.name = name;
        this.size = size;
        this.selected = selected;
        this.image_path = image_path;
        this.cloud = cloud;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public double getSize() { return size; }

    public void setSize(double size) { this.size = size; }

    public boolean isSelected() { return selected; }

    public void setSelected(boolean selected) { this.selected = selected; }

    public String getImage_path() {return image_path; }

    public void setImage_path(String image_path) { this.image_path = image_path; }

    public int getId(){ return id; }

    public void setId(int id) { this.id = id; }

    public int getCloud() {
        return cloud;
    }

    public void setCloud(int cloud) {
        this.cloud = cloud;
    }

    @Override
    public String toString() {
        return name + "   " + size + " m2";
    }
}
