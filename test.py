import html.parser as HTMLparser;
import urllib.request
import sys
import time
import json
import random
from com.sun import jsonformat
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

#获取二维码
data = urllib.request.urlopen("https://login.wx.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN&_=1469174552228%20Request%20Method:GET").read().decode('UTF-8');
print(data)
imgsrc=data[data.find('"')+1:-2]
imgdata = urllib.request.urlopen("https://login.weixin.qq.com/qrcode/"+imgsrc).read()

#写入图片
object = open("C://Users/Administrator/Desktop/123.png","wb")
object.write(imgdata)
object.close();

code=0
#等待扫码，扫码后调用登录，返回一个url
while code!=200:
    time.sleep(3)
    logindata = urllib.request.urlopen('https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login?tip=%s&uuid=%s&_=%s' % (1, imgsrc, int(time.time()))).read().decode('UTF-8');
    print(logindata)
    code=int(logindata[logindata.find('code=')+5:logindata.find('\n')-1])
    print(code)
    

print(logindata)

#调用url 获取sid uin skey
print(logindata[logindata.find('uri="')+5:-2])
xmldata = urllib.request.urlopen(logindata[logindata.find('uri="')+5:-2]+"&fun=new").read().decode('UTF-8');
print(xmldata)
skey=xmldata[xmldata.find("<skey>")+6:xmldata.find("</skey>")]
sid=xmldata[xmldata.find("<wxsid>")+7:xmldata.find("</wxsid>")]
uin=xmldata[xmldata.find("<wxuin>")+7:xmldata.find("</wxuin>")]
pass_ticket=xmldata[xmldata.find("<pass_ticket>")+13:xmldata.find("</pass_ticket>")]


DeviceId='e' + repr(random.random())[2:17]
params = {
'BaseRequest' :    {
            'Uin': int(uin),
            'Sid': sid,
            'Skey': skey,
            'DeviceID': DeviceId,
        }
} 




print("参数："+params.get('BaseRequest').get('DeviceID')) 
#初始化信息
req = urllib.request.Request("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r=%s&pass_ticket=%s" % (int(time.time()),pass_ticket),json.dumps(params).encode(encoding='UTF8'))
req.add_header('ContentType','application/json; charset=UTF-8' ) 
initdata = urllib.request.urlopen(req).read().decode('UTF-8')

jsondata=json.loads(initdata)
print(jsondata)

print(jsondata['User']['UserName'])


synckey='|'.join(
            [str(keyVal['Key']) + '_' + str(keyVal['Val']) for keyVal in jsondata['SyncKey']['List']])


ToUserName=""
for contact in jsondata['ContactList']:
    if contact['NickName']=='拒绝黄赌毒':
        ToUserName=contact['UserName']
        break;
xiaobingId=''
    
while 1==1:
    time.sleep(3) 
    params = { 
         'BaseRequest': { 'Uin': int(uin), 'Sid': sid, 'Skey': skey, 'DeviceID': DeviceId }, 
         'SyncKey': jsondata['SyncKey'], 
         'rr': ~int(time.time())
    }
    str1=jsonformat.someutil().toJson(params,"")
    print(str1)
    req = urllib.request.Request('https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsync?sid=%s&skey=%s&lang=zh_CN&pass_ticket=%s' % (sid, skey,pass_ticket),str1.encode('UTF-8'))
    req.add_header('ContentType','application/json; charset=UTF-8' ) 
    initdata = urllib.request.urlopen(req).read().decode('UTF-8')
    print(json.loads(initdata))    
    #     手动填入小冰ID
    
    if xiaobingId =='':
        xiaobingId=input()
        print(xiaobingId)
        if xiaobingId !='':
            break
        
    
    
#     str1=jsonformat.someutil().toJson(params,"")
#     req = urllib.request.urlopen('https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsync?sid=%s&skey=%s&pass_ticket=%s' % (sid, skey, pass_ticket),str1.encode('UTF-8'))
#     req.add_header('ContentType','application/json; charset=UTF-8' ) 
#     initdata = urllib.request.urlopen(req).read().decode('UTF-8')
#     print(json.loads(initdata))


