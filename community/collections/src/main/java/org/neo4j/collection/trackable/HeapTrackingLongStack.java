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
package org.neo4j.collection.trackable;

import java.util.EmptyStackException;

import org.neo4j.graphdb.Resource;

public class HeapTrackingLongStack implements LongStack, Resource
{
    private final HeapTrackingLongArrayList delegate;

    public HeapTrackingLongStack( HeapTrackingLongArrayList delegate )
    {
        this.delegate = delegate;
    }

    @Override
    public long peek()
    {
        int size = delegate.size();
        if ( size == 0 )
        {
            throw new EmptyStackException();
        }
        return delegate.get( size - 1 );
    }

    @Override
    public void push( long item )
    {
        delegate.add( item );
    }

    @Override
    public long pop()
    {
        return delegate.removeLast();
    }

    @Override
    public int size()
    {
        return delegate.size();
    }

    @Override
    public void close()
    {
        delegate.close();
    }
}
