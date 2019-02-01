package defaultmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import defaultmod.TheHuntedMod;
import defaultmod.actions.GainLoseGroundAction;
import defaultmod.patches.AbstractCardEnum;

public class CrashingBlowCard extends AbstractWardenGroundCard {
    public static final String ID = TheHuntedMod.makeID("CrashingBlow");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.BASIC;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;

    private static final int COST = 1;
    private static final int BASE_DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int BASE_VULN = 1;
    private static final int UPGRADE_VULN = 1;
    private static final int BASE_GROUND_LOSE = 2;
    private static final int UPGRADE_GROUND_LOSE = -1;

    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.HUNTED_ORANGE;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "defaultModResources/images/cards/Attack.png";

    public CrashingBlowCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseDamage = damage = BASE_DAMAGE;
        baseMagicNumber = magicNumber = BASE_VULN;
        baseWardenGainLoseAmount = wardenGainLoseAmount = BASE_GROUND_LOSE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // deal damage
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));

        // apply vulnerability
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));

        // lose ground
        AbstractDungeon.actionManager.addToBottom(new GainLoseGroundAction(p, this.wardenGainLoseAmount));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(UPGRADE_VULN);
            upgradeWardenGainLoseAmount(UPGRADE_GROUND_LOSE);
            initializeDescription();
        }
    }
}