while 1==1:
    params = { 
         'BaseRequest': { 'Uin': int(uin), 'Sid': sid, 'Skey': skey, 'DeviceID': DeviceId }, 
         'SyncKey': jsondata['SyncKey'], 
         'rr': ~int(time.time())
    }
    str1=jsonformat.someutil().toJson(params,"")
    print(str1)
    req = urllib.request.Request('https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsync?sid=%s&skey=%s&lang=zh_CN&pass_ticket=%s' % (sid, skey,pass_ticket),str1.encode('UTF-8'))
    req.add_header('ContentType','application/json; charset=UTF-8' ) 
    initdata = urllib.request.urlopen(req).read().decode('UTF-8')
    SyncKey=json.loads(initdata)['SyncKey']
    
    randomNum=str(int(time.time() * 1000)) + \
                str(random.random())[:5].replace('.', '')
    for msgAdd in json.loads(initdata)['AddMsgList']:
        if msgAdd['FromUserName']==ToUserName:
            print('向小冰发送'+msgAdd['Content'])
            params = { 
                      'BaseRequest': { 'Uin': int(uin), 'Sid': sid, 'Skey': skey, 'DeviceID': DeviceId }, 
                    'Msg': { 
                         'Type': 1 , 
                         'Content': msgAdd['Content'], 
                         'FromUserName': jsondata['User']['UserName'], 
                         'ToUserName': xiaobingId, 
                         'LocalID': randomNum, 
                         'ClientMsgId': randomNum 
                    } 
            }
            str1=jsonformat.someutil().toJson(params,"")
            req = urllib.request.Request("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg?pass_ticket=%s" % (pass_ticket),str1.encode('UTF-8'))
            req.add_header('ContentType','application/json; charset=UTF-8' ) 
            initdata = urllib.request.urlopen(req).read().decode('UTF-8')
            
    time.sleep(3)
            
    params = { 
         'BaseRequest': { 'Uin': int(uin), 'Sid': sid, 'Skey': skey, 'DeviceID': DeviceId }, 
         'SyncKey': SyncKey, 
         'rr': ~int(time.time())
    }
    str1=jsonformat.someutil().toJson(params,"")
#     print(str1)
    req = urllib.request.Request('https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsync?sid=%s&skey=%s&lang=zh_CN&pass_ticket=%s' % (sid, skey,pass_ticket),str1.encode('UTF-8'))
    req.add_header('ContentType','application/json; charset=UTF-8' ) 
    initdata = urllib.request.urlopen(req).read().decode('UTF-8')
#     print(json.loads(initdata))
    for msgAdd in json.loads(initdata)['AddMsgList']:
        if msgAdd['FromUserName']==xiaobingId:
            print("小冰回复："+msgAdd['Content'])
            params = { 
                      'BaseRequest': { 'Uin': int(uin), 'Sid': sid, 'Skey': skey, 'DeviceID': DeviceId }, 
                    'Msg': { 
                         'Type': 1 , 
                         'Content': msgAdd['Content'], 
                         'FromUserName': jsondata['User']['UserName'], 
                         'ToUserName': ToUserName, 
                         'LocalID': randomNum, 
                         'ClientMsgId': randomNum 
                    } 
            }
            str1=jsonformat.someutil().toJson(params,"")
            req = urllib.request.Request("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg?pass_ticket=%s" % (pass_ticket),str1.encode('UTF-8'))
            req.add_header('ContentType','application/json; charset=UTF-8' ) 
            initdata = urllib.request.urlopen(req).read().decode('UTF-8')
    
#     sendMsg = input();
#     randomNum=str(int(time.time() * 1000)) + \
#                 str(random.random())[:5].replace('.', '')
#     params = { 
#          'BaseRequest': { 'Uin': int(uin), 'Sid': sid, 'Skey': skey, 'DeviceID': DeviceId }, 
#          'Msg': { 
#              'Type': 1 , 
#              'Content': sendMsg, 
#              'FromUserName': jsondata['User']['UserName'], 
#              'ToUserName': ToUserName, 
#              'LocalID': randomNum, 
#              'ClientMsgId': randomNum 
#          } 
#     }
#     str1=jsonformat.someutil().toJson(params,"")
#     req = urllib.request.Request("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg?pass_ticket=%s" % (pass_ticket),str1.encode('UTF-8'))
#     req.add_header('ContentType','application/json; charset=UTF-8' ) 
#     initdata = urllib.request.urlopen(req).read().decode('UTF-8')
#     
#     print(json.loads(initdata))



# randomNum=str(int(time.time() * 1000)) + \
#             str(random.random())[:5].replace('.', '')
# params1 = { 
#      'BaseRequest': { 'Uin': int(uin), 'Sid': sid, 'Skey': skey, 'DeviceID': params.get('BaseRequest').get('DeviceID') }, 
#      'Msg': { 
#          'Type': 1 , 
#          'Content': '啦啦啦', 
#          'FromUserName': '@95449721a1167442c65068f550f36222780684a30adfa4149289f256777b3744', 
#          'ToUserName': '@@f6ba269c839dc927ec5a7a7c4af144dcb6c5540970f30032fece02e6bc56c5b9', 
#          'LocalID': randomNum, 
#          'ClientMsgId': randomNum 
#      } 
# }
# #初始化信息
# req = urllib.request.Request("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg?pass_ticket=%s" % ('Gry83u1MuJ1QLh1z%2Fwsbi8s4WGJ0v55G6NyYLDiOwL%2Fm4WXd0H6z9ZEhc5LSHzkM'),json.dumps(params1).encode(encoding='UTF8'))
# req.add_header('ContentType','application/json; charset=UTF-8' ) 
# initdata = urllib.request.urlopen(req).read().decode('UTF-8')
# 
# print(json.loads(initdata))
