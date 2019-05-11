package com.jhart.gamelog.utils.dbconfig;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class DbConfig {
	private String driver;
	private String url;
	private String user;
	private String password;
	private String dialect;

}
