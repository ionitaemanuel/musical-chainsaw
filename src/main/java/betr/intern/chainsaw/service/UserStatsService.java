package betr.intern.chainsaw.service;

import betr.intern.chainsaw.model.domain.ViewRecord;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserStatsService {
    private static Map<UUID, ViewRecord> listUserByIdEndpointAccessMap = new ConcurrentHashMap<>();

    public Map<UUID, ViewRecord> getListUserByIdEndpointAccessMap() {
        return listUserByIdEndpointAccessMap;
    }

    @Transactional
    public void setListUserByIdEndpointAccessMap(final Map<UUID, ViewRecord> listUserByIdEndpointAccessMap) {
        UserStatsService.listUserByIdEndpointAccessMap = listUserByIdEndpointAccessMap;
    }

    @Transactional
    public void resetListUserByIdEndpointAccessMap() {
        listUserByIdEndpointAccessMap.clear();
    }

    @Transactional
    public void clearListUserByIdEndpointAccessMapForOneUser(final UUID id) {
        listUserByIdEndpointAccessMap.remove(id);
    }
}
