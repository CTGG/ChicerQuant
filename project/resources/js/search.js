/**
 * Created by QiHan on 2016/5/14.
 */
function toSingleStockPage() {

    var parm1 = document.getElementById("search").value;
    var resultsOutput = document.getElementById('results').innerHTML;
    var b = "<li onclick=\"javascript:a(this.innerText);\">" + parm1 + "</li>";
    if ((resultsOutput.length == 0 )||(resultsOutput.indexOf(b)==-1)) {

        $.extend($.gritter.options, {
            time: 1500,
        });
        // clean the wrapper position class
        $('#gritter-notice-wrapper').attr('class', '');
        // global setting override
        $.extend($.gritter.options, {
            position: '' + $(this).attr('id') + '' // possibilities: bottom-left, bottom-right, top-left, top-right
        });
        $.gritter.options.position = "bottom-right";
        $.gritter.add({
            title: $(this).find('span.title').text(), // could be simpler, just for demo purposes
            text: "您好！" + "</br>" + "该股票不存在或不完整，请重新输入",
        });
    //    alert("您好，该股票不存在或不完整，请重新输入");

    } else {
        var id1 = parm1.split('(');
        var id = id1[1].split(')');
//    alert(id[0]);
        localStorage.singleStockID = id[0];
        window.location.href = "../singleStockPage/singleStockPage.html";
    }
}

function allStock2SingleStockPage(stockID) {
    localStorage.singleStockID=stockID;
    window.location.href="../singleStockPage/singleStockPage.html";
}


var searchApp = angular.module('mySearchApp',[]);
searchApp.controller('searchCtrl', function ($scope, $http) {

    $scope.url = 'http://anyquant.net:15000/php/serviceController.php'; // The url of our search

    var array=new Array();
    var count=0;

    $http.post($scope.url, {"method": "getAllStocksService"}).
        success(function(data) {
            $scope.error = false;
            $scope.data = data;
            $scope.allStocks =data;

            //   console.log($scope.allStocks);
            for(var item in $scope.allStocks) {
                count++;
            }

            for(var item in  $scope.allStocks) {
                if (item < count-1) {
                    array[item] = $scope.allStocks[item].stock_name+"("+$scope.allStocks[item].stock_id+")";
               //     console.log(array[item] );
                }
            }


            //   console.log($scope.table);

            //initStockPreviewData();//调用linechart的初始化方法
        })
        .
        error(function(data) {
            $scope.error = true;
            $scope.data = data || "Request failed";

        });


    (function() {
        var displayResults, findAll, maxResults, names, resultsOutput, searchInput;

        names =  array;

        findAll = (function(_this) {
            return function(wordList, collection) {
                return collection.filter(function(word) {
                    word = word.toLowerCase();
                    return wordList.some(function(w) {
                        return ~word.indexOf(w);
                    });
                });
            };
        })(this);
        displayResults = function(resultsEl, wordList) {
            return resultsEl.innerHTML = (wordList.map(function(w) {
                return '<li onclick=javascript:a(this.innerText);>' + w + '</li>';
            })).join('');
        };
        searchInput = document.getElementById('search');
        resultsOutput = document.getElementById('results');

        var winWidth=0;
        if (window.innerWidth)
            winWidth = window.innerWidth;
        else if ((document.body) && (document.body.clientWidth))
            winWidth = document.body.clientWidth;
        resultsOutput.style.width= winWidth/6+"px";

        maxResults = 7;
        var empty=[];

        searchInput.addEventListener('keyup', (function(_this) {
            return function(e) {
                var suggested, value;
                value = searchInput.value.toLowerCase().split(' ');
                suggested = (value[0].length ? findAll(value, names) : []);

               if(value[0].length==0){
                   resultsOutput.style.visibility = "hidden";
               }else {
                   resultsOutput.style.visibility = "visible";
               }
                    //console.log(suggested[0]);
                return displayResults(resultsOutput, suggested);
            };
        })(this));


    }).call(this);

});

function a(w){
    // console.log(w);
    document.getElementById('search').value=w;
    /*    var id1= w.split('(');
    var id= id1[1].split(')');
    alert(id[0]);
      var value = w.replace(/[^\a-\z\A-\Z0-9]/ig,"");
     alert(value);
     */
}

