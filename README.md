There are two clients

BookSystemClient1 and BookSystemClient2  

Both have username and password which is sent to authenticate the clients.
If a wrong username and password are sent from any of the clients, it will throw a http unauthorized error.

The clients send periodic requests using Springboot scheduler.

This is deployed as a java application on springboot embedded tomcat. 

You can see the messages on console for the requests being sent to server periodically. 
