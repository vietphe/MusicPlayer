package model;

import interfaces.IPlaylist;
import interfaces.ISong;
import javafx.collections.ModifiableObservableListBase;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Playlist extends ModifiableObservableListBase<ISong> implements IPlaylist , Serializable {

    private ArrayList<ISong> playlist = new ArrayList<>();

    @Override
    public boolean addSong(ISong s) throws RemoteException {
        playlist.add(s);
        return false;
    }

    @Override
    public boolean deleteSong(ISong s) throws RemoteException {
        playlist.remove(s);
        return false;
    }

    @Override
    public boolean deleteSongByID(long id) throws RemoteException {
        playlist.remove(findSongByID(id));
        return false;
    }

    @Override
    public void setList(ArrayList<ISong> s) throws RemoteException {
        playlist = s;

    }

    @Override
    public ArrayList<ISong> getList() throws RemoteException {
        return playlist;
    }

    @Override
    public void clearPlaylist() throws RemoteException {
        playlist.clear();

    }

    @Override
    public int sizeOfPlaylist() throws RemoteException {
        return playlist.size();
    }

    @Override
    public ISong findSongByPath(String path) throws RemoteException {
        for(ISong s:playlist){
           if (s.getPath().equals(path)){
               return s;
           }
        }return null;
    }


/*
        boolean found = false;
        Song song = new I;
        for(int i = 0; (0<playlist.size()) && (found == false); i++)
        {

            if (playlist.get(i).getPath().equals(path))
            {
                song = playlist.get(i);
                found = true;
            }
        }

        if (found) return song;

        throw new RemoteException();

    }*/



    @Override
    public ISong findSongByID(long id) throws RemoteException {
        for(ISong s: playlist){
            if(s.getId() == id){
                return s;
            }
        }
        return null;
    }

    @Override
    public ISong get(int index) {
        try {
            return playlist.get(index);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int size() {
        return playlist.size();
    }

    @Override
    protected void doAdd(int index, ISong element) {
        playlist.add(index,element);

    }

    @Override
    protected ISong doSet(int index, ISong element) {
        playlist.set(index,element);
        return playlist.get(index);
    }

    @Override
    protected ISong doRemove(int index) {
        playlist.remove(index);
        return null;
    }
}
