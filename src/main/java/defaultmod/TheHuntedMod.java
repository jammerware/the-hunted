package defaultmod;

import java.nio.charset.StandardCharsets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.interfaces.*;
import defaultmod.cards.*;
import defaultmod.characters.TheDefault;
import defaultmod.patches.AbstractCardEnum;
import defaultmod.patches.TheHuntedEnum;
import defaultmod.relics.*;
import defaultmod.services.PowerWatcherService;
import defaultmod.variables.WardenGainLoseAmount;

@SpireInitializer
public class TheHuntedMod implements 
    EditCardsSubscriber, 
    EditRelicsSubscriber, 
    EditStringsSubscriber,
    EditKeywordsSubscriber, 
    EditCharactersSubscriber,
    PostBattleSubscriber,
    PostInitializeSubscriber,
    PostPowerApplySubscriber {

    public static final Logger logger = LogManager.getLogger(TheHuntedMod.class.getName());

    // This is for the in-game mod settings panel.
    private static final String MODNAME = "io.benstein.sts.hunted";
    private static final String AUTHOR = "Jammer";
    private static final String DESCRIPTION = "Adds the Hunted, a new player class!";

    // Colors (RGB)
    // Character Color
    public static final Color DEFAULT_GRAY = CardHelper.getColor(255f, 89f, 0f);

    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_DEFAULT_GRAY = "defaultModResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = "defaultModResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = "defaultModResources/images/512/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY = "defaultModResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "defaultModResources/images/512/card_small_orb.png";

    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "defaultModResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "defaultModResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "defaultModResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "defaultModResources/images/1024/card_default_gray_orb.png";

    // Character assets
    private static final String THE_DEFAULT_BUTTON = "defaultModResources/images/charSelect/DefaultCharacterButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "defaultModResources/images/charSelect/DefaultCharacterPortraitBG.png";

    public static final String THE_DEFAULT_SHOULDER_1 = "defaultModResources/images/char/defaultCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "defaultModResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "defaultModResources/images/char/defaultCharacter/corpse.png";

    // Mod Badge - A small icon that appears in the mod settings menu next to your
    // mod.
    public static final String BADGE_IMAGE = "defaultModResources/images/Badge.png";

    // Atlas and JSON files for the Animations
    public static final String THE_DEFAULT_SKELETON_ATLAS = "defaultModResources/images/char/defaultCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "defaultModResources/images/char/defaultCharacter/skeleton.json";

    public TheHuntedMod() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);

        logger.info("Done subscribing");
        logger.info("Creating the color " + AbstractCardEnum.HUNTED_ORANGE.toString());

        BaseMod.addColor(
            AbstractCardEnum.HUNTED_ORANGE, 
            DEFAULT_GRAY, 
            DEFAULT_GRAY, 
            DEFAULT_GRAY, 
            DEFAULT_GRAY,
            DEFAULT_GRAY, 
            DEFAULT_GRAY, 
            DEFAULT_GRAY, 
            ATTACK_DEFAULT_GRAY, 
            SKILL_DEFAULT_GRAY, 
            POWER_DEFAULT_GRAY,
            ENERGY_ORB_DEFAULT_GRAY, 
            ATTACK_DEFAULT_GRAY_PORTRAIT, 
            SKILL_DEFAULT_GRAY_PORTRAIT,
            POWER_DEFAULT_GRAY_PORTRAIT, 
            ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, 
            CARD_ENERGY_ORB
        );

        logger.info("Done creating the color");
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing The Hunted. Hi. =========================");
        TheHuntedMod mod = new TheHuntedMod();
        logger.info("========================= /The Hunted is good to go./ =========================");
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheHuntedEnum.THE_HUNTED.toString());

        BaseMod.addCharacter(new TheDefault("the Default", TheHuntedEnum.THE_HUNTED), THE_DEFAULT_BUTTON,
                THE_DEFAULT_PORTRAIT, TheHuntedEnum.THE_HUNTED);

        logger.info("Added " + TheHuntedEnum.THE_HUNTED.toString());
    }

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        // Load the Mod Badge
        Texture badgeTexture = new Texture(BADGE_IMAGE);

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        settingsPanel
                .addUIElement(new ModLabel("TheHunted doesn't have any settings! An example of those may come later.",
                        400.0f, 700.0f, settingsPanel, (me) -> {
                        }));
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        logger.info("Done loading badge Image and mod options");

    }

    @Override
    public void receivePostBattle(AbstractRoom room) {
        PowerWatcherService.endCurrentBattle();
    }

    @Override
    public void receivePostPowerApplySubscriber(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        PowerWatcherService.powerApplied(source, target, power);
    }

    @Override
    public void receiveEditRelics() {
        logger.debug("Adding relics");
        BaseMod.addRelicToCustomPool(new BrokenManaclesRelic(), AbstractCardEnum.HUNTED_ORANGE);
        logger.debug("Done adding relics!");
    }

    @Override
    public void receiveEditCards() {
        // Add the custom dynamic variables (for warden gain/loss card descriptions)
        logger.info("Adding variables");
        BaseMod.addDynamicVariable(new WardenGainLoseAmount());

        // Add the cards
        logger.info("Adding cards");

        // things
        BaseMod.addCard(new AnemicSlapCard());
        BaseMod.addCard(new AQuietMomentCard());
        BaseMod.addCard(new BarTheDoorCard());
        BaseMod.addCard(new CapitalizeCard());
        BaseMod.addCard(new CorrodeCard());
        BaseMod.addCard(new CrashingBlowCard());
        BaseMod.addCard(new DefendCard());
        BaseMod.addCard(new DesperateStrikeCard());
        BaseMod.addCard(new EntropyProjectorCard());
        BaseMod.addCard(new EvadeCard());
        BaseMod.addCard(new HereGoesNothingCard());
        BaseMod.addCard(new HyperpropellantCard());
        BaseMod.addCard(new OffensiveReboundCard());
        BaseMod.addCard(new RansackCard());
        BaseMod.addCard(new ScrambleCard());
        BaseMod.addCard(new SeepThroughCard());
        BaseMod.addCard(new SharpTurnCard());
        BaseMod.addCard(new StrikeCard());
        BaseMod.addCard(new VolatileGrenadeCard());

        // Unlock the cards
        logger.info("Making sure the cards are unlocked.");
        UnlockTracker.unlockCard(AnemicSlapCard.ID);
        UnlockTracker.unlockCard(AQuietMomentCard.ID);
        UnlockTracker.unlockCard(BarTheDoorCard.ID);
        UnlockTracker.unlockCard(CapitalizeCard.ID);
        UnlockTracker.unlockCard(CorrodeCard.ID);
        UnlockTracker.unlockCard(CrashingBlowCard.ID);
        UnlockTracker.unlockCard(DefendCard.ID);
        UnlockTracker.unlockCard(DesperateStrikeCard.ID);
        UnlockTracker.unlockCard(EntropyProjectorCard.ID);
        UnlockTracker.unlockCard(EvadeCard.ID);
        UnlockTracker.unlockCard(HereGoesNothingCard.ID);
        UnlockTracker.unlockCard(HyperpropellantCard.ID);
        UnlockTracker.unlockCard(OffensiveReboundCard.ID);
        UnlockTracker.unlockCard(RansackCard.ID);
        UnlockTracker.unlockCard(ScrambleCard.ID);
        UnlockTracker.unlockCard(SeepThroughCard.ID);
        UnlockTracker.unlockCard(SharpTurnCard.ID);
        UnlockTracker.unlockCard(StrikeCard.ID);
        UnlockTracker.unlockCard(VolatileGrenadeCard.ID);

        logger.info("Done adding cards!");
    }

    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings");

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                "defaultModResources/localization/eng/TheHunted-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                "defaultModResources/localization/eng/TheHunted-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                "defaultModResources/localization/eng/TheHunted-Relic-Strings.json");

        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                "defaultModResources/localization/eng/TheHunted-Character-Strings.json");

        logger.info("Done editing strings");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal("defaultModResources/localization/eng/TheHunted-Keyword-Strings.json")
                .readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json,
                com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    public static String makeID(String idText) {
        return "TheHunted:" + idText;
    }
}