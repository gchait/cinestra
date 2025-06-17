plugins {
	java
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
}

group = "at.guyc"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(libs.spring.boot.starter.data.jpa)
	implementation(libs.spring.boot.starter.web)
	implementation(libs.htmx.spring.boot)
	compileOnly(libs.lombok)
	developmentOnly(libs.spring.boot.devtools)
	annotationProcessor(libs.spring.boot.configuration.processor)
	annotationProcessor(libs.lombok)
	testImplementation(libs.spring.boot.starter.test)
	testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
