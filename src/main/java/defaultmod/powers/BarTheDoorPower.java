package defaultmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import defaultmod.TheHuntedMod;

public class BarTheDoorPower extends AbstractPower {
    public static final String POWER_ID = TheHuntedMod.makeID("BarTheDoorPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG = "defaultModResources/images/powers/bar-the-door.png";
    private static final Logger logger = LogManager.getLogger(BarTheDoorPower.class.getName());

    public BarTheDoorPower(final AbstractCreature player, int healAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = player;
        this.amount = healAmount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = ImageMaster.loadImage(IMG);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onVictory() {
        AbstractPower wardenPower = this.owner.getPower(WardenPower.POWER_ID);
        if (wardenPower == null) {
            logger.error("Player doesn't have the Warden power. Something's wrong.");
            return;
        }

        if (wardenPower.amount < 2 && AbstractDungeon.player.currentHealth > 0) {
            AbstractDungeon.player.heal(this.amount);
        }
    }
}
