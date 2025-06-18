plugins {
    eclipse

    `java-library`

    `maven-publish`

    // Used to generate wsdl -> java bindings.
    id("com.github.bjornvester.wsdl2java") version "2.0.2"
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    withSourcesJar()
}

sourceSets {
    main {
        resources {
            srcDir("../../../../../specifications/vsphere/wsdl/${project.name}")
        }
    }
}

wsdl2java {
    cxfVersion = "4.0.5"

    // Use the definitions from $ROOT/specifications
    wsdlDir = layout.projectDirectory.file("../../../../../specifications/vsphere/wsdl/${project.name}").asFile

    // The STS wsdl is invalid - there are operations with non-unique body parts,
    // which is a violation of the spec... as a workaround disable the validations,
    // otherwise the bindings won't be created.
    options = listOf("-validate=none")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            pom {
                groupId = "com.vmware.vim"
                artifactId = "ssoclient"
                version = "0.0.1"
            }

            from(components["java"])
        }
    }

    repositories {
        maven {
            url = uri("https://your-repository.com/some/repository")
        }
    }
}
