# IMPACT CoreMod

[![Latest Release](https://img.shields.io/github/v/tag/GT-IMPACT/Impact-Core?label=Latest)](https://github.com/GT-IMPACT/Impact-Core/releases/latest)

## Сборка

Для запуска Gradle 9.7.1 используйте JDK 25 (`JAVA_HOME`). Также установите JDK 8
для задач Minecraft 1.7.10.

```shell
git submodule update --init --recursive
./gradlew build
```

Настройки мода и публикации находятся в `impactMod` в `build.gradle.kts`.
Maven использует `IMPACT_MAVEN_USER` и `IMPACT_MAVEN_PASSWORD`.
