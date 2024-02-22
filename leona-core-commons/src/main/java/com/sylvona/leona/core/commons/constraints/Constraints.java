package com.sylvona.leona.core.commons.constraints;

import com.sylvona.leona.core.commons.streams.LINQ;
import com.sylvona.leona.core.commons.streams.LINQStream;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Provides methods for enforcing constraints on objects.
 */
public class Constraints {

    /**
     * Returns an {@link Enforcer<T>} instance for the provided object, allowing the enforcement of constraints on the object.
     *
     * @param <T>    the type of the object.
     * @param object the object to enforce constraints on.
     * @return       an {@link Enforcer<T>} instance for the provided object.
     */
    public static <T> Enforcer<T> validate(T object) {
       return new Enforcer<>(object);
    }

    /**
     * Returns a {@link GroupEnforcer<T>} instance for the provided collection, allowing the enforcement of constraints on each object in the collection.
     *
     * @param <T>        the type of the objects in the collection.
     * @param collection the collection of objects to enforce constraints on.
     * @return           a {@link GroupEnforcer<T>} instance for the provided collection.
     */
    public static <T> GroupEnforcer<T> validateAll(Collection<T> collection) {
        return new GroupEnforcer<>(LINQ.stream(collection));
    }

    /**
     * Returns a {@link GroupEnforcer<T>} instance for the provided array of objects, allowing the enforcement of constraints on each object in the array.
     *
     * @param <T>     the type of the objects in the array.
     * @param objects the array of objects to enforce constraints on.
     * @return        a {@link GroupEnforcer<T>} instance for the provided array of objects.
     */
    @SafeVarargs
    public static <T> GroupEnforcer<T> validateAll(T... objects) {
        return new GroupEnforcer<>(LINQ.stream(objects));
    }

    /**
     * Provides methods for enforcing constraints on a single object.
     *
     * @param <T> the type of the object to enforce constraints on
     */
    public static class Enforcer<T> {
        private static final String GENERIC_CONSTRAINT_MESSAGE = "Constraint violated for provided object.";
        private final T object;

        private Enforcer(T object) {
            this.object = object;
        }

        /**
         * Enforces the given predicate on the object, throwing a {@link ConstraintViolationException} with the provided message if the predicate is false.
         *
         * @param predicate                     the predicate to enforce
         * @param message                       the error message to throw if the predicate is false
         * @return                              the enforcer for chaining constraints
         * @throws ConstraintViolationException the exception thrown when the constraint is not met
         */
        public Enforcer<T> enforce(Predicate<T> predicate, String message) {
            if (!predicate.test(object)) {
                throw new ConstraintViolationException(this, message);
            }
            return this;
        }

        /**
         * Enforces the given predicate on the object, throwing a {@link ConstraintViolationException} with a generic message if the predicate is false.
         *
         * @param predicate                     the predicate to enforce
         * @return                              the enforcer for chaining constraints
         * @throws ConstraintViolationException the exception thrown when the constraint is not met
         */
        public Enforcer<T> enforce(Predicate<T> predicate) {
            return enforce(predicate, GENERIC_CONSTRAINT_MESSAGE);
        }

        /**
         * Enforces equality between the object and the expected value, throwing a {@link ConstraintViolationException}
         * with the provided message if the objects are not equal.
         *
         * @param expected                      the object to compare for equality.
         * @param message                       the error message to be thrown if the objects are not equal.
         * @return                              the enforcer for chaining constraints
         * @throws ConstraintViolationException the exception thrown when the constraint is not met.
         */
        public Enforcer<T> enforceEqual(T expected, String message) {
            return enforce(expected::equals, message);
        }

        /**
         * Enforces equality between the object and the expected value, throwing a {@link ConstraintViolationException}
         * with a generic message if the objects are not equal.
         *
         * @param expected                      the object to compare for equality.
         * @return                              the enforcer for chaining constraints
         * @throws ConstraintViolationException the exception thrown when the constraint is not met.
         */
        public Enforcer<T> enforceEqual(T expected) {
            return enforceEqual(expected, GENERIC_CONSTRAINT_MESSAGE);
        }

        /**
         * Enforces inequality between the object and the expected value, throwing a {@link ConstraintViolationException}
         * with the provided message if the objects are equal.
         *
         * @param notExpected                   the object to compare for inequality.
         * @param message                       the error message to be thrown if the objects are equal.
         * @return                              the enforcer for chaining constraints
         * @throws ConstraintViolationException the exception thrown when the constraint is not met.
         */
        public Enforcer<T> enforceNotEqual(T notExpected, String message) {
            return enforce(o -> !notExpected.equals(o), message);
        }

        /**
         * Enforces inequality between the object and the expected value, throwing a {@link ConstraintViolationException}
         * with a generic message if the objects are equal.
         *
         * @param notExpected                   the object to compare for inequality.
         * @return                              the enforcer for chaining constraints
         * @throws ConstraintViolationException the exception thrown when the constraint is not met.
         */
        public Enforcer<T> enforceNotEqual(T notExpected) {
            return enforceNotEqual(notExpected, GENERIC_CONSTRAINT_MESSAGE);
        }

        /**
         * Checks the given predicate on the object, returning true if the predicate is passed, otherwise false
         *
         * @param predicate the predicate to check.
         * @return          true if the predicate is true, otherwise false.
         */
        public boolean check(Predicate<T> predicate) {
            try {
                enforce(predicate);
            } catch (ConstraintViolationException constraintViolationException) {
                if (constraintViolationException.enforcer == this) return false;
                throw constraintViolationException;
            }
            return true;
        }

        /**
         * Checks equality between the object and the expected value, returning true if equal, otherwise false
         *
         * @param expected the object to compare for equality.
         * @return         true if the objects are equal, otherwise false.
         */
        public boolean checkEqual(T expected) {
            return check(expected::equals);
        }

        /**
         * Checks inequality between the object and the expected value, returning true if not equal, otherwise false
         *
         * @param notExpected the object to compare for equality.
         * @return            true if the objects are NOT equal, otherwise false.
         */
        public boolean checkNotEqual(T notExpected) {
            return check(o -> !notExpected.equals(o));
        }
    }

    /**
     * Provides methods for enforcing constraints on a collection of objects of type T.
     *
     * @param <T> the type of the objects in the collection.
     */
    public static class GroupEnforcer<T> {
        private final LINQStream<T> stream;

        private GroupEnforcer(LINQStream<T> stream) {
            this.stream = stream;
        }

        /**
         * Checks each item in the collection against the given enforce function, returning a list of items that fulfill the constraint.
         *
         * @param enforceFunction the function to apply to each item.
         * @return                a list of items that fulfill the constraint.
         */
        public List<T> check(Predicate<Enforcer<T>> enforceFunction) {
            return stream.toList(item -> enforceFunction.test(new Enforcer<>(item)));
        }

        /**
         * Enforces the given function on each item in the collection.
         *
         * @param enforceFunction               the function to apply to each item.
         * @throws ConstraintViolationException the exception thrown if ANY item violates a constraint.
         */
        public void enforce(Consumer<Enforcer<T>> enforceFunction) {
            stream.forEach(item -> enforceFunction.accept(new Enforcer<>(item)));
        }
    }
}
