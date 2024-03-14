package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

public enum ETimeOfDay {
    NIGHT,MORNING,AFTERNOON,EVENING,UNSET;

    @Override
    public String toString() {
        return name();
    }
}
