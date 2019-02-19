package training.importer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import training.common.CsvImporter;
import training.common.RowMapper;
import training.resources.BaseEntity;

public abstract class FileImporter<T extends BaseEntity> {
	private CsvImporter<T> csvImporter;

	public List<T> importFile(Reader fileReader) throws IOException {
		ArrayList<String> list = new ArrayList<>();
		String thisLine = "";
		BufferedReader br = new BufferedReader(fileReader);
		while ((thisLine = br.readLine()) != null) {
			list.add(thisLine);
		}
		csvImporter = new CsvImporter<T>(list, getRowMapper());
		List<T> objectList = new ArrayList<>();
		while (csvImporter.hasNext()) {
			objectList.add(csvImporter.next());
		}

		return objectList;
	}

	protected abstract RowMapper<T> getRowMapper();
}
