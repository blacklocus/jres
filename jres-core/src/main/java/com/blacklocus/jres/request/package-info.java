/**
 * Copyright 2015 BlackLocus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Conventions
 *
 * <p>
 *
 * Required parameters are final members and so must be defaulted by or passed in through constructors. Optional
 * parameters are non-final and specified through <code>public ThisClass field(valueToSet)</code> chainable methods.
 * Such methods should <strong>never</strong> be named as bean property setters to distinguish them from members
 * considered as standard properties. Should optional properties be included in any constructors for the conveniences
 * or other patterns they might want to upload, those constructor args should be annotated
 * {@link javax.annotation.Nullable @Nullable}. The predominant example of this is `index` and `type` which are optional
 * many APIs but very often desirable, e.g. <code>/_search</code> or <code>/myIndex/_search</code> or
 * <code>/myIndex/myType/_search</code> are all valid search requests.
 *
 * <p>
 *
 * All fields should be private. For classes that will be serialized to JSON, standard bean property getter methods
 * should be present to imply to Jackson that these properties should be serialized as part of the JSON object. In
 * this package as there is no need and would confuse the conventions herein, bean property setters are never present.
 *
 */
