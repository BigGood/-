package sun.model;

import java.util.List;

public class Table {
	private long id;
	private List user;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Table(long id) {
		// TODO Auto-generated constructor stub
		this.id=id;
	}
	public List getUser() {
		return user;
	}

	public void setUser(List user) {
		this.user = user;
	}


}
