/*
 * Copyright (c) "Neo4j"
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
package org.neo4j.consistency.checking;

import org.neo4j.consistency.report.ConsistencyReport;
import org.neo4j.consistency.store.RecordAccess;
import org.neo4j.consistency.store.RecordReference;
import org.neo4j.io.pagecache.tracing.cursor.PageCursorTracer;
import org.neo4j.kernel.impl.store.record.DynamicRecord;
import org.neo4j.kernel.impl.store.record.Record;
import org.neo4j.kernel.impl.store.record.TokenRecord;

abstract class TokenRecordCheck<RECORD extends TokenRecord, REPORT extends ConsistencyReport>
        implements RecordCheck<RECORD, REPORT>, ComparativeRecordChecker<RECORD, DynamicRecord, REPORT>
{
    @Override
    public void check( RECORD record, CheckerEngine<RECORD, REPORT> engine, RecordAccess records, PageCursorTracer cursorTracer )
    {
        if ( !record.inUse() )
        {
            return;
        }
        if ( !Record.NO_NEXT_BLOCK.is( record.getNameId() ) )
        {
            engine.comparativeCheck( name( records, record.getNameId(), cursorTracer ), this );
        }
    }

    @Override
    public void checkReference( RECORD record, DynamicRecord name, CheckerEngine<RECORD,REPORT> engine, RecordAccess records, PageCursorTracer cursorTracer )
    {
        if ( !name.inUse() )
        {
            nameNotInUse( engine.report(), name );
        }
        else
        {
            if ( name.getLength() <= 0 )
            {
                emptyName( engine.report(), name );
            }
        }
    }

    abstract RecordReference<DynamicRecord> name( RecordAccess records, int id, PageCursorTracer cursorTracer );

    abstract void nameNotInUse( REPORT report, DynamicRecord name );

    abstract void emptyName( REPORT report, DynamicRecord name );
}
