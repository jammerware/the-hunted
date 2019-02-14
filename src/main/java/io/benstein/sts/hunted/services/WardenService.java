package io.benstein.sts.hunted.services;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;

import org.apache.logging.log4j.Logger;

import io.benstein.sts.hunted.powers.WardenPower;

public final class WardenService {
    public static boolean isWardenClose(AbstractPlayer player) {
        AbstractPower power = player.getPower(WardenPower.POWER_ID);
        Logger logger = LoggerService.getLogger(WardenService.class);

        if (power == null) {
            logger.error("WardenService.isWardenClose - The player doesn't have the Warden Power. Something's not quite right...");
            return false;
        }

        return power.amount >= 4;
    }    
}