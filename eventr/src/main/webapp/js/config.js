angular

    .module('eventsApp', ['ngRoute', 'restangular', 'valdr'])

    .config(function(RestangularProvider) {
        RestangularProvider.setBaseUrl('order-manager');
    })

    .config(function($routeProvider) {
        $routeProvider
            .when('/', {
                controller:'OrdersCtrl',
                templateUrl:'orders-list.html'
            })
            .when('/orders', {
                controller: 'OrdersCtrl',
                templateUrl:'orders-list.html'
            })
            .when('/orders/new', {
                controller:'OrderDetailCtrl',
                templateUrl:'order-detail.html'
            })
            .when('/orders/edit/:orderId', {
                controller:'OrderEditCtrl',
                templateUrl:'order-detail.html'
            })
            .otherwise({
                redirectTo:'/'
            });
    })

    .config(function(valdrProvider) {
        valdrProvider.setConstraintUrl('webresources/validation');
    })

    .factory('PersistenceService', ['Restangular', function(Restangular) {
        var ordersResource = Restangular.all('orders');
        var eventsResource = Restangular.all('events');

        return {
            getOrder: function(order) {
                return Restangular.one('orders', order).get();
            },
            getOrders: function(searchTerm) {
                if(searchTerm) {
                    return ordersResource.getList({ q: searchTerm });
                }
                else {
                    return ordersResource.getList();
                }
            },
            createOrder: function(order) {
                return ordersResource.post(order);
            },
            updateOrder: function(order) {
                return order.put();
            },
            deleteOrder: function(order) {
                return Restangular.one('orders', order.id).remove();
            },
            getEvents: function() {
                return eventsResource.getList();
            }
        }
    }])

    .controller('OrdersCtrl', function($scope, $location, PersistenceService) {
        $scope.getOrders = function() {
            PersistenceService.getOrders($scope.searchTerm).then(function (orders) {
                $scope.orders = orders;
            });
        };
        $scope.new = function() {
            PersistenceService.createOrder().then(function (order) {
                $location.path('/orders');
            });
        };
        $scope.remove = function(order) {
            PersistenceService.deleteOrder(order).then(function (order) {
                $scope.getOrders();
            });
        };
        $scope.getOrders();
    })

    .controller('OrderDetailCtrl', function($scope,  $location, PersistenceService) {
        PersistenceService.getEvents().then(function (events) {
            $scope.events = events;
        });

        $scope.order = {};

        $scope.save = function() {
            PersistenceService.createOrder($scope.order).then(function (order) {
                $location.path('/orders');
            });
        };

        $scope.cancel = function() {
            $location.path('/orders');
        };
    })
    .controller('OrderEditCtrl', function($scope,  $location, $routeParams, PersistenceService) {
        $scope.order;

        PersistenceService.getEvents().then(function (events) {
            $scope.events = events;
        });

        PersistenceService.getOrder($routeParams.orderId).then(function (order) {
            $scope.order = order;
        });

        $scope.save = function() {
            PersistenceService.updateOrder($scope.order).then(function (order) {
                $location.path('/orders');
            });
        };

        $scope.cancel = function() {
            $location.path('/orders');
        };
    })

    .run(function($rootScope) {
        $rootScope.$on('$routeChangeSuccess', function(ev, data) {
            if (data.$$route && data.$$route.controller) {
                $rootScope.controller = data.$$route.controller;
            }
       })
    });
