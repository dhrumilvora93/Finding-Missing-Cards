# Finding Minssing Cards

This Map Reduce Program will find missing poker cards from the deck of cards mentioned in input.txt and will provide output in the output folder

## Prerequisite

- Need Hadoop installed in the system

- Need Java installed in the system

### Refer the following link to setup the environment

#### Map Reduce Setup:
[Digital Ocean](https://www.digitalocean.com/community/tutorials/how-to-install-hadoop-in-stand-alone-mode-on-ubuntu-16-04)

### Compile program

```
javac -classpath ${HADOOP_CLASSPATH} -d MissingCards MissingCards.java
```

### Adding Class files to jar

```
jar -cvf MissingCards.jar -C MissingCards/ .
```

### Run

```
hadoop jar MissingCards.jar /input.txt /output
```

#### Input format 

```
Suite, Card_Number
```

#### Output format

```
Suite	Card_Number
```