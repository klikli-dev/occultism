# Ritual Recipes

Ritual recipes are currently only *halfway* configurable, if "static" properties are modified this will only affect the
information displayed in JEI, but not the actual crafting recipe.

Fully configurable recipes are something I'd like to do, but due to the complex options this is almost a mod in itself,
and thus shelved until further notice.

## Properties

- `ingredients`: can be freely configured as long as it is not a empty list, the only limitation being that JEI can display a maximum of 12 ingredients -
  but the crafting itself supports as many ingredients as bowls can be placed in the 8x8 area around the pentacle's
  golden sacrificial bowl.
- `entity_to_sacrifice`:
    - If set, an entity from the tag needs to be sacrificed for the ritual to commence
    - the display name property is used to show the sacrifice in jei, a translation needs to be provided in the lang
      file.
- `item_to_use`: If set, an item from the tag needs to be used (right click) for the ritual to commence.
- `entity_to_summon`: the entity type of the entity to summon.
- `entity_tag_to_summon`: the tag that determines the entity to summon, instead of specifying the entity type directly. A random entity will be chosen from the tag.
- `entity_nbt`: An NBT Tag that will be merged into the entity's nbt. E.g. could be used to set `RabbitType` for rabbits, or `ForgeData` for arbitrary nbt. Uses the NBT formats also used in vanilla shapeless recipe's output: either a json object representing the tag, or a string containing the tag in NBT format.
- `duration`: The duration of the ritual in seconds.
- `spirit_max_age`: The max age of the spirit in seconds.
- `condition`: allows to use Neoforge conditions, plus the additional conditions provided by occultism: "occultism:is_in_biome", "occultism:is_in_biome_with_tag", "occultism:is_in_dimension", "occultism:is_in_dimension_type". Conditions are evaluated at the start of the ritual.
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
  "ritual_type": "occultism:execute_command",
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
    "tag": "c:cows",
    "display_name": "ritual.occultism.sacrifice.cows"
  },
  "entity_to_summon": "minecraft:sheep",
  "entity_nbt": {
    "RabbitType": 99
  },
  "ritual_dummy": {
    "id": "occultism:ritual_dummy/custom_ritual"
  },
  "ingredients": [
    {
      "tag": "c:ores/iron"
    },
    {
      "item": "minecraft:egg"
    }
  ],
  "result": {
    "id": "occultism:jei_dummy/none"
  },
  "command": "execute run say hi",
  "condition": {
    "type": "neoforge:or",
    "values": [
      {
        "type": "occultism:is_in_dimension_type",
        "dimension_type": "minecraft:the_nether"
      },
      {
        "type": "occultism:is_in_biome_with_tag",
        "tag": "minecraft:has_structure/nether_fortress"
      }
    ]
  }
}
```