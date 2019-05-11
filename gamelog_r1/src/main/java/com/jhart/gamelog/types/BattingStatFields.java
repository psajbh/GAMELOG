package com.jhart.gamelog.types;

public enum BattingStatFields {
	ab(1),
	h(2),
	d(3),
	t(4),
	hr(5),
	rbi(6),
	sh(7),
	sf(8),
	hbp(9),
	bb(10),
	ibb(11),
	so(12),
	sb(13),
	cs(14),
	gidp(15),
	awci(16),  //awarded first on catcher's interference
	lob(17);
	
	int code;
	
	BattingStatFields(int code){
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	
}
