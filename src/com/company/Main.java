package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception{

        HomeFrame home = new HomeFrame();
        while( home.getNbOfSenders() == 0 ){ System.out.println(home.getNbOfSenders());}
        int nbOfSenders = home.getNbOfSenders();
        ArrayList<User> users = new ArrayList<User>();
        for(int i=0; i<nbOfSenders; i++){ users.add(new User(String.valueOf(i),nbOfSenders)); }
        Receiver receiver= new Receiver(users);
        for(int i=0; i<nbOfSenders; i++){ users.get(i).setReceiver(receiver); }
        receiver.receive();

    }
}
