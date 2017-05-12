$(document).ready(function () {
    $("#alertDiv").hide();
});

$("#queryBtn").click(function(){
    // Get back to initial html
    $("#grid-basic").bootgrid("destroy");

    var scenario = $("#scenario").val();
    var id = "loss";
    switch(scenario) {
        case 'Loss rate': id = "loss"; break;
        case 'Round trip time': id = "rtt"; break;
        case 'Bandwidth': id = "bw"; break;
    }

    // Spinning to wait for visualization complete
    $('#spinner').modal('show');

    // Start loading data and rendering charts
    renderGrid(id);
    renderBarChart(id);
});

function renderGrid(idVal) {
    $("#grid-basic").bootgrid({
        ajax: true,
        post: function ()
        {
            /* To accumulate custom parameter with the request object */
            return {
                id: idVal
            };
        },
        url: "/table"
    });
}

function renderBarChart(id) {
    d3.json('/report?id=' + id, function (error, data) {
        doRender("#objNumChart", data.objNumChart);
        doRender("#objSizeChart", data.objSizeChart);
        $('#spinner').modal('hide');
    });
}

function doRender(chartId, data) {
    if (data == null) return;
    var chart = dc.barChart(chartId);
    var ndx = crossfilter(data.keys);
    var dim = ndx.dimension(function (d) { return d; });
    var grp = dim.group().reduceSum(function (d) { // Trick-1: param idx doesn't work here
        var idx = data.keys.indexOf(d);
        return data.values[0][idx].avg;
    });

    var height = 400, width = 500;
    chart.width(width)
        .height(height)
        .x(d3.scale.ordinal())
        .xUnits(dc.units.ordinal)
        .brushOn(false)
        .xAxisLabel(data.xAxisLabel)
        .yAxisLabel(data.yAxisLabel)
        .dimension(dim)
        .barPadding(0.1)
        .outerPadding(0.05)
        .group(grp, data.legends[0]);

    chart.legend(dc.legend().x(100));
    for (var i = 1; i < data.legends.length; i++) {
        (function (tmp) { // Trick-3: constant for closure (i is destroyed later)
            chart.stack(grp, data.legends[tmp], function (d, idx) {
                var diff = data.values[tmp][idx].avg - data.values[tmp - 1][idx].avg;
                return diff < 0 ? 0.01 : diff;
            });
        })(i);
    }
    chart.render();
}