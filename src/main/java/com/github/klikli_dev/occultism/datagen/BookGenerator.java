package com.github.klikli_dev.occultism.datagen;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.BookLangHelper;
import com.klikli_dev.modonomicon.api.datagen.EntryLocationHelper;
import com.klikli_dev.modonomicon.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.datagen.book.BookModel;
import com.klikli_dev.modonomicon.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.datagen.book.page.BookTextPageModel;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;

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

        var helperCategory = this.makeHelperCategory(helper);

        var demoBook = BookModel.builder()
                .withId(this.modLoc("dictionary_of_spirits"))
                .withName(helper.bookName())
                .withTooltip(helper.bookTooltip())
                .withCategory(helperCategory)
                .build();
        return demoBook;
    }

    private BookCategoryModel makeHelperCategory(BookLangHelper helper) {
        helper.category("helper");

        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
                "_____________________",
                "_____m_______________",
                "_____________________",
                "_____________________",
                "_____________________",
                "_____________________"
        );

        var multiblockEntry = this.makeMultiblockEntry(helper, entryHelper);

        return BookCategoryModel.builder()
                .withId(this.modLoc("helper"))
                .withName(helper.categoryName())
                .withIcon("minecraft:nether_star")
                .withEntry(multiblockEntry.build())
                .build();
    }

    private BookEntryModel.Builder makeMultiblockEntry(BookLangHelper helper, EntryLocationHelper entryHelper) {
        helper.entry("multiblock");

        helper.page("preview");
        var pentacle = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("summon_foliot"))
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc("helper/multiblock"))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon(OccultismItems.PENTACLE.getId().toString())
                .withLocation(entryHelper.get('m'))
                .withPages(pentacle);
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
