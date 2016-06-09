import json
import urllib2
import sys
import scipy.stats as stats
import scipy.optimize as opt
import statsmodels.stats.diagnostic as stsmdsd
import statsmodels.tsa.stattools    as stsmdts
import pandas
import  numpy
import operator
from numpy import log

IP_ADDRESS='http://115.159.97.98/php/serviceController.php';

def getData(id,start,end):
    postData={'name':id,'startdate':start,'enddate':end,'method':'getStockAmongDateService'};
    req=urllib2.Request(IP_ADDRESS);
    req.add_header('Content-type', 'application/json');
    data=json.dumps(postData);
    response=urllib2.urlopen(req,data);
    return json.loads(response.read());

def parseDataForER(data):
    array={};
    i=0;
    for k,v in data.items():
        if k!= 'retmsg':
            array[i]=float(v['adj_price']);
            i+=1;
    ans={};
    for num in range(i-1):
        te=(array[num+1]/array[num])-0.01;
        ans[num]=log(te);
    return ans;

def tt(a,b):
    aa=a[0].split('-');
    an=0;
    an+=int(aa[0])*10000;
    an+=int(aa[1])*100;
    an+=int(aa[2]);
    bb = b[0].split('-');
    bn = 0;
    bn += int(bb[0]) * 10000;
    bn += int(bb[1]) * 100;
    bn += int(bb[2]);
    return an>bn;

def parseTimeER(data):
    array={};
    i=0;
    for k,v in data.items():
        if k != 'retmsg' :
            array[i]=float(v['adj_price']);
            i += 1;
    ans={};
    for num in range(i-1):
        te = (array[num + 1] / array[num]) - 0.01;
        tempdic=dict();
        ans[data[str(num)]['date']]=log(te);
    fi=sorted(ans.items(),key=operator.itemgetter(0));
    return  fi;


def getInterestRate(start,end):
    pass;
