package com.sylvona.leona.core.commons.streams;

import com.google.common.collect.Streams;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Utility class for operations on Java streams, inspired by LINQ in C#.
 */
public final class LINQ {
    /**
     * Counts the number of elements in the stream that satisfy the given predicate.
     *
     * @param stream    The input stream.
     * @param predicate The predicate used for filtering.
     * @param <T>       The type of elements in the stream.
     * @return The count of elements that satisfy the predicate.
     */
    public static <T> long count(Stream<T> stream, Predicate<T> predicate) {
        return stream.filter(predicate).count();
    }

    /**
     * Finds the first element in the stream that satisfies the given predicate.
     *
     * @param stream    The input stream.
     * @param predicate The predicate used for filtering.
     * @param <T>       The type of elements in the stream.
     * @return An {@link Optional} containing the first matching element, or empty if none found.
     */
    public static <T> Optional<T> findFirst(Stream<T> stream, Predicate<T> predicate) {
        return stream.filter(predicate).findFirst();
    }

    /**
     * Finds the first element in a stream.
     *
     * @param stream    The input stream.
     * @param <T>       The type of elements in the stream.
     * @return The first element of a stream.
     * @throws NoSuchElementException if no value is present
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static <T> T first(Stream<T> stream) {
        return stream.findFirst().get();
    }

    /**
     * Finds the first element in the stream that satisfies the given predicate.
     *
     * @param stream    The input stream.
     * @param predicate The predicate used for filtering.
     * @param <T>       The type of elements in the stream.
     * @return The first element of a stream.
     * @throws NoSuchElementException if no value is present
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static <T> T first(Stream<T> stream, Predicate<T> predicate) {
        return stream.filter(predicate).findFirst().get();
    }

    /**
     * Finds the first element in the stream that satisfies the given predicate, or returns a default value
     * if no such element exists.
     *
     * @param stream    The input stream.
     * @param predicate The predicate used for filtering.
     * @param fallback  The fallback value.
     * @param <T>       The type of elements in the stream.
     * @return An {@link Optional} containing the first matching element, or the fallback value if none found.
     */
    public static <T> T firstOrDefault(Stream<T> stream, Predicate<T> predicate, @Nullable T fallback) {
        return stream.filter(predicate).findFirst().orElse(fallback);
    }

    /**
     * Finds the first element in the stream that satisfies the given predicate, or returns null if no such element exists.
     *
     * @param stream    The input stream.
     * @param predicate The predicate used for filtering.
     * @param <T>       The type of elements in the stream.
     * @return An {@link Optional} containing the first matching element, or null if none found.
     */
    public static <T> T firstOrDefault(Stream<T> stream, Predicate<T> predicate) {
        return firstOrDefault(stream, predicate, null);
    }

    /**
     * Finds the first element in a stream, or returns a fallback value if the stream is empty.
     *
     * @param stream    The input stream.
     * @param fallback  The fallback value.
     * @param <T>       The type of elements in the stream.
     * @return The first element of the stream, or the fallback value if the stream is empty.
     */
    public static <T> T firstOrDefault(Stream<T> stream, @Nullable T fallback) {
        return stream.findFirst().orElse(fallback);
    }

    /**
     * Finds the first element in a stream, or returns null if the stream is empty.
     *
     * @param stream    The input stream.
     * @param <T>       The type of elements in the stream.
     * @return The first element of the stream, or null if the stream is empty.
     */
    public static <T> T firstOrDefault(Stream<T> stream) {
        return stream.findFirst().orElse(null);
    }

    /**
     * Finds the first element in a stream, or returns a fallback value if the stream is empty.
     *
     * @param stream    The input stream.
     * @param predicate The predicate used for filtering.
     * @param fallback  The fallback value supplier.
     * @param <T>       The type of elements in the stream.
     * @return The first element of the stream, or the fallback value if the stream is empty.
     */
    public static <T> T firstOrGet(Stream<T> stream, @NotNull Predicate<T> predicate, @NotNull Supplier<T> fallback) {
        return stream.filter(predicate).findFirst().orElseGet(fallback);
    }

