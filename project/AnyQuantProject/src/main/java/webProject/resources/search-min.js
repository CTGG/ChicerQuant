function SearchCtrl(a,b){a.url="http://115.159.106.212/php/serviceController.php";a.search=function(){b.post(a.url,{date:"---",name:a.keywords,method:"getStockByNameService"}).success(function(d,c){a.status=c;a.data=d;a.result=d}).error(function(d,c){a.data=d||"Request failed";a.status=c})}};