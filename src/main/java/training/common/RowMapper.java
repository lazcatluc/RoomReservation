package training.common;

public abstract class RowMapper<T> {
	private Row row;
	private int currentColumnIndex = 0;

	public final T map(Row row) {
		currentColumnIndex = 0;
		this.row = row;
		T instance = doMap();
		return instance;
	}

	public String nextString() {
		if (currentColumnIndex >= row.getColums().length) {
			throw new RuntimeException("Too few columns");
		}
		return row.getColumn(currentColumnIndex++);
	}

	public int nextInt(){
		if(currentColumnIndex>=row.getColums().length){
			throw new RuntimeException("Too few columns");
		}
		int temp = Integer.parseInt(row.getColumn(currentColumnIndex++));
		return temp;
	}

	protected abstract T doMap();
}
