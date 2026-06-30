package betr.intern.chainsaw.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserStatsService {
    private static Map<UUID, Integer> listUserByIdEndpointAccessMap = new HashMap<>();

    public Map<UUID, Integer> getListUserByIdEndpointAccessMap() {
        return listUserByIdEndpointAccessMap;
    }

    @Transactional
    public void setListUserByIdEndpointAccessMap(final Map<UUID, Integer> listUserByIdEndpointAccessMap) {
        UserStatsService.listUserByIdEndpointAccessMap = listUserByIdEndpointAccessMap;
    }

    @Transactional
    public void resetListUserByIdEndpointAccessMap() {
        listUserByIdEndpointAccessMap.clear();
    }
}
