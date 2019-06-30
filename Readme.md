# Reproducer project for managed dependencies that overrides direct dependencies


# Introduction

The module `application` has a direct dependencies on guava

``` xml
    <dependency>
      <groupId>com.google.guava</groupId>
      <artifactId>guava</artifactId>
      <version>20.0</version>
    </dependency>
```

I am expecting my application to have the expected version of guava but
it's not the case.

# Reproduce

Either do the following : 

``` bash
$> mvn clean install
$> cd application;java -jar target/application-1.0-SNAPSHOT-runner.jar &
$> curl http://localhost:8080/test/service
... 
java.lang.NoSuchMethodError: com.google.common.collect.FluentIterable.of([Ljava/lang/Object;)Lcom/google/common/collect/FluentIterable
...
```

``` bash
$> mvn clean install -Pnative
Discovered unresolved method during parsing: com.google.common.collect.FluentIterable.of(java.lang.Object[]).
```

# Reason

Quarkus Bootstrap resolver is somehow overriding the direct dependencies of the 
`application` module with another version of guava.
It's probably come from the quarkus dependency management we have to do 
in the pom.xml

``` xml
  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>io.quarkus</groupId>
        <artifactId>quarkus-bom</artifactId>
        <version>${quarkus.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>
```

Then the target lib folder contains a version of guava that is not what I am expecting
because I overrided it in the application's pom.xml

```bash 
$> ls application/target/lib
...
com.google.guava.failureaccess-1.0.1.jar
com.google.guava.guava-27.0.1-jre.jar
com.google.guava.listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar
...
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
