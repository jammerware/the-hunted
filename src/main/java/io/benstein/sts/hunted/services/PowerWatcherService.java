package io.benstein.sts.hunted.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import io.benstein.sts.hunted.interfaces.PowerRemovedListener;
import io.benstein.sts.hunted.powers.RecklessFrailtyPower;
import io.benstein.sts.hunted.powers.RecklessVulnerabilityPower;
import io.benstein.sts.hunted.powers.RecklessWeaknessPower;
import io.benstein.sts.hunted.powers.WardenPower;

public final class PowerWatcherService {
    private static int CURRENT_BATTLE_DEBUFF_COUNT = 0;
    private static List<String> POWER_BLACKLIST = Arrays.asList(new String[]{ 
        RecklessFrailtyPower.POWER_ID,
        RecklessVulnerabilityPower.POWER_ID,
        RecklessWeaknessPower.POWER_ID,
        WardenPower.POWER_ID 
    });

    private static final List<PowerRemovedListener> POWER_REMOVED_LISTENERS = new ArrayList<PowerRemovedListener>();

    public static void endCurrentBattle() {
        CURRENT_BATTLE_DEBUFF_COUNT = 0;
    }

    public static int getPlayerDebuffCount() {
        return CURRENT_BATTLE_DEBUFF_COUNT;
    }

    public static void powerApplied(AbstractCreature source, AbstractCreature target, AbstractPower power) {
        if (source != null && source.isPlayer && target.isPlayer && power.type == PowerType.DEBUFF && !POWER_BLACKLIST.contains(power.ID)) {
            CURRENT_BATTLE_DEBUFF_COUNT++;
            LoggerService.getLogger(PowerWatcherService.class).debug("---POWER WATCHER---");
            LoggerService.getLogger(PowerWatcherService.class).debug("player debuffed themselves " + power.ID);
            LoggerService.getLogger(PowerWatcherService.class).debug("battle debuff count: " + CURRENT_BATTLE_DEBUFF_COUNT);
        }
    }

    public static void powerRemoved(AbstractCreature owner, AbstractPower power) {
        for (PowerRemovedListener listener : POWER_REMOVED_LISTENERS) {
            listener.onPowerRemoved(owner, power);
        }
    }

    public static boolean isPowerDebuffBlacklisted(AbstractPower power) {
        return POWER_BLACKLIST.contains(power.ID);
    }

    public static void registerPowerRemovedListener(PowerRemovedListener listener) {
        POWER_REMOVED_LISTENERS.add(listener);
    }

    public static void unregisterPowerRemovedListener(PowerRemovedListener listener) {
        POWER_REMOVED_LISTENERS.remove(listener);
    }
}