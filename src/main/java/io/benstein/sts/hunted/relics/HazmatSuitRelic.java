package io.benstein.sts.hunted.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.benstein.sts.hunted.TheHuntedMod;
import io.benstein.sts.hunted.interfaces.PowerRemovedListener;
import io.benstein.sts.hunted.services.PowerWatcherService;

/* 
    Hazmat Suit
    https://trello.com/c/6w9AiEJm/32-hazmat-suit
    Common Relic

    When a debuff expires or is removed from you, gain 2 health.
*/
public class HazmatSuitRelic extends CustomRelic implements PowerRemovedListener {
    public static final String ID = TheHuntedMod.makeID(HazmatSuitRelic.class.getSimpleName());
    public static final String IMG = "defaultModResources/images/relics/hazmat-suit.png";
    public static final String OUTLINE = "defaultModResources/images/relics/hazmat-suit-outline.png";

    private static final Logger logger = LogManager.getLogger(TheHuntedMod.class.getName());

    public HazmatSuitRelic() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
        PowerWatcherService.registerPowerRemovedListener(this);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HazmatSuitRelic();
    }

    @Override
    protected void finalize() {
        PowerWatcherService.unregisterPowerRemovedListener(this);
    }

    public void onPowerRemoved(AbstractCreature owner, AbstractPower power) {
        if (owner.isPlayer && power.type == PowerType.DEBUFF && !PowerWatcherService.isPowerDebuffBlacklisted(power)) {
            logger.debug("Power " + power.name + " fell off the player, healing");
            this.flash();
            AbstractDungeon
                .actionManager
                .addToTop(new HealAction(owner, owner, 2));
        }
    }
}
