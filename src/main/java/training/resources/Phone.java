package training.resources;

public class Phone extends BaseEntity {

	enum Type {
		ANALOGIC, VOIP
	}

	public Phone() {
		super();
		this.type = Type.VOIP;
	}

	public Phone(String id, String type) {
		this.inventoryNumber = Integer.parseInt(id);
		if (type.equals(Type.VOIP.toString())) {
			this.type = Type.VOIP;
		} else {
			this.type = Type.ANALOGIC;
		}
	}

	private Type type;

	public String getType() {
		return type.name();
	}

	public void setType(String type) {
		if (type.equals(Type.VOIP.name())) {
			this.type = Type.VOIP;
		} else {
			this.type = Type.ANALOGIC;
		}
	}

}
