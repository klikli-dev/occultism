package com.klikli_dev.occultism.datagen.compat.hephaestus;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.Identifier;

public final class HephaestusRecipeBuilder {

    public static final String MODID = "hephaestus";

    public static final Identifier MELTING = id("melting");
    public static final Identifier CASTING_TABLE = id("casting_table");
    public static final Identifier CASTING_BASIN = id("casting_basin");

    public static final Identifier INGOT_CAST = id("ingot_cast");
    public static final Identifier NUGGET_CAST = id("nugget_cast");

    public static final Identifier LAVA = Identifier.fromNamespaceAndPath("minecraft", "lava");
    public static final Identifier MOLTEN_BLAZE = id("molten_blaze");

    public static final int FUEL_AMOUNT = 50;

    private HephaestusRecipeBuilder() {
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }

    public static Identifier fuelFor(int temperature) {
        return temperature <= 1000 ? LAVA : MOLTEN_BLAZE;
    }

    public static JsonObject melting(JsonElement ingredient, Identifier fluid, int amount, int temperature, int time, boolean damageable) {
        var json = recipe(MELTING);
        json.add("ingredient", ingredient);
        json.add("result", fluid(fluid, amount));
        json.add("fuel", fluid(fuelFor(temperature), FUEL_AMOUNT));
        json.addProperty("temperature", temperature);
        json.addProperty("time", time);
        if (damageable)
            json.addProperty("damageable", true);
        return json;
    }

    public static JsonObject castingTable(Identifier cast, Identifier fluid, int amount, Identifier result, int coolingTime) {
        var json = recipe(CASTING_TABLE);
        json.addProperty("cast", cast.toString());
        json.add("fluid", fluid(fluid, amount));
        json.add("result", item(result));
        json.addProperty("cooling_time", coolingTime);
        return json;
    }

    public static JsonObject castingBasin(Identifier fluid, int amount, Identifier result, int coolingTime) {
        var json = recipe(CASTING_BASIN);
        json.add("fluid", fluid(fluid, amount));
        json.add("result", item(result));
        json.addProperty("cooling_time", coolingTime);
        return json;
    }

    private static JsonObject recipe(Identifier type) {
        var json = new JsonObject();
        json.addProperty("type", type.toString());
        return json;
    }

    private static JsonObject fluid(Identifier id, int amount) {
        var json = new JsonObject();
        json.addProperty("id", id.toString());
        json.addProperty("amount", amount);
        return json;
    }

    private static JsonObject item(Identifier id) {
        var json = new JsonObject();
        json.addProperty("id", id.toString());
        return json;
    }
}
