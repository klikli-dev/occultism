package com.klikli_dev.occultism;

public class OccultismConstants {
    public static class Nbt {
        protected static final String PREFIX = Occultism.MODID + ":";

        public static class Divination {
            public static final String DISTANCE = PREFIX + "divination.distance";
            public static final String POS = PREFIX + "divination.pos";
            public static final String LINKED_BLOCK_ID = PREFIX + "divination.linked_block_id";
        }
    }


    /**
     * Use constants in this class instead of java.awt as that is not present in some server JREs and causes crashes
     */
    public static class Color {
        public static final int WHITE = 0xFFFFFF;
        public static final int RED = 0xFF0000;
        public static final int GREEN = 0x00FF00;
        public static final int BLUE = 0x0000FF;
        public static final int YELLOW = 0xFFFF00;

        public static final int ORANGE = 0xFFA500;

        public static final int CYAN = 0x00FFFF;

        public static final int MAGENTA = 0xFF00FF;

        public static final int BLACK = 0x000000;
    }
}
