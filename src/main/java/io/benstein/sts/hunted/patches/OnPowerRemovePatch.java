package io.benstein.sts.hunted.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.powers.AbstractPower;

import org.apache.logging.log4j.Logger;

import io.benstein.sts.hunted.services.LoggerService;

@SpirePatch(
    clz=AbstractPower.class,
    method="onRemove"
)
public class OnPowerRemovePatch {
    private static Logger logger = LoggerService.getLogger(OnPowerRemovePatch.class);

    @SpirePrefixPatch
    public static void OnPowerRemove(AbstractPower __instance) {
        logger.debug("--- ENTERING THE HUNTED ZONE ---");
        logger.debug("intercepted the whole on remove situation");
        logger.debug("The power's name is" + __instance.name);
    }
}