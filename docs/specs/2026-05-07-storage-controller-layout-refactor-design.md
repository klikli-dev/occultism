<!--
SPDX-FileCopyrightText: 2026 klikli-dev

SPDX-License-Identifier: MIT
-->

# Storage controller GUI/menu layout refactor design

## Goal

Refactor the storage controller GUI family to use CDG's layout-driven pattern on both the screen and menu side, while preserving feature parity and layout parity.

This refactor covers:
- `StorageControllerGui`
- `StorageRemoteGui`
- `StableWormholeGui`
- their shared screen base
- their shared menu/container base

This refactor does **not** intentionally change visible layout, packet behavior, or user-facing features. It does intentionally change internal architecture, naming, composition boundaries, and shared layout ownership.

## Required outcomes

- Screen and menu both use shared CDG-style layout data rather than ad-hoc coordinate math.
- The current god-object structure is split into smaller composed parts.
- The storage inventory view and machine/autocrafting view become proper composed widgets/components.
- Networking remains mostly stable and is routed through a dedicated action/controller layer instead of direct widget calls.
- Current row-by-row scrolling behavior is preserved, but renamed to reflect what it actually does.
- Internal storage GUI/container APIs are refactorable.
- Final validation must explicitly note that manual in-game verification will be performed by the user.

## Non-goals

- No intentional visual redesign.
- No intentional gameplay changes.
- No conversion of row scrolling into true paging.
- No packet redesign unless strictly required by implementation constraints.
- No generic GUI framework for unrelated screens.

## Current pain points

### 1. Screen god object

`StorageControllerGuiBase` currently owns layout math, root widget creation, search state, sorting, pagination/row scrolling, JEI sync, tooltip generation, rendering, input handling, button setup, mode switching, networking calls, and manual child object orchestration.

This makes the class difficult to understand, difficult to test, and dangerous to change incrementally.

### 2. Manual layout masquerading as structure

The current inner `Layout` object improves naming somewhat, but it is still manual coordinate math and not integrated with CDG's menu/screen layout model. Layout is still screen-owned and not a shared source of truth between client and common code.

### 3. Client/common layout coupling

Container code currently depends on client GUI constants for slot positioning. This is the wrong dependency direction and blocks a clean menu/screen refactor.

### 4. Full-screen rebuild as state update

Large parts of behavior currently mutate state by calling `init()` again. This makes focus management, search state, and UI behavior fragile and forces lifecycle workarounds.

### 5. Rendering/input/data logic are fused

Search filtering, machine/item display, tooltips, packet dispatch, and click handling all live in the same screen class instead of being organized into view components and controller logic.

## Chosen approach

Use a **shared shell + adapters + composed widgets** architecture.

That means:
- one shared layout-driven screen shell
- one shared layout-driven menu/container shell
- small backend adapters for the storage controller / remote / wormhole differences
- dedicated composed widgets for major screen regions
- a dedicated action/controller layer for UI intents and packet dispatch
- shared layout data for both screen and menu slot geometry

This approach is preferred because it removes the god-object problem at the architectural level instead of only hiding it behind helper methods.

## Target package and class layout

### Client side

```text
com.klikli_dev.occultism.client.gui.storage
  AbstractStorageTerminalScreen<T extends StorageControllerContainerBase>
  StorageControllerGui
  StorageRemoteGui
  StableWormholeGui
  StorageScreenState

com.klikli_dev.occultism.client.gui.storage.adapter
  StorageScreenBackend
  ControllerScreenBackend
  RemoteScreenBackend
  StableWormholeScreenBackend

com.klikli_dev.occultism.client.gui.storage.layout
  StorageTerminalLayouts
  StorageTerminalLayoutVariant

com.klikli_dev.occultism.client.gui.storage.component
  StorageTopBarWidget
  StorageModeTabsWidget
  StorageStatsWidget
  StorageCraftingSidebarWidget
  StorageItemGridWidget
  StorageMachineGridWidget
  ScaledSearchFieldWidget

com.klikli_dev.occultism.client.gui.storage.logic
  StorageScreenActions
  StorageDisplayQuery
```

### Common/container side

```text
com.klikli_dev.occultism.common.container.storage
  StorageControllerContainerBase
  StorageControllerContainer
  StorageRemoteContainer
  StableWormholeContainer

com.klikli_dev.occultism.common.container.storage.layout
  StorageMenuLayout
  StorageMenuLayouts
  StorageMenuVariant

com.klikli_dev.occultism.common.container.storage.logic
  StorageCraftingLogic
  StorageIngredientLookup
```

