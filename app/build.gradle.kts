plugins {
    //application
    id("com.github.ben-manes.versions") version "0.52.0"
    id("org.sonarqube") version "6.2.0.5505"
    java
    checkstyle
    jacoco
    id("io.freefair.lombok") version "8.6"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //implementation("org.apache.commons:commons-lang3:3.17.0")
    //implementation("org.apache.commons:commons-collections4:4.4")
    //implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    //implementation("com.fasterxml.jackson.core:jackson-core:2.17.2")
    //implementation("com.fasterxml.jackson.core:jackson-annotations:2.17.2")
    //implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.17.2")
    testImplementation(platform("org.junit:junit-bom:5.12.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.platform:junit-platform-launcher")
    //testImplementation("org.assertj:assertj-core:3.26.0")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
}

//application {
//    mainClass.set("hexlet.code.App")

//}
tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)

}

//tasks.getByName("run", JavaExec::class) {
//    standardInput = System.`in`
//}

tasks.jacocoTestReport { reports { xml.required.set(true) } }

sonar {
    properties {
        property("sonar.projectKey", "Skier54_java-project-78")
        property("sonar.organization", "skier54")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}