plugins {
  java
  id("org.springframework.boot") version "3.4.5"
  id("io.spring.dependency-management") version "1.1.7"
  id("org.hibernate.orm") version "6.6.13.Final"
  id("org.graalvm.buildtools.native") version "0.10.6"
  id("org.asciidoctor.jvm.convert") version "3.3.2"
  id("com.diffplug.spotless") version "7.0.3"
}

group = "com.example"

version = "0.0.1-SNAPSHOT"

java { toolchain { languageVersion = JavaLanguageVersion.of(21) } }

spotless {
  ratchetFrom("origin/main")
  encoding("UTF-8")
  format("misc") {
    target("**/*.gradle", "**/*.md", "**/*.gitattributes", "**/*.gitignore")
    trimTrailingWhitespace()
    leadingSpacesToTabs()
    endWithNewline()
  }
  java {
    target("**/*.java")
    targetExclude("**/build/**/*.java")
    removeUnusedImports()
    importOrder()
    cleanthat()
    googleJavaFormat()
    formatAnnotations()
  }

  kotlinGradle {
    target("**/*.gradle.kts")
    targetExclude("**/build/**/*.gradle.kts")
    ktfmt().googleStyle()
  }

  yaml {
    target("**/*.yml")
    jackson()
  }

  protobuf { buf() }
}

configurations { compileOnly { extendsFrom(configurations.annotationProcessor.get()) } }

repositories { mavenCentral() }

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-batch")
  implementation("org.springframework.boot:spring-boot-starter-cache")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-mail")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("io.micrometer:micrometer-tracing-bridge-brave")
  implementation("io.zipkin.reporter2:zipkin-reporter-brave")
  implementation("org.liquibase:liquibase-core")
  compileOnly("org.projectlombok:lombok")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  developmentOnly("org.springframework.boot:spring-boot-docker-compose")
  runtimeOnly("io.micrometer:micrometer-registry-otlp")
  runtimeOnly("io.micrometer:micrometer-registry-prometheus")
  runtimeOnly("org.postgresql:postgresql")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  annotationProcessor("org.projectlombok:lombok")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.boot:spring-boot-testcontainers")
  testImplementation("org.springframework.batch:spring-batch-test")
  testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
  testImplementation("org.testcontainers:junit-jupiter")
  testImplementation("org.testcontainers:postgresql")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

hibernate { enhancement { enableAssociationManagement = true } }

tasks.withType<Test> { useJUnitPlatform() }

tasks.test { outputs.dir(project.extra["snippetsDir"]!!) }

tasks.asciidoctor {
  inputs.dir(project.extra["snippetsDir"]!!)
  dependsOn(tasks.test)
}
