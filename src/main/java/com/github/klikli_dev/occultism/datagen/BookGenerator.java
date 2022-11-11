package com.github.klikli_dev.occultism.datagen;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.github.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePageModel;
import com.github.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritTradeRecipePage;
import com.github.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritTradeRecipePageModel;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.BookLangHelper;
import com.klikli_dev.modonomicon.api.datagen.EntryLocationHelper;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookTrueConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class BookGenerator implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final DataGenerator generator;
    private final Map<ResourceLocation, BookModel> bookModels;
    protected String modid;

    public BookGenerator(DataGenerator generator, String modid) {
        this.modid = modid;
        this.generator = generator;
        this.bookModels = new HashMap<>();
    }


    private static Path getPath(Path path, BookModel bookModel) {
        ResourceLocation id = bookModel.getId();
        return path.resolve("data/" + id.getNamespace() + "/modonomicons/" + id.getPath() + "/book.json");
    }

    private static Path getPath(Path path, BookCategoryModel bookCategoryModel) {
        ResourceLocation id = bookCategoryModel.getId();
        return path.resolve("data/" + id.getNamespace() +
                "/modonomicons/" + bookCategoryModel.getBook().getId().getPath() +
                "/categories/" + id.getPath() + ".json");
    }

    private static Path getPath(Path path, BookEntryModel bookEntryModel) {
        ResourceLocation id = bookEntryModel.getId();
        return path.resolve("data/" + id.getNamespace() +
                "/modonomicons/" + bookEntryModel.getCategory().getBook().getId().getPath() +
                "/entries/" + id.getPath() + ".json");
    }

    public ResourceLocation modLoc(String name) {
        return new ResourceLocation(this.modid, name);
    }

    private void start() {
        var dictionaryOfSpirits = this.makeDictionaryOfSpirits();
        this.add(dictionaryOfSpirits);
    }

    private BookModel makeDictionaryOfSpirits() {
        var helper = ModonomiconAPI.get().getLangHelper(this.modid);
        helper.book("dictionary_of_spirits");

        int sortNum = 1;
        var gettingStartedCategory = this.makeGettingStartedCategory(helper).withSortNumber(sortNum++);

        var advancedCategory = this.makeAdvancedCategory(helper).withSortNumber(sortNum++);

        var ritualsCategory = this.makeRitualsCategory(helper).withSortNumber(sortNum++);

        var summoningRitualsCategory = this.makeSummoningRitualsSubcategory(helper).withSortNumber(sortNum++);
        var possessionRitualsCategory = this.makePossessionRitualsSubcategory(helper).withSortNumber(sortNum++);
        var craftingRitualsCategory = this.makeCraftingRitualsSubcategory(helper).withSortNumber(sortNum++);
        var familiarRitualsCategory = this.makeFamiliarRitualsSubcategory(helper).withSortNumber(sortNum++);

        var pentaclesCategory = this.makePentaclesCategory(helper).withSortNumber(sortNum++);

        //https://viewer.diagrams.net/?highlight=0000ff&edit=_blank&layers=1&nav=1&title=Gameplay%20Path.drawio&%24web_only=true&_branch_match_id=1080882655718139071&utm_medium=marketing&_branch_referrer=H4sIAAAAAAAAA8soKSkottLXz87JBCK9xIICvZzMvGz9VP385OTSnJLM4lzdtNTEktKi1GIA%2B%2FLRTSwAAAA%3D#R7V1bc9pKEv41qd19IKU74hGb%2BJIrJySxc15SMoxBRtKQQfj261cSCIRmPBKW0PRgtk5tQEhm6Omv793zTj%2F1H8%2BJM5t8wSPkvdOU0eM7vfdOi%2F7X1qJ%2F4itPyyuqrZjLK2PijlbXNhcG7jNaXVRWVxfuCM23bgwx9kJ3tn1xiIMADcOtaw4h%2BGH7tlvsbX%2FrzBkj6sJg6Hj01St3FE6WV21T2Vy%2FQO54kn6zqqw%2B8Z305tWF%2BcQZ4YfMJf3DO%2F2UYBwuX%2FmPp8iLqZfSZfnc2QufrhdGUBCWeeDpV6fb%2Befk8gs%2Btztt%2F%2FPX4fSq1V6tLXxKfzAaRb9%2F9RaTcILHOHC8D5urJwQvghGK%2F6oSvdvc8xnjWXRRjS7eoTB8Wm2mswhxdGkS%2Bt7qU%2FTohteZ17%2FjP%2FXeXL3rPa7%2BcvLmKX0ThOTpOvsm81T8dvNY8i59jqbSinBzvCBDxCGNteI2h4xRyLmvs7wvplvmC1Z7cI6wj6L1RDcQ5Dmhe7%2FNV86KPcfr%2B9aP9rEbLVlTUiyliFghyeq8t822amir%2F9e3%2F%2BBy2au%2FseGK6EVmUZtLCa%2FswDdaRyTjbJjld5ZXJGMcza6bc7Z2edctXS373vEWq2%2FqIR8H%2F5lHF3sEOT6145v9jDfnYeKGaDBzEto8ROpge%2B9epOc9IiF65BJg9amxjYD07cNGLqupsJ1kZHJ6X%2B0UU1WKJEfpuS0VC0GQSjUgIOhQIBjMXOLGcvjMJUg4BDQFGgasIwaKeLsYBAYoEKTrZqGgG4aLINo6TTlH4jWCbhbDQW8UDiYcu0jlwqECW2tyyvZ03VkLx3UiE2fE4WOlmI9vXc87xR4mybP6yET2yIiuz0OCpyjzia3d6JZVF%2BebW5zfEq8JhLoEwDWBURYybVCQ0fVD39O7hT9Lf844QXYDu6wJ2%2BXw%2BXn0t%2Fet%2B%2FP32XyqB9N7r3%2FaMozKu0yTEfi%2B17vLbLLqoLCcrntL%2Fd27QfTVOIiuf8c8PdiMPWdqwNwbTTt0AVgBCO2y4k4FBQRNGpe1ib0xQe1Nuu6MkPoWThCZhzgQH32xbGjiqbrmPlzxZJeFADA9bbMh8ICJF8ddPuPxXDgS7FwsXrz7uf4NmaCVMyTurTt0nfjpE%2FzgwSOcZosmnF7db6%2FV%2Bt8liLWRKhtB8jv7We1SJRUWhVJFh%2BXJp%2Bt%2BSap05xMkHh2qokCTK7oNCx6wlW5qTkoHD5OCx8mCJPUXgOzPvPIQD47qrpQwcFRg8rLOlQqrwkN7wblKdcAPggDogLyTBUAFSOhl7WYP1RkF10r7XbBUgC5N6lbIbtWfmUoe7RLiPGVumMUVi%2FPMX86VQZo56ZAves3d3s67W0qOT5bfX29tJO3A%2F5i4JJawH57EGxH5GHtrLUyFCVijeppRQjNCL2tGGLDi50b1onEAAYOmPKLyu2yBELCqkpOwNl%2FCUvcbTchYnTZlL%2F3ZgkTiVbmKRWj07%2BnE8abCxS0V0hBuzprVpe0Q%2B%2B5QXsmbklY6ycspzKxenjxy5pP1vXVwvmptc36jhsYn7daada%2FC0cx%2BfHQ7IXnu9euoVpYumIeCUTduhIveDj1nPo%2BBG188c71CMVeMpLQnrwhJqZJoHklsRgBmw7wdRjBr75erxghvMLwPgxEsYBIBWBr0IBmBWScJq%2FwiXXbGxgLmUOjQiiSNg5Ch2xFztY6AwOPCNr5eXo7OP3763fuXfPuLW59bWlnfo%2FZ4QNlNZi77DVrOFWQfo0acxwxQZB9dIn6Ok9TgZTDGIUf0KcWiT0ibFB19EV5vZRyEobEHacmmVtmCEnHikrvuLJQ8%2FFBURwIWR%2FlYjinc6jCrayR5wphM5VE2s2AK87SYy5ZY%2FlWQY2WnwojbLO66WYmW2DZAAWTHqEEZxWR1CQfENKXYzbI5mI6wDiUV24ub3p%2Fzy%2BD638E39LvjX12mFjv8La2zSoi1WUzq1J4ve10Ou523%2BgvKhOgHrAaS2CadzoMmUg2q40202WdVL8YEYfY1H5RIMy%2FFkXlYEdl03dms98L3k6b1bHG%2B8oM4I0SqgSbnXSE18q%2FaLO%2BqY7V1py7vKg8z8TXP1lvyrrj5qWK4wGqhtmitsg2XtNx%2F4Mw8NxgfFnDEF7NadKfdYCWg3mmW48d0G4fJ798WX6c4iH75PJnGIVrvW%2BD6ey06oDYInRsP5dkaRhcLTUEAnEkXTf5yggi3TnTxMkQ%2Bj2xQI5N5MuuWcDLT9sp3N1zETejSCViKuqpo6rZp9fYlnlyknCEnXMalfgYeHop3nvK005rkTHaNS%2FUWJ4H%2B0rpWJYj1ZbZQpZqhl55RsO8J7qpOqQQ7t9N7ntqe%2FtJ9zsjYqiKO3%2FSdMEQkSK5oSl3qNT8IuaUJj0zYB1JX33wsuF02kyWuipi77gyeTvFsFrtSyjcivtWPyoiowo0j%2B%2BDCd42BpGynLjSQ0O2wA9e7hwsSTbiNa1fvTgAGkqZi3CnvF4MEVtDOrl5Ce9zxgh2HVU8reNqEJmbcROndMmo%2FEuN1mWQzd8JEUTd0%2Fn6tXdQ%2BnZ9Qsf3AfjLPNh1EWecITski8uBINb0MIxsgPGjd0Q5DrFcAfNn6XVtYXyB33dlZWTdzd%2BQ6MUZ6izmvGl5QikE8ux%2Ft1tfCpPRpk7BqM2y6PDTx6ZQeXtwss8vQUNJoHo7Z8Kgez9vYBRqMsiU2WYVBg7mclKVk3OUmdkvYbEymO6ZXL9Xe41ArpSQmm5qCzdpldn%2BjsDALe5elToBCkLzsXYYxukxTc0lBi%2B97U%2FfXXfXN5sGj9n8FDzYvQEh3MPl4Qf5OJ48fL%2F%2F0f%2BKT62Atz%2BC3f8AQF2wi7kdc0PhW9G18t82cSf9CvcXOUcKOsZPgoe5v2%2FUKnr%2FtP7b69dNTcGaqN8bPu0HrV6uGiPMbt3CYVAXS8WTn%2BrPXtUUvKj%2FFrPxEoYLVqSqdmjUsc0uAjSo5EEbX3jCjp5V75Rk998Se0jh0pXF%2FQWYeoGFVNrTpt53qjv7eYtR1SIr9FSB1yh59BSyV06GTnR%2FiM9%2BVgazDWPKgEl%2F72qGbnpZ55JjO5agtSCCJb8vpAC6JBC6RSs%2BQqD01UNI6WSMz%2FRvL30R5nMV%2FqeFWgc6B9P%2B%2BNgxSbhh6MYeWLX9IWRmKzmR0W6YFQn08n6Pov5VoR8RPxqNWEexiyoXymeFG1SgzKQbMcwUTb%2BTNDS7MNApLQfFWzQLWGfbceMqm8nnh3yBy51RtYoRRhddsaxaPBTJE70ZM7zthIsTijvHonwuH3KN5CLJoBQAN066Io3AqI5wYHW3M%2B4RVQfBWvXUKbjhMqoGVT4F7K95%2Fow6eF1%2FOlc4nOgKjDDAYXWxsqgo7HZS37OyZjo7n4QfhgKCPTBePiLd4us7uDiO3iLG4fk5YOxN33UUO42CKPBRWHXEkxrDtiDzPjE31o%2BrZBVuMInsZalM1YOJ0l6BwvbvVUI3pqzLTmkrNoqs5B0w6wfOw3xnfXbWe%2Fqq3H88eXT%2FS9pWZ4zAPpy%2FNVGyywuApLa9yigoR8g%2BYRbUO%2BaFEZhOlaNUn5TSe09pNSe2bZ5spRSurtZirqZ7VPO5xA1VYZff49Go6UIK%2B378cqRd3Ov46vV60qhsmDe3xvveKSR2odolRqBPyIQWjZqXAJJd6bMHaBxemcfsjG5b0qemW5k3QQhlMFx5vkCvUmrFNYAJM8EKr7rlIF7yoK0pYNoquCbMJuesuV1biyBgl3FRqgQEatN5m2FHC9Mbic6uFlWyxl%2FMGxWkDu6zBytDr1UMnx11m3CisZYG77u2WhaQMo48cIqUBaoCbw6NJ3IlfAROlM8ywJJ9GZ5j7Lq9UDywS8q6Y%2BNo%2B7SAKj%2FfQdsKmFqOoX4qJVHQZ7OnEHU4Rz8%2BCiiK6Cc4WjSJTYn0iwDYrXQkoLOfBXXf2ROzgdjFPph66PgriE9yS01S%2BOCFxHznYAhvDsEQ2SXINdBbV1%2BfAXWHiT7AnYzMSRfJGT9zjxjfYJMfEGcevviMfS9n%2BRVFcOMHNY5xuB%2F2RzlEr1h%2FA4nQGBautY0D7aITmYdXTAusASN5jF29h6RL7KRU4vay%2FkbIWFE5nzFDPcHoV9i6XR6uQtQHA63QzVv6gy%2B%2FoFkU%2FegiMmPB6Fw1NXrkhQLOW9cwMYJqV9sxOCHKm0aVz4sxlPN24lZdLwo1UQ2Ij9fWQSMlZOMcDVleWQbtwvchfC%2F4zj4MVETb82JdDcc2FYGOzlQ%2BKi2d0YF03r4yJtxuqRzTKTodTa2%2FsfVU9op3jt8KpkPkHNP7t7Tbv9v2ULhrVTxGsuYK2Xf6YOcg8q8FqRjfoVGjiWD1g4o0Sawffix%2FVYAGcA3OsntkBHibD6JEhQ2PSVg8jcN0dhgsnehe9PHHmBxHBbhRg7F5ciVOgFYCi0UBhkwdWOXa67qx7sJXBZKFFdFS6LXIKwyft1pp1r8LRzH58dDshee71wTUuNdGUXQEsZUdj7Wdm684D5%2FMN0q301KAXR7dSTxT5CqpZ8ERld4HNtwcRFRXJt%2BwQEIyGuzoYt2jcAM24dc%2B8YDPuQZjx4Bg3HWR3AIxbdAATxbc1n0vDZluJjWPAbGsfjLzd1Uyo2Uhgto9XtxEONz7C4FB2C%2F6hMGjLrFm9c%2BmarVN3gpGHeBnchqZtavCmbVoSq5XXgy%2B1smVr7bOAHcMIW8SW3mVgzdiWxIWKgHe5%2FmbsV2lSzdgtF0zdX6R411P4Xv9AzYqafZS1AovLtyc2lU43aw15NKXZHFjnbbruuu2x%2Bot4mywaYid4KEqd4yQl31vMQ%2FHWq5rLnRhNVjkzCUab%2BoOZS9xYtJ25BFhhs6rn5LglPuF6EDH8PRgaTGqpDAkMLFHLXTdrbtYXZzhxk86hbzNEVhUNvTs3CNxq6BFT09ARWAfKZgVgRg5whDF6lbgsDQVhjAMPBU99rN08yldONBu7YpMdWMUqcHCVPRIBGrjokaowHQgKIcK1z%2FGEl50AwsjISAEQ2g3qL8gsGWpxOnG8iueA7h0nwmHCGJgXn%2FA5JM4tiAM9dUXkVFjusTlZoewhJ%2FYerpATLg99%2FO%2FSkfhfNQIKGsRLpTst0TTXD6KnrClprpU1d3RhXZfss4OAJWRfFxYXuctssgqbpsddNysm8zO4ibczetW9TYKZ8klPajpSkzqeuf9CD5uBLSn1shiq%2FUiaauqQhtAKL8qH%2Bbz6YJO9mLrCcaBJHPLf%2FfwL3ul8hewuLozPWzZLY1y5SZrwYhFIqS3yY0oaTYwxaU23SH933OBp27tJpI2Mzg3lT%2BpN%2BjZMgtNj7X5MYjLG476If1jUbnQ6GJPa1ePmh2AM8WycQuUgbFgeczXV47xvfkOF%2BYe8VWcVUHK2VQ3x3Do0NpVnF27X1tBG3XwzlGFo2zBQtCIgJO%2F6iLgR2RCpjA5WTYsE8o5R0nI%2BceIiOeVHEoV%2BESB7SbbvA1KNpkW4IbPsRETPeU7GDOOH5EAh%2Bcic98jNJlMpbDIfw%2Fq7CKyy%2BhyY886oEHK9ey6GGtLl%2BdmJ4r3vY9HcTojoyImIDq3Ck2p3%2BXQKhSDhDr7Mk7Re7K1R9wah9Mbiyu7ac8WrR%2FMdWVaOpUw1xyrLpa4ey3HLbs1ZXIpk0HnlrgKf0p5inj%2BSolFNxxzqoVWG6SGoNfa8ExqSzP4yYd1uvFVvHzM5f6edxrEb%2FLB6NZggNFu%2BfP%2F%2BvYRQsoWe0sCkfHWNd7BQKjuouCMswU%2B6g8nHC%2FJ3Onn8ePmn%2FxOfXAd1pKqhT0ynjaxad5lNVlACk44xgSzuV418yrTRuBFrH6uPsTsEgcckDavCAxQUeKvOOsRecvCM8hkFcwjzi%2FIgsBuNUTNpVj1UdLAo0EqiAJjWp1CwHr9eBwyElIColgENODVEiA4WOKwUA5OG4moGOMt%2B8VwPPB6DmIKXP3tdPBhkLB5oCgys4nD4WoSuDY%2BVR3TljDi%2B%2BMJwSiF0hLsTNcxzP1gMMBqDJcAA3Rb8OS1eli7gSAFGVRpNU7PoK03R5esZn3GWK0%2Fc7jstplp6TmzmN7fGtBiHHhKcSU356gA0TPWxpuABU3%2Bb6QtIaL9kRDeEBEZxYFrQpFwGYwxgXmEeAe1Gu1yYRJNGZWQyGvXPN2XTpqyuARa0pVWCpGVMFF6aLWNibvXRL3%2BZNqzKfU5aAQhe0mWzZrZ2w3ARJG0u58gHZk7lh7eKD1pp0nTxVWDy0kltYWcsc9ed5XIUpzBwNiKLCYRwbMcCx9lCzaRXzoFvyExiDryRARF0RHZzrDKtAPrucOo8Snmucr4aXLzboR%2FNqEKYFOJJ3Bkx3HVn8PRlOch77jvJ444f7WB8EImCb2OnHM0Dd1HRrNoHPsTrG%2F2YCy9k%2B2J8wEqG67SfcRrP88wioR4tUwcm8hNwAGBCmmCtAEyU9Up0WAlBQ%2BieghkkWW1PDVh7qtOe5soOAKPw821X4oWbIY1BXIGhy1axGbAMW50uY1s7ij3XR8HcjakevYv5fD5JdLp8XmJ%2BQqp4UOhvIJ6ol%2Fb2gEl5zklngKPmlOhvNBby7DxfnOCuq3bRB%2F%2Fi6ta7%2BqUdO26LFUEWHEwawsooGbSnt60n%2Flk4hDxBOMiBGvIkXuob0pck1DMWgkkbQ04TyqCnqa5HBZ9hz42LcxLrichoOeUneIkvbDOkGS5fAQul3QlxkwJ56876x87YHUaXPjv%2BTLxKyDsCjbIzU71LEw0vxc08C6bQ0hEW0%2BatmiXW0yNblW8ESSzcqVnZjRpIPBbIhiP8GcH3iccF1dDU88dXNUrHv%2B0%2Ftvr101NwZqo3xs%2B7QetXi8G8PSde8Q837sc6rLPCGp3dyqS2RlH7a%2FKth0nvRodxnF5NB0rQ9%2FuXI%2FXiTsdfp9cLeXTma8MKu5%2B9wqQTQ%2FEy7xOmeHmrzk71iiR94kadOb7rudyR01BxRBV8t8wmw3NMSh%2BzzkWQKISOsMg1b9UM6LyIF7ANEvkeVAB4qR69AzDntSkEMUIYzPuEjZTkrTprOBN3%2BpC4GTKrH9qMEw8nupnrnWZ50beeRL61NY5fdD0fJwdNpJ9EX7T%2B8MwD4f5FBAJHWrX6vLo3JKpY57Kwb9RACSvGwSwDvIgfq5ycBiKkGj3qiLvlWzMzZ%2BFiGfnDN%2BJ7imj50yjVVGwvbnp%2Fzi%2BD638H39Dvjn91yfDhNv0nTjicIN5Ic6jsqTZ5wGz0luDIct98dh5RYfIFj1B8x%2F8B

        var demoBook = BookModel.builder()
                .withId(this.modLoc("dictionary_of_spirits"))
                .withModel(this.modLoc("dictionary_of_spirits_icon"))
                .withName(helper.bookName())
                .withTooltip(helper.bookTooltip())
                .withCategories(
                        gettingStartedCategory.build(),
                        pentaclesCategory.build(),
                        ritualsCategory.build(),
                        summoningRitualsCategory.build(),
                        possessionRitualsCategory.build(),
                        craftingRitualsCategory.build(),
                        familiarRitualsCategory.build(),
                        advancedCategory.build()
                )
                .withCraftingTexture(this.modLoc("textures/gui/book/crafting_textures.png"))
                .withGenerateBookItem(false)
                .withCustomBookItem(this.modLoc("dictionary_of_spirits"))
                .withAutoAddReadConditions(true)
                .build();
        return demoBook;
    }

    private BookCategoryModel.Builder makeGettingStartedCategory(BookLangHelper helper) {
        helper.category("getting_started");

        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
                "___________________________",
                "___________________________",
                "___________________________",
                "______i___________p________", //p=pentaclePrep, C=Chalks
                "___________________________",
                "___________________________",
                "___________________________",
                "______d___f___r_______R___N", //d=demonsDream, f=SpiritFire, r=divinationRod, R=ritualPrep, N=Next Steps
                "___________________________",
                "___________________________",
                "___________________________",
                "________e_________b________", //e=thirdEye, b=ritualBook
                "___________________________",
                "______________________c____", //c=?
                "___________________________",
                "__________________s________"  //s=?
        );

        var introEntry = this.makeIntroEntry(helper, entryHelper, 'i');

        var demonsDreamEntry = this.makeDemonsDreamEntry(helper, entryHelper, 'd');
        demonsDreamEntry.withParent(BookEntryParentModel.builder().withEntryId(introEntry.id).build());

        var spiritFireEntry = this.makeSpiritFireEntry(helper, entryHelper, 'f');
        spiritFireEntry.withParent(BookEntryParentModel.builder().withEntryId(demonsDreamEntry.id).build());

        var thirdEyeEntry = this.makeThirdEyeEntry(helper, entryHelper, 'e');
        thirdEyeEntry.withParent(BookEntryParentModel.builder().withEntryId(demonsDreamEntry.id).build());

        var divinationRodEntry = this.makeDivinationRodEntry(helper, entryHelper, 'r');
        divinationRodEntry.withParent(BookEntryParentModel.builder().withEntryId(spiritFireEntry.id).build());

        var pentaclePrepEntry = this.makeRitualPrepEntry(helper, entryHelper, 'p');
        pentaclePrepEntry.withParent(BookEntryParentModel.builder().withEntryId(divinationRodEntry.id).build());

        var ritualBookEntry = this.makeRitualBookEntry(helper, entryHelper, 'b');
        ritualBookEntry.withParent(BookEntryParentModel.builder().withEntryId(divinationRodEntry.id).build());

        var ritualEntry = this.makeRitualEntry(helper, entryHelper, 'R');
        ritualEntry
                .withParent(BookEntryParentModel.builder().withEntryId(pentaclePrepEntry.id).build())
                .withParent(BookEntryParentModel.builder().withEntryId(ritualBookEntry.id).build());

        var nextStepsEntry = this.makeNextStepsEntry(helper, entryHelper, 'N');
        nextStepsEntry.withParent(BookEntryParentModel.builder().withEntryId(ritualEntry.id).build());

        return BookCategoryModel.builder()
                .withId(this.modLoc("getting_started"))
                .withName(helper.categoryName())
                .withIcon(OccultismItems.DICTIONARY_OF_SPIRITS_ICON.getId().toString())
                .withEntry(introEntry.build())
                .withEntry(demonsDreamEntry.build())
                .withEntry(spiritFireEntry.build())
                .withEntries(thirdEyeEntry.build())
                .withEntries(divinationRodEntry.build())
                .withEntries(pentaclePrepEntry.build())
                .withEntries(ritualBookEntry.build())
                .withEntries(ritualEntry.build())
                .withEntries(nextStepsEntry.build());
    }

    private BookEntryModel.Builder makeIntroEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("intro");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("recipe");
        var recipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/dictionary_of_spirits_old_edition"))
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.DICTIONARY_OF_SPIRITS_ICON.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(intro, intro2, recipe);
    }

    private BookEntryModel.Builder makeDemonsDreamEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("demons_dream");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.DATURA.get()))
                .withText(helper.pageText())
                .build();

        helper.page("harvest_effect");
        var harvestEffect = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("datura_screenshot");
        var datureScreenshot = BookImagePageModel.builder()
                .withImages(this.modLoc("textures/gui/book/datura_effect.png"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.DATURA.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(intro, intro2, spotlight, harvestEffect, datureScreenshot);
    }

    private BookEntryModel.Builder makeSpiritFireEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("spirit_fire");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.SPIRIT_FIRE.get()))
                .withText(helper.pageText())
                .build();

        helper.page("spirit_fire_screenshot");
        var spiritFireScreenshot = BookImagePageModel.builder()
                .withImages(this.modLoc("textures/gui/book/spiritfire_instructions.png"))
                .withText(helper.pageText())
                .build();


        helper.page("main_uses");
        var mainUses = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("otherstone_recipe");
        var otherstoneRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/otherstone"))
                .withText(helper.pageText())
                .build();

        helper.page("otherworld_sapling_natural_recipe");
        var otherworldSaplingNaturalRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_sapling_natural"))
                .withText(helper.pageText())
                .build();


        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.SPIRIT_FIRE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(spotlight, spiritFireScreenshot, mainUses, otherstoneRecipe, otherworldSaplingNaturalRecipe);
    }

    private BookEntryModel.Builder makeThirdEyeEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("third_eye");

        helper.page("about");
        var about = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("how_to_obtain");
        var howToObtain = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("otherworld_goggles");
        var otherworldGoggles = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.OTHERWORLD_GOGGLES.get()))
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(this.modLoc("textures/mob_effect/third_eye.png").toString())
                .withLocation(entryHelper.get(icon))
                .withPages(about, howToObtain, otherworldGoggles);
    }

    private BookEntryModel.Builder makeDivinationRodEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("divination_rod");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("otherstone_recipe");
        var otherstoneRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/otherstone"))
                .build();

        helper.page("otherworld_sapling_natural_recipe");
        var otherworldSaplingNaturalRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_sapling_natural"))
                .withText(helper.pageText())
                .build();

        helper.page("divination_rod");
        var divinationRod = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.DIVINATION_ROD.get()))
                .withText(helper.pageText())
                .withAnchor("divination_instructions")
                .build();

        helper.page("about_divination_rod");
        var aboutDivinationRod = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("how_to_use");
        var howToUse = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();


        helper.page("how_to_use2");
        var howToUse2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();


        helper.page("divination_rod_screenshots");
        var divinationRodScreenshots = BookImagePageModel.builder()
                .withImages(
                        this.modLoc("textures/gui/book/rod_detected.png"),
                        this.modLoc("textures/gui/book/rod_close.png")
                )
                .withText(helper.pageText())
                .build();

        helper.page("otherworld_groves");
        var otherworldGroves = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("otherworld_groves_2");
        var otherworldGroves2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("otherworld_trees");
        var otherworldTrees = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();
        helper.page("otherworld_trees_2");
        var otherworldTrees2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.DIVINATION_ROD.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(intro, otherstoneRecipe, otherworldSaplingNaturalRecipe, divinationRod, aboutDivinationRod,
                        howToUse, howToUse2, divinationRodScreenshots, otherworldGroves, otherworldGroves2, otherworldTrees, otherworldTrees2);
    }

    private BookEntryModel.Builder makeRitualPrepEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("ritual_prep");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("white_chalk");
        var whiteChalkSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.CHALK_WHITE.get()))
                .withText(helper.pageText())
                .withAnchor("white_chalk")
                .build();

        helper.page("burnt_otherstone_recipe");
        var burntOtherstoneRecipe = BookSmeltingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("smelting/burnt_otherstone"))
                .build();

        helper.page("otherworld_ashes_recipe");
        var otherworldAshesRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_ashes"))
                .build();

        helper.page("impure_white_chalk_recipe");
        var impureWhiteChalkRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/chalk_white_impure"))
                .build();

        helper.page("white_chalk_recipe");
        var whiteChalkRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_white"))
                .build();

        helper.page("brush_recipe");
        var brushRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/brush"))
                .withText(helper.pageText())
                .build();

        helper.page("white_candle");
        var whiteCandleSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.CANDLE_WHITE.get()))
                .withText(helper.pageText())
                .build();

        helper.page("tallow");
        var tallowSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.TALLOW.get()))
                .withText(helper.pageText())
                .build();

        helper.page("white_candle_recipe");
        var whiteCandleRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/candle"))
                .build();

        helper.page("sacrificial_bowl");
        var sacrificialBowlSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.SACRIFICIAL_BOWL.get()))
                .withText(helper.pageText())
                .build();

        helper.page("sacrificial_bowl_recipe");
        var sacrificialBowlRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/sacrificial_bowl"))
                .build();

        helper.page("golden_sacrificial_bowl");
        var goldenSacrificialBowlSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get()))
                .withText(helper.pageText())
                .build();

        helper.page("golden_sacrificial_bowl_recipe");
        var goldenSacrificialBowlRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/golden_sacrificial_bowl"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        whiteChalkSpotlight,
                        burntOtherstoneRecipe,
                        otherworldAshesRecipe,
                        impureWhiteChalkRecipe,
                        whiteChalkRecipe,
                        brushRecipe,
                        whiteCandleSpotlight,
                        tallowSpotlight,
                        whiteCandleRecipe,
                        sacrificialBowlSpotlight,
                        sacrificialBowlRecipe,
                        goldenSacrificialBowlSpotlight,
                        goldenSacrificialBowlRecipe
                );
    }

    private BookEntryModel.Builder makeRitualBookEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("ritual_book");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText()) //talk about book of binding, and bound book of binding
                .build();

        helper.page("purified_ink_recipe");
        var purifiedInkRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/purified_ink"))
                .withText(helper.pageText())
                .build();

        helper.page("book_of_binding_foliot_recipe");
        var bookOfBindingFoliotRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_foliot"))
                .withText(helper.pageText())
                .build();

        helper.page("book_of_binding_bound_foliot_recipe");
        var bookOfBindingBoundFoliotRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_bound_foliot"))
                .withText(helper.pageText())
                .build();

        helper.page("book_of_binding_djinni_recipe");
        var bookOfBindingDjinniRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_djinni"))
                .withRecipeId2(this.modLoc("crafting/book_of_binding_bound_djinni"))
                .build();

        helper.page("book_of_binding_afrit_recipe");
        var bookOfBindingAfritRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_afrit"))
                .withRecipeId2(this.modLoc("crafting/book_of_binding_bound_afrit"))
                .build();

        helper.page("book_of_binding_marid_recipe");
        var bookOfBindingMaritRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/book_of_binding_marid"))
                .withRecipeId2(this.modLoc("crafting/book_of_binding_bound_marid"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        purifiedInkRecipe,
                        bookOfBindingFoliotRecipe,
                        bookOfBindingBoundFoliotRecipe,
                        bookOfBindingDjinniRecipe,
                        bookOfBindingAfritRecipe,
                        bookOfBindingMaritRecipe
                );
    }

    private BookEntryModel.Builder makeRitualEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("first_ritual");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("bowl_placement");
        var bowlPlacementImage = BookImagePageModel.builder()
                .withImages(modLoc("textures/gui/book/bowl_placement.png"))
                .withBorder(true)
                .build();

        helper.page("bowl_text");
        var bowlText = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        //TODO: actual ritual recipe page
        helper.page("ritual_recipe");
        var ritualRecipe = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_crusher"))
                .withText(helper.pageText())
                .build();

        helper.page("start_ritual");
        var startRitualText = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        bowlPlacementImage,
                        bowlText,
                        ritualRecipe,
                        startRitualText
                );
    }

    private BookEntryModel.Builder makeNextStepsEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("next_steps");

        helper.page("text");
        var text = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.SOUL_GEM_ITEM.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(text);
    }

    private BookCategoryModel.Builder makeAdvancedCategory(BookLangHelper helper) {
        helper.category("advanced");

        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
                "_____________________",
                "_____________________",
                "___________c_________", //c=chalks
                "_____________________",
                "_____________________",
                "_____________________",
                "_____________________",
                "_____________________",
                "_____________________",
                "_____________________",
                "_____________________",
                "_____________________",
                "_____________________"
        );


        var chalksEntry = this.makeChalksEntry(helper, entryHelper, 'c');

        return BookCategoryModel.builder()
                .withId(this.modLoc(helper.category))
                .withName(helper.categoryName())
                .withIcon(OccultismItems.OTHERWORLD_GOGGLES.getId().toString())
                .withEntry(chalksEntry.build());
    }

    private BookEntryModel.Builder makeChalksEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("chalks");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("impure_gold_chalk_recipe");
        var impureGoldChalkRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/chalk_gold_impure"))
                .build();

        helper.page("gold_chalk_recipe");
        var goldChalkRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_gold"))
                .build();

        helper.page("impure_purple_chalk_recipe");
        var impurePurpleChalkRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/chalk_purple_impure"))
                .build();

        helper.page("purple_chalk_recipe");
        var purpleChalkRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_purple"))
                .build();

        helper.page("impure_red_chalk_recipe");
        var impureRedChalkRecipe = BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/chalk_red_impure"))
                .build();

        helper.page("red_chalk_recipe");
        var redChalkRecipe = BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/chalk_red"))
                .build();

        helper.page("afrit_essence");
        var afritEssenceSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.AFRIT_ESSENCE.get()))
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.CHALK_GOLD.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        impureGoldChalkRecipe,
                        goldChalkRecipe,
                        impurePurpleChalkRecipe,
                        purpleChalkRecipe,
                        impureRedChalkRecipe,
                        redChalkRecipe,
                        afritEssenceSpotlight
                );
    }

    private BookEntryModel.Builder makeCraftOtherworldGogglesEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_otherworld_goggles");

        helper.page("goggles_spotlight");
        var gogglesSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.OTHERWORLD_GOGGLES.get()))
                .withText(helper.pageText())
                .build();

        helper.page("lenses_spotlight");
        var lensesSpotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.LENSES.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/otherworld_goggles"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.OTHERWORLD_GOGGLES.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        gogglesSpotlight,
                        lensesSpotlight,
                        ritual
                );
    }

    private BookCategoryModel.Builder makePentaclesCategory(BookLangHelper helper) {
        helper.category("pentacles");

        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
                "___________________________",
                "___________________________",
                "__p___a___b___c___d___e___f", //paraphernalia, summon foliot, summon djinni, summon wild afrit, summon afrit, summon marid, summon wild greater spirit
                "___________________________",
                "___________________________",
                "__o_______g___h___i_________", //overview, possess foliot, possess djinni, possess afrit
                "___________________________",
                "___________________________",
                "__u___j___k___l___m________" //uses of chalks, craft foliot, craft djinni, craft afrit, craft marid
        );

        var overview = this.makePentaclesOverviewEntry(helper, entryHelper, 'o');
        var paraphernalia = this.makeParaphernaliaEntry(helper, entryHelper, 'p');
        paraphernalia.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var chalkUses = this.makeChalkUsesEntry(helper, entryHelper, 'u');
        chalkUses.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());

        var summonFoliot = this.makeSummonFoliotEntry(helper, entryHelper, 'a');
        summonFoliot.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var summonDjinni = this.makeSummonDjinniEntry(helper, entryHelper, 'b');
        summonDjinni.withParent(BookEntryParentModel.builder().withEntryId(summonFoliot.id).build());
        var summonWildAfrit = this.makeSummonWildAfritEntry(helper, entryHelper, 'c');
        summonWildAfrit.withParent(BookEntryParentModel.builder().withEntryId(summonDjinni.id).build());
        var summonAfrit = this.makeSummonAfritEntry(helper, entryHelper, 'd');
        summonAfrit.withParent(BookEntryParentModel.builder().withEntryId(summonWildAfrit.id).build());
        var summonMarid = this.makeSummonMaridEntry(helper, entryHelper, 'e');
        summonMarid.withParent(BookEntryParentModel.builder().withEntryId(summonAfrit.id).build());
        var summonWildGreaterSpirit = this.makeSummonWildGreaterSpiritEntry(helper, entryHelper, 'f');
        summonWildGreaterSpirit.withParent(BookEntryParentModel.builder().withEntryId(summonMarid.id).build());

        var possessFoliot = this.makePossessFoliotEntry(helper, entryHelper, 'g');
        possessFoliot.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var possessDjinni = this.makePossessDjinniEntry(helper, entryHelper, 'h');
        possessDjinni.withParent(BookEntryParentModel.builder().withEntryId(possessFoliot.id).build());
        var possessAfrit = this.makePossessAfritEntry(helper, entryHelper, 'i');
        possessAfrit.withParent(BookEntryParentModel.builder().withEntryId(possessDjinni.id).build());

        var craftFoliot = this.makeCraftFoliotEntry(helper, entryHelper, 'j');
        craftFoliot.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var craftDjinni = this.makeCraftDjinniEntry(helper, entryHelper, 'k');
        craftDjinni.withParent(BookEntryParentModel.builder().withEntryId(craftFoliot.id).build());
        var craftAfrit = this.makeCraftAfritEntry(helper, entryHelper, 'l');
        craftAfrit.withParent(BookEntryParentModel.builder().withEntryId(craftDjinni.id).build());
        var craftMarid = this.makeCraftMaridEntry(helper, entryHelper, 'm');
        craftMarid.withParent(BookEntryParentModel.builder().withEntryId(craftAfrit.id).build());

        return BookCategoryModel.builder()
                .withId(this.modLoc("pentacles"))
                .withName(helper.categoryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withEntries(
                        overview.build(),
                        paraphernalia.build(),
                        chalkUses.build(),

                        summonFoliot.build(),
                        summonDjinni.build(),
                        summonWildAfrit.build(),
                        summonAfrit.build(),
                        summonMarid.build(),
                        summonWildGreaterSpirit.build(),

                        possessFoliot.build(),
                        possessDjinni.build(),
                        possessAfrit.build(),

                        craftFoliot.build(),
                        craftDjinni.build(),
                        craftAfrit.build(),
                        craftMarid.build()
                );
    }

    private BookEntryModel.Builder makePentaclesOverviewEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("pentacles_overview");

        helper.page("intro1");
        var intro1 = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("intro3");
        var intro3 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("intro4");
        var intro4 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        //exact copy found in first ritual entry
        helper.page("bowl_placement");
        var bowlPlacementImage = BookImagePageModel.builder()
                .withImages(this.modLoc("textures/gui/book/bowl_placement.png"))
                .withBorder(true)
                .build();

        //exact copy found in first ritual entry
        helper.page("bowl_text");
        var bowlText = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("summoning_pentacles");
        var summoningPentacles = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("infusion_pentacles");
        var infusionPentacles = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("possession_pentacles");
        var possessionPentacles = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro1,
                        intro2,
                        intro3,
                        intro4,
                        bowlPlacementImage,
                        bowlText,
                        summoningPentacles,
                        infusionPentacles,
                        possessionPentacles
                );
    }

    private BookEntryModel.Builder makeParaphernaliaEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("paraphernalia");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("candle");
        var candle = BookSpotlightPageModel.builder()
                .withText(helper.pageText())
                .withItem(Ingredient.of(OccultismBlocks.CANDLE_WHITE.get()))
                .build();

        helper.page("crystal");
        var crystal = BookSpotlightPageModel.builder()
                .withText(helper.pageText())
                .withItem(Ingredient.of(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()))
                .build();

        helper.page("skeleton_skull");
        var skeletonSkull = BookSpotlightPageModel.builder()
                .withText(helper.pageText())
                .withItem(Ingredient.of(Blocks.SKELETON_SKULL))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.BLOCKS.getKey(Blocks.SKELETON_SKULL).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        candle,
                        crystal,
                        skeletonSkull
                );
    }

    private BookEntryModel.Builder makeChalkUsesEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("chalk_uses");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("white_chalk");
        var whiteChalk = BookSpotlightPageModel.builder()
                .withText(helper.pageText())
                .withItem(Ingredient.of(OccultismItems.CHALK_WHITE.get()))
                .build();

        helper.page("white_chalk_uses");
        var whiteChalkUses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("white_chalk_uses2");
        var whiteChalkUses2 = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("golden_chalk");
        var goldChalk = BookSpotlightPageModel.builder()
                .withText(helper.pageText())
                .withItem(Ingredient.of(OccultismItems.CHALK_GOLD.get()))
                .build();

        helper.page("golden_chalk_uses");
        var goldChalkUses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("purple_chalk");
        var purpleChalk = BookSpotlightPageModel.builder()
                .withText(helper.pageText())
                .withItem(Ingredient.of(OccultismItems.CHALK_PURPLE.get()))
                .build();

        helper.page("purple_chalk_uses");
        var purpleChalkUses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("red_chalk");
        var redChalk = BookSpotlightPageModel.builder()
                .withText(helper.pageText())
                .withItem(Ingredient.of(OccultismItems.CHALK_RED.get()))
                .build();

        helper.page("red_chalk_uses");
        var redChalkUses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.CHALK_PURPLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        intro2,
                        whiteChalk,
                        whiteChalkUses,
                        whiteChalkUses2,
                        goldChalk,
                        goldChalkUses,
                        purpleChalk,
                        purpleChalkUses,
                        redChalk,
                        redChalkUses
                );
    }

    private BookEntryModel.Builder makeSummonFoliotEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_foliot");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_foliot"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makePossessFoliotEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("possess_foliot");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("possess_foliot"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makeCraftFoliotEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_foliot");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("craft_foliot"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makeSummonDjinniEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_djinni");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_djinni"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makePossessDjinniEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("possess_djinni");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("possess_djinni"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makeCraftDjinniEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_djinni");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("craft_djinni"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makeSummonAfritEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_afrit");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_afrit"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makeSummonWildAfritEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_wild_afrit");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_wild_afrit"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makePossessAfritEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("possess_afrit");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("possess_afrit"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makeCraftAfritEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_afrit");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("craft_djinni"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makeSummonMaridEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_marid");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_marid"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makeCraftMaridEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_marid");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("craft_marid"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookEntryModel.Builder makeSummonWildGreaterSpiritEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_wild_greater_spirit");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("multiblock");
        var multiblock = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_wild_greater_spirit"))
                .build();

        helper.page("uses");
        var uses = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        multiblock,
                        uses
                );
    }

    private BookCategoryModel.Builder makeRitualsCategory(BookLangHelper helper) {
        helper.category("rituals");

        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
                "___________________________",
                "___________________________",
                "___________________________",
                "____________________p___s__",
                "___________________________",
                "___________________________",
                "___________________________",
                "________o___i___k__________",
                "___________________________",
                "___________________________",
                "___________________________",
                "____________________c___f__",
                "___________________________",
                "___________________________",
                "___________________________"
        );

        var ritualOverview = this.makeRitualOverviewEntry(helper, entryHelper, 'o');
        var itemUse = this.makeItemUseEntry(helper, entryHelper, 'i');
        itemUse.withParent(BookEntryParentModel.builder().withEntryId(ritualOverview.id).build());
        var sacrifice = this.makeSacrificeEntry(helper, entryHelper, 'k');
        sacrifice.withParent(BookEntryParentModel.builder().withEntryId(itemUse.id).build());

        var summoning = this.makeSummoningRitualsSubcategoryEntry(helper, entryHelper, 's');
        summoning.withParent(BookEntryParentModel.builder().withEntryId(sacrifice.id).build());
        var possession = this.makePossessionRitualsSubcategoryEntry(helper, entryHelper, 'p');
        possession.withParent(BookEntryParentModel.builder().withEntryId(sacrifice.id).build());
        var crafting = this.makeCraftingRitualsSubcategoryEntry(helper, entryHelper, 'c');
        crafting.withParent(BookEntryParentModel.builder().withEntryId(sacrifice.id).build());
        var familiars = this.makeFamiliarRitualsSubcategoryEntry(helper, entryHelper, 'f');
        familiars.withParent(BookEntryParentModel.builder().withEntryId(sacrifice.id).build());

        return BookCategoryModel.builder()
                .withId(this.modLoc(helper.category))
                .withName(helper.categoryName())
                .withIcon("occultism:textures/gui/book/robe.png")
                .withEntries(
                        ritualOverview.build(),
                        itemUse.build(),
                        sacrifice.build(),
                        summoning.build(),
                        possession.build(),
                        crafting.build(),
                        familiars.build()
                );
    }

    private BookEntryModel.Builder makeRitualOverviewEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("overview");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("steps");
        var steps = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("additional_requirements");
        var additional_requirements = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/robe.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        steps,
                        additional_requirements
                );
    }

    private BookEntryModel.Builder makeSacrificeEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("sacrifice");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.IRON_SWORD).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro
                );
    }

    private BookEntryModel.Builder makeItemUseEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("item_use");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.FLINT_AND_STEEL).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro
                );
    }

    private BookEntryModel.Builder makeSummoningRitualsSubcategoryEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summoning_rituals");

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/summoning.png")
                .withCategoryToOpen(this.modLoc("summoning_rituals"))
                .withLocation(entryHelper.get(icon));
    }

    private BookEntryModel.Builder makePossessionRitualsSubcategoryEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("possession_rituals");

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/possession.png")
                .withCategoryToOpen(this.modLoc("possession_rituals"))
                .withLocation(entryHelper.get(icon));
    }

    private BookEntryModel.Builder makeCraftingRitualsSubcategoryEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("crafting_rituals");

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/infusion.png")
                .withCategoryToOpen(this.modLoc("crafting_rituals"))
                .withLocation(entryHelper.get(icon));
    }

    private BookEntryModel.Builder makeFamiliarRitualsSubcategoryEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_rituals");

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/parrot.png")
                .withCategoryToOpen(this.modLoc("familiar_rituals"))
                .withLocation(entryHelper.get(icon));
    }

    private BookEntryModel.Builder makeReturnToRitualsEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("return_to_rituals");

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/robe.png")
                .withCategoryToOpen(this.modLoc("rituals"))
                .withLocation(entryHelper.get(icon));
    }

    private BookCategoryModel.Builder makeSummoningRitualsSubcategory(BookLangHelper helper) {
        helper.category("summoning_rituals");

        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
                "___________b___l______",
                "______________________",
                "_________c_d_h_k______",
                "______________________",
                "___r_o________________",
                "______________________",
                "_________1_e_i_a_m___",
                "______________________",
                "_________2_f_j________",
                "______________________",
                "_________3_g__________",
                "______________________",
                "_________4____________"
        );

        var overview = this.makeSummoningRitualsOverviewEntry(helper, entryHelper, 'o');
        var returnToRituals = this.makeReturnToRitualsEntry(helper, entryHelper, 'r');
        returnToRituals.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        returnToRituals.withCondition(BookTrueConditionModel.builder().build());

        var summonT1Crusher = this.makeSummonCrusherT1Entry(helper, entryHelper, '1');
        summonT1Crusher.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var summonT2Crusher = this.makeSummonCrusherT2Entry(helper, entryHelper, '2');
        summonT2Crusher.withParent(BookEntryParentModel.builder().withEntryId(summonT1Crusher.id).build());
        var summonT3Crusher = this.makeSummonCrusherT3Entry(helper, entryHelper, '3');
        summonT3Crusher.withParent(BookEntryParentModel.builder().withEntryId(summonT2Crusher.id).build());
        var summonT4Crusher = this.makeSummonCrusherT4Entry(helper, entryHelper, '4');
        summonT4Crusher.withParent(BookEntryParentModel.builder().withEntryId(summonT3Crusher.id).build());

        var summonLumberjack = this.makeSummonLumberjackEntry(helper, entryHelper, 'c');
        summonLumberjack.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());

        var summonTransportItems = this.makeSummonTransportItemsEntry(helper, entryHelper, 'd');
        summonTransportItems.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var summonCleaner = this.makeSummonCleanerEntry(helper, entryHelper, 'b');
        summonCleaner.withParent(BookEntryParentModel.builder().withEntryId(summonTransportItems.id).build());
        var summonManageMachine = this.makeSummonManageMachineEntry(helper, entryHelper, 'h');
        summonManageMachine.withParent(BookEntryParentModel.builder().withEntryId(summonTransportItems.id).build());

        var tradeSpirits = this.makeTradeSpiritsEntry(helper, entryHelper, 'e');
        tradeSpirits.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var summonOtherworldSaplingTrader = this.makeSummonOtherworldSaplingTraderEntry(helper, entryHelper, 'f');
        summonOtherworldSaplingTrader.withParent(BookEntryParentModel.builder().withEntryId(tradeSpirits.id).build());
        var summonOtherstoneTrader = this.makeSummonOtherstoneTraderEntry(helper, entryHelper, 'g');
        summonOtherstoneTrader.withParent(BookEntryParentModel.builder().withEntryId(summonOtherworldSaplingTrader.id).build());

        var summonWildParrot = this.makeSummonWildParrotEntry(helper, entryHelper, 'i');
        summonWildParrot.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var summonWildOtherworldBird = this.makeSummonWildOtherworldBirdEntry(helper, entryHelper, 'j');
        summonWildOtherworldBird.withParent(BookEntryParentModel.builder().withEntryId(summonWildParrot.id).build());

        var weatherMagic = this.makeWeatherMagicEntry(helper, entryHelper, 'k');
        weatherMagic.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var timeMagic = this.makeTimeMagicEntry(helper, entryHelper, 'l');
        timeMagic.withParent(BookEntryParentModel.builder().withEntryId(weatherMagic.id).build());

        var afritEssence = this.makeAfritEssenceEntry(helper, entryHelper, 'a');
        afritEssence.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());

        var witherSkull = this.makeWitherSkullEntry(helper, entryHelper, 'm');
        witherSkull.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());

        return BookCategoryModel.builder()
                .withId(this.modLoc(helper.category))
                .withName(helper.categoryName())
                .withIcon("occultism:textures/gui/book/summoning.png")
                .withShowCategoryButton(false)
                .withEntries(
                        overview.build(),
                        returnToRituals.build(),
                        afritEssence.build(),
                        summonCleaner.build(),
                        summonT1Crusher.build(),
                        summonT2Crusher.build(),
                        summonT3Crusher.build(),
                        summonT4Crusher.build(),
                        summonLumberjack.build(),
                        summonManageMachine.build(),
                        summonTransportItems.build(),
                        tradeSpirits.build(),
                        summonOtherstoneTrader.build(),
                        summonOtherworldSaplingTrader.build(),
                        summonWildOtherworldBird.build(),
                        summonWildParrot.build(),
                        timeMagic.build(),
                        weatherMagic.build(),
                        witherSkull.build(),
                        afritEssence.build()
                );
    }

    private BookEntryModel.Builder makeSummoningRitualsOverviewEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("overview");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/summoning.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro
                );
    }

    private BookEntryModel.Builder makeAfritEssenceEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("afrit_essence");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_wild_afrit"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.AFRIT_ESSENCE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        ritual
                );
    }

    private BookEntryModel.Builder makeSummonCrusherT1Entry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_crusher_t1");

        helper.page("about_crushers");
        var aboutCrushers = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("automation");
        var automation = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_crusher"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.COPPER_DUST.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        aboutCrushers,
                        automation,
                        intro,
                        ritual
                );
    }

    private BookEntryModel.Builder makeSummonCrusherT2Entry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_crusher_t2");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_djinni_crusher"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.IRON_DUST.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        ritual
                );
    }

    private BookEntryModel.Builder makeSummonCrusherT3Entry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_crusher_t3");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_afrit_crusher"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.SILVER_DUST.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        ritual
                );
    }

    private BookEntryModel.Builder makeSummonCrusherT4Entry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_crusher_t4");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_marid_crusher"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.GOLD_DUST.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        ritual
                );
    }

    private BookEntryModel.Builder makeSummonLumberjackEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_lumberjack");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_lumberjack"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.BRUSH.getId().toString())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.IRON_AXE).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        ritual
                );
    }

    private BookEntryModel.Builder makeSummonTransportItemsEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_transport_items");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();


        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_transport_items"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.MINECART).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        intro2,
                        ritual
                );
    }

    private BookEntryModel.Builder makeSummonCleanerEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_cleaner");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_cleaner"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.BRUSH.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        intro2,
                        ritual
                );
    }

    private BookEntryModel.Builder makeSummonManageMachineEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_manage_machine");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("tutorial");
        var tutorial = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("tutorial2");
        var tutorial2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_djinni_manage_machine"))
                .build();


        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.LEVER).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        tutorial,
                        tutorial2,
                        ritual
                );
    }

    private BookEntryModel.Builder makeTradeSpiritsEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("trade_spirits");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("intro2");
        var intro2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/cash.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        intro2
                );
    }

    private BookEntryModel.Builder makeSummonOtherstoneTraderEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_otherstone_trader");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("trade");
        var trade = BookSpiritTradeRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_trade/stone_to_otherstone"))
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_otherstone_trader"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.OTHERSTONE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        trade,
                        ritual
                );
    }

    private BookEntryModel.Builder makeSummonOtherworldSaplingTraderEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_otherworld_sapling_trader");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("trade");
        var trade = BookSpiritTradeRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_trade/otherworld_sapling"))
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_foliot_sapling_trader"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.OTHERWORLD_SAPLING.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        trade,
                        ritual
                );
    }

    private BookEntryModel.Builder makeSummonWildParrotEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_wild_parrot");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
                .withEntityId("minecraft:parrot")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_wild_parrot"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        helper.page("description2");
        var description2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();


        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/parrot.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description,
                        description2
                );
    }

    private BookEntryModel.Builder makeSummonWildOtherworldBirdEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("summon_wild_otherworld_bird");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
               .withEntityId("occultism:otherworld_bird")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_wild_otherworld_bird"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/otherworld_bird.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeWeatherMagicEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("weather_magic");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual_clear");
        var ritualClear = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_djinni_clear_weather"))
                .build();

        helper.page("ritual_rain");
        var ritualRain = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_afrit_rain_weather"))
                .build();

        helper.page("ritual_thunder");
        var ritualThunder = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_afrit_thunder_weather"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.WHEAT).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        ritualClear,
                        ritualRain,
                        ritualThunder
                );
    }

    private BookEntryModel.Builder makeTimeMagicEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("time_magic");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual_day");
        var ritualDay = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_djinni_day_time"))
                .build();

        helper.page("ritual_night");
        var ritualNight = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_djinni_night_time"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.CLOCK).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        ritualDay,
                        ritualNight
                );
    }

    private BookEntryModel.Builder makeWitherSkullEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("wither_skull");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/summon_wild_hunt"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.WITHER_SKELETON_SKULL).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        ritual
                );
    }

    private BookCategoryModel.Builder makeCraftingRitualsSubcategory(BookLangHelper helper) {
        helper.category("crafting_rituals");

        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
                "___________________________",
                "_______b_e_x_c_____________",
                "___________________________",
                "_______d_____h_____________",
                "___________________________",
                "___9_0_____________________",
                "___________________________",
                "_______f_a____g____________",
                "___________________________",
                "_________n_m_o_____________",
                "___________________________",
                "___________i_j_k_l_________",
                "___________________________"
        );

        var overview = this.makeCraftingRitualsOverviewEntry(helper, entryHelper, '0');
        var returnToRituals = this.makeReturnToRitualsEntry(helper, entryHelper, '9');
        returnToRituals.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        returnToRituals.withCondition(BookTrueConditionModel.builder().build());



         var craftInfusedPickaxe = this.makeCraftInfusedPickaxeEntry(helper, entryHelper, 'd');
        craftInfusedPickaxe.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var craftDimensionalMineshaft = this.makeCraftDimensionalMineshaftEntry(helper, entryHelper, 'b');
        craftDimensionalMineshaft.withParent(BookEntryParentModel.builder().withEntryId(craftInfusedPickaxe.id).build());
        var craftFoliotMiner = this.makeCraftFoliotMinerEntry(helper, entryHelper, 'e');
        craftFoliotMiner.withParent(BookEntryParentModel.builder().withEntryId(craftDimensionalMineshaft.id).build());
        var craftDjinniMiner = this.makeCraftDjinniMinerEntry(helper, entryHelper, 'x');
        craftDjinniMiner.withParent(BookEntryParentModel.builder().withEntryId(craftFoliotMiner.id).build());

        var craftDimensionalMatrix = this.makeCraftDimensionalMatrixEntry(helper, entryHelper, 'a');
        craftDimensionalMatrix.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var craftStorageControllerBase = this.makeCraftStorageControllerBaseEntry(helper, entryHelper, 'n');
        craftStorageControllerBase.withParent(BookEntryParentModel.builder().withEntryId(craftDimensionalMatrix.id).build());
        var craftStabilizerTier1 = this.makeCraftStabilizerTier1Entry(helper, entryHelper, 'i');
        craftStabilizerTier1.withParent(BookEntryParentModel.builder().withEntryId(craftStorageControllerBase.id).build());
        var craftStabilizerTier2 = this.makeCraftStabilizerTier2Entry(helper, entryHelper, 'j');
        craftStabilizerTier2.withParent(BookEntryParentModel.builder().withEntryId(craftStabilizerTier1.id).build());
        var craftStabilizerTier3 = this.makeCraftStabilizerTier3Entry(helper, entryHelper, 'k');
        craftStabilizerTier3.withParent(BookEntryParentModel.builder().withEntryId(craftStabilizerTier2.id).build());
        var craftStabilizerTier4 = this.makeCraftStabilizerTier4Entry(helper, entryHelper, 'l');
        craftStabilizerTier4.withParent(BookEntryParentModel.builder().withEntryId(craftStabilizerTier3.id).build());

        var craftStableWormhole = this.makeCraftStableWormholeEntry(helper, entryHelper, 'm');
        craftStableWormhole.withParent(BookEntryParentModel.builder().withEntryId(craftStorageControllerBase.id).build());
        var craftStorageRemote = this.makeCraftStorageRemoteEntry(helper, entryHelper, 'o');
        craftStorageRemote.withParent(BookEntryParentModel.builder().withEntryId(craftStableWormhole.id).build());

        var craftOtherworldGoggles = this.makeCraftOtherworldGogglesEntry(helper, entryHelper, 'f');
        craftOtherworldGoggles.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());

        var craftSatchel = this.makeCraftSatchelEntry(helper, entryHelper, 'g');
        craftSatchel.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());

        var craftSoulGem = this.makeCraftSoulGemEntry(helper, entryHelper, 'h');
        craftSoulGem.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var craftFamiliarRing = this.makeCraftFamiliarRingEntry(helper, entryHelper, 'c');
        craftFamiliarRing.withParent(BookEntryParentModel.builder().withEntryId(craftSoulGem.id).build());

        return BookCategoryModel.builder()
                .withId(this.modLoc(helper.category))
                .withName(helper.categoryName())
                .withIcon("occultism:textures/gui/book/infusion.png")
                .withShowCategoryButton(false)
                .withEntries(
                        overview.build(),
                        returnToRituals.build(),
                        craftDimensionalMatrix.build(),
                        craftDimensionalMineshaft.build(),
                        craftInfusedPickaxe.build(),
                        craftFoliotMiner.build(),
                        craftDjinniMiner.build(),
                        craftOtherworldGoggles.build(),
                        craftSatchel.build(),
                        craftSoulGem.build(),
                        craftFamiliarRing.build(),
                        craftStabilizerTier1.build(),
                        craftStabilizerTier2.build(),
                        craftStabilizerTier3.build(),
                        craftStabilizerTier4.build(),
                        craftStableWormhole.build(),
                        craftStorageControllerBase.build(),
                        craftStorageRemote.build()
                );
    }

    private BookEntryModel.Builder makeCraftingRitualsOverviewEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("overview");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/infusion.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro
                );
    }

    private BookEntryModel.Builder makeCraftDimensionalMatrixEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_dimensional_matrix");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.DIMENSIONAL_MATRIX.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_dimensional_matrix"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.DIMENSIONAL_MATRIX.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftDimensionalMineshaftEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_dimensional_mineshaft");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.DIMENSIONAL_MINESHAFT.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_dimensional_mineshaft"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.DIMENSIONAL_MINESHAFT.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeCraftInfusedPickaxeEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_infused_pickaxe");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.INFUSED_PICKAXE.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_infused_pickaxe"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.INFUSED_PICKAXE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftStorageControllerBaseEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_storage_controller_base");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_CONTROLLER_BASE.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_storage_controller_base"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.STORAGE_CONTROLLER_BASE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftStabilizerTier1Entry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_stabilizer_tier1");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER1.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_stabilizer_tier1"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.STORAGE_STABILIZER_TIER1.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftStabilizerTier2Entry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_stabilizer_tier2");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER2.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_stabilizer_tier2"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.STORAGE_STABILIZER_TIER2.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftStabilizerTier3Entry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_stabilizer_tier3");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER3.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_stabilizer_tier3"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.STORAGE_STABILIZER_TIER3.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftStabilizerTier4Entry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_stabilizer_tier4");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STORAGE_STABILIZER_TIER4.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_stabilizer_tier4"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.STORAGE_STABILIZER_TIER4.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftStableWormholeEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_stable_wormhole");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismBlocks.STABLE_WORMHOLE.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_stable_wormhole"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismBlocks.STABLE_WORMHOLE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftStorageRemoteEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_storage_remote");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.STORAGE_REMOTE.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_storage_remote"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.STORAGE_REMOTE.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftFoliotMinerEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_foliot_miner");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_miner_foliot_unspecialized"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftDjinniMinerEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_djinni_miner");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.MINER_DJINNI_ORES.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_miner_djinni_ores"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.MINER_DJINNI_ORES.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftSatchelEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_satchel");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.SATCHEL.get()))
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_satchel"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.SATCHEL.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftSoulGemEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_soul_gem");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.SOUL_GEM_ITEM.get()))
                .withText(helper.pageText())
                .build();

        helper.page("usage");
        var usage = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_soul_gem"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.SOUL_GEM_ITEM.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        usage,
                        ritual
                );
    }

    private BookEntryModel.Builder makeCraftFamiliarRingEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_familiar_ring");

        helper.page("spotlight");
        var spotlight = BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.FAMILIAR_RING.get()))
                .withText(helper.pageText())
                .build();

        helper.page("usage");
        var usage = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/craft_familiar_ring"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.FAMILIAR_RING.getId().toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        spotlight,
                        usage,
                        ritual
                );
    }

    private BookCategoryModel.Builder makePossessionRitualsSubcategory(BookLangHelper helper) {
        helper.category("possession_rituals");

        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
                "___________________________",
                "___________________________",
                "___________________________",
                "_______D_E_A_______________",
                "___r_o_____________________",
                "_______F_G_H_______________",
                "___________________________",
                "___________________________",
                "___________________________"
        );

        var overview = this.makePossessionRitualsOverviewEntry(helper, entryHelper, 'o');
        var returnToRituals = this.makeReturnToRitualsEntry(helper, entryHelper, 'r');
        returnToRituals.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        returnToRituals.withCondition(BookTrueConditionModel.builder().build());

        var possessEnderman = this.makePossessEndermanEntry(helper, entryHelper, 'D');
        possessEnderman.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var possessEndermite = this.makePossessEndermiteEntry(helper, entryHelper, 'E');
        possessEndermite.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var possessGhast = this.makePossessGhastEntry(helper, entryHelper, 'F');
        possessGhast.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var possessSkeleton = this.makePossessSkeletonEntry(helper, entryHelper, 'G');
        possessSkeleton.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());

        helper.category("summoning_rituals"); //re-use the entries from the summoning rituals category
        var possessWitherSkeleton = this.makeWitherSkullEntry(helper, entryHelper, 'H');
        possessWitherSkeleton.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var afritEssence = this.makeAfritEssenceEntry(helper, entryHelper, 'A');
        afritEssence.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        helper.category("possession_rituals");

        return BookCategoryModel.builder()
                .withId(this.modLoc(helper.category))
                .withName(helper.categoryName())
                .withIcon("occultism:textures/gui/book/possession.png")
                .withShowCategoryButton(false)
                .withEntries(
                        overview.build(),
                        returnToRituals.build(),
                        possessEnderman.build(),
                        possessEndermite.build(),
                        possessGhast.build(),
                        possessSkeleton.build(),
                        possessWitherSkeleton.build(),
                        afritEssence.build()
                );
    }

    private BookEntryModel.Builder makePossessionRitualsOverviewEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("overview");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/possession.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro
                );
    }

    private BookEntryModel.Builder makePossessEndermanEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("possess_enderman");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
               .withEntityId("occultism:possessed_enderman")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/possess_enderman"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.ENDER_PEARL).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makePossessEndermiteEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("possess_endermite");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
               .withEntityId("occultism:possessed_endermite")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/possess_endermite"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.BLOCKS.getKey(Blocks.END_STONE).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makePossessGhastEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("possess_ghast");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
               .withEntityId("occultism:possessed_ghast")
                .withScale(0.5f)
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/possess_ghast"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.GHAST_TEAR).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makePossessSkeletonEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("possess_skeleton");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
               .withEntityId("occultism:possessed_skeleton")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/possess_skeleton"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(ForgeRegistries.ITEMS.getKey(Items.SKELETON_SKULL).toString())
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookCategoryModel.Builder makeFamiliarRitualsSubcategory(BookLangHelper helper) {
        helper.category("familiar_rituals");

        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
                "________R_T_V_X____________",
                "___________________________",
                "_______Q_S_U_W_____________",
                "___________________________",
                "___r_o_________Y___________",
                "___________________________",
                "_______I_K_M_O_____________",
                "___________________________",
                "________J_L_N_P____________"
        );

        var overview = this.makeFamiliarsRitualsOverviewEntry(helper, entryHelper, 'o');
        var returnToRituals = this.makeReturnToRitualsEntry(helper, entryHelper, 'r');
        returnToRituals.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        returnToRituals.withCondition(BookTrueConditionModel.builder().build());

        var familiarBat = this.makeFamiliarBatEntry(helper, entryHelper, 'I');
        familiarBat.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarBeaver = this.makeFamiliarBeaverEntry(helper, entryHelper, 'J');
        familiarBeaver.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarBeholder = this.makeFamiliarBeholderEntry(helper, entryHelper, 'K');
        familiarBeholder.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarBlacksmith = this.makeFamiliarBlacksmithEntry(helper, entryHelper, 'L');
        familiarBlacksmith.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarChimera = this.makeFamiliarChimeraEntry(helper, entryHelper, 'M');
        familiarChimera.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarCthulhu = this.makeFamiliarCthulhuEntry(helper, entryHelper, 'N');
        familiarCthulhu.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());
        var familiarDeer = this.makeFamiliarDeerEntry(helper, entryHelper, 'O');
        var familiarDevil = this.makeFamiliarDevilEntry(helper, entryHelper, 'P');
        var familiarDragon = this.makeFamiliarDragonEntry(helper, entryHelper, 'Q');
        var familiarFairy = this.makeFamiliarFairyEntry(helper, entryHelper, 'R');
        var familiarGreedy = this.makeFamiliarGreedyEntry(helper, entryHelper, 'S');
        var familiarGuardian = this.makeFamiliarGuardianEntry(helper, entryHelper, 'T');
