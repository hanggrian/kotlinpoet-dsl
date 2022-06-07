plugins {
    minimal
    `git-publish`
}

minimal {
    authorName.set("Hendra Anggrian")
    authorUrl.set("https://github.com/hendraanggrian")
    projectName.set(RELEASE_ARTIFACT)
    projectDescription.set(RELEASE_DESCRIPTION)
    projectUrl.set(RELEASE_GITHUB)
    headerButtons {
        button("View", "Documentation", "dokka")
    }
    pages {
        index(rootDir.resolve("docs/README.md"))
    }
}

gitPublish {
    repoUri.set("git@github.com:hendraanggrian/$RELEASE_ARTIFACT.git")
    branch.set("gh-pages")
    contents.from("$buildDir/minimal", "$rootDir/$RELEASE_ARTIFACT/build/dokka")
}

tasks {
    register("clean") {
        delete(buildDir)
    }
    gitPublishCopy {
        dependsOn(":$RELEASE_ARTIFACT:dokkaHtml")
    }
}