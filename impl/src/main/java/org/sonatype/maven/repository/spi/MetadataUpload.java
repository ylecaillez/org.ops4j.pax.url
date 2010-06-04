package org.sonatype.maven.repository.spi;

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

import org.sonatype.maven.repository.Metadata;
import org.sonatype.maven.repository.MetadataTransferException;

/**
 * An upload of metadata to a remote repository.
 * 
 * @author Benjamin Bentmann
 */
public class MetadataUpload
    extends MetadataTransfer
{

    /**
     * Creates a new uninitialized upload.
     */
    public MetadataUpload()
    {
        // enables default constructor
    }

    /**
     * Creates a new upload with the specified properties.
     * 
     * @param metadata The metadata to upload, may be {@code null}.
     * @param file The local file to upload the metadata from, may be {@code null}.
     */
    public MetadataUpload( Metadata metadata, File file )
    {
        setMetadata( metadata );
        setFile( file );
    }

    @Override
    public MetadataUpload setMetadata( Metadata metadata )
    {
        super.setMetadata( metadata );
        return this;
    }

    @Override
    public MetadataUpload setFile( File file )
    {
        super.setFile( file );
        return this;
    }

    @Override
    public MetadataUpload setException( MetadataTransferException exception )
    {
        super.setException( exception );
        return this;
    }

}
