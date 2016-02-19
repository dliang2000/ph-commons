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
package com.helger.commons.url;

import java.io.Serializable;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.annotation.ReturnsMutableObject;
import com.helger.commons.collection.ext.ICommonsOrderedMap;

/**
 * Base interface representing the basic elements of a URL from a high level
 * perspective.
 *
 * @author Philip Helger
 */
public interface IURLData extends Serializable
{
  /**
   * @return The protocol used. May be <code>null</code> for an unknown
   *         protocol.
   */
  @Nullable
  default IURLProtocol getProtocol ()
  {
    return URLProtocolRegistry.getInstance ().getProtocol (getPath ());
  }

  /**
   * @return <code>true</code> if the URL has a known protocol
   */
  default boolean hasKnownProtocol ()
  {
    return URLProtocolRegistry.getInstance ().hasKnownProtocol (getPath ());
  }

  /**
   * @return The path part of the URL (everything before the "?" and the "#",
   *         incl. the protocol). Never <code>null</code> but maybe empty (e.g.
   *         for "?x=y").
   */
  @Nonnull
  String getPath ();

  /**
   * @return <code>true</code> if at least one parameter is present,
   *         <code>false</code> otherwise.
   */
  boolean hasParams ();

  /**
   * @return The number of parameters present. Always &ge; 0.
   */
  @Nonnegative
  int getParamCount ();

  /**
   * @return A map of all query string parameters. May be <code>null</code>.
   */
  @Nullable
  @ReturnsMutableObject ("design")
  ICommonsOrderedMap <String, String> directGetAllParams ();

  /**
   * @return A map of all query string parameters in the order they were passed
   *         on. Never <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  ICommonsOrderedMap <String, String> getAllParams ();

  /**
   * @return <code>true</code> if an anchor is present, <code>false</code>
   *         otherwise.
   */
  boolean hasAnchor ();

  /**
   * @return The name of the anchor (everything after the "#") or
   *         <code>null</code> if none is defined.
   */
  @Nullable
  String getAnchor ();
}
