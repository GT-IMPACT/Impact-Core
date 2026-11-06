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


## Совместная сборка GT и Core

Если рядом находится рабочая копия `../gt-worktree`, Gradle автоматически подключает её
как included build. Обычные команды `./gradlew build` и `./gradlew test`, а также импорт
проекта в IDEA используют актуальные исходники GT и сами собирают нужную зависимость.
После изменения настроек выполните Reload All Gradle Projects в IDEA.

Для другого расположения GT задайте путь в свойствах Gradle:

```properties
gregtechProjectDir=../path-to-gregtech
```

Gradle запускается на JDK 25, тесты — через настроенный toolchain JDK 8.
Параметр `gregtechDevJar` остаётся необязательным способом проверить конкретный готовый
артефакт вместо исходного проекта. Если исходный проект и этот параметр отсутствуют,
используется опубликованный GT; он должен содержать API текущей интеграции.

Регрессионные проверки обработки находятся в `ImpactProcessingMigrationTest`, нативных GUI —
в `ImpactProcessingGuiTest`. Последний создаёт панели всех 68 зарегистрированных контроллеров
и проверяет реальные серверные действия, слоты и сохранения. Расчёты проверяются по новому
обработчику GT; старый разгон IMPACT и тесты его формул удалены.

Для проверки запуска с RecipeModule подключён TecTech и его зависимость YAMCore.
Если рядом находится `../tectech-worktree`, он собирается из исходников вместе с GT
и Core. Для другого расположения задайте `tectechProjectDir`. Вне этого рабочего
окружения используется опубликованный dev-артефакт TecTech.

В `gradle.properties` задан один worker: RetroFuturaGradle 2.0.6 иначе может падать
с `ConcurrentModificationException` при проверке общего кеша included builds.
