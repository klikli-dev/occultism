package com.github.klikli_dev.occultism.datagen;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.integration.modonomicon.BookSpiritFireRecipePageModel;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.BookLangHelper;
import com.klikli_dev.modonomicon.api.datagen.EntryLocationHelper;
import com.klikli_dev.modonomicon.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.datagen.book.BookModel;
import com.klikli_dev.modonomicon.datagen.book.page.*;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class BookGenerator implements DataProvider {
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

        var gettingStartedCategory = this.makeGettingStartedCategory(helper);

        var advancedCategory = this.makeAdvancedCategory(helper);

        var ritualsCategory = this.makeRitualsCategory(helper);

        //https://viewer.diagrams.net/?highlight=0000ff&edit=_blank&layers=1&nav=1&title=Gameplay%20Path.drawio&%24web_only=true&_branch_match_id=1080882655718139071&utm_medium=marketing&_branch_referrer=H4sIAAAAAAAAA8soKSkottLXz87JBCK9xIICvZzMvGz9VP385OTSnJLM4lzdtNTEktKi1GIA%2B%2FLRTSwAAAA%3D#R7V1bc9pKEv41qd19IKU74hGb%2BJIrJySxc15SMoxBRtKQQfj261cSCIRmPBKW0PRgtk5tQEhm6Omv793zTj%2F1H8%2BJM5t8wSPkvdOU0eM7vfdOi%2F7X1qJ%2F4itPyyuqrZjLK2PijlbXNhcG7jNaXVRWVxfuCM23bgwx9kJ3tn1xiIMADcOtaw4h%2BGH7tlvsbX%2FrzBkj6sJg6Hj01St3FE6WV21T2Vy%2FQO54kn6zqqw%2B8Z305tWF%2BcQZ4YfMJf3DO%2F2UYBwuX%2FmPp8iLqZfSZfnc2QufrhdGUBCWeeDpV6fb%2Befk8gs%2Btztt%2F%2FPX4fSq1V6tLXxKfzAaRb9%2F9RaTcILHOHC8D5urJwQvghGK%2F6oSvdvc8xnjWXRRjS7eoTB8Wm2mswhxdGkS%2Bt7qU%2FTohteZ17%2FjP%2FXeXL3rPa7%2BcvLmKX0ThOTpOvsm81T8dvNY8i59jqbSinBzvCBDxCGNteI2h4xRyLmvs7wvplvmC1Z7cI6wj6L1RDcQ5Dmhe7%2FNV86KPcfr%2B9aP9rEbLVlTUiyliFghyeq8t822amir%2F9e3%2F%2BBy2au%2FseGK6EVmUZtLCa%2FswDdaRyTjbJjld5ZXJGMcza6bc7Z2edctXS373vEWq2%2FqIR8H%2F5lHF3sEOT6145v9jDfnYeKGaDBzEto8ROpge%2B9epOc9IiF65BJg9amxjYD07cNGLqupsJ1kZHJ6X%2B0UU1WKJEfpuS0VC0GQSjUgIOhQIBjMXOLGcvjMJUg4BDQFGgasIwaKeLsYBAYoEKTrZqGgG4aLINo6TTlH4jWCbhbDQW8UDiYcu0jlwqECW2tyyvZ03VkLx3UiE2fE4WOlmI9vXc87xR4mybP6yET2yIiuz0OCpyjzia3d6JZVF%2BebW5zfEq8JhLoEwDWBURYybVCQ0fVD39O7hT9Lf844QXYDu6wJ2%2BXw%2BXn0t%2Fet%2B%2FP32XyqB9N7r3%2FaMozKu0yTEfi%2B17vLbLLqoLCcrntL%2Fd27QfTVOIiuf8c8PdiMPWdqwNwbTTt0AVgBCO2y4k4FBQRNGpe1ib0xQe1Nuu6MkPoWThCZhzgQH32xbGjiqbrmPlzxZJeFADA9bbMh8ICJF8ddPuPxXDgS7FwsXrz7uf4NmaCVMyTurTt0nfjpE%2FzgwSOcZosmnF7db6%2FV%2Bt8liLWRKhtB8jv7We1SJRUWhVJFh%2BXJp%2Bt%2BSap05xMkHh2qokCTK7oNCx6wlW5qTkoHD5OCx8mCJPUXgOzPvPIQD47qrpQwcFRg8rLOlQqrwkN7wblKdcAPggDogLyTBUAFSOhl7WYP1RkF10r7XbBUgC5N6lbIbtWfmUoe7RLiPGVumMUVi%2FPMX86VQZo56ZAves3d3s67W0qOT5bfX29tJO3A%2F5i4JJawH57EGxH5GHtrLUyFCVijeppRQjNCL2tGGLDi50b1onEAAYOmPKLyu2yBELCqkpOwNl%2FCUvcbTchYnTZlL%2F3ZgkTiVbmKRWj07%2BnE8abCxS0V0hBuzprVpe0Q%2B%2B5QXsmbklY6ycspzKxenjxy5pP1vXVwvmptc36jhsYn7daada%2FC0cx%2BfHQ7IXnu9euoVpYumIeCUTduhIveDj1nPo%2BBG188c71CMVeMpLQnrwhJqZJoHklsRgBmw7wdRjBr75erxghvMLwPgxEsYBIBWBr0IBmBWScJq%2FwiXXbGxgLmUOjQiiSNg5Ch2xFztY6AwOPCNr5eXo7OP3763fuXfPuLW59bWlnfo%2FZ4QNlNZi77DVrOFWQfo0acxwxQZB9dIn6Ok9TgZTDGIUf0KcWiT0ibFB19EV5vZRyEobEHacmmVtmCEnHikrvuLJQ8%2FFBURwIWR%2FlYjinc6jCrayR5wphM5VE2s2AK87SYy5ZY%2FlWQY2WnwojbLO66WYmW2DZAAWTHqEEZxWR1CQfENKXYzbI5mI6wDiUV24ub3p%2Fzy%2BD638E39LvjX12mFjv8La2zSoi1WUzq1J4ve10Ou523%2BgvKhOgHrAaS2CadzoMmUg2q40202WdVL8YEYfY1H5RIMy%2FFkXlYEdl03dms98L3k6b1bHG%2B8oM4I0SqgSbnXSE18q%2FaLO%2BqY7V1py7vKg8z8TXP1lvyrrj5qWK4wGqhtmitsg2XtNx%2F4Mw8NxgfFnDEF7NadKfdYCWg3mmW48d0G4fJ798WX6c4iH75PJnGIVrvW%2BD6ey06oDYInRsP5dkaRhcLTUEAnEkXTf5yggi3TnTxMkQ%2Bj2xQI5N5MuuWcDLT9sp3N1zETejSCViKuqpo6rZp9fYlnlyknCEnXMalfgYeHop3nvK005rkTHaNS%2FUWJ4H%2B0rpWJYj1ZbZQpZqhl55RsO8J7qpOqQQ7t9N7ntqe%2FtJ9zsjYqiKO3%2FSdMEQkSK5oSl3qNT8IuaUJj0zYB1JX33wsuF02kyWuipi77gyeTvFsFrtSyjcivtWPyoiowo0j%2B%2BDCd42BpGynLjSQ0O2wA9e7hwsSTbiNa1fvTgAGkqZi3CnvF4MEVtDOrl5Ce9zxgh2HVU8reNqEJmbcROndMmo%2FEuN1mWQzd8JEUTd0%2Fn6tXdQ%2BnZ9Qsf3AfjLPNh1EWecITski8uBINb0MIxsgPGjd0Q5DrFcAfNn6XVtYXyB33dlZWTdzd%2BQ6MUZ6izmvGl5QikE8ux%2Ft1tfCpPRpk7BqM2y6PDTx6ZQeXtwss8vQUNJoHo7Z8Kgez9vYBRqMsiU2WYVBg7mclKVk3OUmdkvYbEymO6ZXL9Xe41ArpSQmm5qCzdpldn%2BjsDALe5elToBCkLzsXYYxukxTc0lBi%2B97U%2FfXXfXN5sGj9n8FDzYvQEh3MPl4Qf5OJ48fL%2F%2F0f%2BKT62Atz%2BC3f8AQF2wi7kdc0PhW9G18t82cSf9CvcXOUcKOsZPgoe5v2%2FUKnr%2FtP7b69dNTcGaqN8bPu0HrV6uGiPMbt3CYVAXS8WTn%2BrPXtUUvKj%2FFrPxEoYLVqSqdmjUsc0uAjSo5EEbX3jCjp5V75Rk998Se0jh0pXF%2FQWYeoGFVNrTpt53qjv7eYtR1SIr9FSB1yh59BSyV06GTnR%2FiM9%2BVgazDWPKgEl%2F72qGbnpZ55JjO5agtSCCJb8vpAC6JBC6RSs%2BQqD01UNI6WSMz%2FRvL30R5nMV%2FqeFWgc6B9P%2B%2BNgxSbhh6MYeWLX9IWRmKzmR0W6YFQn08n6Pov5VoR8RPxqNWEexiyoXymeFG1SgzKQbMcwUTb%2BTNDS7MNApLQfFWzQLWGfbceMqm8nnh3yBy51RtYoRRhddsaxaPBTJE70ZM7zthIsTijvHonwuH3KN5CLJoBQAN066Io3AqI5wYHW3M%2B4RVQfBWvXUKbjhMqoGVT4F7K95%2Fow6eF1%2FOlc4nOgKjDDAYXWxsqgo7HZS37OyZjo7n4QfhgKCPTBePiLd4us7uDiO3iLG4fk5YOxN33UUO42CKPBRWHXEkxrDtiDzPjE31o%2BrZBVuMInsZalM1YOJ0l6BwvbvVUI3pqzLTmkrNoqs5B0w6wfOw3xnfXbWe%2Fqq3H88eXT%2FS9pWZ4zAPpy%2FNVGyywuApLa9yigoR8g%2BYRbUO%2BaFEZhOlaNUn5TSe09pNSe2bZ5spRSurtZirqZ7VPO5xA1VYZff49Go6UIK%2B378cqRd3Ov46vV60qhsmDe3xvveKSR2odolRqBPyIQWjZqXAJJd6bMHaBxemcfsjG5b0qemW5k3QQhlMFx5vkCvUmrFNYAJM8EKr7rlIF7yoK0pYNoquCbMJuesuV1biyBgl3FRqgQEatN5m2FHC9Mbic6uFlWyxl%2FMGxWkDu6zBytDr1UMnx11m3CisZYG77u2WhaQMo48cIqUBaoCbw6NJ3IlfAROlM8ywJJ9GZ5j7Lq9UDywS8q6Y%2BNo%2B7SAKj%2FfQdsKmFqOoX4qJVHQZ7OnEHU4Rz8%2BCiiK6Cc4WjSJTYn0iwDYrXQkoLOfBXXf2ROzgdjFPph66PgriE9yS01S%2BOCFxHznYAhvDsEQ2SXINdBbV1%2BfAXWHiT7AnYzMSRfJGT9zjxjfYJMfEGcevviMfS9n%2BRVFcOMHNY5xuB%2F2RzlEr1h%2FA4nQGBautY0D7aITmYdXTAusASN5jF29h6RL7KRU4vay%2FkbIWFE5nzFDPcHoV9i6XR6uQtQHA63QzVv6gy%2B%2FoFkU%2FegiMmPB6Fw1NXrkhQLOW9cwMYJqV9sxOCHKm0aVz4sxlPN24lZdLwo1UQ2Ij9fWQSMlZOMcDVleWQbtwvchfC%2F4zj4MVETb82JdDcc2FYGOzlQ%2BKi2d0YF03r4yJtxuqRzTKTodTa2%2FsfVU9op3jt8KpkPkHNP7t7Tbv9v2ULhrVTxGsuYK2Xf6YOcg8q8FqRjfoVGjiWD1g4o0Sawffix%2FVYAGcA3OsntkBHibD6JEhQ2PSVg8jcN0dhgsnehe9PHHmBxHBbhRg7F5ciVOgFYCi0UBhkwdWOXa67qx7sJXBZKFFdFS6LXIKwyft1pp1r8LRzH58dDshee71wTUuNdGUXQEsZUdj7Wdm684D5%2FMN0q301KAXR7dSTxT5CqpZ8ERld4HNtwcRFRXJt%2BwQEIyGuzoYt2jcAM24dc%2B8YDPuQZjx4Bg3HWR3AIxbdAATxbc1n0vDZluJjWPAbGsfjLzd1Uyo2Uhgto9XtxEONz7C4FB2C%2F6hMGjLrFm9c%2BmarVN3gpGHeBnchqZtavCmbVoSq5XXgy%2B1smVr7bOAHcMIW8SW3mVgzdiWxIWKgHe5%2FmbsV2lSzdgtF0zdX6R411P4Xv9AzYqafZS1AovLtyc2lU43aw15NKXZHFjnbbruuu2x%2Bot4mywaYid4KEqd4yQl31vMQ%2FHWq5rLnRhNVjkzCUab%2BoOZS9xYtJ25BFhhs6rn5LglPuF6EDH8PRgaTGqpDAkMLFHLXTdrbtYXZzhxk86hbzNEVhUNvTs3CNxq6BFT09ARWAfKZgVgRg5whDF6lbgsDQVhjAMPBU99rN08yldONBu7YpMdWMUqcHCVPRIBGrjokaowHQgKIcK1z%2FGEl50AwsjISAEQ2g3qL8gsGWpxOnG8iueA7h0nwmHCGJgXn%2FA5JM4tiAM9dUXkVFjusTlZoewhJ%2FYerpATLg99%2FO%2FSkfhfNQIKGsRLpTst0TTXD6KnrClprpU1d3RhXZfss4OAJWRfFxYXuctssgqbpsddNysm8zO4ibczetW9TYKZ8klPajpSkzqeuf9CD5uBLSn1shiq%2FUiaauqQhtAKL8qH%2Bbz6YJO9mLrCcaBJHPLf%2FfwL3ul8hewuLozPWzZLY1y5SZrwYhFIqS3yY0oaTYwxaU23SH933OBp27tJpI2Mzg3lT%2BpN%2BjZMgtNj7X5MYjLG476If1jUbnQ6GJPa1ePmh2AM8WycQuUgbFgeczXV47xvfkOF%2BYe8VWcVUHK2VQ3x3Do0NpVnF27X1tBG3XwzlGFo2zBQtCIgJO%2F6iLgR2RCpjA5WTYsE8o5R0nI%2BceIiOeVHEoV%2BESB7SbbvA1KNpkW4IbPsRETPeU7GDOOH5EAh%2Bcic98jNJlMpbDIfw%2Fq7CKyy%2BhyY886oEHK9ey6GGtLl%2BdmJ4r3vY9HcTojoyImIDq3Ck2p3%2BXQKhSDhDr7Mk7Re7K1R9wah9Mbiyu7ac8WrR%2FMdWVaOpUw1xyrLpa4ey3HLbs1ZXIpk0HnlrgKf0p5inj%2BSolFNxxzqoVWG6SGoNfa8ExqSzP4yYd1uvFVvHzM5f6edxrEb%2FLB6NZggNFu%2BfP%2F%2BvYRQsoWe0sCkfHWNd7BQKjuouCMswU%2B6g8nHC%2FJ3Onn8ePmn%2FxOfXAd1pKqhT0ynjaxad5lNVlACk44xgSzuV418yrTRuBFrH6uPsTsEgcckDavCAxQUeKvOOsRecvCM8hkFcwjzi%2FIgsBuNUTNpVj1UdLAo0EqiAJjWp1CwHr9eBwyElIColgENODVEiA4WOKwUA5OG4moGOMt%2B8VwPPB6DmIKXP3tdPBhkLB5oCgys4nD4WoSuDY%2BVR3TljDi%2B%2BMJwSiF0hLsTNcxzP1gMMBqDJcAA3Rb8OS1eli7gSAFGVRpNU7PoK03R5esZn3GWK0%2Fc7jstplp6TmzmN7fGtBiHHhKcSU356gA0TPWxpuABU3%2Bb6QtIaL9kRDeEBEZxYFrQpFwGYwxgXmEeAe1Gu1yYRJNGZWQyGvXPN2XTpqyuARa0pVWCpGVMFF6aLWNibvXRL3%2BZNqzKfU5aAQhe0mWzZrZ2w3ARJG0u58gHZk7lh7eKD1pp0nTxVWDy0kltYWcsc9ed5XIUpzBwNiKLCYRwbMcCx9lCzaRXzoFvyExiDryRARF0RHZzrDKtAPrucOo8Snmucr4aXLzboR%2FNqEKYFOJJ3Bkx3HVn8PRlOch77jvJ444f7WB8EImCb2OnHM0Dd1HRrNoHPsTrG%2F2YCy9k%2B2J8wEqG67SfcRrP88wioR4tUwcm8hNwAGBCmmCtAEyU9Up0WAlBQ%2BieghkkWW1PDVh7qtOe5soOAKPw821X4oWbIY1BXIGhy1axGbAMW50uY1s7ij3XR8HcjakevYv5fD5JdLp8XmJ%2BQqp4UOhvIJ6ol%2Fb2gEl5zklngKPmlOhvNBby7DxfnOCuq3bRB%2F%2Fi6ta7%2BqUdO26LFUEWHEwawsooGbSnt60n%2Flk4hDxBOMiBGvIkXuob0pck1DMWgkkbQ04TyqCnqa5HBZ9hz42LcxLrichoOeUneIkvbDOkGS5fAQul3QlxkwJ56876x87YHUaXPjv%2BTLxKyDsCjbIzU71LEw0vxc08C6bQ0hEW0%2BatmiXW0yNblW8ESSzcqVnZjRpIPBbIhiP8GcH3iccF1dDU88dXNUrHv%2B0%2Ftvr101NwZqo3xs%2B7QetXi8G8PSde8Q837sc6rLPCGp3dyqS2RlH7a%2FKth0nvRodxnF5NB0rQ9%2FuXI%2FXiTsdfp9cLeXTma8MKu5%2B9wqQTQ%2FEy7xOmeHmrzk71iiR94kadOb7rudyR01BxRBV8t8wmw3NMSh%2BzzkWQKISOsMg1b9UM6LyIF7ANEvkeVAB4qR69AzDntSkEMUIYzPuEjZTkrTprOBN3%2BpC4GTKrH9qMEw8nupnrnWZ50beeRL61NY5fdD0fJwdNpJ9EX7T%2B8MwD4f5FBAJHWrX6vLo3JKpY57Kwb9RACSvGwSwDvIgfq5ycBiKkGj3qiLvlWzMzZ%2BFiGfnDN%2BJ7imj50yjVVGwvbnp%2Fzi%2BD638H39Dvjn91yfDhNv0nTjicIN5Ic6jsqTZ5wGz0luDIct98dh5RYfIFj1B8x%2F8B

        var demoBook = BookModel.builder()
                .withId(this.modLoc("dictionary_of_spirits"))
                .withModel(this.modLoc("dictionary_of_spirits_icon"))
                .withName(helper.bookName())
                .withTooltip(helper.bookTooltip())
                .withCategory(gettingStartedCategory)
                .withCategory(ritualsCategory)
                .withCategory(advancedCategory)
                .withCraftingTexture(this.modLoc("textures/gui/book/crafting_textures.png"))
                .build();
        return demoBook;
    }

    private BookCategoryModel makeGettingStartedCategory(BookLangHelper helper) {
        helper.category("getting_started");

        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
                "_____________p_____C_", //p=pentaclePrep, C=Chalks
                "_____________________",
                "_____________________",
                "_____________________",
                "__d___f___r________R_", //d=demonsDream, f=SpiritFire, r=divinationRod, R=ritualPrep
                "_____________________",
                "_____________________",
                "_____________________",
                "____e_________b______", //e=thirdEye, b=ritualBook
                "_____________________",
                "__________________c__", //c=?
                "_____________________",
                "______________s______"  //s=?
        );


        //TODO: Entry about making a pentacle (chalks, bowls, candles, ... )
        //TODO: Entry about books
        //TODO: ritualentry

        var demonsDreamEntry = this.makeDemonsDreamEntry(helper, entryHelper, 'd');
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

        //        var ritualEntry = this.makeRitualEntry(helper, entryHelper, 'R');
