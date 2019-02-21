package io.benstein.sts.hunted.interfaces;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public interface PowerRemovedListener {
    public void onPowerRemoved(AbstractCreature owner, AbstractPower power);
}