app.controller('GeneratorCtrl', [
    '$scope',
    '$http',
    'Messager',
    '$modal',
    function ($scope, $http, Messager, $modal) {

        $scope.setup = 0;
        $scope.forward = function (setup) {
            $scope.setup = setup;
        }


        $scope.onTableClick = function (table) {
            $scope.current = table;
            if (table.tableComment) {
                $scope.setting.entityName = table.tableComment;
            }
        }

        $scope.parseEnityName = function (table) {
            if (!table) {
                return;
            }
            table = table.toLowerCase();
            if ($scope.setting.tablePrefix) {
                table = table.replace($scope.setting.tablePrefix, "");
            }
            var names = table.split("_");
            var entityName = [];
            for (var i = 0; i < names.length; i++) {
                var name = names[i];
                if (name.length > 0) {
                    var upperName = name.substring(0, 1).toUpperCase() + name.substring(1, name.length);
                    entityName.push(upperName);
                }
            }
            return entityName.join("");
        }


        $scope.generate = function () {

            Messager.confirm("提示", "确定要生成代码?").then(function () {
                var setting = $scope.setting;
                var params = {
                    projectLocation: setting.projectLocation,
                    developer: setting.developer,
                    module: setting.module,
                    entityName: setting.entityName,
                    tableName: setting.tableName,
                    tablePrefix: setting.tablePrefix,
                    packageName: setting.packageName,
                    onlyGenerateEntity: setting.onlyGenerateEntity,
                    dbType: setting.dbType,
                    username: setting.username,
                    password: setting.password,
                    sqlUrl: setting.sqlUrl,
                    filterName: setting.filterName
                };
                $scope.generating = true;
                var toast = $scope.toaster.wait("正在生成...");
                $http.post('generator/generate', params).success(
                    function (data, status, headers, config) {
                        $scope.generating = false;
                        if (data.success) {
                            /* $scope.settingForm.$setPristine();
                             $scope.settingForm.$setUntouched();*/
                            toast.doSuccess("生成成功");
                            $scope.forward(0);
                        } else {
                            toast.doError("生成失败：" + (result.msg || status));
                        }
                    }).error(function (result, status) {
                    $scope.generating = false;
                    toast.doError("生成失败：" + (result.msg || status));
                });
            });


        }
    }]);
