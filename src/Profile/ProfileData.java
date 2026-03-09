package Profile;

import java.io.Serializable;

public class ProfileData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String profileName;
    private int currentLevel;
    private float playerHealth;
    private int enemyKillCount;
    private long playTime;
    private long createdAt;
    private long lastPlayedAt;

    public ProfileData(String profileName) {
        this.profileName = profileName;
        this.currentLevel = 0;
        this.playerHealth = 150;
        this.enemyKillCount = 0;
        this.playTime = 0;
        this.createdAt = System.currentTimeMillis();
        this.lastPlayedAt = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public float getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(float playerHealth) {
        this.playerHealth = playerHealth;
    }

    public int getEnemyKillCount() {
        return enemyKillCount;
    }

    public void setEnemyKillCount(int enemyKillCount) {
        this.enemyKillCount = enemyKillCount;
    }

    public long getPlayTime() {
        return playTime;
    }

    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getLastPlayedAt() {
        return lastPlayedAt;
    }

    public void setLastPlayedAt(long lastPlayedAt) {
        this.lastPlayedAt = lastPlayedAt;
    }

    @Override
    public String toString() {
        return profileName + " (Level " + (currentLevel + 1) + ")";
    }
}
