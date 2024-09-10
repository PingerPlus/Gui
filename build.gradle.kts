plugins {
    id("java")
    id("maven-publish")
}

group = "io.pnger"
version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")
    implementation("net.kyori:adventure-text-serializer-legacy:4.17.0")
    implementation("org.spongepowered:configurate-yaml:4.0.0")
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    repositories {
        mavenLocal()
    }

    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}