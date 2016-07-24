import html.parser as HTMLparser;
import urllib.request
import sys
import time
import json
import random
url = "http://wx.qq.com"

# class parseLinks(HTMLparser.HTMLParser): 
#     def handle_starttag(self, tag, attrs): 
#         if tag == 'img':
#             for name,value in attrs:
#                 if name == 'mm-src':
#                     print(value);
#                     print(self.get_starttag_text());
#                     
#                                
#                 
# lParser = parseLinks()
# lParser.feed(urllib.request.urlopen(url).read().decode('UTF-8'))

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

print(logindata[logindata.find('uri="')+5:-2])
xmldata = urllib.request.urlopen(logindata[logindata.find('uri="')+5:-2]+"&fun=new").read().decode('UTF-8');
print(xmldata)
skey=xmldata[xmldata.find("<skey>")+6:xmldata.find("</skey>")]
sid=xmldata[xmldata.find("<wxsid>")+7:xmldata.find("</wxsid>")]
uin=xmldata[xmldata.find("<wxuin>")+7:xmldata.find("</wxuin>")]
pass_ticket=xmldata[xmldata.find("<pass_ticket>")+13:xmldata.find("</pass_ticket>")]



params = {
'BaseRequest' :    {
            'Uin': int(uin),
            'Sid': sid,
            'Skey': skey,
            'DeviceID': 'e' + repr(random.random())[2:17],
        }
}  

req = urllib.request.Request("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r=%s&pass_ticket=%s" % (int(time.time()),pass_ticket),json.dumps(params).encode(encoding='UTF8'))
req.add_header('ContentType','application/json; charset=UTF-8' ) 
initdata = urllib.request.urlopen(req).read().decode('UTF-8')

print(json.loads(initdata))
