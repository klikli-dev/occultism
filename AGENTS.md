# AGENTS.md

## General
- MAKE CONVENTIONAL COMMITS FOR EVERY ATOMIC CHANGE.
- For fixes and features always create a branch, and open a PR when ready. If given an issue to work on, link the issue in the PR description.
- Read `build.gradle` and `gradle.properties`, and `src/main/resources/META-INF/neoforge.mods.toml` to find the current minecraft version used.
- When updating, **never** change `gradle.properties`, a human will update it before you start work.
- Do not guess vanilla API changes; verify them.

## Minecraft source lookups
- Use the `minecraft-dev` MCP tools for all vanilla lookups, diffs, and signature checks.
- Always use **mojmaps** when querying Minecraft code with `minecraft-dev`.

## Mapping and version rules
- Minecraft source code is now distributed without obfuscation, so there are no mapping names. Use the official Mojmap names for code, access transformers, and documentation.
- After `1.21.11`, Minecraft also moved to the new versioning scheme beginning with **`26.1`**. 

## Build and validation
- Use the Gradle wrapper from repo root.
- Common checks:
  - `./gradlew.bat compileJava`
  - `./gradlew.bat runClient`
  - `./gradlew.bat runServer`
  - `./gradlew.bat runClientData`
- Keep changes minimal and validate edited code before finishing.

## Repo conventions
- Follow the REUSE standard for SPDX license file headers.
- Generated resources live in `src/generated/resources`; main assets/data live in `src/main/resources`.
- Several integrations are intentionally excluded in `build.gradle`; do not re-enable them.

## GIT Workflow
- Do not create branches, use only the current checked out branch for all work.
- Use conventional commit messages, and keep commits focused on a single change or fix.
- Commit early and commit often.
