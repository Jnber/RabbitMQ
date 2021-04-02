package com.company;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import java.util.ArrayList;

public class Receiver {

    private String EXCHANGE_NAME = "Direct_Messages";
    private ArrayList<String> senders;
    private ArrayList<Frame> frames;

    public Receiver(ArrayList<User> senders) {
        this.senders = new ArrayList<String>();
        this.frames = new ArrayList<Frame>();
        for (User sender : senders) {
            this.senders.add(sender.getBinding_key());
        }
    }

    public void addFrame (Frame f){frames.add(f); }

    public void receive() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        // channel : canal de communication , liaison logique vers le serveur pour envoyer msg
        try(Connection connection = factory.newConnection();
            Channel channel1 = connection.createChannel() ;
        )
        {
            channel1.exchangeDeclare(EXCHANGE_NAME,"direct");
            String queue_name = channel1.queueDeclare().getQueue();
            for(String sender: senders){
                channel1.queueBind(queue_name,EXCHANGE_NAME,sender);
            }
            System.out.println("[*] waiting for messages from"+ queue_name+". To exit press Ctrl+C");
            //fct qui se declenche automatiquement à chaque fois qu'il y a un msg dans la file
            DeliverCallback deliverCallback = (consumerTag, delivery ) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("[x] received '" + message + "' , from sender: "+ delivery.getEnvelope().getRoutingKey());
                for (int i=0 ; i<frames.size(); i++){
                    if (!delivery.getEnvelope().getRoutingKey().equals(senders.get(i)))
                    frames.get(i).write(message,delivery.getEnvelope().getRoutingKey());
                }

            };
            while(true)
                channel1.basicConsume(queue_name, true, deliverCallback, ConsumerTag->{});
        }
    }}
