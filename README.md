# podcasts-restservice

## Getting started

To run the application with Maven:

```sh
mvn spring-boot:run
```

OR with Maven Wrapper:

```sh
./mvnw spring-boot:run
```

## Build a Docker image

Make sure the `docker.service` is running.

```sh
systemctl is-active docker.service
```

If it's not running start it with:

```sh
systemctl start docker.service
```

If you want to be able to run the `docker` CLI command as a non-root user,
add your user to the `docker` user group, re-login, and restart `docker.service`.
Otherwise, you have to use `sudo` to build the image.

```sh
sudo mvn spring-boot:build-image -Dspring-boot.build-image.imageName=podcasts.com/restservice
```

## Testing

**Note!** The `GET` endpoints that return collections have pagination.

Get episodes for a podcast:

```sh
curl -X GET -u user -G --data-urlencode "podcastName=Lex Fridman Podcast" localhost:8080/api/podcast/episodes
```

Add episode to a podcast:

```sh
curl -H "Content-Type: application/json" -d '{"title": "#1 - The Begginning", "published": "2023-06-01", "podcastId": 2}' -u user localhost:8080/api/podcast/episode
```

Get podcasts:

```sh
curl -X GET -i -u user localhost:8080/api/podcasts
```

Get podcast by id:

```sh
curl -X GET -u user localhost:8080/api/podcast/1
```

Get podcast by name:

```sh
curl -X GET -u user -G --data-urlencode "podcastName=Darknet diaries" localhost:8080/api/podcast
```
