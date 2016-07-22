import html.parser as HTMLparser;
import urllib.request;
import sys
import time
url = "http://wx.qq.com"

class parseLinks(HTMLparser.HTMLParser): 
    def handle_starttag(self, tag, attrs): 
        if tag == 'img':
            for name,value in attrs:
                if name == 'mm-src':
                    print(value);
                    print(self.get_starttag_text());
                    
                               
                
lParser = parseLinks()
lParser.feed(urllib.request.urlopen(url).read().decode('UTF-8'))

data = urllib.request.urlopen("https://login.wx.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN&_=1469174552228%20Request%20Method:GET").read().decode('UTF-8');
print(data)
imgsrc=data[data.find('"')+1:-2]
imgdata = urllib.request.urlopen("https://login.weixin.qq.com/qrcode/"+imgsrc).read()

object = open("C://Users/Administrator/Desktop/123.png","wb")
object.write(imgdata)
object.close();

time.sleep(20)
logindata = urllib.request.urlopen('https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login?tip=%s&uuid=%s&_=%s' % (1, imgsrc, int(time.time()))).read().decode('UTF-8');
print(logindata)

