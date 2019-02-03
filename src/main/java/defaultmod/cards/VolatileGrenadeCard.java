package defaultmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import basemod.abstracts.CustomCard;
import defaultmod.TheHuntedMod;
import defaultmod.patches.AbstractCardEnum;

/* 
    https://trello.com/c/bc8LH0Oz/41-mercurial-grenade
    Volatile Grenade
    Rare Attack | 3

    Deal 24 (32) damage to all enemies.
    Apply 3 vulnerable to yourself.
*/
public class VolatileGrenadeCard extends CustomCard {
    public static final String ID = TheHuntedMod.makeID(VolatileGrenadeCard.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = "defaultModResources/images/cards/Attack.png";
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.HUNTED_ORANGE;

    private static final int COST = 3;
    private static final int DAMAGE = 24;
    private static final int UPGRADE_PLUS_DMG = 8;
    private static final int BASE_VULN = 3;

    public VolatileGrenadeCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = this.damage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = BASE_VULN;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // BONK!
        AbstractDungeon
            .actionManager
            .addToBottom(
                new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AttackEffect.SLASH_HEAVY)
            );

        // apply self vuln
        AbstractDungeon
            .actionManager
            .addToBottom(new ApplyPowerAction(p, p, new VulnerablePower(p, this.magicNumber, false)));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
