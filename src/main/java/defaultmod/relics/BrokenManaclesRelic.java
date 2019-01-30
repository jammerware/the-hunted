package defaultmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import defaultmod.TheHunted;
import defaultmod.actions.LoseGroundAction;

public class BrokenManaclesRelic extends CustomRelic {

    // ID, images, text.
    public static final String ID = TheHunted.makeID("BrokenManaclesRelic");
    public static final String IMG = "defaultModResources/images/relics/broken-manacles.png";
    public static final String OUTLINE = "defaultModResources/images/relics/broken-manacles-outline.png";

    public BrokenManaclesRelic() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), AbstractRelic.RelicTier.STARTER,
                AbstractRelic.LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new LoseGroundAction(AbstractDungeon.player));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new BrokenManaclesRelic();
    }
}
