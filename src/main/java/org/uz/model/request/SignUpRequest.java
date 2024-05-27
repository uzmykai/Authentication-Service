package org.uz.model.request;

import lombok.Data;

@Data
public class SignUpRequest {
    private String  name;
    private  String ip;
    private String password;
}
