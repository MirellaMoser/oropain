package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

/**
 * Enum representing different times of the day for recording or scheduling
 * activities.
 * <p>
 * This enum is used to categorize or schedule activities, symptoms, or
 * observations based on the time of day,
 * providing a simple way to segment data or actions into morning, afternoon,
 * and evening.
 * </p>
 */
public enum ETimeOfDay {
    MORNING(1), // Represents the morning part of the day.
    AFTERNOON(2), // Represents the afternoon part of the day.
    EVENING(3); // Represents the evening part of the day.

    private final int value; // Numeric value associated with each time of day.

    /**
     * Constructor for the enum to set its numeric value.
     *
     * @param value The numeric value associated with the time of day.
     */
    private ETimeOfDay(int value) {
        this.value = value;
    }

    /**
     * Gets the numeric value associated with the time of day.
     *
     * @return The numeric value of the enum.
     */
    public int getValue() {
        return value;
    }

    /**
     * Overrides the default toString method to return the enum's numeric value as a
     * string.
     *
     * @return The string representation of the enum's numeric value.
     */
    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
