package defaultmod.cards;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.TheHuntedMod;
import defaultmod.actions.GainLoseGroundAction;
import defaultmod.patches.AbstractCardEnum;

/*
    https://trello.com/c/jSoivG5A/15-scramble
    Scramble
    Uncommon Skill | 2

    Draw a card.
    Gain 1 (2) energy.
    Gain 5 (10) block.
    Discard a random card.
    Gain (2) ground.
*/
public class ScrambleCard extends AbstractWardenGroundCard {
    public static final String ID = TheHuntedMod.makeID("Scramble");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;

    private static final int COST = 2;

    private static final int BASE_BLOCK = 5;
    private static final int UPGRADE_BLOCK = 5;
    private static final int BASE_ENERGY_RETURN = 1;
    private static final int UPGRADE_ENERGY_RETURN = 1;
    private static final int BASE_WARDEN_GROUND = -1;
    private static final int UPGRADE_WARDEN_GROUND = -1;
    
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.HUNTED_ORANGE;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "defaultModResources/images/cards/Skill.png";

    public ScrambleCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseBlock = block = BASE_BLOCK;
        baseMagicNumber = magicNumber = BASE_ENERGY_RETURN;
        baseWardenGainLoseAmount = wardenGainLoseAmount = BASE_WARDEN_GROUND;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // draw a card
        AbstractDungeon
            .actionManager
            .addToBottom(new DrawCardAction(player, 1));
        
        // gain 1 energy
        AbstractDungeon
            .actionManager
            .addToBottom(new GainEnergyAction(magicNumber));

        // gain block
        AbstractDungeon
            .actionManager
            .addToBottom(new GainBlockAction(player, player, block));

        // discard
        AbstractDungeon
            .actionManager
            .addToBottom(new DiscardAction(player, player, 1, true));

        // gain ground
        AbstractDungeon
            .actionManager
            .addToBottom(new GainLoseGroundAction(player, wardenGainLoseAmount));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            upgradeMagicNumber(UPGRADE_ENERGY_RETURN);
            upgradeWardenGainLoseAmount(UPGRADE_WARDEN_GROUND);
            initializeDescription();
        }
    }
}
