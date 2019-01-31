package defaultmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.TheHuntedMod;

public class WardenPower extends AbstractPower {
    public static final String POWER_ID = TheHuntedMod.makeID("WardenPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG = "defaultModResources/images/powers/warden.png";

    public WardenPower(final AbstractCreature player) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = player;
        this.amount = 0;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.img = ImageMaster.loadImage(IMG);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.amount >= 0 && this.amount < DESCRIPTIONS.length) {
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[this.amount];
        }
    }
}
