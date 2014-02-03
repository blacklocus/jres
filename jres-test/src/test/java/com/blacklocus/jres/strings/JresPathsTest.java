/**
 * Copyright 2013 BlackLocus
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
package com.blacklocus.jres.strings;

import org.junit.Assert;
import org.junit.Test;

public class JresPathsTest {

    @Test
    public void testSlashedPath() {
        String path;

        path = JresPaths.slashedPath("the_index", "my_type") + "12345678";
        Assert.assertEquals("Sort of the usual, simple use case", "the_index/my_type/12345678", path);

        path = JresPaths.slashedPath("with spaces", "my_type");
        Assert.assertEquals("With spaces which should be percent-encoded", "with%20spaces/my_type/", path);

        path = JresPaths.slashedPath("/with spaces", "my_type");
        Assert.assertEquals("Leading slash should be dropped", "with%20spaces/my_type/", path);

        path = JresPaths.slashedPath("/for™•¶•ˆ∆µÓÔ◊Â");
        Assert.assertEquals("Unicode should not break this", "for™•¶•ˆ∆µÓÔ◊Â/", path);
    }

}
