from Daily import *
from datetime import datetime,timedelta
now=datetime()
use=datetime(2016,9,2)
while use<now:
    if ts.is_holiday(date=datetime.strftime(use,format='%Y-%m-%d')) :
        print 'is holiday'
    else:
        daily(use)
    use=use+timedelta(1)
    
    print datetime.strftime(use,format='%Y-%m-%d')