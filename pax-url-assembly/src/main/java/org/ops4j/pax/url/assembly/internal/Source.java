/*
 * Copyright 2009 Alin Dreghiciu.
 *
 * Licensed  under the  Apache License,  Version 2.0  (the "License");
 * you may not use  this file  except in  compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed  under the  License is distributed on an "AS IS" BASIS,
 * WITHOUT  WARRANTIES OR CONDITIONS  OF ANY KIND, either  express  or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.url.assembly.internal;

import java.util.regex.Pattern;

/**
 * A source of resources.
 *
 * @author Alin Dreghiciu
 * @since 1.1.0, August 31, 2009
 */
public interface Source
{

    /**
     * Base source path for resources.
     *
     * @return source path for resources
     */
    String path();

    /**
     * List of patterns for inclusion.
     *
     * @return list of patterns
     */
    Pattern[] includes();

    /**
     * List of patterns for exclusion.
     *
     * @return list of patterns
     */
    Pattern[] excludes();

}