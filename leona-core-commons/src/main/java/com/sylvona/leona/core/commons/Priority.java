package com.sylvona.leona.core.commons;

/**
 * An integer wrapper class for various, arbitrary, priorities. Higher input values represent
 * higher priorities.
 * <p>
 * This class contains a few constant priorities but additional user priorities can easily be created. The pre-made
 * priorities begin at value 0 and end at 10,000.
 *
 * @param value The priority value, this value can be negative to achieve priorities lower than {@link Priority#LAST Priority.LAST}
 * @author Evan Cowin
 * @since 0.0.1
 */
public record Priority(int value) implements Comparable<Priority> {
    /**
     * The value for the highest priority.
     */
    public static final int FIRST_VALUE = 10000;
    /**
     * The value for a very high priority.
     */
    public static final int VERY_HIGH_VALUE = 8000;
    /**
     * The value for a high priority.
     */
    public static final int HIGH_VALUE = 6500;
    /**
     * The value for a normal priority.
     */
    public static final int NORMAL_VALUE = 5000;
    /**
     * The value for a low priority.
     */
    public static final int LOW_VALUE = 4500;
    /**
     * The value for a very low priority.
     */
    public static final int VERY_LOW_VALUE = 2000;
    /**
     * The value for the lowest priority.
     */
    public static final int LAST_VALUE = 0;

    /**
     * The highest priority.
     */
    public static final Priority FIRST = new Priority(FIRST_VALUE);
    /**
     * A very high priority.
     */
    public static final Priority VERY_HIGH = new Priority(VERY_HIGH_VALUE);
    /**
     * A high priority.
     */
    public static final Priority HIGH = new Priority(HIGH_VALUE);
    /**
     * A normal priority.
     */
    public static final Priority NORMAL = new Priority(NORMAL_VALUE);
    /**
     * A low priority.
     */
    public static final Priority LOW = new Priority(LOW_VALUE);
    /**
     * A very low priority.
     */
    public static final Priority VERY_LOW = new Priority(VERY_LOW_VALUE);
    /**
     * The lowest priority.
     */
    public static final Priority LAST = new Priority(LAST_VALUE);

    /**
     * Returns a priority constrained to the range 0 to 10,000. If you need to create priorities outside this range
     * use the default {@link #Priority(int) constructor}.
     * @param value The value of the priority (within the inclusive range 0 to 10,000)
     * @return A new {@link Priority}
     * @throws IllegalArgumentException If the provided value is less than the inclusive range 0 to 10,000.
     */
    public static Priority of(int value) {
        if (value < 0 || value > 10000) throw new IllegalArgumentException("Priority value cannot be less than zero or greater than 10,000.");
        return switch (value) {
            case FIRST_VALUE -> FIRST;
            case VERY_HIGH_VALUE -> VERY_HIGH;
            case HIGH_VALUE -> HIGH;
            case NORMAL_VALUE -> NORMAL;
            case LOW_VALUE -> LOW;
            case VERY_LOW_VALUE -> VERY_LOW;
            case LAST_VALUE -> LAST;
            default -> new Priority(value);
        };
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Priority priority = (Priority) o;
        return value == priority.value;
    }

    @Override
    public int compareTo(Priority o) {
        return Integer.compare(value, o.value);
    }
}
