# Crushing Recipes

Crushing recipes are mostly created via Datagen in CrushingRecipeProvider.

They support "tag" outputs instead of "item" too, and use AlmostUnified (if present) to determine which item to output. Without AU present, the first item in the tag is used.

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