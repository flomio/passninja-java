package com.passninja.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.passninja.exception.ApiException;
import com.passninja.exception.AuthenticationException;
import com.passninja.net.ApiResource;
import com.passninja.net.PassninjaResponse;
import com.passninja.net.RequestOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PassTemplate extends ApiResource {

    public static final String RESOURCE = "pass_templates";

    @JsonProperty private final String id;
    @JsonProperty private final String name;
    @JsonProperty private final String passTypeId;
    @JsonProperty private final String platform;
    @JsonProperty private final String style;
    @JsonProperty private final Integer issuedPassCount;
    @JsonProperty private final Integer installedPassCount;

    @JsonCreator
    public PassTemplate(
            @JsonProperty("id") final String id,
            @JsonProperty("name") final String name,
            @JsonProperty("passTypeId") final String passTypeId,
            @JsonProperty("platform") final String platform,
            @JsonProperty("style") final String style,
            @JsonProperty("issued_pass_count") final Integer issuedPassCount,
            @JsonProperty("installed_pass_count") final Integer installedPassCount) {
        this.id = id;
        this.name = name;
        this.passTypeId = passTypeId;
        this.platform = platform;
        this.style = style;
        this.issuedPassCount = issuedPassCount;
        this.installedPassCount = installedPassCount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassTypeId() {
        return passTypeId;
    }

    public String getPlatform() {
        return platform;
    }

    public String getStyle() {
        return style;
    }

    public Integer getIssuedPassCount() {
        return issuedPassCount;
    }

    public Integer getInstalledPassCount() {
        return installedPassCount;
    }

    @Override
    public String toString() {
        return "PassTemplate{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", passTypeId='" + passTypeId + '\''
                + ", platform='" + platform + '\''
                + ", style='" + style + '\''
                + ", issuedPassCount='" + issuedPassCount + '\''
                + ", installedPassCount='" + installedPassCount + '\''
                + '}';
    }

    public static PassninjaResponse<PassTemplate> find(String passTemplateId) throws ApiException, IOException,
        AuthenticationException {
        return request(RequestMethod.GET, RESOURCE + "/" + passTemplateId, null, PassTemplate.class, null);
    }
}
