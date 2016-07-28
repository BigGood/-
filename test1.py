import urllib.request
import json
from json import *
import sys
import time
import random

randomNum=str(int(time.time() * 1000)) + \
            str(random.random())[:5].replace('.', '')
msg='啦啦啦' 
print("\u5566")    
params = { 
     'BaseRequest': { 'Uin': 679154514, 'Sid': '7nb0U2OIcjHJDDy4', 'Skey': '@crypt_15364f67_6582315adb05b871561317ddc31ce09f', 'DeviceID': 'e558641053297944' }, 
     'Msg': { 
         'Type': 1 , 
         'Content': msg, 
         'FromUserName': '@7d758e6d1fdb763c395c62660d2488597c2481f9eed90b63e66287556cc6a545', 
         'ToUserName': '@@8c2eba2460588ab7446a292cddbcc7b900461709be40c845c049b2a5b7254d4a', 
         'LocalID': randomNum, 
         'ClientMsgId': randomNum 
     } 
}
param = '{"Msg": {"ToUserName": "@@8c2eba2460588ab7446a292cddbcc7b900461709be40c845c049b2a5b7254d4a", "ClientMsgId": "'+randomNum+'", "Type": 1, "LocalID": "'+randomNum+'", "FromUserName": "@7d758e6d1fdb763c395c62660d2488597c2481f9eed90b63e66287556cc6a545", "Content": "'+msg+'"}, "BaseRequest": {"Skey": "@crypt_15364f67_6582315adb05b871561317ddc31ce09f", "Uin": 679154514, "Sid": "7nb0U2OIcjHJDDy4", "DeviceID": "e558641053297944"}}'
#初始化信息
# JSONEncoder().encode(params
def toJson(obj,strObj):
    if type(obj)==dict:
        strObj+="{"    
        for key in obj.keys():
            if type(obj[key])==dict or type(obj[key])==list:
                strObj+="\""+key+"\""+":"
                strObj+=toJson(obj[key], "")
            else:
                if type(key)==str:
                    strObj+="\""+key+"\""
                    strObj+=":"
                if type(obj[key])==str:
                    strObj+="\""+obj[key]+"\""
                else:
                    strObj+=str(obj[key]) 
                strObj+=","        
        
        strObj+="},"          
    return strObj
str=toJson(params,"")
print(str)
req = urllib.request.Request("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg?pass_ticket=%s" % ('59O%2BUdFL3y5rVraYPjpOOKEcgDwIasHJ8AFdXGDNBP%2Fv7tObzqZExu35Dc1u%2BzqO'),str.encode('UTF-8'))
req.add_header('ContentType','application/json; charset=UTF-8' ) 
initdata = urllib.request.urlopen(req).read().decode('UTF-8')

print(json.loads(initdata))




