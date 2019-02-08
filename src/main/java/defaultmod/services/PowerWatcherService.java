package defaultmod.services;

import java.util.Arrays;
import java.util.List;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import defaultmod.powers.RecklessWeaknessPower;
import defaultmod.powers.WardenPower;

public final class PowerWatcherService {
    private static int CURRENT_BATTLE_DEBUFF_COUNT = 0;
    private static List<String> POWER_BLACKLIST = Arrays.asList(new String[]{ 
        RecklessWeaknessPower.POWER_ID,
        WardenPower.POWER_ID 
    });

    public static void endCurrentBattle() {
        LoggerService.getLogger(PowerWatcherService.class).debug("Battle ended, count reset");
        CURRENT_BATTLE_DEBUFF_COUNT = 0;
    }

    public static int getPlayerDebuffCount() {
        return CURRENT_BATTLE_DEBUFF_COUNT;
    }

    public static void powerApplied(AbstractCreature source, AbstractCreature target, AbstractPower power) {
        if (source.isPlayer && target.isPlayer && power.type == PowerType.DEBUFF && !POWER_BLACKLIST.contains(power.ID)) {
            CURRENT_BATTLE_DEBUFF_COUNT++;
            LoggerService.getLogger(PowerWatcherService.class).debug("---POWER WATCHER---");
            LoggerService.getLogger(PowerWatcherService.class).debug("player debuffed themselves " + power.ID);
            LoggerService.getLogger(PowerWatcherService.class).debug("battle debuff count: " + CURRENT_BATTLE_DEBUFF_COUNT);
        }
    }
}