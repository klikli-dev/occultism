<!--
SPDX-FileCopyrightText: 2026 klikli-dev

SPDX-License-Identifier: MIT
-->

# Storage controller GUI/menu layout refactor implementation plan

This plan follows `docs/specs/2026-05-07-storage-controller-layout-refactor-design.md` and defines the execution order for the current branch.

## Goals

- Fix current layout correctness issues before larger refactors.
- Keep screen and menu layout changes incremental and easy to validate.
- Preserve existing behavior while improving layout ownership.

## Phase 1 — correctness fixes

### 1. Make the client layout variant-aware

Problem:
- `StorageRemoteContainer` uses `StorageMenuVariant.REMOTE`.
- `StorageTerminalLayouts` currently hardcodes the standard player inventory placement for all screens.

Implementation:
- Change the screen layout API from `create(int visibleRows)` to `create(StorageMenuVariant variant, int visibleRows)`.
- Feed the active variant from the container/menu into the screen layout.
- Use the shared menu layout data for the player inventory anchor so the remote screen background and the remote menu slots stay aligned.

Expected result:
- `StorageRemoteGui` uses the remote inventory placement on the screen side.
- `StorageControllerGui` and `StableWormholeGui` stay visually unchanged.

### 2. Fix the order input slot offset

Problem:
- On all storage terminal screens, the order input slot is rendered one pixel too far left and one pixel too high.

Implementation:
- Move only the actual menu slot position by `+1 x` and `+1 y`.
- Do not move:
  - the order slot node in the screen layout
  - the order slot background
  - the larger background behind it

Expected result:
- Item rendering and interaction for the order input slot shift into the existing background framing without moving the background art.

### 3. Validation

- Run `./gradlew.bat compileJava`.
- Manually verify later in-game:
  - `StorageControllerGui`
  - `StorageRemoteGui`
  - `StableWormholeGui`
  - remote player inventory alignment
  - order input slot alignment

## Phase 2 — consolidate layout ownership

- Introduce a shared storage terminal layout model for common geometry.
- Reduce duplicated layout constants between:
  - `StorageControllerGuiBase`
  - `StorageTerminalLayouts`
  - `StorageMenuLayouts`

## Phase 3 — extract layout sections

- Extract section owners for:
  - top bar
  - mode tabs
  - crafting/order area
  - storage info labels
- Each section should own widgets, backgrounds/sprites, and tooltip logic together.

## Phase 4 — slim base classes

- Reduce `StorageControllerGuiBase` to orchestration/state duties.
- Split `StorageControllerContainerBase` layout/setup concerns from crafting and ingredient lookup logic.

## Commit plan

1. `docs: add storage layout refactor implementation plan`
2. `fix: align storage remote screen layout`
3. `fix: nudge storage order input slot`

If the two code fixes land together cleanly and are validated together, they may be combined into one implementation commit.
