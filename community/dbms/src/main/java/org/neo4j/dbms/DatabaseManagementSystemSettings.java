/*
 * Copyright (c) 2002-2016 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.dbms;

import java.io.File;

import org.neo4j.graphdb.config.Setting;
import org.neo4j.graphdb.factory.Description;
import org.neo4j.kernel.configuration.Internal;
import org.neo4j.kernel.configuration.Settings;

public interface DatabaseManagementSystemSettings
{
    @Description("Name of the database to load")
    Setting<String> active_database = Settings.setting( "dbms.active_database", Settings.STRING, "graph.db" );
    @Description("Path of the data directory")
    Setting<File> data_directory = Settings.setting( "dbms.directories.data", Settings.PATH, "data" );
    @Internal
    Setting<File> database_path = Settings.derivedSetting( "unsupported.dbms.directories.database",
            data_directory, active_database,
            ( data, current ) -> new File( new File( data, "databases" ), current ),
            Settings.PATH );
}
