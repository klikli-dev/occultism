# Ritual Recipes

Ritual recipes are currently only *halfway* configurable, if "static" properties are modified this will only affect the information displayed in JEI, but not the actual crafting recipe.

Fully configurable recipes are something I'd like to do, but due to the complex options this is almost a mod in itself, and thus shelved until further notice.

## Configurable Properties

The `ingredients` element can be freely configured, the only limitation being that JEI can display a maximum of 12 ingredients - but the crafting itself supports as many ingredients as bowls can be placed in the 8x8 area around the pentacle's golden sacrificial bowl. 

## Static Properties

The following items cannot be configured. They are intended only to allow JEI to display the hard-coded properties of this ritual. If you modify these properties the information in JEI will update accordingly, but it will no longer match how the ritual actually behaves.

- `activation_item`
- `pentacle_id`
- `require_item_use`
- `require_sacrifice`
- `ritual`
- `result`
