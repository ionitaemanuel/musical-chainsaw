package betr.intern.chainsaw.service;

import betr.intern.chainsaw.model.domain.ViewRecord;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserStatsService {
    private static Map<String, ViewRecord> listUserByIdEndpointAccessMap = new ConcurrentHashMap<>();

    public Map<String, ViewRecord> getListUserByIdEndpointAccessMap() {
        return listUserByIdEndpointAccessMap;
    }

    @Transactional
    public void setListUserByIdEndpointAccessMap(final Map<String, ViewRecord> listUserByIdEndpointAccessMap) {
        UserStatsService.listUserByIdEndpointAccessMap = listUserByIdEndpointAccessMap;
    }

    @Transactional
    public void resetListUserByIdEndpointAccessMap() {
        listUserByIdEndpointAccessMap.clear();
    }

    @Transactional
    public void clearListUserByIdEndpointAccessMapForOneUser(final String id) {
        listUserByIdEndpointAccessMap.remove(id);
    }
}
