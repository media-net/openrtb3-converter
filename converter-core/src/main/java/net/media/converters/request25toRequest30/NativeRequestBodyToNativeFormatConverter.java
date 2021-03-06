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

package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Asset;
import net.media.openrtb25.request.NativeRequestBody;
import net.media.openrtb3.AssetFormat;
import net.media.openrtb3.NativeFormat;
import net.media.utils.CollectionUtils;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import java.util.HashMap;

import static java.util.Objects.nonNull;
import static net.media.utils.ExtUtils.putToExt;

/** Created by rajat.go on 03/01/19. */
public class NativeRequestBodyToNativeFormatConverter
    implements Converter<NativeRequestBody, NativeFormat> {

  @Override
  public NativeFormat map(
      NativeRequestBody nativeRequestBody, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (nativeRequestBody == null) {
      return null;
    }
    NativeFormat nativeFormat = new NativeFormat();
    enhance(nativeRequestBody, nativeFormat, config, converterProvider);
    return nativeFormat;
  }

  @Override
  public void enhance(
      NativeRequestBody nativeRequestBody,
      NativeFormat nativeFormat,
      Config config,
      Provider converterProvider)
      throws OpenRtbConverterException {
    if (nativeRequestBody == null || nativeFormat == null) {
      return;
    }
    if (nonNull(nativeRequestBody.getExt())) {
      nativeFormat.setExt(new HashMap<>(nativeRequestBody.getExt()));
    }
    putToExt(
      nativeRequestBody::getContextsubtype,
      nativeFormat.getExt(),
      CommonConstants.CONTEXTSUBTYPE,
      nativeFormat::setExt);
    putToExt(
      nativeRequestBody::getAdunit,
      nativeFormat.getExt(),
      CommonConstants.ADUNIT,
      nativeFormat::setExt);
    putToExt(
      nativeRequestBody::getLayout,
      nativeFormat.getExt(),
      CommonConstants.LAYOUT,
      nativeFormat::setExt);
    putToExt(
      nativeRequestBody::getVer,
      nativeFormat.getExt(),
      CommonConstants.VER,
      nativeFormat::setExt);
    Converter<Asset, AssetFormat> assetAssetFormatConverter =
        converterProvider.fetch(new Conversion<>(Asset.class, AssetFormat.class));
    nativeFormat.setAsset(
        CollectionUtils.convert(
            nativeRequestBody.getAssets(), assetAssetFormatConverter, config, converterProvider));
  }
}
