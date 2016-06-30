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
package org.sonar.jproperties.ast.visitors;

import com.google.common.collect.Lists;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Token;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import org.sonar.jproperties.FileUtils;

public class SourceFileOffsets {

  private final int length;
  private final boolean startsWithBOM;
  private final List<Integer> lineStartOffsets = Lists.newArrayList();

  public SourceFileOffsets(File file, Charset charset) {
    String content = FileUtils.fileContent(file, charset);
    this.startsWithBOM = FileUtils.startsWithBOM(file, charset);
    this.length = content.length();
    initOffsets(content);
  }

  private void initOffsets(String toParse) {
    if (startsWithBOM) {
      lineStartOffsets.add(-1);
    } else {
      lineStartOffsets.add(0);
    }

    int i = 0;
    while (i < length) {
      if (toParse.charAt(i) == '\n' || toParse.charAt(i) == '\r') {
        int nextLineStartOffset = i + 1;
        if (i < (length - 1) && toParse.charAt(i) == '\r' && toParse.charAt(i + 1) == '\n') {
          nextLineStartOffset = i + 2;
          i++;
        }
        lineStartOffsets.add(nextLineStartOffset - (startsWithBOM ? 1 : 0));
      }
      i++;
    }
  }

  public int startOffset(Token token) {
    int lineStartOffset = lineStartOffsets.get(token.getLine() - 1);
    int column = token.getColumn();
    return lineStartOffset + column;
  }

  public int endOffset(Token token) {
    return startOffset(token) + token.getValue().length();
  }

  public int startOffset(AstNode astNode) {
    int lineStartOffset = lineStartOffsets.get(astNode.getTokenLine() - 1);
    int column = astNode.getToken().getColumn();
    return lineStartOffset + column;
  }

  public int endOffset(AstNode astNode) {
    return startOffset(astNode) + astNode.getToIndex() - astNode.getFromIndex();
  }

}
