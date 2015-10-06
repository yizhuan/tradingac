var app = angular.module('tradingapp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute',
    'ngAnimate'
]);

app.config(['$routeProvider', '$locationProvider', function ($routeProvider) {
    $routeProvider.when('/traders/:id/overview', {
        templateUrl: 'views/account-overview.html',
        controller: 'AccountOverviewCtrl',
        controllerAs: 'overview'
    }).when('/traders/:id/:symbol/details', {
        templateUrl: 'views/stock-details.html',
        controller: 'StockDetailsCtrl',
        controllerAs: 'stockDetails'         	        	
    }).otherwise({
        redirectTo: '/'
    })
}]);



app.controller('AccountOverviewCtrl', ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {
    $http.get('/api/traders/'+$routeParams.id+'/portfolios')
    .success(function (data) {
    	$scope.traderId = data.traderId;
        $scope.summary = data.summary;
        $scope.assets = data.assets;
    }).error(function (data, status) {
        console.log('Error ' + data)
    });
    
	$scope.submit = function(){
		var tradingReq = {
				symbol : $scope.buy.symbol,
				shares : $scope.buy.shares,
				price: $scope.buy.price,
				type: 0
		};
		var url = "/api/traders/"+$routeParams.id+"/buy";
		
		$http.post(url, tradingReq).
		  then(function(response) {
			  //success
		  }, function(response) {
			  //error handler here
		  });
		
		
		$scope.buy.symbol = "";
		$scope.buy.shares = "";
		$scope.buy.price = "";
	};    
    
    $scope.deleteAsset = function(symbol){
    	
    };
}]);

app.controller('StockDetailsCtrl', ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {
   
}]);


app.controller('SellCtrl', ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {
		

	
}]);


app.controller('BuyCtrl', ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {
	

	
}]);




app.controller('BuyHistoryCtrl', ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {
    $http.get('/api/traders/'+$routeParams.id+'/trades/'+$routeParams.symbol+'/buys')
    .success(function (data) {
        $scope.buys = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    });
    
    
	$scope.submit = function(){
		var tradingReq = {
				symbol : $scope.sell.symbol,
				shares : $scope.sell.shares,
				price: $scope.sell.price,
				costPerShare: $scope.sell.costPerShare,
				type: 0
		};
		var url = "/api/traders/"+$routeParams.id+"/sell";
		
		$http.post(url, tradingReq).
		  then(function(response) {
			  //success
		  }, function(response) {
			  //error handler here
		  });
		
		
		$scope.sell.symbol = "";
		$scope.sell.shares = "";
		$scope.sell.price = "";
		$scope.sell.costPerShare = "";
	} ;
	
	$scope.onSelect = function(symbol, shares, costPerShare){
		$scope.sell.symbol = symbol;
		$scope.sell.shares = shares;
		$scope.sell.price = "";
		$scope.sell.costPerShare = costPerShare;
		
		//$scope.$apply();
	};
    
}]);

app.controller('SellHistoryCtrl', ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {
	$http.get('/api/traders/'+$routeParams.id+'/trades/'+$routeParams.symbol+'/sells')
    .success(function (data) {
        $scope.sells = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    });     
}]);


