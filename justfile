install CLEAN="false":
    {{ if CLEAN == "false" { "just _update-gradlew" } else { "" } }}
    uv sync {{ if CLEAN == "true" { "--locked" } else { "" } }}
    pnpm install {{ if CLEAN == "true" { "--frozen-lockfile" } else { "" } }}

lint-gradle:
    just _gradle-{{ os() }} ktlintCheck

lint-python:
    uv run poe lint

lint-node:
    pnpm lint

[parallel]
lint: lint-gradle lint-python lint-node

# skip lint-node with network calls
[parallel]
minimal-lint: lint-gradle lint-python

format:
    go fmt ./...

test:
    just _gradle-{{ os() }} test

coverage:
    just _gradle-{{ os() }} koverXmlReport

documentation:
    just _gradle-{{ os() }} dokkaGenerateHtml

_gradle-linux *args:
    ./gradlew {{ args }}

_gradle-macos *args:
    ./gradlew {{ args }}

_gradle-windows *args:
    gradlew.bat {{ args }}

_update-gradlew:
    just _gradle-{{ os() }} wrapper
