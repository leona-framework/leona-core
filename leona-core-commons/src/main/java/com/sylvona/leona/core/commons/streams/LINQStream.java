package com.sylvona.leona.core.commons.streams;

import com.sylvona.leona.core.commons.containers.Enumerated;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * A wrapper class for a standard Java {@link Stream} that provides implementations of {@link LINQ} stream functions.
 * @param <T> the type of the stream elements.
 * @see Stream
 * @see LINQ
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class LINQStream<T> implements Stream<T> {
    private final Stream<T> stream;

    @Override
    public LINQStream<T> filter(Predicate<? super T> predicate) {
        return new LINQStream<>(stream.filter(predicate));
    }

    @Override
    public <R> LINQStream<R> map(Function<? super T, ? extends R> mapper) {
        return new LINQStream<>(stream.map(mapper));
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return stream.mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return stream.mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return stream.mapToDouble(mapper);
    }

    @Override
    public <R> LINQStream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return new LINQStream<>(stream.flatMap(mapper));
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return stream.flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return stream.flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return stream.flatMapToDouble(mapper);
    }

    @Override
    public LINQStream<T> distinct() {
        return new LINQStream<>(stream.distinct());
    }

    @Override
    public LINQStream<T> sorted() {
        return new LINQStream<>(stream.sorted());
    }

    @Override
    public LINQStream<T> sorted(Comparator<? super T> comparator) {
        return new LINQStream<>(stream.sorted(comparator));
    }

    @Override
    public LINQStream<T> peek(Consumer<? super T> action) {
        return new LINQStream<>(stream.peek(action));
    }

    @Override
    public LINQStream<T> limit(long maxSize) {
        return new LINQStream<>(stream.limit(maxSize));
    }

    @Override
    public LINQStream<T> skip(long n) {
        return new LINQStream<>(stream.skip(n));
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        stream.forEach(action);
    }

    /**
     * Performs an action for each element of the stream, providing the index of the element as an additional parameter to the action.
     * The index starts at 0 and increments for each element.
     *
     * @param action The action to be performed for each element, accepting the index and the element itself.
     */
    public void forEach(BiConsumer<Integer, ? super T> action) {
        LINQ.forEach(stream, action);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        stream.forEachOrdered(action);
    }

    @Override
    public Object[] toArray() {
        return stream.toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return stream.toArray(generator);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return stream.reduce(identity, accumulator);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return stream.reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return stream.reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return stream.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return stream.collect(collector);
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return stream.min(comparator);
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return stream.max(comparator);
    }

    @Override
    public long count() {
        return stream.count();
    }

    /**
     * Counts the number of elements in the stream that satisfy the given predicate.
     *
     * @param predicate The predicate used for filtering..
     * @return The count of elements that satisfy the predicate.
     */
    public long count(Predicate<T> predicate) {
        return LINQ.count(stream, predicate);
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return stream.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return stream.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return stream.noneMatch(predicate);
    }

    @Override
    public Optional<T> findFirst() {
        return stream.findFirst();
    }

    /**
     * Finds the first element in the stream that satisfies the given predicate.
     *
     * @param predicate The predicate used for filtering.
     * @return An {@link Optional} containing the first matching element, or empty if none found.
     */
    public Optional<T> findFirst(Predicate<T> predicate) {
        return LINQ.findFirst(stream, predicate);
    }

    @Override
    public Optional<T> findAny() {
        return stream.findAny();
    }

    /**
     * Finds the first element in a stream.
     *
     * @return The first element of a stream.
     * @throws NoSuchElementException if no value is present
     */
    public T first() {
        return LINQ.first(stream);
    }

    /**
     * Finds the first element in the stream that satisfies the given predicate.
     *
     * @param predicate The predicate used for filtering.
     * @return The first element of a stream.
     * @throws NoSuchElementException if no value is present
     */
    public T first(Predicate<T> predicate) {
        return LINQ.first(stream, predicate);
    }

    /**
     * Finds the first element in the stream that satisfies the given predicate, or returns a default value
     * if no such element exists.
     *
     * @param predicate The predicate used for filtering.
     * @param fallback  The fallback value.
     * @return An {@link Optional} containing the first matching element, or the fallback value if none found.
     */
    public T firstOrDefault(Predicate<T> predicate, @Nullable T fallback) {
        return LINQ.firstOrDefault(stream, predicate, fallback);
    }

    /**
     * Finds the first element in the stream that satisfies the given predicate, or returns null if no such element exists.
     *
     * @param predicate The predicate used for filtering.
     * @return An {@link Optional} containing the first matching element, or null if none found.
     */
    public T firstOrDefault(Predicate<T> predicate) {
        return LINQ.firstOrDefault(stream, predicate);
    }

    /**
     * Finds the first element in a stream, or returns a fallback value if the stream is empty.
     *
     * @param fallback  The fallback value.
     * @return The first element of the stream, or the fallback value if the stream is empty.
     */
    public T firstOrDefault(@Nullable T fallback) {
        return LINQ.firstOrDefault(stream, fallback);
    }

    /**
     * Finds the first element in a stream, or returns null if the stream is empty.
     *
     * @return The first element of the stream, or null if the stream is empty.
     */
    public T firstOrDefault() {
        return LINQ.firstOrDefault(stream);
    }

    /**
     * Finds the first element in a stream, or returns a fallback value if the stream is empty.
     *
     * @param fallback  The fallback value supplier.
     * @param predicate The predicate used for filtering.
     * @return The first element of the stream, or the fallback value if the stream is empty.
     */
    public T firstOrGet(Predicate<T> predicate, Supplier<T> fallback) {
        return LINQ.firstOrGet(stream, predicate, fallback);
    }

    /**
     * Finds the last element in a stream.
     *
     * @return An {@link Optional} containing the last element, or empty if none found.
     */
    public Optional<T> findLast() {
        return LINQ.findLast(stream);
    }

    /**
     * Finds the last element in the stream that satisfies the given predicate.
     *
     * @param predicate The predicate used for filtering.
     * @return An {@link Optional} containing the last matching element, or empty if none found.
     */
    public Optional<T> findLast(Predicate<T> predicate) {
        return LINQ.findLast(stream, predicate);
    }

    /**
     * Finds the last element in a stream.
     *
     * @return The last element of a stream.
     * @throws NoSuchElementException if no value is present
     */
    public T last() {
        return LINQ.last(stream);
    }

    /**
     * Finds the last element in the stream that satisfies the given predicate.
     *
     * @param predicate The predicate used for filtering.
     * @return The last element of a stream.
     * @throws NoSuchElementException if no value is present
     */
    public T last(Predicate<T> predicate) {
        return LINQ.last(stream, predicate);
    }

    /**
     * Finds the last element in the stream that satisfies the given predicate, or returns a default value
     * if no such element exists.
     *
     * @param predicate The predicate used for filtering.
     * @param fallback  The fallback value.
     * @return An {@link Optional} containing the last matching element, or the fallback value if none found.
     */
    public T lastOrDefault(Predicate<T> predicate, @Nullable T fallback) {
        return LINQ.lastOrDefault(stream, predicate, fallback);
    }

    /**
     * Finds the last element in a stream, or returns a fallback value if the stream is empty.
     *
     * @param fallback  The fallback value.
     * @return The last element of the stream, or the fallback value if the stream is empty.
     */
    public T lastOrDefault(@Nullable T fallback) {
        return LINQ.lastOrDefault(stream, fallback);
    }

    /**
     * Finds the last element in the stream that satisfies the given predicate, or returns null if no such element exists.
     *
     * @param predicate The predicate used for filtering.
     * @return An {@link Optional} containing the last matching element, or null if none found.
     */
    public T lastOrDefault(Predicate<T> predicate) {
        return LINQ.lastOrDefault(stream, predicate);
    }


    /**
     * Finds the last element in a stream, or returns null if the stream is empty.
     *
     * @return The last element of the stream, or null if the stream is empty.
     */
    public T lastOrDefault() {
        return LINQ.lastOrDefault(stream);
    }

    /**
     * Finds the last element in a stream, or returns a fallback value if the stream is empty.
     *
     * @param fallback  The fallback value supplier.
     * @param predicate The predicate used for filtering.
     * @return The last element of the stream, or the fallback value if the stream is empty.
     */
    public T lastOrGet(Predicate<T> predicate, Supplier<T> fallback) {
        return LINQ.lastOrGet(stream, predicate, fallback);
    }

    @Override
    public Iterator<T> iterator() {
        return stream.iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return stream.spliterator();
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    @Override
    public LINQStream<T> sequential() {
        return new LINQStream<>(stream.sequential());
    }

    @Override
    public LINQStream<T> parallel() {
        return new LINQStream<>(stream.parallel());
    }

    @Override
    public LINQStream<T> unordered() {
        return new LINQStream<>(stream.unordered());
    }

    @Override
    public LINQStream<T> onClose(Runnable closeHandler) {
        return new LINQStream<>(stream.onClose(closeHandler));
    }

    @Override
    public void close() {
        stream.close();
    }

    /**
     * Concatenates a stream, creating a new stream that contains all elements from both input streams.
     *
     * @param other The other stream to concat.
     * @return A {@link LINQStream} containing elements from both input streams.
     */
    public LINQStream<T> concat(Stream<? extends T> other) {
        return LINQ.concat(stream, other);
    }

    /**
     * Concatenates a collection of streams, creating a new stream that contains elements from all sources.
     *
     * @param collection The collection to concatenate.
     * @return A {@link LINQStream} containing elements from the stream and the collection.
     */
    public LINQStream<T> concat(Collection<? extends T> collection) {
        return LINQ.concat(stream, collection);
    }

    /**
     * Concatenates multiple streams, creating a new stream that contains elements from all input streams.
     *
     * @param streams    The additional streams to concatenate.
     * @return A {@link LINQStream} containing elements from all input streams.
     */
    @SafeVarargs
    public final LINQStream<T> join(Stream<? extends T>... streams) {
        return LINQ.join(stream, streams);
    }

    /**
     * Returns whether any element of this stream equals the provided object. If the stream is empty this returns {@code false}.
     * @param object the element to check for its presence in the stream
     * @return true if the stream contained the element
     */
    public boolean contains(T object) {
        return LINQ.contains(stream, object);
    }

    /**
     * Creates a new stream by applying the given enumerator to each element of the input stream along with its index.
     * The index starts at 0 and increments for each element.
     *
     * @param enumerator The enumerator function to apply.
     * @param <R>        The type of elements in the resulting stream.
     * @return A {@link LINQStream} with elements produced by the enumerator.
     */
    public <R> LINQStream<R> enumerated(Enumerator<T, R> enumerator) {
        return LINQ.enumerated(stream, enumerator);
    }

    /**
     * Creates a new stream by pairing each element of the input stream with its index.
     * The index starts at 0 and increments for each element.
     *
     * @return A {@link LINQStream} with elements as tuples containing the index and the original element.
     */
    public LINQStream<Enumerated<T>> enumerated() {
        return LINQ.enumerated(stream, Enumerated::new);
    }

    /**
     * Filters a stream to include only elements of a specific type, creating a new stream of the target type.
     *
     * @param targetClass  The target class for type filtering.
     * @param <R>          The desired type for filtering and mapping.
     * @return A {@link LINQStream} containing elements of the specified target type.
     */
    public <R> LINQStream<R> ofType(Class<R> targetClass) {
        return LINQ.ofType(stream, targetClass);
    }

    /**
     * Reverses the order of elements in a stream, creating a new stream with elements in reverse order.
     * This method eagerly gets all items in the stream, returning a cached version of the stream.
     *
     * @return A {@link LINQStream} containing elements from the input stream in reverse order.
     */
    public LINQStream<T> reverse() {
        return LINQ.reverse(stream);
    }

    /**
     * Reduces a stream into only its distinct elements dictated by the provided key function. The order in-which elements
     * are identified for "distinctness" is not guarantee.
     *
     * @param keyFunction The function for generating keys used to filter distinct elements.
     * @return A {@link LINQStream} containing only distinct elements from the input stream.
     */
    public LINQStream<T> distinctBy(Function<T, Object> keyFunction) {
        return LINQ.distinctBy(stream, keyFunction);
    }

    /**
     * Collects the elements of a stream into a {@link List}.
     *
     * @param predicate The filtering predicate to apply before collecting.
     * @return A {@link List} containing the collected elements.
     */
    public List<T> toList(Predicate<T> predicate) {
        return LINQ.toList(stream, predicate);
    }

    /**
     * Collects the elements of a stream into a {@link Set}.
     *
     * @return A {@link Set} containing the collected elements.
     */
    public Set<T> toSet() { return LINQ.toSet(stream); }

    /**
     * Collects the elements of a stream into a {@link Set} after applying a filtering predicate.
     *
     * @param predicate The filtering predicate to apply before collecting.
     * @return A {@link Set} containing the collected elements.
     */
    public Set<T> toSet(Predicate<T> predicate) { return LINQ.toSet(stream, predicate); }

    /**
     * Collects the elements of a stream into a {@link Map} using provided key and value functions.
     *
     * @param keyFunction  The function to extract keys from elements.
     * @param valueFunction The function to extract values from elements.
     * @param <TKey>       The type of keys for the resulting map.
     * @param <TValue>     The type of values for the resulting map.
     * @return A {@link Map} containing the collected elements.
     */
    public <TKey, TValue> Map<TKey, TValue> toMap(Function<T, TKey> keyFunction, Function<T, TValue> valueFunction) {
        return LINQ.toMap(stream, keyFunction, valueFunction);
    }

    /**
     * Collects the elements of a stream into a {@link Map} using provided key and value functions,
     * with a specified supplier for the resulting map.
     *
     * @param keyFunction  The function to extract keys from elements.
     * @param valueFunction The function to extract values from elements.
     * @param mapSupplier  The supplier for the resulting map.
     * @param <TKey>       The type of keys for the resulting map.
     * @param <TValue>     The type of values for the resulting map.
     * @return A {@link Map} containing the collected elements.
     */
    public <TKey, TValue> Map<TKey, TValue> toMap(Function<T, TKey> keyFunction, Function<T, TValue> valueFunction, Supplier<Map<TKey, TValue>> mapSupplier) {
        return LINQ.toMap(stream, keyFunction, valueFunction, mapSupplier);
    }

    /**
     * Collects the elements of a stream into a specific type of collection.
     *
     * @param collection The supplier for the target collection.
     * @param <C>              The type of the target collection.
     * @return A collection containing the collected elements.
     */
    public <C extends Collection<T>> C collect(Supplier<C> collection) {
        return LINQ.collect(stream, collection);
    }
}
