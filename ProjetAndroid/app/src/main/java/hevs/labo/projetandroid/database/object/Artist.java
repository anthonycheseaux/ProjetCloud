package hevs.labo.projetandroid.database.object;

/**
 * Created by Darlène on 23.10.2015.
 */
public class Artist {

    private int id;
    private String firstname;
    private String lastname;
    private String pseudo;
    private String birth;
    private String death;
    private String movement;
    private String image_path;
    private boolean exposed;

    public  Artist(){}

    public Artist(String firstname, String lastname, String pseudo,String birth, String death, String movement,String image_path, boolean selected){
        this.firstname = firstname;
        this.lastname = lastname;
        this.pseudo = pseudo;
        this.birth = birth;
        this.death = death;
        this.movement = movement;
        this.image_path = image_path;
        this.exposed = selected;
    }



    public String toString(){



            return firstname + "    " + lastname + "    " + pseudo  ;


    }

    //getter

    public int getId() { return id; }

    public String getFirstname(){
        return firstname;
    }

    public String getLastname(){
        return lastname;
    }

    public String getPseudo(){
        return pseudo;
    }

    public boolean isExposed(){return exposed;}

    public String getBirth(){
        return birth;
    }

    public String getDeath(){
        return  death;
    }

    public String getMovement(){
        return  movement;
    }

    public String getImage_path() { return image_path; }


    //setter
    public void setId(int id) { this.id = id; }

    public void setFirstname(String firstname){
        this.firstname = firstname;
    }

    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public void setPseudo(String pseudo){
        this.pseudo = pseudo;
    }

    public void setExposed(boolean selected){
        this.exposed = selected;
    }

    public void setBirth(String birth){
        this.birth = birth;
    }

    public void setDeath(String death){
        this.death = death;
    }

    public  void setMovement(String movement){
        this.movement = movement;
    }

    public void setImage_path(String image_path) { this.image_path = image_path; }


}
