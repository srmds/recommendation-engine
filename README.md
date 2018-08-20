# Movie-recommandations engine

A movie recommendations engine written in Scala with Spark

## Prerequisites
- [Java](https://java.com/en/download/)
- [Gradle](https://gradle.org/)
- [Scala](https://www.scala-lang.org/)

## Build, compile & run

### Clone the Repo

```shell
$ git clone git@github.com:srmds/movie-recommandations.git
```

### Configure


Logging is done via [log4j](https://logging.apache.org/log4j/2.x/)

A template for log4j properties is included in the _src/main/resources/log4j.properties.template_ path.

- Create a custom log4j.properties file by using the template, from root of project run:


```shell
$ cp src/main/resources/log4j.properties.template src/main/resources/log4j.properties 
```

In order to have less verbose logging and only log our own explicit log lines, change the default logging settings.

- Set the the loggin level from: _INFO_ to _ERROR_:

Change the following line:

```shell
log4j.rootCategory=INFO, console
```

to:

```shell
log4j.rootCategory=ERROR, console
```

_Note:_ the custom _log4j.properties_ file should not be checked into version control and is therefore added to the _.gitignore_ file.

### Build

```shell
$ ./gradlew clean build
```
### Run

```shell
$ ./gradlew run
```

### All Together

```shell
$ ./gradlew clean run
```

## Dependencies

- Spark - 2.1.0

## Resources
- [Spark Docs - Root Page](http://spark.apache.org/docs/latest/)
- [Spark Programming Guide](http://spark.apache.org/docs/latest/programming-guide.html)
- [Spark Latest API docs](http://spark.apache.org/docs/latest/api/)
- [Scala API Docs](http://www.scala-lang.org/api/2.12.1/scala/)

## License

MIT License

Copyright (c) 2018 srmds

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.