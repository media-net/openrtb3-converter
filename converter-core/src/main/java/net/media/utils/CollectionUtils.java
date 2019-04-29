/*
 * Copyright  2019 - present. MEDIA.NET ADVERTISING FZ-LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.media.utils;

import net.media.config.Config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.nonNull;

/** Created by rajat.go on 04/01/19. */
public class CollectionUtils {

  public static <T> T firstElementFromCollection(Collection<T> in) {
    if (in != null && !in.isEmpty()) {
      return in.iterator().next();
    } else {
      return null;
    }
  }

  public static boolean isEmpty(Collection collection) {
    return (collection == null || collection.isEmpty());
  }

  public static <T> Collection<T> copyCollection(Collection<T> input, Config config) {
    if (config.isCloningDisabled()) {
      return input;
    }
    if (nonNull(input)) {
      if (input instanceof Set) {
        return new HashSet<>(input);
      } else {
        return new ArrayList<>(input);
      }
    }
    return null;
  }
}
