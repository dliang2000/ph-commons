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
package com.helger.commons.io.streams;

import java.io.FilterReader;
import java.io.Reader;

import javax.annotation.Nonnull;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.string.ToStringGenerator;

/**
 * A wrapper around another {@link Reader}. Pass through of all {@link Reader}
 * methods.
 *
 * @author Philip Helger
 */
public class WrappedReader extends FilterReader
{
  public WrappedReader (@Nonnull final Reader aWrappedReader)
  {
    super (ValueEnforcer.notNull (aWrappedReader, "WrappedReader"));
  }

  @Nonnull
  public Reader getWrappedReader ()
  {
    return in;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("wrappedReader", in).toString ();
  }
}
