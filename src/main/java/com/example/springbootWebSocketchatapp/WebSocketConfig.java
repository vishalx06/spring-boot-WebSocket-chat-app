package com.example.springbootWebSocketchatapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/*
@EnableWebSocketMessageBroker: This annotation is used to enable the WebSocket Server.
We also implement the WebSocketMessageBrokerConfigurer interface in order to provide
definitions or logic to some of its methods to establish a WebSocket connection.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /*
    * In this method, we define a WebSocket endpoint so that client will use it to establish a connection with our WebSocket server.
    *  If you noticed, there is the word Stomp in the method name, Actually, these all methods come from the STOMP implementation in
        Spring frameworks. The full form of STOMP is the Simple Text Oriented Messaging Protocol.
    * In simple words, STOMP is a messaging protocol that defines the rules and format for data exchanging between server and client.
    * So, the question is Why we need STOMP for developing the chat application?? As we already using the WebSocket for exchanging the messages.
        You know the problem with WebSocket is that it fails to identify particular users.
    * For example, there are no functionalities in Websocket to send the message only to the users who subscribed to the channel or topic or How to
        send the message to selective users.
    */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    /*
    * This method is used to route the message from one client to another client, who is in the group chat.
        In more simple terms, when one user sends the message in the group chat, it appears in front of all the group members.
    * This functionality we can achieve with the method configureMessageBroker(MessageBrokerRegistry registry).
    * Inside the above method, the very first line is used to define the messages whose destination starts with “/app” and it
        should be routed to the message handling method. Still, we have not defined the message handling method, we will define shortly.
    * The purpose of the second line is to defines that messages whose destination starts with “/topics” and it should be routed to
        the message broker. And Message Broker will broadcast the messages to all the connected users or clients who subscribed to that topic.
    Note: In our application, we are using an in-memory message broker, but there are lots of message broker out there in the market such as RabbitMQ
    or ActiveMQ, you can use any one of them as per your requirements and need.
    */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }
}