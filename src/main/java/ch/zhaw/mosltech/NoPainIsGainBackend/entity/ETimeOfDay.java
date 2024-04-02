package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

public enum ETimeOfDay {
    MORNING(1),AFTERNOON(2),EVENING(3);

    private final int value;
    private ETimeOfDay(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return Integer.toString(value);
    }
}
