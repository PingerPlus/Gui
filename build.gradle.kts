plugins {
    id("java")
}

group = "io.pnger"
version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")
    implementation("dev.oop778.shelftor:shelftor-core:0.2")
}