    /**
     * Finds the last element in a stream.
     *
     * @param stream    The input stream.
     * @param <T>       The type of elements in the stream.
     * @return An {@link Optional} containing the last element, or empty if none found.
     */
    public static <T> Optional<T> findLast(Stream<T> stream) {
        return Streams.findLast(stream);
    }

    /**
     * Finds the last element in the stream that satisfies the given predicate.
     *
     * @param stream    The input stream.
     * @param predicate The predicate used for filtering.
     * @param <T>       The type of elements in the stream.
     * @return An {@link Optional} containing the last matching element, or empty if none found.
     */
    public static <T> Optional<T> findLast(Stream<T> stream, Predicate<T> predicate) {
        return Streams.findLast(stream.filter(predicate));
    }

    /**
     * Finds the last element in a stream.
     *
     * @param stream    The input stream.
     * @param <T>       The type of elements in the stream.
     * @return The last element of a stream.
     * @throws NoSuchElementException if no value is present
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static <T> T last(Stream<T> stream) {
        return Streams.findLast(stream).get();
    }

    /**
     * Finds the last element in the stream that satisfies the given predicate.
     *
     * @param stream    The input stream.
     * @param predicate The predicate used for filtering.
     * @param <T>       The type of elements in the stream.
     * @return The last element of a stream.
     * @throws NoSuchElementException if no value is present
     */
    public static <T> T last(Stream<T> stream, Predicate<T> predicate) {
        return Streams.findLast(stream.filter(predicate)).get();
    }

    /**
     * Finds the last element in the stream that satisfies the given predicate, or returns a default value
     * if no such element exists.
     *
     * @param stream    The input stream.
     * @param predicate The predicate used for filtering.
     * @param fallback  The fallback value.
     * @param <T>       The type of elements in the stream.
     * @return An {@link Optional} containing the last matching element, or the fallback value if none found.
     */
    public static <T> T lastOrDefault(Stream<T> stream, Predicate<T> predicate, @Nullable T fallback) {
        return Streams.findLast(stream.filter(predicate)).orElse(fallback);
    }

    /**
     * Finds the last element in the stream that satisfies the given predicate, or returns null if no such element exists.
     *
     * @param stream    The input stream.
     * @param predicate The predicate used for filtering.
     * @param <T>       The type of elements in the stream.
     * @return An {@link Optional} containing the last matching element, or null if none found.
     */
    public static <T> T lastOrDefault(Stream<T> stream, Predicate<T> predicate) {
        return lastOrDefault(stream, predicate, null);
    }

    /**
     * Finds the last element in a stream, or returns a fallback value if the stream is empty.
     *
     * @param stream    The input stream.
     * @param fallback  The fallback value.
     * @param <T>       The type of elements in the stream.
     * @return The last element of the stream, or the fallback value if the stream is empty.
     */
    public static <T> T lastOrDefault(Stream<T> stream, @Nullable T fallback) {
        return Streams.findLast(stream).orElse(fallback);
    }

    /**
     * Finds the last element in a stream, or returns null if the stream is empty.
     *
     * @param stream    The input stream.
     * @param <T>       The type of elements in the stream.
     * @return The last element of the stream, or null if the stream is empty.
     */
    public static <T> T lastOrDefault(Stream<T> stream) {
        return Streams.findLast(stream).orElse(null);
    }

    /**
     * Finds the last element in a stream, or returns a fallback value if the stream is empty.
     *
     * @param stream    The input stream.
     * @param predicate The predicate used for filtering.
     * @param fallback  The fallback value supplier.
     * @param <T>       The type of elements in the stream.
     * @return The last element of the stream, or the fallback value if the stream is empty.
     */
    public static <T> T lastOrGet(Stream<T> stream, Predicate<T> predicate, Supplier<T> fallback) {
        return Streams.findLast(stream.filter(predicate)).orElseGet(fallback);
    }

