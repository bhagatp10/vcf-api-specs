rootProject.name = "vcf-api-specs-java"

// Add vSphere WSDL module builds
listOf("vim25", "vslm", "ssoclient", "pbm", "sms", "eam").forEach {
    module -> include("vsphere:wsdl:${module}")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("jaxws-api", "jakarta.xml.ws:jakarta.xml.ws-api:2.3.3")
            library("bind-api", "jakarta.xml.bind:jakarta.xml.bind-api:2.3.3")
            library("jaxws-rt", "com.sun.xml.ws:jaxws-rt:3.0.2")
            library("jaxb-runtime", "org.glassfish.jaxb:jaxb-runtime:2.3.9")
        }
    }
}

