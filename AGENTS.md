# AGENTS.md

## General
- CREATE CONVENTIONAL COMMITS FOR EACH ATOMIC CHANGE as you go along. DO NOT WAIT FOR INSTRUCTIONS TO COMMIT, THE INSTRUCTION IS HEREGBY GIVEN.
- When delegating to another agent for work inside the current change, instruct it to reuse the current branch and not open an additional PR unless explicitly requested.
- If prompted to work in a worktree, create the worktree based off the main branch for that minecraft version.

## Java
Do not use fully qualified class names in code. Always import classes and use their simple names.

## Minecraft source lookups
- EXCLUSIVELY use the `minecraft-dev` skill for all minecraft, mod and modloader (neoforge) source code lookups, diffs, and signature checks.
- Always use `mojmaps` mapping when querying code with `minecraft-dev`.
- Read `gradle.properties` to find the current minecraft version (variable name `minecraft_version`) and NeoForge version (variable name `neo_version`) used.

## Mapping and version rules
- Minecraft no longer uses obfuscation. If anything requests a mapping, use mojmaps.

## Build and validation
- Use the Gradle wrapper from repo root.
- Query the gradle tasks and use them to build, run datagen, run client and run server.

## Repo conventions
- Follow the REUSE standard for SPDX license file headers.
- Generated resources live in `src/generated/resources`; main assets/data live in `src/main/resources`.
- Never manually create generated resources, always run the appropriate runData gradle task.
- Several integrations are intentionally excluded in `build.gradle`; do not re-enable them.