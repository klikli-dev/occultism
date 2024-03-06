package com.klikli_dev.occultism.common.item.spirit.calling;


public interface IItemModeSubset<T extends IItemModeSubset<T>> {
    //region Getter / Setter
    ItemMode getItemMode();
    //endregion Getter / Setter

    T next();
}
