# TwitterClientExample

[![Build Status](https://travis-ci.com/royshahaf/TwitterClientExample.svg?branch=master)](https://travis-ci.com/royshahaf/TwitterClientExample)

The app will have 2 kinds of users:

Regular users:

1. Can define topics which interest them.

2. Receive tweets according to the topics they chose.

Administrators

1. Have the same abilities as regular users.

2. Can administer topics for other users (view, add, edit and delete).

## Built With

* [Twitter4j](http://twitter4j.org/en/) - A twitter api for java
* [Maven](https://maven.apache.org/) - Dependency Management
* [Spark Java](http://sparkjava.com/) - A lightweight http framework for java
* [Guice](https://github.com/google/guice) - Dependency Injection
* [mongodb-driver](https://mongodb.github.io/mongo-java-driver/) - A driver for accessing mongo from java, we used the synchronous api, for simplicity
* [slf4j](https://www.slf4j.org/) - A logging facade, allows easier transition between logging implementations
* [slf4j-log4j12] - A binding of slf4j to log4j
* [tyrus-standalone-client](https://tyrus-project.github.io/dependencies.html) - A websocket implementation
* [Gson](https://github.com/google/gson) - A Java serialization/deserialization library to convert Java Objects into JSON and back

### Things already implemented
1. TopicsManager - An application which (currently) uses spark java to expose http endpoints for viewing, adding, deleting, and editing topics
    1. Topics are currently persisted against a mongodb server
    2. Role based access checks for operations
    3. The terminology we use (and is visible on the client side) is: 
        * requesting - the user requesting the action (for example I am adding a topic)
        * requested - the user for which we want the action to happen (for example the topic I am adding will be added to the requested user)
2. TweetSender - An application which (currently) uses twiter4j to create a twitter stream (filtering by the sum of all topics uses are interested in) and broadcasting the tweets via websocket
3. Configuration
    1. prefixed by "twitter.example."
    2. topicsPort - port used to expose http endpoints of TopicsManager
    3. secure, keystoreFilePath, keystorePassword, trustFilePath, trustPassword - security parameters used to setup https for TopicsManager and wss for TweetSender
    4. connectionString, databaseName, collectionName - Used to setup mongodb for both applications

### Things to implement
1. The current implementation would fail when we have over 200 topics (a single twitter stream cannot handle more than 200 topics, according to the twitter api). Alternatives:
    1. Use firehose (requires special permissions from twitter)
    2. Use another service that will divide topics between the various topics (will require multiple twitter developer credentials)
2. The current implementation forces all users to receive tweets on topics they aren't interested in (if another user is interested in them). Alternatives:
    1. I was trying to use kafka to split the data collected by the twitter stream into topics, consuming only topics of interest depending on the current user (but I ran out of time). For this approach we will need to learn how to determine if a tweet belongs to a topic (I couldn't find a nice library / documentation of how twitter determines that)
    2. Another option is to use websockets to implement "topics"
3. The current implementation lacks actual authentication (so users can pretend to be other users). Alternatives:
    1. Use OAuth
4. Performance monitoring
5. Other abilities
    1. add, remove, and edit users (currently it needs to be set up directly via mongo)
    2. pause and resume tweets page (This is more of a client-side ability but I'm sort of referring to both of them in this page)
6. UI / UX improvements - make the app look better (using css or more material design)

## Getting started

### Prerequisites
1. MongoDB - a node must be up and reachable
    1. You will need to setup a users collection with documents of the form:``{"_id":"5bdc0dcce8e87af7c3a247d8","name":"adam","roles":["REGULAR","ADMIN"],"topics":[]}``
    2. Then you need to set environment variables to work with your MongoDB:
        1. twitter.example.connectionString - for example ``http://localhost:27017``
        2. twitter.example.databaseName
        3. twitter.example.collectionName
2. Java8+ and Maven - after cloning (or downloading) the project you'll need to run mvn install to download all of the dependencies
    1. To run TweetSender you will need to create a file named twitter4j.properties and fill it with your twitter credentials: 
        * ``debug=false``
        * ``oauth.consumerKey=<your consumer key>``
        * ``oauth.consumerSecret=<your consumer secret>``
        * ``oauth.accessToken=<your access token>``
        * ``oauth.accessTokenSecret=<your access token secret>``
    2. Running the java applications can be done via the IDE or in the following way
        1. in the project folder (the folder that contains the pom.xml file) run mvn dependency:copy-dependencies to download all of the dependencies and mvn install to create the jar for this project
        2. TopicsManager - ``java -classpath "target\dependency\*;target\TwitterApp-0.0.1-SNAPSHOT.jar" app.TopicsManager``
        3. TweetSender - ``java -classpath "target\dependency\*;target\TwitterApp-0.0.1-SNAPSHOT.jar" app.TweetSender``
3. For information about running the client side, please go [here](https://github.com/royshahaf/TwitterClientExample/tree/master/my-app)