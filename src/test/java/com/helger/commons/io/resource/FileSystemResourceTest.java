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
package com.helger.commons.io.resource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;

import org.junit.Test;

import com.helger.commons.charset.CCharset;
import com.helger.commons.io.EAppend;
import com.helger.commons.io.file.FileOperations;
import com.helger.commons.io.streams.StreamUtils;
import com.helger.commons.mock.PHTestUtils;

/**
 * Test class for class {@link FileSystemResource}.
 * 
 * @author Philip Helger
 */
public final class FileSystemResourceTest
{
  @Test
  public void testCtor ()
  {
    assertNotNull (new FileSystemResource ("file.txt"));
    assertNotNull (new FileSystemResource ("dir/file.text"));
    assertNotNull (new FileSystemResource ("/file.text"));
    assertNotNull (new FileSystemResource ("/dir/file2.txt"));
    assertNotNull (new FileSystemResource ("dir", "file2.txt"));
    assertNotNull (new FileSystemResource (new File ("file2.txt")));
    assertNotNull (new FileSystemResource (new File ("dir"), "file2.txt"));

    try
    {
      new FileSystemResource ((File) null);
      fail ();
    }
    catch (final NullPointerException ex)
    {}
  }

  @Test
  public void testAccess ()
  {
    FileSystemResource fr = new FileSystemResource ("pom.xml");
    assertTrue (fr.exists ());
    assertTrue (fr.getResourceID ().endsWith ("pom.xml"));
    assertTrue (fr.getPath ().endsWith ("pom.xml"));
    StreamUtils.close (fr.getReader (CCharset.CHARSET_ISO_8859_1_OBJ));
    final byte [] aBytes = StreamUtils.getAllBytes (fr);
    assertTrue (aBytes.length > 0);
    assertNotNull (fr.getAsURL ());
    assertNotNull (fr.getAsFile ());

    PHTestUtils.testDefaultImplementationWithEqualContentObject (fr, new FileSystemResource ("pom.xml"));
    PHTestUtils.testDefaultImplementationWithEqualContentObject (fr, fr.getReadableCloneForPath ("pom.xml"));
    PHTestUtils.testDefaultImplementationWithEqualContentObject (fr, fr.getWritableCloneForPath ("pom.xml"));
    PHTestUtils.testDefaultImplementationWithDifferentContentObject (fr, new FileSystemResource ("pom.xml2"));

    fr = new FileSystemResource ("this file does not exist");
    assertFalse (fr.exists ());
    assertNull (fr.getInputStream ());
    assertNull (fr.getReader (CCharset.CHARSET_ISO_8859_1_OBJ));
  }

  @Test
  public void testWrite ()
  {
    final File f = new File ("restest.xxx");
    try
    {
      final FileSystemResource fr = new FileSystemResource (f);
      OutputStream aOS = fr.getOutputStream (EAppend.TRUNCATE);
      assertNotNull (aOS);
      StreamUtils.close (aOS);
      aOS = fr.getOutputStream (EAppend.APPEND);
      assertNotNull (aOS);
      StreamUtils.close (aOS);

      Writer w = fr.getWriter (CCharset.CHARSET_ISO_8859_1_OBJ, EAppend.TRUNCATE);
      assertNotNull (w);
      StreamUtils.close (w);
      w = fr.getWriter (CCharset.CHARSET_ISO_8859_1_OBJ, EAppend.APPEND);
      assertNotNull (w);
      StreamUtils.close (w);
    }
    finally
    {
      FileOperations.deleteFile (f);
    }
  }

  @Test
  public void testSerialize () throws Exception
  {
    PHTestUtils.testDefaultSerialization (new FileSystemResource ("file.txt"));
  }
}
