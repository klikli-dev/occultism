# Crushing Recipes

Crushing recipes are mostly created via Datagen in CrushingRecipeProvider.

They support "tag" outputs instead of "item" too, and use AlmostUnified (if present) to determine which item to output. Without AU present, the first item in the tag is used.

## Properties

- `ingredient`: Takes standard forge ingredient (Item or Tag) 
- `min_tier`: If set, the recipe can only be performed by a crusher spirit of at least this tier
- `result`: A standard forge result Item, supporting "count", "nbt", and so on
- `crushing_time`: The time in ticks it takes to crush the item.
- `ignore_crushing_multiplier` (optional): If set to true, the crushing multiplier of the crusher spirit is ignored for this recipe. This is important to avoid duplication for non-raw-material crushing recipes for e.g. crushing an iron ingot into 2 iron dust, and then smelting each back into an iron ingot.

## Sample Recipe

```json
{
  "type": "occultism:crushing",
  "ingredient": {
    "tag": "c:ores/copper"
  },
  "min_tier": 3,
  "result": {
    "item": "occultism:copper_dust",
    "count": 2
  },
  "crushing_time": 200,
  "ignore_crushing_multiplier": false
}
```