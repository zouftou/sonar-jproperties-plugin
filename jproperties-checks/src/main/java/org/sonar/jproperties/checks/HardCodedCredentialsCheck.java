/*
 * SonarQube Java Properties Plugin
 * Copyright (C) 2015-2016 David RACODON
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
package org.sonar.jproperties.checks;

import com.sonar.sslr.api.AstNode;

import java.util.regex.Pattern;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.jproperties.JavaPropertiesCheck;
import org.sonar.jproperties.parser.JavaPropertiesGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;

@Rule(
  key = "S2068",
  name = "Credentials should not be hard-coded",
  priority = Priority.CRITICAL,
  tags = {Tags.SECURITY, Tags.CWE, Tags.OWASP_A2, Tags.SANS_TOP25_POROUS})
@ActivatedByDefault
public class HardCodedCredentialsCheck extends JavaPropertiesCheck {

  private static final Pattern HARD_CODED_USERNAME = Pattern.compile(".*(login|username).*", Pattern.CASE_INSENSITIVE);
  private static final Pattern HARD_CODED_PASSWORD = Pattern.compile(".*(password|passwd|pwd).*", Pattern.CASE_INSENSITIVE);

  @Override
  public void init() {
    subscribeTo(JavaPropertiesGrammar.KEY);
  }

  @Override
  public void leaveNode(AstNode node) {
    if (HARD_CODED_USERNAME.matcher(node.getTokenValue()).matches()) {
      addIssue(this, "Remove this hard-coded username.", node);
    }
    if (HARD_CODED_PASSWORD.matcher(node.getTokenValue()).matches()) {
      addIssue(this, "Remove this hard-coded password.", node);
    }
  }

}
