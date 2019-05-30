# Movie-recommandations engine

A movie recommendations engine written in Scala with Spark

Have a look at the [wiki](https://github.com/srmds/recommendation-engine-spark/wiki) for Scala and Spark fundamentals

## Prerequisites
- [Java](https://java.com/en/download/)
- [Gradle](https://gradle.org/)
- [Scala](https://www.scala-lang.org/)

## Build, compile & run

### Clone the Repo

```shell
$ git clone https://github.com/srmds/recommendation-engine-spark
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


## Benchmark of recommendations


### Get all movie ratings

| rating (stars)    | count (votes) | 
|-------------------| --------------|
| 1                 | 6110          | 
| 2                 | 11370         |  
| 3                 | 27145         |  
| 4                 | 34174         |  
| 5                 | 21201         |

See [here](https://github.com/srmds/recommendation-engine-spark/wiki/Analysis-results#get-all-movie-ratings) for full analysis

Source file (100.000 rows): [datasets/movielens/ml-100k/u.data](https://github.com/srmds/recommendation-engine-spark/blob/development/datasets/movielens/ml-100k/u.data)

Elapsed time: `298 ms` 

### Get the averages of friends by ages

| age    | average of friends | 
|------- | --------------|
|18|343|
|19|213|
|26|242|
|27|228|
|28|209|
|34|245|
|35|211|
|36|246|
|37|249|
|38|193|
|39|169|
|67|214|
|68|269|
|69|235|

See [here](https://github.com/srmds/recommendation-engine-spark/wiki/Analysis-results#get-the-averages-of-friends-by-ages) for full analysis

Source file (500 rows): [datasets/friends/fakefriends.csv](https://github.com/srmds/recommendation-engine-spark/blob/development/datasets/friends/fakefriends.csv)

Elapsed time: `173 ms` 

## Weather stations

### Get _minimum_ of temperatures

| stationId | Temperature (Fahrenheit)| 
|-----------| --------------------------------|
|EZE00100082 | 7.700001                       | 
|ITE00100554 | 5.3600006                      |


### Get _maximum_ of temperatures 
| stationId | Temperature (Fahrenheit)| 
|-----------| --------------------------------|
|EZE00100082| 16.52                           |
|ITE00100554| 18.5                            | 

See [here](https://github.com/srmds/recommendation-engine-spark/wiki/Analysis-results#weather-stations) for full analysis

Source file (1825 rows): [datasets/weather/temperatures.csv](https://github.com/srmds/recommendation-engine-spark/blob/development/datasets/weather/temperatures.csv)

Elapsed time: `506 ms` 


## Word occurrences

| count (occurence) | word |
|-------------------|------|
|2|refer|
|3|compared|
|4|forces|
|560|is|
|616|in|
|649|it|
|747|that|
|934|and|
|970|of|
|1191|a|
|1292|the|
|1420|your|
|1828|to|
|1878|you|

See [here](https://github.com/srmds/recommendation-engine-spark/wiki/Analysis-results#word-occurrences) for full analysis

Source file (~46.249 words): [datasets/book/book.txt](https://github.com/srmds/recommendation-engine-spark/blob/development/datasets/book/book.txt)

Elapsed time: _377 ms_

## Spending amount per customer

|amount (spent) | customerId|
|-------|-----------|
|3309.3804|45|
|4316.3|47|
|4327.7305|77|
|4367.62|13|
|4836.86|20|
|4851.4795|89|
|4876.8394|95|
|4898.461|38|
|5206.3994|87|
|5245.0605|52|

See [here](Results.md#markdown-header-word-occurrences) for full analysis

Source file (10.000 rows): [datasets/spending/customer_orders.csv](https://github.com/srmds/recommendation-engine-spark/blob/development/datasets/spending/customer_orders.csv)

Elapsed time: _267 ms_

## Popularity of movies by ratings

|count | movieId|
|--------------|--------|
|1|1494|
|1|1414|
|2|1585|
|2|907|
|2|1547|
|3|1361|
|3|1391|
|4|1223|
|4|1423|
|5|1489|
|5|1333|
|507|181|
|508|100|
|509|258|
|583|50|

See [here](https://github.com/srmds/recommendation-engine-spark/wiki/Analysis-results#popularity-of-movies-by-ratings) for full analysis

Source file (100.000 rows): [datasets/movielens/ml-100k/u.data](https://github.com/srmds/recommendation-engine-spark/blob/development/datasets/movielens/ml-100k/u.data)

Elapsed time: _219 ms_

## Popularity of superhero in social network

### _Most_ popular superhero

|friendsCount | (id,name)|
|-------------|----------|
| 1933 |(859,CAPTAIN AMERICA)|

### _Least_ popular superhero

|friendsCount | (id,name)|
|-------------|----------|
|0|(467,BERSERKER II)|

|friendsCount| (name, id)|
|------------|-----------|
|106|RATTLER|4545|
|238|SUPREME INTELLIGENCE|5555|
|121|LEWIS| SHIRLEY WASHI|3173|
|84|UNICORN/MYLOS MASARY|5955|
|966|ICEMAN/ROBERT BOBBY |2603|
|147|EEL II/EDWARD LAVELL|1689|
|109|BLACK KNIGHT IV/PROF|521|
|668|SILVER SURFER/NORRIN|5121|
|198|STANKOWICZ| FABIAN|5375|
|1014|HERCULES [GREEK GOD]|2449|


See [here](https://github.com/srmds/recommendation-engine-spark/wiki/Analysis-results#popularity-of-superhero-in-social-network) for full analysis

Source files:
 
 - (6.589 rows): [datasets/social/Marvel-graph.txt](https://github.com/srmds/recommendation-engine-spark/blob/development/datasets/social/Marvel-graph.txt)
 - (19.428 rows): [datasets/social/Marvel-names.txt](https://github.com/srmds/recommendation-engine-spark/blob/development/datasets/social/Marvel-names.txt)

Elapsed time: _1515 ms_

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
