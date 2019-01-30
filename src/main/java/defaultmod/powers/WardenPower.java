package defaultmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import defaultmod.TheHunted;

public class WardenPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = TheHunted.makeID("WardenPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = "defaultModResources/images/powers/warden.png";

    public WardenPower(final AbstractCreature player) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = player;
        this.amount = 1;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.img = ImageMaster.loadImage(IMG);
        this.source = player;
        updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(this.ID)) {
            this.stackPower(1);

            if (power.amount > 5) {
                // stun people
                AbstractDungeon.actionManager
                        .addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
                AbstractDungeon.actionManager
                        .addToBottom(new ApplyPowerAction(this.owner, this.owner, new WardenPower(this.owner)));
            } else {
                this.stackPower(1);
            }

            flash(); // Makes the power icon flash.
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + DESCRIPTIONS[this.amount];
    }
}
