package hevs.labo.projetandroid.database.object;

/**
 * Created by Anthony on 12/01/2016.
 */
public class Sync {
    public enum Type { insert, update, delete}
    public enum Table { artist, artwork, room}

    private int id;
    private Table table;
    private long objectId;
    private Type type;

    public Sync() {}

    public Sync(Table table, long objectId, Type type) {
        this.table = table;
        this.objectId = objectId;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
