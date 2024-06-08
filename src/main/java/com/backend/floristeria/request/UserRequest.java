package com.backend.floristeria.request;

import com.backend.floristeria.modelo.Rol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

	int id;
	String username;
	String firstname;
	String lastname;
	String country;
	Rol rol;
}
