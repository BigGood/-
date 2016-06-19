package sun.model;

public class userSchema implements SimpleSchema{
	private String id;
	private String usernum;
	private String username;
	private String password;
	private static String[] PK;
	private static String[] column = {"id","userNum","userName","passWord"};

	 public userSchema()
	    {
	        String[] pk = new String[1];
	        pk[0] = "id";
	        PK = pk;
	    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserNum() {
		return usernum;
	}
	public void setUserNum(String userNum) {
		this.usernum = userNum;
	}

	public String getUserName() {
		return username;
	}
	public void setUserName(String userName) {
		this.username = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
    public String[] getPK() {
        return PK;
    }

    @Override
    public String[] getColumn() {
        return column;
    }
}
