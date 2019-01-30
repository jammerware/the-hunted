package defaultmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import defaultmod.TheHunted;
import defaultmod.actions.GainGroundAction;
import defaultmod.patches.AbstractCardEnum;

public class EvadeCard extends CustomCard {
    public static final String ID = TheHunted.makeID("Evade");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.BASIC;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;

    private static final int COST = 1;
    private static final int BASE_BLOCK = 1;
    private static final int UPGRADE_BLOCK = 2;
    private static final int BASE_WEAK = 1;
    private static final int UPGRADE_WEAK = 1;

    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.HUNTED_ORANGE;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "defaultModResources/images/cards/Skill.png";

    public EvadeCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BASE_BLOCK;
        baseMagicNumber = magicNumber = BASE_WEAK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // apply block
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));

        // apply weak
        AbstractDungeon.actionManager
                .addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));

        // gain ground
        AbstractDungeon.actionManager.addToBottom(new GainGroundAction(p));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            upgradeMagicNumber(UPGRADE_WEAK);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
