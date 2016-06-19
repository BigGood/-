/**  
 *	   @company  澳亚卫视
 *	   @author  游德禄
 *     @Email youdelu@sina.cn
 *     @date  2015年8月25日 下午2:05:39 
 *     @version 1.0 
 *     @parameter  
 *     @return  
 *     
 */
package sun.model;

/**
 * @author 游德禄
 *
 */
public class SFCard {
	private Data data;
	public class Data{
		private String gender;
		private String birthday;
		private String address;
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public String getBirthday() {
			return birthday;
		}
		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
	}
	private String message;
	private int error_code ;
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getError_code() {
		return error_code;
	}
	public void setError_code(int error_code) {
		this.error_code = error_code;
	}

}
