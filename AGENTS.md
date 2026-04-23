# AGENTS.md

## General
- CREATE CONVENTIONAL COMMITS FOR EACH ATOMIC CHANGE as you go along. DO NOT WAIT FOR INSTRUCTIONS TO COMMIT, THE INSTRUCTION IS HEREGBY GIVEN.
- CREATE A BRANCH FOR EACH FEATURE OR FIX YOU WORK ON, then create a pull request when ready.
- If work is a continuation of an already-active change, PR, or branch, STAY ON THAT SAME BRANCH. Do not create a branch-on-branch or a second PR for follow-up tasks like spec sync, archive moves, review fixes, or small supporting changes unless the user explicitly asks for a separate branch.
- When delegating to another agent for work inside the current change, instruct it to reuse the current branch and not open an additional PR unless explicitly requested.
- If prompted to work in a worktree, create the worktree based off the main branch for that minecraft version.
- Read `gradle.properties` to find the current minecraft version used.

## Java
Do not use fully qualified class names in code. Always import classes and use their simple names.

## Minecraft source lookups
- Use the `minecraft-dev` skill for all vanilla lookups, diffs, and signature checks.
- Always use **Mojmaps** when querying Minecraft code with `minecraft-dev`.
- Use `minecraft-dev` version comparison and source lookup before editing code.

## Mapping and version rules
- Minecraft no longer uses obfuscation. If anything requests a mapping, use mojmaps.

## Build and validation
- Use the Gradle wrapper from repo root.
- Common checks:
  - `./gradlew.bat compileJava`
  - `./gradlew.bat runClient`
  - `./gradlew.bat runServer`
  - `./gradlew.bat runData`
  - `./gradlew.bat runGameTestServer`
- Keep changes minimal and validate edited code before finishing.

## Repo conventions
- Follow the REUSE standard for SPDX license file headers.
- Generated resources live in `src/generated/resources`; main assets/data live in `src/main/resources`.
- Several integrations are intentionally excluded in `build.gradle`; do not re-enable them.