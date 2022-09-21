<<<<<<< HEAD
# Http Server
Http server is a Kotlin application that can be used to serve files over the web
=======
# Echo Server
Echo server is a Kotlin application that returns the text a user inputs once connected.
>>>>>>> 24458258e68ceb98bb4afb5fdd6eedd4e3c1ced9

## Installation


Clone the repo
```git
<<<<<<< HEAD
git clone https://github.com/drewb8L/echo_server.git
```
Optionally, you can see the latest features with:
```git
git checkout http_server
=======
git clone https://github.com/drewb8L/echo_server.git 
>>>>>>> 24458258e68ceb98bb4afb5fdd6eedd4e3c1ced9
```

## Usage

<<<<<<< HEAD
### Note:
This read me will be updated once gradle errors are resolved. As of now, using Intelij is the recomended was to build and run the server.
# ___________________________________________

=======
Before you can connect to the server download a client like telnet if you don't already have a suitable client.
```bash
sudo apt install telnet
```
>>>>>>> 24458258e68ceb98bb4afb5fdd6eedd4e3c1ced9
Open repo in IntelliJ Idea and allow the IDE to install sdk and dependencies.
Run the main function to start the server.

Once the server is running open your client and connect to 127.0.0.1 port 8080.

when a connection is established, you will see this message,

```"Enter some text to send to the server, close connection by entering '.':"```

The server will echo your input after you type in some text and hit enter.

To close the connection type a period '.' with no other text or space and hit enter. The connection will close.

## License
[MIT](https://choosealicense.com/licenses/mit/)