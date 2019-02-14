package io.benstein.sts.hunted.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import io.benstein.sts.hunted.TheHuntedMod;
import io.benstein.sts.hunted.patches.AbstractCardEnum;
import io.benstein.sts.hunted.services.PowerWatcherService;

public class EntropyProjectorCard extends CustomCard {
    public static final String ID = TheHuntedMod.makeID("EntropyProjector");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final CardType TYPE = AbstractCard.CardType.SKILL;

    private static final int COST = 5;
    private static final int BASE_BLOCK = 16;
    private static final int UPGRADE_BLOCK = 4;
    
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.HUNTED_ORANGE;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "defaultModResources/images/cards/Skill.png";

    public EntropyProjectorCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseBlock = block = BASE_BLOCK;
    }

    // this still fucks up if you play a debuff on your turn obv
    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();

        // let's say the starting cost is 5, the current cost is 4 (i.e. the player has played 1 debuff),
        // and they play a second debuff
        // (5 - 4) - 2 = -1
        this.modifyCostForCombat(COST - this.cost - PowerWatcherService.getPlayerDebuffCount());
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster m) {
        // block
        AbstractDungeon
            .actionManager
            .addToBottom(new GainBlockAction(player, player, block));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            initializeDescription();
        }
    }
}
