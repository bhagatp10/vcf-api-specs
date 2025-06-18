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
            srcDir("../../../../../specifications/vsphere/wsdl/vim")
        }
    }
}

// Create a new configuration whose output is an episode file.
// The bindings for other services may require it.
configurations {
    create("vim.episode")

    artifacts.add("vim.episode", layout.buildDirectory.file("vim.episode")) {
        type = "episode"
        builtBy("wsdl2java")
    }
}
val vimEpisodeFile = configurations["vim.episode"].artifacts.files.singleFile

wsdl2java {
    cxfVersion = "4.0.5"

    packageName = "com.vmware.vim25"

    // Use the definitions from $ROOT/specifications
    wsdlDir = layout.projectDirectory.file("../../../../../specifications/vsphere/wsdl/vim").asFile

    includesWithOptions.set(
        mapOf(
           "vsphere.wsdl" to listOf(
                "-p", "urn:vim25=com.vmware.vim25",
                "-p", "urn:vsan=com.vmware.vsan.sdk",
                "-xjc-episode", "-xjc${vimEpisodeFile.absolutePath}"
            )
        )
    )

}

tasks.named("wsdl2java") {
    doLast {
        // Unfortunately, we'll have to patch the generated episode by hand -
        // far from nice thing to do, but a necessary step for bindings whose
        // emitting depends on this episode (e.g. vslm).
        val vimEpisodeContent = vimEpisodeFile.readText().replace(
            "bindings scd", "bindings if-exists=\"true\" scd")
        vimEpisodeFile.writeText(vimEpisodeContent)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            pom {
                groupId = "com.vmware.vim"
                artifactId = "vim25"
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
