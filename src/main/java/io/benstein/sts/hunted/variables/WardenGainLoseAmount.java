package io.benstein.sts.hunted.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import io.benstein.sts.hunted.TheHuntedMod;
import io.benstein.sts.hunted.cards.AbstractWardenGroundCard;

public class WardenGainLoseAmount extends DynamicVariable {

    @Override
    public String key() {
        return TheHuntedMod.makeID("WardenGainLoseAmountVariable");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractWardenGroundCard) card).isWardenGainLoseAmountModified;

    }

    @Override
    public int value(AbstractCard card) {
        return Math.abs(((AbstractWardenGroundCard) card).wardenGainLoseAmount);
    }

    @Override
    public int baseValue(AbstractCard card) {
        return Math.abs(((AbstractWardenGroundCard) card).baseWardenGainLoseAmount);
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractWardenGroundCard) card).upgradedWardenGainLoseAmount;
    }
}