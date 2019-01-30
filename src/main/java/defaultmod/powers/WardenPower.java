package defaultmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import defaultmod.TheHunted;

public class WardenPower extends AbstractPower {
    public static final String POWER_ID = TheHunted.makeID("WardenPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG = "defaultModResources/images/powers/warden.png";
    private static final Logger logger = LogManager.getLogger(TheHunted.class.getName());

    private static final int MAX_STACKS = 5;

    public WardenPower(final AbstractCreature player, final int groundLost) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = player;
        this.amount = groundLost;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.img = ImageMaster.loadImage(IMG);
        updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(this.ID)) {
            if (this.amount + power.amount > MAX_STACKS) {
                // stun people
                logger.info("Stunned cuz warden!");
                AbstractDungeon.actionManager
                        .addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
                AbstractDungeon.actionManager
                        .addToBottom(new ApplyPowerAction(this.owner, this.owner, new WardenPower(this.owner, 1)));
            } else {
                power.stackPower(this.amount);
            }

            flash();
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + DESCRIPTIONS[this.amount];
    }
}
