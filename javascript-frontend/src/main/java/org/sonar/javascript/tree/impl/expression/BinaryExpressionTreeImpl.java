/*
 * SonarQube JavaScript Plugin
 * Copyright (C) 2011-2018 SonarSource SA
 * mailto:info AT sonarsource DOT com
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
package org.sonar.javascript.tree.impl.expression;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import java.util.Iterator;
import org.sonar.javascript.tree.impl.JavaScriptTree;
import org.sonar.javascript.tree.impl.lexical.InternalSyntaxToken;
import org.sonar.javascript.tree.symbols.type.TypableTree;
import org.sonar.plugins.javascript.api.symbols.Type;
import org.sonar.plugins.javascript.api.symbols.TypeSet;
import org.sonar.plugins.javascript.api.tree.Tree;
import org.sonar.plugins.javascript.api.tree.expression.BinaryExpressionTree;
import org.sonar.plugins.javascript.api.tree.expression.ExpressionTree;
import org.sonar.plugins.javascript.api.tree.lexical.SyntaxToken;
import org.sonar.plugins.javascript.api.visitors.DoubleDispatchVisitor;

public class BinaryExpressionTreeImpl extends JavaScriptTree implements BinaryExpressionTree, TypableTree {

  private final ExpressionTree leftOperand;
  private final SyntaxToken operand;
  private final ExpressionTree rightOperand;
  private final Kind kind;
  private TypeSet types = TypeSet.emptyTypeSet();

  public BinaryExpressionTreeImpl(Kind kind, ExpressionTree leftOperand, InternalSyntaxToken operand, ExpressionTree rightOperand) {
    this.leftOperand = Preconditions.checkNotNull(leftOperand);
    this.operand = operand;
    this.rightOperand = Preconditions.checkNotNull(rightOperand);
    this.kind = Preconditions.checkNotNull(kind);

  }

  @Override
  public ExpressionTree leftOperand() {
    return leftOperand;
  }

  @Override
  public SyntaxToken operatorToken() {
    return operand;
  }

  @Override
  public ExpressionTree rightOperand() {
    return rightOperand;
  }

  @Override
  public Kind getKind() {
    return kind;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(leftOperand, operand, rightOperand);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitBinaryExpression(this);
  }

  @Override
  public TypeSet types() {
    return types.immutableCopy();
  }

  @Override
  public void add(Type type) {
    types.add(type);
  }

}
