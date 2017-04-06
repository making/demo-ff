# Feature Toggle demo with Config Server / Spring Cloud Services

## Create Config Service

```
cf create-service p-config-server standard config-server -c "{\"count\":1,\"git\":{\"cloneOnStart\":true,\"uri\":\"https://github.com/making/ff-config\"}}"
```

## Deploy Application

```
./mvnw clean package -DskipTests=true && cf push
```

```
$ curl http://demo-ff.apps.example.com/
{"message":"Hello"}
```

```
$ curl -s -u demo:password http://demo-ff.apps.example.com/env | jq '."configService:https://github.com/making/ff-config/demo.properties"'
{
  "feature2.enabled": "false",
  "feature1.enabled": "false"
}
```

## Enable Feature1 (Git)

Change [demo.properties](https://github.com/making/ff-config/blob/master/demo.properties) and `git push` ([diff](https://github.com/making/ff-config/commit/34a6bdf8653539bdce94a9d0c917a1ce8aca588e)), then

```
$ curl -s -u demo:password -X POST http://demo-ff.apps.example.com/refresh

["config.client.version","feature1.enabled"]
```

```
$ curl http://demo-ff.apps.example.com/
{"feature1":"This is feature1!","message":"Hello"}
```

## Enable Feature1 (Adhoc)

```
$ curl -u demo:password http://demo-ff.apps.example.com/env -d feature1.enabled=true
{"feature1.enabled":"true"}
```

```
$ curl -s -u demo:password -X POST http://demo-ff.apps.example.com/refresh
[]
```

```
$ curl http://demo-ff.apps.example.com/
{"feature1":"This is feature1!","message":"Hello"}
```

## Enable Feature2 (Adhoc)

```
$ curl -u demo:password http://demo-ff.apps.example.com/env -d feature2.enabled=true
{"feature2.enabled":"true"}
```

```
$ curl -s -u demo:password -X POST http://demo-ff.apps.example.com/refresh
[]
```

```
$ curl http://demo-ff.apps.example.com/
{"feature2":"This is feature2!","feature1":"This is feature1!","message":"Hello"}
```

## Disable Feature1

```
$ curl -u demo:password http://demo-ff.apps.example.com/env -d feature1.enabled=false
{"feature1.enabled":"false"}
```

```
$ curl -s -u demo:password -X POST http://demo-ff.apps.example.com/refresh
[]
```

```
$ curl http://demo-ff.apps.example.com/
{"feature2":"This is feature2!","message":"Hello"}
```

