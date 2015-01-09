/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
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
package com.helger.commons.id;

import java.util.Comparator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.compare.ESortOrder;

/**
 * This is a collation {@link java.util.Comparator} for objects that implement
 * the {@link IHasID} interface with {@link Integer}.
 * 
 * @author Philip Helger
 * @param <DATATYPE>
 *        The type of elements to be compared.
 */
public class ComparatorHasIDInteger <DATATYPE extends IHasID <Integer>> extends
                                                                        ComparatorHasIDComparable <Integer, DATATYPE>
{
  /**
   * Comparator with default sort order and no nested comparator.
   */
  public ComparatorHasIDInteger ()
  {
    super ();
  }

  /**
   * Constructor with sort order.
   * 
   * @param eSortOrder
   *        The sort order to use. May not be <code>null</code>.
   */
  public ComparatorHasIDInteger (@Nonnull final ESortOrder eSortOrder)
  {
    super (eSortOrder);
  }

  /**
   * Comparator with default sort order and a nested comparator.
   * 
   * @param aNestedComparator
   *        The nested comparator to be invoked, when the main comparison
   *        resulted in 0.
   */
  public ComparatorHasIDInteger (@Nullable final Comparator <? super DATATYPE> aNestedComparator)
  {
    super (aNestedComparator);
  }

  /**
   * Comparator with sort order and a nested comparator.
   * 
   * @param eSortOrder
   *        The sort order to use. May not be <code>null</code>.
   * @param aNestedComparator
   *        The nested comparator to be invoked, when the main comparison
   *        resulted in 0.
   */
  public ComparatorHasIDInteger (@Nonnull final ESortOrder eSortOrder,
                                 @Nullable final Comparator <? super DATATYPE> aNestedComparator)
  {
    super (eSortOrder, aNestedComparator);
  }
}