## Core design

### 1. Shared layout ownership

Slot geometry and named regions must move into shared layout types rather than being split between client constants and container math.

Two layout layers will exist:

- `StorageMenuLayout` / `StorageMenuLayouts`
  - owns menu slot positions and menu-relevant shared geometry
  - used by container setup/binding

- `StorageTerminalLayouts`
  - owns screen-side CDG `LayoutSpec`
  - used by the screen shell and widgets

These two layers should be intentionally aligned and derived from the same conceptual geometry rather than maintained independently through copy-pasted constants.

The implementation may use a shared metrics record or layout fragment if needed, but the design goal is a single source of truth for structural geometry.

### 2. Thin screen shell

`AbstractStorageTerminalScreen` becomes the CDG host and orchestration shell.

It should own:
- `GuiRootWidget`
- `ScreenLayoutController`
- current screen state
- the chosen backend adapter
- composed widgets/components
- shared action/query services

It should **not** own:
- raw search/sort/filter implementation details
- manual slot entry rendering loops
- giant tooltip switchboards
- direct packet code in button callbacks
- ad-hoc coordinate calculations

### 3. Thin menu shell

`StorageControllerContainerBase` should stop being the menu equivalent of a god object.

It should remain responsible for container lifecycle and shared container behavior, but menu slot layout and distinct logic areas should be extracted.

The important split is:
- layout/binding
- crafting logic
- ingredient lookup logic
- backend-specific persistence/state differences

### 4. Backend adapters

Differences between controller/remote/wormhole variants should be represented by small backend adapters, not large subclass logic trees.

`StorageScreenBackend` is responsible only for per-variant differences such as:
- GUI validity checks
- origin position used for actions
- sort state accessors

The corresponding container-side variant differences remain in the concrete containers, but only where persistence/source differences actually matter.

## Screen components

### `StorageTopBarWidget`

Responsible for:
- title
- search field
- clear search button
- sort type button
- sort direction button
- JEI sync toggle

It captures UI intent and forwards actions through `StorageScreenActions`.

### `StorageModeTabsWidget`

Responsible for:
- inventory mode tab
- autocrafting mode tab
- current mode indicator
- mode switch intents

### `StorageStatsWidget`

Responsible for:
- storage usage text
- type usage text
- associated tooltip hitboxes/content

### `StorageCraftingSidebarWidget`

Responsible for:
- crafting decorations
- order slot decoration
- clear crafting button
- order slot tooltip/overlay behavior

### `StorageItemGridWidget`

Responsible for:
- rendering visible item entries
- row-scrolling window presentation
- item hover and item tooltip integration
- item extraction/insertion click intents

This widget owns the inventory-view presentation instead of the screen manually maintaining and iterating `ItemSlotWidget` lists.

### `StorageMachineGridWidget`

Responsible for:
- rendering visible machine entries
- row-scrolling window presentation in autocrafting mode
- machine hover tooltip integration
- machine click intents for order creation and shift-highlight behavior

This widget owns the machine-view presentation instead of the screen manually maintaining and iterating `MachineSlotWidget` lists.

## Controller / action layer

### `StorageScreenActions`

This is the behavior gateway used by widgets.

It owns UI-triggered behavior such as:
- request stacks
- clear crafting matrix
- clear search side effects
- take item
- insert carried item
- request machine order
- change sort type/direction
- toggle JEI sync
- switch screen mode

Widgets should call this layer instead of using `Networking` directly.

This keeps packet usage centralized and allows the UI to be composed without smuggling backend behavior into render components.

### Packet handling policy

Existing packets should remain stable unless a change is clearly necessary. The main architectural change is routing, not protocol redesign.

## Query and state model

### `StorageScreenState`

This holds UI state only:
- current mode
- current search text
- row-scrolling state
- focus/interaction flags if still required

This must not accumulate rendering or networking responsibilities.

### `StorageDisplayQuery`

This isolates display-oriented data logic:
- filter item list by query
- filter machine list by query
- sort item list
- sort machine list
- compute visible row window bounds

This replaces display/query logic currently embedded across the screen base.

## Row scrolling terminology

Current behavior must remain the same: each scroll step advances by one row.

The current code uses page terminology for row scrolling. That should be renamed.

Preferred naming direction:
- `currentPage` -> `firstVisibleRow`
- `previousPage` -> `previousFirstVisibleRow`
- `totalPages` -> `maxFirstVisibleRow`

The implementation may choose slightly different names, but they must describe row-window scrolling honestly.

## Menu/container decomposition

