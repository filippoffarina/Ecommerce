package org.acme.application.dto;

import java.util.List;

public class UserRequest {

    public String codiceFiscale;
    public String name;
    public String surname;
    public String mail;
    public Long phone;
    public List<String> roles; // ["ADMIN", "CUSTOMER"]
}