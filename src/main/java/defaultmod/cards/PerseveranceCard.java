package defaultmod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.TheHuntedMod;
import defaultmod.actions.WardenGainLoseGroundAction;
import defaultmod.patches.AbstractCardEnum;

/* 
    https://trello.com/c/svri6hEL/38-perseverance
    Perseverance
    Uncommon Power | 1

    When you shuffle your deck, gain (2) ground.

*/
public class PerseveranceCard extends AbstractWardenGroundCard {
    public static final String ID = TheHuntedMod.makeID("Perserverance");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;

    private static final int COST = 1;
    private static final int BASE_WARDEN_GROUND = -1;
    private static final int UPGRADE_WARDEN_GROUND = -1;
    
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.HUNTED_ORANGE;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "defaultModResources/images/cards/Power.png";

    public PerseveranceCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseWardenGainLoseAmount = wardenGainLoseAmount = BASE_WARDEN_GROUND;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // draw
        AbstractDungeon
            .actionManager
            .addToBottom(new DrawCardAction(player, magicNumber));
        
        // lose ground
        AbstractDungeon
            .actionManager
            .addToBottom(new WardenGainLoseGroundAction(player, wardenGainLoseAmount));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeWardenGainLoseAmount(UPGRADE_WARDEN_GROUND);
            initializeDescription();
        }
    }
}
