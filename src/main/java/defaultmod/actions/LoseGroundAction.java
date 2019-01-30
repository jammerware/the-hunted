package defaultmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import defaultmod.TheHunted;
import defaultmod.powers.WardenPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoseGroundAction extends AbstractGameAction {
    private AbstractCreature player;
    public static final Logger logger = LogManager.getLogger(TheHunted.class.getName());

    public LoseGroundAction(final AbstractCreature player) {
        this.player = player;
    }

    @Override
    public void update() {
        logger.info("Losing ground!");

        AbstractDungeon
                .actionManager
                .addToBottom(new ApplyPowerAction(
                    player,
                    player,
                    new WardenPower(player)
                ));
        this.isDone = true;
    }
}
