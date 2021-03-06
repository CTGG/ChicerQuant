import requests
from datetime import timedelta, datetime
import pandas as pd
import time

URL="http://114.55.37.133:8010/api/"
token="2ddaec3a541de9ce9430ad95119655bb"
header={"X-Auth-Code":token}

def buildfield(start,end):
    realstart=start-timedelta(days=1)
    realend=end+timedelta(days=1)
    
    dic={'end':realend.strftime('%Y-%m-%d'),'start':realstart.strftime('%Y-%m-%d'),'fields':'open high low close adj_price volume turnover pb'}
    return dic

def buildfieldv(start,end):
    realstart=start-timedelta(days=1)
    realend=end+timedelta(days=1)
    
    dic={'end':realend.strftime('%Y-%m-%d'),'start':realstart.strftime('%Y-%m-%d'),'fields':'open+high+close+low+volume+adj_price+pb'}
    return dic
def getBench(id,start,end):
    url=URL+'benchmark/hs300'
    s=requests.session()
    s.keep_alive=False
    response=requests.get(url=url,params=buildfieldv(start, end),headers=header)
#     try:
    print response.url
    print response.json()
    ans=response.json()['data']['trading_info']
    df=pd.DataFrame(ans)
    df['date']=pd.DatetimeIndex(df['date'])    
    df.set_index('date', drop=True, inplace=True)
    return df
#     except :
#         time.sleep(0.05)
#         return getSingleStock(id, start, end)

def getSingleStock(id,start,end):
    url=URL+'stock/'+id+'/'
    s=requests.session()
    s.keep_alive=False
    response=requests.get(url=url,params=buildfield(start, end),headers=header)
    try:
        ans=response.json()['data']['trading_info']
        df=pd.DataFrame(ans)
        df['date']=pd.DatetimeIndex(df['date'])    
        df.set_index('date', drop=True, inplace=True)
        return df
    except :
        time.sleep(0.05)
        return getSingleStock(id, start, end)

def getStockList():
    szdic={'year':'2015','exchange':'sz'}
    shdic={'year':'2015','exchange':'sh'}
    url=URL+'stocks/'
    try:
        respsz=requests.get(url=url,params=szdic,headers=header)
        print respsz.json()
        sz=respsz.json()['data']
        #     print sz
        respsh=requests.get(url=url,params=shdic,headers=header)
        print respsh.url
        sh=respsh.json()['data']
        ans=[]
        for obj in sh:
            ans.append(obj['name'])
        for obj in sz:
            ans.append(obj['name'])
        return ans
    except Exception:
        print Exception
        print 'error'
        time.sleep(100)
        return getStockList()
    