### `StorageMenuLayout` and `StorageMenuLayouts`

These own container-side structural positioning:
- player inventory slots
- hotbar slots
- crafting grid slots
- output slot
- order slot

Container setup should bind through these layouts instead of depending on client GUI constants.

### `StorageCraftingLogic`

Extract from the current container base:
- recipe lookup
- crafting matrix/result sync
- shift craft handling

### `StorageIngredientLookup`

Extract ingredient-related logic such as:
- presence checks
- missing ingredient queries

These extractions are meant to reduce the container base to a coordinator rather than a logic dump.

## Interfaces and existing APIs

Current mod-internal APIs in this area are considered refactorable.

That means types such as:
- `IStorageControllerGui`
- `IStorageControllerGuiContainer`
- existing helper/interfaces around this family

may be reshaped, removed, or replaced if that improves the architecture. However, unnecessary churn should still be avoided.

## Migration plan

### Phase 1 — extract shared layout ownership

Create shared menu/layout types and remove container dependence on client GUI constants.

This is the first step because it removes the worst architectural coupling and provides the structural base for the rest of the refactor.

### Phase 2 — add new shell types alongside old code

Introduce:
- `AbstractStorageTerminalScreen`
- `StorageScreenBackend`
- `StorageScreenState`
- `StorageScreenActions`
- `StorageDisplayQuery`

At this stage, the old implementation can still exist while the new structure is assembled.

### Phase 3 — build CDG screen layout

Create `StorageTerminalLayouts` and migrate the outer screen structure to named CDG nodes.

This covers:
- top bar
- main panel
- sidebar
- tabs
- player inventory frame
- slot background regions

### Phase 4 — extract composed widgets

Extract in this order:
1. top bar
2. tabs
3. stats
4. crafting sidebar
5. item grid
6. machine grid

This sequencing minimizes risk while making the architecture readable early.

### Phase 5 — rename row-scrolling model

Replace page terminology with row-window terminology while preserving behavior.

### Phase 6 — decompose menu logic

Move container logic into:
- shared layout/binding types
- crafting logic helper
- ingredient lookup helper

### Phase 7 — reduce full-screen rebuild churn

Stop relying on `init()` for every state mutation where possible.

Rebuild/reinitialize only when layout structure actually changes. Smaller state updates should stay within widgets or controller-owned state.

### Phase 8 — remove obsolete code and verify

Delete old scaffolding once the new path is stable.

## Deletions and cleanup targets

### Screen side

Expected removals or major reductions:
- current `StorageControllerGuiBase` god-object structure
- inner `Layout`
- giant coordinate constant block
- `initRootWidgets()`
- most of `initButtons()`
- monolithic tooltip/router methods where replaced by composed widgets
- manual item/machine slot list lifecycle
- inner `ScaledEditBox` once externalized

### Widget/helper side

`ItemSlotWidget` and `MachineSlotWidget` are candidates for deletion or demotion if their responsibilities are fully absorbed into the new grid widgets.

### Menu side

Expected removals or reductions:
- container dependence on screen constants
- large logic sections inside `StorageControllerContainerBase` once extracted
- obsolete slot layout helper patterns superseded by shared layout data

## YAGNI constraints

To avoid the refactor ballooning:

1. Do not build a generic terminal framework for unrelated screens.
2. Do not introduce an event bus or reactive UI system.
3. Do not redesign packets unless necessary.
4. Do not over-generalize item and machine grids into deep inheritance hierarchies.
5. Do not over-engineer layout beyond shared metrics + CDG layout specs.
6. Do not redesign JEI integration beyond isolating it behind top-bar/actions logic.
7. Do not convert row scrolling into real paging.
8. Do not collapse all backend persistence differences into one giant abstraction.

## Validation plan

### Automated validation

- `./gradlew.bat compileJava`

Additional automated checks may be run during implementation if needed, but compile success is the minimum required automated gate.

### Manual validation

Manual in-game verification will be performed by the user at the end.

The implementation plan must explicitly preserve and verify:
- `StorageControllerGui`
- `StorageRemoteGui`
- `StableWormholeGui`
- inventory mode
- autocrafting mode
- row-by-row scrolling
- search prefixes (`@`, `#`, `$`)
- JEI sync behavior
- item extraction/insertion behavior
- order slot behavior and auto mode switching
- machine order creation and shift-highlighting behavior
- remote-specific locked slot behavior

## Recommended implementation mindset

Prefer structure-preserving refactors first, then behavior isolation, then cleanup. Keep the visual and gameplay behavior stable while aggressively improving ownership boundaries and layout composition.
