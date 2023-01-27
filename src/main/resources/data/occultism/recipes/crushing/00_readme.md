# Crushing Recipes

Ritual recipes are currently only *halfway* configurable, if "static" properties are modified this will only affect the
information displayed in JEI, but not the actual crafting recipe.

Fully configurable recipes are something I'd like to do, but due to the complex options this is almost a mod in itself,
and thus shelved until further notice.

## Properties

- `ingredient`: Takes standard forge ingredient (Item or Tag) 
- `min_tier`: If set, the recipe can only be performed by a crusher spirit of at least this tier
- `result`: A standard forge result Item, supporting "count", "nbt", and so on
- `crushing_time`: The time in ticks it takes to crush the item.

## Sample Recipe

```json
{
  "type": "occultism:crushing",
  "ingredient": {
    "tag": "forge:ores/copper"
  },
  "min_tier": 3,
  "result": {
    "item": "occultism:copper_dust",
    "count": 2
  },
  "crushing_time": 200
}
```