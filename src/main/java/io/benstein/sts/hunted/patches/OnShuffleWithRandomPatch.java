package io.benstein.sts.hunted.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.random.Random;
import org.apache.logging.log4j.Logger;

import io.benstein.sts.hunted.services.LoggerService;
import io.benstein.sts.hunted.services.ShuffleService;;

@SpirePatch(
    clz=CardGroup.class,
    method="shuffle",
    paramtypez={ Random.class }
)
public class OnShuffleWithRandomPatch {
    private static Logger logger = LoggerService.getLogger(OnShuffleWithRandomPatch.class);

    @SpirePrefixPatch
    public static void OnShuffleWithRandom(CardGroup __instance) {
        logger.debug("--- ENTERING THE HUNTED ZONE ---");
        logger.debug("intercepted shuffle/random action: " + __instance.type.toString());
        
        ShuffleService
            .getInstance()
            .notifyShuffle(__instance.type);
    }
}