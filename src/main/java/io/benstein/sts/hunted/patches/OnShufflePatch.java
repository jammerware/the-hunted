package io.benstein.sts.hunted.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import org.apache.logging.log4j.Logger;

import io.benstein.sts.hunted.services.LoggerService;
import io.benstein.sts.hunted.services.ShuffleService;

@SpirePatch(
    clz=CardGroup.class,
    method="shuffle",
    paramtypez={ }
)
public class OnShufflePatch {
    private static Logger logger = LoggerService.getLogger(OnShufflePatch.class);

    @SpirePrefixPatch
    public static void OnShuffle(CardGroup __instance) {
        logger.debug("--- ENTERING THE HUNTED ZONE ---");
        logger.debug("intercepted shuffle action: " + __instance.type.toString());

        ShuffleService
            .getInstance()
            .notifyShuffle(__instance.type);
    }
}