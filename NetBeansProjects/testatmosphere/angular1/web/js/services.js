angular.module("services", [])
        .constant("messageText", "Hello constant!")
        .provider("message4", [function () {
                var text = null;
                this.setText = function (textString) {
                    text = textString;
                };
                this.$get = [function () {
                        return new Messages(text);
                    }];
            }])
        .value("message", "Hello world 3")
        .value("factor", 9)
        .value("factors", 6)
        .service("multiplier", ["factors", "factors", Multiplier])
        .factory("square", ["factor", function (factor) {
                return factor * factor;
            }])
        .filter("round", function () {
            return function (input, precision) {
                return input ?
                        parseFloat(input).toFixed(precision) :
                        "";
            };
        })
        .filter("dollars", function () {
            return function (input) {
                return input ? "$" + input : "";
            };
        });




