val developerId: String by project
val developerName: String by project
val developerUrl: String by project
val releaseArtifact: String by project
val releaseDescription: String by project
val releaseUrl: String by project

plugins {
    alias(libs.plugins.dokka)
    alias(libs.plugins.pages)
    alias(libs.plugins.git.publish)
}

pages {
    resources.from("src", layout.buildDirectory.dir("dokka"))
    styles.add("styles/prism-tomorrow.css")
    scripts.addAll(
        "https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/prism.min.js",
        "https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/components/prism-gradle.min.js",
        "https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/components/prism-kotlin.min.js",
    )
    languageAliases.put("kt", "kotlin")
    minimal {
        authorName = developerName
        authorUrl = developerUrl
        projectName = releaseArtifact
        projectDescription = releaseDescription
        projectUrl = releaseUrl
        button("View\nDocumentation", "dokka")
    }
}

dokka.dokkaPublications.html {
    outputDirectory.set(layout.buildDirectory.dir("dokka/dokka/"))
}

dependencies {
    dokka(project(":$releaseArtifact"))
}

gitPublish {
    repoUri.set("git@github.com:$developerId/$releaseArtifact.git")
    branch.set("gh-pages")
    contents.from(pages.outputDirectory)
}

tasks.deployResources {
    dependsOn(tasks.dokkaGeneratePublicationHtml)
}
