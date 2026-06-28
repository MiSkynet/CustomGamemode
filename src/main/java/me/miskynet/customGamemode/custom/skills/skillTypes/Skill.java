package me.miskynet.customGamemode.custom.skills.skillTypes;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.config.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the base for all other skills.
 * */
public class Skill {

    public enum SkillType {
        FIGHTING,
        MINING,
        FARMING
    }

    public static NamespacedKey skillKey = new NamespacedKey(Main.getInstance(), "skill");

    private final SkillType skillType;
    private final int maxLevel;
    private final int levelUpOnLevel;
    private final Component name;
    private final List<Component> description;

    public static ArrayList<Skill> skillsList = new ArrayList<>();

    public Skill(SkillType skillType, int maxLevel, int levelUpOnLevel, Component name, List<Component> description) {
        this.skillType = skillType;
        this.maxLevel = maxLevel;
        this.levelUpOnLevel = levelUpOnLevel;
        this.name = name;
        this.description = description;
        skillsList.add(this);
    }

    /**
     * Gets the {@link SkillType} of a {@link Skill}
     *
     * @return {@link SkillType}
     * */
    public SkillType getSkillType() {
        return this.skillType;
    }

    /**
     * Gets the maximum level of the {@link Skill}
     *
     * @return Maximum level of the {@link Skill}
     * */
    public int getMaxLevel() {
        return this.maxLevel;
    }

    /**
     * Gets the level of when to level up
     *
     * @return level as int
     * */
    public int getLevelUpOnLevel() {
        return this.levelUpOnLevel;
    }

    /**
     * Gets the name of the {@link Skill}
     *
     * @return Name of the {@link Skill}
     * */
    public Component getName() {
        return this.name;
    }

    /**
     * Gets the description of the {@link Skill}
     *
     * @return A {@link List} of the {@link Component}
     * */
    public List<Component> getDescription() {
        return this.description;
    }

    /**
     * Gets the current Level of a {@link Player} of the {@link Skill}
     *
     * @param player The player
     * @return Level of the players {@link FightingSkill}
     * */
    public static Integer getCurrentPlayerLevel(SkillType skillType, Player player) {
        return PlayerData.get(PlayerData.FileType.SKILLS, player.getUniqueId()).getInt("skills." + skillType.name().toLowerCase() + ".currentLevel");
    }

    /**
     * Gets the current XP of a {@link Player} of the {@link Skill}
     *
     * @param player The player
     * @return XP of the players {@link FightingSkill}
     * */
    public static Integer getCurrentPlayerXP(SkillType skillType, Player player) {
        return PlayerData.get(PlayerData.FileType.SKILLS, player.getUniqueId()).getInt("skills." + skillType.name().toLowerCase() + ".currentXP");
    }

    /**
     * Increases the current Level of a {@link Player} of the {@link Skill} and resets the current XP to 0
     *
     * @param skillType The type of skill to increase the level of
     * @param player The player whose level should be increased
     * */
    public static void increasePlayerLevel(SkillType skillType, Player player) {
        Integer currentPlayerLevel = getCurrentPlayerLevel(skillType, player);

        PlayerData.set(PlayerData.FileType.SKILLS, player.getUniqueId(), "skills." + skillType.name().toLowerCase() + ".currentLevel", currentPlayerLevel + 1);
        PlayerData.set(PlayerData.FileType.SKILLS, player.getUniqueId(), "skills." + skillType.name().toLowerCase() + ".currentXP", 0);
    }


    /**
     * Increases the current XP of a {@link Player} of the {@link Skill}
     *
     * @param skillType The type of skill to increase the XP of
     * @param player The player whose XP should be increased
     * */
    public static void increasePlayerXP(SkillType skillType, Player player) {
        Integer currentPlayerXP = getCurrentPlayerXP(skillType, player);

        PlayerData.set(PlayerData.FileType.SKILLS, player.getUniqueId(), "skills." + skillType.name().toLowerCase() + ".currentXP", currentPlayerXP + 1);
    }

    public static Skill getSkillBySkillType(SkillType skillType) {
        for (Skill skill : skillsList) {
            if (skill.getSkillType().equals(skillType)) {
                return skill;
            }
        }
        return null;
    }

}
