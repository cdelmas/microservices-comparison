/*
   Copyright 2015 Cyril Delmas

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.cdelmas.spike.springboot.hello;

import io.github.cdelmas.spike.common.domain.Car;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@EnableOAuth2Resource
@RestController
public class SampleController {

    @RequestMapping("/")
    @ResponseBody
    String home(@RequestHeader("Authorization") String authToken) {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Accept", "application/json");
        headers.add("Authorization", authToken);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, headers);

        RestTemplate rest = new RestTemplate();
        ResponseEntity<Car[]> responseEntity = rest.exchange("https://localhost:8443/cars", HttpMethod.GET, requestEntity, Car[].class);
        List<Car> cars = asList(responseEntity.getBody());

        return "Hello World! " + cars.stream().map(Car::getName).collect(toList());
    }

}
