package training.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CsvImporter<T> implements Iterator<T> {

	private final RowMapper<T> rowMapper;
	private List<String> list;
	private int index = 0;

	public CsvImporter(ArrayList<String> list, RowMapper<T> rowMapper2) {
		this.rowMapper = rowMapper2;
		this.list = list;
	}

	@Override
	public boolean hasNext() {
		return index < list.size();
	}

	@Override
	public T next() {
		if (hasNext()) {
			Row row = new Row(list.get(index).split(","), list.get(index));
			index++;
			return rowMapper.map(row);
		}
		throw new RuntimeException();
	}

}
