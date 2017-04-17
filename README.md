# Android Deployd Client
A library to simplify [Deployd](http://deployd.com) integration in Android

## Getting started
```java
Deployd.init("http://server:2403/", context);
```

## Examples
#### Auth
```java
Deployd.auth().login("username", "password");
Deployd.auth().logout();
```

#### Accessing collections
```java
Deployd.collection("users").get();
Deployd.collection("users").get("id");

JSONObject query;
Deployd.collection("users").get(query);
```

#### Insert data
```java
JSONObject data;
Deployd.collection("users").post(data);
```

#### Updating data
```java
JSONObject data;
Deployd.collection("users").put("id", data);
```

#### Deleting data
```java
JSONObject query;
Deployd.collection("users").del(query);
Deployd.collection("users").del("id");
```
## Running tests
```
./gradlew test
```

## Roadmap
- Offline data availability
- Sockets