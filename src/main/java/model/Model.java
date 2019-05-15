package model;

public class Model {

    private Playlist playlist = new Playlist();
    private Playlist library = new Playlist();

    public Model(){}

    public Playlist getPlaylist() {
        return playlist;
    }

    public Playlist getLibrary() {
        return library;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public void setLibrary(Playlist library) {
        this.library = library;
    }


}
