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
package com.helger.commons.stats;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import com.helger.commons.IHasStringRepresentation;
import com.helger.commons.annotations.Nonempty;

/**
 * Base interface for all statistic handlers
 * 
 * @author Philip Helger
 */
public interface IStatisticsHandler extends IHasStringRepresentation
{
  /**
   * @return The number of times this statistics hander was invoked.
   */
  @Nonnegative
  int getInvocationCount ();

  /**
   * @return An internal string representation of the statistics handler.
   */
  @Nonnull
  @Nonempty
  String getAsString ();
}