    /**
     * Concatenates two streams, creating a new stream that contains all elements from both input streams.
     *
     * @param stream1 The first input stream.
     * @param stream2 The second input stream.
     * @param <T>     The type of elements in the streams.
     * @return A {@link LINQStream} containing elements from both input streams.
     */
    public static <T> LINQStream<T> concat(Stream<? extends T> stream1, Stream<? extends T> stream2) {
        return new LINQStream<>(Stream.concat(stream1, stream2));
    }

    /**
     * Concatenates a stream with a collection, creating a new stream that contains elements from both input sources.
     *
     * @param stream1    The input stream.
     * @param collection The collection to concatenate.
     * @param <T>        The type of elements in the streams and collection.
     * @return A {@link LINQStream} containing elements from the stream and the collection.
     */
    public static <T> LINQStream<T> concat(Stream<T> stream1, Collection<? extends T> collection) {
        return concat(stream1, collection.stream());
    }

    /**
     * Concatenates multiple streams, creating a new stream that contains elements from all input streams.
     *
     * @param baseStream The base input stream.
     * @param streams    The additional streams to concatenate.
     * @param <T>        The type of elements in the streams.
     * @return A {@link LINQStream} containing elements from all input streams.
     */
    @SafeVarargs
    public static <T> LINQStream<T> join(Stream<T> baseStream, Stream<? extends T>... streams) {
        for (Stream<? extends T> stream : streams) {
            baseStream = Stream.concat(baseStream, stream);
        }
        return new LINQStream<>(baseStream);
    }

    /**
     * Returns whether any element of this stream equals the provided object. If the stream is empty this returns {@code false}.
     * @param stream the input stream.
     * @param object the element to check for its presence in the stream
     * @return true if the stream contained the element
     * @param <T> the type of elements in the stream.
     */
    public static <T> boolean contains(Stream<T> stream, T object) {
        return stream.anyMatch(o -> o.equals(object));
    }

    /**
     * Filters a stream to include only elements of a specific type, creating a new stream of the target type.
     *
     * @param stream       The input stream.
     * @param targetClass  The target class for type filtering.
     * @param <T>          The type of elements in the input stream.
     * @param <R>          The desired type for filtering and mapping.
     * @return A {@link LINQStream} containing elements of the specified target type.
     */
    public static <T, R> LINQStream<R> ofType(Stream<T> stream, Class<R> targetClass) {
        //noinspection unchecked
        return new LINQStream<>(stream)
                .filter(i -> targetClass.isAssignableFrom(i.getClass()))
                .map(i -> (R) i);
    }

