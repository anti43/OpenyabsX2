package openyabsx2

import grails.gorm.services.Service

@Service(HistoryLogEntry)
interface HistoryLogEntryService {

    HistoryLogEntry get(Serializable id)

    List<HistoryLogEntry> list(Map args)

    Long count()

    void delete(Serializable id)

    HistoryLogEntry save(HistoryLogEntry historyLogEntry)
}