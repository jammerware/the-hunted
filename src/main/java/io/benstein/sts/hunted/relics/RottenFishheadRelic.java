package io.benstein.sts.hunted.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.benstein.sts.hunted.TheHuntedMod;
import io.benstein.sts.hunted.actions.WardenGainLoseGroundAction;
import io.benstein.sts.hunted.interfaces.ShuffleListener;
import io.benstein.sts.hunted.services.ShuffleService;

public class RottenFishheadRelic extends CustomRelic implements ShuffleListener {
    public static final String ID = TheHuntedMod.makeID("RottenFishheadRelic");
    public static final String IMG = "defaultModResources/images/relics/rotten-fishhead.png";
    public static final String OUTLINE = "defaultModResources/images/relics/rotten-fishhead-outline.png";
    private static final Logger logger = LogManager.getLogger(TheHuntedMod.class.getName());

    private ShuffleService _shuffleService;

    public RottenFishheadRelic(ShuffleService shuffleService) {
        super(
            ID, 
            ImageMaster.loadImage(IMG), 
            new Texture(OUTLINE), 
            AbstractRelic.RelicTier.UNCOMMON,
            AbstractRelic.LandingSound.SOLID
        );

        this._shuffleService = shuffleService;
        this._shuffleService.registerShuffleListener(this);
    }

	@Override
	public void onShuffle(CardGroupType groupType) {
        this.flash();
        
        logger.debug("Player has rotten fishhead and shuffled, gain some ground yo");
        AbstractDungeon
            .actionManager
            .addToBottom(new WardenGainLoseGroundAction(AbstractDungeon.player, -1));
	}
}
