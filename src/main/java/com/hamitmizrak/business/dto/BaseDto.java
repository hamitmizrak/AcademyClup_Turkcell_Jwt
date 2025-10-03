package com.hamitmizrak.business.dto;

import com.hamitmizrak.audit.AuditingAwareBaseDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

// LOMBOK
@Getter
@Setter

abstract public class BaseDto extends AuditingAwareBaseDto implements Serializable {

    // SERILEŞTIRME
    public static final Long serialVersionUID = 456546546546456456L;

    // FIELD
    // ID
    protected Long id;

    // SYSTEM DATE
    protected Date systemCreatedDate;
}