    /**
     * Reverses the order of elements in a stream, creating a new stream with elements in reverse order.
     * This method eagerly gets all items in the stream, returning a cached version of the stream.
     *
     * @param stream  The input stream to reverse.
     * @param <T>     The type of elements in the stream.
     * @return A {@link LINQStream} containing elements from the input stream in reverse order.
     */
    public static <T> LINQStream<T> reverse(Stream<T> stream) {
        return stream.collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
            Collections.reverse(list);
            return new ListCollectedStream<>(list.stream(), list);
        }));
    }

    /**
     * Collects the elements of a stream into a {@link List}.
     *
     * @param stream   The input stream to collect.
     * @param predicate The filtering predicate to apply before collecting.
     * @param <T>      The type of elements in the stream.
     * @return A {@link List} containing the collected elements.
     */
    public static <T> List<T> toList(Stream<T> stream, Predicate<T> predicate) {
        return stream.filter(predicate).toList();
    }

    /**
     * Collects the elements of a stream into a {@link Set}.
     *
     * @param stream The input stream to collect.
     * @param <T>    The type of elements in the stream.
     * @return A {@link Set} containing the collected elements.
     */
    public static <T> Set<T> toSet(Stream<T> stream) {
        return stream.collect(Collectors.toSet());
    }

    /**
     * Collects the elements of a stream into a {@link Set} after applying a filtering predicate.
     *
     * @param stream    The input stream to collect.
     * @param predicate The filtering predicate to apply before collecting.
     * @param <T>       The type of elements in the stream.
     * @return A {@link Set} containing the collected elements.
     */
    public static <T> Set<T> toSet(Stream<T> stream, Predicate<T> predicate) {
        return stream.filter(predicate).collect(Collectors.toSet());
    }

    /**
     * Collects the elements of a stream into a {@link Map} using provided key and value functions.
     *
     * @param stream       The input stream to collect.
     * @param keyFunction  The function to extract keys from elements.
     * @param valueFunction The function to extract values from elements.
     * @param <T>          The type of elements in the stream.
     * @param <TKey>       The type of keys for the resulting map.
     * @param <TValue>     The type of values for the resulting map.
     * @return A {@link Map} containing the collected elements.
     */
    public static <T, TKey, TValue> Map<TKey, TValue> toMap(Stream<T> stream,
                                                            Function<T, TKey> keyFunction,
                                                            Function<T, TValue> valueFunction) {
        return stream.collect(Collectors.toMap(keyFunction, valueFunction));
    }

    /**
     * Collects the elements of a stream into a {@link Map} using provided key and value functions,
     * with a specified supplier for the resulting map.
     *
     * @param stream       The input stream to collect.
     * @param keyFunction  The function to extract keys from elements.
     * @param valueFunction The function to extract values from elements.
     * @param mapSupplier  The supplier for the resulting map.
     * @param <T>          The type of elements in the stream.
     * @param <TKey>       The type of keys for the resulting map.
     * @param <TValue>     The type of values for the resulting map.
     * @return A {@link Map} containing the collected elements.
     */
    public static <T, TKey, TValue> Map<TKey, TValue> toMap(Stream<T> stream,
                                                            Function<T, TKey> keyFunction,
                                                            Function<T, TValue> valueFunction,
                                                            Supplier<Map<TKey, TValue>> mapSupplier) {
        return stream.collect(Collectors.toMap(keyFunction, valueFunction, (m1, m2) -> m1, mapSupplier));
    }

    /**
     * Collects the elements of a stream into a specific type of collection.
     *
     * @param stream           The input stream to collect.
     * @param collectionSupplier The supplier for the target collection.
     * @param <C>              The type of the target collection.
     * @param <T>              The type of elements in the stream.
     * @return A collection containing the collected elements.
     */
    public static <C extends Collection<T>, T> C collect(Stream<T> stream, Supplier<C> collectionSupplier) {
        return stream.collect(Collectors.toCollection(collectionSupplier));
    }

    /**
     * Creates a stream of elements from the given array.
     *
     * @param array The input array.
     * @param <T>   The type of elements in the array.
     * @return A {@link LINQStream} containing the elements of the array.
     */
    public static <T> LINQStream<T> stream(T[] array) {
        return new LINQStream<>(Arrays.stream(array));
    }

    /**
     * Creates a stream of elements from the given collection.
     *
     * @param collection The input collection.
     * @param <T>        The type of elements in the collection.
     * @return A {@link LINQStream} containing the elements of the collection.
     */
    public static <T> LINQStream<T> stream(Collection<T> collection) {
        return new LINQStream<>(collection.stream());
    }

    /**
     * Creates a stream of elements from the given iterable.
     *
     * @param collection The input iterable.
     * @param <T>        The type of elements in the iterable.
     * @return A {@link LINQStream} containing the elements of the iterable.
     */
    public static <T> LINQStream<T> stream(Iterable<T> collection) {
        return new LINQStream<>(StreamSupport.stream(collection.spliterator(), false));
    }

    /**
     * Converts a general stream into a {@link LINQStream}.
     *
     * @param stream The input stream.
     * @param <T>    The type of elements in the stream.
     * @return A {@link LINQStream} containing the elements of the input stream.
     */
    public static <T> LINQStream<T> stream(Stream<T> stream) {
        if (stream instanceof LINQStream<T> linqStream) return linqStream;
        return new LINQStream<>(stream);
    }
}
