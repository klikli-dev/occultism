package com.klikli_dev.occultism.util;

import com.mojang.datafixers.util.Function7;
import com.mojang.datafixers.util.Function9;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Function;

public class OccultismExtraStreamCodecs {

    public static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9> StreamCodec<B, C> composite(
            final StreamCodec<? super B, T1> pCodec1,
            final Function<C, T1> pGetter1,
            final StreamCodec<? super B, T2> pCodec2,
            final Function<C, T2> pGetter2,
            final StreamCodec<? super B, T3> pCodec3,
            final Function<C, T3> pGetter3,
            final StreamCodec<? super B, T4> pCodec4,
            final Function<C, T4> pGetter4,
            final StreamCodec<? super B, T5> pCodec5,
            final Function<C, T5> pGetter5,
            final StreamCodec<? super B, T6> pCodec6,
            final Function<C, T6> pGetter6,
            final StreamCodec<? super B, T7> pCodec7,
            final Function<C, T7> pGetter7,
            final StreamCodec<? super B, T8> pCodec8,
            final Function<C, T8> pGetter8,
            final StreamCodec<? super B, T9> pCodec9,
            final Function<C, T9> pGetter9,
            final Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, C> pFactory
    ) {
        return new StreamCodec<B, C>() {
            @Override
            public C decode(B p_330310_) {
                T1 t1 = pCodec1.decode(p_330310_);
                T2 t2 = pCodec2.decode(p_330310_);
                T3 t3 = pCodec3.decode(p_330310_);
                T4 t4 = pCodec4.decode(p_330310_);
                T5 t5 = pCodec5.decode(p_330310_);
                T6 t6 = pCodec6.decode(p_330310_);
                T7 t7 = pCodec7.decode(p_330310_);
                T8 t8 = pCodec8.decode(p_330310_);
                T9 t9 = pCodec9.decode(p_330310_);
                return pFactory.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9);
            }

            @Override
            public void encode(B p_332052_, C p_331912_) {
                pCodec1.encode(p_332052_, pGetter1.apply(p_331912_));
                pCodec2.encode(p_332052_, pGetter2.apply(p_331912_));
                pCodec3.encode(p_332052_, pGetter3.apply(p_331912_));
                pCodec4.encode(p_332052_, pGetter4.apply(p_331912_));
                pCodec5.encode(p_332052_, pGetter5.apply(p_331912_));
                pCodec6.encode(p_332052_, pGetter6.apply(p_331912_));
                pCodec7.encode(p_332052_, pGetter7.apply(p_331912_));
                pCodec8.encode(p_332052_, pGetter8.apply(p_331912_));
                pCodec9.encode(p_332052_, pGetter9.apply(p_331912_));
            }
        };
    }

    public static <B, C, T1, T2, T3, T4, T5, T6, T7> StreamCodec<B, C> composite(
            final StreamCodec<? super B, T1> pCodec1,
            final Function<C, T1> pGetter1,
            final StreamCodec<? super B, T2> pCodec2,
            final Function<C, T2> pGetter2,
            final StreamCodec<? super B, T3> pCodec3,
            final Function<C, T3> pGetter3,
            final StreamCodec<? super B, T4> pCodec4,
            final Function<C, T4> pGetter4,
            final StreamCodec<? super B, T5> pCodec5,
            final Function<C, T5> pGetter5,
            final StreamCodec<? super B, T6> pCodec6,
            final Function<C, T6> pGetter6,
            final StreamCodec<? super B, T7> pCodec7,
            final Function<C, T7> pGetter7,
            final Function7<T1, T2, T3, T4, T5, T6, T7, C> pFactory
    ) {
        return new StreamCodec<B, C>() {
            @Override
            public C decode(B p_330310_) {
                T1 t1 = pCodec1.decode(p_330310_);
                T2 t2 = pCodec2.decode(p_330310_);
                T3 t3 = pCodec3.decode(p_330310_);
                T4 t4 = pCodec4.decode(p_330310_);
                T5 t5 = pCodec5.decode(p_330310_);
                T6 t6 = pCodec6.decode(p_330310_);
                T7 t7 = pCodec7.decode(p_330310_);
                return pFactory.apply(t1, t2, t3, t4, t5, t6, t7);
            }

            @Override
            public void encode(B p_332052_, C p_331912_) {
                pCodec1.encode(p_332052_, pGetter1.apply(p_331912_));
                pCodec2.encode(p_332052_, pGetter2.apply(p_331912_));
                pCodec3.encode(p_332052_, pGetter3.apply(p_331912_));
                pCodec4.encode(p_332052_, pGetter4.apply(p_331912_));
                pCodec5.encode(p_332052_, pGetter5.apply(p_331912_));
                pCodec6.encode(p_332052_, pGetter6.apply(p_331912_));
                pCodec7.encode(p_332052_, pGetter7.apply(p_331912_));
            }
        };
    }

}
