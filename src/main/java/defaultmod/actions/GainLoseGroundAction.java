package defaultmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.TheHuntedMod;
import defaultmod.powers.WardenPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GainLoseGroundAction extends AbstractGameAction {
    private AbstractCreature player;
    private int wardenGainsGround;
    private static final int MIN_STACKS = 1;
    private static final int MAX_STACKS = 5;
    private static final Logger logger = LogManager.getLogger(TheHuntedMod.class.getName());

    public GainLoseGroundAction(final AbstractCreature player, final int wardenGainsGround) {
        this.wardenGainsGround = wardenGainsGround;
        this.player = player;
    }

    @Override
    public void update() {
        AbstractPower wardenPower = player.getPower(WardenPower.POWER_ID);
        if (wardenPower == null) {
            logger.error("The player doesn't have the Warden power - something's wrong.");
            this.isDone = true;
            return;
        }

        if (wardenGainsGround > 0) {
            logger.info("The Warden gains ground..." + wardenGainsGround);

            if (wardenPower.amount + wardenGainsGround <= MAX_STACKS) {
                logger.info("the warden can get closer without catching the player");

                AbstractDungeon.actionManager
                        .addToBottom(new ApplyPowerAction(player, player, new WardenPower(player), wardenGainsGround));
            } else {
                AbstractDungeon.actionManager.addToBottom(new PlayerCaughtAction(player));
            }
        } else if (wardenGainsGround < 0) {
            logger.info("The Warden loses ground..." + wardenGainsGround);

            if (wardenPower.amount + wardenGainsGround >= MIN_STACKS) {
                logger.info("the warden can move further away without going below 1 stack");

                AbstractDungeon.actionManager
                        .addToBottom(new ReducePowerAction(player, player, wardenPower, -wardenGainsGround));
                
                wardenPower.playApplyPowerSfx();
            } else {
                logger.info("The Warden backs off as far as she can.");

                AbstractDungeon.actionManager.addToBottom(
                        new ReducePowerAction(player, player, wardenPower, wardenPower.amount - MIN_STACKS));
                wardenPower.playApplyPowerSfx();
            }
        }

        this.isDone = true;

    }
}
