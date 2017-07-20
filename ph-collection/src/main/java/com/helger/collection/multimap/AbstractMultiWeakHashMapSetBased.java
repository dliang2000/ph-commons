/**
 * Copyright (C) 2014-2017 Philip Helger (www.helger.com)
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
package com.helger.collection.multimap;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.collection.ext.ICommonsSet;

/**
 * Abstract multi map based on {@link java.util.WeakHashMap} and
 * {@link ICommonsSet} values.<br>
 *
 * @author Philip Helger
 * @param <KEYTYPE>
 *        key type
 * @param <VALUETYPE>
 *        value type
 * @param <COLLTYPE>
 *        Set type containing value types
 */
@NotThreadSafe
public abstract class AbstractMultiWeakHashMapSetBased <KEYTYPE, VALUETYPE, COLLTYPE extends ICommonsSet <VALUETYPE>>
                                                       extends AbstractMultiWeakHashMap <KEYTYPE, VALUETYPE, COLLTYPE>
                                                       implements IMultiMapSetBased <KEYTYPE, VALUETYPE, COLLTYPE>
{
  public AbstractMultiWeakHashMapSetBased ()
  {}

  public AbstractMultiWeakHashMapSetBased (@Nonnull final KEYTYPE aKey, @Nullable final VALUETYPE aValue)
  {
    super (aKey, aValue);
  }

  public AbstractMultiWeakHashMapSetBased (@Nonnull final KEYTYPE aKey, @Nullable final COLLTYPE aCollection)
  {
    super (aKey, aCollection);
  }

  public AbstractMultiWeakHashMapSetBased (@Nullable final Map <? extends KEYTYPE, ? extends COLLTYPE> aCont)
  {
    super (aCont);
  }
}