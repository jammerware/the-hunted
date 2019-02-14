package defaultmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;

import defaultmod.TheHuntedMod;

public class RecklessFrailtyPower extends AbstractPower {
    public static final String POWER_ID = TheHuntedMod.makeID("RecklessFrailtyPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG = "defaultModResources/images/powers/reckless-frailty.png";    

    public RecklessFrailtyPower(final AbstractCreature player, int weakDuration) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = player;
        this.amount = weakDuration;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.img = ImageMaster.loadImage(IMG);

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner,  new FrailPower(this.owner, this.amount, false), this.amount));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}