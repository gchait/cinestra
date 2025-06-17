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
	all {
		exclude(group = "org.apache.logging.log4j", module = "log4j-slf4j2-impl")
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)

    implementation(libs.htmx.spring.boot)
    implementation(libs.sqlite.jdbc)
	implementation(libs.hibernate.community.dialects)

    implementation(libs.spark.core)
	implementation(libs.spark.sql)
	implementation(libs.spark.mllib)

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
