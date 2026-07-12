package me.miskynet.customGamemode.custom.index;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
