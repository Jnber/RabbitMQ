package com.company;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class User {

    private String EXCHANGE_NAME = "Direct_Messages";
    private Frame show;
    private String binding_key;
    private Receiver receiver;

    public User(String binding_key, int size){

        show = new Frame(Integer.valueOf(binding_key) , this, size);
        this.binding_key = binding_key;

    }

    public Frame getShow (){return this.show; }

    public void setReceiver(Receiver receiver){
        this.receiver = receiver;
        this.receiver.addFrame(show);
    }
    public String getBinding_key(){
        return this.binding_key;
    }

    public void send(String message) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        // channel : canal de communication , liaison logique vers le serveur pour envoyer msg
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel() )
        {
            channel.exchangeDeclare(EXCHANGE_NAME,"direct");
            //envoie direct de message (sans utilisation des exchanges )
            channel.basicPublish(EXCHANGE_NAME, binding_key, null, message.getBytes());
            System.out.println("[sender "+binding_key+"] sent '"+ message + "'");
        }
    }
}
