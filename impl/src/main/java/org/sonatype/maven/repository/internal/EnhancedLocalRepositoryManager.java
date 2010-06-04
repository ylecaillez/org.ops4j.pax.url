package org.sonatype.maven.repository.internal;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.sonatype.maven.repository.Artifact;
import org.sonatype.maven.repository.LocalArtifactQuery;
import org.sonatype.maven.repository.RemoteRepository;
import org.sonatype.maven.repository.spi.Logger;

/**
 * A local repository manager that builds upon the classical Maven 2.0 local repository structure but additionally keeps
 * track of from what repositories a cached artifact was resolved. Resolution of locally cached artifacts will be
 * rejected in case the current resolution request does not match the known source repositories of an artifact, thereby
 * emulating physically separated artifact caches per remote repository.
 * 
 * @author Benjamin Bentmann
 */
public class EnhancedLocalRepositoryManager
    extends SimpleLocalRepositoryManager
{

    private static final String LOCAL_REPO_ID = "";

    private TrackingFileManager trackingFileManager;

    public EnhancedLocalRepositoryManager( File basedir )
    {
        super( basedir, "enhanced" );
        trackingFileManager = new TrackingFileManager();
    }

    @Override
    public EnhancedLocalRepositoryManager setLogger( Logger logger )
    {
        super.setLogger( logger );
        trackingFileManager.setLogger( logger );
        return this;
    }

    @Override
    public void find( LocalArtifactQuery query )
    {
        String path = getPathForLocalArtifact( query.getArtifact() );
        File file = new File( getRepository().getBasedir(), path );
        if ( file.isFile() )
        {
            query.setFile( file );
            Properties props = readRepos( file );
            if ( props == null )
            {
                /*
                 * NOTE: tracking file not present at all, for inter-op with Maven 2.x, assume the artifact was locally
                 * built.
                 */
                query.setAvailable( true );
            }
            else if ( props.getProperty( getKey( file, LOCAL_REPO_ID ) ) != null )
            {
                query.setAvailable( true );
            }
            else
            {
                String context = query.getContext();
                for ( RemoteRepository repository : query.getRepositories() )
                {
                    if ( props.getProperty( getKey( file, getRepositoryKey( repository, context ) ) ) != null )
                    {
                        query.setAvailable( true );
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void addLocalArtifact( Artifact artifact )
    {
        addArtifact( artifact, LOCAL_REPO_ID );
    }

    @Override
    public void addRemoteArtifact( Artifact artifact, RemoteRepository repository, String context )
    {
        addArtifact( artifact, getRepositoryKey( repository, context ) );
    }

    private void addArtifact( Artifact artifact, String repository )
    {
        String path = getPathForLocalArtifact( artifact );
        File file = new File( getRepository().getBasedir(), path );
        addRepo( file, repository );
    }

    private Properties readRepos( File artifactFile )
    {
        File trackingFile = getTrackingFile( artifactFile );

        return trackingFileManager.read( trackingFile );
    }

    private void addRepo( File artifactFile, String repository )
    {
        Map<String, String> updates = Collections.singletonMap( getKey( artifactFile, repository ), "" );

        File trackingFile = getTrackingFile( artifactFile );

        trackingFileManager.update( trackingFile, updates );
    }

    private File getTrackingFile( File artifactFile )
    {
        return new File( artifactFile.getParentFile(), "_maven.repositories" );
    }

    private String getKey( File file, String repository )
    {
        return file.getName() + '>' + repository;
    }

}
