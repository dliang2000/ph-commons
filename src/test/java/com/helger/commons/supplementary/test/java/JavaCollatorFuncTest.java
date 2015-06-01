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
package com.helger.commons.supplementary.test.java;

import java.text.Collator;
import java.text.ParseException;
import java.text.RuleBasedCollator;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.collections.CollectionHelper;
import com.helger.commons.compare.CollatingComparator;
import com.helger.commons.locale.LocaleCache;

public class JavaCollatorFuncTest
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (JavaCollatorFuncTest.class);

  @Test
  public void testCollation () throws ParseException
  {
    final Set <String> aSet = new HashSet <String> ();
    aSet.add ("a");
    aSet.add ("A");
    aSet.add ("Ä");
    aSet.add ("ä");
    aSet.add (" ");
    aSet.add (".");
    aSet.add ("_");
    aSet.add ("#");
    aSet.add ("+");
    aSet.add ("0");
    aSet.add ("1");
    aSet.add ("2");
    aSet.add ("3");
    aSet.add ("4");
    aSet.add ("1.1 A");
    aSet.add ("1.1.1 A");
    aSet.add ("1.1.1.A");
    aSet.add ("A A A");
    aSet.add ("A A");
    aSet.add ("AA A");

    final Locale aLocale = LocaleCache.getLocale ("de_DE");

    for (final String s : CollectionHelper.getSorted (aSet, new CollatingComparator (Locale.US)))
    {
      s_aLogger.info (s);
    }
    s_aLogger.info ("-------------------------1");

    final Collator aColl = Collator.getInstance (aLocale);
    aColl.setStrength (Collator.TERTIARY);
    aColl.setDecomposition (Collator.FULL_DECOMPOSITION);
    for (final String s : CollectionHelper.getSorted (aSet, new CollatingComparator (aColl)))
    {
      s_aLogger.info (s);
    }
    s_aLogger.info ("-------------------------2");

    final RuleBasedCollator defaultCollator = (RuleBasedCollator) Collator.getInstance (aLocale);
    final String rules = defaultCollator.getRules ();
    // add rule for space before '_'
    final String sNewRules = rules.replace ("<'.'<", "<' '<'.'<");
    final RuleBasedCollator collator2 = new RuleBasedCollator (sNewRules);
    collator2.setStrength (Collator.TERTIARY);
    collator2.setDecomposition (Collator.FULL_DECOMPOSITION);
    for (final String s : CollectionHelper.getSorted (aSet, new CollatingComparator (collator2)))
    {
      s_aLogger.info (s);
    }
    s_aLogger.info ("-------------------------3");
  }
}
