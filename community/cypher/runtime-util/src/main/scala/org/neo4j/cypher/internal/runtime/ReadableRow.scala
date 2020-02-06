/*
 * Copyright (c) 2002-2020 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.cypher.internal.runtime

import org.neo4j.cypher.internal.expressions.ASTCachedProperty
import org.neo4j.values.AnyValue
import org.neo4j.values.storable.Value

// TODO: scaladoc
trait ReadableRow {

  def getLongAt(offset: Int): Long
  def getRefAt(offset: Int): AnyValue
  def getByName(name: String): AnyValue
  def copyTo(target: WritableRow, sourceLongOffset: Int = 0, sourceRefOffset: Int = 0, targetLongOffset: Int = 0, targetRefOffset: Int = 0): Unit

  /**
    * Returns the cached property value
    *   or NO_VALUE if the entity does not have the property,
    *   or null     if this cached value has been invalidated, or the property value has not been cached.
    */
  def getCachedProperty(key: ASTCachedProperty): Value

  /**
    * Returns the cached property value
    *   or NO_VALUE if the entity does not have the property,
    *   or null     if this cached value has been invalidated, or the property value has not been cached.
    */
  def getCachedPropertyAt(offset: Int): Value

  def getLinenumber: Option[ResourceLinenumber]
}
