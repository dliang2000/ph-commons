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
package com.helger.commons.io.file.filter;

import java.io.File;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotations.Nonempty;
import com.helger.commons.annotations.ReturnsMutableCopy;
import com.helger.commons.collections.ArrayHelper;
import com.helger.commons.io.file.FilenameHelper;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.ToStringGenerator;

/**
 * A filter that rejects certain file names, based on a regular expression. If
 * at least one regular expressions is fulfilled, the file is rejected. The
 * filter is applied on directories and files!
 *
 * @author Philip Helger
 */
@ThreadSafe
public final class FilenameFilterMatchNoRegEx extends AbstractFileFilter
{
  private final String [] m_aRegExs;

  public FilenameFilterMatchNoRegEx (@Nonnull @Nonempty final String sRegEx)
  {
    this (new String [] { sRegEx });
  }

  public FilenameFilterMatchNoRegEx (@Nonnull @Nonempty final String... aRegExs)
  {
    m_aRegExs = ArrayHelper.getCopy (ValueEnforcer.notEmpty (aRegExs, "RegularExpressions"));
  }

  @Nonnull
  @ReturnsMutableCopy
  public String [] getRegularExpressions ()
  {
    return ArrayHelper.getCopy (m_aRegExs);
  }

  public boolean matchesFilter (@Nullable final File aFile)
  {
    if (aFile == null)
      return false;
    final String sRealName = FilenameHelper.getSecureFilename (aFile.getName ());
    if (sRealName == null)
      return false;
    for (final String sRegEx : m_aRegExs)
      if (RegExHelper.stringMatchesPattern (sRegEx, sRealName))
        return false;
    return true;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("regExs", m_aRegExs).toString ();
  }
}