//        var familiarHeadlessRatman = this.makeFamiliarHeadlessRatmanEntry(helper, entryHelper, 'U');
//        var familiarMummy = this.makeFamiliarMummyEntry(helper, entryHelper, 'V');
//        var familiarOtherworldBird = this.makeFamiliarOtherworldBirdEntry(helper, entryHelper, 'W');
//        var familiarParrot = this.makeFamiliarParrotEntry(helper, entryHelper, 'X');
        var familiarShubNiggurath = this.makeFamiliarShubNiggurathEntry(helper, entryHelper, 'Y');
        familiarShubNiggurath.withParent(BookEntryParentModel.builder().withEntryId(overview.id).build());


        return BookCategoryModel.builder()
                .withId(this.modLoc(helper.category))
                .withName(helper.categoryName())
                .withIcon("occultism:textures/gui/book/parrot.png")
                .withShowCategoryButton(false)
                .withEntries(
                        overview.build(),
                        returnToRituals.build(),
                        familiarBat.build(),
                        familiarBeaver.build(),
                        familiarBeholder.build(),
                        familiarBlacksmith.build(),
                        familiarChimera.build(),
                        familiarCthulhu.build(),
                        familiarDeer.build(),
                        familiarDevil.build(),
                        familiarDragon.build(),
                        familiarShubNiggurath.build()
                );
    }

    private BookEntryModel.Builder makeFamiliarsRitualsOverviewEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("overview");

        helper.page("intro");
        var intro = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("ring");
        var ring = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("trading");
        var trading = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/parrot.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        intro,
                        ring,
                        trading
                );
    }

    private BookEntryModel.Builder makeFamiliarBatEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_bat");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
               .withEntityId("occultism:bat_familiar")
                .withText(helper.pageText())
                .withScale(0.7f)
                .withOffset(0.3f)
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_bat"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/bat_familiar.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarBeaverEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_beaver");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
               .withEntityId("occultism:beaver_familiar")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_beaver"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_beaver.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarBeholderEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_beholder");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
               .withEntityId("occultism:beholder_familiar")
                .withText(helper.pageText())
                .withScale(0.7f)
                .withOffset(0.3f)
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_beholder"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_beholder.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarBlacksmithEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_blacksmith");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
               .withEntityId("occultism:blacksmith_familiar")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_blacksmith"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("description2");
        var description2 = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_blacksmith.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description,
                        description2
                );
    }

    private BookEntryModel.Builder makeFamiliarChimeraEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_chimera");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
               .withEntityId("occultism:chimera_familiar")
                .withText(helper.pageText())
                .withScale(0.7f)
                .withOffset(0.3f)
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_chimera"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("description2");
        var description2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_chimera.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description,
                        description2
                );
    }

    private BookEntryModel.Builder makeFamiliarCthulhuEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_cthulhu");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
               .withEntityId("occultism:cthulhu_familiar")
                .withText(helper.pageText())
                .withScale(0.5f)
                .withOffset(0.3f)
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_cthulhu"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_cthulhu.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarDeerEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_deer");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
               .withEntityId("occultism:deer_familiar")
                .withText(helper.pageText())
                .withScale(0.7f)
                .withOffset(0.3f)
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_deer"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_deer.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarDevilEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_devil");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
               .withEntityId("occultism:devil_familiar")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_devil"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_devil.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarDragonEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_dragon");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
               .withEntityId("occultism:dragon_familiar")
                .withText(helper.pageText())
                .withScale(0.7f)
                .withOffset(0.3f)
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_dragon"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_dragon.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarFairyEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_fairy");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
               .withEntityId("occultism:fairy_familiar")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_fairy"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_fairy.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarGreedyEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_greedy");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
               .withEntityId("occultism:greedy_familiar")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_greedy"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_greedy.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookEntryModel.Builder makeFamiliarGuardianEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_guardian");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
               .withEntityId("occultism:guardian_familiar{for_book:true}")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual = BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/familiar_guardian"))
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("description2");
        var description2 = BookTextPageModel.builder()
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_guardian.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description,
                        description2
                );
    }

    private BookEntryModel.Builder makeFamiliarShubNiggurathEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("familiar_shub_niggurath");

        helper.page("entity");
        var entity = BookEntityPageModel.builder()
               .withEntityId("occultism:shub_niggurath_familiar")
                .withText(helper.pageText())
                .build();

        helper.page("ritual");
        var ritual= BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        helper.page("description");
        var description = BookTextPageModel.builder()
                .withTitle(helper.pageTitle())
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon("occultism:textures/gui/book/familiar_shub_niggurath.png")
                .withLocation(entryHelper.get(icon))
                .withPages(
                        entity,
                        ritual,
                        description
                );
    }

    private BookModel add(BookModel bookModel) {
        if (this.bookModels.containsKey(bookModel.getId()))
            throw new IllegalStateException("Duplicate book " + bookModel.getId());
        this.bookModels.put(bookModel.getId(), bookModel);
        return bookModel;
    }

    @Override
    public void run(HashCache cache) throws IOException {
        Path folder = this.generator.getOutputFolder();
        this.start();

        for (var bookModel : this.bookModels.values()) {
            Path bookPath = getPath(folder, bookModel);
            try {
                DataProvider.save(GSON, cache, bookModel.toJson(), bookPath);
            } catch (IOException exception) {
                Occultism.LOGGER.error("Couldn't save book {}", bookPath, exception);
            }

            for (var bookCategoryModel : bookModel.getCategories()) {
                Path bookCategoryPath = getPath(folder, bookCategoryModel);
                try {
                    DataProvider.save(GSON, cache, bookCategoryModel.toJson(), bookCategoryPath);
                } catch (IOException exception) {
                    Occultism.LOGGER.error("Couldn't save book category {}", bookCategoryPath, exception);
                }

                for (var bookEntryModel : bookCategoryModel.getEntries()) {
                    Path bookEntryPath = getPath(folder, bookEntryModel);
                    try {
                        DataProvider.save(GSON, cache, bookEntryModel.toJson(), bookEntryPath);
                    } catch (IOException exception) {
                        Occultism.LOGGER.error("Couldn't save book entry {}", bookEntryPath, exception);
                    }
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Books: " + Occultism.MODID;
    }
}
