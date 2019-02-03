package defaultmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EndTurnAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import defaultmod.TheHuntedMod;
import defaultmod.powers.WardenPower;

public class PlayerCaughtAction extends AbstractGameAction {
    private AbstractCreature player;
    private static final int CAUGHT_FRAIL = 2;
    private static final int CAUGHT_VULNERABLE = 2;
    private static final int CAUGHT_WEAK = 2;
    private static final Logger logger = LogManager.getLogger(TheHuntedMod.class.getSimpleName());

    public PlayerCaughtAction(final AbstractCreature player) {
        this.player = player;
    }

    @Override
    public void update() {
        logger.info("Player caught by the Warden!");

        // apply debuffs - vuln, weak, frail
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(player, player, new VulnerablePower(player, CAUGHT_VULNERABLE, false)));

        AbstractDungeon.actionManager
                .addToBottom(new ApplyPowerAction(player, player, new WeakPower(player, CAUGHT_WEAK, false)));

        AbstractDungeon.actionManager
                .addToBottom(new ApplyPowerAction(player, player, new FrailPower(player, CAUGHT_FRAIL, false)));

        // reset the warden's position
        AbstractPower wardenPower = player.getPower(WardenPower.POWER_ID);
        if (wardenPower == null) {
            logger.error("The player doesn't have the Warden power - something's wrong.");
            this.isDone = true;
            return;
        }

        AbstractDungeon
            .actionManager
            .addToBottom(new ReducePowerAction(player, player, wardenPower, wardenPower.amount - 1));

        // end the turn
        AbstractDungeon
            .actionManager
            .addToBottom(new EndTurnAction());

        this.isDone = true;
    }
}