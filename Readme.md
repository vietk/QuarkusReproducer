# Reproducer project for quarkus native profile builds

# Reproduce

``` bash
mvn clean install -Pnative
```

See the error reported by native-image 

``` bash
Error: Invalid or corrupt jarfile /.../Reproducer/application/target/application-1.0-SNAPSHOT-runner.jar
```

# Reason

The quarkus-maven-plugin seems to have a problem with the following dependency :

``` xml
<dependency>
   <groupId>maven.dependency</groupId>
   <artifactId>allModules</artifactId>
   <version>1.0-SNAPSHOT</version>
   <type>pom</type>
</dependency>
```

It supposed by, transitive dependencies, resolve the own dependencies of allModules.

So it resolves well the dependencies in the lib folder but adds a suspicious pom file

``` bash

maven.dependency.moduleA-1.0-SNAPSHOT.jar
maven.dependency.moduleB-1.0-SNAPSHOT.jar
maven.dependency.pom.xml

```

I suppose that GraalVM tries to read all dependencies, for ahead of time compilation, in the lib folder and fails with the pom file found there.

Since I did not face issues with HotSpot, it probably acting differently ...
