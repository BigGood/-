/**  
 *	   @company  澳亚卫视
 *	   @author  游德禄
 *     @Email youdelu@sina.cn
 *     @date  2015年8月26日 上午9:03:05 
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
public class WeatherAlaMode {
	private Weather[] weather;
	public class Weather{
		//{"alarms":[{"level":"黄色","stat":"预警中","title":"广东省珠海珠海气象台发布灰霾黄色预警","txt":"12小时内可能出现灰霾天气，或者已经出现灰霾天气且可能持续","type":"灰霾"},{"level":"黄色","stat":"预警中","title":"广东省珠海珠海气象台发布高温黄色预警","txt":"天气闷热。一般指24小时内最高气温将接近或达到35℃或已达到35℃以上","type":"高温"}],
		private Alarms[] alarms ;
		public class Alarms{
			private String level;
			private String stat;
			public String getStat() {
				return stat;
			}
			public void setStat(String stat) {
				this.stat = stat;
			}
			public String getTitle() {
				return title;
			}
			public void setTitle(String title) {
				this.title = title;
			}
			public String getTxt() {
				return txt;
			}
			public void setTxt(String txt) {
				this.txt = txt;
			}
			private String title;
			private String txt;
			public String getLevel() {
				return level;
			}
			public void setLevel(String level) {
				this.level = level;
			}
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
			}
			private String type;
		}
		private Basic basic;
		public class Basic{
			private String city;
			private String cnty;
			private String id ;
			public String getCity() {
				return city;
			}
			public void setCity(String city) {
				this.city = city;
			}
			public String getId() {
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
			public String getLat() {
				return lat;
			}
			public void setLat(String lat) {
				this.lat = lat;
			}
			public String getLon() {
				return lon;
			}
			public void setLon(String lon) {
				this.lon = lon;
			}
			public Update getUpdate() {
				return update;
			}
			public void setUpdate(Update update) {
				this.update = update;
			}
			public String getCnty() {
				return cnty;
			}
			public void setCnty(String cnty) {
				this.cnty = cnty;
			}
			private String lat;
			private String lon;
			private Update update;
			public class Update{
				private String loc;
				public String getLoc() {
					return loc;
				}
				public void setLoc(String loc) {
					this.loc = loc;
				}
				public String getUtc() {
					return utc;
				}
				public void setUtc(String utc) {
					this.utc = utc;
				}
				private String utc ;
			}
		}
		private Daily_forecast[] daily_forecast;
		public class Daily_forecast{
			private Astro astro;
			public class Astro{
				private String sr;
				private String ss;
				public String getSr() {
					return sr;
				}
				public void setSr(String sr) {
					this.sr = sr;
				}
				public String getSs() {
					return ss;
				}
				public void setSs(String ss) {
					this.ss = ss;
				}
			}
			private Cond cond;
			public class Cond{
				private String code_d;
				private String code_n;
				public String getCode_d() {
					return code_d;
				}
				public void setCode_d(String code_d) {
					this.code_d = code_d;
				}
				public String getCode_n() {
					return code_n;
				}
				public void setCode_n(String code_n) {
					this.code_n = code_n;
				}
				public String getTxt_d() {
					return txt_d;
				}
				public void setTxt_d(String txt_d) {
					this.txt_d = txt_d;
				}
				public String getTxt_n() {
					return txt_n;
				}
				public void setTxt_n(String txt_n) {
					this.txt_n = txt_n;
				}
				private String txt_d;
				private String txt_n;
			}
			private String date ;
			private String hum;
			private String pcpn;
			private String pop;
			public String getDate() {
				return date;
			}
			public void setDate(String date) {
				this.date = date;
			}
			public String getHum() {
				return hum;
			}
			public void setHum(String hum) {
				this.hum = hum;
			}
			public String getPcpn() {
				return pcpn;
			}
			public void setPcpn(String pcpn) {
				this.pcpn = pcpn;
			}
			public String getPop() {
				return pop;
			}
			public void setPop(String pop) {
				this.pop = pop;
			}
			public String getPres() {
				return pres;
			}
			public void setPres(String pres) {
				this.pres = pres;
			}
			public Tmp getTmp() {
				return tmp;
			}
			public void setTmp(Tmp tmp) {
				this.tmp = tmp;
			}
			public String getVis() {
				return vis;
			}
			public void setVis(String vis) {
				this.vis = vis;
			}
			public Wind getWind() {
				return wind;
			}
			public void setWind(Wind wind) {
				this.wind = wind;
			}
			private String pres ;
			private Tmp tmp;
			public class Tmp{
				private String max;
				private String min;
				public String getMax() {
					return max;
				}
				public void setMax(String max) {
					this.max = max;
				}
				public String getMin() {
					return min;
				}
				public void setMin(String min) {
					this.min = min;
				}
			}
			private String vis;
			private Wind wind;
			public class Wind {
				private String deg;
				private String dir;
				public String getDeg() {
					return deg;
				}
				public void setDeg(String deg) {
					this.deg = deg;
				}
				public String getDir() {
					return dir;
				}
				public void setDir(String dir) {
					this.dir = dir;
				}
				public String getSc() {
					return sc;
				}
				public void setSc(String sc) {
					this.sc = sc;
				}
				public String getSpd() {
					return spd;
				}
				public void setSpd(String spd) {
					this.spd = spd;
				}
				private String sc;
				private String spd;
			}
			public Astro getAstro() {
				return astro;
			}
			public void setAstro(Astro astro) {
				this.astro = astro;
			}
			public Cond getCond() {
				return cond;
			}
			public void setCond(Cond cond) {
				this.cond = cond;
			}
		}
		private Now now;
		public class Now{
			private Cond cond;
			public class Cond{
				private String code;
				private String txt;
				public String getCode() {
					return code;
				}
				public void setCode(String code) {
					this.code = code;
				}
				public String getTxt() {
					return txt;
				}
				public void setTxt(String txt) {
					this.txt = txt;
				}
			}
			private String fl;
			private String hum;
			private String pcpn;
			private String pres;
			private String tmp;
			private String vis;
			public Cond getCond() {
				return cond;
			}
			public void setCond(Cond cond) {
				this.cond = cond;
			}
			public String getFl() {
				return fl;
			}
			public void setFl(String fl) {
				this.fl = fl;
			}
			public String getHum() {
				return hum;
			}
			public void setHum(String hum) {
				this.hum = hum;
			}
			public String getPcpn() {
				return pcpn;
			}
			public void setPcpn(String pcpn) {
				this.pcpn = pcpn;
			}
			public String getPres() {
				return pres;
			}
			public void setPres(String pres) {
				this.pres = pres;
			}
			public String getTmp() {
				return tmp;
			}
			public void setTmp(String tmp) {
				this.tmp = tmp;
			}
			public String getVis() {
				return vis;
			}
			public void setVis(String vis) {
				this.vis = vis;
			}
			public Wind getWind() {
				return wind;
			}
			public void setWind(Wind wind) {
				this.wind = wind;
			}
			private Wind wind;
			public class Wind{
				private String deg;
				private String dir;
				private String sc;
				public String getDeg() {
					return deg;
				}
				public void setDeg(String deg) {
					this.deg = deg;
				}
				public String getDir() {
					return dir;
				}
				public void setDir(String dir) {
					this.dir = dir;
				}
				public String getSc() {
					return sc;
				}
				public void setSc(String sc) {
					this.sc = sc;
				}
				public String getSpd() {
					return spd;
				}
				public void setSpd(String spd) {
					this.spd = spd;
				}
				private String spd;
			}
		}
		private String status;
		private Suggestion suggestion;
		public class Suggestion{
			private Baisc comf;
			public class Baisc{
				private String brf;
				private String txt;
				public String getBrf() {
					return brf;
				}
				public void setBrf(String brf) {
					this.brf = brf;
				}
				public String getTxt() {
					return txt;
				}
				public void setTxt(String txt) {
					this.txt = txt;
				}
			}
			private Baisc cw;
		 
			public Baisc getCw() {
				return cw;
			}

			public void setCw(Baisc cw) {
				this.cw = cw;
			}

			public Baisc getDrsg() {
				return drsg;
			}

			public void setDrsg(Baisc drsg) {
				this.drsg = drsg;
			}

			public Baisc getFlu() {
				return flu;
			}

			public void setFlu(Baisc flu) {
				this.flu = flu;
			}

			public Baisc getSport() {
				return sport;
			}

			public void setSport(Baisc sport) {
				this.sport = sport;
			}

			public Baisc getTrav() {
				return trav;
			}

			public void setTrav(Baisc trav) {
				this.trav = trav;
			}

			public Baisc getUv() {
				return uv;
			}

			public void setUv(Baisc uv) {
				this.uv = uv;
			}

			private Baisc drsg;
			 
			private Baisc flu;
		 
			private Baisc sport;
			 
			private Baisc trav;
			 
			private Baisc uv;

			public Baisc getComf() {
				return comf;
			}

			public void setComf(Baisc comf) {
				this.comf = comf;
			}
		}
		public Basic getBasic() {
			return basic;
		}
		public void setBasic(Basic basic) {
			this.basic = basic;
		}
		public Daily_forecast[] getDaily_forecast() {
			return daily_forecast;
		}
		public void setDaily_forecast(Daily_forecast[] daily_forecast) {
			this.daily_forecast = daily_forecast;
		}
		public Now getNow() {
			return now;
		}
		public void setNow(Now now) {
			this.now = now;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public Suggestion getSuggestion() {
			return suggestion;
		}
		public void setSuggestion(Suggestion suggestion) {
			this.suggestion = suggestion;
		}
		public Alarms[] getAlarms() {
			return alarms;
		}
		public void setAlarms(Alarms[] alarms) {
			this.alarms = alarms;
		}
	}
	public Weather[] getWeather() {
		return weather;
	}
	public void setWeather(Weather[] weather) {
		this.weather = weather;
	}
}
