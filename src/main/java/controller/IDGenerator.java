package controller;


import java.util.ArrayList;
import java.util.Random;

public class IDGenerator {

    public static ArrayList<Long> songID = new ArrayList<>();


    public static long getNextID() throws IdOverFlowException {

        Random random = new Random();
        Long id = (long) random.nextInt(9999);
        if (songID.size()<=9999) {
            if (!songID.contains(id)) {
                songID.add(id);
            }
        }
        return id;
    }

}
