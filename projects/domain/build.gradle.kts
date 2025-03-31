plugins {
    id("kotlin")
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.bundles.testing)
}

tasks.withType<Test> {
    useJUnitPlatform()
}