package model;


import interfaces.ISong;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import java.io.Serializable;

public class Song implements ISong ,Serializable {
    public static final long serialVersionUID = 12345L;
    private long id = 0;

    private String path ;
    private String title ;
    private String album ;
    private String interpret;


    public Song(long id, String path, String interpret, String album,String title) throws Exception{

        this.path = path;
        this.interpret = interpret;
        this.album = album;
        this.title = title;
        this.id = id;
    }


   /* public Song (String path, String interpret, String album, String title) throws Exception{

        this.path.set(path);
        this.interpret.set(interpret);
        this.album.set(album);
        this.title.set(title);
       /* try {
            this.id = IDGenerator.getNextID();
        }catch(IdOverFlowException e){
            System.out.println(e.getMessage());
        }
    }*/
    public Song(){}

    @Override
    public String getAlbum() {
        return this.album;
    }

    @Override
    public void setAlbum(String album) {
        this.album = album;

    }

    @Override
    public String getInterpret() {
        return this.interpret;
    }

    @Override
    public void setInterpret(String interpret) {
        this.interpret = interpret;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;

    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title= title;

    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id= id;

    }

    @Override
    public ObservableValue<String> pathProperty() {
        SimpleStringProperty path =new SimpleStringProperty();
        this.getPath();
        return path;
    }

    @Override
    public ObservableValue<String> albumProperty() {
        SimpleStringProperty album =new SimpleStringProperty();
        this.getAlbum();
        return album;
    }

    @Override
    public ObservableValue<String> interpretProperty() {
        SimpleStringProperty interpret=new SimpleStringProperty();
        this.getInterpret();
        return interpret;
    }

    @Override
    public ObservableValue<String> titleProperty() {
        SimpleStringProperty title =new SimpleStringProperty();
        this.getTitle();
        return title;
    }

    public String toString(){ return this.title;}

}
