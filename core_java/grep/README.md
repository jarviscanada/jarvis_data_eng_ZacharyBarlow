# Introduction
This app is an implementation of the Linux grep command. It takes the regex/pattern that you want to
search for, directory in which the search will happen, and the file it outputs the
results to. As follows:

```
$ java -cp <jar file> <class_file> <regex/pattern> <directory> <out_file>
```
There are two implementations of this application:
1. JavaGrepImp : uses for loops for searching
2. JavaGrepLambdaImp : uses Streams for the implementation instead of loops

To get the jar file, you can use Maven to compile the project or run a container. 
Use either Github or docker hub to get the files.

* `mvn clean package`
* `docker pull barlowza/grep`

# Quick Start
1. Maven
```
$ mvn clean package

$ java -cp target/*.jar <class_file> <regex/pattern> <directory> <out_file>
```

2. Docker
```
$ docker pull barlowza/grep

$ docker run --rm -v <directory> -v <out_file> barlowza/grep <regex/pattern> <directory> <out_file>
```

# Implementation
There are two implementations for this:
1. JavaGrepImp:
```java
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```

2. JavaGrepLambdaImp
```java
listFiles(getRootPath()).stream()
    .forEach(writeToFile(readLines(file).stream()
    .filter(containsPattern(line)).toList)
);
```

## Performance Issue
The performance issue with `JavaGrepImp` was dealing with Lists and looping through
each individual peace causing the memory to get bloated due to creating an Object for each piece.
This was solved by using Streams for the implementation to combat this issue.

# Testing
Testing was done manually by creating files for output the test for certain cases that it could run against.

# Deployment
To deploy, I used a Dockerfile to create the image of the program and uploaded it to Docker Hub
to allow an easier distribution and access to the data inorder to use the application.

# Improvement
1. Modify interfaces to use Streams and not Lists.
2. Allow a depth magnitude for searching down directories.
3. In output file, display the file name the line is from.