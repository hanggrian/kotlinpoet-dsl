plugins {
    `git-publish`
}

gitPublish {
    repoUri.set(RELEASE_URL)
    branch.set("gh-pages")
    contents.from(
        "src",
        "../$RELEASE_ARTIFACT/build/dokka"
    )
}

tasks.named("gitPublishCopy") {
    dependsOn(":$RELEASE_ARTIFACT:dokkaHtml")
}