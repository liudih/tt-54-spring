package com.tomtop.management.export.data.model;

import com.tomtop.management.export.annotation.ExportFileColumn;

@ExportFileColumn(name = "currencies", priority = 0)
public class CurrencyData {
	@ExportFileColumn(name = "currency", priority = 1)
	private String currency;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
