enum DangerLevel {
    HIGH(3),
    MEDIUM(2),
    LOW(1);

    int level;

    DangerLevel(int dangerLevel) {
        this.level = dangerLevel;
    }

    public int getLevel() {
        return level;
    }
}