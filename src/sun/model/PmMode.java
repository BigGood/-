/**  
 *	   @company  澳亚卫视
 *	   @author  游德禄
 *     @Email youdelu@sina.cn
 *     @date  2015年8月25日 下午1:50:07 
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
public class PmMode {
	private Data data;
	public class Data{
		private String city;
		private String pm2_5;
		private String cls;
		private String primary;
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getPrimary() {
			return primary;
		}
		public void setPrimary(String primary) {
			this.primary = primary;
		}
		public String getCls() {
			return cls;
		}
		public void setCls(String cls) {
			this.cls = cls;
		}
		public String getPm2_5() {
			return pm2_5;
		}
		public void setPm2_5(String pm2_5) {
			this.pm2_5 = pm2_5;
		}
	}
	private String message;
	private int error_code;
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
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}

}
