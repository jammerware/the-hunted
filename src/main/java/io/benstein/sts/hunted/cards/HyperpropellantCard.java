package io.benstein.sts.hunted.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import io.benstein.sts.hunted.TheHuntedMod;
import io.benstein.sts.hunted.actions.WardenGainLoseGroundAction;
import io.benstein.sts.hunted.patches.AbstractCardEnum;

/*
    https://trello.com/c/jmZJZMPU/27-headlong-rush
    Hyperpropellant
    Rare Skill | X

    Gain 8 Block X (+2) times.
    Gain X ground.
*/
public class HyperpropellantCard extends AbstractWardenGroundCard {
    public static final String ID = TheHuntedMod.makeID("Hyperpropellant");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;

    // this is cost X
    private static final int COST = -1 ;

    private static final int BASE_BLOCK = 8;
    private static final int BASE_WARDEN_GROUND_GAIN = -1;
    private static final int BASE_X_MODIFIER = 0;
    private static final int UPGRADE_X_MODIFIER = 2;
    
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.HUNTED_ORANGE;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "defaultModResources/images/cards/Skill.png";

    public HyperpropellantCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseBlock = block = BASE_BLOCK;
        baseMagicNumber = magicNumber = BASE_X_MODIFIER;
        baseWardenGainLoseAmount = wardenGainLoseAmount = BASE_WARDEN_GROUND_GAIN;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        int numberOfInstances = this.energyOnUse + this.magicNumber;

        // have to do this bullshit because Chemical X (yes, this is how they do it too)
        // i'll encapsulate this if i have to do it again because that's how i get down
        if (player.hasRelic("Chemical X")) {
            numberOfInstances += 2;
            player.getRelic("Chemical X").flash();
        }

        // consume the E 
        player.energy.use(this.energyOnUse);

        // X times
        for(int i = 0; i < numberOfInstances; i++) {
            // gain block
            AbstractDungeon
                .actionManager
                .addToBottom(new GainBlockAction(player, player, block));
        }

        // gain X ground
        AbstractDungeon
            .actionManager
            .addToBottom(new WardenGainLoseGroundAction(player, -numberOfInstances));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            upgradeName();
            upgradeMagicNumber(UPGRADE_X_MODIFIER);
            initializeDescription();
        }
    }
}
