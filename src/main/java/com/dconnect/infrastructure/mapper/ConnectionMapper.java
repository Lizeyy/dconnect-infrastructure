package com.dconnect.infrastructure.mapper;


import com.dconnect.client.protocol.domain.request.ConnectionCreateRequest;
import com.dconnect.client.protocol.domain.response.ConnectionCreateResponse;
import com.dconnect.infrastructure.domain.Connection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ConnectionMapper {

    ConnectionMapper INSTANCE = Mappers.getMapper(ConnectionMapper.class);
    @Mapping(target = "creationDate", constant = "OffsetDateTime.MIN", qualifiedByName = "dateNow", dateFormat = "dd.MM.yyyy HH:mm:ss")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modificationBy", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "active", constant = "true")
    Connection map(ConnectionCreateRequest request);

    //ConnectionCreateResponse map(Connection connection);

    /*
    public static Connection map(ConnectionCreateRequest request) {
        final Connection connection = new Connection();
        connection.setName(request.getName());
        connection.setCreationBy(request.getCreationBy());
        connection.setCreationDate(OffsetDateTime.now());
        connection.setActive(true);
        connection.setConPrivate(request.isConPrivate());
        return connection;
    }

     */

    @Named("dateNow")
    default OffsetDateTime dateNow() {
        return OffsetDateTime.now();
    }
}
