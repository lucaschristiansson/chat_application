Project for Object Oriented Applications course at Chalmers technical university. Developed by @marakerl @auniquee @lucaschristiansson @Eweh4 @Metterklus1
About
Chat Application is a project aiming to create a scalable and well-designed application in Java. We always aimed to have proper design following as many object oriented design patterns as reasonable. Adding new features to the project is easy due to the extendable design. 
The result is an application where you can chat in different channels, have different accounts and add new channels.
The program has the following functionality:
Browse chat instances
Connect to a chat.
View a persistent chat history.
Send a text message.
Send an image.
Receive new text messages or images when sent from other users.
See and hide list of available channels
See and hide list of users in channel
See which user sent which message or image.


*User manual*
Docker setup
In order to run the docker-compose file the user must have docker desktop (windows) or docker/docker-compose (linux) installed on their PC. 
To start the docker container that houses the database the user will need to navigate to the directory containing the docker compose file, which is the server package in the program, and type “docker compose up” in their terminal. 
This starts the database at the preselected port.
If the user wishes to change the port of the database they would need to change the port in the docker-compose.yml file, in the ports section. 
Ports are written in a specified format: xxxx:xxxx where the first number is the local port that needs to be changed. In order for the server to function the port would also need to be changed in the PortalConnection class to correspond to the newly selected port. 
This is changed in the getDB method.

Rest of the setup
To start the server once the SQL database is running the main method in the Main class on the server side needs to run. 
To run the client the main method of the Launcher class is started, if the server and client are on the same network the connection should be established automatically. 
