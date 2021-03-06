package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.core.service.TrackService;
import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.persistence.domain.Account;
import de.saphijaga.spoozer.service.AccountAccessDetails;
import de.saphijaga.spoozer.service.Api;
import de.saphijaga.spoozer.service.utils.ApiService;
import de.saphijaga.spoozer.web.details.AccountDetails;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.GetSearchResultRequest;
import de.saphijaga.spoozer.web.domain.request.GetTrackDetailsRequest;
import de.saphijaga.spoozer.web.domain.response.GetServiceTrackResultResponse;
import de.saphijaga.spoozer.web.domain.response.GetTrackDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.compare;

/**
 * Created by samuel on 08.11.15.
 */
@RestController
public class TrackController {
    @Autowired
    private TrackService trackService;

    @Autowired
    private ApiService apiService;

    @Autowired
    private UserService userService;

    @MessageMapping("/getSearchResult")
    @SendToUser("/setSearchResult")
    public GetServiceTrackResultResponse getSearchResult(UserDetails user, @Payload GetSearchResultRequest request) {
        Map<String, List<TrackDetails>> searchResult = new LinkedHashMap<>();
        List<Api<? extends Account, ? extends AccountDetails, ? extends AccountAccessDetails>> apis = apiService.getApis();
        List<String> sequence = (List<String>) userService.getSettings(user).get("resultSequence");
        apis.sort((api0, api1) -> compare(sequence.indexOf(api0.getService().getName().toLowerCase()), sequence.indexOf(api1.getService().getName().toLowerCase())));
        apis.forEach(api -> {
            List<TrackDetails> apiResult = api.getSearchResult(user, request.getSearch());
            if (!apiResult.isEmpty()) {
                searchResult.put(api.getService().name().toLowerCase(), apiResult);
            }
        });
        return new GetServiceTrackResultResponse(searchResult);
    }

    @MessageMapping("/getServiceTrackDetails")
    @SendToUser("/setServiceTrackDetails")
    public GetTrackDetailsResponse getServiceTrackDetails(UserDetails user, GetTrackDetailsRequest request) {
        return new GetTrackDetailsResponse(trackService.getTrack(request.getService(), request.getId()).orElse(apiService.getApi(request.getService()).getTrack(user, request.getId())));
    }
}