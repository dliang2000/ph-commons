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
package com.helger.commons.text;

import com.helger.commons.IHasLocales;
import com.helger.commons.IHasSize;

/**
 * This is an in-between interface between the {@link ITextProvider} and the
 * {@link IReadonlyMultiLingualText}. The practical purpose of this interface is
 * to have a simple serializable multi lingual text.<br>
 * It only defines the read-access methods, to allow for immutable
 * implementations.
 * 
 * @author Philip Helger
 */
public interface ISimpleMultiLingualText extends ITextProvider, IHasLocales, IHasSize
{
  /* empty */
}
