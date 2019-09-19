plugins {
    `git-publish`
}

gitPublish {
    repoUri = RELEASE_WEBSITE
    branch = "gh-pages"
    contents.from(
        "src",
        "../$RELEASE_ARTIFACT/build/docs"
    )
}

tasks {
    "gitPublishCopy" {
        dependsOn(":$RELEASE_ARTIFACT:dokka")
    }
}