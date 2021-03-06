/*
 * Copyright  2019 - present. IAB Tech Lab
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

package net.media.converters.request30toRequest23;

import net.media.config.Config;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import static net.media.utils.ExtUtils.putToExt;

/** Created by rajat.go on 03/04/19. */
public class VideoPlacementToVideoConverter
    extends net.media.converters.request30toRequest25.VideoPlacementToVideoConverter {

  public void enhance(
      VideoPlacement videoPlacement, Video video, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (videoPlacement == null || video == null) {
      return;
    }
    super.enhance(videoPlacement, video, config, converterProvider);
    putToExt(video::getPlacement, video.getExt(), CommonConstants.PLACEMENT, video::setExt);
    video.setPlacement(null);
    putToExt(video::getPlaybackend, video.getExt(), CommonConstants.PLAYBACKEND, video::setExt);
    video.setPlaybackend(null);
    putToExt(video::getSkip, video.getExt(), CommonConstants.SKIP, video::setExt);
    video.setSkip(null);
    putToExt(video::getSkipmin, video.getExt(), CommonConstants.SKIPMIN, video::setExt);
    video.setSkipmin(null);
    putToExt(video::getSkipafter, video.getExt(), CommonConstants.SKIPAFTER, video::setExt);
    video.setSkipafter(null);
  }
}
