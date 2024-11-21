$(function () {
    function getParamsCar() {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: `/api/info`,
                type: 'GET',
                success: function (info) {
                    resolve({
                        brand: info.find(p => p.id == 1),
                        model: info.find(p => p.id == 2),
                        carInfo: info
                    });
                }
            });
        });
    }

    function refreshCarInfo(newBrand = "", newModel = "") {
        const brand = $('#modelCar').text().split('-')[0];
        const model = $('#modelCar').text().split('-')[1];
        $('#modelCar').text(`${newBrand ? newBrand : brand} - ${newModel ? newModel : model}`);
    }

    // set brand and model car in page
    (() => {
        getParamsCar().then(({ brand, model }) => {
            if (brand && model) {
                refreshCarInfo(brand.valueInfo, model.valueInfo);
            }
        });
    })();

    $('#modelCar').on('click', function () {
        const brand = $('#modelCar').text().split('-')[0];
        const model = $('#modelCar').text().split('-')[1];
        $('#carBrand').val(brand);
        $('#carModel').val(model);
    });

    $('#saveCarChangesBtn').on('click', function () {
        getParamsCar().then(({ brand, model }) => {
            if (!brand || !model) {
                return;
            }

            brand.valueInfo = $('#carBrand').val();
            model.valueInfo = $('#carModel').val();

            $.ajax({
                url: `/api/info`,
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(brand),
                success: function (info) {
                    refreshCarInfo($('#carBrand').val());
                }
            });

            $.ajax({
                url: `/api/info`,
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(model),
                success: function (info) {
                    refreshCarInfo("", $('#carModel').val());
                }
            });
        });
    });
});