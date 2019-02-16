package io.benstein.sts.hunted.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.benstein.sts.hunted.TheHuntedMod;
import io.benstein.sts.hunted.powers.WardenPower;

public class HazmatSuitRelic extends CustomRelic {
    public static final String ID = TheHuntedMod.makeID(HazmatSuitRelic.class.getSimpleName());
    public static final String IMG = "defaultModResources/images/relics/hazmat-suit.png";
    public static final String OUTLINE = "defaultModResources/images/relics/hazmat-suit-outline.png";

    private static final Logger logger = LogManager.getLogger(TheHuntedMod.class.getName());

    public HazmatSuitRelic() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), AbstractRelic.RelicTier.STARTER,
                AbstractRelic.LandingSound.FLAT);
    }

    @Override
    public void atBattleStartPreDraw() {
        logger.info("Pre-battle: applying Warden power");

        flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new WardenPower(AbstractDungeon.player)));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HazmatSuitRelic();
    }
}
