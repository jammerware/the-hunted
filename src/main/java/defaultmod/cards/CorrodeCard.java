package defaultmod.cards;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import basemod.abstracts.CustomCard;
import defaultmod.TheHuntedMod;
import defaultmod.patches.AbstractCardEnum;

/*
    https://trello.com/c/VADi8G4w/25-corrode
    Corrode
    Uncommon Attack | 1

    Remove half (all) Block from the enemy. Deal 7 damage.

*/
public class CorrodeCard extends CustomCard {
    public static final String ID = TheHuntedMod.makeID("Corrode");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;

    private static final int COST = 1;
    private static final int BASE_DAMAGE = 7;
    private static final int BASE_UPGRADE_FLAG = 0; // this is lazy but WHATEV LOL
    private static final int UPGRADE_FLAG = 1;
    
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.HUNTED_ORANGE;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "defaultModResources/images/cards/Attack.png";

    public CorrodeCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseDamage = damage = BASE_DAMAGE;
        baseMagicNumber = magicNumber = BASE_UPGRADE_FLAG;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // Remove half (all) block from the enemy
        if (monster.currentBlock > 0) {
            int blockToRemove = monster.currentBlock;

            // if the magic number (which is my shitty upgrade flag) is zero,
            // we only should be removing half the monster's block (rounded down)
            if (this.magicNumber == 0) {
                blockToRemove = monster.currentBlock / 2;
            }

            // remove the bloooooock
            AbstractDungeon
                .actionManager
                .addToBottom(new LoseBlockAction(player, player, blockToRemove));
        }

        // deal damage
        AbstractDungeon
            .actionManager
            .addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage, this.damageTypeForTurn)));
        
        
        // check for debuff
        boolean isDebuffed = false;
        for(AbstractPower power : monster.powers) {
            if (power.type == PowerType.DEBUFF) {
                isDebuffed = true;
                break;
            }
        }

        // gain energy and draw if isDebuffed
        if (isDebuffed) {
            // gain nrg
            AbstractDungeon
                .actionManager
                .addToBottom(new GainEnergyAction(this.magicNumber));

            // draw
            AbstractDungeon
                .actionManager
                .addToBottom(new DrawCardAction(player, 1));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            upgradeName();
            upgradeMagicNumber(UPGRADE_FLAG);
            initializeDescription();
        }
    }
}
