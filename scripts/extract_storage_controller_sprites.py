# SPDX-FileCopyrightText: 2026 klikli-dev
#
# SPDX-License-Identifier: MIT

from pathlib import Path

from PIL import Image


ROOT = Path(__file__).resolve().parents[1]
TEXTURES = ROOT / "src/main/resources/assets/occultism/textures/gui"
SPRITES = TEXTURES / "sprites/storage_controller"


def crop(source: str, box: tuple[int, int, int, int], output: str) -> None:
    source_path = TEXTURES / source
    output_path = SPRITES / output
    output_path.parent.mkdir(parents=True, exist_ok=True)
    with Image.open(source_path) as image:
        image.crop(box).save(output_path)


def main() -> None:
    crops: dict[str, tuple[str, tuple[int, int, int, int]]] = {
        "search_field.png": ("storage_controller_top.png", (55, 4, 151, 18)),
        "order_panel.png": ("storage_controller_bottom.png", (0, 13, 42, 55)),
        "button/clear_normal.png": ("buttons.png", (0, 196, 28, 224)),
        "button/clear_hover.png": ("buttons.png", (28, 196, 56, 224)),
        "button/sort_amount_normal.png": ("buttons.png", (0, 0, 28, 28)),
        "button/sort_amount_hover.png": ("buttons.png", (28, 0, 56, 28)),
        "button/sort_name_normal.png": ("buttons.png", (0, 28, 28, 56)),
        "button/sort_name_hover.png": ("buttons.png", (28, 28, 56, 56)),
        "button/sort_mod_normal.png": ("buttons.png", (0, 56, 28, 84)),
        "button/sort_mod_hover.png": ("buttons.png", (28, 56, 56, 84)),
        "button/sort_direction_down_normal.png": ("buttons.png", (0, 84, 28, 112)),
        "button/sort_direction_down_hover.png": ("buttons.png", (28, 84, 56, 112)),
        "button/sort_direction_up_normal.png": ("buttons.png", (0, 112, 28, 140)),
        "button/sort_direction_up_hover.png": ("buttons.png", (28, 112, 56, 140)),
        "button/jei_on_normal.png": ("buttons.png", (0, 140, 28, 168)),
        "button/jei_on_hover.png": ("buttons.png", (28, 140, 56, 168)),
        "button/jei_off_normal.png": ("buttons.png", (0, 168, 28, 196)),
        "button/jei_off_hover.png": ("buttons.png", (28, 168, 56, 196)),
        "button/rows_normal.png": ("buttons.png", (0, 224, 28, 252)),
        "button/rows_hover.png": ("buttons.png", (28, 224, 56, 252)),
        "tab/inventory_active.png": ("buttons.png", (160, 0, 208, 58)),
        "tab/inventory_inactive.png": ("buttons.png", (160, 58, 208, 116)),
        "tab/crafting_active.png": ("buttons.png", (160, 116, 208, 174)),
        "tab/crafting_inactive.png": ("buttons.png", (160, 174, 208, 232)),
    }

    for output, (source, box) in crops.items():
        crop(source, box, output)


if __name__ == "__main__":
    main()
