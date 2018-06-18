/*
 * SonarQube Java Properties Analyzer
 * Copyright (C) 2015-2017 David RACODON
 * david.racodon@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.jproperties.checks.generic;

import org.junit.Test;
import org.sonar.jproperties.checks.CheckTestUtils;
import org.sonar.jproperties.checks.generic.EmptyElementCheck;
import org.sonar.jproperties.checks.verifier.JavaPropertiesCheckVerifier;

public class EmptyElementCheckTest {

  @Test
  public void should_find_some_empty_elements_and_raise_issues() {
    JavaPropertiesCheckVerifier.verify(new EmptyElementCheck(), CheckTestUtils.getTestFile("emptyElement.properties"));
  }

}
