package me.miskynet.customGamemode.custom.index.utils;

import me.miskynet.customGamemode.custom.index.IndexMenu;

import java.util.ArrayList;

/**
 * Represents a level in the {@link IndexMenu}. Each {@link IndexLevel} has a level, a required level to unlock all the rewards and a list of {@link Reward}'s.
 * */
public class IndexLevel {

    private final int level;
    private final int requiredLevel;
    private final ArrayList<Reward> rewards = new ArrayList<>();

    /**
     * @param level The level of the {@link IndexLevel}. This will be displayed in the {@link IndexMenu}
     * @param requiredLevel The required level to unlock all the rewards of the {@link IndexLevel}.
     * */
    public IndexLevel(int level, int requiredLevel) {
        this.level = level;
        this.requiredLevel = requiredLevel;
    }

    /**
     * Gets the level of the {@link IndexLevel}
     *
     * @return Level as {@link Integer}
     * */
    public int getLevel() {
        return this.level;
    }

    /**
     * Gets the required level to unlock all the rewards of the {@link IndexLevel}
     *
     * @return Required level as {@link Integer}
     * */
    public int getRequiredLevel() {
        return this.requiredLevel;
    }

    /**
     * Adds a {@link Reward} to the {@link IndexLevel}
     *
     * @param reward The {@link Reward} to add
     * */
    public void addReward(Reward reward) {
        this.rewards.add(reward);
    }

    /**
     * Gets the rewards of the {@link IndexLevel}
     *
     * @return Rewards as {@link ArrayList<Reward>}
     * */
    public ArrayList<Reward> getRewards() {
        return rewards;
    }

}
