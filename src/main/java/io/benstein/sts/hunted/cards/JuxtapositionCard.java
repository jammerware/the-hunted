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

/* 
    Juxtaposition
    https://trello.com/c/EztyAMLe/42-matter-magnet
    Uncommon Skill | 2 (1)

    Exchange block amounts with your target.
*/
public class JuxtapositionCard extends CustomCard {
    public static final String ID = TheHuntedMod.makeID("Juxtaposition");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.HUNTED_ORANGE;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "defaultModResources/images/cards/Skill.png";

    public JuxtapositionCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    public void use(AbstractPlayer player, AbstractMonster monster) {
        int playerBlock = player.currentBlock;
        int targetBlock = monster.currentBlock;

        // player and target lose block
        player.loseBlock();
        monster.loseBlock();

        // grant the player their new block
        if (targetBlock > 0) {
            AbstractDungeon
                .actionManager
                .addToBottom(new GainBlockAction(player, player, targetBlock));
        }

        // grant target their new block
        if (playerBlock > 0) {
            AbstractDungeon
                .actionManager
                .addToBottom(new GainBlockAction(monster, player, playerBlock));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}