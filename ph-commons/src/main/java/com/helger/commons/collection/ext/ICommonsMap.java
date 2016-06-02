/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.commons.collection.ext;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.lang.ICloneable;
import com.helger.commons.state.EChange;

public interface ICommonsMap <KEYTYPE, VALUETYPE> extends
                             Map <KEYTYPE, VALUETYPE>,
                             ICloneable <ICommonsMap <KEYTYPE, VALUETYPE>>,
                             Serializable
{
  /**
   * @return A new non-<code>null</code> set with all keys.
   * @see #keySet()
   */
  @Nonnull
  @ReturnsMutableCopy
  default ICommonsSet <KEYTYPE> copyOfKeySet ()
  {
    return new CommonsHashSet <> (keySet ());
  }

  /**
   * @return A new non-<code>null</code> set with all values.
   * @see #values()
   * @see #copyOfValues(Predicate)
   * @see #copyOfValuesMapped(Function)
   * @see #copyOfValuesMapped(Predicate, Function)
   */
  @Nonnull
  @ReturnsMutableCopy
  default ICommonsList <VALUETYPE> copyOfValues ()
  {
    return new CommonsArrayList <> (values ());
  }

  /**
   * Create a copy of all values matching the passed filter.
   *
   * @param aFilter
   *        The filter to be applied. May be <code>null</code>.
   * @return A new list with all matching values. If no filter is provided the
   *         returned value is identical as of {@link #copyOfValues()}
   * @see #values()
   * @see #copyOfValues()
   * @see #copyOfValuesMapped(Function)
   * @see #copyOfValuesMapped(Predicate, Function)
   */
  @Nonnull
  @ReturnsMutableCopy
  default ICommonsList <VALUETYPE> copyOfValues (@Nullable final Predicate <? super VALUETYPE> aFilter)
  {
    if (aFilter == null)
      return copyOfValues ();
    return CollectionHelper.newList (values (), aFilter);
  }

  /**
   * Create a copy of all values after applying the passed mapping function.
   *
   * @param aMapper
   *        The mapping function to be applied. May not be <code>null</code>.
   * @return A new list with all mapped values.
   * @param <DSTTYPE>
   *        The destination type to be mapped to
   * @see #values()
   * @see #copyOfValues()
   * @see #copyOfValuesMapped(Function)
   * @see #copyOfValuesMapped(Predicate, Function)
   */
  @Nonnull
  @ReturnsMutableCopy
  default <DSTTYPE> ICommonsList <DSTTYPE> copyOfValuesMapped (@Nonnull final Function <? super VALUETYPE, ? extends DSTTYPE> aMapper)
  {
    return CollectionHelper.newListMapped (values (), aMapper);
  }

  /**
   * Create a copy of all values matching the passed filter which are then
   * converted using the provided function.
   *
   * @param aFilter
   *        The filter to be applied. May be <code>null</code>.
   * @param aMapper
   *        The mapping function to be applied. May not be <code>null</code>.
   * @return A new list with all matching converted values. If no filter is
   *         provided the returned value is identical as of
   *         {@link #copyOfValuesMapped(Function)}
   * @param <DSTTYPE>
   *        The destination type to be mapped to
   * @see #values()
   * @see #copyOfValues()
   * @see #copyOfValues(Predicate)
   * @see #copyOfValuesMapped(Predicate, Function)
   */
  @Nonnull
  @ReturnsMutableCopy
  default <DSTTYPE> ICommonsList <DSTTYPE> copyOfValuesMapped (@Nullable final Predicate <? super VALUETYPE> aFilter,
                                                               @Nonnull final Function <? super VALUETYPE, ? extends DSTTYPE> aMapper)
  {
    if (aFilter == null)
      return copyOfValuesMapped (aMapper);
    return CollectionHelper.newListMapped (values (), aFilter, aMapper);
  }

  /**
   * @return A new non-<code>null</code> copy of the entry set.
   */
  @Nonnull
  @ReturnsMutableCopy
  default ICommonsSet <Map.Entry <KEYTYPE, VALUETYPE>> copyOfEntrySet ()
  {
    return new CommonsHashSet <> (entrySet ());
  }

  /**
   * @return <code>true</code> if the map is not empty, <code>false</code>
   *         otherwise.
   */
  default boolean isNotEmpty ()
  {
    return !isEmpty ();
  }

  /**
   * Get the first element of this map.
   *
   * @return <code>null</code> if the map is <code>null</code> or empty, the
   *         first element otherwise.
   */
  @Nullable
  default Map.Entry <KEYTYPE, VALUETYPE> getFirstEntry ()
  {
    return isEmpty () ? null : entrySet ().iterator ().next ();
  }

  /**
   * Get the first key of this map.
   *
   * @return <code>null</code> if the map is <code>null</code> or empty, the
   *         first key otherwise.
   */
  @Nullable
  default KEYTYPE getFirstKey ()
  {
    return isEmpty () ? null : keySet ().iterator ().next ();
  }

  /**
   * Get the first value of this map.
   *
   * @return <code>null</code> if the map is empty, the first value otherwise.
   */
  @Nullable
  default VALUETYPE getFirstValue ()
  {
    return isEmpty () ? null : values ().iterator ().next ();
  }

  /**
   * Find the first entry that matches the passed filter.
   *
   * @param aFilter
   *        The filter to be applied. May be <code>null</code>.
   * @return <code>null</code> if no matching element was found. I no filter was
   *         provided, the result is the same as {@link #getFirstEntry()}.
   */
  @Nullable
  default Map.Entry <KEYTYPE, VALUETYPE> findFirstEntry (@Nullable final Predicate <? super Map.Entry <KEYTYPE, VALUETYPE>> aFilter)
  {
    return CollectionHelper.findFirst (entrySet (), aFilter);
  }

  /**
   * Find the first key that matches the passed filter.
   *
   * @param aFilter
   *        The filter to be applied. May be <code>null</code>.
   * @return <code>null</code> if no matching element was found. I no filter was
   *         provided, the result is the same as {@link #getFirstKey()}.
   */
  @Nullable
  default KEYTYPE findFirstKey (@Nullable final Predicate <? super Map.Entry <KEYTYPE, VALUETYPE>> aFilter)
  {
    final Map.Entry <KEYTYPE, VALUETYPE> aEntry = findFirstEntry (aFilter);
    return aEntry == null ? null : aEntry.getKey ();
  }

  /**
   * Find the first value that matches the passed filter.
   *
   * @param aFilter
   *        The filter to be applied. May be <code>null</code>.
   * @return <code>null</code> if no matching element was found. I no filter was
   *         provided, the result is the same as {@link #getFirstValue()}.
   */
  @Nullable
  default VALUETYPE findFirstValue (@Nullable final Predicate <? super Map.Entry <KEYTYPE, VALUETYPE>> aFilter)
  {
    final Map.Entry <KEYTYPE, VALUETYPE> aEntry = findFirstEntry (aFilter);
    return aEntry == null ? null : aEntry.getValue ();
  }

  /**
   * Check if at least one entry matches the passed filter.
   *
   * @param aFilter
   *        The filter to be applied. May be <code>null</code>.
   * @return <code>true</code> if the map is not empty and contains at least one
   *         element matching the filter, <code>false</code> otherwise. If no
   *         filter is provided the return value is identical to
   *         {@link #isNotEmpty()}.
   */
  default boolean containsAnyEntry (@Nullable final Predicate <? super Map.Entry <KEYTYPE, VALUETYPE>> aFilter)
  {
    return CollectionHelper.containsAny (entrySet (), aFilter);
  }

  /**
   * Check if at least one key matches the passed filter.
   *
   * @param aFilter
   *        The filter to be applied. May be <code>null</code>.
   * @return <code>true</code> if the map is not empty and contains at least one
   *         key matching the filter, <code>false</code> otherwise. If no filter
   *         is provided the return value is identical to {@link #isNotEmpty()}.
   */
  default boolean containsAnyKey (@Nullable final Predicate <? super KEYTYPE> aFilter)
  {
    return CollectionHelper.containsAny (keySet (), aFilter);
  }

  /**
   * Check if at least one value matches the passed filter.
   *
   * @param aFilter
   *        The filter to be applied. May be <code>null</code>.
   * @return <code>true</code> if the map is not empty and contains at least one
   *         value matching the filter, <code>false</code> otherwise. If no
   *         filter is provided the return value is identical to
   *         {@link #isNotEmpty()}.
   */
  default boolean containsAnyValue (@Nullable final Predicate <? super VALUETYPE> aFilter)
  {
    return CollectionHelper.containsAny (values (), aFilter);
  }

  /**
   * Invoke the provided consumer on each key.
   *
   * @param aConsumer
   *        The consumer to be invoked. May not be <code>null</code>.
   */
  default void forEachKey (@Nonnull final Consumer <? super KEYTYPE> aConsumer)
  {
    forEach ( (k, v) -> aConsumer.accept (k));
  }

  /**
   * Invoke the provided consumer on each value.
   *
   * @param aConsumer
   *        The consumer to be invoked. May not be <code>null</code>.
   */
  default void forEachValue (@Nonnull final Consumer <? super VALUETYPE> aConsumer)
  {
    forEach ( (k, v) -> aConsumer.accept (v));
  }

  /**
   * Invoke the provided consumer on each entry (pair of key and value) that
   * matches the provided filter.
   *
   * @param aFilter
   *        The filter to be applied. May be <code>null</code>.
   * @param aConsumer
   *        The consumer to be invoked. May not be <code>null</code>.
   * @see #forEach(BiConsumer)
   */
  default void forEach (@Nullable final BiPredicate <? super KEYTYPE, ? super VALUETYPE> aFilter,
                        @Nonnull final BiConsumer <? super KEYTYPE, ? super VALUETYPE> aConsumer)
  {
    if (aFilter == null)
      forEach (aConsumer);
    else
    {
      // Use eventually present more performant forEach
      forEach ( (k, v) -> {
        if (aFilter.test (k, v))
          aConsumer.accept (k, v);
      });
    }
  }

  /**
   * Invoke the provided consumer on each key that matches the provided filter.
   *
   * @param aFilter
   *        The filter to be applied. May be <code>null</code>.
   * @param aConsumer
   *        The consumer to be invoked. May not be <code>null</code>.
   * @see #forEachKey(Consumer)
   * @see #forEach(BiPredicate, BiConsumer)
   */
  default void forEachKey (@Nullable final Predicate <? super KEYTYPE> aFilter,
                           @Nonnull final Consumer <? super KEYTYPE> aConsumer)
  {
    if (aFilter == null)
      forEachKey (aConsumer);
    else
      forEach ( (k, v) -> aFilter.test (k), (k, v) -> aConsumer.accept (k));
  }

  /**
   * Invoke the provided consumer on each value that matches the provided
   * filter.
   *
   * @param aFilter
   *        The filter to be applied. May be <code>null</code>.
   * @param aConsumer
   *        The consumer to be invoked. May not be <code>null</code>.
   * @see #forEachValue(Consumer)
   * @see #forEach(BiPredicate, BiConsumer)
   */
  default void forEachValue (@Nullable final Predicate <? super VALUETYPE> aFilter,
                             @Nonnull final Consumer <? super VALUETYPE> aConsumer)
  {
    if (aFilter == null)
      forEachValue (aConsumer);
    else
      forEach ( (k, v) -> aFilter.test (v), (k, v) -> aConsumer.accept (v));
  }

  /**
   * Get the map sorted by its keys. The comparison order is defined by the
   * passed comparator object.
   *
   * @param aKeyComparator
   *        The comparator to be used. May not be <code>null</code>.
   * @return the sorted map and never <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  default ICommonsOrderedMap <KEYTYPE, VALUETYPE> getSortedByKey (@Nonnull final Comparator <? super KEYTYPE> aKeyComparator)
  {
    return CollectionHelper.getSortedByKey (this, aKeyComparator);
  }

  /**
   * Get the map sorted by its values. The comparison order is defined by the
   * passed comparator object.
   *
   * @param aValueComparator
   *        The comparator to be used. May not be <code>null</code>.
   * @return the sorted map and never <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  default ICommonsOrderedMap <KEYTYPE, VALUETYPE> getSortedByValue (@Nonnull final Comparator <? super VALUETYPE> aValueComparator)
  {
    return CollectionHelper.getSortedByValue (this, aValueComparator);
  }

  /**
   * Get a map where keys and values are exchanged.
   *
   * @return The swapped hash map (unsorted!)
   */
  @Nullable
  @ReturnsMutableCopy
  default ICommonsMap <VALUETYPE, KEYTYPE> getSwappedKeyValues ()
  {
    final ICommonsMap <VALUETYPE, KEYTYPE> ret = new CommonsHashMap <> ();
    for (final Map.Entry <KEYTYPE, VALUETYPE> aEntry : entrySet ())
      ret.put (aEntry.getValue (), aEntry.getKey ());
    return ret;
  }

  /**
   * Put the passed value into the map if the provided predicate is fulfilled.
   *
   * @param aKey
   *        Key to use. May not be <code>null</code> for certain map types.
   * @param aValue
   *        The value to be added. May be <code>null</code> in which case
   *        nothing happens.
   * @param aFilter
   *        The value predicate to be checked before insertion. May not be
   *        <code>null</code>.
   */
  @Nonnull
  default void putIf (@Nonnull final KEYTYPE aKey,
                      @Nullable final VALUETYPE aValue,
                      @Nonnull final Predicate <VALUETYPE> aFilter)
  {
    ValueEnforcer.notNull (aFilter, "Filter");
    if (aFilter.test (aValue))
      put (aKey, aValue);
  }

  /**
   * Put the passed value into the map if it is not <code>null</code>.
   *
   * @param aKey
   *        Key to use. May not be <code>null</code> for certain map types.
   * @param aValue
   *        The value to be added. May be <code>null</code> in which case
   *        nothing happens.
   */
  default void putIfNotNull (@Nonnull final KEYTYPE aKey, @Nullable final VALUETYPE aValue)
  {
    if (aValue != null)
      put (aKey, aValue);
  }

  /**
   * Add all items from the passed array to this map using the provided key and
   * value mapper.
   *
   * @param aElements
   *        Source collection. May be <code>null</code>.
   * @param aKeyMapper
   *        The key mapper. May not be <code>null</code>.
   * @param aValueMapper
   *        The value mapper. May not be <code>null</code>.
   * @param <ELEMENTTYPE>
   *        Array element type
   */
  @Nonnull
  default <ELEMENTTYPE> void putAll (@Nullable final ELEMENTTYPE [] aElements,
                                     @Nonnull final Function <? super ELEMENTTYPE, ? extends KEYTYPE> aKeyMapper,
                                     @Nonnull final Function <? super ELEMENTTYPE, ? extends VALUETYPE> aValueMapper)
  {
    ValueEnforcer.notNull (aKeyMapper, "KeyMapper");
    ValueEnforcer.notNull (aValueMapper, "ValueMapper");
    if (aElements != null)
      for (final ELEMENTTYPE aElement : aElements)
        put (aKeyMapper.apply (aElement), aValueMapper.apply (aElement));
  }

  /**
   * Add all items from the passed collection to this map using the provided key
   * and value mapper.
   *
   * @param aElements
   *        Source collection. May be <code>null</code>.
   * @param aKeyMapper
   *        The key mapper. May not be <code>null</code>.
   * @param aValueMapper
   *        The value mapper. May not be <code>null</code>.
   * @param <ELEMENTTYPE>
   *        Collection element type
   */
  default <ELEMENTTYPE> void putAll (@Nullable final Collection <? extends ELEMENTTYPE> aElements,
                                     @Nonnull final Function <? super ELEMENTTYPE, ? extends KEYTYPE> aKeyMapper,
                                     @Nonnull final Function <? super ELEMENTTYPE, ? extends VALUETYPE> aValueMapper)
  {
    ValueEnforcer.notNull (aKeyMapper, "KeyMapper");
    ValueEnforcer.notNull (aValueMapper, "ValueMapper");
    if (aElements != null)
      for (final ELEMENTTYPE aItem : aElements)
        put (aKeyMapper.apply (aItem), aValueMapper.apply (aItem));
  }

  /**
   * Add all provided values.
   *
   * @param aValues
   *        The values to be added. May be <code>null</code>.
   */
  default void addAll (@Nullable final Map <? extends KEYTYPE, ? extends VALUETYPE> aValues)
  {
    if (aValues != null)
      putAll (aValues);
  }

  /**
   * Clear and add all provided values.
   *
   * @param aValues
   *        The values to be added. May be <code>null</code>.
   */
  default void setAll (@Nullable final Map <? extends KEYTYPE, ? extends VALUETYPE> aValues)
  {
    clear ();
    addAll (aValues);
  }

  /**
   * Remove all elements from this collection. This is similar to
   * {@link #clear()} but it returns a different value whether something was
   * cleared or not.
   *
   * @return {@link EChange#CHANGED} if the collection was not empty and
   *         something was removed, {@link EChange#UNCHANGED} otherwise.
   * @see #clear()
   */
  @Nonnull
  default EChange removeAll ()
  {
    if (isEmpty ())
      return EChange.UNCHANGED;
    clear ();
    return EChange.CHANGED;
  }

  /**
   * Remove the object with the passed key from this map.
   *
   * @param aKey
   *        The key to be removed. May be <code>null</code>.
   * @return {@link EChange#CHANGED} if the removal was successful,
   *         {@link EChange#UNCHANGED} if removal fails.
   * @see #remove(Object)
   */
  @Nonnull
  default EChange removeObject (@Nullable final KEYTYPE aKey)
  {
    return EChange.valueOf (remove (aKey) != null);
  }

  /**
   * @return An unmodifiable version of this map. Never <code>null</code>.
   * @see Collections
   */
  @Nonnull
  @CodingStyleguideUnaware
  default Map <KEYTYPE, VALUETYPE> getAsUnmodifiable ()
  {
    return Collections.unmodifiableMap (this);
  }
}
