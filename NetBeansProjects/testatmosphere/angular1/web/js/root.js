angular.module('root', ["services"])
        .config(["message4Provider", "messageText", function (message4Provider, messageText) {
                message4Provider.setText("hello");
                message4Provider.setText(messageText);
            }])
        .controller("index", ["$scope", "message", "square", "multiplier", "message4", function ($scope, message, square, multiplier, message4) {
                $scope.message = "Hello World!";
                $scope.message2 = "Hello Worldss!";
                $scope.favoriteWord;
                $scope.favoriteColor;
                $scope.favoriteShape;
                $scope.value = 1;
                $scope.isBold = function () {
                    return $scope.value % 2 === 0;
                };
                $scope.isItalic = function () {
                    return $scope.value % 3 === 0;
                };
                $scope.isUnderlined = function () {
                    return $scope.value % 5 === 0;
                };
                $scope.products = [
                    {id: 1, name: "Hockey puck"},
                    {id: 2, name: "Golf club"},
                    {id: 3, name: "Baseball bat"},
                    {id: 4, name: "Lacrosse stick"}
                ];
                $scope.isFirstElementVisible = true;
                $scope.isSecondElementVisible = true;
                $scope.message3 = message;
                $scope.product = square;
                $scope.product2 = multiplier.multiply(square);
                $scope.message4 = message4.text;
                $scope.$watch("factor_watch", function (newValue) {
                    $scope.product3 = newValue * 2;
                });
                $scope.factor_watch = 0;
//                var users = $resource("http://www.learn-angular.org/ResourceLesson/Users/:id");
//
//                $scope.getUser = function () {
//                    $scope.user = users.get({id: 1});
//                };
//
//                $scope.postUser = function () {
//                    var response = $scope.user.$save(function () {
//                        alert("User saved!");
//                    });
//                };

            }]);


