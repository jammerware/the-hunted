package io.benstein.sts.hunted.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import io.benstein.sts.hunted.TheHuntedMod;

public class PerseverancePower extends AbstractPower {
    public static final String POWER_ID = TheHuntedMod.makeID("PerseverancePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG = "defaultModResources/images/powers/perseverance.png";

    public PerseverancePower(final AbstractCreature player) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = player;
        this.amount = -1; // doesn't stack
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = ImageMaster.loadImage(IMG);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
