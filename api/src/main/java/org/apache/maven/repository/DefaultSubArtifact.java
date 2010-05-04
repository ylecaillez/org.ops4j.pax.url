package org.apache.maven.repository;

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

/**
 * @author Benjamin Bentmann
 */
public class DefaultSubArtifact
    extends DefaultArtifact
{

    private final Artifact mainArtifact;

    public DefaultSubArtifact( Artifact mainArtifact, String subClassifier, String subType )
    {
        super( mainArtifact.getGroupId(), mainArtifact.getArtifactId(), expand( subClassifier,
                                                                                mainArtifact.getClassifier() ),
               expand( subType, mainArtifact.getType() ), mainArtifact.getVersion() );
        setBaseVersion( mainArtifact.getBaseVersion() );
        this.mainArtifact = mainArtifact;
    }

    private static String expand( String pattern, String replacement )
    {
        return ( pattern != null ) ? pattern.replace( "*", replacement ) : pattern;
    }

    public Artifact getMainArtifact()
    {
        return mainArtifact;
    }

}
