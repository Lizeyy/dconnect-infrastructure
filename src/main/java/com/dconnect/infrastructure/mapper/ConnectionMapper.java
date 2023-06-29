package com.dconnect.infrastructure.mapper;


import com.dconnect.client.protocol.domain.request.ConnectionCreateRequest;
import com.dconnect.client.protocol.domain.response.ConnectionCreateResponse;
import com.dconnect.infrastructure.domain.Connection;
import com.netflix.servo.util.Strings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ConnectionMapper {

    ConnectionMapper INSTANCE = Mappers.getMapper(ConnectionMapper.class);
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modificationBy", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "rootChannel", ignore = true)
    @Mapping(target = "channels", ignore = true)
    Connection map(ConnectionCreateRequest request);

    default ConnectionCreateResponse map(Connection connection, String token) {
        if(Objects.isNull(connection) || Strings.isNullOrEmpty(token)) return null;

        final ConnectionCreateResponse response = new ConnectionCreateResponse();
        response.setName(connection.getName());
        response.setJoinToken(token);
        return response;
    }
}
