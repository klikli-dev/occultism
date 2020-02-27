/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.common.item;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.github.klikli_dev.occultism.api.common.data.MachineReference;
import com.github.klikli_dev.occultism.api.common.item.IHandleItemMode;
import com.github.klikli_dev.occultism.api.common.item.IIngredientCopyNBT;
import com.github.klikli_dev.occultism.api.common.item.IIngredientPreventCrafting;
import com.github.klikli_dev.occultism.api.common.tile.IStorageController;
import com.github.klikli_dev.occultism.common.entity.spirits.EntitySpirit;
import com.github.klikli_dev.occultism.common.entity.spirits.WorkAreaSize;
import com.github.klikli_dev.occultism.common.jobs.SpiritJobManageMachine;
import com.github.klikli_dev.occultism.registry.ItemRegistry;
import com.github.klikli_dev.occultism.util.EntityUtil;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import com.github.klikli_dev.occultism.util.TextUtil;
import com.github.klikli_dev.occultism.util.TileEntityUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Rarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemBookOfCallingActive extends Item implements IIngredientPreventCrafting, IIngredientCopyNBT, IHandleItemMode {

    //region Fields
    public static final String TRANSLATION_KEY_BASE = "item." + Occultism.MODID + ".book_of_calling_active";
    public static Map<UUID, Long> spiritDeathRegister = new HashMap<>();
    //endregion Fields

    /**
     * @param name              the item name for the resource location.
     * @param oreDictionaryName the ore dictionary name, should be the same for all books for the same type of spirit.
     */
    //region Initialization
    public ItemBookOfCallingActive(String name, String oreDictionaryName) {
        this.setMaxStackSize(1);
        ItemRegistry.registerItem(this, name, oreDictionaryName);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public ActionResultType onItemUse(PlayerEntity player, World world, BlockPos pos, Hand hand, Direction facing,
                                      float hitX, float hitY, float hitZ) {

        ItemStack itemStack = player.getHeldItem(hand);
        CompoundNBT entityData = ItemNBTUtil.getSpiritEntityData(itemStack);
        if (entityData != null) {
            //whenever we have an entity stored we can do nothing but release it
            if (!world.isRemote) {
                EntitySpirit entity = (EntitySpirit) EntityUtil.entityFromNBT(world, entityData);

                facing = facing == null ? Direction.UP : facing;

                entity.setLocationAndAngles(pos.getX() + facing.getXOffset(), pos.getY() + facing.getYOffset(),
                        pos.getZ() + facing.getZOffset(), world.rand.nextInt(360), 0);
                entity.setTamedBy(player);

                //refresh item nbt
                ItemNBTUtil.updateItemNBTFromEntity(itemStack, entity);

                world.spawnEntity(entity);

                itemStack.getTagCompound().removeTag(ItemNBTUtil.SPIRIT_DATA_TAG); //delete entity from item
                player.inventoryContainer.detectAndSendChanges();
            }
        }
        else {
            //if there are no entities stored, we can either open the ui or perform the action
            if (player.isSneaking()) {
                //when sneaking, perform action based on mode
                return this.handleItemMode(player, world, pos, hand, facing, hitX, hitY, hitZ);
            }
            else {
                //if not sneaking, open general ui
                ItemMode mode = ItemMode.get(this.getItemMode(itemStack));
                WorkAreaSize workAreaSize = ItemNBTUtil.getWorkAreaSize(itemStack);
                Occultism.proxy.openBookOfCallingUI(mode, workAreaSize);
            }
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity player, LivingEntity target,
                                            Hand hand) {
        if (target.world.isRemote)
            return false;

        //Ignore anything that is not a spirit
        if (!(target instanceof EntitySpirit))
            return false;

        EntitySpirit entitySpirit = (EntitySpirit) target;

        //books can only control the spirit that is bound to them.
        if (!entitySpirit.getUniqueID().equals(ItemNBTUtil.getSpiritEntityUUID(stack))) {
            player.sendStatusMessage(
                    new TranslationTextComponent(TRANSLATION_KEY_BASE + ".message_target_uuid_no_match"), true);
            return false;
        }

        //serialize entity
        CompoundNBT entityNbt = EntityUtil.entityToNBT(entitySpirit, null);
        ItemNBTUtil.setSpiritEntityData(stack, entityNbt);

        //show player swing anim
        player.swingArm(hand);
        entitySpirit.setDead();
        player.inventoryContainer.detectAndSendChanges();
        return true;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (worldIn.getTotalWorldTime() % (20 * 60) == 0) {
            UUID spiritID = ItemNBTUtil.getSpiritEntityUUID(stack);
            if (spiritID != null) {
                Long deathTime = spiritDeathRegister.get(spiritID);
                if (deathTime != null && deathTime < worldIn.getTotalWorldTime()) {
                    spiritDeathRegister.remove(spiritID);
                    stack.getTagCompound().removeTag(ItemNBTUtil.SPIRIT_UUID_TAG);
                }
            }
        }
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        String text = I18n.format(
                TRANSLATION_KEY_BASE + (ItemNBTUtil.getSpiritEntityUUID(stack) != null ? ".tooltip" : ".tooltip_dead"),
                TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(stack)));
        tooltip.add(text);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return ItemNBTUtil.getSpiritEntityData(stack) != null;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return ItemNBTUtil.getSpiritEntityUUID(stack) != null ? Rarity.RARE : Rarity.COMMON;
    }

    @Override
    public boolean shouldCopyNBT(ItemStack itemStack, IRecipe recipe, CraftingInventory inventory) {
        return recipe.getRecipeOutput().getItem() instanceof ItemBookOfBindingActive;
    }

    @Override
    public CompoundNBT overrideNBT(ItemStack itemStack, CompoundNBT nbt, IRecipe recipe,
                                   CraftingInventory inventory) {
        CompoundNBT result = new CompoundNBT();
        //copy over only the spirit name
        if (nbt.hasKey(ItemNBTUtil.SPIRIT_NAME_TAG))
            result.setString(ItemNBTUtil.SPIRIT_NAME_TAG, nbt.getString(ItemNBTUtil.SPIRIT_NAME_TAG));
        return result;
    }

    @Override
    public boolean shouldPreventCrafting(ItemStack itemStack, IRecipe recipe, CraftingInventory inventory,
                                         World world) {
        CompoundNBT entityNBT = ItemNBTUtil.getSpiritEntityData(itemStack);
        if (entityNBT != null)
            return true; //entity stored in the book.

        UUID entityUUID = ItemNBTUtil.getSpiritEntityUUID(itemStack);

        MinecraftServer server =
                world.getMinecraftServer() != null ? world.getMinecraftServer() : FMLCommonHandler.instance()
                                                                                          .getMinecraftServerInstance();
        return entityUUID != null && server != null &&
               server.getEntityFromUuid(entityUUID) != null; //entity exists still in the world.
    }

    @Override
    public int getItemMode(ItemStack stack) {
        return ItemNBTUtil.getItemMode(stack);
    }

    @Override
    public void setItemMode(ItemStack stack, int mode) {
        ItemNBTUtil.setItemMode(stack, mode);
    }
    //endregion Overrides


    //region Methods

    public boolean setSpiritManagedMachine(PlayerEntity player, World world, BlockPos pos, Hand hand,
                                           Direction face) {
        ItemStack stack = player.getHeldItem(hand);
        UUID boundSpiritId = ItemNBTUtil.getSpiritEntityUUID(stack);
        if (boundSpiritId != null) {
            EntitySpirit boundSpirit = (EntitySpirit) world.getMinecraftServer().getEntityFromUuid(boundSpiritId);
            TileEntity tileEntity = world.getTileEntity(pos);
            if (boundSpirit != null && tileEntity != null) {
                if (boundSpirit.getJob() instanceof SpiritJobManageMachine) {
                    SpiritJobManageMachine job = (SpiritJobManageMachine) boundSpirit.getJob();
                    MachineReference newReference = MachineReference.fromTileEntity(tileEntity);
                    if (job.getManagedMachine() == null ||
                        !job.getManagedMachine().globalPos.equals(newReference.globalPos)) {
                        //if we are setting a completely new machine, just overwrite the reference.
                        job.setManagedMachine(newReference);
                    }
                    else {
                        //otherwise just update the registry name in case the block type was switched out
                        job.getManagedMachine().registryName = newReference.registryName;
                    }

                    //write data into item nbt for client side usage
                    ItemNBTUtil.updateItemNBTFromEntity(stack, boundSpirit);

                    player.sendStatusMessage(
                            new TranslationTextComponent(TRANSLATION_KEY_BASE + ".message_set_managed_machine",
                                    TextUtil.formatDemonName(boundSpirit.getName())), true);
                    return true;
                }
            }
            else {
                player.sendStatusMessage(
                        new TranslationTextComponent(TRANSLATION_KEY_BASE + ".message_spirit_not_found"), true);
            }
        }
        return false;
    }

    public boolean setSpiritStorageController(PlayerEntity player, World world, BlockPos pos, Hand hand,
                                              Direction face) {
        ItemStack stack = player.getHeldItem(hand);
        UUID boundSpiritId = ItemNBTUtil.getSpiritEntityUUID(stack);
        if (boundSpiritId != null) {
            EntitySpirit boundSpirit = (EntitySpirit) world.getMinecraftServer().getEntityFromUuid(boundSpiritId);

            if (boundSpirit != null) {
                if (boundSpirit.getJob() instanceof SpiritJobManageMachine) {
                    SpiritJobManageMachine job = (SpiritJobManageMachine) boundSpirit.getJob();
                    job.setStorageControllerPosition(new GlobalBlockPos(pos, world.provider.getDimension()));
                    //write data into item nbt for client side usage
                    ItemNBTUtil.updateItemNBTFromEntity(stack, boundSpirit);

                    String blockName = world.getBlockState(pos).getBlock().getLocalizedName();
                    player.sendStatusMessage(
                            new TranslationTextComponent(TRANSLATION_KEY_BASE + ".message_set_storage_controller",
                                    TextUtil.formatDemonName(boundSpirit.getName()), blockName), true);
                    return true;
                }
            }
            else {
                player.sendStatusMessage(
                        new TranslationTextComponent(TRANSLATION_KEY_BASE + ".message_spirit_not_found"), true);
            }
        }
        return false;
    }

    public boolean setSpiritDepositLocation(PlayerEntity player, World world, BlockPos pos, Hand hand,
                                            Direction face) {
        ItemStack stack = player.getHeldItem(hand);
        UUID boundSpiritId = ItemNBTUtil.getSpiritEntityUUID(stack);
        if (boundSpiritId != null) {
            EntitySpirit boundSpirit = (EntitySpirit) world.getMinecraftServer().getEntityFromUuid(boundSpiritId);

            if (boundSpirit != null) {
                //update properties on entity
                boundSpirit.setDepositPosition(pos);
                boundSpirit.setDepositFacing(face);
                //also update control item with latest data
                ItemNBTUtil.updateItemNBTFromEntity(stack, boundSpirit);

                String blockName = world.getBlockState(pos).getBlock().getLocalizedName();
                player.sendStatusMessage(new TranslationTextComponent(TRANSLATION_KEY_BASE + ".message_set_deposit",
                        TextUtil.formatDemonName(boundSpirit.getName()), blockName, face.getName()), true);
                return true;
            }
            else {
                player.sendStatusMessage(
                        new TranslationTextComponent(TRANSLATION_KEY_BASE + ".message_spirit_not_found"), true);
            }
        }
        return false;
    }

    public boolean setSpiritBaseLocation(PlayerEntity player, World world, BlockPos pos, Hand hand,
                                         Direction face) {
        ItemStack stack = player.getHeldItem(hand);
        UUID boundSpiritId = ItemNBTUtil.getSpiritEntityUUID(stack);
        if (boundSpiritId != null) {
            EntitySpirit boundSpirit = (EntitySpirit) world.getMinecraftServer().getEntityFromUuid(boundSpiritId);

            if (boundSpirit != null) {
                //update properties on entity
                boundSpirit.setWorkAreaPosition(pos);
                //also update control item with latest data
                ItemNBTUtil.updateItemNBTFromEntity(stack, boundSpirit);

                String blockName = world.getBlockState(pos).getBlock().getLocalizedName();
                player.sendStatusMessage(new TranslationTextComponent(TRANSLATION_KEY_BASE + ".message_set_base",
                        TextUtil.formatDemonName(boundSpirit.getName()), blockName), true);
                return true;
            }
            else {
                player.sendStatusMessage(
                        new TranslationTextComponent(TRANSLATION_KEY_BASE + ".message_spirit_not_found"), true);
            }
        }
        return false;
    }

    public ActionResultType handleItemMode(PlayerEntity player, World world, BlockPos pos, Hand hand,
                                           Direction facing, float hitX, float hitY, float hitZ) {

        ItemStack stack = player.getHeldItem(hand);
        ItemMode itemMode = ItemMode.get(this.getItemMode(stack));
        TileEntity tileEntity = world.getTileEntity(pos);

        //handle the serverside item modes
        if (!world.isRemote) {
            switch (itemMode) {
                case SET_DEPOSIT:
                    if (tileEntity != null &&
                        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing) != null) {
                        return this.setSpiritDepositLocation(player, world, pos, hand,
                                facing) ? ActionResultType.SUCCESS : ActionResultType.PASS;
                    }
                    break;
                case SET_BASE:
                    return this.setSpiritBaseLocation(player, world, pos, hand,
                            facing) ? ActionResultType.SUCCESS : ActionResultType.PASS;
                case SET_STORAGE_CONTROLLER:
                    if (tileEntity instanceof IStorageController) {
                        return this.setSpiritStorageController(player, world, pos, hand,
                                facing) ? ActionResultType.SUCCESS : ActionResultType.PASS;
                    }
                case SET_MANAGED_MACHINE:
                    if (tileEntity != null && TileEntityUtil.hasCapabilityOnAnySide(tileEntity,
                            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
                        this.setSpiritManagedMachine(player, world, pos, hand, facing);
                        return ActionResultType.SUCCESS;
                    }
                    break;
            }
        }
        else {
            switch (itemMode) {
                case SET_MANAGED_MACHINE:
                    if (tileEntity != null && TileEntityUtil.hasCapabilityOnAnySide(tileEntity,
                            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
                        MachineReference machine = ItemNBTUtil.getManagedMachine(stack);
                        if (machine != null) {
                            Occultism.proxy
                                    .openBookOfCallingManageMachineUI(machine.insertFacing, machine.extractFacing,
                                            machine.customName);
                        }
                    }
                    break;
            }
        }


        return ActionResultType.PASS;
    }
    //endregion Methods

    public enum ItemMode {

        SET_DEPOSIT(0, "set_deposit"),
        SET_PICKUP(1, "set_pickup"),
        SET_BASE(2, "set_base"),
        SET_STORAGE_CONTROLLER(3, "set_storage_controller"),
        SET_MANAGED_MACHINE(4, "set_managed_machine");

        //region Fields
        private static final Map<Integer, ItemMode> lookup = new HashMap<Integer, ItemMode>();
        private static final String TRANSLATION_KEY_BASE =
                "enum." + Occultism.MODID + ".book_of_calling_active.item_mode";

        static {
            for (ItemMode itemMode : ItemMode.values()) {
                lookup.put(itemMode.getValue(), itemMode);
            }
        }

        private int value;
        private String translationKey;
        //endregion Fields

        //region Initialization
        ItemMode(int value, String translationKey) {
            this.value = value;
            this.translationKey = TRANSLATION_KEY_BASE + "." + translationKey;
        }
        //endregion Initialization

        //region Getter / Setter
        public int getValue() {
            return this.value;
        }

        public String getTranslationKey() {
            return this.translationKey;
        }
        //endregion Getter / Setter

        //region Static Methods
        public static ItemMode get(int value) {
            return lookup.get(value);
        }
        //endregion Static Methods

        //region Methods
        public ItemMode next() {
            return values()[(this.ordinal() + 1) % values().length];
        }

        public boolean equals(int value) {
            return this.value == value;
        }
        //endregion Methods
    }
}
