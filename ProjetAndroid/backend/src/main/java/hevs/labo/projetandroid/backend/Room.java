package hevs.labo.projetandroid.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Created by Anthony on 12/01/2016.
 */
@Entity
public class Room {
    @com.googlecode.objectify.annotation.Id
    private Long id;
    private String name;
    private double size;
    private String image_path;
    private boolean selected;

    public Room() {}

    public Room(String name, double size, boolean selected, String image_path) {
        this.name = name;
        this.size = size;
        this.selected = selected;
        this.image_path = image_path;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public double getSize() { return size; }

    public void setSize(double size) { this.size = size; }

    public boolean isSelected() { return selected; }

    public void setSelected(boolean selected) { this.selected = selected; }

    public String getImage_path() {return image_path; }

    public void setImage_path(String image_path) { this.image_path = image_path; }

    public Long getId(){ return id; }

    public void setId(Long id) { this.id = id; }

    @Override
    public String toString() {
        return name + "   " + size + " m2";
    }
}
