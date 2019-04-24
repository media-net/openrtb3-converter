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

package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.BidRequest2_X;
import net.media.openrtb25.request.Imp;
import net.media.openrtb25.request.Source;
import net.media.openrtb25.request.User;
import net.media.openrtb3.*;
import net.media.utils.CollectionToCollectionConverter;
import net.media.utils.CollectionUtils;
import net.media.utils.CommonConstants;
import net.media.utils.JacksonObjectMapperUtils;
import net.media.utils.Provider;

import java.util.HashMap;
import java.util.Map;

/** Created by rajat.go on 03/01/19. */
public class BidRequestToRequestConverter implements Converter<BidRequest2_X, Request> {

  private String bidRequestUserCustomdata(BidRequest2_X bidRequest) {
    if (bidRequest == null) {
      return null;
    }
    User user = bidRequest.getUser();
    if (user == null) {
      return null;
    }
    String customdata = user.getCustomdata();
    if (customdata == null) {
      return null;
    }
    return customdata;
  }

  @Override
  public Request map(BidRequest2_X source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }

    Request request = new Request();

    enhance(source, request, config, converterProvider);

    return request;
  }

  @Override
  public void enhance(
      BidRequest2_X source, Request target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    Converter<BidRequest2_X, Context> bidRequestContextConverter =
        converterProvider.fetch(new Conversion<>(BidRequest2_X.class, Context.class));
    Converter<Imp, Item> impItemConverter =
        converterProvider.fetch(new Conversion<>(Imp.class, Item.class));
    target.setContext(bidRequestContextConverter.map(source, config, converterProvider));
    target.setItem(
        CollectionToCollectionConverter.convert(
            source.getImp(), impItemConverter, config, converterProvider));
    target.setPack(source.getAllimps());
    String customdata = bidRequestUserCustomdata(source);
    if (customdata != null) {
      target.setCdata(customdata);
    }
    target.setId(source.getId());
    target.setTest(source.getTest());
    target.setTmax(source.getTmax());
    target.setAt(source.getAt());
    target.setCur(CollectionUtils.copyCollection(source.getCur(), config));
    Converter<Source, net.media.openrtb3.Source> source25Source3Converter =
        converterProvider.fetch(new Conversion<>(Source.class, net.media.openrtb3.Source.class));
    target.setSource(source25Source3Converter.map(source.source, config, converterProvider));
    Map<String, Object> map = source.getExt();
    if (map != null) {
      target.setExt(new HashMap<>(map));
    }

    if (!CollectionUtils.isEmpty(target.getItem())) {
      for (Item item : target.getItem()) {
        if (item.getSpec() == null) {
          item.setSpec(new Spec());
        }
        bidRequestToSpec(source, item.getSpec(), config);
      }
    }
    if (source.getWseat() != null && source.getWseat().size() > 0) {
      target.setWseat(1);
      target.setSeat(CollectionUtils.copyCollection(source.getWseat(), config));
    } else {
      target.setWseat(0);
      target.setSeat(CollectionUtils.copyCollection(source.getBseat(), config));
    }
    if (target.getExt() == null) return;
    if (target.getExt().containsKey(CommonConstants.CATTAX)) {
      target.getExt().remove(CommonConstants.CATTAX);
    }
    if (target.getExt().containsKey(CommonConstants.RESTRICTIONS)) {
      target.getExt().remove(CommonConstants.RESTRICTIONS);
    }
    if (source.getExt() == null) return;
    if (source.getExt().containsKey(CommonConstants.DOOH)) {
      if (target.getContext() == null) target.setContext(new Context());
      try {
        target.getContext().setDooh(JacksonObjectMapperUtils.getMapper().convertValue(source.getExt().get(CommonConstants.DOOH),
          Dooh.class));
        target.getExt().remove(CommonConstants.DOOH);
      } catch (ClassCastException e) {
        throw new OpenRtbConverterException("error while typecasting ext for BidRequest2_X", e);
      }
    }
  }

  private void bidRequestToSpec(BidRequest2_X bidRequest, Spec mappingTarget, Config config) {
    if (bidRequest == null) {
      return;
    }

    if (mappingTarget.getPlacement() == null) {
      mappingTarget.setPlacement(new Placement());
    }
    bidRequestToPlacement(bidRequest, mappingTarget.getPlacement(), config);
  }

  private void bidRequestToPlacement(
      BidRequest2_X bidRequest, Placement mappingTarget, Config config) {
    if (bidRequest == null) {
      return;
    }

    mappingTarget.setWlang(CollectionUtils.copyCollection(bidRequest.getWlang(), config));
  }
}
