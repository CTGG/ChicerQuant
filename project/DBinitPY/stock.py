from datetime import datetime
from AnyQuant import getSingleStock, getStockList
from pdCal import *
from sql import getCursor, setPrimaryKey
from industData import getMap
import pandas.io.sql as pdsql

__industry_name_=getMap()
def query(id):
    idr=id[2:]
    num=int(idr)
    t=__industry_name_.query('code=='+str(num))
    a=t.at[num,'name']
    b=t.at[num,'c_name']
    return a,b
def oneStock(id,starttime=datetime(2006,1,1),endtime=datetime.now()):
    df=getSingleStock(id,starttime,endtime)
    color(df)
    kdj(df)
    updown(df)
    ema(df)
    rsi(df)
    bias(df)  
    macd(df)
    poly(df)
    df.fillna(0,inplace=True)
    c_name,i_name=query(id)
    df['stock_id']=id
    df['stock_name']=c_name
    df['industry']=i_name
    cols = df.columns.tolist()
    cols = cols[-3:] + cols[:-3]
    df=df[cols]
    
    return df

def init():

    list=getStockList()
    total=len(list)
    count=1
    for v in list:
        print str(count)+'/'+str(total),'start',v
#         try:
        df=oneStock(v,starttime=datetime(2016,1,1),endtime=datetime(2016,8,30))
        [conn,cur]=getCursor()
        pdsql.to_sql(df, v, conn, flavor='mysql')
#         setPrimaryKey(v)
        break
#         except :
#             count+=1
#             print 'error'
#             continue
        print str(count)+'/'+str(total),'end',v
        count+=1
    return

def test():
    v='sh600216'
#     print str(count)+'/'+str(total),'start',v
#         try:
    df=oneStock(v,starttime=datetime(2016,1,1),endtime=datetime(2016,8,30))
    [conn,cur]=getCursor()
    pdsql.to_sql(df, v, conn, flavor='mysql')
    setPrimaryKey(v)
    return

if __name__ == '__main__':
    test()

