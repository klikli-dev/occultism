# Ritual Recipes

Ritual recipes are currently only *halfway* configurable, if "static" properties are modified this will only affect the
information displayed in JEI, but not the actual crafting recipe.

Fully configurable recipes are something I'd like to do, but due to the complex options this is almost a mod in itself,
and thus shelved until further notice.

## Properties

- `ingredients`: can be freely configured, the only limitation being that JEI can display a maximum of 12 ingredients -
  but the crafting itself supports as many ingredients as bowls can be placed in the 8x8 area around the pentacle's
  golden sacrificial bowl.
- `entity_to_sacrifice`:
    - If set, an entity from the tag needs to be sacrificed for the ritual to commence
    - the display name property is used to show the sacrifice in jei, a translation needs to be provided in the lang
      file.
- `item_to_use`: If set, an item from the tag needs to be used (right click) for the ritual to commence.
- `entity_to_summon`: the entity type of the entity to summon.
- `duration`: The duration of the ritual in seconds.
- `spirit_max_age`: The max age of the spirit in seconds.
- ...

## Sample Recipe

```json
{
  "type": "occultism:ritual",
  "ritual_type": "occultism:summon",
  "ritual_type": "occultism:summon_tamed",
  "ritual_type": "occultism:summon_spirit_with_job",
  "ritual_type": "occultism:summon_wild_hunt",
  "ritual_type": "occultism:familiar",
  "ritual_type": "occultism:craft",
  "ritual_type": "occultism:craft_with_spirit_name",
  "ritual_type": "occultism:craft_miner_spirit",
  "activation_item": {
    "item": "occultism:book_of_binding_bound_foliot"
  },
  "pentacle_id": "occultism:summon_foliot",
  "duration": 60,
  "spirit_max_age": 86400,
  "spirit_job_type": "occultism:crush_tier1",
  "item_to_use": {
    "item": "minecraft:egg"
  },
  "entity_to_sacrifice": {
    "tag": "forge:cows",
    "display_name": "ritual.occultism.sacrifice.cows"
  },
  "entity_to_summon": "minecraft:sheep",
  "ritual_dummy": {
    "item": "occultism:ritual_dummy/custom_ritual"
  },
  "ingredients": [
    {
      "tag": "forge:ores/iron"
    },
    {
      "item": "minecraft:egg"
    }
  ],
  "result": {
    "item": "occultism:jei_dummy/none"
  }
}
```