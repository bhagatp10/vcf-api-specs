plugins {
    eclipse

    `java-library`

    `maven-publish`

    // Used to generate wsdl -> java bindings.
    id("com.github.bjornvester.wsdl2java") version "2.0.2"
}

repositories {
    mavenCentral()
    // TODO: used to reference locally published artifacts (vim25) which are not yet public
    mavenLocal()
}

val vimEpisodeCfg: Configuration by configurations.creating
dependencies {
    vimEpisodeCfg(project(mapOf("path" to ":vsphere:wsdl:vim25", "configuration" to "vim.episode")))
    api(project(":vsphere:wsdl:vim25"))
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

    bindingFile = vimEpisodeCfg.singleFile

    // Use the definitions from $ROOT/specifications
    wsdlDir = layout.projectDirectory.file("../../../../../specifications/vsphere/wsdl/${project.name}").asFile

    packageName = "com.vmware.pbm"
}

tasks.named("wsdl2java") {
    dependsOn(vimEpisodeCfg)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            pom {
                groupId = "com.vmware.vim"
                artifactId = "pbm"
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
