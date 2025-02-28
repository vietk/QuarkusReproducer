# resolver-issue

This repository is a reproducer for an unexpected behavior of the Quarkus [RunnerClassLoader](https://quarkus.io/guides/class-loading-reference) 

## Problem statement

I want to load classes as resource from the class loader and
For that I am reusing `org.springframework.core.io.support.PathMatchingResourcePatternResolver`.
Unfortunately when using the one of the production classloader
of Quarkus, the RunnerClassLoader, this latter do not manage
to load the resource effectivelly, as opposed to the System classloader or the dev mode QuarkusClassLoader.

When loading a path resource, the classloader is stripping the last `\` from the resource

When loading the path `org/acme/` with RunnerClassLoader :
```shell
URL [jar:file:/User/Projects/Support/Misc/Quarkus/resolver-issue/target/quarkus-app/app/resolver-issue-1.0.0-SNAPSHOT.jar!/org/acme]
```

When loading the same path with QuarkusClassLoader
```shell
URL [file:/User/Projects/Support/Misc/Quarkus/resolver-issue/target/classes/org/acme/]
```

This is the same kind of issue that has been reported and fixed for the [QuarkusClassLoader](https://github.com/quarkusio/quarkus/issues/10943)  

## How to use

There are 3 tests that test the same thing: try to extract a class from a package and see if the resource is returned by the `org.springframework.core.io.support.PathMatchingResourcePatternResolver`

- JUnit test is it's working (System classloader)
- QuarkusTest is working (QuarkusClassLoader)
- quarkus-runner-app.jar is failing (RunnerClassLoader) 

### To reproduce

```shell
mvn verify
launch `./src/test/shell/test.sh`
```
