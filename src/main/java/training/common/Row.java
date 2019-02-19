package training.common;

import java.util.Arrays;

public class Row {
	private final String rawString;
	private final String[] colums;

	public Row(String[] columns, String rawString) {
		this.rawString = rawString;
		this.colums = Arrays.copyOf(columns, columns.length);
	}

	public String getRawString() {
		return rawString;
	}

	public String[] getColums() {
		return colums;
	}

	public String getColumn(int index) {
		return colums[index];
	}
}
