# Generating vSphere SOAP Bindings for Java

## Description

Each sub-folder contains a Gradle 8.7-based project which can be used to generate client-side bindings. Use the `gradlew` wrapper to execute the commands below. Replace `sub-project` with the name of one of the root-projects (e.g. vim25).

_Note: The source and target compatibility of the bindings are set to Java 8, but the build process requires JDK 11._

## Building the bindings

By convention, the `build` task of each project uses the API definitions in the "specs" folder to emit the bindings.

```shell
../gradlew :sub-project:build
```
For example to build the vim25.jar file use the following command:
```shell
../grdlew :vsphere:wsdl:vim25:build
```

## Publishing the bindings
Gradle's `publish` plugin is used to prepare a Maven publication.

### Publishing the bindings to the local .m2 repository
To package the generated bindings as a Maven artifact and to copy them to the local `.m2` folder:

```shell
../gradlew :sub-project:build
```

### Publishing the bindings to a remote Maven repository
To package the generated bindings as a Maven artifact and to upload them to a remote Maven repository, edit `build.gradle` to specify its address and:

```shell
../gradlew :sub-project:publish
```

### More information

To customize the bindings and for additional information reading process of emitting, refer to the official documentation of [Apache CXF](https://cxf.apache.org/docs/wsdl-to-java.html) and [wsdl2java-gradle-plugin](https://github.com/bjornvester/wsdl2java-gradle-plugin).

To customize the Maven publication (e.g. to include/exclude sources, to sign the artifacts, etc.), refer to the official documentation of the [The Maven Publish Plugin](https://docs.gradle.org/8.7/userguide/publishing_maven.html).

If you wish to use your own PGP key to sign the artifacts, refer to the ofiical documentation of the [The Signing Plugin](https://docs.gradle.org/8.7/userguide/signing_maven.html).
