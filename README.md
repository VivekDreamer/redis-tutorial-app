
## How to setup the project?

**1. Clone the repository** 
```bash
  git clone https://github.com/VivekDreamer/redis-tutorial-app
```
**2. Install the redis server or setup redis on docker**
```bash
  1. Download the latest version of the Redis image:
     docker pull redis
  2. Now that you have the Redis image, you can start a Redis 
     container using the following command:

     docker run --name my-redis-container -d -p 6379:6379 redis
     
    --name my-redis-container: Assign a name to your Redis 
      container (you can choose any name).
    -d: Run the container in detached mode (in the background).
    -p 6379:6379: Map port 6379 on your host machine to port 6379
     in the container.
    redis: The name of the Redis image to run.
```
**3. Setup Redis configuration java file by providing hostName and port.**
```bash
  Path of Redis configuration java file:
  src/main/java/com/example/vivek/springbootredistutorialapp/config
```
