package training.importer;

import training.common.RowMapper;
import training.resources.Phone;

public class PhoneImporter extends FileImporter<Phone>{

	private RowMapper<Phone> rowMapper = new PhoneRowMapper();
	
	private class PhoneRowMapper extends RowMapper<Phone> {

		@Override
		protected Phone doMap() {
			Phone phone = new Phone();
			phone.setInventoryNumber(nextInt());
			phone.setType(nextString());
			return phone;
		}

	}

	@Override
	protected RowMapper<Phone> getRowMapper() {
		return rowMapper;
	}
}
