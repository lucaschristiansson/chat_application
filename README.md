# Chat Application

A multi-threaded client-server chat application written in Java, built for the **Object Oriented Applications** course at Chalmers University of Technology.

**Developed by:** [@marakerl](https://github.com/marakerl) · [@auniquee](https://github.com/auniquee) · [@lucaschristiansson](https://github.com/lucaschristiansson) · [@Eweh4](https://github.com/Eweh4) · [@Metterklus1](https://github.com/Metterklus1)

---

## About

The goal of the project was to build a scalable, well-designed chat application in Java. Throughout development we applied object-oriented design patterns wherever they made sense, which keeps the codebase modular and makes new features straightforward to add.

Users can create accounts, chat in multiple channels, and create new channels. Messages and images are delivered in real time and stored persistently.

### Features

- Browse available chat channels
- Connect to a chat channel
- View persistent chat history
- Send text messages and images
- Receive messages and images from other users in real time
- Show or hide the list of available channels
- Show or hide the list of users in a channel
- See which user sent each message or image

### Tech stack

| Area | Technology |
|------|------------|
| Client UI | JavaFX (designed in Figma) |
| Networking | Java Sockets, multi-threaded server |
| Database | PostgreSQL (3NF-normalized schema) |
| Infrastructure | Docker / Docker Compose |

---

## Getting started

### Prerequisites

- Java (JDK)
- **Windows/macOS:** Docker Desktop
- **Linux:** `docker` and `docker-compose`

### 1. Start the database

The PostgreSQL database runs in a Docker container. From the **server** package (the directory containing `docker-compose.yml`), run:

```bash
docker compose up
```

This starts the database on the preconfigured port.

#### Changing the database port (optional)

1. In `docker-compose.yml`, edit the `ports` section. Ports use the format `HOST:CONTAINER` — change the **first** number (the local port).
2. Update the port in the `getDB` method of the `PortalConnection` class so the server connects to the new port.

### 2. Start the server

With the database running, run the `main` method in the server's `Main` class.

### 3. Start the client

Run the `main` method in the `Launcher` class. If the client and server are on the same network, the connection is established automatically.

You can launch several clients to chat between multiple users.