//        ritualEntry
//                .withParent(BookEntryParentModel.builder().withEntryId(pentaclePrepEntry.id).build())
//                .withParent(BookEntryParentModel.builder().withEntryId(ritualBookEntry.id).build());

        return BookCategoryModel.builder()
                .withId(this.modLoc("getting_started"))
                .withName(helper.categoryName())
                .withIcon(OccultismItems.DICTIONARY_OF_SPIRITS_ICON.getId().toString())
                .withEntry(demonsDreamEntry.build())
                .withEntry(spiritFireEntry.build())
                .withEntries(thirdEyeEntry.build())
                .withEntries(divinationRodEntry.build())
                .withEntries(pentaclePrepEntry.build())
                .withEntries(ritualBookEntry.build())
                .build();
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

//        helper.page("purified_ink");
//        var purifiedInkSpotlight = BookSpotlightPageModel.builder()
//                .withItem(Ingredient.of(OccultismItems.PURIFIED_INK.get()))
//                .withText(helper.pageText())
//                .build();

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
//                        purifiedInkSpotlight,
                        purifiedInkRecipe,
                        bookOfBindingFoliotRecipe,
                        bookOfBindingBoundFoliotRecipe,
                        bookOfBindingDjinniRecipe,
                        bookOfBindingAfritRecipe,
                        bookOfBindingMaritRecipe
                );
    }

    private BookCategoryModel makeAdvancedCategory(BookLangHelper helper) {
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
                .withEntry(chalksEntry.build())
                .build();
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


    private BookCategoryModel makeRitualsCategory(BookLangHelper helper) {
        helper.category("rituals");

        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
                "_____________________",
                "_____________________",
                "____________________",
                "_______g_____________",
                "_____________________",
                "_____________________",
                "_____________________"
        );


        var craftOtherworldGogglesEntry = this.makeCraftOtherworldGogglesEntry(helper, entryHelper, 'g');


        return BookCategoryModel.builder()
                .withId(this.modLoc("rituals"))
                .withName(helper.categoryName())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withEntry(craftOtherworldGogglesEntry.build())
                .build();
    }

    private BookEntryModel.Builder makeCraftOtherworldGogglesEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char icon) {
        helper.entry("craft_otherworld_goggles");

        //TODO: add actual content

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry))
                .withName(helper.entryName())
                .withIcon(OccultismItems.OTHERWORLD_GOGGLES.getId().toString())
                .withLocation(entryHelper.get(icon));
    }

    private BookModel add(BookModel bookModel) {
        if (this.bookModels.containsKey(bookModel.getId()))
            throw new IllegalStateException("Duplicate book " + bookModel.getId());
        this.bookModels.put(bookModel.getId(), bookModel);
        return bookModel;
    }

    @Override
    public void run(CachedOutput cache) throws IOException {
        Path folder = this.generator.getOutputFolder();
        this.start();

        for (var bookModel : this.bookModels.values()) {
            Path bookPath = getPath(folder, bookModel);
            try {
                DataProvider.saveStable(cache, bookModel.toJson(), bookPath);
            } catch (IOException exception) {
                Occultism.LOGGER.error("Couldn't save book {}", bookPath, exception);
            }

            for (var bookCategoryModel : bookModel.getCategories()) {
                Path bookCategoryPath = getPath(folder, bookCategoryModel);
                try {
                    DataProvider.saveStable(cache, bookCategoryModel.toJson(), bookCategoryPath);
                } catch (IOException exception) {
                    Occultism.LOGGER.error("Couldn't save book category {}", bookCategoryPath, exception);
                }

                for (var bookEntryModel : bookCategoryModel.getEntries()) {
                    Path bookEntryPath = getPath(folder, bookEntryModel);
                    try {
                        DataProvider.saveStable(cache, bookEntryModel.toJson(), bookEntryPath);
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
