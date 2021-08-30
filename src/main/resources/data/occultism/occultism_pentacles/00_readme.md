# Pentacles

Mappings:

- an object with a block entry supports a display tag, but does not need it.
- an object with tag entry requires a display tag.

```json
{
  "pattern": [
    "     N     ",
    "    GGG    ",
    "   GCPCG   ",
    "  G WCW G  ",
    " GCWW WWCG ",
    "ZGPC 0 CPGZ",
    " GCWW WWCG ",
    "  G WCW G  ",
    "   GCPCG   ",
    "    GGG    ",
    "     Z     "
  ],
  "mapping": {
    "0": "occultism:golden_sacrificial_bowl",
    "W": "occultism:chalk_glyph_white",
    "G": "occultism:chalk_glyph_gold",
    "P": "occultism:chalk_glyph_purple",
    "R": {
      "block:": "occultism:chalk_glyph_red"
    },
    "C": {
      "tag": "forge:candles",
      "display": "occultism:candle_white"
    },
    "S": "occultism:spirit_attuned_crystal",
    "Z": {
      "block": "minecraft:skeleton_skull",
      "display": "occultism:skeleton_skull_dummy"
    },
    "N": {
      "block": "minecraft:wither_skeleton_skull",
      "display": "occultism:wither_skeleton_skull_dummy"
    }
  }
}
```