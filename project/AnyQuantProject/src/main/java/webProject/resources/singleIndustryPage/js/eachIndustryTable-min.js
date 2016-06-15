var app=angular.module("eachIndustryApp",[]);app.controller("eachIndustryTableCtrl",function(m,j){m.url="http://115.159.97.98/php/serviceController.php";var i=new Array();var g=-1;var d=0;var e=0;var l=[];var h=0;var c=0;var f=0;m.allIndustry=[];m.singleNum=0;m.singleClose=0;m.singleOpen=0;m.singleMin=0;m.singleMax=0;m.singleTotal=0;m.singleUpDown=0;m.Up=0;m.Down=0;m.Keep=0;console.log(localStorage.singleIndustryID);j.post(m.url,{industry_name:localStorage.singleIndustryID,method:"getStocksByIndustryService"}).success(function(o){m.error=false;m.data=o;m.allStocks=o;for(var n in m.allStocks){d++}b(localStorage.singleIndustryID);for(var n in m.allStocks){if(n<d-1){k(m.allStocks[n].stock_id)}}}).error(function(n){m.error=true;m.data=n||"Request failed"});function a(t){var p=new Date();p.setDate(p.getDate()+t);var u=p.getFullYear();var o=p.getMonth()+1;var s=p.getDate();var q=p.getDay();var n=new Array("星期一","星期二","星期三","星期四","星期五","星期六","星期日");var r="";if(q==0){r="星期日"}else{r=n[q-1]}if(r=="星期日"){s=s-2;if(s<=0){o--;s=30+s}}else{if(r=="星期一"){s=s-3;if(s<=0){o--;s=30+s}}else{if(r=="星期六"){s=s-1;if(s<=0){o--;s=30+s}}}}console.log(u+"-"+o+"-"+s);return u+"-"+o+"-"+s}function k(n){j.post(m.url,{name:n,date:localStorage.latestDate,method:"getStockByNameService"}).success(function(s,o){m.status=o;m.data=s;m.singleStock=s;var p=Object.keys(m.singleStock[0]);if(p.length>2){i[g+1]=new Array;i[g+1][0]=n;i[g+1][1]=m.singleStock[0].stock_name;i[g+1][2]=m.singleStock[0].open;i[g+1][3]=m.singleStock[0].high;i[g+1][4]=m.singleStock[0].low;i[g+1][5]=m.singleStock[0].close;i[g+1][6]=m.singleStock[0].volumn;i[g+1][7]=m.singleStock[0].adj_price;i[g+1][8]=m.singleStock[0].pe_ttm;i[g+1][9]=m.singleStock[0].pb;i[g+1][10]=m.singleStock[0].industry;var q=m.singleStock[0].close-m.singleStock[0].open;if(q>0){m.Up++}else{if(q<0){m.Down++}else{m.Keep++}}}else{i[g+1]=new Array;i[g+1][0]=n;i[g+1][1]="-";i[g+1][2]="-";i[g+1][3]="-";i[g+1][4]="-";i[g+1][5]="-";i[g+1][6]="-";i[g+1][7]="-";i[g+1][8]="-";i[g+1][9]="-";i[g+1][10]="-"}g++;if(g==d-2){g=0;var r=i;$(document).ready(function(){var t=[];$("#table").DataTable({rowCallback:function(v,u){if($.inArray(u.DT_RowId,t)!==-1){$(v).addClass("selected")}},createdRow:function(u){$("td",u).eq(4).css("color","green");$("td",u).eq(3).css("color","red")},data:r,columns:[{title:"股票代码"},{title:"股票简称"},{title:"开盘价"},{title:"最高价"},{title:"最低价"},{title:"收盘价"},{title:"成交量"},{title:"后复权价"},{title:"市盈率"},{title:"市净率"},{title:"所属行业"}]});$("#table tbody").on("click","tr",function(v){var x=this.id;var u=$.inArray(x,t);console.log(u);if(u===-1){t.push(x);var w=$(this).index();console.log(w);localStorage.singleStockID=$(this).eq(0)[0].firstChild.textContent;console.log(localStorage.singleStockID);window.location.href="../singleStockPage/singleStockPage.html"}else{t.splice(u,1)}$(this).toggleClass("selected")})})}}).error(function(p,o){m.data=p||"Request failed";m.status=o})}function b(n){j.post(m.url,{industry_name:n,date:localStorage.latestDate,method:"getIndustryService"}).success(function(t,o){m.status=o;m.data=t;m.eachIndustry=t;m.singleNum=m.eachIndustry[0].companySum;var q=m.eachIndustry[0].total/100000000;var r=m.eachIndustry[0].updown*100;var v=m.eachIndustry[0].close*1;var u=m.eachIndustry[0].open*1;var s=m.eachIndustry[0].min*1;var p=m.eachIndustry[0].max*1;m.singleClose=v.toFixed(4);m.singleOpen=u.toFixed(4);m.singleMin=s.toFixed(4);m.singleMax=p.toFixed(4);m.singleTotal=q.toFixed(4);m.singleUpDown=r.toFixed(4);document.getElementById("elementTitle").innerHTML=n;document.getElementById("elements").innerHTML="开盘价:"+m.singleOpen+"&nbsp&nbsp&nbsp&nbsp收盘价:"+m.singleClose+"<br/>最高价:"+m.singleMax+"&nbsp&nbsp&nbsp&nbsp最低价:"+m.singleMin+"<br/>涨跌幅:"+m.singleUpDown+"%<br/>总资产（亿）:"+m.singleTotal}).error(function(p,o){m.data=p||"Request failed";m.status=o})}});