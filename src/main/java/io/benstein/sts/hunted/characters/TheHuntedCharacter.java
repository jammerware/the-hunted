package io.benstein.sts.hunted.characters;

import static io.benstein.sts.hunted.TheHuntedMod.THE_DEFAULT_CORPSE;
import static io.benstein.sts.hunted.TheHuntedMod.THE_DEFAULT_SHOULDER_1;
import static io.benstein.sts.hunted.TheHuntedMod.THE_DEFAULT_SHOULDER_2;
import static io.benstein.sts.hunted.TheHuntedMod.THE_DEFAULT_SKELETON_ATLAS;
import static io.benstein.sts.hunted.TheHuntedMod.THE_DEFAULT_SKELETON_JSON;
import static io.benstein.sts.hunted.TheHuntedMod.makeID;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import io.benstein.sts.hunted.TheHuntedMod;
import io.benstein.sts.hunted.cards.CrashingBlowCard;
import io.benstein.sts.hunted.cards.DefendCard;
import io.benstein.sts.hunted.cards.EvadeCard;
import io.benstein.sts.hunted.cards.StrikeCard;
import io.benstein.sts.hunted.patches.AbstractCardEnum;
import io.benstein.sts.hunted.relics.BrokenManaclesRelic;

public class TheHuntedCharacter extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(TheHuntedMod.class.getName());

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    private static final String ID = makeID("TheHuntedCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // =============== TEXTURES OF BIG ENERGY ORB ===============

    public static final String[] orbTextures = { "defaultModResources/images/char/defaultCharacter/orb/layer1.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer2.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer3.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer4.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer5.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer6.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer1d.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer2d.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer3d.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer4d.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer5d.png", };

    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============
    public TheHuntedCharacter(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, "defaultModResources/images/char/defaultCharacter/orb/vfx.png", null,
                new SpriterAnimation(
                        "defaultModResources/images/char/defaultCharacter/Spriter/theDefaultAnimation.scml"));

        // =============== TEXTURES, ENERGY, LOADOUT =================

        initializeClass(null, // required call to load textures and setup energy/loadout.
                // I left these in TheHunted.java (Ctrl+click them to see where they are,
                // Ctrl+hover to see what they read.)
                THE_DEFAULT_SHOULDER_1, // campfire pose
                THE_DEFAULT_SHOULDER_2, // another campfire pose
                THE_DEFAULT_CORPSE, // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================

        // =============== ANIMATIONS =================

        loadAnimation(THE_DEFAULT_SKELETON_ATLAS, THE_DEFAULT_SKELETON_JSON, 1.0f);
        AnimationState.TrackEntry e = state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        // =============== /ANIMATIONS/ =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

    }

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
            NAMES[0], 
            TEXT[0], 
            STARTING_HP, 
            MAX_HP, 
            ORB_SLOTS, 
            STARTING_GOLD, 
            CARD_DRAW, 
            this,
            getStartingRelics(), 
            getStartingDeck(), 
            false
        );
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(StrikeCard.ID);
        retVal.add(StrikeCard.ID);
        retVal.add(StrikeCard.ID);
        retVal.add(StrikeCard.ID);

        retVal.add(DefendCard.ID);
        retVal.add(DefendCard.ID);
        retVal.add(DefendCard.ID);
        retVal.add(DefendCard.ID);

        retVal.add(CrashingBlowCard.ID);
        retVal.add(EvadeCard.ID);

        return retVal;
    }

    // Starting Relics
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(BrokenManaclesRelic.ID);
        UnlockTracker.markRelicAsSeen(BrokenManaclesRelic.ID);

        return retVal;
    }

    // Character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);                                                                                                       // Effect
    }

    // Character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp
    // respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.HUNTED_ORANGE;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return TheHuntedMod.DEFAULT_GRAY;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    // Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new StrikeCard();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name
    // parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheHuntedCharacter(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in
    // run history.
    @Override
    public Color getCardRenderColor() {
        return TheHuntedMod.DEFAULT_GRAY;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return TheHuntedMod.DEFAULT_GRAY;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] { 
            AbstractGameAction.AttackEffect.BLUNT_HEAVY,
            AbstractGameAction.AttackEffect.BLUNT_HEAVY, 
            AbstractGameAction.AttackEffect.BLUNT_HEAVY 
        };
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires
    // event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

}
